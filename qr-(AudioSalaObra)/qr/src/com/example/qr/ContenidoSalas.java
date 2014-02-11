package com.example.qr;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;

import com.example.qr.ContenidoObras.FtpAudio;

import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContenidoSalas extends Activity {
	TextView tv1, tv2;
	ImageView img;
	Button btnSalaObra, btnRec, btnPlay;
	ProgressDialog pDialog;
	int ftplogin=0;
	MediaPlayer mp;
	int posicion = 0;
	String ftplog=String.valueOf(ftplogin), error="ninguno!", idsala = "";
	public FTPClient mFTPClient = null;
	private String nom = "", dir = "";
	String nombre="", idSala="";
	private static int TAKE_PICTURE = 1;
	Consumirws ws = new Consumirws();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contenido_salas);
		destruir();
		
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.tvDescripcionSala);
		img = (ImageView)findViewById(R.id.imageView1);
		btnSalaObra = (Button) findViewById(R.id.btnObrasSala);
		btnRec = (Button) findViewById(R.id.btnREC);
		btnPlay = (Button) findViewById(R.id.btnAudio);
		Bundle extras = getIntent().getExtras();
		
		String[] separated = extras.getString("result").split("=>");
		
		dir = Environment.getExternalStorageDirectory() + "/test.jpg";;
		
		idsala = separated[0];
		FtpExecute ftp = new FtpExecute();
  	    String[] sala = {idsala,separated[(separated.length-1)]};	
	    
  	    idSala = separated[0];
  	    nombre = separated[1];
		tv1.setText("Nombre: " + separated[1]);
		tv2.setText("Descripción: " + separated[2]);
		btnPlay.setText("Play");
		ftp.execute(sala);
		btnSalaObra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.print( "hola:" );
				Intent intent = new Intent(ContenidoSalas.this,ListaObras.class);
				intent.putExtra("idSala", idsala);
				startActivity(intent);
			}
		});
		
		btnRec.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    	
                int code = TAKE_PICTURE;
                
                Uri output = Uri.fromFile(new File(dir));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                
                startActivityForResult(intent, code);
			}
		});
		
		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(btnPlay.getText().toString()=="Play"){
					FtpAudio ftpaudio = new FtpAudio();
					ftpaudio.execute(idSala,nombre);				
					btnPlay.setText("Stop");
				}else{
					btnPlay.setText("Play");
					detener(v);
				}
			}
				
                
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contenido_salas, menu);
		return true;
	}

	 @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (requestCode == TAKE_PICTURE) {
     		Toast.makeText(this, "TAKE_PICTURE", Toast.LENGTH_SHORT).show();
	        
     		//llamo al hilo para subir la imagen
     		ReconocimientoImg ftpImg = new ReconocimientoImg();
	    	String[] obra = {nom,dir,idsala};
	    	ftpImg.execute(obra);
	    	
	        new MediaScannerConnectionClient() {
	        	private MediaScannerConnection msc = null; {
	        		msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
	        	}
	        	public void onMediaScannerConnected() {
	        		msc.scanFile(dir, null);
	        	}
                public void onScanCompleted(String path, Uri uri) {
                	msc.disconnect();
                }
            };   
		}
	 }
	 
	class FtpExecute extends AsyncTask<String,String,Bitmap>{
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(ContenidoSalas.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
 
        @Override
        protected Bitmap doInBackground(String... params) {
	    	Bitmap bitmap=null;
        	try {
        		MyFTP ftp = new MyFTP(getApplicationContext());
		    	if(ftp.LoginSalas()){
		    		bitmap = ftp.GetImgSala(params[0],params[1],true,0);
		    	}
			} catch (Exception e) {
				System.out.println("error: " + e);
			}
        	return bitmap;
        }
 
        @Override
        protected void onPostExecute(Bitmap bitmap) {
        	if(bitmap != null){
    			img.setImageBitmap(bitmap);
    		}
        	else{
    			System.out.print(" img: null ");
    		}
        	pDialog.dismiss();
        }
    }

	class ReconocimientoImg extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(ContenidoSalas.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
        	String result="";
        	String nombre = params[0], directorio = params[1], idDeSala = params[2];
        	String idObra = "";
        	try {
        		MyFTP ftp = new MyFTP(getApplicationContext());
        		Consumirws ws = new Consumirws();
		    	if(ftp.LoginObras()){	
		    		if(ftp.subirImgObra(nombre,directorio)){
		    			idObra = ws.getNombreObraDescriptor(Integer.parseInt(idDeSala), nombre);
		    			result = ws.getDataObraId(Integer.parseInt(idObra));
		    		}
		    	}
			} catch (Exception e) {
				result="Error: "+e;
			}
        	return result;
        }
 
        @Override
        protected void onPostExecute(String v) {
        	pDialog.dismiss();
        	System.out.print(" resultado :"+v);
        	Intent intent = new Intent(ContenidoSalas.this,ContenidoObras.class);
        	intent.putExtra("result", v);
        	startActivity(intent);
        }
    }
	
	class FtpAudio extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(ContenidoSalas.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
        	String result="Audio..";
        	String nombre=params[1];
        	String idSala=params[0];
        	String audio = null;
        	try {
        		MyFTP ftp = new MyFTP(getApplicationContext());
		    	if(ftp.LoginSalas()){
		    		
                    result = ws.getContenidoSalaId(Integer.parseInt(idSala));
		    		
		    		String separatedaux[] = result.split("=>");
		    		String separatedaudio[] = separatedaux[0].split("/");
		    		audio = separatedaudio[separatedaudio.length-1];
		    		result = "Audio : " + audio + " ID: " + idSala;
		    		
//		    		result = ws.getContenidoSalaNombre(nombre);
//		    		String separatedaux[] = result.split("=>");
//		    		audio = separatedaux[0];
		    		//result = "Audio : " + audio + " ID: " + idSala;
		    		if(audio!=null){
			    		if(ftp.getAudioSala(idSala, audio, true, 0)){
			    			File fil = new File(ContenidoSalas.this.getCacheDir() + "/" + audio );
			    			FileInputStream fis = new FileInputStream(fil.getPath());
			    			if(fil.exists()){
				    			mp = new MediaPlayer();
				    			mp.setDataSource(fis.getFD());
				    	        if(mp == null) {            
				    	           result = "Create() on MediaPlayer failed";       
				    	        } 
				    	        else {
				    	            mp.setOnCompletionListener(new OnCompletionListener() {
										@Override
										public void onCompletion(MediaPlayer mp) {
											// TODO Auto-generated method stub
									        if (mp != null) {
									            mp.stop();
									            mp.release();
									            mp = null;
									            posicion = 0;
									            btnPlay.setText("Play");
									        }
										}
				    	            });
				    	            mp.prepare(); 
				    	            mp.start();
				    	            result = "Reprodiciendo...";		
				    	        }
			    			}
			    			else{
			    				result = "Sala sin audio ";
			    			}
			    			fis.close();
			    		}
		    		}
		    		else{
		    			
		    		}
		    	}
			}catch(NullPointerException n){
				result = "Sala sin audio "+ result;
			}catch (Exception e) {
				result = "Sala sin audio "+ result;
			}
        	return result;
        }
        @Override
        protected void onPostExecute(String v) {
        	pDialog.dismiss();
        	System.out.print(" resultado :"+v);
        	Toast.makeText(ContenidoSalas.this, "Result:" + v,Toast.LENGTH_LONG).show();
        }
}
	
	public void destruir() {
        if (mp != null)
            mp.release();
        	mp = null;
    }
	
    public void detener(View v) {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
            posicion = 0;
        }
    }
}
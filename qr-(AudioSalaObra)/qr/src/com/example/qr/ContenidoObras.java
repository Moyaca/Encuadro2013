package com.example.qr;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContenidoObras extends Activity {
	TextView tv1,tv2,tv3;
	Button btnPlay, btnVideo;
	ImageView img;
	public FTPClient mFTPClient = null;
	ProgressDialog pDialog;
	MediaPlayer mp;
	int posicion = 0;
	String nombre="", idObra="", videocache="";
	Consumirws ws = new Consumirws();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contenido_obras);
		
		destruir();
		
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		img = (ImageView)findViewById(R.id.imageView1);
		btnPlay = (Button) findViewById(R.id.btnPlay);
		btnVideo = (Button) findViewById(R.id.btnVideo);
		
		Bundle extras = getIntent().getExtras();
		System.out.print(extras);
		String[] separated = extras.getString("result").split("=>");
		String[] separatedImg = separated[4].split("/");
		
		FtpExecute ftp = new FtpExecute();
    	String[] obra = {separated[0],separatedImg[(separatedImg.length-1)]};
    	ftp.execute(obra);
		
    	idObra = separated[0];
    	nombre = separated[1];
    	tv1.setText("Nombre Obra: " + separated[1]);
		tv2.setText("Autor: " + separated[6]);
		tv3.setText("Descripcion Obra: " + separated[2]);
		btnPlay.setText("Play");
		
		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(btnPlay.getText().toString()=="Play"){
					FtpAudio ftpaudio = new FtpAudio();
					ftpaudio.execute(idObra,nombre);				
					btnPlay.setText("Stop");
				}else{
					btnPlay.setText("Play");
					detener(v);
				}
			}
		});
		
		btnVideo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				descargarVideo();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contenido_obras, menu);
		return true;
	}
	
	class FtpExecute extends AsyncTask<String,String,Bitmap>{
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(ContenidoObras.this);
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
		    	if(ftp.LoginObras()){
		    		bitmap = ftp.GetImgObra(params[0],params[1],true,0);
		    	}
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
        	return bitmap;
        }
 
        @Override
        protected void onPostExecute(Bitmap bitmap) {
        	if(bitmap != null){
    			img.setImageBitmap(bitmap);
    		}else{
    			System.out.print(" img: null ");
    		}
        	pDialog.dismiss();
        }
    }

	class FtpAudio extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(ContenidoObras.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
        	String result="Audio..";
        	String nombre=params[1];
        	String idObra=params[0];
        	String audio = null;
        	try {
        		MyFTP ftp = new MyFTP(getApplicationContext());
		    	if(ftp.LoginObras()){
		    		result = ws.getContenidoObra(nombre);
		    		String separatedaux[] = result.split("=>");
		    		String separatedAudio[] = separatedaux[1].split("/");
		    		audio = separatedAudio[separatedAudio.length-1];
		    		result = "Audio : " + audio + " ID: " + idObra;
		    		if(audio!=null){
			    		if(ftp.getAudioObra(idObra, audio, true, 0)){
			    			File fil = new File(ContenidoObras.this.getCacheDir() + "/" + audio );
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
			    				result = "Obra sin audio ";
			    			}
			    			fis.close();
			    		}
		    		}
		    		else{
		    			result = "Obra sin audio ";
		    		}
		    	}
			}catch(NullPointerException n){
				result = "Obra sin audio ";
			}catch (Exception e) {
				result = "Obra sin audio ";
			}
        	return result;
        }
 
        @Override
        protected void onPostExecute(String v) {
        	if(v=="Obra sin audio ") btnPlay.setText("Play");
        	Toast.makeText(getApplicationContext(), v, Toast.LENGTH_LONG).show();
        	System.out.print(v);
        	pDialog.dismiss();	
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

	private void descargarVideo(){
//		System.out.print( "Entro a descargarVideo\n");
		obtenerNombreVideo name = new obtenerNombreVideo();
		name.execute();
		if (name != null && name.getStatus() == AsyncTask.Status.FINISHED) {
			if (nombre.equals("")){
		    	Toast.makeText(this, "No se pudo obtener el nombre de video", Toast.LENGTH_LONG).show();
			}
			else{
		    	Toast.makeText(this, "El video es: "+ nombre, Toast.LENGTH_LONG).show();
			}
	    }
	}
	
	class obtenerNombreVideo extends AsyncTask<String,String,String>{
	    @Override
	    protected void onPreExecute() {
	    	pDialog = new ProgressDialog(ContenidoObras.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
	    }
	
	    @Override
	    protected String doInBackground(String... params) {
	    	String video="";
	    	try {
	    		String consulta =ws.getContenidoObra(nombre);
	    		String[] separated = consulta.split("=>");
				video= separated[2];
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
	    	return video;
	    }
	
	    @Override
	    protected void onPostExecute(String video) {
	    	pDialog.dismiss();
	    	if(video.equals("")){
				nombre="";
			}
			else{
				obtenerVideo obtener = new obtenerVideo();
				obtener.execute(video);
	        	pDialog.dismiss();
			}
	    	pDialog.dismiss();
	    }
	}
	
	class obtenerVideo extends AsyncTask<String,Boolean,Boolean>{
	    @Override
	    protected void onPreExecute() {
	    	pDialog = new ProgressDialog(ContenidoObras.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
	    }
	
	    @Override
	    protected Boolean doInBackground(String... params) {
	    	Boolean file=false;
	    	try {
	    		MyFTP ftp = new MyFTP(getApplicationContext());
		    	if(ftp.LoginObras()){
		    		String[] nombrevideo = params[0].split("/");
		    		file = ftp.getVideoObra(idObra, nombrevideo[5], true, 0);
		    		videocache=nombrevideo[5];
		    		System.out.println("Params: " + nombrevideo[5]);
		    	}
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
	    	return file;
	    }
	
	    @Override
	    protected void onPostExecute(Boolean file) {
	    	pDialog.dismiss();
	    	if(file != false){
	    		reproducirVideo();
			}
			else{
				Toast.makeText(getApplicationContext(), "No se encuentra video para esta obra", Toast.LENGTH_LONG).show();
			}
	    	System.out.println(" : ");
	    }
	}

    private void reproducirVideo(){
    	System.out.println("reproducirVideo");
		Intent i= new Intent(ContenidoObras.this, ReproducirVideo.class);
		i.putExtra("video",videocache);
		startActivity(i);
	}
}

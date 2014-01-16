package com.example.qr;


import org.apache.commons.net.ftp.FTPClient;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContenidoObras extends Activity {

	TextView tv1;
	TextView tv2;
	TextView tv3;
	Button btnPlay;
	Button btnPause;
	ImageView img;

	public FTPClient mFTPClient = null;
	ProgressDialog pDialog;
	MediaPlayer mp;
	int posicion = 0;
	
	String nombre="", idObra="";
	
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
		btnPause = (Button) findViewById(R.id.btnPause);
		
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
		btnPause.setText("Pause");
		
		
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
		
		btnPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(btnPause.getText().toString()=="Pause"){
					pausar(v);
					btnPause.setText("Continuar");
				}else{
					btnPause.setText("Pause");
					continuar(v);
				}
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
    			Consumirws ws = new Consumirws();
		    	if(ftp.LoginObras()){
		    		result = ws.getContenidoObra(nombre);
		    		String separatedaux[] = result.split("=>");
		    		String separatedAudio[] = separatedaux[1].split("/");
		    		audio = separatedAudio[separatedAudio.length-1];
		    		result = "Audio : " + audio + " ID: " + idObra;
		    		if(audio!=null){
			    		if(ftp.getAudioObra(idObra, audio, true, 0)){
			    			//File file = new File(Multimedia.this.getCacheDir(), "fun.-WeAreYoung(Feat.JanelleMonae).mp3");
			    			Uri datos = Uri.parse(ContenidoObras.this.getCacheDir() + "/" + audio );
			    	        mp = MediaPlayer.create(ContenidoObras.this, datos);
			    	        mp.start();
			    	        result = "Reproduciendo..";
			    		}
		    		}else{
		    			result = "Obra sin audio";
		    		}
		    	}
			}catch(NullPointerException n){
				result = "Obra sin audio";
			}catch (Exception e) {
				result = e.toString();
			}
        	return result;
        }
 
        @Override
        protected void onPostExecute(String v) {
        	Toast.makeText(getApplicationContext(), v, Toast.LENGTH_LONG).show();
//        	System.out.print(v);
        	pDialog.dismiss();	
        }
    }
	
		public void destruir() {
	        if (mp != null)
	            mp.release();
	    }
	    public void pausar(View v) {
	        if (mp != null && mp.isPlaying()) {
	            posicion = mp.getCurrentPosition();
	            mp.pause();
	        }
	    }
	    public void continuar(View v) {
	        if (mp != null && mp.isPlaying() == false) {
	            mp.seekTo(posicion);
	            mp.start();
	        }
	    }
	    public void detener(View v) {
	        if (mp != null) {
	            mp.stop();
	            posicion = 0;
	        }
	    }
}


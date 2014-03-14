package com.example.ftp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.media.AudioTrack;
import android.media.MediaPlayer;

public class Multimedia extends Activity {
	AudioTrack AT;
	ProgressDialog pDialog;
	private Button btnPlay;
	private Button btnPause;
	MediaPlayer mp;
	int posicion = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multimedia);
		destruir();
		
		btnPlay = (Button) findViewById(R.id.button1); btnPlay.setText("Play");
		btnPause = (Button) findViewById(R.id.button2); btnPause.setText("Pause");
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(btnPlay.getText().toString()=="Play"){
					FtpExecute ftp = new FtpExecute();
					ftp.execute("");
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

	class FtpExecute extends AsyncTask<String,String,String>{
		 
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(Multimedia.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
        	String result="nada";
        	try {
        		MyFTP ftp = new MyFTP(getApplicationContext());
		    	if(ftp.LoginObras()){
		    		result = "login";
		    		if(ftp.getAudioObra("159", "Adele-RollingInTheDeep.mp3", true, 0)){
		    			//File file = new File(Multimedia.this.getCacheDir(), "fun.-WeAreYoung(Feat.JanelleMonae).mp3");
		    			Uri datos = Uri.parse(Multimedia.this.getCacheDir() + "/Adele-RollingInTheDeep.mp3");
		    	        mp = MediaPlayer.create(Multimedia.this, datos);
		    	        mp.start();
		    	        result ="play";
		    		}
		    	}
			} catch (Exception e) {
				result = e.toString();
			}
        	return result;
        }
 
        @Override
        protected void onPostExecute(String v) {
        	Toast.makeText(getApplicationContext(), v, Toast.LENGTH_LONG).show();
        	pDialog.dismiss();	
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.multimedia, menu);
		return true;
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

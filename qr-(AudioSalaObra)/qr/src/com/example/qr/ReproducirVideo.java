package com.example.qr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.VideoView;

public class ReproducirVideo extends Activity {
	
	private VideoView reproductor;
	File convertedFile;
	FileOutputStream out;
	MediaPlayer mp;
	SurfaceView surfaceView;
	SurfaceHolder holder;
	MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reproducir_video);
		
		Bundle extras = getIntent().getExtras();
		String video = extras.getString("video");
		
		File vid = new File(getApplicationContext().getCacheDir() ,"/"+video);
		System.out.println("La cache: "+getApplicationContext().getCacheDir()+"/"+video);
		verContenidoDirectorio();

		if(!vid.exists())
			try {
				vid.createNewFile();
				vid.setReadable(true, false);
				
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		
		try {
			Runtime.getRuntime().exec("chmod 755 "+getCacheDir() +"/"+ video);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		reproductor =(VideoView)findViewById(R.id.surfaceView); 
		
		try{
			reproductor.setVideoPath(vid.getAbsolutePath()); 
			reproductor.setMediaController(new MediaController(this));
			reproductor.start(); 
			 
			reproductor.requestFocus(); 
		}
		catch (Exception e){
			System.out.println(" Catch: " + e);  
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reproducir_video, menu);
		return true;
	}
	
	public void verContenidoDirectorio(){ 
		File f = new File(this.getCacheDir().getPath()); 
		File file[] = f.listFiles(); 	
		System.out.println("Path: " + this.getCacheDir().getPath()); 
		for (int i=0; i < file.length; i++) { 
			System.out.println(" FileName:" + file[i].getName());  
		}
	}
}

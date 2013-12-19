package com.example.encuadroapp;

import java.io.File;

import org.apache.commons.net.ftp.FTPClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContenidoSalas extends Activity {
	
	TextView tv1;
	TextView tv2;
	ImageView img;
	public FTPClient mFTPClient = null;
	ProgressDialog pDialog;
	int ftplogin=0;
	String ftplog=String.valueOf(ftplogin);
	String error="ninguno!";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contenido_salas);
		
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		img = (ImageView)findViewById(R.id.imageView1);
		
		Bundle extras = getIntent().getExtras();
		String[] separated = extras.getString("result").split("=>");
		
		FtpExecute ftp = new FtpExecute();
  	    String[] sala = {separated[0],separated[(separated.length-1)]};	
  	    Toast.makeText(this, separated[0] + separated[(separated.length-1)], Toast.LENGTH_LONG).show();
    	ftp.execute(sala);
		tv1.setText("Nombre: " + separated[1]);
	    
		//tv2.setText("Descripción: " + separated[2]);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contenido_salas, menu);
		return true;
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
        		
//		    	File file = new File(getApplicationContext().getCacheDir().getPath());
//		    	File[] s = file.listFiles();
//		    	for(int i=0;i<=s.length;i++){
//			    	System.out.println(s[i]);	
//		    	}
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
    		}else{
    			System.out.print(" img: null ");
    		}
        	pDialog.dismiss();
        	System.out.println(" : ");
        }
    }

}

package com.example.ftp;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView txtConectar;
	private Button btnBajar;
	public FTPClient mFTPClient = null;
	ProgressDialog pDialog;
	int ftplogin=0;
	String ftplog=String.valueOf(ftplogin);
	String error="ninguno!";
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		img = (ImageView)findViewById(R.id.imageView1);
		btnBajar = (Button) findViewById(R.id.btnBajar);
		txtConectar = (TextView) findViewById(R.id.txtConecta);
		
		btnBajar.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view){
		    	
		    	FtpExecute ftp = new FtpExecute();
		    	String[] obra = {"151","Figari_pericon.jpg"};
		    	ftp.execute(obra);
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class FtpExecute extends AsyncTask<String,String,String>{
		 
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
        	try {
        		MyFTP ftp = new MyFTP(getApplicationContext());
//		    	Bitmap bitmap=null;
		    	if(ftp.LoginObras()){
//		    		bitmap = ftp.GetImgObra(params[0],params[1],true,0);
//		    		if(bitmap != null){
//		    			img.setImageBitmap(bitmap);
//		    		}
		    		if(ftp.subirImgObra(params[0],params[1])){
		    			return "subio";
		    		}
		    	}
			} catch (Exception e) {
				System.out.println("error: " + e);
				
			}
        	return "no funco";
        }
 
        @Override
        protected void onPostExecute(String v) {
        	//Toast.makeText(getApplicationContext(), v, Toast.LENGTH_LONG).show();
        	pDialog.dismiss();
        	Toast.makeText(getApplicationContext(), v, Toast.LENGTH_LONG).show();
        }
    }

	public void bajarArchivo() throws SocketException, UnknownHostException, IOException {
		FTPClient client = new FTPClient();
		FileOutputStream fos = null;
		
		try {
	    	Toast.makeText(getApplicationContext(), "BajarArchivo!", Toast.LENGTH_LONG).show();
			
	    	client.connect("ftp://10.0.2.109");
	    	client.login("obras", "12345678");

	    	// Create an OutputStream for the file
	    	String filename = "test.jpg";
	    	fos = new FileOutputStream(filename);
	    	 
	    	// Fetch file from server 
	    	client.retrieveFile("/153/imagen/" + "figari_toque.jpg", fos);
	        
	        Toast.makeText(getApplicationContext(), "FTP Correcto!", Toast.LENGTH_LONG).show();
	 
	    } 
		
		catch (Exception e){
	    	Toast.makeText(getApplicationContext(), "Excepcion!"+e, Toast.LENGTH_LONG).show();
	        Log.i("consola","Ups...");
	    }
		
		try {
			if (fos != null) {
				fos.close();
			}
			client.disconnect();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

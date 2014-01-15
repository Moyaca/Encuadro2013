package com.example.qr;
	 
import java.io.File;
import android.media.MediaScannerConnection;
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
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button b1;
	private Button b2;
	private Button buttonQR;
	ProgressDialog pDialog;
	
	private String name = "";
	private static int TAKE_PICTURE = 1;
//	public String sala ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		b1 = (Button) findViewById(R.id.btnEscO);
		b2 = (Button) findViewById(R.id.btnObrasSala);
		buttonQR = (Button) findViewById(R.id.btnEscS);
		name = Environment.getExternalStorageDirectory() + "/test.jpg";
		
		b1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent inten = new Intent(MainActivity.this, MenPpal.class); 
					startActivity(inten);
				}
		});
		buttonQR.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Intent intent = new Intent("com.example.qr.SCAN");
					//startActivityForResult(intent, 0);
					Intent inten = new Intent(MainActivity.this, MenPpalAuto.class); 
					startActivity(inten);
				}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				if(sala==""){
//					
//				}else{
	                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    	
	                int code = TAKE_PICTURE;
			    	
	                Uri output = Uri.fromFile(new File(name));
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
	                
	                startActivityForResult(intent, code);
//				}
			}
	});
	        
	}
	@Override
public void onActivityResult(int requestCode, int resultCode, Intent intent) { 
		 Toast.makeText(getApplicationContext(),
                 "Entro", Toast.LENGTH_SHORT).show();
		 if (requestCode == TAKE_PICTURE) {
	     		Toast.makeText(this, "TAKE_PICTURE", Toast.LENGTH_LONG).show();

		      //llamo al hilo para subir la imajen
	            System.out.print("antes del execute");
		    	FtpExecute ftp = new FtpExecute();
		    	String[] obra = {"test.jpg",name};
		    	ftp.execute(obra);
		        new MediaScannerConnectionClient() {
		        	
		        	private MediaScannerConnection msc = null; {
		        		msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
		        	}
		        	public void onMediaScannerConnected() {
		        		msc.scanFile(name, null);
		        	}
		                    public void onScanCompleted(String path, Uri uri) {
		                        msc.disconnect();
		                    }
	            };   
			}
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
        	String result="no funco";
        	try {
        		MyFTP ftp = new MyFTP(getApplicationContext());
        		Consumirws ws = new Consumirws();
		    	if(ftp.LoginObras()){	
		    		result="Login";
		    		if(ftp.subirImgObra(params[0],params[1])){
		    			result="subio";
		    			String idObra = ws.getNombreObraDescriptor(161, params[0]);
//		    			result = ws.getNombreObraDescriptor(Integer.parseInt(params[2]), params[0]);
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
        	//Toast.makeText(getApplicationContext(), v, Toast.LENGTH_LONG).show();
        	pDialog.dismiss();
        	System.out.print(" resultado :"+v);
//        	Toast.makeText(getApplicationContext(), "resultado:" +v, Toast.LENGTH_LONG).show();
        	Intent i= new Intent(MainActivity.this, ContenidoObras.class);
			i.putExtra("result",v);	
			startActivity(i);
        	
        }
    }
}


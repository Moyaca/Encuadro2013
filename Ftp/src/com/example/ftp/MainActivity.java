package com.example.ftp;


import java.io.File;
import org.apache.commons.net.ftp.FTPClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView txtNom;
	private Button btnC;
	private Button btnAudio;
	public FTPClient mFTPClient = null;
	ProgressDialog pDialog;
	int ftplogin=0;
	
	String ftplog=String.valueOf(ftplogin);
	String error="ninguno!";
	ImageView img;
	
	private String name = "";
	private static int TAKE_PICTURE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		img = (ImageView)findViewById(R.id.imageView1);
		btnC = (Button) findViewById(R.id.btnComenzar);
		btnAudio = (Button) findViewById(R.id.button1);
		txtNom = (TextView) findViewById(R.id.txtNombre);
		
		name = Environment.getExternalStorageDirectory() + "/test.jpg";
		 
		btnC.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view){
		    	
                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    	
                int code = TAKE_PICTURE;
		    	
                Uri output = Uri.fromFile(new File(name));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                
                startActivityForResult(intent, code);

		    }
		});
		btnAudio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,Multimedia.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		
     	
		 if (requestCode == TAKE_PICTURE) {
     		Toast.makeText(this, "TAKE_PICTURE", Toast.LENGTH_LONG).show();
    		ImageView iv = (ImageView)findViewById(R.id.imageView1);
	        iv.setImageBitmap(BitmapFactory.decodeFile(name));
	        
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

//		    			result = ws.getNombreObraDescriptor(Integer.parseInt(params[2]), params[0]);
		    			result = ws.getNombreObraDescriptor(161, params[0]);
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
        	txtNom.setText("ID: " + v);
        	Toast.makeText(getApplicationContext(), "resultado:" +v, Toast.LENGTH_LONG).show();
        	
        }
    }

}

package com.example.qr;


import java.io.File;

import org.apache.commons.net.ftp.FTPClient;


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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContenidoSalas extends Activity {
	
	TextView tv1;
	TextView tv2;
	ImageView img;
	Button btnSalaObra;
	Button btnRec;
	public FTPClient mFTPClient = null;
	ProgressDialog pDialog;
	int ftplogin=0;
	String ftplog=String.valueOf(ftplogin);
	String error="ninguno!";
	
	private String nom = "";
	private String dir = "";
	private static int TAKE_PICTURE = 1;
	String idsala = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contenido_salas);
		
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.tvDescripcionSala);
		img = (ImageView)findViewById(R.id.imageView1);
		btnSalaObra = (Button) findViewById(R.id.btnObrasSala);
		btnRec = (Button) findViewById(R.id.btnREC);
		Bundle extras = getIntent().getExtras();
		
		
		String[] separated = extras.getString("result").split("=>");
		
		nom = "test.jpg";
		dir = Environment.getExternalStorageDirectory() + "/test.jpg";
		
		idsala = separated[0];
		FtpExecute ftp = new FtpExecute();
  	    String[] sala = {idsala,separated[(separated.length-1)]};	
	    
		tv1.setText("Nombre: " + separated[1]);
		tv2.setText("Descripción: " + separated[2]);
		
//		Toast.makeText(this, "sla: " + idsala + " Nombre archibo: " + nom + " dir: " + dir , Toast.LENGTH_LONG).show();
//		System.out.print("sla: " + idsala + " Nombre archibo: " + nom + " dir: " + dir );
    	
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
	        //llamo al hilo para subir la imajen
	    	
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
    		}else{
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
//		    			idObra = ws.getNombreObraDescriptor(161, params[0]);
		    			result = ws.getDataObraId(Integer.parseInt(idObra));
		    		}
		    	}
//		    	ftp.verContenidoDirectorio();
			} catch (Exception e) {
				result="Error: "+e;
			}
        	
        	return result;
        }
 
        @Override
        protected void onPostExecute(String v) {
        	pDialog.dismiss();
        	System.out.print(" resultado :"+v);
//        	Toast.makeText(getApplicationContext(), "resultado:" +v, Toast.LENGTH_LONG).show();
        	Intent intent = new Intent(ContenidoSalas.this,ContenidoObras.class);
        	intent.putExtra("result", v);
        	startActivity(intent);
        	
        }
    }
	
}

package com.example.qr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenPpalAuto extends Activity {
	
	ProgressDialog pDialog;
	private Button b1;
	private Button b2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_men_ppal_auto);
		
		b1 = (Button) findViewById(R.id.btnObrasSala);
		b2 = (Button) findViewById(R.id.button2);
		
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.qr.SCAN");
				startActivityForResult(intent, 0);
				
			}
	});
		
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.qr.SCAN");
				startActivityForResult(intent, 1);
				
			}
	});
	}
	
	@Override
public void onActivityResult(int requestCode, int resultCode, Intent intent) { 
	 if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            String contenido = intent.getStringExtra("SCAN_RESULT");
	            String formato = intent.getStringExtra("SCAN_RESULT_FORMAT");
	           
	            Toast toast1 =
	                    Toast.makeText(getApplicationContext(),
	                            "C�digo: "+contenido, Toast.LENGTH_SHORT);
	            toast1.show();

	            QRExecuteObra qr = new QRExecuteObra();
	            qr.execute(contenido);
	            
	        } else if (resultCode == RESULT_CANCELED) {
	            // Si se cancelo la captura...
	        	System.out.print("Sacan Canceled");
	        }
	        System.out.print(intent.getAction());
	     
	    }
	  if (requestCode == 1) {
	        if (resultCode == RESULT_OK) {
	            String contenido = intent.getStringExtra("SCAN_RESULT");
	            String formato = intent.getStringExtra("SCAN_RESULT_FORMAT");
	           
	            Toast toast1 =
	                    Toast.makeText(getApplicationContext(),
	                            "C�digo: "+contenido, Toast.LENGTH_SHORT);
	            toast1.show();
	            
	            QRExecuteSala qr = new QRExecuteSala();
	            qr.execute(contenido);
	            
	        } else if (resultCode == RESULT_CANCELED) {
	            // Si se cancelo la captura...
	        	System.out.print("Sacan Canceled");
	        }
	        System.out.print(intent.getAction());
	     
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.men_ppal_auto, menu);
		return true;
	}
	
	class QRExecuteObra extends AsyncTask<String,String,String>{
		 
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(MenPpalAuto.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
        	String result ="";
        	try {
        		Consumirws ws = new Consumirws();
        		result =ws.getDataObraId(Integer.parseInt(params[0]));
		 
			} catch (Exception e) {
				System.out.println("error: " + e);
			}
        	return result;
        }
 
        @Override
        protected void onPostExecute(String v) {
        	System.out.println(" resultado " + v);
        	pDialog.dismiss();
        	Intent i= new Intent(MenPpalAuto.this, ContenidoObras.class);
			i.putExtra("result",v);	
			startActivity(i);

        }
    }
	
	class QRExecuteSala extends AsyncTask<String,String,String>{
		 
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(MenPpalAuto.this);
			pDialog.setMessage("Espere por favor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
        	String result ="";
        	try {
        		Consumirws ws = new Consumirws();
        		result =ws.getDataSalaIdImagen(Integer.parseInt(params[0]));
		 
			} catch (Exception e) {
				System.out.println("error: " + e);
			}
        	return result;
        }
 
        @Override
        protected void onPostExecute(String v) {
        	System.out.println(" resultado " + v);
        	pDialog.dismiss();
        	Intent i= new Intent(MenPpalAuto.this, ContenidoSalas.class);
			i.putExtra("result",v);	
			startActivity(i);

        }
    }

}

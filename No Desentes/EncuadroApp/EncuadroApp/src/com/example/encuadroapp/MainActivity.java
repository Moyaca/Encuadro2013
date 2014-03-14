package com.example.encuadroapp;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button b1;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		b1 = (Button) findViewById(R.id.button1);
		
		 b1.setOnClickListener(new View.OnClickListener() {
				
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent inten = new Intent(MainActivity.this, MenPpal.class); 
					startActivity(inten);
					
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
        		
		    		
		 
			} catch (Exception e) {
				System.out.println("error: " + e);
			}
        	return "";
        }
 
        @Override
        protected void onPostExecute(String v) {
        	pDialog.dismiss();
        }
    }
}

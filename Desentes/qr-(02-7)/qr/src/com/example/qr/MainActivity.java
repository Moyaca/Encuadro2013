package com.example.qr;
	 
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button buttonMan,buttonQR;
	ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		buttonMan = (Button) findViewById(R.id.btnEscO);
		buttonQR = (Button) findViewById(R.id.btnEscS);
		
		buttonMan.setOnClickListener(new View.OnClickListener() {
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
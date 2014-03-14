package com.example.encuadroapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MenPpal extends Activity {
	
	private Button b1;
	private Button b2;
	
	String[] separated;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_men_ppal);
		
		b2 = (Button) findViewById(R.id.button2);
		b1 = (Button) findViewById(R.id.button1);
		
		 b1.setOnClickListener(new View.OnClickListener() {
				
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent inten = new Intent(MenPpal.this, ListaObras.class); 
					startActivity(inten);
					
				}
			});
		
		
		 b2.setOnClickListener(new View.OnClickListener() {
				
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent inten = new Intent(MenPpal.this, ListaSalas.class); 
					startActivity(inten);
					
				}
			});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.men_ppal, menu);
		return true;
	}

}

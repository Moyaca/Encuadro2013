package com.example.wsylaputamadre;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Lista extends Activity {
	ListView lv;
	String[] separated;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		Bundle extras = getIntent().getExtras();
		lv = (ListView) findViewById(R.id.listView1);
		
		String result;
		String[] separated;

		result = extras.getString("result");
		separated = result.split("=>");
		
		
		ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, separated);
	     lv.setAdapter(adapter);
	     
	     lv.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
					consumirws ws = new consumirws();
					String result =ws.getDataObra(lv.getItemAtPosition(posicion).toString()); 				
					Intent i= new Intent(Lista.this, VerObra.class);
					i.putExtra("result",result);	
					startActivity(i);
	            }
	       });	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista, menu);	
		return true;
	}

}

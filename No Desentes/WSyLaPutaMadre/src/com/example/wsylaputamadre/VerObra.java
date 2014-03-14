package com.example.wsylaputamadre;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class VerObra extends Activity {

	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_obra);
		
		tv = (TextView) findViewById(R.id.textView1);
		Bundle extras = getIntent().getExtras();
		tv.setText(extras.getString("result"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ver_obra, menu);
		return true;
	}

}

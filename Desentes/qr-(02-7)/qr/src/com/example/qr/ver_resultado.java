package com.example.qr;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ver_resultado extends Activity {
	 TextView tw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_qr);
		tw = (TextView) findViewById(R.id.mostrarqr);
		Bundle extras = getIntent().getExtras();
		tw.setText(extras.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostrar_qr, menu);
		return true;
	}

}

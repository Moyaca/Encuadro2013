package com.example.wsylaputamadre;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getDataSalaId";
	private String NAMESPACE = "http://10.0.2.109/server_php/";
	private String METHOD_NAME = "getDataSalaId";
	private String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
    
	private Button b;
	private Button b2;
	private EditText et;
	private TextView tv1;
	private TextView tv2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et = (EditText) findViewById(R.id.editText1);
		et.setText("161");
		b = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		tv1 = (TextView) findViewById(R.id.textView2);
		tv2 = (TextView) findViewById(R.id.textView3);
		
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				consumirws ws = new consumirws();
				String result =ws.getDataSala(Integer.parseInt(et.getText().toString())); 
				String[] separated = result.split("=>");
				tv1.setText("Nombre: " + separated[0]);
				tv2.setText("Descripcion: " + separated[1]);
				
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				consumirws ws = new consumirws();
				String result =ws.getObraSala(Integer.parseInt(et.getText().toString())); 				
				Intent i=new Intent(MainActivity.this, Lista.class);
				i.putExtra("result",result);	
				startActivity(i);
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

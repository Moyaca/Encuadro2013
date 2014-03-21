package com.example.formulario_dinamico;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	List<Item> p = new ArrayList();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView)findViewById(R.id.listView1);
		
		List<Respuesta> l = new ArrayList();
		List<Respuesta> l2 = new ArrayList();
		List<Respuesta> l3 = new ArrayList();
		
		l.add(new Respuesta("asd", false));
		l.add(new Respuesta("asd2", false));
		l.add(new Respuesta("asd3", false));
		
		l2.add(new Respuesta("asd", false));
		l2.add(new Respuesta("asd2", false));
		l2.add(new Respuesta("asd3", false));
		l2.add(new Respuesta("asd4", false));
		
		l3.add(new Respuesta("asd", false));
		l3.add(new Respuesta("asd2", false));
		
		Item item = new Pregunta("hola", l, "MultipleOpcion");
		Item item2 = new Pregunta("hola2", l2, "MultipleOpcion");
		Item item3 = new Pregunta("hola3", l3, "MultipleOpcion");
		
		
		
		p.add(item);
		p.add(item2);
		p.add(item3);
		
		lv.setAdapter(new ItemAdapter(getApplicationContext(), p));
		
		Button btn = (Button) findViewById(R.id.button1);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Boolean falta_algo = false;
				for (Item i: p) {
					Pregunta pre = (Pregunta) i;
//					Toast.makeText(getApplicationContext(), "any true:" + pre.anyTrue().toString() + " / nom: " + pre.getPregunta(), Toast.LENGTH_SHORT).show();
					if(!pre.anyTrue())
						falta_algo = true;
				}
				if(falta_algo)
					Toast.makeText(getApplicationContext(), "falsta algo", Toast.LENGTH_SHORT).show();
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

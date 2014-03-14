package com.example.qr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Estadistica extends Activity {
	
	
	private Button b1;
	ProgressDialog pDialog;
	private RadioGroup rdgVisita;
	private RadioGroup rdgNacio;
	private RadioGroup rdgSexo;
	private RadioGroup rdgEdad;
	private String checkVisita="";
	private String checkNacio="";
	private String checkSexo="";
	private String checkEdad="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadistica);
		
		b1 = (Button) findViewById(R.id.button1);
		rdgVisita = (RadioGroup)findViewById(R.id.rdgVisita);
		rdgNacio = (RadioGroup)findViewById(R.id.rdgNacio);
		rdgSexo = (RadioGroup)findViewById(R.id.rdgSexo);
		rdgEdad = (RadioGroup) findViewById (R.id.rdgEd);
		
		  rdgVisita.setOnCheckedChangeListener(new OnCheckedChangeListener(){
	    		 
	 		    @Override
	 		    public void onCheckedChanged(RadioGroup group, int checkedId) {
	 		        // TODO Auto-generated method stub
	 		        if (checkedId == R.id.radio0){
	 	               checkVisita = "Grupal";
	 		        }else if (checkedId == R.id.radio1){
	 		           checkVisita = "Invividual"; 
	 		         }
	 		    }
	 		     
	 		});
		  
		  rdgNacio.setOnCheckedChangeListener(new OnCheckedChangeListener(){
	    		 
	 		    @Override
	 		    public void onCheckedChanged(RadioGroup group, int checkedId) {
	 		        // TODO Auto-generated method stub
	 		        if (checkedId == R.id.radio0){
	 	               checkNacio = "Uruguaya";
	 		        }else if (checkedId == R.id.radio1){
	 		           checkNacio = "Extranjera"; 
	 		           }
	 		    }
	 		     
		  });
		  
		  rdgSexo.setOnCheckedChangeListener(new OnCheckedChangeListener(){
	    		 
	 		    @Override
	 		    public void onCheckedChanged(RadioGroup group, int checkedId) {
	 		        // TODO Auto-generated method stub
	 		        if (checkedId == R.id.radio0){
	 	               checkSexo = "Masculino";
	 		        }else if (checkedId == R.id.radio1){
	 		           checkSexo = "Femenino"; 
	 		           }
	 		    }
	 		     
	 		});
		  
		  rdgEdad.setOnCheckedChangeListener(new OnCheckedChangeListener(){
	    		 
	 		    @Override
	 		    public void onCheckedChanged(RadioGroup group, int checkedId) {
	 		        // TODO Auto-generated method stub
	 		        if (checkedId == R.id.radio0){
	 	               checkEdad = "0 a 12";
	 		        }else if (checkedId == R.id.radio1){
	 		           checkEdad = "13 a 15"; 
	 		        }else if (checkedId == R.id.radio2){
	 		           checkEdad = "16 a 18";
	 		       }else if (checkedId == R.id.radio3){
	 		           checkEdad = "+ de 18";
	 		         }
	 		    }
	 		     
	 		});
		  
		  b1.setOnClickListener(new View.OnClickListener() {
				
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub 
					EnviarDatosEstadistica envDatos = new EnviarDatosEstadistica();
					System.out.println(checkNacio + " : " + checkSexo  +" : "+checkVisita +" : " +checkEdad +" : ");
					envDatos.execute(checkNacio,checkSexo,checkVisita,checkEdad);
					
					 AlertDialog.Builder builder = new AlertDialog.Builder(Estadistica.this);
		             //builder.setTitle("Pista");
		             builder.setMessage("Datos Enviados Correctamente");
		             builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		             {
		                    public void onClick(DialogInterface dialog, int which) {
		                    //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
		                    	Intent intent = new Intent(Estadistica.this,MainActivity.class);
		    					startActivity(intent);
		    					Estadistica.this.finish();
		                    }
		               });
		             builder.create();
		             builder.show();
				}
			});
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Menú modificado
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.estadistica, menu);
	    return true;

	}
	
	class EnviarDatosEstadistica extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(Estadistica.this);
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
        		result =ws.Altavisita(params[0],params[1],params[2],params[3] );
		 
			} catch (Exception e) {
				System.out.println("error: " + e);
			}
        	return result;
        }
 
        @Override
        protected void onPostExecute(String v) {
        	System.out.println(" resultado " + v);        	
        	 pDialog.dismiss();
        }
    }	
}
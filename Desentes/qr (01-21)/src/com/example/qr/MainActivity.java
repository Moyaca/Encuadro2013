package com.example.qr;
	 
import java.io.File;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button b1;
	private Button b2;
	private Button buttonQR;
	ProgressDialog pDialog;
	
	private String name = "";
	private static int TAKE_PICTURE = 1;
//	public String sala ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		b1 = (Button) findViewById(R.id.btnEscO);
		b2 = (Button) findViewById(R.id.btnObrasSala);
		buttonQR = (Button) findViewById(R.id.btnEscS);
		name = Environment.getExternalStorageDirectory() + "/test.jpg";
		
		b1.setOnClickListener(new View.OnClickListener() {
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


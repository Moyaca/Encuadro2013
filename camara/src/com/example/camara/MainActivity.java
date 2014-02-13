package com.example.camara;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int IMAGE_CAPTURE = 0;
    private Button startBtn;
    private Uri imageUri;
    private ImageView imageView;
    
	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 2;
	private String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        name = Environment.getExternalStorageDirectory() + "/test1.jpg";
       
        name = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test2.jpg";
        Button btnAction = (Button)findViewById(R.id.button1);
        
        btnAction.setOnClickListener(new OnClickListener() { 
        	@Override
            public void onClick(View v) {
        		RadioButton rbtnFull = (RadioButton)findViewById(R.id.r2);
                RadioButton rbtnGallery = (RadioButton)findViewById(R.id.r3);
                
//                startCamera();
                
                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
                
                Uri uri = Uri.fromFile(new File(name));
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                int code = TAKE_PICTURE;	
                    	
                if (rbtnGallery.isChecked()){
                    	intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    	    code = SELECT_PICTURE;
                }
                
                startActivityForResult(intent, code);
            }
        });
    }
    
    //no funciona bien
    public void startCamera() {
        Log.d("ANDRO_CAMERA", "Starting camera on the phone...");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NombreImagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageUri = getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(intent, IMAGE_CAPTURE);
    }
    
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == RESULT_OK){
    		
	    	if (requestCode == TAKE_PICTURE) {
	    		
	    		if (data != null) {
	    			if (data.hasExtra("data")) {
	    				try {
	    					
	    					Bitmap bm = (Bitmap) data.getParcelableExtra("data");
	    					Bitmap reBm = getResizedBitmap(bm,150,150);
		    				ImageView iv = (ImageView)findViewById(R.id.imgview);
		    				iv.setImageBitmap(reBm);
		    				
		    	    	    	FileOutputStream fos;
								fos = new FileOutputStream(name);
								reBm.compress(Bitmap.CompressFormat.JPEG, 15, fos);
								fos.close();
	    	            
	    	            } catch (Exception e) {
							System.out.print(e.toString());
							Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
						}
		              }                       
	    		} 
	    		
	    	else {
	    		ImageView iv = (ImageView)findViewById(R.id.imgview);
		        iv.setImageBitmap(BitmapFactory.decodeFile(name));
		        new MediaScannerConnectionClient() {
		        	private MediaScannerConnection msc = null; {
		        		msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
		        		}
		        	public void onMediaScannerConnected() {
		        		msc.scanFile(name, null);
		        		}
		                                public void onScanCompleted(String path, Uri uri) {
		                                        msc.disconnect();
		                                }
		                        };                                
		                }
	    		} 
	    	
	    	else if (requestCode == SELECT_PICTURE){
	    		Uri selectedImage = data.getData();
	    		InputStream is;
	    		try {
	    			is = getContentResolver().openInputStream(selectedImage);
		            BufferedInputStream bis = new BufferedInputStream(is);
		            Bitmap bitmap = BitmapFactory.decodeStream(bis);
		            ImageView iv = (ImageView)findViewById(R.id.imgview);
		            iv.setImageBitmap(bitmap);                                                
		            } catch (FileNotFoundException e) {}
		        }
    	}
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
    	 
    	int width = bm.getWidth();
    	 
    	int height = bm.getHeight();
    	 
    	float scaleWidth = ((float) newWidth) / width;
    	 
    	float scaleHeight = ((float) newHeight) / height;
    	 
    	// CREATE A MATRIX FOR THE MANIPULATION
    	 
    	Matrix matrix = new Matrix();
    	 
    	// RESIZE THE BIT MAP
    	 
    	matrix.postScale(scaleWidth, scaleHeight);
    	 
    	// RECREATE THE NEW BITMAP
    	 
    	Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    	 
    	return resizedBitmap;
    	 
    	}
    
    
}

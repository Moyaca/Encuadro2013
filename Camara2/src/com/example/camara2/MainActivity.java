package com.example.camara2;


import java.util.List;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.camara2.R;

public class MainActivity extends Activity {
	 	public final static String DEBUG_TAG = "MainActivity";
	  private Camera camera;
	  private int cameraId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		 do we have a camera?
	    if (!getPackageManager()
	        .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
	      Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
	          .show();
	    } 
//		else {
	      cameraId = findFrontFacingCamera();
	      if (cameraId < 0) {
	        Toast.makeText(this, "No front facing camera found.",
	            Toast.LENGTH_LONG).show();
	      } else {
	        camera = Camera.open(cameraId);
	        Camera.Parameters params = camera.getParameters();
	         
	        // Check what resolutions are supported by your camera
	        List<Size> sizes = params.getSupportedPictureSizes();
	         
	        // Iterate through all available resolutions and choose one.
	        // The chosen resolution will be stored in mSize.
	        Size mSize = sizes.get(0);
	        Log.i(DEBUG_TAG, "Available resolution: "+mSize.width+" "+mSize.height);
	        for (Size size : sizes) {
	            Log.i(DEBUG_TAG, "Available resolution: "+size.width+" "+size.height);
	            mSize = size;
	            break;
	        }
	        params.setPictureSize(mSize.width, mSize.height);
	        camera.setParameters(params);
	      }
//	    }
	  }

	  public void onClick(View view) {
	    camera.takePicture(null, null, new PhotoHandler(getApplicationContext(),"nombre_pintura"));
	  }

	  private int findFrontFacingCamera() {
	    int cameraId = -1;
	    // Search for the front facing camera
	    int numberOfCameras = Camera.getNumberOfCameras();
	    for (int i = 0; i < numberOfCameras; i++) {
	      CameraInfo info = new CameraInfo();
	      Camera.getCameraInfo(i, info);
	      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
	        Log.d(DEBUG_TAG, "Camera found " + cameraId);
	        cameraId = i;
	        break;
	      }
	    }
	    return cameraId;
	  }

	  @Override
	  protected void onPause() {
	    if (camera != null) {
	      camera.release();
	      camera = null;
	    }
	    super.onPause();
	  }

	} 
package com.example.asd;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.CursorJoiner.Result;
import android.net.wifi.WifiManager;

public class Hadware {

	private Context context;
	public Hadware(Context context) {
		this.context = context;
	}
	public String getMacAddress() {
	    WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	    String macAddress = wimanager.getConnectionInfo().getMacAddress();
	    if (macAddress == null) {
	        macAddress = "Device don't have mac address or wi-fi is disabled";
	    }
	    return macAddress;
	}
	
	public String getVesion(){
		String version ="error";
		PackageInfo pInfo;
		try {
			pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version = pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}
}

package com.example.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyFTP {
	
	
	private boolean isObraLogin = false;
	private boolean isSalaLogin = false;
	private boolean isZonaLogin = false;
	
	
	public FTPClient salaClient = null;
	public FTPClient obraClient = null;
	public FTPClient zonaClient = null;
	
	
		String host = "10.0.2.109";
		
		//salas
		String sUser = "salas";
		String sPassword = "12345678";
		//obras
		String oUser = "obras";
		String oPassword = "12345678";
		//zona
		String zUser = "zonas";
		String zPassword = "12345678";
		
		Context context;
		
		MyFTP(Context c){
			context = c;
		}
		
		public boolean LoginObras(){
			boolean status = false;
			try{
        		obraClient = new FTPClient();
        		obraClient.connect(host, 21);
	             // now check the reply code, if positive mean connection success
	             if (FTPReply.isPositiveCompletion(obraClient.getReplyCode())) {
	                 // login using username & password
	                 status = obraClient.login(oUser, oPassword);
	                 isObraLogin = status;
	                 
	                 obraClient.setFileType(FTP.BINARY_FILE_TYPE);
	                 obraClient.enterLocalPassiveMode();
	                 
	             }
        	} catch(Exception e) {
	        	 String error=String.valueOf(e);
	        	 System.out.println(e);
//	        	 Toast.makeText(context, "Login error: " + error, Toast.LENGTH_LONG).show(); 
	        	 return false;
        	}
			return status;
		}
		
		public boolean LoginSalas(){
			boolean status = false;
			try{
        		salaClient = new FTPClient();
        		salaClient.connect(host, 21);

	             // now check the reply code, if positive mean connection success
	             if (FTPReply.isPositiveCompletion(salaClient.getReplyCode())) {
	                 // login using username & password
	                 status = salaClient.login(sUser, sPassword);
	                 isSalaLogin = status;
	                 
	                 salaClient.setFileType(FTP.BINARY_FILE_TYPE);
	                 salaClient.enterLocalPassiveMode();
	                 
	             }
        	} catch(Exception e) {
	        	 String error=String.valueOf(e);
	        	 System.out.println(e);
//	        	 Toast.makeText(context, "error: " + error, Toast.LENGTH_LONG).show(); 
	        	 return false;
        	}
			return status;
		}
		
		public boolean LoginZona(){
			boolean status = false;
			try{
        		zonaClient = new FTPClient();
        		zonaClient.connect(host, 21);

	             // now check the reply code, if positive mean connection success
	             if (FTPReply.isPositiveCompletion(zonaClient.getReplyCode())) {
	                 // login using username & password
	                 status = zonaClient.login(zUser, zPassword);
	                 isZonaLogin = status;
	                 
	                 zonaClient.setFileType(FTP.BINARY_FILE_TYPE);
	                 zonaClient.enterLocalPassiveMode();
	                 
	             }
        	} catch(Exception e) {
	        	 String error=String.valueOf(e);
	        	 System.out.println(e);
	        	 //Toast.makeText(context, "error: " + error, Toast.LENGTH_LONG).show(); 
	        	 return false;
        	}
			return status;
		}
		
		public boolean LoginAll(){
			boolean statusObra=isObraLogin,statusSala=isSalaLogin,statusZona=isZonaLogin,result=false;
			if(!statusObra){
				statusObra = LoginObras();
			}
			if(!statusSala){
				statusSala = LoginSalas();
			}
			if(!statusZona){
				statusZona = LoginZona();
			}
			if(statusObra & statusSala & statusZona){
				result=true;
			}
			return true;
		}
		
		
		public void LogoutObras(){
			try {
				if(isObraLogin){
					obraClient.disconnect();
					isObraLogin = false;
				}
			} catch (IOException ioE) {
				String error=String.valueOf(ioE);
				System.out.println(ioE);
//				Toast.makeText(context, "error: " + error, Toast.LENGTH_LONG).show(); 
			}
			
		}
		
		public void LogoutSalas(){
			try {
				if(isSalaLogin){
					salaClient.disconnect();
					isSalaLogin = false;
				}
			} catch (IOException ioE) {
				String error=String.valueOf(ioE);
				System.out.println(ioE);
//	        	Toast.makeText(context, "error: " + error, Toast.LENGTH_LONG).show(); 
			}
			
		}
		
		public void LogoutZonas(){
			try {
				if(isZonaLogin){
					zonaClient.disconnect();
					isZonaLogin = false;
				}
			} catch (IOException ioE) {
				String error=String.valueOf(ioE);
				System.out.println(ioE);
				//Toast.makeText(context, "error: " + error, Toast.LENGTH_LONG).show(); 
			}
			
		}
		
		public void LogoutAll(){
			LogoutObras();
			LogoutSalas();
			LogoutZonas();
		}
		
		
		public boolean IfLoginSalas(){
			return isSalaLogin;
		}
		
		public boolean IfLoginZonas(){
			return isZonaLogin;
		}
		
		public boolean IfLoginObras(){
			return isObraLogin;
		}
		
		public boolean ifAllLogin(){
			if(isObraLogin & isSalaLogin & isZonaLogin) {
				return true;
			}
			else{
				return false;
			}
		}
		
		
		public Bitmap GetImgObra(String id, String name, boolean descargar, int loop){
			Bitmap img= null;
			File file = null;
			if(loop<=10){
				if(IfLoginObras()){
					try {
						file = new File(context.getCacheDir(), name); //or any other format supported
						if(file.exists()){
							System.out.println("Existe");
							FileInputStream streamIn = new FileInputStream(file);
				       		img = BitmapFactory.decodeStream(streamIn); //This gets the image
				       		streamIn.close();
				       		file.delete();
						}else{
							System.out.println("no existe");
							file.delete();
							if(descargar){
								if(descargarImg(id, name, obraClient)){
									System.out.println("descargo");
									// re false para que no buelba asecrargaralgo inesesario;
									descargar = false;
									img = GetImgObra(id, name, descargar, loop+1);
								}else{
									System.out.println("else de la descarga");
									descargar = true;
									img = GetImgObra(id, name, descargar,loop+1);
								}
							}else{
								img = GetImgObra(id, name, descargar,loop+1);
							}
						}
					} catch (Exception e) {
						String error=String.valueOf(e);
						System.out.println(e);
			        	//Toast.makeText(context, "GetImg error: " + error, Toast.LENGTH_LONG).show(); 
						return img;
					}
				}
			}
			return img;
		}
		
		
		public Bitmap GetImgSala(String id, String name, boolean descargar,int loop){
			Bitmap img= null;
			File file = null;
			if(loop<=10){
				if(IfLoginSalas()){
					try {
						file = new File(context.getCacheDir(), name); //or any other format supported
						if(file.exists()){
							System.out.println("Existe");
							FileInputStream streamIn = new FileInputStream(file);
				       		img = BitmapFactory.decodeStream(streamIn); //This gets the image
				       		streamIn.close();
				       		file.delete();
						}else{
							System.out.println("no existe");
							file.delete();
							if(descargar){
								if(descargarImg(id, name, salaClient)){
									System.out.println("descargo");
									// re false para que no buelba asecrargaralgo inesesario;
									descargar = false;
									img = GetImgSala(id, name, descargar, loop+1);
								}else{
									System.out.println("else de la descarga");
									descargar = true;
									img = GetImgSala(id, name, descargar,loop+1);
								}
							}else{
								img = GetImgSala(id, name, descargar,loop+1);
							}
						}
					} catch (Exception e) {
						String error=String.valueOf(e);
						System.out.println(e);
			        	//Toast.makeText(context, "GetImg error: " + error, Toast.LENGTH_LONG).show(); 
						return img;
					}
				}
			}
			return img;
		}
		public Bitmap GetImgZona(String id, String name, boolean descargar, int loop){
			Bitmap img= null;
			File file = null;
			if(loop<=10){
				if(IfLoginZonas()){
					try {
						file = new File(context.getCacheDir(), name); //or any other format supported
						if(file.exists()){
							System.out.println("Existe");
							FileInputStream streamIn = new FileInputStream(file);
				       		img = BitmapFactory.decodeStream(streamIn); //This gets the image
				       		streamIn.close();
				       		file.delete();
						}else{
							System.out.println("no existe");
							file.delete();
							if(descargar){
								if(descargarImg(id, name, zonaClient)){
									System.out.println("descargo");
									// re false para que no buelba asecrargaralgo inesesario;
									descargar = false;
									img = GetImgZona(id, name, descargar, loop+1);
								}else{
									System.out.println("else de la descarga");
									descargar = true;
									img = GetImgZona(id, name, descargar,loop+1);
								}
							}else{
								img = GetImgZona(id, name, descargar,loop+1);
							}
						}
					} catch (Exception e) {
						String error=String.valueOf(e);
						System.out.println(e);
			        	//Toast.makeText(context, "GetImg error: " + error, Toast.LENGTH_LONG).show(); 
						return img;
					}
				}
			}
			return img;
		}
		
		
		public boolean descargarImg(String id, String name, FTPClient cliente){
			boolean result = false;
				try {
					FileOutputStream fos = null;
		            fos = new FileOutputStream(context.getCacheDir() + "/" + name);
		            cliente.retrieveFile("/" + id + "/imagen/" + name, fos);
//			    	File file = new File(context.getCacheDir().getPath());
//			    	File[] s = file.listFiles();
//			    	for(int i=0;i<=s.length;i++){
//				    	System.out.println(s[i]);	
//			    	}
			    	fos.close();
			    	result = true;
				} catch (Exception e) {
					String error=String.valueOf(e);
					System.out.println(e);
					//Toast.makeText(context, "Descargar error: " + error, Toast.LENGTH_LONG).show(); 
					return false;
				}
            return result;
   		}
		
		public boolean subirImgSala(String id, String name){
			boolean result = false;
			File file = null;
			if(IfLoginSalas()){
					try {
					file = new File(context.getCacheDir(), name);
					if(file.exists()){
						file.delete();
						System.out.println("Existe");
						result =subirImg(id, name, salaClient);
					}else{
						file.delete();
						System.out.println("no existe");
					}
				} catch (Exception e) {
					String error=String.valueOf(e);
					System.out.println(error);
					result = false;
				}
			}
			return result;
		}
		public boolean subirImgObra(String id, String name){
			boolean result = false;
			File file = null;
			if(IfLoginObras()){
					try {
					file = new File(context.getCacheDir(), name);
					if(file.exists()){
						file.delete();
						System.out.println("Existe");
						result =subirImg(id, name, obraClient);
					}else{
						file.delete();
						System.out.println("no existe");
					}
				} catch (Exception e) {
					String error=String.valueOf(e);
					System.out.println(error);
					result = false;
				}
			}
			return result;
		}
		public boolean subirImgZona(String id, String name){
			boolean result = false;
			File file = null;
			if(IfLoginZonas()){
					try {
					file = new File(context.getCacheDir(), name);
					if(file.exists()){
						file.delete();
						System.out.println("Existe");
						result =subirImg(id, name, zonaClient);
					}else{
						file.delete();
						System.out.println("no existe");
					}
				} catch (Exception e) {
					String error=String.valueOf(e);
					System.out.println(error);
					result = false;
				}
			}
			return result;
		}
		
		public boolean subirImg(String id, String name, FTPClient cliente){
			boolean result = false;
			try{
				FileInputStream srcFileStream = context.openFileInput(context.getCacheDir() + "/" + name);  
				result = cliente.storeFile("/" + id + "/imagen/" + name, srcFileStream);  
				srcFileStream.close();
			}catch(Exception e){
				result = false;
				System.out.print(e.toString());
			}
			return result;
		}
		
		
}

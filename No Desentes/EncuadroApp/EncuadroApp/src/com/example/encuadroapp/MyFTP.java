package com.example.encuadroapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
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
		
		public MyFTP(Context c){
			context = c;
			obraClient = new FTPClient();
			salaClient = new FTPClient();
			zonaClient = new FTPClient();
		}
		
		public boolean LoginObras(){
			boolean status = false;
			try{
        		
        		obraClient.connect(host, 21);
	             if (FTPReply.isPositiveCompletion(obraClient.getReplyCode())) {
	                 status = obraClient.login(oUser, oPassword);
	                 isObraLogin = status;
	                 
	                 obraClient.setFileType(FTP.BINARY_FILE_TYPE);
	                 obraClient.enterLocalPassiveMode();
	                 
	             }
        	} catch(Exception e) {
	        	 System.out.println(e);
	        	 return false;
        	}
			return status;
		}
		
		public boolean LoginSalas(){
			boolean status = false;
			try{
        		
        		salaClient.connect(host, 21);

	             if (FTPReply.isPositiveCompletion(salaClient.getReplyCode())) {
	                 status = salaClient.login(sUser, sPassword);
	                 isSalaLogin = status;
	                 
	                 salaClient.setFileType(FTP.BINARY_FILE_TYPE);
	                 salaClient.enterLocalPassiveMode();
	                 
	             }
        	} catch(Exception e) {
	        	 System.out.println(e);
	        	 return false;
        	}
			return status;
		}
		
		public boolean LoginZona(){
			boolean status = false;
			try{
        		
        		zonaClient.connect(host, 21);
	             if (FTPReply.isPositiveCompletion(zonaClient.getReplyCode())) {
	                 status = zonaClient.login(zUser, zPassword);
	                 isZonaLogin = status;
	                 
	                 zonaClient.setFileType(FTP.BINARY_FILE_TYPE);
	                 zonaClient.enterLocalPassiveMode();
	                 
	             }
        	} catch(Exception e) {
	        	 System.out.println(e);
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
			return result;
		}
		
		
		public void LogoutObras(){
			try {
				if(isObraLogin){
					obraClient.disconnect();
					isObraLogin = false;
				}
			} catch (IOException ioE) {
				System.out.println(ioE);
			}
			
		}
		
		public void LogoutSalas(){
			try {
				if(isSalaLogin){
					salaClient.disconnect();
					isSalaLogin = false;
				}
			} catch (IOException ioE) {
				System.out.println(ioE);
			}
			
		}
		
		public void LogoutZonas(){
			try {
				if(isZonaLogin){
					zonaClient.disconnect();
					isZonaLogin = false;
				}
			} catch (IOException ioE) {
				System.out.println(ioE);
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
		
		
		public Bitmap GetImgObra(String id, String name,Boolean descargar,int loop){
			Bitmap img= null;
			File file = null;
			try {
				if(loop<=10){
					file = new File(context.getCacheDir(), name);
					if(file.exists()){
						System.out.println("Existe");
						FileInputStream streamIn = new FileInputStream(file);
			       		img = BitmapFactory.decodeStream(streamIn); 
			       		streamIn.close();
					}else{
						System.out.println("no existe");
						if(descargarImg(id, name, obraClient) && IfLoginObras() && descargar){
							if(file.exists()){
								System.out.println("Existe");
								FileInputStream streamIn = new FileInputStream(file);
					       		img = BitmapFactory.decodeStream(streamIn);
					       		streamIn.close();
							}else{
								img = GetImgObra(id, name, true, loop+1);
							}
						}else{
							img = null;
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
				return img;
			}
			return img;
		}

		public Bitmap GetImgSala(String id, String name,Boolean descargar,int loop){
			Bitmap img= null;
			File file = null;
			try {
				if(loop<=10){
					file = new File(context.getCacheDir(), name); 
					if(file.exists()){
						System.out.println("Existe");
						FileInputStream streamIn = new FileInputStream(file);
			       		img = BitmapFactory.decodeStream(streamIn); 
			       		streamIn.close();
					}else{
						System.out.println("no existe");
						if(descargarImg(id, name, salaClient) && IfLoginSalas()){
							if(file.exists()){
								System.out.println("Existe");
								FileInputStream streamIn = new FileInputStream(file);
					       		img = BitmapFactory.decodeStream(streamIn); 
					       		streamIn.close();
							}else{
								img = GetImgObra(id, name, true, loop+1);
							}
						}else{
							img = null;
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
				return img;
			}
			return img;
		}
		public Bitmap GetImgZona(String id, String name,Boolean descargar,int loop){
			Bitmap img= null;
			File file = null;
			try {
				if(loop<=10){
					file = new File(context.getCacheDir(), name);
					if(file.exists()){
						System.out.println("Existe");
						FileInputStream streamIn = new FileInputStream(file);
			       		img = BitmapFactory.decodeStream(streamIn);
			       		streamIn.close();
					}else{
						if(descargarImg(id, name, zonaClient) && IfLoginZonas()){
							if(file.exists()){
								System.out.println("Existe");
								FileInputStream streamIn = new FileInputStream(file);
					       		img = BitmapFactory.decodeStream(streamIn);
					       		streamIn.close();
							}else{
								img = GetImgObra(id, name, true, loop+1);
								System.out.println("no existe");
							}
						}else{
							img = null;
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
				return img;
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
					System.out.println("error descarga :" + e);
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
						System.out.println("Existe");
						result =subirImg(id, name, salaClient);
						file.delete();
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
					descargarImg(id, name, obraClient); // eliminar si anda
					if(file.exists()){
						System.out.println("Existe");
						result =subirImg(id, name, obraClient);
						file.delete();
					}else{
						file.delete();
						System.out.println("no existe");
					}
				} catch (Exception e) {
					String error=String.valueOf(e);
					System.out.println("error SO:" + error);
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
						System.out.println("Existe");
						result =subirImg(id, name, zonaClient);
						file.delete();
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
			File file = null;
			try{				
				file = new File(context.getCacheDir(), name);
//				FileInputStream srcFileStream = context.openFileInput(context.getCacheDir()+ "/" + name);
				FileInputStream srcFileStream = new FileInputStream(file);
				System.out.print("cojio la imagen");
				result = cliente.storeFile("/" + id + "/" +name, srcFileStream);  
				System.out.print("subio la imagen");
				srcFileStream.close();
			}catch(Exception e){
				result = false;
				System.out.print(" Error: "+e.toString());
			}
			return result;
		}
		
		public void PrintFilesListOfFtp()  
	      {  
	        try {  
	          FTPFile[] ftpFiles = obraClient.listFiles("/");  
	          int length = ftpFiles.length;  
	          for (int i = 0; i < length; i++) {  
	            String name = ftpFiles[i].getName();  
	            boolean isFile = ftpFiles[i].isFile();  
	            if (isFile) {  
	            	System.out.print("File : " + name);  
	            }  
	            else {  
	            	System.out.print( "Directory : " + name);  
	            }  
	          }  
	        } catch(Exception e) {  
	          e.printStackTrace();  
	        }  
	      }   
		
}

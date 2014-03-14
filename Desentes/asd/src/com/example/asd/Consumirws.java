package com.example.asd;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Consumirws {
	
	 public String getpuntajes(){
			String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getpuntajes";
		    String NAMESPACE = "http://10.0.2.109/server_php/";
		    String METHOD_NAME = "getpuntajes";
		    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
   	    
   	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

   		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(	SoapEnvelope.VER11);

   		envelope.setOutputSoapObject(request);
   		envelope.dotNet = true;

   		try {
   			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

   			// this is the actual part that will call the webservice
   			androidHttpTransport.call(SOAP_ACTION, envelope);

   			// Get the SoapResult from the envelope body.
   			SoapObject result = (SoapObject) envelope.bodyIn;

   			if (result != null) {
   				// Get the first property and change the label text
   				return result.getProperty(0).toString();	
   			} else {
   				return "error:=>no se encontrosala=>";
   			}
   		} catch (Exception e) {
   			return "error =>" + e.toString();
   		}
	   		
   	 }
	 public String getHora(){
			String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/gethora";
		    String NAMESPACE = "http://10.0.2.109/server_php/";
		    String METHOD_NAME = "gethora";
		    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		//request.addProperty("id_sala",idsala);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;

		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL);

			// this is the actual part that will call the webservice
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Get the SoapResult from the envelope body.
			SoapObject result = (SoapObject) envelope.bodyIn;

			if (result != null) {
				// Get the first property and change the label text
				return result.getProperty(0).toString();	
			} else {
				return "error:=>no se encontrosala=>";
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
	   		
	 }
	 public String FinJuego(Integer id_visitante,Integer id_juego, Integer puntaje, String nick_name){
			String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/finJuego";
		    String NAMESPACE = "http://10.0.2.109/server_php/";
		    String METHOD_NAME = "finJuego";
		    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		request.addProperty("id_visitante",id_visitante);
		request.addProperty("id_juego",id_juego);
		request.addProperty("puntaje",puntaje);
		request.addProperty("nick_name", nick_name);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;

		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL);

			// this is the actual part that will call the webservice
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Get the SoapResult from the envelope body.
			SoapObject result = (SoapObject) envelope.bodyIn;

			if (result != null) {
				// Get the first property and change the label text
				return result.getProperty(0).toString();	
			} else {
				return "error:=>no se encontrosala=>";
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
	   		
	 }
	 
}
package com.example.qr;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Consumirws {
	public String getObraSala(int idsala){
		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getObraSala";
	    String NAMESPACE = "http://10.0.2.109/server_php/";
	    String METHOD_NAME = "getObraSala";
	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		request.addProperty("id_sala",idsala);

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
			} 
			else {
				return "error:=>no se encontrosala=>";
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
	}

	public String getContenidoObra(String nombre){
		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getContenidoObra";
	    String NAMESPACE = "http://10.0.2.109/server_php/";
	    String METHOD_NAME = "getContenidoObra";
	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		request.addProperty("nombre",nombre);

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
			} 
			else {
				return "error:=>no se encontrosala=>";
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
		
	}
	
	public String getContenidoSalaNombre(String nombre){
		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getContenidoSalaNombre";
	    String NAMESPACE = "http://10.0.2.109/server_php/";
	    String METHOD_NAME = "getContenidoSalaNombre";
	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		request.addProperty("nombre",nombre);

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
			} 
			else {
				return "error:=>no se encontrosala=>";
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
		
	}


	public String getNombreObraDescriptor(int idSala, String nombre_archivo){
		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getNombreObra";
	    String NAMESPACE = "http://10.0.2.109/server_php/";
	    String METHOD_NAME = "getNombreObra";
	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		request.addProperty("id_sala",idSala);
		request.addProperty("nombre_archivo",nombre_archivo);

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
			} 
			else {
				return "error:=>no se encontrosala=>";
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
	}
	
	public String getDataSalaNombreImagen(String nombre){
		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getDataSalaNombreImagen";
	    String NAMESPACE = "http://10.0.2.109/server_php/";
	    String METHOD_NAME = "getDataSalaNombreImagen";
	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		request.addProperty("nombre",nombre);

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
			} 
			else {
				return "error:=>no se encontrosala=>";
				
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
	}

    public String getDataSalaIdImagen(int idsala){
		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getDataSalaIdImagen";
	    String NAMESPACE = "http://10.0.2.109/server_php/";
	    String METHOD_NAME = "getDataSalaIdImagen";
	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	
		// Use this to add parameters
	
		request.addProperty("id_sala",idsala);
	
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
			} 
			else {
				return "error:=>no se encontrosala=>";
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
    }
    
	public String getDataSala(int idsala){
		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getDataSalaId";
	    String NAMESPACE = "http://10.0.2.109/server_php/";
	    String METHOD_NAME = "getDataSalaId";
	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		request.addProperty("id_sala",idsala);

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
			} 
			else {
				return "error:=>no se encontrosala=>";
				
			}
		} catch (Exception e) {
			return "error =>" + e.toString();
		}
	}
	
	public String getDataObraId(int idobra){
		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getDataObraId";
	    String NAMESPACE = "http://10.0.2.109/server_php/";
	    String METHOD_NAME = "getDataObraId";
	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	    
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// Use this to add parameters

		request.addProperty("id_obra",idobra);

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
			} 
			else {
				return "error:=>no se encontrosala=>";
			}
		} catch (Exception e) {
			return "error e =>" + e.toString();
		}
	}
	
	 public String getObras(){
   		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getObras";
   	    String NAMESPACE = "http://10.0.2.109/server_php/";
   	    String METHOD_NAME = "getObras";
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
	 
	 public String getObrasl(String obra){
   		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getObrasl";
   	    String NAMESPACE = "http://10.0.2.109/server_php/";
   	    String METHOD_NAME = "getObrasl";
   	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
   	    
   	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

   		// Use this to add parameters

   		request.addProperty("nombre_obra",obra);

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
   			} 
   			else {
   				return "error:=>no se encontrosala=>";
   			}
   		} catch (Exception e) {
   			return "error =>" + e.toString();
   		}
	 }
	 
	 public String getSalasl(String sala){
   		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getSalasl";
   	    String NAMESPACE = "http://10.0.2.109/server_php/";
   	    String METHOD_NAME = "getSalasl";
        
   	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
   	    
   	    
   	    
   	    
   	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

   		// Use this to add parameters

   		request.addProperty("nombre",sala);

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
	 
	 public String getNombreSalas(){	
   		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getNombreSalas";
   	    String NAMESPACE = "http://10.0.2.109/server_php/";
   	    String METHOD_NAME = "getNombreSalas";
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
       
	 public String getDataObra(String obra){	
   		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getDataObra";
   	    String NAMESPACE = "http://10.0.2.109/server_php/";
   	    String METHOD_NAME = "getDataObra";
   	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
   	    
   	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

   		// Use this to add parameters

   		request.addProperty("nombre_obra",obra);

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
   	
	 public String getNombreObraLike(String obra){
  		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getNombreObraLike";
  	    String NAMESPACE = "http://10.0.2.109/server_php/";
  	    String METHOD_NAME = "getNombreObraLike";
  	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
  	    
  	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

  		// Use this to add parameters

  		request.addProperty("nombre_obra",obra);

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
  				return "error:=>no se encontro obra=>";
  			}
  		} catch (Exception e) {
  			return "error =>" + e.toString();
  		} 		
  	}
	 public String getContenidoSalaId(int id_Sala){
	   		
	   		String SOAP_ACTION = "http://10.0.2.109/server_php/server_php.php/getContenidoSala";
	   	    String NAMESPACE = "http://10.0.2.109/server_php/";
	   	    String METHOD_NAME = "getContenidoSala";
	   	    String URL = "http://10.0.2.109/server_php/server_php.php?wsdl";
	   	    
	   	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

	   		// Use this to add parameters

	   		request.addProperty("id_Sala",id_Sala);

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
	   				
	   				//nombreapp.setText("Nombre "+separated[0] +" id "+contents+ "desc. " + separated[1]);

	   				
	   			} else {
	   				return "error:=>no se encontrosala=>";
	   				
	   			}
	   		} catch (Exception e) {
	   			return "error =>" + e.toString();
	   		}
	   		
	   	}
}
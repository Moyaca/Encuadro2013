package com.example.formulario_dinamico;

import java.util.List;

public class Pregunta extends Item {

	private String pregunta;
	private List<Respuesta> res;
	private String tipo;

	
	Pregunta(String pregunta, List<Respuesta> res, String tipo){
		this.setPregunta(pregunta);
		this.setRes(res);
		this.setTipo(tipo);
	}

	public Object getItem(int position) {
        return this.res.get(position);
    }

	public int getResCount() {
		return res.size();
		
	}
	public String getPregunta() {
		return pregunta;
	}


	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}


	public List<Respuesta> getRes() {
		return res;
	}


	public void setRes(List<Respuesta> res) {
		this.res = res;
	}
    
    //refresca rodas las respuestas y las coloca en false
    public void refreshRes() {
    	for (Respuesta r : res) {
			r.setSelected(false);
		}
    }
    
    //busca en las respuestas si existe alguna en true
    public Boolean anyTrue(){
    	for (Respuesta r : res) {
			if(r.getSelected())
			return true;
		}
    	return false;
    }

	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}

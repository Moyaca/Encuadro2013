package com.example.formulario_dinamico;

import android.widget.RadioButton;


public class Respuesta {
	private String text;
	private Boolean selected;
	
	public Respuesta( String text, Boolean selected) {
		this.selected = selected;
		this.text = text;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

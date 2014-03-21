package com.example.formulario_dinamico;

public class ItemList extends Item {
	 
    private int puntaje;
    private String nombre;
 
    public ItemList(int puntaje, String nombre) {
        super();
        this.setPuntaje(puntaje);
        this.setNombre(nombre);
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
}
package com.example.asd;

public class ItemList {
	 
    private int posicion;
    private String tiempo;
    private String porcentaje;
    private String nombre;
 
    public ItemList(int posicion, String tiempo, String porcentaje, String nombre) {
        super();
        this.posicion = posicion;
        this.tiempo = tiempo;
        this.porcentaje = porcentaje;
        this.nombre = nombre;
    }
 
    public int getPosicion() {
        return posicion;
    }
 
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
 
    public String getTiempo() {
        return tiempo;
    }
 
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
 
    public String getPorcentaje() {
        return porcentaje;
    }
 
    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

 
}
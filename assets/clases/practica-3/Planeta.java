package com.inserta.ejercicio98.pojos;

import java.util.Random;

public class Planeta {
	private String nombre;
	private long distancia;
	private char elemento;
	private double gravedad;
	private boolean hayAtmosfera;
	
	public Planeta(String nombre, long distancia, char elemento, double gravedad, boolean hayAtmosfera) {
		this.nombre = nombre;
		this.distancia = distancia;
		this.elemento = elemento;
		this.gravedad = gravedad;
		this.hayAtmosfera = hayAtmosfera;
	}
	
	public Planeta() {
		Random rnd = new Random();
		this.nombre = "Planeta_" + this.hashCode();
		this.distancia = rnd.nextInt(1499) + 2;
		this.elemento = (rnd.nextBoolean() ? 'C':'S');
		this.gravedad = (rnd.nextInt(19) + 2) + rnd.nextDouble();
		this.hayAtmosfera = rnd.nextBoolean();
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getDistancia() {
		return distancia;
	}

	public void setDistancia(long distancia) {
		this.distancia = distancia;
	}

	public char getElemento() {
		return elemento;
	}

	public void setElemento(char elemento) {
		this.elemento = elemento;
	}

	public double getGravedad() {
		return gravedad;
	}

	public void setGravedad(double gravedad) {
		this.gravedad = gravedad;
	}

	public boolean isHayAtmosfera() {
		return hayAtmosfera;
	}

	public void setHayAtmosfera(boolean hayAtmosfera) {
		this.hayAtmosfera = hayAtmosfera;
	}

	/**
	 * Método que indica si un planeta cumple con las condiciones para la vida
	 * @return true, si cumple con los parámetros que hemos decidido para que haya vida, o false en caso contrario
	 */
	public boolean hayVida() {
		return (
				this.distancia >= 100 && this.distancia <= 300 && 
				this.gravedad >= 5 && this.gravedad<= 14.9 && 
				this.hayAtmosfera && 
				this.elemento == 'C' 
				);
	}

	public String toString() {
		return "Planeta [nombre=" + nombre + ", distancia=" + distancia + ", elemento=" + elemento + ", gravedad="
				+ gravedad + ", hayAtmosfera=" + hayAtmosfera + ", hayVida()=" + hayVida() + "]";
	}

	
	
	
	
	
	
	
}

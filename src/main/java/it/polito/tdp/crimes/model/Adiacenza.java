package it.polito.tdp.crimes.model;

public class Adiacenza {

	private String s1;
	private String s2;
	private int peso;
	
	public Adiacenza(String s1, String s2, int peso) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.peso = peso;
	}

	public String getS1() {
		return s1;
	}

	public String getS2() {
		return s2;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return s1 + " --> " + s2 + " | " + peso;
	}
	
}

package it.polito.tdp.food.model;


public class Correlato {

	private String correlato;
	private double peso;
	/**
	 * @param correlato
	 * @param d
	 */
	public Correlato(String correlato, double d) {
		super();
		this.correlato = correlato;
		this.peso = d;
	}
	public String getCorrelato() {
		return correlato;
	}
	public double getPeso() {
		return peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlato == null) ? 0 : correlato.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Correlato other = (Correlato) obj;
		if (correlato == null) {
			if (other.correlato != null)
				return false;
		} else if (!correlato.equals(other.correlato))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return correlato + " (" + peso + ")";
	}
	
}

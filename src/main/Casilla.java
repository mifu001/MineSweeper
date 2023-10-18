package main;
/**
 * Clase casilla, la cual contiene caracteristicas de cada una de las casillas del buscaminas
 * @author Miguel Fuente García
 *
 */
public class Casilla {

	// numeroDeBombasCerca: variable que contiene el número de bombas que tiene la casilla cerca.
	private int numeroDeBombasCerca;
	// tieneBomba: variable booleana que indica si la casilla tiene una bomba o no.
	private boolean tieneBomba;
	// visible: variable booleana que indica si el usuario ha levantado la casilla o no.
	private boolean visible;
	// flagged: variable booleana que indica si el usuario ha puesto una bandera en la casilla.
	private boolean flagged;
	/**
	 * Constructor de Casilla, se inicializa con todo a 0, menos el si tiene bomba o no.
	 * @param tieneBomba Booleano que indica si en la casilla hay una bomba o no.
	 */
	public Casilla(boolean tieneBomba) {
		this.numeroDeBombasCerca = 0;
		this.tieneBomba = tieneBomba;
		this.visible = false;
		this.flagged = false;
		
	}
	/**
	 * Método set de numeroDeBombasCerca.
	 * @param numeroBombas Parametro del número de bombas que tiene la casilla.
	 */
	public void setNumeroDeBombasCerca(int numeroBombas) {
		this.numeroDeBombasCerca = numeroBombas;
	}
	/**
	 * Método get de numeroDeBombasCerca.
	 * @return numeroDeBombasCerca.
	 */
	public int getNumeroDeBombasCerca() {
		return this.numeroDeBombasCerca;
	}
	
	/**
	 * Método get de tieneBomba.
	 * @return tieneBomba.
	 */
	public boolean getTieneBomba() {
		return this.tieneBomba;
	}
	
	/**
	 * Método set de visible. Pone el valor a True.
	 */
	public void setVisible() {
		this.visible = true;
	}
	/**
	 * Método get de visible.
	 * @return visible.
	 */
	public boolean getVisible() {
		return this.visible;
	}
	/**
	 * Método set de flagged. Pone el valor contrario a flagged.
	 * 
	 */
	public void setFlagged() {
		this.flagged = !this.flagged;
	}
	/**
	 * Método get de flagged. 
	 * @return flagged.
	 */
	public boolean getFlagged() {
		return this.flagged;
	}
}

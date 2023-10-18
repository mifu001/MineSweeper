package main;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase Tablero. Se encarga de crear el tablero donde se realizara el buscaminas.
 * @author Miguel Fuente García
 *
 */
public class Tablero {
	/**  Tablero: variable que contiene una matriz de casillas.*/
	private Casilla[][] tablero;
	/**  NUMERO_FILAS: Variable estatica, que define el tamaño del tablero.*/
	public final static int NUMERO_FILAS = 10;
	/** NUMERO_COLUMNAS: Variable estatica, que define el tamaño del tablero.*/
	public final static int NUMERO_COLUMNAS = 10;
	/** ORDEN_FILA: Variable estatica, que tiene el conjunto de celdas adyacentes segun la fila.*/
	public final static int[] ORDEN_FILA = {-1,-1,-1,0,0,1,1,1};
	/** ORDEN_COLUMNA: Variable estatica, que tiene el conjunto de celdas adyacentes segun la columna.*/
	public final static int[] ORDEN_COLUMNA = {-1,0,1,-1,1,-1,0,1};
	/** RED: Variable estatica que sirve para imprimir por pantalla en color rojo.*/
	public final static String RED = "\033[31m";
	/** GREEN: Variable estatica que sirve para imprimir por pantalla en color verde.*/
	public final static String GREEN = "\033[32m";
	/** RESET: Variable estatica que sirve para imprimir por pantalla en el color por defecto.*/
	public final static String RESET = "\u001B[0m";
	
	/**
	 * Contructor del tablero, el cual inicializa el tablero con el numero de bombas pasado.
	 * @param numeroDeBombas Parametro que proporciona el numero de bombas a añadir en el tablero.
	 */
	public Tablero(int numeroDeBombas) {
		
		this.tablero = new Casilla[NUMERO_FILAS][NUMERO_COLUMNAS];
		ArrayList<Integer> casillasBomba = new ArrayList<>();
		Random rand = new Random();
		while(casillasBomba.size() < numeroDeBombas) {
			int posicionAleatoria = rand.nextInt(NUMERO_FILAS*NUMERO_COLUMNAS);
			if(!casillasBomba.contains(posicionAleatoria)) {
				casillasBomba.add(posicionAleatoria);
			}
		}
		for(int i = 0; i < NUMERO_FILAS; i++) {
			for(int j = 0; j < NUMERO_COLUMNAS; j++) {
				if(casillasBomba.contains(i*NUMERO_FILAS+j)){
					this.tablero[i][j] = new Casilla(true);
				}else {
					this.tablero[i][j] = new Casilla(false);
				}
			}
		}
		colocarNumeroBombas();
	}
	/**
	 * Método que imprime  el tablero según las casillas visibles y el valor que incluyan.
	 */
	public void imprimirTablero() {
		System.out.print(GREEN + "   |");
		for(int j = 0; j < NUMERO_COLUMNAS; j++) {
				System.out.print(" ");
				System.out.print(j);
				System.out.print(" |");
		}
		System.out.println("");
		for(int i = 0; i < NUMERO_FILAS; i++) {

			for(int j = 0; j < NUMERO_COLUMNAS + 1; j++) {
				System.out.print(GREEN + "---+");
			}
			System.out.println("");
			System.out.print(" ");
			System.out.print(i);
			System.out.print(" |" + RESET);
			for(int j = 0; j < NUMERO_COLUMNAS; j++) {
				if(this.tablero[i][j].getFlagged()) {
					System.out.print(RESET + " 🚩" + GREEN + "|" + RESET);
				}else {
					if(!this.tablero[i][j].getVisible()) {
						System.out.print(GREEN + "   |" + RESET);
					}else {
						if(this.tablero[i][j].getTieneBomba()) {
							System.out.print(RED + " M "+ GREEN +"|" + RESET );
						}else {
							System.out.print(" " + Integer.toString(this.tablero[i][j].getNumeroDeBombasCerca()) + GREEN + " |" + RESET);
						 }
					}
				}
			}
			System.out.println();
		}
		
		for(int j = 0; j < NUMERO_COLUMNAS + 1; j++) {
			System.out.print(GREEN + "---+" + RESET);
		}
	}
	
	/**
	 * Método que permite colocar las bombas en el tablero.
	 */
	private void colocarNumeroBombas() {
		for(int i = 0; i < NUMERO_FILAS; i++) {
			for(int j = 0; j < NUMERO_COLUMNAS; j++) {
				tablero[i][j].setNumeroDeBombasCerca(numeroBombasCasilla(i,j));
			}
		}
	}
	
	/**
	 * Método que cuenta el número de bombas que hay cerca de la casilla indicada.
	 * @param fila Parametro que indica la fila de la casilla.
	 * @param columna Parametro que indica la columna de la casilla.
	 * @return Devuelve el número de bombas que hay cerca de la casilla.
	 */
	private int numeroBombasCasilla(int fila, int columna) {
		int bombas = 0;
		for(int pos = 0; pos < ORDEN_FILA.length; pos++ ) {
			int filaAdy = fila + ORDEN_FILA[pos];
			int columnaAdy = columna + ORDEN_COLUMNA[pos];
			if(filaAdy < 0 || filaAdy >= NUMERO_FILAS || columnaAdy < 0 || columnaAdy >= NUMERO_COLUMNAS) {
				continue;
			}
			if(tablero[filaAdy][columnaAdy].getTieneBomba()) {
				bombas += 1;
			}
		}
		return bombas;
	}
	/**
	 * Método que permite "cavar", para descubrir que el valor de la Casilla seleccionada.
	 * @param fila Parametro que indica la fila de la casilla.
	 * @param columna Parametro que indica la columna de la casilla.
	 * @return Devuelve verdadero si ha encontrado bomba, y falso en caso contrario, levantando las celdas correspondientes.
	 */
	public boolean escogerPosicion(int fila, int columna) {
		this.tablero[fila][columna].setVisible();
		if(this.tablero[fila][columna].getTieneBomba()) {
			return true;
		}
		if(this.tablero[fila][columna].getNumeroDeBombasCerca() == 0) {
			for(int pos = 0; pos < ORDEN_FILA.length; pos++ ) {
				int filaAdy = fila + ORDEN_FILA[pos];
				int columnaAdy = columna + ORDEN_COLUMNA[pos];
				if(filaAdy < 0 || filaAdy >= NUMERO_FILAS || columnaAdy < 0 || columnaAdy >= NUMERO_COLUMNAS) {
					continue;
				}
				if(!this.tablero[filaAdy][columnaAdy].getVisible())
					escogerPosicion(filaAdy,columnaAdy);
				}
		}
		return false;
	}
	/**
	 * Método que permite contabilizar el número de casillas que no estan visibles por el usuario.
	 * @return Devuelve el numero de casillas que no son visibles.
	 */
	public int casillasTapadas() {
		int contadorCasillasTapadas = 0;
		for(Casilla[] filaCasillas: this.tablero) {
			for(Casilla c : filaCasillas) {
				if(!c.getVisible()) {
					contadorCasillasTapadas++;
				}
			}
		}
		return contadorCasillasTapadas;
	}
	/**
	 * Método que permite poner una bandera a la casilla seleccionada.
	 * @param fila Parametro que indica la fila de la casilla.
	 * @param columna Parametro que indica la columna de la casilla.
	 */
	public void ponerFlag(int fila, int columna) {
		if(!(fila < 0 ||fila >= NUMERO_FILAS || columna < 0 || columna >= NUMERO_COLUMNAS)) {
			tablero[fila][columna].setFlagged();
		}
	}
}

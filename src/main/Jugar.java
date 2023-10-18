package main;
import java.util.Scanner;
/**
 * Clase Jugar, permite disfrutar del juego "Buscaminas".
 * @author Miguel Fuente García
 *
 */
public class Jugar {
	
	/**
	 * Método main, método que permite jugar al Buscaminas, donde se pide el número de bombas al usuario con las posiciones a realizar.
	 * @param args Argumentos del programa.
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numeroBombas;
		do {
			System.out.print("Introduce el numero de bombas: ");
			numeroBombas = s.nextInt();	
		}while(numeroBombas >= Tablero.NUMERO_COLUMNAS * Tablero.NUMERO_FILAS);
		
		Tablero tablero = new Tablero(numeroBombas);
		tablero.imprimirTablero();

		s.nextLine();
		pedirMovimiento(s, numeroBombas, tablero);
		System.out.println("\nHas perdido");
		s.close();
		
	}

	/**
	 * Método principal del juego, donde el usuario puede introducir los puntos a minar o a poner bomba, hasta que se acabe el juego.
	 * @param s Scanner.
	 * @param numeroBombas Parametro que determina el numero de bombas.
	 * @param tablero Parametro que contiene el tablero con las bombas colocadas en el.
	 */
	private static void pedirMovimiento(Scanner s, int numeroBombas, Tablero tablero) {
		Boolean finish = false;
		while(!finish) {
			System.out.println("");
			System.out.print("Introduce una casilla: ");
			
			String movimiento = s.nextLine();
			if(movimiento.length() == 3) {
				int fila =  movimiento.charAt(0) - 48;
				int columna = movimiento.charAt(1) - 48;
				char objetivo = movimiento.charAt(2);
				if(objetivo == 'B') {
					tablero.ponerFlag(fila, columna);
					tablero.imprimirTablero();
				}
				if(objetivo == 'C') {
					finish = tablero.escogerPosicion(fila, columna);
					tablero.imprimirTablero();
					if(numeroBombas == tablero.casillasTapadas() && !finish) {
						System.out.println("\nHas resuelto el Buscaminas");
						s.close();
						System.exit(0);
					}
				}
			}
		}
	}

}

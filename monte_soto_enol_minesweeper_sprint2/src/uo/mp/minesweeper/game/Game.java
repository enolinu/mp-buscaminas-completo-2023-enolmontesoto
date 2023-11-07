package uo.mp.minesweeper.game;

import java.io.PrintStream;

import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.lab.util.console.Console;

/**
 * Clase que gestiona la entrada y salida del juego.
 * 
 * @author enolmontesoto
 *
 */
public class Game {
	
	private Board board;
	private long time;
	
	/**
	 * Constructor del juego, inicializa el tablero.
	 * @param board
	 */
	public Game(Board board) {
		
		ArgumentChecks.checkNotNull(board, "The board cannot be null!");
		this.board = board;
		time = System.currentTimeMillis(); 
		
	}
	
	/**
	 * Ejecuta el bucle principal del juego.
	 */
	public void play() {
		
		PrintStream out = System.out;
		print(out, Message.START);
		gameLoop(out);
		
	}
	
	// Imprime un mensaje.
	
	private void print(PrintStream out, Message m) {
		out.print(getMessage(m));
	}
	
	// Obtiene la cadena que debe imprimirse en función del tipo de mensaje.
	
	private String getMessage(Message m) {
		
		switch(m) {
		case START: return "Ready to start\n";
		case TIME: return "Time: " + getTime() + " seconds\n";
		case FLAGS: return getFlagsLeft();
		case BOARD: return getBoard() + "\n\n";
		case MOVE: return "movement (s | f | u):\n";
		case LOSE: return "GAME OVER!\n";
		case WIN: return "CONGRATULATIONS! YOU WON!\n";
		case MINES: return getMinesLeft() + "\n";
		default: return "\n";
		}
		
	}
	
	// Devuelve las banderas que quedan por colocar.
	
	private String getFlagsLeft() {
		return "Flags Left: "+ board.getNumberOfFlagsLeft() + "\n";
	}
	
	// Devuelve las minas que quedaron por marcar.
	
	private String getMinesLeft() {
		return "Mines Left: "+ board.getNumberOfFlagsLeft() + "\n";
	}
	
	// Devuelve el toString() representativo del tablero.
	
	private String getBoard() {
		return board.toString();
	}
	
	// Lee y ejecuta la acción leída en las coordenadas leídas.
	
	private void executeAction(String action, int xCoord, int yCoord) {
		
		ArgumentChecks.checkNotNull(action, "Action cannot be null.");
		ArgumentChecks.isTrue(action.equalsIgnoreCase("s") 
							|| action.equalsIgnoreCase("f") 
							|| action.equalsIgnoreCase("u"),
							"Action must be s, f, or, u");
		
		
		if(action.equalsIgnoreCase("s")) {
			board.stepOn(xCoord, yCoord);
		}
		
		else if(action.equalsIgnoreCase("f")) {
			board.flag(xCoord, yCoord);
		}
		
		else if(action.equalsIgnoreCase("u")) {
			board.unflag(xCoord, yCoord);
		}
		
		
	}
	
	// Imprime los mensajes con la información en cada ronda.
	
	private void printMessageLoop(PrintStream out) {
		
		out.println("-----------------");
		print(out, Message.TIME);
		print(out, Message.FLAGS);
		out.println("-----------------");
		print(out, Message.BOARD);
		print(out, Message.MOVE);
		
	}
	
	// Bucle principal del juego.
	
	private void gameLoop(PrintStream out) {
		
		while(!board.isExploded() && board.getNumberOfMinesLeft() > 0) {
			
			printMessageLoop(out);
	
			String action = Console.readString();
			int xCoord = Console.readInt();
			int yCoord= Console.readInt();
			
			executeAction(action, xCoord, yCoord);
			
		}
		
		if(board.getNumberOfMinesLeft() <= 0) {
			print(out, Message.WIN);
			print(out, Message.BOARD);
			return;
		}
		
		printLost(out);
		
	}
	
	// Imprime el mensaje de fin de juego junto con el tablero destapado.
	
	private void printLost(PrintStream out) {
		
		print(out, Message.LOSE);
		print(out, Message.MINES);
		print(out, Message.BOARD);
		
	}
	
	// Para obtener el tiempo transcurrido.
	
	private long getTime() {
		long nowMillis = System.currentTimeMillis();
        return ((nowMillis - this.time) / 1000);
	}
	
	

}

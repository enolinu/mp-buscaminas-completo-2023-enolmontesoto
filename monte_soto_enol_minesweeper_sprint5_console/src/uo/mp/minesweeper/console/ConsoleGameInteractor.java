package uo.mp.minesweeper.console;

import java.io.PrintStream;

import uo.mp.lab.util.console.Console;
import uo.mp.minesweeper.exception.UserInteractionException;
import uo.mp.minesweeper.game.Board;
import uo.mp.minesweeper.game.GameInteractor;
import uo.mp.minesweeper.game.GameMove;

/**
 * Implementación de la interfaz GameInteractor. Se trata de una
 * interfaz de usuario basada en entrada y salida por consola.
 * 
 * @author enolmontesoto
 *
 */
public class ConsoleGameInteractor implements GameInteractor {
	
	/**
	 * Pregunta al usuario por un tipo de movimiento a realizar,
	 * así como de la casilla donde realizarlo
	 * @param rows, filas que tiene el tablero.
	 * @param columns, columnas que tiene el tablero.
	 * @throws UserInteractionException 
	 */
	@Override
	public GameMove askMove(int rows, int columns) throws UserInteractionException {
		
		print(System.out, "Movement (s | f | u) ?");
		char movement = (char)Console.readCharacter();
		movement = Character.toLowerCase(movement);
		
		if(movement != 's' && movement != 'f' && movement != 'u') {
			throw new UserInteractionException("Options can only be s, f or u!");
		}
		
		print(System.out, "Row?");
		int row = Console.readInt();
		
		if(row < 0 || row >= rows) {
			throw new UserInteractionException("The row must be inside the rank!");
		}
		
		print(System.out, "Column?");
		int col = Console.readInt();
		
		if(col < 0 || col >= columns) {
			throw new UserInteractionException("The column must be inside the rank!");
		}
		
		GameMove gm = new GameMove(movement, row, col);
		
		return gm;
	}
	
	/**
	 * Muestra el tablero de juego junto el tiempo transcurrido
	 * desde el inicio de la partida.
	 * @param elapsedTime, los segundos transcurridos.
	 * @param board, referencia al tablero.
	 */
	@Override
	public void showGame(long elapsedTime, Board board) {
		
		print(System.out, "Time: " + elapsedTime + " seconds");
		
		System.out.print(getBoard(board));
		
	}
	
	/**
	 * Muestra el mensaje de fin de juego.
	 */
	@Override
	public void showGameFinished() {
		
		print(System.out, "GAME OVER!");
		
	}

	/**
	 * Muestra el mensaje de enhorabuena, al ganarse la partida.
	 * @param elapsedTime, el tiempo transcurrido durante toda la partida.
	 */
	@Override
	public void showCongratulations(long elapsedTime) {
		
		print(System.out, "CONGRATULATIONS! You won the game in: " + elapsedTime + " seconds.");
		
	}
	
	/**
	 * Muestra el mensaje adecuado a cuando estalla una mina.
	 */
	@Override
	public void showBooommm() {
		
		print(System.out, "BOOOOM!");
		
	}
	
	/**
	 * Muestra el mensaje de "preparado para comenzar".
	 */
	@Override
	public void showReadyToStart() {
		
		print(System.out, "Ready to start.");
		
	}
	
	// Método auxiliar para imprimir mensajes.
	
	private void print(PrintStream out, String message) {
		out.println(message);
	}
	
	// Pinta el tablero.
	
	private String getBoard(Board board) {
		
		StringBuilder sb = new StringBuilder();
		String columnSeparator = generateColumnSeparator(board);
		
		int rowCount = 0;
		sb.append(generateColumnMarker(board));
		sb.append(columnSeparator + rowCount + " | ");
		for(int i=0; i<board.getNumberOfRows(); i++) {
			if(i!=0 && rowCount!=0) {
				sb.append(columnSeparator + rowCount + " | ");
			}
			rowCount++;
			for(int j=0; j<board.getNumberOfColumns(); j++) {
				sb.append(board.getState()[i][j]).append(" | ");
			}
		}
		sb.append(columnSeparator + "\n");
		
		return sb.toString();
		
	}
	
	private String generateColumnMarker(Board b) {
		
		StringBuilder sb = new StringBuilder("\n-   ");
		for(int i = 0; i < b.getNumberOfColumns(); i++) {
			sb.append(i).append("   ");
		}
		return sb.toString();
		
	}
	
	private String generateColumnSeparator(Board b) {
		
		StringBuilder sb = new StringBuilder("\n  ");
		for(int i = 0; i < b.getNumberOfColumns(); i++) {
			sb.append("+---");
		}
		sb.append("+\n");
		return sb.toString();
		
	}

}

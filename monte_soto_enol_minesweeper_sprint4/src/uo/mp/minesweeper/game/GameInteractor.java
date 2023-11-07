package uo.mp.minesweeper.game;

import uo.mp.minesweeper.exception.UserInteractionException;

/**
 * Se trata de una interfaz, que sirve como contrato para cualquier
 * interfaz de usuario que se implemente para éste proyecto.
 * 
 * @author enolmontesoto
 *
 */
public interface GameInteractor {
	
	/**
	 * Pregunta al usuario por un tipo de movimiento a realizar,
	 * así como de la casilla donde realizarlo
	 * @param rows, filas que tiene el tablero.
	 * @param columns, columnas que tiene el tablero.
	 * @throws UserInteractionException 
	 */
	GameMove askMove(int rows, int columns) throws UserInteractionException;
	
	/**
	 * Muestra el tablero de juego junto el tiempo transcurrido
	 * desde el inicio de la partida.
	 * @param elapsedTime, los segundos transcurridos.
	 * @param board, referencia al tablero.
	 */
	void showGame(long elapsedTime, Board board);
	
	/**
	 * Muestra el mensaje de fin de juego.
	 */
	void showGameFinished();
	
	/**
	 * Muestra el mensaje de enhorabuena, al ganarse la partida.
	 * @param elapsedTime, el tiempo transcurrido durante toda la partida.
	 */
	void showCongratulations(long elapsedTime);
	
	/**
	 * Muestra el mensaje adecuado a cuando estalla una mina.
	 */
	void showBooommm();
	
	/**
	 * Muestra el mensaje de "preparado para comenzar".
	 */
	void showReadyToStart();

}

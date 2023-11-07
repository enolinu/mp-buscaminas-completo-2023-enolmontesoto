package uo.mp.minesweeper.game.square.actions;

import uo.mp.minesweeper.exception.GameException;

/**
 * Todas las acciones comparten la funcionalidad de ésta interfaz
 * 
 * @author enolmontesoto
 *
 */
public interface Executable {
	
	/**
	 * Ejecutar la acción correspondiente a cada tipo.
	 * @throws GameException 
	 */
	void execute() throws GameException;

}

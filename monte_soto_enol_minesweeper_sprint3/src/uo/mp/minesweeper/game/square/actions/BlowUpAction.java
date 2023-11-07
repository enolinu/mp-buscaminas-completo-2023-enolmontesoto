package uo.mp.minesweeper.game.square.actions;

import uo.mp.minesweeper.game.Board;

/**
 * Acción destinada a ser asociada a una casilla de mina.
 * 
 * @author enolmontesoto
 *
 */
public class BlowUpAction implements Executable {
	
	Board board;
	
	/**
	 * Recibe un puntero a el tablero de juego y lo guarda.
	 * 
	 * @param squares
	 */
	public BlowUpAction(Board squares) {
		
		this.board = squares;
		
	}	
	
	/**
	 * Marca el tablero de juego como explotado.
	 */
	@Override
	public void execute() {

		board.setExploded(true);
		board.unveil();
		
	}

}

package uo.mp.minesweeper.test.board;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.model.Board;

/**
 * Clase que verifica los parámetros del contructor de la clase "Board".
 * 
 * @author enolmontesoto
 *
 */
class TestConstructor {
	
	Board b;
	
	/**
	 * WHEN Se crea un tablero.
	 * THEN Debe tener los parámetros correctos.
	 */
	@Test
	void GeneralConstructorTest() {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE+1, Board.DEFAULT_PERCENTAGE);
		
		assertEquals(Board.DEFAULT_SIDE, b.getBoardForTesting().length);
		assertEquals(Board.DEFAULT_SIDE + 1, b.getBoardForTesting()[0].length);
		
	}

}

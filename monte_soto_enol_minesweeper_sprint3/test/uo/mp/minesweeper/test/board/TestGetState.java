package uo.mp.minesweeper.test.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.game.Board;

/**
 * La siguiente clase recoge los métodos de prueba del método "getState()" de
 * la clase "Board".
 * 
 * @author enolmontesoto
 *
 */
class TestGetState {
	
	Board b;
	
	@BeforeEach
	void setUp() throws Exception {
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, Board.DEFAULT_PERCENTAGE, true);
	}
	
	/**
	 * GIVEN Tablero completamente cerrado.
	 * WHEN Se invoca getState()
	 * THEN Todo asteriscos.
	 */
	@Test
	void CompletelyClosedBoardTest() {
		
		char[][] result = b.getState();
		
		for(int i=0; i<b.getBoardForTesting().length; i++) {
			for(int j=0; j<b.getBoardForTesting()[0].length; j++) {
				assertEquals('#', result[i][j]);
			}
		}
		
	}
	
	/**
	 * GIVEN Tablero completamente abierto.
	 * WHEN Se invoca getState()
	 * THEN Todo pistas numericas y minas.
	 */
	@Test
	void CompletelyOpenedBoardTest() {
		
		for(int i=0; i<b.getBoardForTesting().length; i++) {
			for(int j=0; j<b.getBoardForTesting()[0].length; j++) {
				b.getBoardForTesting()[i][j].open();
			}
		}
		
		char[][] result = b.getState();
		
		for(int i=0; i<b.getBoardForTesting().length; i++) {
			for(int j=0; j<b.getBoardForTesting()[0].length; j++) {
				assertNotEquals('#', result[i][j]);
			}
		}
		
	}
	

	/**
	 * GIVEN Tablero mezclado.
	 * WHEN Se invoca getState()
	 * THEN Se verifica que imprime la bandera.
	 */
	@Test
	void MixedOpenedBoardTest() {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0, true);
		b.flag(1, 1);
		b.stepOn(2, 5);
		char[][] result = b.getState();
		assertEquals((char) 182, result[1][1]);		
		
	}

}

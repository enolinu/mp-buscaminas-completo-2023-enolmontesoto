package uo.mp.minesweeper.test.board;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.game.Board;

/**
 * La siguiente clase recoge los métodos de prueba del método "flag" de
 * la clase "Board" del modelo.
 * 
 * @author enolmontesoto
 *
 */
class TestFlag {
	
	Board b;
	
	/*
	 * Se presentan los siguientes casos de prueba:
	 * 		1 => Bandera sobre casilla ya marcada.
	 * 		2 => Bandera sobre casilla desmarcada de mina.
	 * 		3 => Bandera sobre casilla desmarcada sin mina.
	 * 		4 => Bandera sobre una casilla dos veces consecutivas.
	 */
	
	/**
	 * GIVEN Una casilla con bandera.
	 * WHEN Se intenta marcar de nuevo.
	 * THEN No se ejecuta dos veces porque ya está marcada.
	 * @throws GameException 
	 */
	@Test
	void FlagFlaggedSquareTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 100, true);
		try {
			b.flag(1, 1);
			b.flag(1, 1);
			fail();
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Cant flag a flagged or opened square!");
		}
		
	}
	
	/**
	 * GIVEN Una casilla de mina sin bandera.
	 * WHEN Se intenta desmarcar.
	 * THEN Se marca, con todo lo que ello implica.
	 * @throws GameException 
	 */
	@Test
	void FlagUnflaggedSquareWithMine() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 100, true);
		b.flag(1, 1);
		assertTrue(b.getBoardForTesting()[1][1].isFlagged());
		assertEquals(b.getNumberOfMines()-1, b.getNumberOfFlagsLeft());
		assertEquals(b.getNumberOfMines()-1, b.getNumberOfMinesLeft());
		
	}
	
	/**
	 * GIVEN Una casilla sin mina sin bandera.
	 * WHEN Se intenta desmarcar.
	 * THEN Se marca, con todo lo que ello implica.
	 * @throws GameException 
	 */
	@Test
	void FlagUnflaggedSquareWithoutMineTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0, true);
		b.flag(1, 1);
		assertTrue(b.getBoardForTesting()[1][1].isFlagged());
		assertEquals(b.getNumberOfMines(), b.getNumberOfFlagsLeft());
		assertEquals(b.getNumberOfMines(), b.getNumberOfMinesLeft());
		
	}
	
	/**
	 * GIVEN Una casilla sin mina sin bandera.
	 * WHEN Se intenta desmarcar.
	 * THEN No ocurre nada la segunda vez porque ya está marcada.
	 * @throws GameException 
	 */
	@Test
	void FlagTwoTimesTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 100, true);
		try {
			b.flag(1, 1);
			b.flag(1, 1);
			fail();
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Cant flag a flagged or opened square!");
		}
		
	}

}

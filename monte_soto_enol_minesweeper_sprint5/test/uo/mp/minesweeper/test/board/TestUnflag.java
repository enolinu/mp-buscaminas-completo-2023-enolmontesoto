package uo.mp.minesweeper.test.board;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.game.Board;

/**
 * La siguiente clase recoge los métodos de prueba del método unflag de
 * la clase "Board" del modelo.
 * 
 * @author enolmontesoto
 *
 */
class TestUnflag {
	
	Board b;
	
	/*
	 * Se presentan los siguientes casos de prueba:
	 * 		1 => Desmarcar casilla de mina con bandera.
	 * 		2 => Desmarcar casilla de mina sin bandera.
	 * 		3 => Desmarcar casilla sin bandera.
	 * 		4 => Desmarcar una casilla dos veces.
	 */
	
	/**
	 * GIVEN Una casilla de mina con bandera.
	 * WHEN Se intenta desmarcar.
	 * THEN Se desmarca correctamente, con todo lo que ello implica.
	 * @throws GameException 
	 */
	@Test
	void UnflagFlaggedSquareWithMineTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 100, true);
		b.flag(1, 1);
		b.unflag(1, 1);
		assertFalse(b.getBoardForTesting()[1][1].isFlagged());
		assertEquals(b.getNumberOfMines(), b.getNumberOfFlagsLeft());
		assertEquals(b.getNumberOfMines(), b.getNumberOfMinesLeft());
		
	}
	
	/**
	 * GIVEN Una casilla de mina sin bandera.
	 * WHEN Se intenta desmarcar.
	 * THEN No ocurre nada porque no está marcada.
	 * @throws GameException 
	 */
	@Test
	void UnflagUnflaggedSquareWithMineTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 100, true);
		try {
			b.unflag(1, 1);
			assertFalse(b.getBoardForTesting()[1][1].isFlagged());
			assertEquals(b.getNumberOfMines(), b.getNumberOfFlagsLeft());
			assertEquals(b.getNumberOfMines(), b.getNumberOfMinesLeft());
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Cant unflag a flagged or opened square!");
		}
		
	}
	
	/**
	 * GIVEN Una casilla sin mina sin bandera.
	 * WHEN Se intenta desmarcar.
	 * THEN No ocurre nada porque no está marcada.
	 * @throws GameException 
	 */
	@Test
	void UnflagUnflaggedSquareWithoutMineTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0, true);
		
		try {
			b.unflag(1, 1);
			fail();
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Cant unflag a flagged or opened square!");
		}
		
	}
	
	/**
	 * GIVEN Una casilla sin mina sin bandera.
	 * WHEN Se intenta desmarcar.
	 * THEN No ocurre nada porque no está marcada.
	 * @throws GameException 
	 */
	@Test
	void UnflagTwoTimesTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0, true);
		
		try {
			b.unflag(1, 1);
			b.unflag(1, 1);
			fail();
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Cant unflag a flagged or opened square!");
		}
		
	}

}

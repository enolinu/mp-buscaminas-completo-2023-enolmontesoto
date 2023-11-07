package uo.mp.minesweeper.test.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.game.Board;

/**
 * Clase que recoge todas las pruebas unitarias del método stepOn() ubicado en
 * la clase "Board".
 * 
 * @author enolmontesoto
 *
 */
class TestStepOn {
	
	Board b;

	/*
	 * Se presentan los siguientes casos de prueba:
	 * 1 => stepOn en casilla ya descubierta.
	 * 2 => stepOn en casilla con bandera con mina.
	 * 3 => stepOn en casilla con bandera sin mina.
	 * 4 => stepOn en casilla con mina.
	 * 5 => stepOn en casilla con pista numérica
	 * 6 => stepOn en casilla vacía. (???).
	 */
	
	/**
	 * GIVEN Una casilla ya descubierta.
	 * WHEN Se le aplica stepOn()
	 * THEN No ocurre nada porque ya está descubierta.
	 * @throws GameException 
	 */
	@Test
	void stepOnOpenedSquareTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0, true);
		b.stepOn(3,5);
		b.stepOn(3,5);
		assertTrue(b.getBoardForTesting()[3][5].isOpened());
		assertFalse(b.isExploded());
		
	}
	
	/**
	 * GIVEN Una casilla marcada y con mina.
	 * WHEN Se le aplica stepOn()
	 * THEN No explota porque está marcada.
	 * @throws GameException 
	 */
	@Test
	void stepOnFlaggedSquareWithMineTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 100, true);
		b.flag(3,5);
		b.stepOn(3,5);
		assertFalse(b.getBoardForTesting()[3][5].isOpened());
		assertTrue(b.getBoardForTesting()[3][5].isFlagged());
		assertFalse(b.isExploded());
		
	}
	
	/**
	 * GIVEN Una casilla cubierta y sin mina.
	 * WHEN Se le aplica stepOn()
	 * THEN Sale la pista numérica.
	 * @throws GameException 
	 */
	@Test
	void stepOnFlaggedSquareWithoutMineTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0, true);
		b.flag(3,5);
		b.stepOn(3,5);
		assertFalse(b.getBoardForTesting()[3][5].isOpened());
		assertTrue(b.getBoardForTesting()[3][5].isFlagged());
		assertFalse(b.isExploded());
		
	}
	
	/**
	 * GIVEN Una casilla cubierta y con mina.
	 * WHEN Se le aplica stepOn()
	 * THEN Explota.
	 * @throws GameException 
	 */
	@Test
	void stepOnSquareWithMineTest() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 100, true);
		b.stepOn(3,5);
		assertTrue(b.getBoardForTesting()[3][5].isOpened());
		assertTrue(b.isExploded());
		
	}
	
	/**
	 * GIVEN Una casilla cubierta y con pista numérica.
	 * WHEN Se le aplica stepOn().
	 * THEN La pista no varía.
	 * @throws GameException 
	 */
	@Test
	void stepOnSquareWithNumericClue() throws GameException {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0);
		b.stepOn(3, 5);
		int oldValue = b.getBoardForTesting()[3][5].getValue();
		b.stepOn(3,5);
		int newValue = b.getBoardForTesting()[3][5].getValue();
		assertEquals(oldValue, newValue);
		assertTrue(b.getBoardForTesting()[3][5].isOpened());
		assertFalse(b.isExploded());
		
	}

}

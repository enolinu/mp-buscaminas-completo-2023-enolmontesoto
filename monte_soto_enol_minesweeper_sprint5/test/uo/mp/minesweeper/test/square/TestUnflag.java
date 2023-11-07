package uo.mp.minesweeper.test.square;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.game.square.Square;

/**
 * La siguiente clase se trata de una clase de prueba cuyo objetivo es probar 
 * la funcionalidad del método "flag" de la clase "Square"
 * 
 * @author enolmontesoto
 *
 */
class TestUnflag {
	
	/*
	 * Se presentan los siguientes casos de prueba:
	 * 		1 => Flag sobre casilla OPENED.
	 * 		2 => Flag sobre casilla CLOSED.
	 * 		3 => Flag sobre casilla FLAGGED. 
	 */
	
	private Square s;

	@BeforeEach
	void setUp() throws Exception {
		s = new Square();
	}
	
	/**
	 * GIVEN Una casilla abierta.
	 * WHEN Se intenta desmarcar la bandera.
	 * THEN No ocurre nada porque la casilla debe estar exclusivamente marcada con bandera.
	 * @throws GameException 
	 */
	@Test
	void OpenedUnflagTest() throws GameException {
		
		s.open();
		assertFalse(s.isFlagged());
		try {
			s.unflag();
			fail();
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Cant unflag a flagged or opened square!");
		}
		
	}
	
	/**
	 * GIVEN Una casilla cerrada.
	 * WHEN Se intenta desmarcar la bandera.
	 * THEN No ocurre nada porque la casilla debe estar exclusivamente marcada con bandera.
	 * @throws GameException 
	 */
	@Test
	void ClosedUnflagTest() throws GameException {
		
		assertFalse(s.isFlagged());
		try {
			s.unflag();
			fail();
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Cant unflag a flagged or opened square!");
		}
		
	}
	
	/**
	 * GIVEN Una casilla marcada con bandera.
	 * WHEN Se intenta desmarcar la bandera.
	 * THEN Se desmarca la casilla.
	 * @throws GameException 
	 */
	@Test
	void FlaggedUnflagTest() throws GameException {
		
		s.flag();
		s.unflag();
		assertFalse(s.isFlagged());
		
	}
	
	

}

package uo.mp.minesweeper.test.square;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	 */
	@Test
	void OpenedUnflagTest() {
		
		s.open();
		assertFalse(s.isFlagged());
		s.unflag();
		assertFalse(s.isFlagged());
		
	}
	
	/**
	 * GIVEN Una casilla cerrada.
	 * WHEN Se intenta desmarcar la bandera.
	 * THEN No ocurre nada porque la casilla debe estar exclusivamente marcada con bandera.
	 */
	@Test
	void ClosedUnflagTest() {
		
		assertFalse(s.isFlagged());
		s.unflag();
		assertFalse(s.isFlagged());
		
	}
	
	/**
	 * GIVEN Una casilla marcada con bandera.
	 * WHEN Se intenta desmarcar la bandera.
	 * THEN Se desmarca la casilla.
	 */
	@Test
	void FlaggedUnflagTest() {
		
		s.flag();
		s.unflag();
		assertFalse(s.isFlagged());
		
	}
	
	

}

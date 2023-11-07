/**
 * 
 */
package uo.mp.minesweeper.test.square;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.game.square.Square;

/**
 * 
 * Clase que recoge los métodos de prueba del método "step on", el cual se
 * encuentra en la clase "Square" del modelo.
 * 
 * @author enolmontesoto
 *
 */
class TestStepOn {
	
	/*
	 * Se presentan los siguentes casos de prueba:
	 * 		1 => stepOn sobre casilla OPENED.
 	 *		2 => stepOn sobre casilla CLOSED.
 	 *		3 => stepOn sobre casilla FLAGGED.
	 */

	
	private Square s;
	
	@BeforeEach
	void setUp() throws Exception {
		s = new Square();
	}
	
	/**
	 * GIVEN Una casilla abierta.
	 * WHEN Se intenta aplicar stepOn().
	 * THEN No ocurre nada debido a que la casilla debe estar cerrada.
	 * @throws GameException 
	 */
	@Test
	void OpenedStepOnTest() throws GameException {
		
		s.open();
		try {
			s.stepOn();
			fail();
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Can´t step on a closed or flagged square!");
		}
		
	}
	
	/**
	 * GIVEN Una casilla cerrada.
	 * WHEN Se intenta aplicar stepOn().
	 * THEN La casilla se abre.
	 * @throws GameException 
	 */
	@Test
	void ClosedStepOnTest() throws GameException {
		
		s.stepOn();
		assertTrue(s.isOpened());
		
	}
	
	/**
	 * GIVEN Una casilla marcada con bandera.
	 * WHEN Se intenta aplicar stepOn().
	 * THEN No ocurre nada debido a que la casilla debe estar cerrada.
	 * @throws GameException 
	 */
	@Test
	void FlaggedStepOnTest() throws GameException {
		
		s.flag();
		try {
			s.stepOn();
			fail();
		} catch (GameException e) {
			assertEquals(e.getMessage(),"Can´t step on a closed or flagged square!");
		}
		
	}
	
	

}

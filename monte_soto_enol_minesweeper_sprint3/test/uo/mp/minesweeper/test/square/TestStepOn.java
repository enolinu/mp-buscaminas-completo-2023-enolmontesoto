/**
 * 
 */
package uo.mp.minesweeper.test.square;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	 */
	@Test
	void OpenedStepOnTest() {
		
		s.open();
		s.stepOn();
		assertTrue(s.isOpened());
		
	}
	
	/**
	 * GIVEN Una casilla cerrada.
	 * WHEN Se intenta aplicar stepOn().
	 * THEN La casilla se abre.
	 */
	@Test
	void ClosedStepOnTest() {
		
		s.stepOn();
		assertTrue(s.isOpened());
		
	}
	
	/**
	 * GIVEN Una casilla marcada con bandera.
	 * WHEN Se intenta aplicar stepOn().
	 * THEN No ocurre nada debido a que la casilla debe estar cerrada.
	 */
	@Test
	void FlaggedStepOnTest() {
		
		s.flag();
		s.stepOn();
		assertFalse(s.isOpened());
		assertTrue(s.isFlagged());
		
	}
	
	

}

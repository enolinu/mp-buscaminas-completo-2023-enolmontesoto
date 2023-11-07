package uo.mp.minesweeper.test.actions.blowupaction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.game.Board;
import uo.mp.minesweeper.game.square.actions.BlowUpAction;
/**
 * Se trata de una clase de prueba que recopila las pruebas relativas a la
 * clase BlowUpAction, en concreto a su método execute() redefinido.
 * @author enolmontesoto
 *
 */
class TestBlowExecute {
	
	BlowUpAction a;
	Board b;

	@BeforeEach
	void setUp() throws Exception {
		
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 2);
		
		a = new BlowUpAction(b);
		
	}
	
	/*
	 * Se presentan los siguientes casos de prueba:
	 * 		1 => Caso único: Explota una mina.
	 */
	
	/**
	 * GIVEN Una BlowUpAction asociada a casillas con minas.
	 * WHEN Se ejecuta la acción.
	 * THEN Explota el tablrero.
	 */
	@Test
	void BlowsUpTest() {
		
		a.execute();
		assertTrue(b.isExploded());
		
	}
	
	

}

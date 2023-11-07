package uo.mp.minesweeper.test.actions.clearaction;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.game.square.Square;
import uo.mp.minesweeper.game.square.actions.ClearAction;

/**
 * Se trata de una clase de prueba que recopila las pruebas relativas a la
 * clase ClearAction, en concreto a su método execute() redefinido.
 * @author enolmontesoto
 *
 */
class TestClearExecute {
	
	ClearAction a;
	List<Square> neighbours;

	@BeforeEach
	void setUp() throws Exception {
		
		neighbours = new ArrayList<Square>();
		
		for(int i=0; i<9; i++) {
			neighbours.add(new Square());
		}
		
		a = new ClearAction(neighbours);
		
	}
	
	/*
	 * Se presentan los siguientes casos de prueba:
	 * 		1 => Recibe una lista sin minas.
	 * 		2 => Recibe una lista solo con minas.
	 * 		3 => REcibe una lista mezclada.
	 */
	
	/**
	 * GIVEN Una ClearAction asociada a casillas sin minas.
	 * WHEN Se ejecuta la acción.
	 * THEN El valor es cero.
	 */
	@Test
	void NoMinesTest() {
		
		a.execute();
		assertEquals(0, a.getValue());
		
	}
	
	/**
	 * GIVEN Una ClearAction asociada a casillas donde todas son minas.
	 * WHEN Se ejecuta la acción.
	 * THEN El valor es la longitud de la lista.
	 */
	@Test
	void AllMinesTest() {
		
		for(Square s: neighbours) {
			s.putMine();
		}
		
		a.execute();
		assertEquals(neighbours.size(), a.getValue());
		
	}
	
	/**
	 * GIVEN Una ClearAction asociada a casillas sin minas y con minas.
	 * WHEN Se ejecuta la acción.
	 * THEN El valor es el número de casillas que tienen mina.
	 */
	@Test
	void SomeMinesTest() {
		
		neighbours.get(0).putMine();
		neighbours.get(3).putMine();
		neighbours.get(6).putMine();
		
		a.execute();
		assertEquals(3, a.getValue());
		
	}

}

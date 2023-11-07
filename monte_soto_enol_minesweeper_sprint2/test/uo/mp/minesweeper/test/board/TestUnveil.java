package uo.mp.minesweeper.test.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.game.Board;

/**
 * La siguiente clase recoge las pruebas unitarias del método unveil(),
 * el cual se ubica en la clase "Board" del modelo.
 * 
 * @author enolmontesoto
 *
 */
class TestUnveil {
	
	Board b;

	@BeforeEach
	void setUp() throws Exception {
		b = new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, Board.DEFAULT_PERCENTAGE);
	}
	
	/*
	 * Se presentan los siguientes casos de prueba:
	 * 	 1 => Tablero completamente cubierto.
	 * 	 2 => Tablero con alguna bandera.
	 * 	 3 => Tablero con alguna casilla ya descubierta.
	 */
	
	/**
	 * GIVEN Un tablero con todas las casillas cubiertas.
	 * WHEN Se destapa.
	 * THEN Todas las casillas del tablero están abiertas.
	 */
	@Test
	void CompletelyClosedTest() {
		
		b.unveil();
		
		for(int i=0; i < Board.DEFAULT_SIDE; i++) {
			for(int j=0; j < Board.DEFAULT_SIDE; j++) {
				assertTrue(b.getBoardForTesting()[i][j].isOpened());
			}
		}
	}
	
	/**
	 * GIVEN Un tablero con algunas casillas con bandera.
	 * WHEN Se destapa.
	 * THEN Todas las casillas del tablero están abiertas.
	 */
	@Test
	void PartiallyFlaggedTest() {
		
		b.flag(1, 1);
		b.flag(8, 4);
		b.unveil();
		
		for(int i=0; i < Board.DEFAULT_SIDE; i++) {
			for(int j=0; j < Board.DEFAULT_SIDE; j++) {
				assertTrue(b.getBoardForTesting()[i][j].isOpened());
			}
		}
		
	}
	
	/**
	 * GIVEN Un tablero con algunas casillas ya abiertas.
	 * WHEN Se destapa.
	 * THEN Todas las casillas del tablero están abiertas.
	 */
	@Test
	void PartiallyOpenedTest() {
		
		b.stepOn(1, 1);
		b.stepOn(8, 4);
		b.unveil();
		
		for(int i=0; i < Board.DEFAULT_SIDE; i++) {
			for(int j=0; j < Board.DEFAULT_SIDE; j++) {
				assertTrue(b.getBoardForTesting()[i][j].isOpened());
			}
		}
		
	}
	
	
	
	

}

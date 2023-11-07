package uo.mp.minesweeper.test.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.exception.InvalidLineFormatException;
import uo.mp.minesweeper.parsers.RankingParser;
import uo.mp.minesweeper.ranking.GameRankingEntry;
import uo.mp.minesweeper.session.GameLevel;

/**
 * Clase que recoge las pruebas del RankingParser
 * 
 * @author enolmontesoto
 *
 */
class ParseTest {
	
	private RankingParser p;
	private List<String> lines;
	
	private GameRankingEntry player0;
	private GameRankingEntry player1;
	private GameRankingEntry player2;
	private GameRankingEntry player3;

	@BeforeEach
	void setUp() throws Exception {
		
		p = new RankingParser();
		
		lines = new ArrayList<String>();
		player0 = new GameRankingEntry("chemari", "10-04-2023", "16:37:21", GameLevel.EASY, false, 8);
		player1 = new GameRankingEntry("chicho", "10-04-2023", "16:37:21", GameLevel.MEDIUM, false, 34);
		player2 = new GameRankingEntry("paul", "10-01-2023", "14:48:23", GameLevel.HARD, false, 55);
		player3 = new GameRankingEntry("blasa", "10-04-2023", "8:56:43", GameLevel.EASY, false, 8);
		
		
	}
	
	/*
	 * Se presentan los siguientes casos de prueba:
	 * 	1 => Lista vacía.
	 * 	2 => Lista con todos los campos correctos.
	 * 	3 => Lista con alguna linea en blanco.
	 * 	4 => Lista con falta de parámetros.
	 * 	5 => Lista con el tiempo incorrecto.
	 * 	6 => Lista con el nivel incorrecto.
	 * 	7 => Lista con el campo hasWon incorrecto.
	 * 	8 => Lista con todos los campos incorrectos.
	 */
	
	/**
	 * GIVEN Una lista vacía
	 * WHEN Se ejecuta el parser
	 * THEN Devuelve una lista vacía de entradas de ranking.
	 * @throws InvalidLineFormatException
	 */
	@Test
	void emptyListTest() throws InvalidLineFormatException {
		
		List<GameRankingEntry> entries = p.parseRanking(lines);
		assertTrue(entries.isEmpty());
		
	}
	
	/**
	 * GIVEN Una lista correcta.
	 * WHEN Se ejecuta el parser
	 * THEN Devuelve una lista de todas las entradas parseadas.
	 * @throws InvalidLineFormatException
	 */
	@Test
	void correctListTest() throws InvalidLineFormatException {
		
		lines.add("chemari;10-04-2023;16:37:21;EASY;won;8");
		lines.add("chicho;10-04-2023;16:37:21;MEDIUM;won;34");
		lines.add("paul;10-01-2023;14:48:23;HARD;won;55");
		lines.add("blasa;10-04-2023;8:56:43;EASY;won;8");
		List<GameRankingEntry> entries = p.parseRanking(lines);
		
		assertEquals(entries.size(), 4);
		assertEquals(player0, entries.get(0));
		assertEquals(player1, entries.get(1));
		assertEquals(player2, entries.get(2));
		assertEquals(player3, entries.get(3));
		
	}
	
	/**
	 * GIVEN Una entrada con lineas en blanco
	 * WHEN Se ejecuta el parser
	 * THEN El parser solo devuelve las entradas válidas.
	 * @throws InvalidLineFormatException
	 */
	@Test
	void someBlankLinesListTest() throws InvalidLineFormatException {
		
		lines.add("chemari;10-04-2023;16:37:21;EASY;won;8");
		lines.add("");
		lines.add("chicho;10-04-2023;16:37:21;MEDIUM;won;34");
		lines.add("paul;10-01-2023;14:48:23;HARD;won;55");
		lines.add("");
		lines.add("blasa;10-04-2023;8:56:43;EASY;won;8");
		List<GameRankingEntry> entries = p.parseRanking(lines);
		
		assertEquals(entries.size(), 4);
		assertEquals(player0, entries.get(0));
		assertEquals(player1, entries.get(1));
		assertEquals(player2, entries.get(2));
		assertEquals(player3, entries.get(3));
		assertFalse(entries.contains(null));
		
	}
	
	/**
	 * GIVEN Una entrada con algunas lineas con incorrecto número de parámetros.
	 * WHEN Se ejecuta el parser
	 * THEN El parser solo devuelve las entradas válidas.
	 * @throws InvalidLineFormatException
	 */
	@Test
	void notEnoughParametersListTest() throws InvalidLineFormatException {
		
		lines.add("chemari;10-04-2023;16:37:21;EASY;won");
		lines.add("chicho;10-04-2023;16:37:21;MEDIUM;won;34");
		lines.add("10-01-2023;14:48:23;HARD;won;55");
		lines.add("blasa;10-04-2023;8:56:43;EASY;won;8");
		List<GameRankingEntry> entries = p.parseRanking(lines);
		
		assertEquals(entries.size(), 2);
		assertEquals(player1, entries.get(0));
		assertEquals(player3, entries.get(1));
		assertFalse(entries.contains(null));
		
	}
	
	/**
	 * GIVEN Una lista de líneas, una de ellas con el parámetro duration incorrecto.
	 * WHEN Se ejecuta el parser
	 * THEN El parser solo devuelve las entradas válidas.
	 * @throws InvalidLineFormatException
	 */
	@Test
	void invalidTimeParametersListTest() throws InvalidLineFormatException {
		
		lines.add("chemari;10-04-2023;16:37:21;EASY;won;8");
		lines.add("chicho;10-04-2023;16:37:21;MEDIUM;won;invalid");
		List<GameRankingEntry> entries = p.parseRanking(lines);
		
		assertEquals(entries.size(), 1);
		assertEquals(player0, entries.get(0));
		assertFalse(entries.contains(null));
		assertFalse(entries.contains(player1));
		
	}
	
	/**
	 * GIVEN Una lista de líneas, una de ellas con el parámetro nivel incorrecto.
	 * WHEN Se ejecuta el parser
	 * THEN El parser solo devuelve las entradas válidas.
	 * @throws InvalidLineFormatException
	 */
	@Test
	void invalidLevelParametersListTest() throws InvalidLineFormatException {
		
		lines.add("chemari;10-04-2023;16:37:21;EASY;won;8");
		lines.add("chicho;10-04-2023;16:37:21;INVALID;won;34");
		List<GameRankingEntry> entries = p.parseRanking(lines);
		
		assertEquals(entries.size(), 1);
		assertEquals(player0, entries.get(0));
		assertFalse(entries.contains(null));
		assertFalse(entries.contains(player1));
		
	}
	
	/**
	 * GIVEN Una lista de líneas, una de ellas con el parámetro hasWon incorrecto.
	 * WHEN Se ejecuta el parser
	 * THEN El parser solo devuelve las entradas válidas.
	 * @throws InvalidLineFormatException
	 */
	@Test
	void invalidWinParametersListTest() throws InvalidLineFormatException {
		
		lines.add("chemari;10-04-2023;16:37:21;EASY;won;8");
		lines.add("chicho;10-04-2023;16:37:21;HARD;invalid;34");
		List<GameRankingEntry> entries = p.parseRanking(lines);
		
		assertEquals(entries.size(), 1);
		assertEquals(player0, entries.get(0));
		assertFalse(entries.contains(null));
		assertFalse(entries.contains(player1));
		
	}
	
	/**
	 * GIVEN Una lista de líneas, una de ellas con todos los parámetros incorrectos.
	 * WHEN Se ejecuta el parser
	 * THEN El parser solo devuelve las entradas válidas.
	 * @throws InvalidLineFormatException
	 */
	@Test
	void invalidParametersListTest() throws InvalidLineFormatException {
		
		lines.add("chemari;10-04-2023;16:37:21;EASY;won;8");
		lines.add("chicho;INVALID;INVALID;INVALID;invalid;INVALID");
		List<GameRankingEntry> entries = p.parseRanking(lines);
		
		assertEquals(entries.size(), 1);
		assertEquals(player0, entries.get(0));
		assertFalse(entries.contains(null));
		assertFalse(entries.contains(player1));
		
	}

}

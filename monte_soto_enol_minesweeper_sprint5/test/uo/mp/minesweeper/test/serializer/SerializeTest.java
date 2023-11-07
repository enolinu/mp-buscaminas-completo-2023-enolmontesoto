package uo.mp.minesweeper.test.serializer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uo.mp.minesweeper.ranking.GameRankingEntry;
import uo.mp.minesweeper.serializers.RankingSerializer;
import uo.mp.minesweeper.session.GameLevel;

class SerializeTest {
	
	private GameRankingEntry player0;
	private GameRankingEntry player1;
	private GameRankingEntry player2;
	private GameRankingEntry player3;
	
	private String line0;
	private String line1;
	private String line2;
	private String line3;

	@BeforeEach
	void setUp() throws Exception {
		
		player0 = new GameRankingEntry("chemari", "10-04-2023", "16:37:21", GameLevel.EASY, true, 8);
		player1 = new GameRankingEntry("chicho", "10-04-2023", "16:37:21", GameLevel.MEDIUM, true, 34);
		player2 = new GameRankingEntry("paul", "10-01-2023", "14:48:23", GameLevel.HARD, true, 55);
		player3 = new GameRankingEntry("blasa", "10-04-2023", "8:56:43", GameLevel.EASY, true, 8);
		
		line0 = "chemari;10-04-2023;16:37:21;EASY;won;8";
		line1 = "chicho;10-04-2023;16:37:21;MEDIUM;won;34";
		line2 = "paul;10-01-2023;14:48:23;HARD;won;55";
		line3 = "blasa;10-04-2023;8:56:43;EASY;won;8";
		
	}
	
	/*
	 * Se presentan los siguientes casos de prueba:
	 * 	1 => Lista vacía.
	 * 	2 => Lista con atributos variados.
	 */
	
	/**
	 * GIVEN Lista vacía
	 * WHEN Se ejecuta el serializador.
	 * THEN Devuelve una lista vacía.
	 */
	@Test
	void emptyListTest() {
		
		List<String> lines = RankingSerializer.serializeRanking(new ArrayList<GameRankingEntry>());
		assertTrue(lines.isEmpty());
		
	}
	
	/**
	 * GIVEN Lista mixta.
	 * WHEN Se ejecuta el serializador.
	 * THEN Devuelve una con los campos separados por ";".
	 */
	@Test
	void mixedListTest() {
		
		List<GameRankingEntry> entries = new ArrayList<>();
		entries.add(player0);
		entries.add(player1);
		entries.add(player2);
		entries.add(player3);
		List<String> lines = RankingSerializer.serializeRanking(entries);
		assertTrue(!lines.isEmpty());
		assertTrue(lines.size() == 4);
		assertEquals(line0, lines.get(0));
		assertEquals(line1, lines.get(1));
		assertEquals(line2, lines.get(2));
		assertEquals(line3, lines.get(3));
		
	}

}

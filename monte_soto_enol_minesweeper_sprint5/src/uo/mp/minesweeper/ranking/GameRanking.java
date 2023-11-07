/**
 * 
 */
package uo.mp.minesweeper.ranking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.lab.util.io.FileUtil;
import uo.mp.minesweeper.comparators.EntryComparator;
import uo.mp.minesweeper.exception.InvalidLineFormatException;
import uo.mp.minesweeper.parsers.RankingParser;
import uo.mp.minesweeper.serializers.DefaultSerializationManager;
import uo.mp.minesweeper.serializers.RankingSerializer;

/**
 * Clase de servicio que guarda el ránking del juego.
 * 
 * @author enolmontesoto
 *
 */
public class GameRanking {
	
	private String filename;
	List<GameRankingEntry> gameRankingEntries = new ArrayList<>();
	
	/**
	 * Constructor de la clase, pide un archivo para llevar a cabo la serialización.
	 * @param filename
	 */
	public GameRanking(String filename) {
		
		ArgumentChecks.checkNotNull(filename, "Filename cannot be null.");
		
		this.filename = filename;
		deserializeRanking(filename);
		
	}
	
	/**
	 * Añade una entrada al ránking.
	 * @param gameRankingEntry
	 */
	public void append(GameRankingEntry gameRankingEntry) {
		
		ArgumentChecks.checkNotNull(gameRankingEntry, "Cannot be null.");
		gameRankingEntries.add(gameRankingEntry);
	}
	
	/**
	 * Devuelve una lista con todas las entradas ordenadas.
	 * @return Lista ordenada.
	 */
	public List<GameRankingEntry> getAllEntries() {
		ArrayList<GameRankingEntry> allEntries =  new ArrayList<GameRankingEntry>(gameRankingEntries);
		Collections.sort(allEntries, new EntryComparator());
		return allEntries;
	}
	
	/**
	 * Devuelve una lista con todas las entradas de un usuario ordenadas.
	 * @return Lista ordenada.
	 */
	public List<GameRankingEntry> getEntriesForUsername (String userName) {
		ArgumentChecks.checkNotNull(userName, "Username cannot be null");
		ArgumentChecks.isTrue(!userName.trim().isEmpty());;
		
		List<GameRankingEntry> entriesByUsername = new ArrayList<>();
		
		for(GameRankingEntry e: gameRankingEntries) {
			if(e.getUser().equals(userName)) {
				entriesByUsername.add(e);
			}
		}
		
		Collections.sort(entriesByUsername, new EntryComparator());
		
		return entriesByUsername;
	}
	
	/**
	 * Guarda el contenido en un fichero.
	 * @param filename
	 */
	public void saveToFile(String filename) {
		
		// Se valida el parámetro en FileUtil
		List<String> lines = RankingSerializer.serializeRanking(new ArrayList<GameRankingEntry>(getAllEntries()));
		FileUtil.writeLines(filename, lines);
		
		
	}
	
	/**
	 * Importa el contenido de un fichero.
	 * @param filename
	 * @throws InvalidLineFormatException
	 */
	public void importFromFile(String filename) throws InvalidLineFormatException {
		
		// Se valida el parámetro en FileUtil.
		List<String> importedLines = FileUtil.readLines(filename);
		RankingParser parser = new RankingParser();
		List<GameRankingEntry> importedEntries = parser.parseRanking(importedLines);
		this.gameRankingEntries = new ArrayList<GameRankingEntry>(importedEntries);
		
	}
	
	/**
	 * Serializa el ránking, justo antes de salir de la aplicación.
	 */
	public void serializeRanking() {
		try {
			DefaultSerializationManager.serializeRanking(new ArrayList<>(gameRankingEntries), filename);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void deserializeRanking(String filename) {
		
		ArgumentChecks.checkNotNull(filename, "Filename cannot be null.");
		
		try {
			gameRankingEntries = DefaultSerializationManager.deserializeRanking(filename);
		} catch (IOException e) {
			throw new RuntimeException("FAILED TO LOAD THE RANKING: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("CLASS NOT FOUND: " + e.getMessage());
		}
		
	}
	

}

package uo.mp.minesweeper.parsers;

import java.util.ArrayList;
import java.util.List;

import uo.mp.minesweeper.exception.InvalidLineFormatException;
import uo.mp.minesweeper.ranking.GameRankingEntry;
import uo.mp.minesweeper.session.GameLevel;

/**
 * Parser del ranking.
 * 
 * @author enolmontesoto
 *
 */
public class RankingParser {
	
	private static final int NUM_FIELDS = 6;
	private int lineIdentifier = 1;
	
	/**
	 * Recibe una lista de líneas de texto y las convierte a objetos de tipo
	 * GameRankingEntry
	 * @param lines, lineas leídas de un fichero de texto.
	 * @return Lista de objetos GameRankingEntry a partir de las líneas.
	 */
	public List<GameRankingEntry> parseRanking(List<String> lines) {
		
		// En el fileutil ya se validan los parametros.
		
		List<GameRankingEntry> entries = new ArrayList<>();
	
		for(String line: lines) {
			try {
				checkIsBlank(line);
				entries.add(parseLine(line));
			} catch(InvalidLineFormatException e) {
				/*
				 * Sólo notifica el error, no lo guarda en el logger porque el logger no
				 * es estático, y el atributo está en la clase GameSession. Intenté delegar
				 * excepción en cabecera, no conseguía que siguiese "parseando" si había
				 * alguna línea inválida.
				 */
				System.err.println(e.getMessage());
			}
			
			lineIdentifier++;
		}
		
		return entries;
		
	}
	
	private GameRankingEntry parseLine(String line) throws InvalidLineFormatException {
		
		String[] tokens = line.split(";");
		
		if(tokens.length != NUM_FIELDS) {
			throw new InvalidLineFormatException(lineIdentifier, "Non valid number of fields!");
		}
		
		String user = tokens[0];
		String date = tokens[1];
		String time = tokens[2];
		GameLevel level = parseLevel(tokens[3]);
		boolean hasWon = parseWin(tokens[4]);
		long duration = parseLong(tokens[5]);
		
		return new GameRankingEntry(user, date, time, level, hasWon, duration);
		
		
	}

	private void checkIsBlank(String line) throws InvalidLineFormatException {
		
		if(line.isBlank()) {
			throw new InvalidLineFormatException(lineIdentifier, "Blank line!");
		}
		
	}
	
	private long parseLong(String string) throws InvalidLineFormatException {
		
		try {
			return Long.parseLong(string);
		} catch(NumberFormatException e) {
			throw new InvalidLineFormatException(lineIdentifier, "Non valid number format!");
		}
		
	}
	
	private boolean parseWin(String message) throws InvalidLineFormatException {
		
		if(!message.equals("won") && !message.equals("lose")) {
			throw new InvalidLineFormatException(lineIdentifier, "Non valid win identifier!");
		}
		
		return message.equals("win");
	}
	
	private GameLevel parseLevel(String level) throws InvalidLineFormatException {
		
		if(!level.equals("EASY") && !level.equals("MEDIUM")&& !level.equals("HARD")) {
			throw new InvalidLineFormatException(lineIdentifier, "Non valid level identifier!");
		}
		
		switch(level) {
		case("EASY"): return GameLevel.EASY;
		case("MEDIUM"): return GameLevel.MEDIUM;
		default: return GameLevel.HARD;
		}
		
	}

}

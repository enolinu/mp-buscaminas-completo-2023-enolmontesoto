package uo.mp.minesweeper.serializers;

import java.util.ArrayList;
import java.util.List;

import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.minesweeper.ranking.GameRankingEntry;

/**
 * Serializa los objetos, si se desea exportar en un fichero no genérico,
 * y como un fichero de texto.
 * 
 * @author enolmontesoto
 *
 */
public class RankingSerializer {
	
	/**
	 * Convierte una lista de entradas a líneas.
	 * @param entries
	 * @return lines
	 */
	public static List<String> serializeRanking(List<GameRankingEntry> entries) {
		
		ArgumentChecks.checkNotNull(entries, "Cannot be null.");
		
		List<String> lines = new ArrayList<String>();
		
		for(GameRankingEntry entry: entries) {
			lines.add(entry.serialize());
		}
		
		return lines;
	}

}

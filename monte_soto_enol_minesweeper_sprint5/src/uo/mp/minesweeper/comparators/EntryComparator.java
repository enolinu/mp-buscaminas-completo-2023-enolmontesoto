package uo.mp.minesweeper.comparators;

import java.util.Comparator;

import uo.mp.minesweeper.ranking.GameRankingEntry;
import uo.mp.minesweeper.session.GameLevel;

/**
 * Clase comparadora, compara dos entradas de ranking por nivel, tiempo y fecha.
 * 
 * @author enolmontesoto
 *
 */
public class EntryComparator implements Comparator<GameRankingEntry>{
	
	/**
	 * Método redefinido que compara las dos entradas del ránking.
	 * @param o1, la primera entrada
	 * @param o2, la segunda entrada
	 * @return 1 si o1 es mayor que o2, -1 en caso contrario, 0 si empatan.
	 */
	@Override
	public int compare(GameRankingEntry o1, GameRankingEntry o2) {
		
		int byLevel = compareByLevel(o1, o2);
		
		if(byLevel != 0) {
			return byLevel;
		}
		
		int byDuration = compareByDuration(o1, o2);
		
		if(byDuration != 0) {
			return byDuration;
		}
		
		return o1.getDate().compareTo(o2.getDate());
	}
	
	private int compareByLevel(GameRankingEntry o1, GameRankingEntry o2) {
		
		if(o1.getLevel() == o2.getLevel()) {
			return 0;
		}
		
		if((o1.getLevel() == GameLevel.EASY && o2.getLevel() == GameLevel.MEDIUM)
		   || (o1.getLevel() == GameLevel.EASY && o2.getLevel() == GameLevel.HARD)
		   || (o1.getLevel() == GameLevel.MEDIUM && o2.getLevel() == GameLevel.HARD)) {
			return 1;
		}
		
		return -1;
	}
	
	private int compareByDuration(GameRankingEntry o1, GameRankingEntry o2) {
		return ((Long)o1.getDuration()).compareTo(((Long) o2.getDuration()));
	}


}

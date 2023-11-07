/**
 * 
 */
package uo.mp.minesweeper.ranking;

import java.util.ArrayList;
import java.util.List;

import uo.mp.lab.util.check.ArgumentChecks;

/**
 * 
 * 
 * @author enolmontesoto
 *
 */
public class GameRanking {
	
	List<GameRankingEntry> gameRankingEntries = new ArrayList<>();
	
	public void append(GameRankingEntry gameRankingEntry) {
		gameRankingEntries.add(gameRankingEntry);
	}
	
	public List< GameRankingEntry > getAllEntries() {
		return new ArrayList<GameRankingEntry>(gameRankingEntries);
	}
	
	public List<GameRankingEntry> getEntriesForUsername (String userName) {
		ArgumentChecks.checkNotNull(userName, "Username cannot be null");
		ArgumentChecks.isTrue(!userName.trim().isEmpty());;
		
		List<GameRankingEntry> entriesByUsername = new ArrayList<>();
		
		for(GameRankingEntry e: gameRankingEntries) {
			if(e.getUser().equals(userName)) {
				entriesByUsername.add(e);
			}
		}
		
		return entriesByUsername;
	}
	

}

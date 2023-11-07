/**
 * 
 */
package uo.mp.minesweeper.ranking;

import java.text.SimpleDateFormat;
import java.util.Date;

import uo.mp.minesweeper.session.GameLevel;

/**
 * @author enolmontesoto
 * 
 * Clase que guarda el resultado de una partida finalizada.
 *
 */
public class GameRankingEntry {
	
	private String user;
	private long duration;
	private boolean hasWon;
	private GameLevel level;
	private String date;
	
	public GameRankingEntry(String user, long time, boolean win, GameLevel level) {
		
		this.user = user;
		this.duration = time;
		this.hasWon = win;
		this.level = level;
		this.date = getDateAndTime();
		
	}

	public String getUser() {
		return user;
	}

	public long getDuration() {
		return duration;
	}

	public boolean hasWon() {
		return hasWon;
	}

	public GameLevel getLevel() {
		return level;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(user).append("\t").append(date).append(level+"\t").append(hasWonText()+"\t").append(duration);
		return sb.toString();
		
	}
	
	public String toStringWithoutUser() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(date+"\t").append(level).append(hasWonText()).append(duration);
		return sb.toString();
		
	}
	
	private String hasWonText() {
		
		return (hasWon ? "won" : "lose");
	}

	private String getDateAndTime() {
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy\tHH:mm:ss");
		return formatter.format(date);
		
	}
	
	
	
}

/**
 * 
 */
package uo.mp.minesweeper.ranking;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.minesweeper.session.GameLevel;

/**
 * @author enolmontesoto
 * 
 * Clase que guarda el resultado de una partida finalizada.
 *
 */
public class GameRankingEntry implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String user;
	private long duration;
	private boolean hasWon;
	private GameLevel level;
	private Date fullDate;
	private String date;
	private String time;
	
	/**
	 * Constructor para el parser.
	 * @param user
	 * @param date
	 * @param time
	 * @param level
	 * @param win
	 * @param duration
	 */
	public GameRankingEntry(String user, String date, String time, GameLevel level, boolean win, long duration) {
		
		ArgumentChecks.checkNotNull(user, "Cannot be null.");
		ArgumentChecks.checkNotNull(date, "Cannot be null.");
		ArgumentChecks.checkNotNull(time, "Cannot be null.");
		ArgumentChecks.checkNotNull(level, "Cannot be null.");
		ArgumentChecks.isTrue(duration >= 0);
		
		this.user = user;
		this.date = date;
		this.time = time;
		this.level = level;
		this.hasWon = win;
		this.duration = duration;
		
	}
	
	/**
	 * Constructor que se invoca al finalizar una partida.
	 * @param user
	 * @param time
	 * @param win
	 * @param level
	 */
	public GameRankingEntry(String user, long time, boolean win, GameLevel level) {
		
		ArgumentChecks.checkNotNull(user, "Cannot be null.");
		ArgumentChecks.checkNotNull(time, "Cannot be null.");
		ArgumentChecks.checkNotNull(level, "Cannot be null.");
		ArgumentChecks.isTrue(duration >= 0);
		
		fullDate = new Date();
		this.user = user;
		this.duration = time;
		this.hasWon = win;
		this.level = level;
		setDate();
		setTime();
		
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
		sb.append(user).append("\t").append(date+"\t").append(time+" ").append(level+"\t").append(hasWonText()+"\t").append(duration);
		return sb.toString();
		
	}
	
	public String toStringWithoutUser() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(date+"\t").append(time+"\t").append(level+"\t").append(hasWonText()+"\t").append(duration);
		return sb.toString();
		
	}
	
	public Date getFullDate() {
		return fullDate;
	}
	
	private String hasWonText() {
		return (hasWon ? "won" : "lose");
	}

	private void setDate() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		date = formatter.format(fullDate);
		
	}
	
	private void setTime() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		time =  formatter.format(fullDate);
		
	}
	
	public String serialize() {
		return user+";"+date+";"+time+";"+level+";"+hasWonText()+";"+duration;
	}

	public String getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, duration, fullDate, hasWon, level, time, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameRankingEntry other = (GameRankingEntry) obj;
		return Objects.equals(date, other.date) && duration == other.duration
				&& hasWon == other.hasWon && level == other.level
				&& Objects.equals(time, other.time) && Objects.equals(user, other.user);
	}
	
	
	
}

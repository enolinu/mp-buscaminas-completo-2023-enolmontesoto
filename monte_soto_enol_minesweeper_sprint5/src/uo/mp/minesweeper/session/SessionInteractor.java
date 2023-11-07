/**
 * 
 */
package uo.mp.minesweeper.session;

import java.util.List;

import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.exception.UserInteractionException;
import uo.mp.minesweeper.ranking.GameRankingEntry;

/**
 * 
 * Interfaz que debe ser implemetada por la UI de la sesión.
 * 
 * @author enolmontesoto
 *
 */
public interface SessionInteractor {
	
	GameLevel askGameLevel() throws UserInteractionException;
	
	String askUserName() throws GameException;
	
	int askNextOption() throws UserInteractionException;
	
	boolean doYouWantToRegisterYourScore() throws UserInteractionException;
	
	void showRanking(List<GameRankingEntry> ranking);
	
	void showPersonalRanking(List<GameRankingEntry> ranking);
	
	void showGoodBye();
	
	void showErrorMessage(String message);
	
	void showFatalErrorMessage(String message);
	
	String askFileName();
	
	

}

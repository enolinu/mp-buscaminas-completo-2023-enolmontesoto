/**
 * 
 */
package uo.mp.minesweeper.session;

import uo.mp.lab.util.logger.SimpleLogger;
import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.exception.UserInteractionException;
import uo.mp.minesweeper.game.Board;
import uo.mp.minesweeper.game.Game;
import uo.mp.minesweeper.game.GameInteractor;
import uo.mp.minesweeper.ranking.GameRanking;
import uo.mp.minesweeper.ranking.GameRankingEntry;

/**
 * @author enolmontesoto
 *
 */
public class GameSession {
	
	private static final int EXIT = 0;
	
	private GameInteractor gameInteractor;
	private SessionInteractor sessionInteractor;
	private GameRanking ranking;
	private SimpleLogger logger;
	
	String user;
	
	public void run() {
		
			int option = -1;
			
			try {
				
				this.user = sessionInteractor.askUserName();
				
				do {
					
					try {
						option = sessionInteractor.askNextOption(); 
						evaluateOption(option);
					} catch(RuntimeException e) {
						runtimeExceptionManager(e);
					} catch(UserInteractionException | GameException e) {
						userOrGameExceptionManager(e);
					}
					
			} while(option != EXIT);	
			
			} catch(GameException e) {
				sessionInteractor.showErrorMessage("USERNAME NOT VALID: " + e.getMessage());
				run();
			}
			sessionInteractor.showGoodBye();
			
	}
	
	public void setGameInteractor(GameInteractor gameInteractor) {
		this.gameInteractor = gameInteractor;
	}
	public void setSessionInteractor(SessionInteractor sessionInteractor) {
		this.sessionInteractor = sessionInteractor;
	}
	public void setGameRanking(GameRanking ranking) {
		this.ranking = ranking;
	}
	public void setLogger(SimpleLogger logger) {
		this.logger = logger;
	}
	
	private void evaluateOption(int option) throws UserInteractionException, GameException {
		
		switch(option) {
		case 1: processPlayOption(); break;
		case 2: processMyResults(); break;
		case 3: processAllResults(); break;
		}
		
	}

	private void processAllResults() {
		this.sessionInteractor.showRanking(ranking.getAllEntries());
	}

	private void processMyResults() {
		this.sessionInteractor.showPersonalRanking(ranking.getEntriesForUsername(user));
	}

	private void processPlayOption() throws UserInteractionException, GameException {
		
		GameLevel level = sessionInteractor.askGameLevel();
		
		Game game = new Game(new Board(9, 9, 12));
		
		switch(level) {
		case EASY: game = new Game(new Board(9, 9, 12)); break;
		case MEDIUM: game = new Game(new Board(16, 16, 15)); break;
		case HARD: game = new Game(new Board(30, 16, 20)); break;
		}
		
		game.setInteractor(gameInteractor);
		game.play();
		
		if(sessionInteractor.doYouWantToRegisterYourScore()) {
			
			if(isInRanking()) {
				throw new GameException("The user is already in the ranking, cant save the result!.");
			}
			
			ranking.append(new GameRankingEntry(user, game.getTime(), game.hasWon(), level));
		}
		
	}
	
	private void runtimeExceptionManager(RuntimeException e) {
		sessionInteractor.showFatalErrorMessage("FATAL ERROR: " + e);
		logger.log(e);
		System.exit(0);
	}
	
	private void userOrGameExceptionManager(Exception e) {
		sessionInteractor.showErrorMessage("OPTION NOT VALID: " + e);
	}
	
	private boolean isInRanking() {
		for(GameRankingEntry e: ranking.getAllEntries()) {
			if(e.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}

}

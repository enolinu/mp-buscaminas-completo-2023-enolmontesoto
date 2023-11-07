/**
 * 
 */
package uo.mp.minesweeper.session;

import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.lab.util.logger.SimpleLogger;
import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.exception.InvalidLineFormatException;
import uo.mp.minesweeper.exception.UserInteractionException;
import uo.mp.minesweeper.game.Board;
import uo.mp.minesweeper.game.Game;
import uo.mp.minesweeper.game.GameInteractor;
import uo.mp.minesweeper.ranking.GameRanking;
import uo.mp.minesweeper.ranking.GameRankingEntry;

/**
 * 
 * Clase que gestiona una sesión de usuario.
 * 
 * @author enolmontesoto
 *
 */
public class GameSession {
	
	private static final int EXIT = 0;
	
	private GameInteractor gameInteractor;
	private SessionInteractor sessionInteractor;
	private GameRanking ranking;
	private SimpleLogger logger;
	
	private String user;
	
	/**
	 * Método que contiene el bucle del juego, pide una opción, ejecuta la que
	 * se pide y sigue pidiendo hasta que la opción sea cero. Aquí es donde 
	 * se manejan la mayor parte de las excepciones.
	 */
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
						interactionOrGameExceptionManager(e);
					} catch(InvalidLineFormatException e) {
						lineFormatExceptionManager(e);
					}
					
			} while(option != EXIT);	
			
			} catch(GameException e) {
				invalidUsernameExceptionManager(e);
			}
			sessionInteractor.showGoodBye();
			ranking.serializeRanking();
			
	}
	
	/**
	 * Setter del gameInteractor
	 * @param gameInteractor
	 */
	public void setGameInteractor(GameInteractor gameInteractor) {
		ArgumentChecks.checkNotNull(gameInteractor, "Cannot be null.");
		this.gameInteractor = gameInteractor;
	}
	
	/**
	 * Setter del session interactor
	 * @param gameInteractor
	 */
	public void setSessionInteractor(SessionInteractor sessionInteractor) {
		ArgumentChecks.checkNotNull(sessionInteractor, "Cannot be null.");
		this.sessionInteractor = sessionInteractor;
	}
	
	/**
	 * Setter del ranking
	 * @param gameInteractor
	 */
	public void setGameRanking(GameRanking ranking) {
		ArgumentChecks.checkNotNull(ranking, "Cannot be null.");
		this.ranking = ranking;
	}
	
	/**
	 * Setter del logger
	 * @param gameInteractor
	 */
	public void setLogger(SimpleLogger logger) {
		ArgumentChecks.checkNotNull(logger, "Cannot be null.");
		this.logger = logger;
	}
	
	private void evaluateOption(int option) throws UserInteractionException, GameException, InvalidLineFormatException   {
		
		switch(option) {
		case 1: processPlayOption(); break;
		case 2: processMyResults(); break;
		case 3: processAllResults(); break;
		case 4: processExportOperation(); break;
		case 5: processImportOperation(); break;
		}
		
	}

	private void processExportOperation() {
		
		String filename = sessionInteractor.askFileName();
		ranking.saveToFile(filename);
		
	}

	private void processImportOperation() throws InvalidLineFormatException {
		
		String filename = sessionInteractor.askFileName();
		ranking.importFromFile(filename);
		
	}

	private void processAllResults() {
		this.sessionInteractor.showRanking(ranking.getAllEntries());
	}

	private void processMyResults() {
		this.sessionInteractor.showPersonalRanking(ranking.getEntriesForUsername(user));
	}

	// Procesa opción del juego.
	
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
		
		if(game.hasWon()) {
			if(sessionInteractor.doYouWantToRegisterYourScore()) {
				
				ranking.append(new GameRankingEntry(user, game.getTime(), game.hasWon(), level));
			}
		}
		
	}
	
	// Manejadores de excepciones.
	
	private void runtimeExceptionManager(RuntimeException e) {
		sessionInteractor.showFatalErrorMessage("FATAL ERROR: " + e);
		logger.log(e);
		System.exit(0);
	}
	
	private void interactionOrGameExceptionManager(Exception e) {
		sessionInteractor.showErrorMessage("OPTION NOT VALID: " + e);
		logger.log(e);
	}
	
	private void invalidUsernameExceptionManager(GameException e) {
		sessionInteractor.showErrorMessage("USERNAME NOT VALID: " + e.getMessage());
		logger.log(e);
		run();
	}
	
	private void lineFormatExceptionManager(InvalidLineFormatException e) {
		sessionInteractor.showErrorMessage("ERROR IN LINE: " + e);
		logger.log(e);
	}
	
	

}

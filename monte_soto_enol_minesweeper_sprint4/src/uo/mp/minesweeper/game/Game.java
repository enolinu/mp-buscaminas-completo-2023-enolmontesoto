package uo.mp.minesweeper.game;

import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.exception.UserInteractionException;
import uo.mp.minesweeper.session.GameLevel;

/**
 * Clase que gestiona la entrada y salida del juego.
 * 
 * @author enolmontesoto
 *
 */
public class Game {
	
	private Board board;
	private GameInteractor interactor;
	private long time;
	private GameMove gm;
	
	/**
	 * Constructor del juego, inicializa el tablero.
	 * @param board
	 */
	public Game(Board board) {
		
		ArgumentChecks.checkNotNull(board, "The board cannot be null!");
		this.board = board;
		time = System.currentTimeMillis(); 
		
	}
	
	public Game(GameLevel level) throws GameException {
	
		switch(level) {
		case EASY: this.board = new Board(9, 9, 12); break;
		case MEDIUM: this.board = new Board(16, 16, 15); break;
		case HARD: this.board = new Board(30, 16, 20); break;
		}
		
		time = System.currentTimeMillis();
		
	}

	/**
	 * Ejecuta el bucle principal del juego.
	 * @throws GameException 
	 * @throws UserInteractionException 
	 */
	public void play() throws GameException, UserInteractionException {
		
		interactor.showReadyToStart();
		interactor.showGame(0, board);
		gameLoop();
		
	}
	
	/**
	 * Le asigna un objeto interactor al juego, será diferente en función del
	 * tipo de interfaz de usuario que se implemente.
	 * @param interactor
	 */
	public void setInteractor(GameInteractor interactor) {
		this.interactor = interactor;
	}
	
	public boolean hasWon() {
		return board.hasWon();
	}
	
	// Lee y ejecuta la acción leída en las coordenadas leídas.
	
	private void executeAction(char action, int xCoord, int yCoord) throws GameException {
		
		if(action == 's') {
			board.stepOn(xCoord, yCoord);
		}
		
		else if(action == 'f') {
			board.flag(xCoord, yCoord);
		}
		
		else if(action == 'u') {
			board.unflag(xCoord, yCoord);
		}
		
		
	}
	
	// Imprime los mensajes con la información en cada ronda.
	
	private void roundLoop() throws GameException, UserInteractionException {
		
		gm = interactor.askMove(board.getNumberOfRows(), board.getNumberOfColumns());
		executeAction(gm.getOperation(), gm.getRow(), gm.getColumn());
		interactor.showGame(getTime(), board);
		
		
	}
	
	// Bucle principal del juego.
	
	private void gameLoop() throws GameException, UserInteractionException {
		
		while(!board.isExploded() && board.getNumberOfMinesLeft() > 0) {
			
			roundLoop();		
			
		}
		
		if(board.getNumberOfMinesLeft() <= 0) {
			
			interactor.showGameFinished();
			interactor.showCongratulations(0);
			return;
			
		}
		
		interactor.showGameFinished();
		interactor.showBooommm();
		
	}
	
	public long getTime() {
		long nowMillis = System.currentTimeMillis();
        return ((nowMillis - this.time) / 1000);
	}
	
	

}

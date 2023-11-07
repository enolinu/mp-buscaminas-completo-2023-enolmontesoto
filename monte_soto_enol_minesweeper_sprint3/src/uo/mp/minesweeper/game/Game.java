package uo.mp.minesweeper.game;

import uo.mp.lab.util.check.ArgumentChecks;

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
	
	/**
	 * Ejecuta el bucle principal del juego.
	 */
	public void play() {
		
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
	
	// Lee y ejecuta la acción leída en las coordenadas leídas.
	
	private void executeAction(char action, int xCoord, int yCoord) {
		
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
	
	private void roundLoop() {
		
		gm = interactor.askMove(board.getNumberOfRows(), board.getNumberOfColumns());
		executeAction(gm.getOperation(), gm.getRow(), gm.getColumn());
		interactor.showGame(getTime(), board);
		
		
	}
	
	// Bucle principal del juego.
	
	private void gameLoop() {
		
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
	
	private long getTime() {
		long nowMillis = System.currentTimeMillis();
        return ((nowMillis - this.time) / 1000);
	}
	
	

}

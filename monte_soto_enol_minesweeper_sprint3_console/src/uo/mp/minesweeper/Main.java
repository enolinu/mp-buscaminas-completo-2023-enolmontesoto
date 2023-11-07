package uo.mp.minesweeper;

import uo.mp.minesweeper.console.ConsoleGameInteractor;
import uo.mp.minesweeper.game.Board;
import uo.mp.minesweeper.game.Game;

public class Main {

	private Game game;

	public static void main(String[] args) { 
		new Main()
			.configure()
			.run();
	}

	private Main configure() {
		Board board = new Board(9, 9, 12 /* % */);
		game = new Game( board );
		// Define this method in the Game class
		//    public void setInteractor ( GameInteractor  interactor ) 
		// GameInteractor is an interface and it must be in uo.mp.minesweeper.game in sprint3 project 
		game.setInteractor( new ConsoleGameInteractor() );
		return this;
	}

	private void run() {
		game.play();
	}

}

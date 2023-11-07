package uo.mp.minesweeper;

import uo.mp.minesweeper.game.Board;
import uo.mp.minesweeper.game.Game;

public class Main {
	
	public static void main(String[] args) {
		new Game(new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, Board.DEFAULT_PERCENTAGE)).play();
		//new Game(new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0)).play();
	}

}

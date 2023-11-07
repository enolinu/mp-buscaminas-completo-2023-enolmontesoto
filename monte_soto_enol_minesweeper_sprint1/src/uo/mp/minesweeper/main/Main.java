package uo.mp.minesweeper.main;

import uo.mp.minesweeper.model.Board;
import uo.mp.minesweeper.service.Game;

public class Main {
	
	public static void main(String[] args) {
		new Game(new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, Board.DEFAULT_PERCENTAGE)).play();
		//new Game(new Board(Board.DEFAULT_SIDE, Board.DEFAULT_SIDE, 0)).play();
	}

}

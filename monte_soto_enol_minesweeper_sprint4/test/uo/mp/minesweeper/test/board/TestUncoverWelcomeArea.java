//package uo.mp.minesweeper.test.board;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import uo.mp.minesweeper.game.Board;
//import uo.mp.minesweeper.game.square.Square;
//
//class TestUncoverWelcomeArea {
//	
//	Board b;
//
//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@Test
//	void FirstTest() {
//		Square[][] squares = new Square[3][3];
//		squares[0][0] = new Square();
//		squares[0][1] = new Square();
//		squares[0][2] = new Square();
//		squares[1][0] = new Square();
//		squares[1][1] = new Square();
//		squares[1][2] = new Square();
//		squares[2][0] = new Square();
//		squares[2][1] = new Square();
//		squares[2][2] = new Square();
//		squares[0][0].putMine();
//		
//		b = new Board(0, squares);
//		assertFalse(b.getBoardForTesting()[0][0].isOpened());
//		assertTrue(b.getBoardForTesting()[0][2].isOpened());
//		assertTrue(b.getBoardForTesting()[1][2].isOpened());
//		assertTrue(b.getBoardForTesting()[2][2].isOpened());
//		
//	}
//	
//	@Test
//	void SecondTest() {
//		
//		Square[][] squares = new Square[3][6];
//		squares[0][0] = new Square();
//		squares[0][1] = new Square();
//		squares[0][2] = new Square();
//		squares[1][0] = new Square();
//		squares[1][1] = new Square();
//		squares[1][2] = new Square();
//		squares[2][0] = new Square();
//		squares[2][1] = new Square();
//		
//		squares[0][3] = new Square();
//		squares[0][4] = new Square();
//		squares[0][5] = new Square();
//		squares[1][3] = new Square();
//		squares[1][4] = new Square();
//		squares[1][5] = new Square();
//		squares[2][3] = new Square();
//		squares[2][4] = new Square();
//		squares[2][5] = new Square();
//		
//		squares[0][0].putMine();
//		squares[0][4].putMine();
//		squares[1][4].putMine();
//		squares[2][4].putMine();
//		
//		b = new Board(0, squares);
//		assertFalse(b.getBoardForTesting()[0][0].isOpened());
//		assertTrue(b.getBoardForTesting()[0][2].isOpened());
//		assertTrue(b.getBoardForTesting()[1][2].isOpened());
//		assertTrue(b.getBoardForTesting()[2][2].isOpened());
//		assertTrue(b.getBoardForTesting()[0][3].isOpened());
//		assertTrue(b.getBoardForTesting()[1][3].isOpened());
//		assertTrue(b.getBoardForTesting()[2][3].isOpened());
//		
//	}
//
//}


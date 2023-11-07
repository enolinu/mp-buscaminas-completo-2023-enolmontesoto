package uo.mp.minesweeper.model;

import java.util.Random;

import uo.mp.lab.util.check.ArgumentChecks;

/**
 * Clase que crea y controa el funcionamiento del tablero de juego.
 * 
 * @author enolmontesoto
 *
 */
public class Board {
	
	/**
	 * Constante que indica el lado por defecto del tablero.
	 */
	public static final int DEFAULT_SIDE = 9;
	
	/**
	 * Constante que indica el porcentaje de minas por defecto del tablero.
	 */
	public static final int DEFAULT_PERCENTAGE = 12;
	
	// Atributos.
	
	private Square[][] board;
	private int numberOfMines;
	private int numberOfMinesLeft;
	private int numberOfFlagsLeft;
	private boolean exploded;
	
	/**
	 * Constructor, con parámetros ancho, alto y porcentaje de minas.
	 * @param width
	 * @param height
	 * @param percentage
	 */
	public Board(int width, int height, int percentage) {
		
		validateFields(width, height, percentage);
		board = new Square[width][height];
		fillBoard();
		setExploded(false);
		setUpMines(percentage);
		
		
	}
	
	/**
	 * COnstructor, con parámetros número de minas y matriz de tablero.
	 * @param mines
	 * @param squares
	 */
	public Board(int mines, Square[][] squares) {
		
		ArgumentChecks.isTrue(mines >= 0 && mines <= (squares.length * squares[0].length));
		
		setExploded(false);
		setNumberOfMines(obtainNumberOfMines(mines));
		setNumberOfFlagsLeft(mines);
		setNumberOfMinesLeft(mines);
		board = copyBoard(squares);
		placeMines(mines);
		
	}
	
	/**
	 * @return numberOfFlagsLeft
	 */
	public int getNumberOfFlagsLeft() {
		return numberOfFlagsLeft;
	}

	private void setNumberOfFlagsLeft(int numberOfFlagsLeft) {
		ArgumentChecks.isTrue(numberOfFlagsLeft >= 0);
		this.numberOfFlagsLeft = numberOfFlagsLeft;
	}

	/**
	 * @return numberOfMinesLeft
	 */
	public int getNumberOfMinesLeft() {
		return numberOfMinesLeft;
	}

	private void setNumberOfMinesLeft(int numberOfMinesLeft) {
		ArgumentChecks.isTrue(numberOfMinesLeft >= 0);
		this.numberOfMinesLeft = numberOfMinesLeft;
	}

	/**
	 * @return numberOfMines
	 */
	public int getNumberOfMines() {
		return numberOfMines;
	}

	private void setNumberOfMines(int numberOfMines) {
		// Se verifica el argumento en el constructor.
		this.numberOfMines = numberOfMines;
	}

	/**
	 * @return exploded
	 */
	public boolean isExploded() {
		return exploded;
	}

	private void setExploded(boolean exploded) {
		this.exploded = exploded;
	}
	
	/**
	 * Abre casilla ubicada en las coordenadas que se le pase por parámetro.
	 * @param x
	 * @param y
	 */
	public void stepOn(int x, int y) {
		
		ArgumentChecks.isTrue(x >= 0 && x < board.length, "The square does not exist.");
		ArgumentChecks.isTrue(y >= 0 && x < board[0].length, "The square does not exist.");
		
		if(!board[x][y].hasMine()) {
			
			if(x == 0 && y == 0) showValueUpperLeftCorner();
			else if(x == 0 && y == board[0].length-1) showValueUpperRightCorner();
			else if(x == board.length-1 && y == 0) showValueDownLeftCorner();
			else if(x == board.length-1 && y == board[0].length-1) showValueDownRightCorner();
			else if(y == 0) showValueUpperSide(x, y);
			else if(y == board.length-1) showValueDownSide(x, y);
			else if(x == 0) showValueLeftSide(x, y);
			else if(x == board[0].length-1) showValueRightSide(x, y);
			else showValue(x, y);
			
			board[x][y].stepOn();
			
		} else if(board[x][y].hasMine() && !board[x][y].isFlagged()) {
			unveil();
			markAsExploded();
		} 
		
	}
	
	/**
	 * Pone una bandera en las coordenadas que se le pase por parámetro.
	 * @param x
	 * @param y
	 */
	public void flag(int x, int y) {
		
		ArgumentChecks.isTrue(x >= 0 && x < board.length, "The square does not exist.");
		ArgumentChecks.isTrue(y >= 0 && x < board[0].length, "The square does not exist.");
		
		if(!(board[x][y].isFlagged()) && !(board[x][y].isOpened())) {
			board[x][y].flag();
			
			if(numberOfFlagsLeft > 0) numberOfFlagsLeft--;
			
			if(board[x][y].hasMine()) numberOfMinesLeft--;
		}
		
		
	}
	
	/**
	 * Quita una bandera en las coordenadas que se le pase por parámetro.
	 * @param x
	 * @param y
	 */
	public void unflag(int x, int y) {
		
		ArgumentChecks.isTrue(x >= 0 && x < board.length, "The square does not exist.");
		ArgumentChecks.isTrue(y >= 0 && x < board[0].length, "The square does not exist.");
		
		if(board[x][y].isFlagged() && !(board[x][y].isOpened())) {
			board[x][y].unflag();
			numberOfFlagsLeft++;
			
			if(board[x][y].hasMine()) numberOfMinesLeft++;
		}
		
	}
	
	/**
	 * Copia del tablero para las pruebas.
	 * @return board (copia)
	 */
	public Square[][] getBoardForTesting() {
		return copyBoard(this.board);
	}
	
	/**
	 * Redefinición del método toString()
	 * @return CAdena representativa del tablero.
	 */
	@Override
	public String toString() {
		
		int width = board.length;
		int height = board[0].length;
		char[][] stateArray = getState();
		StringBuilder sb = new StringBuilder();
		
		for(int row=0; row<width; row++) {
			
			sb.append("\n");
			for(int col=0; col<height; col++) {
				sb.append(stateArray[row][col]).append(" ");
			}
			
		}
		
		return sb.toString();
		
	}
	
	/**
	 * Muestra el estado del tablero con un array bidimensional de caracteres.
	 * @return Array bidimensional de caracteres, representando al tablero.
	 */
	public char[][] getState() {
		
		int width = board.length;
		int height = board[0].length;
		char[][] stateArray = new char[width][height];
		
		for(int row=0; row<width; row++) {
			for(int col=0; col<height; col++) {
				stateArray[row][col] = board[row][col].toChar();
			}
			
		}
		
		return stateArray;
		
	}
	
	// Destapa el tablero (originalmente private, public para las pruebas).

	public void unveil() {
		
		int width = board.length;
		int height = board[0].length;
				
		for(int row=0; row<width; row++) {
			for(int col=0; col<height; col++) {
				board[row][col].open();
			}
		}
		
	}
	
	// Marca el tablero como "explotado".
	
	private void markAsExploded() {
		
		if(hasExploded()) {
			setExploded(true);
		}
		
	}
	
	// Muestra el valor de la pista numérica de una casilla.
	
	private void showValue(int x, int y) {
		
		ArgumentChecks.isTrue(x >= 0 && x < board.length, "The square does not exist.");
		ArgumentChecks.isTrue(y >= 0 && x < board[0].length, "The square does not exist.");
		
		int counter = 0;
		
		if(board[x-1][y-1].hasMine()) counter++;
		if(board[x+1][y+1].hasMine()) counter++;
		if(board[x+1][y-1].hasMine()) counter++;
		if(board[x-1][y+1].hasMine()) counter++;
		if(board[x][y-1].hasMine()) counter++;
		if(board[x][y+1].hasMine()) counter++;
		if(board[x-1][y].hasMine()) counter++;
		if(board[x+1][y].hasMine()) counter++;
		
		board[x][y].setValue(counter);
		
	}
	
	private void showValueUpperLeftCorner() {
		
		int counter = 0;
		
		if(board[1][0].hasMine()) counter++;
		if(board[0][1].hasMine()) counter++;
		if(board[1][1].hasMine()) counter++;
		
		board[0][0].setValue(counter);
	}
	
	private void showValueUpperRightCorner() {
		
		int counter = 0;
		int col = board[0].length-1;
		
		if(board[0][col-1].hasMine()) counter++;
		if(board[1][col-1].hasMine()) counter++;
		if(board[1][col].hasMine()) counter++;
		
		board[0][col].setValue(counter);
	}
	
	private void showValueDownLeftCorner() {
		
		int counter = 0;
		int row = board.length-1;
		
		if(board[row-1][0].hasMine()) counter++;
		if(board[row-1][1].hasMine()) counter++;
		if(board[row][1].hasMine()) counter++;
		
		board[row][0].setValue(counter);
		
	}
	
	private void showValueDownRightCorner() {
		
		int counter = 0;
		int row = board.length-1;
		int col = board[0].length-1;
		
		if(board[row-1][col-1].hasMine()) counter++;
		if(board[row-1][col].hasMine()) counter++;
		if(board[row][col-1].hasMine()) counter++;
		
		board[row][col].setValue(counter);
		
	}
	
	private void showValueUpperSide(int x, int y) {
		
		ArgumentChecks.isTrue(x >= 0 && x < board.length, "The square does not exist.");
		ArgumentChecks.isTrue(y >= 0 && x < board[0].length, "The square does not exist.");
		
		int counter = 0;
		
		if(board[x+1][y+1].hasMine()) counter++;
		if(board[x-1][y+1].hasMine()) counter++;
		if(board[x][y+1].hasMine()) counter++;
		if(board[x-1][y].hasMine()) counter++;
		if(board[x+1][y].hasMine()) counter++;
		
		board[x][y].setValue(counter);
		
	}
	
	
	private void showValueDownSide(int x, int y) {
		
		ArgumentChecks.isTrue(x >= 0 && x < board.length, "The square does not exist.");
		ArgumentChecks.isTrue(y >= 0 && x < board[0].length, "The square does not exist.");
		
		int counter = 0;
		
		if(board[x-1][y-1].hasMine()) counter++;
		if(board[x+1][y-1].hasMine()) counter++;
		if(board[x][y-1].hasMine()) counter++;
		if(board[x-1][y].hasMine()) counter++;
		if(board[x+1][y].hasMine()) counter++;
		
		board[x][y].setValue(counter);
		
	}
	
	private void showValueLeftSide(int x, int y) {
		
		ArgumentChecks.isTrue(x >= 0 && x < board.length, "The square does not exist.");
		ArgumentChecks.isTrue(y >= 0 && x < board[0].length, "The square does not exist.");
		
		int counter = 0;
		
		if(board[x+1][y+1].hasMine()) counter++;
		if(board[x+1][y-1].hasMine()) counter++;
		if(board[x][y-1].hasMine()) counter++;
		if(board[x][y+1].hasMine()) counter++;
		if(board[x+1][y].hasMine()) counter++;
		
		board[x][y].setValue(counter);
		
	}
	
	private void showValueRightSide(int x, int y) {
		
		ArgumentChecks.isTrue(x >= 0 && x < board.length, "The square does not exist.");
		ArgumentChecks.isTrue(y >= 0 && x < board[0].length, "The square does not exist.");
		
		int counter = 0;
		
		if(board[x-1][y-1].hasMine()) counter++;
		if(board[x-1][y+1].hasMine()) counter++;
		if(board[x][y-1].hasMine()) counter++;
		if(board[x][y+1].hasMine()) counter++;
		if(board[x-1][y].hasMine()) counter++;
		
		board[x][y].setValue(counter);
		
	}
	
	// Copia un array bidimensional (auxiliar).
	
	private Square[][] copyBoard(Square[][] board) {
		
		int width = board.length;
		int height = board[0].length;
		
		Square[][] aux = new Square[width][height];
		
		for(int row=0; row<width; row++) {
			for(int col=0; col<height; col++) {
				aux[row][col] = board[row][col];
			}
		}
		
		return aux;
	}
	
	// Muestra el estado del tablero con un array bidimensional de caracteres.
	
	
	
	// Validación de parámetros del constructor.
	
	private void validateFields(int width, int height, int percentage) {
		
		ArgumentChecks.isTrue(width >= 2);
		ArgumentChecks.isTrue(height >= 2);
		ArgumentChecks.isTrue(percentage >= 0 && percentage <= 100);
		
	}
	
	private int obtainNumberOfMines(int percentage) {
		
		ArgumentChecks.isTrue(percentage >= 0 && percentage <= 100);
		
		int numberOfSquares = board.length * board[0].length;
		int numberOfMinesToPlace = (numberOfSquares*percentage)/100;
		return numberOfMinesToPlace;
		
	}
	
	// Los siguientes métodos crean y colocan las minas.
	
	private void setUpMines(int percentage) {
		
		ArgumentChecks.isTrue(percentage >= 0 && percentage <= 100);
		
		int mines = obtainNumberOfMines(percentage);
		setNumberOfMines(mines);
		setNumberOfFlagsLeft(mines);
		setNumberOfMinesLeft(mines);
		placeMines(mines);
		
	}

	private void placeMine() {
		
		Random r = new Random();
		int xPos, yPos;
		
		do {
			xPos = r.nextInt(board.length);
			yPos = r.nextInt(board[0].length);
		} while(board[xPos][yPos].hasMine());
		
		board[xPos][yPos].putMine();
	
	}
	
	private void placeMines(int minesToPlace) {
		
		while(minesToPlace > 0) {
			placeMine();
			minesToPlace--;
		}
		
	}
	
	// Rellena el tablero de objetos de tipo "Square".
	
	private void fillBoard() {
		
		int width = board.length;
		int height = board[0].length;
		
		
		for(int row=0; row<width; row++) {
			for(int col=0; col<height; col++) {
				board[row][col] = new Square();
			}
		}
	}
	
	// Devuelve "true" si el tablero ha explotado.

	private boolean hasExploded() {
		
		int width = board.length;
		int height = board[0].length;
		
		
		for(int row=0; row<width; row++) {
			for(int col=0; col<height; col++) {
				if(board[row][col].hasMine() && board[row][col].isOpened()) {
					return true;
				}
			}
		}
		
		return false;
		
	}

}

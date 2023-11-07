package uo.mp.minesweeper.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.minesweeper.game.square.Square;
import uo.mp.minesweeper.game.square.actions.BlowUpAction;
import uo.mp.minesweeper.game.square.actions.ClearAction;
import uo.mp.minesweeper.game.square.actions.Executable;
import uo.mp.minesweeper.game.square.actions.NullAction;

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
	private int rows;
	private int cols;
	
	/**
	 * Constructor, con parámetros ancho, alto y porcentaje de minas.
	 * @param width
	 * @param height
	 * @param percentage
	 */
	public Board(int width, int height, int percentage) {
		
		validateFields(width, height, percentage);
		board = new Square[width][height];
		setNumberOfRows(width);
		setNumberOfColumns(height);
		fillBoard();
		setExploded(false);
		setUpMines(percentage);
		associateActions();
		uncoverWelcomeArea();
		
		
	}
	
	/*
	 * Auxiliar para pruebas!!!! Para que no de bule infinito cuando solo hay minas.
	 * No descubre el area de bienvenida
	 */
	public Board(int width, int height, int percentage, boolean test) {
		
		validateFields(width, height, percentage);
		board = new Square[width][height];
		setNumberOfRows(width);
		setNumberOfColumns(height);
		fillBoard();
		setExploded(false);
		setUpMines(percentage);
		associateActions();
		
		
	}
	
	/**
	 * COnstructor, con parámetros número de minas y matriz de tablero.
	 * @param mines
	 * @param squares
	 */
	public Board(int mines, Square[][] squares) {
		
		ArgumentChecks.isTrue(mines >= 0 && mines < (squares.length * squares[0].length));
		
		board = copyBoard(squares);
		setExploded(false);
		setNumberOfMines(obtainNumberOfMines(mines));
		setNumberOfFlagsLeft(mines);
		setNumberOfMinesLeft(mines);
		//placeMines(mines);
		uncoverWelcomeArea();
		
	}
	
	/**
	 * @return numberOfFlagsLeft
	 */
	public int getNumberOfFlagsLeft() {
		return numberOfFlagsLeft;
	}

	private void setNumberOfFlagsLeft(int numberOfFlagsLeft) {
		this.numberOfFlagsLeft = numberOfFlagsLeft;
	}

	/**
	 * @return numberOfMinesLeft
	 */
	public int getNumberOfMinesLeft() {
		return numberOfMinesLeft;
	}

	private void setNumberOfMinesLeft(int numberOfMinesLeft) {
		this.numberOfMinesLeft = numberOfMinesLeft;
	}

	/**
	 * @return numberOfMines
	 */
	public int getNumberOfMines() {
		return numberOfMines;
	}

	private void setNumberOfMines(int numberOfMines) {
		this.numberOfMines = numberOfMines;
	}

	/**
	 * @return exploded
	 */
	public boolean isExploded() {
		return exploded;
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}
	
	public int getNumberOfRows() {
		return rows;
	}

	public void setNumberOfRows(int rows) {
		this.rows = rows;
	}

	public int getNumberOfColumns() {
		return cols;
	}

	public void setNumberOfColumns(int cols) {
		this.cols = cols;
	}

	/**
	 * Executa la Action correspondiente en función del tipo de casilla.
	 * @param x
	 * @param y
	 */
	public void stepOn(int x, int y) {
		
		if(board[x][y].isOpened()) {
			board[x][y].getAction().execute();
			return;
		}
		
		if(board[x][y].hasMine() && !board[x][y].isOpened() && !board[x][y].isFlagged()) {
			board[x][y].getAction().execute();
			return;
		}
		
		List<Square> neighbours = obtainNeighbours(x, y);
		
		if(board[x][y].getAction() instanceof ClearAction) {
			
			board[x][y].getAction().execute();
			board[x][y].setValue(((ClearAction)board[x][y].getAction()).getValue());
			board[x][y].stepOn();
			board[x][y].setAction(new NullAction());
			
			if(board[x][y].getValue() == 0) {
				for(Square s: neighbours) {
					if(!s.hasMine()) {
						stepOn(s.getxCoord(), s.getyCoord());
						//break;
					}
				}
			}
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
		
		if(board[x][y].isFlagged() && !board[x][y].isOpened()) {
			
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
	 * @return Cadena representativa del tablero.
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
	
		char[][] stateArray = new char[rows][cols];
		
		for(int row=0; row<rows; row++) {
			for(int col=0; col<cols; col++) {
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
	
	/**
	 * Marca el tablero como "explotado".
	 */
	public void markAsExploded() {
		
		if(hasExploded()) {
			setExploded(true);
		}
		
	}
	
	/*
	 * Gestión de acciones.
	 */
	
	// Asocia una casilla a su acción correspondiente (null no, porque inicialmente ninguna casilla está abierta).
	
	private void setUpAction(int x, int y) {
		
		if(board[x][y].hasMine()) {
			setUpBlowUpAction(x, y);
		} else {
			setUpClearAction(x, y);
		}
		
	}

	private void setUpClearAction(int x, int y) {
		
		List<Square> neighbours = obtainNeighbours(x, y);
		Executable clearAction = new ClearAction(neighbours);
		board[x][y].setAction(clearAction);
		
	}

	private void setUpBlowUpAction(int x, int y) {
		
		Executable blowAction = new BlowUpAction(this);
		board[x][y].setAction(blowAction);
		
	}
	
	/*
	 * Buscar vecinos.
	 */

	// Obtiene una lista de casillas vecinas en función de su posición.
	private List<Square> obtainNeighbours(int x, int y) {
		
		if(x == 0 && y == 0) 
			return obtainNeighboursUpLeftCorner();
		else if(x == 0 && y == board[0].length-1) 
			return obtainNeighboursUpRightCorner();
		else if(x == board.length-1 && y == 0) 
			return obtainNeighboursDownLeftCorner();
		else if(x == board.length-1 && y == board[0].length-1) 
			return obtainNeighboursDownRightCorner();
		else if(y == 0) 
			return obtainNeighboursUpSide(x, y);
		else if(y == board[0].length-1)
			return obtainNeighboursDownSide(x, y);
		else if(x == 0) 
			return obtainNeighboursLeftSide(x, y);
		else if(x == board.length-1) 
			return obtainNeighboursRightSide(x, y);
		else 
			return obtainNeighboursCenter(x, y);
		
	}

	private List<Square> obtainNeighboursCenter(int x, int y) {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		neighbours.add(board[x-1][y-1]);
		neighbours.add(board[x+1][y+1]); 
		neighbours.add(board[x+1][y-1]); 
		neighbours.add(board[x-1][y+1]); 
		neighbours.add(board[x][y-1]); 
		neighbours.add(board[x][y+1]); 
		neighbours.add(board[x-1][y]); 
		neighbours.add(board[x+1][y]);
		
		return neighbours;
	}

	private List<Square> obtainNeighboursLeftSide(int x, int y) {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		neighbours.add(board[x+1][y+1]);
		neighbours.add(board[x+1][y-1]);
		neighbours.add(board[x][y-1]);
		neighbours.add(board[x][y+1]);
		neighbours.add(board[x+1][y]);
		
		return neighbours;
		
	}

	private List<Square> obtainNeighboursRightSide(int x, int y) {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		neighbours.add(board[x-1][y-1]);
		neighbours.add(board[x-1][y+1]);
		neighbours.add(board[x][y-1]);
		neighbours.add(board[x][y+1]);
		neighbours.add(board[x-1][y]);
		
		return neighbours;
		
	}

	private List<Square> obtainNeighboursUpSide(int x, int y) {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		neighbours.add(board[x+1][y+1]);
		neighbours.add(board[x-1][y+1]);
		neighbours.add(board[x][y+1]);
		neighbours.add(board[x-1][y]);
		neighbours.add(board[x+1][y]);
		
		
		return neighbours;
		
	}

	private List<Square> obtainNeighboursDownSide(int x, int y) {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		neighbours.add(board[x-1][y-1]);
		neighbours.add(board[x+1][y-1]);
		neighbours.add(board[x][y-1]);
		neighbours.add(board[x-1][y]);
		neighbours.add(board[x+1][y]);
		
		return neighbours;
		
	}

	private List<Square> obtainNeighboursUpLeftCorner() {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		neighbours.add(board[1][0]);
		neighbours.add(board[0][1]);
		neighbours.add(board[1][1]);
		
		return neighbours;
		
	}

	private List<Square> obtainNeighboursUpRightCorner() {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		int col = board[0].length-1;
		
		neighbours.add(board[0][col-1]);
		neighbours.add(board[1][col-1]);
		neighbours.add(board[1][col]);
		
		return neighbours;
		
	}

	private List<Square> obtainNeighboursDownLeftCorner() {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		int row = board.length-1;
		
		neighbours.add(board[row-1][0]);
		neighbours.add(board[row-1][1]);
		neighbours.add(board[row][1]);
		
		return neighbours;
		
	}

	private List<Square> obtainNeighboursDownRightCorner() {
		
		List<Square> neighbours = new ArrayList<Square>();
		
		int row = board.length-1;
		int col = board[0].length-1;
		
		neighbours.add(board[row-1][col-1]);
		neighbours.add(board[row-1][col]);
		neighbours.add(board[row][col-1]);
		
		return neighbours;
		
	}

	/*
	 * Métodos auxiliares.
	 */
	
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
	
	
	
	// Validación de parámetros del constructor.
	
	private void validateFields(int width, int height, int percentage) {
		
		ArgumentChecks.isTrue(width >= 2);
		ArgumentChecks.isTrue(height >= 2);
		ArgumentChecks.isTrue(percentage >= 0 && percentage <= 100);
		
	}
	
	/*
	 * Gestión de minas.
	 */
	
	private int obtainNumberOfMines(int percentage) {
		
		ArgumentChecks.isTrue(percentage >= 0 && percentage <= 100);
		
		int numberOfSquares = board.length * board[0].length;
		int numberOfMinesToPlace = (numberOfSquares*percentage)/100;
		return numberOfMinesToPlace;
		
	}
	
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
	
	/*
	 * Inicialización del tablero.
	 */
	
	// Rellena el tablero de objetos de tipo "Square".
	
	private void fillBoard() {
		
		int width = board.length;
		int height = board[0].length;
		
		
		for(int row=0; row<width; row++) {
			for(int col=0; col<height; col++) {
				
				board[row][col] = new Square();
				board[row][col].setxCoord(row);
				board[row][col].setyCoord(col);
				
			}
		}
	}
	
	private void associateActions() {
		
		int width = board.length;
		int height = board[0].length;
		
		for(int row=0; row<width; row++) {
			for(int col=0; col<height; col++) {
				setUpAction(row, col);
			}
		}
	}
	
	// Busca una isla vacía al azar y la descubre.
	
	private void uncoverWelcomeArea() {
		
		Random r = new Random();
		int xPos, yPos;
		
		do {
			xPos = r.nextInt(board.length);
			yPos = r.nextInt(board[0].length);
			
		} while(!isEmpty(xPos, yPos));
		
		stepOn(xPos, yPos);
		
	}
	
	private boolean isEmpty(int x, int y) {
		
		if(board[x][y].hasMine()) {
			return false;
		}
		
		for(Square s: obtainNeighbours(x, y)) {
			
			if(s.hasMine()) {
				return false;
			}
		
		}
		
		return true;
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

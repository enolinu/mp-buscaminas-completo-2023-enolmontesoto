package uo.mp.minesweeper.game;

/**
 * Clase que representa y guarda los valores relativos a
 * una operación en el juego. Dicha operación puede ser
 * marcar, desmarcar y abrir, y está asociada a una 
 * casilla del juego.
 * 
 * @author enolmontesoto
 *
 */
public class GameMove {
	
	private char operation;
	private int row, column;
	
	/**
	 * Constructor, con el tipo de operación y las coordenadas como
	 * parámetros.
	 * @param operation
	 * @param row
	 * @param column
	 */
	public GameMove(char operation, int row, int column) {
		
		setOperation(operation);
		setRow(row);
		setColumn(column);
		
	}
	
	/**
	 * @return operation
	 */
	public char getOperation() {
		return operation;
	}

	private void setOperation(char operation) {
		this.operation = operation;
	}
	
	/**
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	private void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * @return column
	 */
	public int getColumn() {
		return column;
	}
	
	private void setColumn(int column) {
		this.column = column;
	}
	
	@Override
	public String toString() {
		return "GameMove [operation=" + operation + ", row=" + row + ", column=" + column + "]";
	}

}

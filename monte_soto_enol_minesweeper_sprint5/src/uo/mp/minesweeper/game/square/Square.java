package uo.mp.minesweeper.game.square;

import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.game.square.actions.ClearAction;
import uo.mp.minesweeper.game.square.actions.Executable;
import uo.mp.minesweeper.game.square.actions.NullAction;

/**
 * Clase que describe una casilla del tablero. Tiene asociado un estado, que
 * puede ser cubierto, descubierto o marcado. Tambien almacena el contenido
 * de dicha casilla (mina, pista numerica o vacia):
 * 
 * @author enolmontesoto
 *
 */
public class Square {
	
	// Constantes
	private final static int EMPTY = 0; // Define el valor "vacio".
	private final static int MINE = -1; // Define que tiene mina.
	
	// Atributos
	private Status status; // Estado (abierto / cerrado / marcado)
	private int value; // Valor (vacio / mina / pista numerica)
	private Executable action;
	private int xCoord;
	private int yCoord;
	
	/**
	 * Metodo constructor por defecto que crea una casilla con estado cerrado
	 * y valor vacio.
	 */
	public Square() {
		
		setStatus(Status.CLOSED);
		setValue(EMPTY);
		setAction(new NullAction());
		
	}

	/**
	 * Metodo get del aributo que describe el estado.
	 * @return status, el estado actual de la casilla.
	 */
	public Status getStatus() {
		return status;
	}


	private void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Metodo get del aributo que describe el valor de la casilla.
	 * @return value, el estado actual de la casilla. (0 = vacio, 1 = con mina y
	 * un valor entre 1 y 8 para describir una pista numerica.
	 */
	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		ArgumentChecks.isTrue(value >= -1 && value <= 8);
		this.value = value;
	}
	
	public Executable getAction() {
		return action;
	}

	public void setAction(Executable action) {
		this.action = action;
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * Metodo que destapa una casilla en el caso de que la misma se encuentre en
	 * estado cerrado. Si no se cumple esa condicion el metodo no ejecuta
	 * ninguna instruccion.
	 * @throws GameException 
	 */
	public void stepOn() throws GameException {
		
		if(this.getStatus() == Status.OPENED || this.getStatus() == Status.FLAGGED) {
			throw new GameException("Can´t step on a closed or flagged square!");
		}
		
		this.setStatus(Status.OPENED);
		
	}
	
	/**
	 * Metodo que marca una casilla en el caso de que la misma se encuentre en
	 * estado cerrado. Si no se cumple esa condicion el metodo no ejecuta
	 * ninguna instruccion.
	 * @throws GameException 
	 */
	public void flag() throws GameException {
		
		if(this.getStatus() == Status.FLAGGED || this.getStatus() == Status.OPENED) {
			throw new GameException("Cant flag a flagged or opened square!");
		}
		
		this.setStatus(Status.FLAGGED);
		
	}
	
	public void unflag() throws GameException {
		
		if(this.getStatus() == Status.CLOSED || this.getStatus() == Status.OPENED) {
			throw new GameException("Cant unflag a flagged or opened square!");
		}
		
		this.setStatus(Status.CLOSED);
		
	}
	
	/**
	 * Metodo que destapa una casilla de manera incondicional.
	 * @throws GameException 
	 */
	public void open() throws GameException {
		
		if(this.getStatus() == Status.CLOSED) {
			this.setStatus(Status.OPENED);
		}
	}
	
	/**
	 * Metodo booleano que indica si la casilla contiene una mina.
	 * @return true si la casilla contiene una mina, false en caso contrario,
	 */
	public boolean hasMine() {
		
		return this.getValue() == MINE;
		
	}
	
	/**
	 * Metodo booleano que indica si la casilla se encuentra marcada.
	 * @return true si la casilla esta marcada, false en caso contrario,
	 */
	public boolean isFlagged() {
		
		return this.getStatus() == Status.FLAGGED;
		
	}
	
	/**
	 * Metodo booleano que indica si la casilla se encuentra abierta.
	 * @return true si la casilla esta abierta, false en caso contrario,
	 */
	public boolean isOpened() {
		
		return this.getStatus() == Status.OPENED;
		
	}
	
	/**
	 * Metodo que "coloca" una mina en la casilla.
	 */
	public void putMine() {
		
		this.setValue(MINE);
		
	}
	
	public void executeAction() throws GameException {
		
		action.execute();
		
		if(action instanceof ClearAction) {
			setValue(((ClearAction) action).getValue());
			stepOn();
			
		}
		
	}
	
	/**
	 * Devuelve caracter representativo del objeto.
	 * @return char.
	 */
	public char toChar() {
		
		switch(status) {
		case CLOSED: 
			return '#';
		case FLAGGED: 
			return (char) 182;
		case OPENED: 
			return charOpened();
		default: 
			return ' ';
		
		}
	}
	
	private char charOpened() {
		if(hasMine()) 
			return '@';
		if(value == 0) 
			return ' ';
		else 
			return String.valueOf(value).charAt(0);
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}
}

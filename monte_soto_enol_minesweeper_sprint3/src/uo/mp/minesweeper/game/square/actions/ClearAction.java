package uo.mp.minesweeper.game.square.actions;

import java.util.ArrayList;
import java.util.List;

import uo.mp.minesweeper.game.square.Square;

/**
 * Acción pensada para ser utilizada en una casilla vacía sin mina. La acción
 * que llevará a cabo es la de obtener una pista numérica para la casilla.
 * 
 * @author enolmontesoto
 *
 */
public class ClearAction implements Executable {
	
	private List<Square> neighbours = new ArrayList<Square>();
	
	private int value;
	
	/**
	 * Obtiene las casillas vecinas y las guarda.
	 * 
	 * @param neighbouringSquares
	 */
	public ClearAction(List<Square> neighbouringSquares) {
		
		neighbours.addAll(neighbouringSquares);
		
		
	}
	
	/**
	 * Tras calcular el valor de la casilla, lo podrá devolver a través de éste método.
	 * @return
	 */
	public int getValue() {
		return value;
	}
	
	
	/**
	 * El siguiente método es la redefinición del método execute() que ofrece
	 * la interfaz executable. En esta clase le da el valor a la casilla que
	 * representa.
	 */
	@Override
	public void execute() {
		
//		int counter = 0;
//		
//		for(Square s: neighbours) {
//			
//			if(s.hasMine()) {
//				counter ++;
//			}
//			
//		}
//		
//		this.value = counter;
//		
//		List<Square> visited = new ArrayList<Square>();
//		
//		if (counter == 0) {
//	        for (Square s : neighbours) {
//	            if (!visited.contains(s)) {
//	                visited.add(s);
//	                s.getAction().execute();
//	            }
//	        }
//	    }
	    
	    int counter = 0;
	    
	    for(Square s: neighbours) {
	        
	        if(s.hasMine()) {
	            counter ++;
	        }
	        
	    }
	    
	    this.value = counter;
	    
	}
	

}

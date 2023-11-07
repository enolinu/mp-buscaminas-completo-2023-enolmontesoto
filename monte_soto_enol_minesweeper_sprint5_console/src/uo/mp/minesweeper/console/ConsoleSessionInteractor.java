package uo.mp.minesweeper.console;

import java.io.PrintStream;
import java.util.List;

import uo.mp.lab.util.console.Console;
import uo.mp.minesweeper.exception.GameException;
import uo.mp.minesweeper.exception.UserInteractionException;
import uo.mp.minesweeper.ranking.GameRankingEntry;
import uo.mp.minesweeper.session.GameLevel;
import uo.mp.minesweeper.session.SessionInteractor;

public class ConsoleSessionInteractor implements SessionInteractor {
	
	private String userName;

	/**
	 * Pide al usuario el nivel de dificultad y lo devuelve.
	 * @return El nivel de dificultad, de tipo enumerado.
	 * @throws UserInteractionException 
	 */
	@Override
	public GameLevel askGameLevel() throws UserInteractionException {
		
		showMessage(System.out, "Level? (e)asy, (m)edium, (h)ard");
		char level = (char) Console.readCharacter();
		level = Character.toLowerCase(level);
		
		if(level != 'e' && level != 'm' && level != 'h') {
			throw new UserInteractionException("Level must be e, m, or h!");
		}
		
		switch(level) {
		case('e'): return GameLevel.EASY;
		case('m'): return GameLevel.MEDIUM;
		case('h'): return GameLevel.HARD;
		}
		
		return null;
		
	}
	
	/**
	 * Pide y devuelve el nombre de usuario del jugador.
	 * @return userName, el nombre de usuario que introduzca el jugador.
	 * @throws GameException 
	 */
	@Override
	public String askUserName() throws GameException {
		
		showMessage(System.out, "Player name? ");
		userName = Console.readString();
		if(!userName.matches("^[a-z]+$")) {
			throw new GameException("Name must contain only small case letters!");
		}
		return userName;
		
	}
	
	/**
	 * Método que muestra el menú y pide una opción del mismo al usuario.
	 * Devuelve el identificador de dicha acción.
	 * @return option, la opción del menú elegida.
	 * @throws UserInteractionException 
	 */
	@Override
	public int askNextOption() throws UserInteractionException {
		
		showMessage(System.out, buildMenu());
		int option = Console.readInt();
		
		if(option > 5 || option < 0) {
			throw new UserInteractionException("Menu option must be between 0-5.");
		}
		
		return option;
		
	}
	
	/**
	 * Pregunta y devuelve si el usuario desea guardar su puntuación.
	 * @return true si el usuario desea guardar su puntuación en el ranking.
	 * @throws UserInteractionException 
	 */
	@Override
	public boolean doYouWantToRegisterYourScore() throws UserInteractionException {
		
		showMessage(System.out, "Do you want to register your score? (y)es, (n)o");
		char option = (char) Console.readCharacter();
		option = Character.toLowerCase(option);
		
		if(option != 'y' && option != 'n' && option != 'Y' && option != 'N') {
			throw new UserInteractionException("The options can only be 'y' or 'n'");
		}
		
		switch(option) {
		case('y'): return true;
		case('n'): return false;
		case('Y'): return true;
		case('N'): return false;
		}
		
		return false;
		
	}
	
	/**
	 * Muestra el ránking.
	 */
	@Override
	public void showRanking(List<GameRankingEntry> ranking) {
		
		showMessage(System.out, buildHeading(true));
		
		for(GameRankingEntry entry: ranking) {
			showMessage(System.out, entry.toString());
		}
		
		printNewLine();
		
	}
	
	/**
	 * Muestra el ranking del usuario.
	 */
	@Override
	public void showPersonalRanking(List<GameRankingEntry> ranking) {
		
		showMessage(System.out, buildHeading(false));
		
		for(GameRankingEntry entry: ranking) {
			showMessage(System.out, entry.toStringWithoutUser());
		}
		
		printNewLine();
		
	}
	
	/**
	 * Muestra el mensaje de despedida.
	 */
	@Override
	public void showGoodBye() {
		
		showMessage(System.out, "Thanks for your session. Bye, bye!");
		
	}
	
	/**
	 * Muestra el mensaje de error.
	 */
	@Override
	public void showErrorMessage(String message) {
		
		showMessage(System.err, message);
		
	}
	
	/**
	 * Muestra el mensaje de error irrecuperable.
	 */
	@Override
	public void showFatalErrorMessage(String message) {
		
		showMessage(System.err, message);
		
	}
	
	@Override
	public String askFileName() {
		
		showMessage(System.out, "Enter file name: ");
		String filename = Console.readString();
		return filename;
		
	}

	private void showMessage(PrintStream out, String content) {
		out.println(content);
	}
	
	private String buildMenu() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Available options:\n");
		sb.append(" 1- Play a new game.\n");
		sb.append(" 2- Show my results.\n");
		sb.append(" 3- Show all results.\n");
		sb.append(" 4- Export results.\n");
		sb.append(" 5- Import results.\n");
		sb.append(" 0- Exit.\n");
		return sb.toString();
		
	}
	
	private String buildHeading(boolean withUser) {
		
		StringBuilder sb = new StringBuilder();
		
		if(withUser) sb.append("User\t");
		sb.append(".Date\t\t");
		sb.append(".Hour\t");
		sb.append(".Level\t");
		sb.append(".Res\t");
		sb.append(".Time\t");
		
		return sb.toString();
		
	}
	
	private void printNewLine() {
		System.out.println();
	}

}

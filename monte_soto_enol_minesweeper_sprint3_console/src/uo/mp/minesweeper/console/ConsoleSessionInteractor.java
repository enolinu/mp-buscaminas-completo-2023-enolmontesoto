package uo.mp.minesweeper.console;

import java.io.PrintStream;
import java.util.List;

import uo.mp.lab.util.console.Console;
import uo.mp.minesweeper.ranking.GameRankingEntry;
import uo.mp.minesweeper.session.GameLevel;
import uo.mp.minesweeper.session.SessionInteractor;

public class ConsoleSessionInteractor implements SessionInteractor {
	
	/**
	 * Pide al usuario el nivel de dificultad y lo devuelve.
	 * @return El nivel de dificultad, de tipo enumerado.
	 */
	@Override
	public GameLevel askGameLevel() {
		
		showMessage(System.out, "Level? (e)asy, (m)edium, (h)ard");
		char level = (char) Console.readCharacter();
		
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
	 */
	@Override
	public String askUserName() {
		
		showMessage(System.out, "Player name? ");
		String userName = Console.readString();
		return userName;
		
	}
	
	/**
	 * Método que muestra el menú y pide una opción del mismo al usuario.
	 * Devuelve el identificador de dicha acción.
	 * @return option, la opción del menú elegida.
	 */
	@Override
	public int askNextOption() {
		
		showMessage(System.out, buildMenu());
		int option = Console.readInt();
		return option;
		
	}

	@Override
	public boolean doYouWantToRegisterYourScore() {
		
		showMessage(System.out, "Do you want to register your score? (y)es, (n)o");
		char option = (char) Console.readCharacter();
		
		switch(option) {
		case('y'): return true;
		case('n'): return false;
		}
		
		return false;
	}

	@Override
	public void showRanking(List<GameRankingEntry> ranking) {
		
		
		
	}

	@Override
	public void showPersonalRanking(List<GameRankingEntry> ranking) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showGoodBye() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showErrorMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showFatalErrorMessage(String message) {
		// TODO Auto-generated method stub
		
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
		sb.append(" 0- Exit.");
		return sb.toString();
		
	}

}

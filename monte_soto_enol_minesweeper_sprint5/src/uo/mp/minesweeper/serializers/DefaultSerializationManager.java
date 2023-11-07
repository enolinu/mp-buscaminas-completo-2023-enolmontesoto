package uo.mp.minesweeper.serializers;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import uo.mp.lab.util.check.ArgumentChecks;
import uo.mp.minesweeper.ranking.GameRankingEntry;

/**
 * Esta clase se encarga de gestionar la serialización de las entradas del
 * ranking por defecto, es decir, utilizando la serialización de objetos
 * que ofrece Java.
 * 
 * @author enolmontesoto
 *
 */
public class DefaultSerializationManager {
	
	public static void serializeRanking(List<GameRankingEntry> ranking, String filename) throws IOException {
		
		ArgumentChecks.checkNotNull(ranking, "Ranking must not be null!");
		ArgumentChecks.checkNotNull(filename, "Filename must not be null!");
		
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (GameRankingEntry entry : ranking) {
                oos.writeObject(entry);
            }
        }
    }

	public static List<GameRankingEntry> deserializeRanking(String filename) throws IOException, ClassNotFoundException {
		
		ArgumentChecks.checkNotNull(filename, "Filename must not be null!");
		
        List<GameRankingEntry> ranking = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                try {
                    GameRankingEntry entry = (GameRankingEntry) ois.readObject();
                    ranking.add(entry);
                } catch (EOFException e) {
                    break;
                }
            }
        }
        return ranking;
    }

}

package uo.mp.lab.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import uo.mp.lab.util.check.ArgumentChecks;

/**
 * A utility class to read/write text lines from/to a text file
 */
public class FileUtil {

	public static List<String> readLines(String inFileName) {
		
		// Falta validar parámetros.
		
		List<String> res = new LinkedList<>();
		
		String line;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(inFileName));
			try {
				while((line = in.readLine()) != null) {
					res.add(line);
				}
			} finally {
				in.close();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("No se encontró el fichero especificado");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return res;
		
	}

	public static void writeLines(String outFileName, List<String> lines) {
		
		ArgumentChecks.checkNotNull(outFileName, "Output filename cannot be null.");
		ArgumentChecks.checkNotNull(lines, "Lines to write list cannot be null.");
		ArgumentChecks.isTrue(!outFileName.trim().isEmpty(), "Output filename cannot be empty!");
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(outFileName));
			try {	
				int lineNumber = 0;
				for(String line: lines) {
					lineNumber++;
					out.write(line);
					if(lineNumber < lines.size()) {
						out.newLine();
					}
				} 	
			} finally {	
				out.close();
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void writeLoggerMessage(String filename, String message) {
		try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            writer.write(message);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
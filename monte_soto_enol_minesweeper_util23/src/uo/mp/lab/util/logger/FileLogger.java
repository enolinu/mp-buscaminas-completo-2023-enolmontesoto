package uo.mp.lab.util.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import uo.mp.lab.util.io.FileUtil;

public class FileLogger implements SimpleLogger {
	
	private String file;

	public FileLogger(String logFile) {
		file = logFile;
	}

	@Override
	public void log(Exception ex) {
		
		FileUtil.writeLoggerMessage(file, buildMessage(ex));
		
	}
	
	private String buildMessage(Exception ex) {
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		String formattedDate = formatter.format(date);
		
		StringBuilder sb = new StringBuilder();
		sb.append("[" + formattedDate + "] " + ex);
		return sb.toString();
		
	}

}

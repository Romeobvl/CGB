package cgb.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static Logger instance ;

	private Logger(){
	}

	public static Logger getInstance(){
		if (instance ==null) {
			instance = new Logger();
		}
		return instance;
	}

	private FileWriter fileWriter;
	private PrintWriter printWriter;
	private static final String NOM = "log.txt";
	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 

	public void log(String message) {
		try {
			fileWriter = new FileWriter(NOM, true);
			printWriter = new PrintWriter(fileWriter);
			printWriter.println(timestamp + " | " + message);
			printWriter.flush();
		} catch (IOException e) {
			System.err.println("Error initializing logger: " + e.getMessage());
		}
	}
}

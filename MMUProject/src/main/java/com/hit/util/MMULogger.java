package com.hit.util;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MMULogger {
	
	public static final String DEFUALT_FILE_NAME = "logs/log.txt";
	private static MMULogger instance = null;
	private FileHandler handler;
	
	
	private MMULogger(){
		try{
				//trying to create log file
			handler = new FileHandler(DEFUALT_FILE_NAME);
			handler.setFormatter(new OnlyMessageFormatter());
		}catch(Exception e){
			e.printStackTrace();
			}
	}
	
	public static MMULogger getInstance(){
		if(instance == null)
			instance =  new MMULogger();
		return instance;
	}
	
	public synchronized void write(String command, Level level){
		handler.publish(new LogRecord(level, command + System.lineSeparator()));
		handler.flush();
	}
	
	public class OnlyMessageFormatter extends Formatter{

		public OnlyMessageFormatter() {
			super();
		}

		@Override
		public String format(final LogRecord record) {
			return record.getMessage();
		}
		
	}
	public void close() {
		try {
			if(handler != null) {
				handler.flush();
				handler.close();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		instance = null;
		
	}

}


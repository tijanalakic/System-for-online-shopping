package waf.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import waf.main.MyFilter;

public class MyLogger {
	private static Logger LOGGER;
	private static String LOG_FILE_PATH = MyFilter.DATA_PATH + File.separator + "error.log";
	
    public Handler fileHandler;
    public Formatter plainText;
    
   
    public MyLogger() throws IOException{
    	
        LOGGER = Logger.getLogger(MyLogger.class.getName());
        
        fileHandler = new FileHandler(LOG_FILE_PATH, true);
        plainText = new SimpleFormatter();
        fileHandler.setFormatter(plainText);
        
        LOGGER.addHandler(fileHandler);
        
    }
    
    private static Logger getLogger(){
    	
        if(LOGGER == null){
            try {
                new MyLogger();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return LOGGER;
    }
    
    public static void log(Level level, String message, StackTraceElement[] stackTrace){
    	
    	StringBuilder sb=new StringBuilder();
    	sb.append(message+"\n");
    	
    	for(StackTraceElement traceElement:stackTrace) {
    		sb.append("\tat " + traceElement + "\n");
    	}
        getLogger().log(level, sb.toString());
    }
}
package com.lld.logger;

public class Main {
	
   public static void main(String[] args) {
	   Logger instance = Logger.getInstance();
	   instance.setLeval(LogLevel.INFO);
	   instance.addAppender(new ConsoleAppender());
	   instance.addAppender(new FileAppender("logs/application.log"));
	   instance.info("Application started");
	   instance.warn("low memory waring");
	   instance.error("DataBase connecation failed");
	   instance.info("debug connecation failed");
 }
}

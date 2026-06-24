package com.lld.logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Logger {
     private  final static Logger instance = new Logger();
     private Logger() {};
     private volatile LogLevel curLevel = LogLevel.INFO;
     private final List<Appender> appnders = new CopyOnWriteArrayList<>();
     public static Logger getInstance() {
    	 return instance;
     }
     public void setLeval(LogLevel curLevel) {
    	 this.curLevel = curLevel;
     }
	public void addAppender(Appender Appender) {
		appnders.add(Appender);
	}
	public void log(LogLevel level,String msg) {
		LogEvent event = new LogEvent(level, msg);
		if(level.ordinal()>=curLevel.ordinal()) {
			for(Appender app : appnders) {
				 app.append(event);
			}
		}
	}
	public void info(String msg) {
		log(LogLevel.INFO, msg);
	}
	public void warn( String msg) {
		log(LogLevel.WARN, msg);
	}
	public void error(String msg) {
		log(LogLevel.ERROR, msg);
	}
	public void debug(String msg) {
		log(LogLevel.DEBUG, msg);
	}
}

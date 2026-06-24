package com.lld.logger;

public class LogEvent {
    private final LogLevel logLevel;
    private final String mesage;
    private final long timeStamp;
    
	public LogEvent(LogLevel logLevel, String mesage) {
		super();
		this.logLevel = logLevel;
		this.mesage = mesage;
		this.timeStamp = System.currentTimeMillis()/1000;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public String getMesage() {
		return mesage;
	}

	public long getTimeStamp() {
		return timeStamp;
	}
    
    
    
}

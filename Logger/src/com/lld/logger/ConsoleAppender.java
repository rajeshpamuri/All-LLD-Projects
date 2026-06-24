package com.lld.logger;

public class ConsoleAppender implements Appender{

	@Override
	public void append(LogEvent event) {
		System.out.println(event.getLogLevel()+"["+event.getMesage()
		+"]"+event.getTimeStamp());	
	}

}

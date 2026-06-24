package com.lld.logger;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements Appender{
     private final String filePath;
      
     
	public FileAppender(String filePath) {
		super();
		this.filePath = filePath;
	}
     

	@Override
	public void append(LogEvent event) {
		try(FileWriter fw = new FileWriter(filePath,true)){
			fw.write(event.getMesage()+"["+event.getLogLevel()+"]"+event.getTimeStamp()+"\n");
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}

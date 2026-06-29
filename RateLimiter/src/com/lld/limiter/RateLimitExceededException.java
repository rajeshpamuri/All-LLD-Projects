package com.lld.limiter;

public class RateLimitExceededException extends RuntimeException{
   
	private static final long serialVersionUID = 1L;

	public RateLimitExceededException(String msg) {
    	super(msg);
    }
}

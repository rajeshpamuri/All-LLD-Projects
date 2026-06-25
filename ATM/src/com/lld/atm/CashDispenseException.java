package com.lld.atm;

public class CashDispenseException extends RuntimeException{
    
	private static final long serialVersionUID = 1L;

	public CashDispenseException(String msg) {
    	  super(msg);
      }
}

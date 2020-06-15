package com.sap.sf.rest.client;

/**
 * App triggered exception.
 * @author ganeshkumar.venugopalan@sap.com
 */
public class AppException extends Exception{

	public AppException(String message, Throwable t){
		super(message,t);
	}
	
	@Override
	public String toString() {
		return "AppException ["+this.getMessage()+", Exception - "+this.getCause()+"]";
	}

}
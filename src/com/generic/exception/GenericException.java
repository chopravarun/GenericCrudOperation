package com.generic.exception;

public class GenericException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 582113405412228288L;

	public GenericException(Exception e) {
		super(e);
	}
	public GenericException(String e){
		super(e);
	}
}

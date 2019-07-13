package com.exception;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode=null;

	public ApplicationException(String errCode,String errMessage){
		super(errMessage);
		this.errorCode=errCode;
	}
	
	public ApplicationException(Exception exception){
		super(exception);
	}
	
	
	public String getErrorCode() {
		return errorCode;
	}


}

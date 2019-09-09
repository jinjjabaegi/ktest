package com.realtimetech.ktest.assertion;

public class AssertException extends RuntimeException {

	private static final long serialVersionUID = 5252733358266612903L;

	int lineNumber = 0;
	String fileName = null;
	
	public AssertException(String msg) {
		super(msg);
		
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		if (!stackTrace[2].getFileName().equals("Assert.java")) {
			fileName = stackTrace[2].getFileName();
			lineNumber = stackTrace[2].getLineNumber();
		} else {
			fileName = stackTrace[3].getFileName();
			lineNumber = stackTrace[3].getLineNumber();
		}
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();
		
		if (fileName != null && lineNumber >= 0) {
			message += " Exception at " + fileName + ":" + lineNumber;
		}
		
		return message;
	}

}

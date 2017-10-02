package com.idc.omega.xml;


public class AntlrErrorMessage
{
	private final int charPositionInLine;
	
	private final String message;

	private int line;

	public AntlrErrorMessage(int line, int charPositionInLine,String message)
	{
		this.line = line;
		this.charPositionInLine = charPositionInLine;
		this.message = message;
	}

	public int getCharPositionInLine() {
		return charPositionInLine;
	}

	
	
	public int getLine() {
		return line;
	}

	public String getMessage() {
		return message;
	}

}

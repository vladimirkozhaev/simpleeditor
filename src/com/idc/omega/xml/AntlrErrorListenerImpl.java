package com.idc.omega.xml;

import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class AntlrErrorListenerImpl extends BaseErrorListener {
	private final Set<AntlrErrorMessage> errors = new HashSet<AntlrErrorMessage>();

	public Set<AntlrErrorMessage> getErrors() {
		return errors;
	}

	public AntlrErrorListenerImpl(Parser pParser, Lexer pLexer) {
		pParser.removeErrorListeners();
		pParser.addErrorListener(this);
		pLexer.removeErrorListeners();
		pLexer.addErrorListener(this);
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException re) {
		errors.add(new AntlrErrorMessage(line, charPositionInLine, msg));
	}

}

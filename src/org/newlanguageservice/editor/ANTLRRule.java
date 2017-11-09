package org.newlanguageservice.editor;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ANTLRRule implements IPredicateRule {

	IToken token=new Token(new TextAttribute(new Color(Display.getCurrent(), new RGB(139,0,139))));
	
	public ANTLRRule(IToken token) {
		super();
		this.token = token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return token;
	}

	@Override
	public IToken getSuccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		// TODO Auto-generated method stub
		return null;
	}

}

package org.newlanguageservice.editor;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class MinDocumentPatritionScanner extends RuleBasedPartitionScanner {

	/**
	 * 
	 */
	public MinDocumentPatritionScanner() {
		super();
		
		IToken xmlComment = new Token("comment");

		IPredicateRule[] rules = new IPredicateRule[1];

		rules[0] = new MultiLineRule("/*", "*/", xmlComment);
		

		setPredicateRules(rules);
	}

}

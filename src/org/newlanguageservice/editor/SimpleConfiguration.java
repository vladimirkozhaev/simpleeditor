package org.newlanguageservice.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class SimpleConfiguration extends SourceViewerConfiguration {

	/**
	 * @param editor
	 */
	public SimpleConfiguration(MinimalEditor editor, ColorManager colorManager) {
		super();
		this.editor = editor;
		this.colorManager = colorManager;
	}

	private ColorManager colorManager;
	private MinimalEditor editor;
	private MonoReconciler reconciler;
	private PresentationReconciler presentationReconcicler;

	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		if (reconciler != null) {
			return reconciler;
		}
		SimpleReconciclingStrategy strategy = new SimpleReconciclingStrategy();
		strategy.setEditor(editor);

		reconciler = new MonoReconciler(strategy, false);

		return reconciler;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		if (presentationReconcicler != null)
			return presentationReconcicler;
		presentationReconcicler = new PresentationReconciler();
		// 1) Damager and repairer for JNLP tags.
		RuleBasedScanner scanner = new RuleBasedScanner();
		IToken stringColor = new Token(new TextAttribute(colorManager.getColor(ColorConstants.STRING)));

		IRule[] rules = new IRule[2];
		// the rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", stringColor, '\\');
		// The white space rule.
		IWhitespaceDetector whitespaceDetector = new IWhitespaceDetector() {
			public boolean isWhitespace(char c) {
				return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
			}
		};
		rules[1] = new WhitespaceRule(whitespaceDetector);
		scanner.setRules(rules);
		scanner.setDefaultReturnToken(new Token(new TextAttribute(colorManager.getColor(ColorConstants.TAG))));

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);

		presentationReconcicler.setDamager(dr, JNLPPartitionScanner.JNLP_TAG);
		presentationReconcicler.setRepairer(dr, JNLPPartitionScanner.JNLP_TAG);

		// 2) Damager and repairer for JNLP default content type.
		IToken procInstr = new Token(new TextAttribute(colorManager.getColor(ColorConstants.PROC_INSTR)));
		rules = new IRule[2];
		// the rule for processing instructions
		rules[0] = new SingleLineRule("<?", "?>", procInstr);
		// the rule for generic whitespace.
		rules[1] = new WhitespaceRule(whitespaceDetector);
		scanner = new RuleBasedScanner();
		scanner.setRules(rules);
		scanner.setDefaultReturnToken(new Token(new TextAttribute(colorManager.getColor(ColorConstants.DEFAULT))));
		dr = new DefaultDamagerRepairer(scanner);
		presentationReconcicler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		presentationReconcicler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		return presentationReconcicler;

	}

}

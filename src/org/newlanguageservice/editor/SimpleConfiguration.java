package org.newlanguageservice.editor;

import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class SimpleConfiguration extends SourceViewerConfiguration {
	
	/**
	 * @param editor
	 */
	public SimpleConfiguration(MinimalEditor editor) {
		super();
		this.editor = editor;
	}

	private MinimalEditor editor;

	public IReconciler getReconciler(ISourceViewer sourceViewer)
    {
        SimpleReconciclingStrategy strategy = new SimpleReconciclingStrategy();
        strategy.setEditor(editor);
        
        MonoReconciler reconciler = new MonoReconciler(strategy,false);
        
        return reconciler;
    }
}

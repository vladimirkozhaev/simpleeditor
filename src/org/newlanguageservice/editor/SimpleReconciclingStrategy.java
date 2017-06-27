package org.newlanguageservice.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;

public class SimpleReconciclingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {
	private MinimalEditor editor;

	private IDocument fDocument;

	/** holds the calculated positions */
	protected final List<Position> fPositions = new ArrayList<Position>();

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	public void initialReconcile() {

		calculatePositions();

	}

	private void calculatePositions() {
		String string = fDocument.get();
		String[] split = string.split("");
		int pos = 0;
		while (pos < split.length) {
			while (pos < split.length && isSpaceChar(split, pos)) {
				pos++;
			}

			int posStart = pos;

			while (pos < split.length && !isSpaceChar(split, pos)) {
				pos++;
			}

			int posEnd = pos;
			Position position = new Position(pos, posEnd - posStart);
			fPositions.add(position);
		}

		 Display.getDefault().asyncExec(new Runnable() {
             public void run() {
                     editor.updateFoldingStructure(fPositions);
             }

     });
	}

	private boolean isSpaceChar(String[] split, int pos) {
		return split[pos].equals("\n") || split[pos].equals("\r") || split[pos].equals(" ") || split[pos].equals("\t");
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		initialReconcile();

	}

	@Override
	public void reconcile(IRegion partition) {
		initialReconcile();

	}

	public void setEditor(MinimalEditor editor) {
		this.editor = editor;
	}

	public void setDocument(IDocument document) {
		this.fDocument = document;
	}

}

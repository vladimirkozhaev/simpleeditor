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

import com.idc.omega.xml.XMLNodeInfo;
import com.idc.omega.xml.XMLParserVisitor;

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
		fPositions.clear();
		String string = fDocument.get();
		try {

			while (string.length() > 0) {
				int startIndex = string.indexOf("XML>>");
				int endIndex = string.indexOf("<<XML");

				if (!(startIndex >= 0 && endIndex > 0))
					break;
				String substring = string.substring(startIndex + "XML>>".length(), endIndex);
				XMLNodeInfo makeNodesInfo = XMLParserVisitor.makeNodesInfo(substring);
				fillPositions(fPositions, makeNodesInfo);
				string = string.substring(endIndex + "<<XML".length());

			}

			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					editor.updateFoldingStructure(fPositions);
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void fillPositions(List<Position> fPositions, XMLNodeInfo makeNodesInfo) {
		if (makeNodesInfo == null) {
			return;
		}
		int offset = makeNodesInfo.getOffset();
		int lenght = makeNodesInfo.getLenght();
		Position position = new Position(offset, lenght);
		fPositions.add(position);
		makeNodesInfo.getChildren().forEach(child -> fillPositions(fPositions, child));

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

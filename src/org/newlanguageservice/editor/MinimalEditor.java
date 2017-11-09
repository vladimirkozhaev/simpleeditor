package org.newlanguageservice.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;

import com.idc.omega.xml.XMLNodeInfo;
import com.idc.omega.xml.XMLParserVisitor;

public class MinimalEditor extends TextEditor implements IDocumentListener {

	/**
	 * 
	 */

	XMLNodeInfo nodeInfo;

	public MinimalEditor() {
		super();
		setSourceViewerConfiguration(new SimpleConfiguration(this,new ColorManager()));
		setDocumentProvider(new JNLPDocumentProvider());
	}

	private ProjectionSupport projectionSupport;
	private ProjectionAnnotationModel annotationModel;
	private Annotation[] oldAnnotations = new Annotation[0];

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createPartControl(parent);
		ProjectionViewer viewer = (ProjectionViewer) getSourceViewer();

		projectionSupport = new ProjectionSupport(viewer, getAnnotationAccess(), getSharedColors());
		projectionSupport.install();

		// turn projection mode on
		viewer.doOperation(ProjectionViewer.TOGGLE);

		annotationModel = viewer.getProjectionAnnotationModel();
		viewer.getDocument().addDocumentListener(this);
	}
	
	

	@Override
	protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
		ISourceViewer viewer = new ProjectionViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(),
				styles);

		getSourceViewerDecorationSupport(viewer);

		return viewer;
	}

	public void updateFoldingStructure(List<Position> positions) {
		Annotation[] annotations = new Annotation[positions.size()];

		// this will hold the new annotations along
		// with their corresponding positions
		Map<Annotation, Position> newAnnotations = new HashMap<Annotation, Position>();

		for (int i = 0; i < positions.size(); i++) {
			ProjectionAnnotation annotation = new ProjectionAnnotation();

			newAnnotations.put(annotation, positions.get(i));

			annotations[i] = annotation;
		}

		annotationModel.replaceAnnotations(oldAnnotations, newAnnotations);

		oldAnnotations = annotations;
	}

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void documentChanged(DocumentEvent event) {

		nodeInfo = XMLParserVisitor.makeNodesInfo(event.fDocument.get());

	}

	public XMLNodeInfo getNodeInfo() {
		// TODO Auto-generated method stub
		return nodeInfo;
	}

}
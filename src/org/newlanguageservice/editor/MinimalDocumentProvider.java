package org.newlanguageservice.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.projection.ProjectionDocument;
import org.eclipse.jface.text.projection.ProjectionDocumentManager;
import org.eclipse.ui.editors.text.FileDocumentProvider;



public class MinimalDocumentProvider extends FileDocumentProvider {
	
	private ProjectionDocumentManager manager;
	private ProjectionDocument projectionDocument;

	protected IDocument createDocument(Object element) throws CoreException
	{
	    IDocument document = super.createDocument(element);
	    if (document != null) {
	    	manager = new ProjectionDocumentManager();
	    	projectionDocument = (ProjectionDocument)
	    	               manager.createSlaveDocument(document);
		}
	    return document;
	}
}

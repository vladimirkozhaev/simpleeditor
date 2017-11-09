package com.idc.omega.xml;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Interval;
import org.newlanguageservice.antlr.XMLLexer;
import org.newlanguageservice.antlr.XMLParser;
import org.newlanguageservice.antlr.XMLParser.ContentSequenceContext;
import org.newlanguageservice.antlr.XMLParser.DocumentContext;
import org.newlanguageservice.antlr.XMLParser.ElementContext;
import org.newlanguageservice.antlr.XMLParserBaseVisitor;

/**
 * 
 * @author vkozhaev
 *
 */

public class XMLParserVisitor extends XMLParserBaseVisitor<XMLNodeInfo> {

	private boolean mDoNotAddEmptyNodes;

	public XMLParserVisitor(boolean pAddEmptyNodes) {
		super();
		this.mDoNotAddEmptyNodes = pAddEmptyNodes;
	}

	@Override
	public XMLNodeInfo visitDocument(DocumentContext pCtx) {

		return visitElement(pCtx.elem);
	}

	@Override
	public XMLNodeInfo visitElement(ElementContext pCtx) {
		if (pCtx == null) {
			return null;
		}
		
		String text = getFullText(pCtx);

		if (mDoNotAddEmptyNodes && text.indexOf("\n") < 0 && text.indexOf("\r") < 0) {
			return null;
		}

		String pNodeName = pCtx.elementName == null ? " " : pCtx.elementName.getText();
		int textLen = text.length();

		if (mDoNotAddEmptyNodes) {
			textLen += 1;
		}
		XMLNodeInfo parentNode = new XMLNodeInfo(pNodeName, pCtx.getStart().getStartIndex(), textLen);
		pCtx.attribute().forEach(attr -> parentNode.getProperties().put(attr.attrName.getText(),
				attr.attrValue.getText().substring(1, attr.attrValue.getText().length() - 1)));
		if (pCtx.cont != null) {
			List<ContentSequenceContext> contentSequence = pCtx.cont.contentSequence();
			for (ContentSequenceContext sequenceEl : contentSequence) {
				if (sequenceEl.el != null) {
					XMLNodeInfo visit = visitElement(sequenceEl.el);
					parentNode.getChildren().add(visit);
				}
			}
		}

		return parentNode;
	}

	private String getFullText(ElementContext pCtx) {
		int startIndex = pCtx.start.getStartIndex();
		int stopIndex = pCtx.stop.getStopIndex();
		Interval interval = new Interval(startIndex, stopIndex);
		return pCtx.start.getInputStream().getText(interval);
	}

	public static XMLNodeInfo makeNodesInfo(String pStr, boolean pAddEmptyNodes) {
		XMLLexer lexer = new XMLLexer(new ANTLRInputStream(pStr));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		XMLParser parser = new XMLParser(tokens);

		DocumentContext tree = parser.document();
		XMLParserVisitor eval = new XMLParserVisitor(pAddEmptyNodes);
		XMLNodeInfo info = eval.visitElement(tree.elem);
		return info;

	}

	public static XMLNodeInfo makeNodesInfo(String pStr) {
		return makeNodesInfo(pStr, false);
	}
}

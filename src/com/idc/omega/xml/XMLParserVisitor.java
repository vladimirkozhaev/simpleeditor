package com.idc.omega.xml;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
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

	@Override
	public XMLNodeInfo visitDocument(DocumentContext pCtx) {
		return visitElement(pCtx.elem);
	}

	@Override
	public XMLNodeInfo visitElement(ElementContext pCtx) {

		String pNodeName = pCtx.elementName == null ? " " : pCtx.elementName.getText();
		XMLNodeInfo parentNode = new XMLNodeInfo(pNodeName, pCtx.getStart().getStartIndex(), pNodeName.length());
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

	public static XMLNodeInfo makeNodesInfo(String pStr) {
		XMLLexer lexer = new XMLLexer(new ANTLRInputStream(pStr));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		XMLParser parser = new XMLParser(tokens);
		//parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
		DocumentContext tree = parser.document();
		XMLParserVisitor eval = new XMLParserVisitor();
		XMLNodeInfo info = eval.visitElement(tree.elem);
		return info;
	}
}

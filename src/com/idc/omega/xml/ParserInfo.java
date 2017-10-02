package com.idc.omega.xml;

import java.util.Set;

public class ParserInfo {
	private XMLNodeInfo xmlNodeInfo;
	private Set<AntlrErrorMessage> antlrErrorMessage;
	public ParserInfo(XMLNodeInfo xmlNodeInfo, Set<AntlrErrorMessage> antlrErrorMessage) {
		super();
		this.xmlNodeInfo = xmlNodeInfo;
		this.antlrErrorMessage = antlrErrorMessage;
	}
	public XMLNodeInfo getXmlNodeInfo() {
		return xmlNodeInfo;
	}
	public Set<AntlrErrorMessage> getAntlrErrorMessage() {
		return antlrErrorMessage;
	}
	
	
}

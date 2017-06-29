package com.idc.omega.xml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author vkozhaev
 *
 */
public class XMLNodeInfo {
	
	private String mNodeName;
	private int mOffset;
	private int mLenght;
	private List<XMLNodeInfo> mChildren;
	private Map<String,String> mProperties;
	
	
	public XMLNodeInfo(String pNodeName, int pOffset, int pLenght) {
		super();
		this.mNodeName = pNodeName;
		this.mOffset = pOffset;
		this.mLenght = pLenght;
		this.mChildren = new ArrayList<XMLNodeInfo>();
		this.mProperties = new LinkedHashMap<String, String>();
	}
	
	public String getNodeName() {
		return mNodeName;
	}
	
	public int getOffset() {
		return mOffset;
	}
	
	public int getLenght() {
		return mLenght;
	}
	
	public List<XMLNodeInfo> getChildren() {
		return mChildren;
	}
	
	public Map<String, String> getProperties(){
		return mProperties;
	}

	@Override
	public String toString() {
		return "XMLNodeInfo [mNodeName=" + mNodeName + ", mOffset=" + mOffset + ", mLenght=" + mLenght + ", mChildren="
				+ mChildren + ", mProperties=" + mProperties + "]";
	}

	
	
}

package com.sap.sf.odata.meta;


import java.util.LinkedHashSet;
import java.util.Set;

public class EntityDocumentation{
	private String summary;
	private String longDescription;
	private Set<String> tagCollection;
	
	public EntityDocumentation(String summary, String longDescription){
		this.summary = summary;
		this.longDescription = longDescription;
		tagCollection = new LinkedHashSet<String>();			
	}
	
	public void addTagCollection(String tag){
		tagCollection.add(tag);
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Set<String> getTagCollection() {
		return tagCollection;
	}
	
	
}
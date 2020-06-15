/**
 * 
 */
package com.sap.sf.odata.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author I309192
 *
 */
public class Association extends MetaAttributes{
	private String associationName;
    private List<AssociationType> associationType;
    
    public Association(){
    	this.associationType = new ArrayList<AssociationType>();
    }
    
    public void addassociationType(AssociationType at){
    	this.associationType.add(at);
    }
    public Association(String associationName, Map<String,Object> allAttrib){
    	this();
    	this.associationName = associationName;
    	this.allAttributes = allAttrib;
    }
	/**
	 * @param associationName
	 * @param associationType
	 */
	public Association(String associationName, List<AssociationType> associationType) {
		super();
		this.associationName = associationName;
		this.associationType = associationType;
	}
	/**
	 * @return the associationName
	 */
	public String getAssociationName() {
		return associationName;
	}
	/**
	 * @param associationName the associationName to set
	 */
	public void setAssociationName(String associationName) {
		this.associationName = associationName;
	}
	/**
	 * @return the associationType
	 */
	public List<AssociationType> getAssociationType() {
		return associationType;
	}
	/**
	 * @param associationType the associationType to set
	 */
	public void setAssociationType(List<AssociationType> associationType) {
		this.associationType = associationType;
	}
    
    
	
	
}

/**
 * 
 */
package com.sap.sf.odata.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * @author I309192
 *
 */
public class MetaAttributes {

	protected Map<String,Object> allAttributes;
	
	public String getAttributeValue(String attribKey){
		
		return this.allAttributes.get(attribKey).toString();
	}
	public MetaAttributes(){
		allAttributes = new HashMap<String,Object>();
	}

	/**
	 * @return the allAttributes
	 */
	public Map<String, Object> getAllAttributes() {
		return allAttributes;
	}

	/**
	 * @param allAttributes
	 */
	public MetaAttributes(Map<String, Object> allAttributes) {
		this.allAttributes = allAttributes;
	}

	/**
	 * @param allAttributes the allAttributes to set
	 */
	public void setAllAttributes(Map<String, Object> allAttributes) {
		if(allAttributes !=null){
			this.allAttributes = allAttributes;
		}
		
	}
}

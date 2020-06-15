package com.sap.sf.odata.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author I309192
 *
 */
public class ComplexType extends MetaAttributes{
	
	private String complexTypeName;

	List<EntityAttribute> attributeList;

	public ComplexType(){
		attributeList = new ArrayList<EntityAttribute>();
	}
	
	public void addCTProperty(EntityAttribute eaObj){
		attributeList.add(eaObj);
	}
	public ComplexType(String complexTypeName,Map<String,Object> allAttrib){
		this();
		this.complexTypeName = complexTypeName;
		this.allAttributes = allAttrib;
	}
	
	/**
	 * @param complexTypeName
	 * @param ea
	 */
	public ComplexType(String complexTypeName, List<EntityAttribute> ea) {
		this();
		this.complexTypeName = complexTypeName;
		this.attributeList = ea;
	}

	/**
	 * @return the complexTypeName
	 */
	public String getComplexTypeName() {
		return complexTypeName;
	}

	/**
	 * @param complexTypeName the complexTypeName to set
	 */
	public void setComplexTypeName(String complexTypeName) {
		this.complexTypeName = complexTypeName;
	}

	/**
	 * @return the ea
	 */
	public List<EntityAttribute> getEa() {
		return attributeList;
	}

	/**
	 * @param ea the ea to set
	 */
	public void setEa(List<EntityAttribute> ea) {
		this.attributeList = ea;
	}
	
	/**
	 * Method to get complexType element Metadata as key value pairs
	 * Key - Name of the Complex type element
	 * Value - Complex type object
	 * @param ctList
	 * @return
	 */
	public Map<String, Object> getComplexTypeMap(List<ComplexType> ctList) {
		Map<String, Object> complexTypeMap = new HashMap<String, Object>();

		for (ComplexType ctObj : ctList) {

			complexTypeMap.put(ctObj.getComplexTypeName(), ctObj);
		}

		return complexTypeMap;
	}

	
	

}

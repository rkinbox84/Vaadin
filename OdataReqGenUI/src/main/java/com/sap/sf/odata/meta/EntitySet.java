/**
 * 
 */
package com.sap.sf.odata.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author I309192
 *
 */
public class EntitySet extends MetaAttributes{

	String name;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param name
	 */
	public EntitySet(String name) {
		super();
		this.name = name;
	}

	public EntitySet(){
		
	}
	
	public EntitySet(String name,Map<String,Object> allAttrib){
		this.name = name;
		this.allAttributes = allAttrib;
	}
	
	/**
	 * This method will return Key Value map of given entityset List
	 * Key - Name of the entity set
	 * Value - EntitySet object
	 * @param esList
	 * @return
	 */
	
	public Map<String, Object> getEntitySetMap(List<EntitySet> esList) {
		Map<String, Object> entitySetMap = new HashMap<String, Object>();

		for (EntitySet esObj : esList) {

			entitySetMap.put(esObj.getName(), esObj);
		}

		return entitySetMap;
	}
}

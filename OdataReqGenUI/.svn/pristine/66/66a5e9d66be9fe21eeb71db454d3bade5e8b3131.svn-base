package com.sap.sf.beanClass;

import java.util.HashMap;
import java.util.Map;

public class EntityList {

	//Entity Name -> [mandatory->[fields],optional->[fields]]
	Map<String,FieldsTypeList> entityList;

	public EntityList(){
		entityList = new HashMap<String,FieldsTypeList>();
	}
	public EntityList(Map<String, FieldsTypeList> entityList) {
		this();
		this.entityList = entityList;
	}

	public Map<String, FieldsTypeList> getEntityList() {
		return entityList;
	}

/*	public void setEntityList(Map<String, FieldsTypeList> entityList) {
		this.entityList = entityList;
	}*/
	
	public void setEntityList(String entityName, FieldsTypeList entityList) {
		this.entityList.put(entityName, entityList);
	}
	
	
}

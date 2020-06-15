package com.sap.sf.beanClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldsTypeList {
	
	//Keys - required, optional
	Map<String, List<EntityFieldAttributes>> fieldTypeList;
	
	public FieldsTypeList(){
		fieldTypeList = new HashMap<String,List<EntityFieldAttributes>>();
	}

	public FieldsTypeList(Map<String, List<EntityFieldAttributes>> fieldTypeList) {
		this();
		this.fieldTypeList = fieldTypeList;
	}

	public Map<String, List<EntityFieldAttributes>> getFieldTypeList() {
		return fieldTypeList;
	}

/*	public void setFieldTypeList(Map<String, List<EntityFieldAttributes>> fieldTypeList) {
		this.fieldTypeList = fieldTypeList;
	}*/
	
	public void setFieldTypeList(String type, List<EntityFieldAttributes> fieldTypeList) {
		this.fieldTypeList.put(type, fieldTypeList);
	}


	
	
}

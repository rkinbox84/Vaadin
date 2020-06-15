package com.sap.sf.odata.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityMetadata implements Comparable<EntityMetadata>{
	
	private String entitySetname;
	private String entityTypeName;
	private String entityLabel;
	
	private EntityDocumentation documentation;
	
	private EntityType entityType;
	
	public EntityMetadata(){
		
	}
	public EntityMetadata(String name, String type, String label){
		this.entitySetname = name;
		this.entityTypeName = type;
		this.entityLabel = label;
	}
	
	public EntityMetadata(String name, String type, String label, EntityType eType, EntityDocumentation documentation){
		this(name, type, label);
		this.entityType = eType;
		this.documentation = documentation;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null) return false;
		if (!(arg0 instanceof EntityMetadata)) return false;
		
		return entitySetname.equals(((EntityMetadata)arg0).getEntitySetname());
	}

	public String getEntitySetname() {
		return entitySetname;
	}

	public void setEntitySetname(String entitySetname) {
		this.entitySetname = entitySetname;
	}

	public String getEntityTypeName() {
		return entityTypeName;
	}

	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}

	public String getEntityLabel() {
		return entityLabel;
	}

	public void setEntityLabel(String entityLabel) {
		this.entityLabel = entityLabel;
	}

	public EntityDocumentation getDocumentation() {
		return documentation;
	}

	public void setDocumentation(EntityDocumentation documentation) {
		this.documentation = documentation;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	@Override
	public int compareTo(EntityMetadata o) {
		return entitySetname.compareTo(o.getEntitySetname()); 
	}
	

}

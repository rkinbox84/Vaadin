package com.sap.sf.odata.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EntityAttribute extends MetaAttributes implements Comparable<EntityAttribute>{
	private String propertyName;
	private String propertyType;
	private boolean proertyNullable;
	private boolean required;
	private boolean creatable;
	private boolean updatable;
	private boolean upsertable;
	private boolean visible;
	private boolean sortable;
	private boolean filterable;
	private int maxLength;
	private String label;
	private boolean selectable;
	private String pickListId;



	
	EntityNavigationAttribute navigationlAttribute;
	

	public  EntityAttribute(){
		
	}
	
	public  EntityAttribute(String name,String type,boolean nullable,boolean required,boolean creatable,boolean updatable,boolean upsertable,boolean visible,boolean sortable,boolean filtrable,String label ){
		this.propertyName = name;
		this.propertyType = type;
		this.proertyNullable = nullable;
		this.required = required;
		this.creatable = creatable;
		this.updatable = updatable;
		this.upsertable = upsertable;
		this.visible = visible;
		this.sortable = sortable;
		this.filterable = filtrable;
		this.label = label;
	}
	public  EntityAttribute(String name, String type, boolean nullable, int maxLength){
		this.propertyName = name;
		this.propertyType = type;
		this.proertyNullable = nullable;
		this.maxLength = maxLength;
	}
	
	public EntityAttribute(String name, String type, String label, int maxLength, boolean nullable, boolean required, boolean insertable, boolean updatable, boolean selectable, boolean upsertable, boolean sortable, boolean filterable) {
		this(name, type,nullable, maxLength);
		this.label = label;
		this.required = required;
		this.creatable = insertable;
		this.updatable = updatable;
		this.selectable = selectable;
		this.upsertable = upsertable;
		this.sortable = sortable;
		this.filterable = filterable;
	}
	
	public EntityAttribute(String name, String type, String label, int maxLength, boolean nullable, boolean required, boolean insertable, boolean updatable, boolean selectable, boolean upsertable, boolean sortable, boolean filterable, String picklistId) {
		this(name, type, label, maxLength, nullable, required, insertable, updatable, selectable, upsertable, sortable, filterable);
		if (picklistId != null && picklistId.trim().length() > 0){
			this.pickListId = picklistId;
		}
		
	}
	
	public EntityAttribute(String name, String type, String label, int maxLength, boolean nullable, boolean required, boolean insertable, boolean updatable, boolean selectable, boolean upsertable, boolean sortable, boolean filterable, String picklistId, boolean visible) {
		this(name, type, label, maxLength, nullable, required, insertable, updatable, selectable, upsertable, sortable, filterable, picklistId);
		this.visible = visible;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof EntityAttribute)) return false;
		
		return propertyName.equals(((EntityAttribute)obj).getPropertyName());
	}
	
	@Override
	public int hashCode() {
		return propertyName.hashCode();
	}

	public String getPropertyName() {
		
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public boolean isProertyNullable() {
		return proertyNullable;
	}

	public void setProertyNullable(boolean proertyNullable) {
		this.proertyNullable = proertyNullable;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isCreatable() {
		return creatable;
	}

	public void setisCreatable(boolean insertable) {
		this.creatable = insertable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
	}

	public boolean isUpsertable() {
		return upsertable;
	}

	public void setUpsertable(boolean upsertable) {
		this.upsertable = upsertable;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int compareTo(EntityAttribute o) {
		return propertyName.compareTo(o.getPropertyName());
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public String getPickListId() {
		return pickListId;
	}

	public void setPickListId(String pickListId) {
		this.pickListId = pickListId;
	}

	public EntityNavigationAttribute getNavigationlAttribute() {
		return navigationlAttribute;
	}

	public void setNavigationlAttribute(EntityNavigationAttribute navigationlAttribute) {
		this.navigationlAttribute = navigationlAttribute;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Method to return key value map of PropertyMap
	 * Key - Property Name
	 * Value - Property Object
	 * @param ea
	 * @return
	 */
	public Map<String, Object> getPropertyKeyValueMap(List<EntityAttribute> ea) {

		Map<String, Object> entitytypePropMap = new HashMap<String, Object>();

		for (EntityAttribute eaObj : ea) {
			entitytypePropMap.put(eaObj.getPropertyName(), eaObj);
		}

		return entitytypePropMap;

	}
}
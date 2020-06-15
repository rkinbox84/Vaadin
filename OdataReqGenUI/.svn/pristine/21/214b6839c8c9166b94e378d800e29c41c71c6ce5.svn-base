package com.sap.sf.odata.meta;

public class EntityNavigationAttribute extends MetaAttributes implements Comparable<EntityNavigationAttribute> {
	private String propertyname;
	private String relationship;
	private String fromRole;
	private String toRole;
	
	private String fromRoleEntityset;
	private String toRoleEntityset;
	
	private String fieldControl;
	private String label;
	private boolean required;
	private boolean insertable;
	private boolean updatable;
	private boolean selectable;
	private boolean upsertable;
	private boolean sortable;
	private boolean filterable; 
	private boolean visible;
	private int maxLength;
	private String pickListId;

	public EntityNavigationAttribute(){
		super();
	}
			
	public EntityNavigationAttribute(String name, String relationship, String fromRole, String toRole, String fieldControl, String label){
		this.propertyname = name;
		this.relationship = relationship;
		this.fromRole = fromRole;
		this.toRole = toRole;
		this.fieldControl = fieldControl;
		this.label = label;
	}
	
	public EntityNavigationAttribute(String name, String relationship, String fromRole, String toRole, String fieldControl, String label, boolean required, boolean insertable, boolean updatable, boolean selectable, boolean upsertable, boolean sortable, boolean filterable, boolean visible, int maxLength){
		this(name, relationship, fromRole, toRole, fieldControl, label);
		this.required = required;
		this.insertable = insertable;
		this.updatable = updatable;
		this.selectable = selectable;
		this.upsertable = upsertable;
		this.sortable = sortable;
		this.filterable = filterable;
		this.visible = visible;
		this.maxLength = maxLength;
	}
	

	public String getPropertyname() {
		return propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getFromRole() {
		return fromRole;
	}

	public void setFromRole(String fromRole) {
		this.fromRole = fromRole;
	}

	public String getToRole() {
		return toRole;
	}

	public void setToRole(String toRole) {
		this.toRole = toRole;
	}

	public String getFieldControl() {
		return fieldControl;
	}

	public void setFieldControl(String fieldControl) {
		this.fieldControl = fieldControl;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean getUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	public String getPickListId() {
		return pickListId;
	}

	public void setPickListId(String pickListId) {
		this.pickListId = pickListId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof EntityNavigationAttribute)) return false;
		
		return propertyname.equals(((EntityNavigationAttribute)obj).getPropertyname());
	}

	@Override
	public int compareTo(EntityNavigationAttribute o) {
		return propertyname.compareTo(o.getPropertyname());
	}

	public String getFromRoleEntityset() {
		return fromRoleEntityset;
	}

	public void setFromRoleEntityset(String fromRoleEntityset) {
		this.fromRoleEntityset = fromRoleEntityset;
	}

	public String getToRoleEntityset() {
		return toRoleEntityset;
	}

	public void setToRoleEntityset(String toRoleEntityset) {
		this.toRoleEntityset = toRoleEntityset;
	}

}

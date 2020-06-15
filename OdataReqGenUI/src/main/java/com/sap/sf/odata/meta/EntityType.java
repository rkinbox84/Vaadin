package com.sap.sf.odata.meta;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityType extends MetaAttributes implements Comparable<EntityType> {
	private String entityNamespace;
	private String entityTypeName;
	private List<String> keys;
	
	private List<EntityAttribute> attributeList;
	private List<EntityNavigationAttribute> navigationAttributeList;
	
	public EntityType(){
		super();
		keys = new ArrayList<String>();
		attributeList = new ArrayList<EntityAttribute>();
		navigationAttributeList = new ArrayList<EntityNavigationAttribute>();
	}
	
	public EntityType(String namespace, String typeName, List<String> keys, List<EntityAttribute> aList, List<EntityNavigationAttribute> naList){
		this();
		this.entityNamespace = namespace;
		this.entityTypeName = typeName;
		this.keys = keys;
		this.attributeList = aList;
		this.navigationAttributeList = naList;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null) return false;
		if (!(arg0 instanceof EntityType)) return false;
		return entityTypeName.equals(((EntityType)arg0).getEntityTypeName());
	}

	public String getEntityNamespace() {
		return entityNamespace;
	}

	public void setEntityNamespace(String entityNamespace) {
		this.entityNamespace = entityNamespace;
	}

	public String getEntityTypeName() {
		return entityTypeName;
	}

	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public List<EntityAttribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<EntityAttribute> attributeList) {
		this.attributeList = attributeList;
	}
	
	public void addEntityAttribute(EntityAttribute ea){
		this.attributeList.add(ea);
	}

	public List<EntityNavigationAttribute> getNavigationAttributeList() {
		return navigationAttributeList;
	}

	public void setNavigationAttributeList(List<EntityNavigationAttribute> navigationAttributeList) {
		this.navigationAttributeList = navigationAttributeList;
	}
	
	public void addEntityNavigationAttribute(EntityNavigationAttribute ena){
		this.navigationAttributeList.add(ena);
	}
	
	public List<EntityAttribute> getBusinessKeyAttributes(){
		List<EntityAttribute> keyAttributes = new ArrayList<EntityAttribute>();
		
		for (int i = 0; i < keys.size(); i++){
			keyAttributes.add(getAttributeForPropertyName(keys.get(i)));
		}
		
		return keyAttributes;
	}
	
	
	
	public boolean isEffectiveDatedEntity(){
		if (keys.contains("startDate"))
			return true;
		return false;
	}
	
	public boolean isMepdAllowed(){
		if (keys.contains("seqNumber"))
			return true;
		return false;
	}
	

	@Override
	public int compareTo(EntityType o) {
		return entityTypeName.compareTo(o.getEntityTypeName());
	}
	
	public EntityAttribute getAttributeForLabel(String labelName){
		if (labelName == null ) return null;
		for (int i = 0; i < attributeList.size(); i++){
			if (labelName.equals(attributeList.get(i).getLabel()))
				return attributeList.get(i);
		}
		return null;
	}
	
	public EntityAttribute getAttributeForPropertyName(String propertyName){
		int index = Collections.binarySearch(getAttributeList(), new EntityAttribute(propertyName, "", false, 100));
		if (index < 0 ) return null;
		EntityAttribute ea = this.getAttributeList().get(index);
		return ea;
	}
	
	public List<String> getNavigationalFieldNames(){
		List<String> navFields = new ArrayList<String>();
		
		for (int i = 0; i < navigationAttributeList.size(); i++){
			navFields.add(navigationAttributeList.get(i).getPropertyname());
		}
		
		return navFields;
	}
	
	/**
	 * Method to get Entity type as Key Value Pair map
	 * Key - Name of the entity Type
	 * Value - Entity Type Object
	 * @param em
	 * @return
	 */
	public Map<String, Object> getEntityTypeMap(List<EntityMetadata> em) {
		Map<String, Object> entitytypeMap = new HashMap<String, Object>();

		for (EntityMetadata emObj : em) {
			EntityType et = emObj.getEntityType();
			entitytypeMap.put(et.getEntityTypeName(), et);
		}

		return entitytypeMap;
	}

	/**
	 * Method will return Key value map of NavProperty
	 * Key- NavProperty Name
	 * Value - NavProperty Object
	 * @param eNavAttrib
	 * @return
	 */
	public Map<String, Object> getEntityTypeNavPropertyMap(List<EntityNavigationAttribute> eNavAttrib) {

		Map<String, Object> entitytypeNavPropMap = new HashMap<String, Object>();

		for (EntityNavigationAttribute eaNavObj : eNavAttrib) {
			entitytypeNavPropMap.put(eaNavObj.getPropertyname(), eaNavObj);
		}

		return entitytypeNavPropMap;

	}





	public Map<String, Object> getAssociationMap(List<Association> associationList) {
		Map<String, Object> associationMap = new HashMap<String, Object>();

		for (Association associationObj : associationList) {

			associationMap.put(associationObj.getAssociationName(), associationObj);
		}

		return associationMap;
	}
}
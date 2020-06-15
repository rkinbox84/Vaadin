package com.sap.sf.utilClass;

import java.util.ArrayList;
import java.util.List;

import com.sap.sf.beanClass.EntityFieldAttributes;
import com.sap.sf.beanClass.EntityList;
import com.sap.sf.beanClass.FieldsTypeList;
import com.sap.sf.odata.meta.EntityAttribute;
import com.sap.sf.odata.meta.EntityMetadata;
import com.sap.sf.odata.meta.EntityType;

public class FetchFields {

	public EntityList getQualifiedFields(List<EntityMetadata> metaList, String opr) {
		EntityList entityList = new EntityList();

		for (EntityMetadata em : metaList) {

			EntityType et = em.getEntityType();
			
			if(et.getEntityTypeName().equals("Background_Community")){
				System.out.println(et.getEntityTypeName());
			}

			List<EntityAttribute> attributeList = et.getAttributeList();
			FieldsTypeList fieldTypeList = new FieldsTypeList();
			List<String> keys = et.getKeys();
			List<EntityFieldAttributes> required = new ArrayList<EntityFieldAttributes>();
			List<EntityFieldAttributes> optional = new ArrayList<EntityFieldAttributes>();
			List<EntityFieldAttributes> keyList = new ArrayList<EntityFieldAttributes>();

			for (EntityAttribute eaL : attributeList) {

				EntityFieldAttributes efA = new EntityFieldAttributes();
				EntityFieldAttributes efAKey = new EntityFieldAttributes();

				boolean isEligibleField = isEligibleField(eaL, opr);

				if (isEligibleField) {
					if (eaL.isRequired()) {
						//Donot add keys to required or optional fields since its not allowed to modify
						if (keys.contains(eaL.getPropertyName()) && opr.equals(FMWConstants.ODATA_UPDATE)) {
							efAKey.setFieldName(eaL.getPropertyName());
							efAKey.setDataType(eaL.getPropertyType());
							efAKey.setValue("");
							keyList.add(efAKey);
						}else if(keys.contains(eaL.getPropertyName()) && opr.equals(FMWConstants.ODATA_DELETE)){
							efAKey.setFieldName(eaL.getPropertyName());
							efAKey.setDataType(eaL.getPropertyType());
							efAKey.setValue("");
							keyList.add(efAKey);
						}else {
							efA.setFieldName(eaL.getPropertyName());
							efA.setDataType(eaL.getPropertyType());
							efA.setValue("");
							required.add(efA);
						}
					} else {

						if (keys.contains(eaL.getPropertyName())&& opr.equals(FMWConstants.ODATA_UPDATE)) {
							efAKey.setFieldName(eaL.getPropertyName());
							efAKey.setDataType(eaL.getPropertyType());
							efAKey.setValue("");
							keyList.add(efAKey);
						}else{
							efA.setFieldName(eaL.getPropertyName());
							efA.setDataType(eaL.getPropertyType());
							efA.setValue("");
							optional.add(efA);
						}
					}
				}

			}

			fieldTypeList.setFieldTypeList("required", required);
			fieldTypeList.setFieldTypeList("optional", optional);
			fieldTypeList.setFieldTypeList("keys", keyList);

			entityList.setEntityList(et.getEntityTypeName(), fieldTypeList);
			FrameworkUtils.writeObjectToJsonFile("C:/RAVI/Work/MyTools/ReqGenJsonFiles/",
					et.getEntityTypeName() + ".json", fieldTypeList);

		}
		
		return entityList;
	}

	public boolean isEligibleField(EntityAttribute eaL, String opr) {

		if (opr.equals(FMWConstants.ODATA_CREATE)) {
			return eaL.isCreatable() && eaL.isVisible();
		} else if (opr.equals(FMWConstants.ODATA_UPDATE)) {
			return eaL.isUpdatable() && eaL.isVisible();
		}else if (opr.equals(FMWConstants.ODATA_UPSERT)) {
			return eaL.isUpsertable() && eaL.isVisible();
		}else if (opr.equals(FMWConstants.ODATA_DELETE)) {
			return true;
		}

		return false;
	}

}

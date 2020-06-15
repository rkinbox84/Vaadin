/**
 * 
 */
package com.sap.sf.utilClass;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.sap.sf.beanClass.EntityFieldAttributes;
import com.sap.sf.beanClass.EntityList;
import com.sap.sf.beanClass.FieldsTypeList;

/**
 * @author I309192
 *
 */
public class OdataReqGenHelper {

	public String getReq(String entity, String type, EntityList el){
		OdataReqGenHelper reqH = new OdataReqGenHelper();
		if(type.equals(FMWConstants.ODATA_CREATE)||type.equals(FMWConstants.ODATA_UPDATE)||type.equals(FMWConstants.ODATA_UPSERT)){
			return reqH.getCreateQuery(entity, el);
		}
		return "No Data";
	}
	
	public String getCreateQuery(String entity, EntityList el){
		Map<String,FieldsTypeList> entityList = el.getEntityList();
		
		String reqString = "";
			System.out.println("Entity Name: "+ entity);
			FieldsTypeList ftL = entityList.get(entity);
			reqString = "{";
			Map<String, List<EntityFieldAttributes>> fieldTypeList = ftL.getFieldTypeList();
			
			List<EntityFieldAttributes> req = fieldTypeList.get("required");
			List<EntityFieldAttributes> op = fieldTypeList.get("optional");
			
			int length = req.size();
			int count = 1;
			
			for(EntityFieldAttributes eaObj: req){
				String field = eaObj.getFieldName();
				String value="";
				if(eaObj.getDataType().equals("Edm.DateTime")){
					value = "\\/Date("+FrameworkUtils.convertToEpocDate(eaObj.getValue())+")\\/";
				}else {
					value = eaObj.getValue();
				}
				if(count==1){
					reqString = reqString+"\"" + field + "\":\""+value+"\"";
				}else if(eaObj.getDataType().equals("Edm.Boolean")){
					reqString = reqString + ",\""+ field + "\":"+value;
				}else{
					reqString = reqString + ",\""+ field + "\":\""+value+"\"";
				}
				count++;
			}
			
			int opCount = 1;
			int opLen = 0;
			for(EntityFieldAttributes eaObj: op){
				if(!eaObj.getValue().equals("")){
					opLen ++;
				}
			}
			
			if(op!=null && opLen > 0){
				
				for(EntityFieldAttributes eaObj: op){
					String field = eaObj.getFieldName();
					String value="";
					if(!eaObj.getValue().equals("")){
						if(eaObj.getDataType().equals("Edm.DateTime")){
							value = "\\/Date("+FrameworkUtils.convertToEpocDate(eaObj.getValue())+")\\/";
						}else {
							value = eaObj.getValue();
						}
					
					
					if(opCount == opLen && eaObj.getDataType().equals("Edm.Boolean")){
						reqString = reqString + ",\""+ field + "\":"+value+"}";
					}else if(opCount == opLen){
						reqString = reqString + ",\""+ field + "\":\""+value+"\"}";					
					}else if(eaObj.getDataType().equals("Edm.Boolean")){
						reqString = reqString + ",\""+ field + "\":"+value;
					}else{
						reqString = reqString + ",\""+ field + "\":\""+value+"\"";
					}
					opCount++;
					}
				}
			}else {
				reqString = reqString + "}";
			}
			
		
		
		System.out.println("The Request String: "+ reqString);
		
		return reqString;
	}
	
	public String getUpdateURL(String envURL, String entityName, List<EntityFieldAttributes> keys) throws ParseException{
		String updateURL="";
		updateURL = envURL + "/"+entityName+"(";
		String keyStr="";
		

		
		for(EntityFieldAttributes ea: keys){
			if(keyStr.equals("")){
				if(ea.getDataType().equals(FMWConstants.DATE)){
					String formatedValue = FrameworkUtils.convertDateToQuery(ea.getValue());
					keyStr = ea.getFieldName()+"=datetime'"+formatedValue+"'";
				}else {
					keyStr = ea.getFieldName()+ "='"+ea.getValue()+"'";
				}
				
			}else {
			if(ea.getDataType().equals(FMWConstants.DATE)){
				String formatedValue = FrameworkUtils.convertDateToQuery(ea.getValue());
				keyStr = keyStr + "," + ea.getFieldName()+"=datetime'"+formatedValue+"'";
			}else {
				keyStr = keyStr + "," + ea.getFieldName()+ "='"+ea.getValue()+"'";
			}
			
			} 
		}
		
		keyStr = keyStr+")?";
		updateURL = updateURL + keyStr;
		System.out.println("Update URL: " + updateURL);
		
		return updateURL;
	}
}

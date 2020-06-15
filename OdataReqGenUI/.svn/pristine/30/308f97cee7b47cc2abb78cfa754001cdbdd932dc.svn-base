/**
 * 
 */
package com.sap.sf.odata.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author I309192
 *
 */
public class FunctionImport extends MetaAttributes{

	private String functionImportName;
	private String functionImportReturnType;
	private String functionImportEntitySet;
	private String functionImportHttpMethod;
	
	List<FunctionImportParameter> functionImportParameters;
	
	//Map<String, Object> allElements;

	public FunctionImport() {
		functionImportParameters = new ArrayList<FunctionImportParameter>();
		
		this.functionImportName = null;
		this.functionImportReturnType = null;
		this.functionImportEntitySet = null;
		this.functionImportHttpMethod = null;

	}
	
	public FunctionImport(String functionImportName, String functionImportReturnType, String functionImportEntitySet,
			String functionImportHttpMethod) {
		this();
		this.functionImportName = functionImportName;
		this.functionImportReturnType = functionImportReturnType;
		this.functionImportEntitySet = functionImportEntitySet;
		this.functionImportHttpMethod = functionImportHttpMethod;

	}
	
	/**
	 * @param functionImportName
	 * @param functionImportReturnType
	 * @param functionImportEntitySet
	 * @param functionImportHttpMethod
	 * @param functionImportParameters
	 * @param functionImportAttribs
	 */
	public FunctionImport(String functionImportName, String functionImportReturnType, String functionImportEntitySet,
			String functionImportHttpMethod,Map<String, Object> newAttributes) {
		this();
		this.functionImportName = functionImportName;
		this.functionImportReturnType = functionImportReturnType;
		this.functionImportEntitySet = functionImportEntitySet;
		this.functionImportHttpMethod = functionImportHttpMethod;
		this.functionImportParameters = functionImportParameters;
		this.allAttributes = newAttributes;
	}

	public void addfunctionImportParameters(FunctionImportParameter functionImportParameters){
		
		this.functionImportParameters.add(functionImportParameters);
	}

	
	/**
	 * @return the functionImportName
	 */
	public String getFunctionImportName() {
		return functionImportName;
	}

	/**
	 * @param functionImportName the functionImportName to set
	 */
	public void setFunctionImportName(String functionImportName) {
		this.functionImportName = functionImportName;
	}

	/**
	 * @return the functionImportReturnType
	 */
	public String getFunctionImportReturnType() {
		return functionImportReturnType;
	}

	/**
	 * @param functionImportReturnType the functionImportReturnType to set
	 */
	public void setFunctionImportReturnType(String functionImportReturnType) {
		this.functionImportReturnType = functionImportReturnType;
	}

	/**
	 * @return the functionImportEntitySet
	 */
	public String getFunctionImportEntitySet() {
		return functionImportEntitySet;
	}

	/**
	 * @param functionImportEntitySet the functionImportEntitySet to set
	 */
	public void setFunctionImportEntitySet(String functionImportEntitySet) {
		this.functionImportEntitySet = functionImportEntitySet;
	}

	/**
	 * @return the functionImportHttpMethod
	 */
	public String getFunctionImportHttpMethod() {
		return functionImportHttpMethod;
	}

	/**
	 * @param functionImportHttpMethod the functionImportHttpMethod to set
	 */
	public void setFunctionImportHttpMethod(String functionImportHttpMethod) {
		this.functionImportHttpMethod = functionImportHttpMethod;
	}

	/**
	 * @return the functionImportParameters
	 */
	public List<FunctionImportParameter> getFunctionImportParameters() {
		return functionImportParameters;
	}

	/**
	 * @param functionImportParameters the functionImportParameters to set
	 */
	public void setFunctionImportParameters(List<FunctionImportParameter> functionImportParameters) {
		this.functionImportParameters = functionImportParameters;
	}

	/**
	 * This method to convert given FunctionImport object to Key Value Map
	 * Key - name of the FunctionImport element
	 * Value - FunctionImport Object
	 * @param fiObjList
	 * @return
	 */
	public HashMap<String, Object> getFunctionImportElementMap(List<FunctionImport> fiObjList) {

		HashMap<String, Object> fiObjMap = new HashMap<String, Object>();

		for (FunctionImport src : fiObjList) {

			fiObjMap.put(src.getFunctionImportName(), (Object) src);
		}

		return fiObjMap;
	}
	
	/**
	 * Method to return FI parameters as KeyValue Map
	 * Key - Name of the FunctionImportParameter
	 * Value - FI Parameter Object
	 * @param fiObjList
	 * @return
	 */
	
	public HashMap<String, Object> getFunctionImportParamElementMap(List<FunctionImportParameter> fiObjList) {

		HashMap<String, Object> fiObjMap = new HashMap<String, Object>();
		FunctionImportParameter target;

		for (FunctionImportParameter src : fiObjList) {

			fiObjMap.put(src.getParameterName(), (Object) src);
		}

		return fiObjMap;
	}
	
	
	
	
}

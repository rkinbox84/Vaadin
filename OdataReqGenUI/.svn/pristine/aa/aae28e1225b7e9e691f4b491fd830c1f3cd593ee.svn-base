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
public class FunctionImportParameter {

	private String parameterName;
	private String parameterType;
	private Map<String,Object> allParameters;
	
	
	
	/**
	 * @return the allParameters
	 */
	public Map<String, Object> getAllParameters() {
		return allParameters;
	}

	/**
	 * @param allParameters the allParameters to set
	 */
	public void setAllParameters(Map<String, Object> allParameters) {
		this.allParameters = allParameters;
	}

	public FunctionImportParameter(){
		this.allParameters = new HashMap<String,Object>();
	}
	
	public FunctionImportParameter(String parameterName, String parameterType) {
		this();
		this.parameterName = parameterName;
		this.parameterType = parameterType;
		
	}
	
	
	/**
	 * @param parameterName
	 * @param parameterType
	 * @param newParameters
	 */
	public FunctionImportParameter(String parameterName, String parameterType, Map<String, Object> allParameters) {
		this();
		this.parameterName = parameterName;
		this.parameterType = parameterType;
		this.allParameters = allParameters;
	}



	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}
	/**
	 * @param parameterName the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	/**
	 * @return the parameterType
	 */
	public String getParameterType() {
		return parameterType;
	}
	/**
	 * @param parameterType the parameterType to set
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	

	
}

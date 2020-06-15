/**
 * 
 */
package com.sap.sf.odata.meta;

import java.util.Map;

/**
 * @author I309192
 *
 */
public class AssociationType extends MetaAttributes{

	private String type;
	private String multiplicity;
	private String role;
	
	
	public AssociationType(){
		
	}
	
	public AssociationType(String type, String mul,String role,Map<String,Object> allAttrib){
		this.type = type;
		this.multiplicity = mul;
		this.allAttributes = allAttrib;
	}
	/**
	 * @param type
	 * @param multiplicity
	 * @param role
	 */
	public AssociationType(String type, String multiplicity, String role) {
		super();
		this.type = type;
		this.multiplicity = multiplicity;
		this.role = role;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the multiplicity
	 */
	public String getMultiplicity() {
		return multiplicity;
	}
	/**
	 * @param multiplicity the multiplicity to set
	 */
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	
}

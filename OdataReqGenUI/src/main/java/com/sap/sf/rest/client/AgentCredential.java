package com.sap.sf.rest.client;

import com.sap.sf.rest.client.AgentConstants.AUTHENTICAITON_METHODS;

/**
 * Simple POJO for handling Http Basic Authentication.
 * @author ganeshkumar.venugopalan@sap.com
 */
public class AgentCredential {
	
	AgentConstants.AUTHENTICAITON_METHODS authType=AUTHENTICAITON_METHODS.BASIC;
	String userName;
	String password;
	
	public AgentCredential(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public AgentCredential(String userName, String password, AUTHENTICAITON_METHODS authType){
		this(userName,password);
		this.authType = authType;	
	}
	/**
	 * @return the authType
	 */
	public AgentConstants.AUTHENTICAITON_METHODS getAuthType() {
		return authType;
	}
	/**
	 * @param authType the authType to set
	 */
	public void setAuthType(AgentConstants.AUTHENTICAITON_METHODS authType) {
		this.authType = authType;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

}


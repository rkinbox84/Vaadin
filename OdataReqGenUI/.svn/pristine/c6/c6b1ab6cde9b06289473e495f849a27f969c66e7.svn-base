package com.sap.sf.beanClass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EnvDetails")
public class EnvDetails {

	String environment;
	String instanceName;
	String userName;
	String pwd;
	boolean refreshMeta;
	
	
	/**
	 * @return the refreshMeta
	 */
	public boolean isRefreshMeta() {
		return refreshMeta;
	}

	/**
	 * @param refreshMeta the refreshMeta to set
	 */
	public void setRefreshMeta(boolean refreshMeta) {
		this.refreshMeta = refreshMeta;
	}

	public EnvDetails(String environment, String instanceName, String userName, String pwd, boolean refreshMeta) {
		this.environment = environment;
		this.instanceName = instanceName;
		this.userName = userName;
		this.pwd = pwd;
		this.refreshMeta = refreshMeta;
	}

	public EnvDetails(){
		
	}
	
	/**
	 * @param environment
	 * @param instanceName
	 * @param userName
	 * @param pwd
	 */
	public EnvDetails(String environment, String instanceName, String userName, String pwd) {
		this.environment = environment;
		this.instanceName = instanceName;
		this.userName = userName;
		this.pwd = pwd;
	}
	/**
	 * @return the environment
	 */
	@XmlElement(required=true)
	public String getEnvironment() {
		return environment;
	}
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	/**
	 * @return the instanceName
	 */
	@XmlElement(required=true)
	public String getInstanceName() {
		return instanceName;
	}
	/**
	 * @param instanceName the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	/**
	 * @return the userName
	 */
	@XmlElement(required=true)
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
	 * @return the pwd
	 */
	@XmlElement(required=true)
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}

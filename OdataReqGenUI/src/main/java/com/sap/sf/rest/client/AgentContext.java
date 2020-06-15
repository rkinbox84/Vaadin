package com.sap.sf.rest.client;


import java.util.HashMap;
import java.util.Map;

/**
 * Context for http request.  POJO object that can be passed to IHttpAgent.
 * This includes Url, Http_Method, Headers(accept, content-type and etc.), queryParams and Credential object
 * @author ganeshkumar.venugopalan@sap.com
 */
public class AgentContext {
	
	public static enum HTTP_METHOD {GET, POST, PUT, DELETE, HEAD, OPTIONS};
	
	HTTP_METHOD method;
	
	String url;
	
	Object payload;
	
	Map<String, String> headers;
	
	Map<String, String> queryParams;
	
	AgentCredential credentials;
	
//	boolean attachParamsToUrl = false;
	
	public AgentContext(){
		headers = new HashMap<String, String>();
		queryParams = new HashMap<String, String>();
	}
	
	public AgentContext(String payLoad){
		this.payload = payLoad;
	}
	public AgentContext(String url, HTTP_METHOD method, Map<String, String> headers, Map<String, String> queryParams, Object payload){
		this();
		this.url = url;
		this.method = method;
		if (headers != null)
			this.headers = headers;
		
		if (queryParams != null)
			this.queryParams = queryParams;
		
		this.payload = payload;
	}
	
	public AgentContext(String url, HTTP_METHOD method, Map<String, String> headers, Map<String, String> queryParams, Object payload, AgentCredential credentials){
		this(url, method, headers, queryParams,payload);
		this.credentials = credentials;
	}

	/**
	 * @return the method
	 */
	public HTTP_METHOD getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(HTTP_METHOD method) {
		this.method = method;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the payload
	 */
	public Object getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(Object payload) {
		this.payload = payload;
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	public void addHeader(String key, String value){
		if (key != null && value != null)
			headers.put(key, value);
	}

	/**
	 * @return the queryParams
	 */
	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	/**
	 * @param queryParams the queryParams to set
	 */
	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	
	public void addQueryParam(String key, String value){
		if (key != null && value != null)
			queryParams.put(key, value);
	}

	/**
	 * @return the credentials
	 */
	public AgentCredential getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(AgentCredential credentials) {
		this.credentials = credentials;
	}

//	public boolean isAttachParamsToUrl() {
//		return attachParamsToUrl;
//	}

//	public void setAttachParamsToUrl(boolean attachParamsToUrl) {
//		this.attachParamsToUrl = attachParamsToUrl;
//	}
	
	

}
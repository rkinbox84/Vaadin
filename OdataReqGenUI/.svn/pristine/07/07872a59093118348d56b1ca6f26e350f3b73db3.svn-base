package com.sap.sf.rest.client;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Response of a http request.
 * @author ganeshkumar.venugopalan@sap.com
 */
public class HttpResponse {
	
	int status;
	String url;
	
	Map<String, String> responseHeaders;
	
	Object payload;
	
	AgentContext requestObject;
	
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
	
	public HttpResponse(){
		responseHeaders = new LinkedHashMap<String, String>();
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the responseHeaders
	 */
	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	/**
	 * @param responseHeaders the responseHeaders to set
	 */
	public void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
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

	public AgentContext getRequestObject() {
		return requestObject;
	}

	public void setRequestObject(AgentContext requestObject) {
		this.requestObject = requestObject;
	}

}

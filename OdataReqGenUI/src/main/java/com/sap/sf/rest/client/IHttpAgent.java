package com.sap.sf.rest.client;

/**
 * Contract for Http Agent
 * @author ganeshkumar.venugopalan@sap.com
 */
public interface IHttpAgent {
	
	public HttpResponse getResponse(AgentContext context) throws AppException;

}

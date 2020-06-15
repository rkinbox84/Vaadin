package com.sap.sf.rest.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.auth.UsernamePasswordCredentials;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.sap.sf.rest.client.AgentContext.HTTP_METHOD;

/**
 * HtmlUnit based User Agent.
 * 
 * @author ganeshkumar.venugopalan@sap.com
 */
public class HuAgent implements IHttpAgent {

	WebClient client;

	public HuAgent() {
		client = new WebClient();
	}

	public HuAgent(String proxyHost, int proxyPort) {
		client = new WebClient(BrowserVersion.getDefault(), proxyHost, proxyPort);
	}

	public HuAgent(String proxyHost, int proxyPort, boolean simulateWeb) {
		this(proxyHost, proxyPort);

		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setRedirectEnabled(true);
		client.getOptions().setThrowExceptionOnScriptError(false);
		client.getOptions().setCssEnabled(true);
		// client.getOptions().setThrowExceptionOnFailingStatusCode(false);
	}

	@Override
	public HttpResponse getResponse(AgentContext context) throws AppException {
		return responseFromPage(hitServer(context));
	}

	private HttpResponse responseFromPage(ResponseObject responseObject) throws AppException {
		HttpResponse response = new HttpResponse();
		response.setStatus(responseObject.getStatusCode());
		Page pg = responseObject.getPg();
		if (responseObject.getStatusCode() >= 200 && responseObject.getStatusCode() < 400) {
			
			if (pg != null) {
				response.setUrl(pg.getUrl().toString());
				//System.out.println("Web Url - " + pg.getUrl().toString());
				List<NameValuePair> headers = pg.getWebResponse().getResponseHeaders();
				for (int i = 0; i < headers.size(); i++) {
					NameValuePair nvp = headers.get(i);
					//System.out.println(nvp.getName() + " = " + nvp.getValue());
					response.getResponseHeaders().put(nvp.getName(), nvp.getValue());
				}
				response.setPayload(pg.getWebResponse().getContentAsString());
				// BAODataLog.info("Response payload - "+response.getPayload());

				response.setRequestObject(explorePage(pg));

			} else {
				response.setStatus(responseObject.getStatusCode());
				response.setPayload(responseObject.getMessage());
				response.setUrl(responseObject.getReqURL());
				//Setting the request body when there is failure
				response.setRequestObject(new AgentContext(responseObject.getReqBody()));
			}

		} else {
			response.setStatus(responseObject.getStatusCode());
			response.setPayload(responseObject.getMessage());
			response.setUrl(responseObject.getReqURL());
			//Setting the request body when there is failure
			response.setRequestObject(new AgentContext(responseObject.getReqBody()));
			
		}

		return response;
	}

	private AgentContext explorePage(Page pg) {
		WebResponse wresponse = pg.getWebResponse();
		WebRequest requestObject = wresponse.getWebRequest();
		AgentContext context = new AgentContext();
		context.setUrl(requestObject.getUrl().toExternalForm());
		context.setMethod(HTTP_METHOD.GET);
		context.setHeaders(requestObject.getAdditionalHeaders());
		List<NameValuePair> nvp = requestObject.getRequestParameters();

		for (int i = 0; i < nvp.size(); i++) {
			String name = nvp.get(i).getName();
			String value = nvp.get(i).getValue();

			context.addQueryParam(name, value);
		}
		context.setPayload(requestObject.getRequestBody());

		return context;

	}

	private ResponseObject hitServer(AgentContext context) {
		String url = context.getUrl();
		HTTP_METHOD method = context.getMethod();
		AgentCredential ac = context.getCredentials();
		Map<String, String> headers = context.getHeaders();
		Map<String, String> params = context.getQueryParams();
		Object payload = context.getPayload();

		/*
		 * if ((method == HTTP_METHOD.POST || method == HTTP_METHOD.PUT) &&
		 * context.isAttachParamsToUrl() && params != null && params.size()>0){
		 * url = url+"?";
		 * 
		 * Iterator<String> keys = params.keySet().iterator(); int ctr = 0;
		 * while(keys.hasNext()){ String key = keys.next(); String val =
		 * params.get(key);
		 * 
		 * if (ctr != 0) url = url + "&";
		 * 
		 * url = url + key+"="+val; ctr++; }
		 * 
		 * }
		 */

		// BAODataLog.info("Url - "+url);

		WebRequest request = null;
		try {
			request = new WebRequest(new URL(url));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		switch (method) {
		case GET: {
			request.setHttpMethod(HttpMethod.GET);
			break;
		}
		case POST: {
			request.setHttpMethod(HttpMethod.POST);
			if (payload != null)
				request.setRequestBody(payload.toString());
			break;
		}
		case PUT: {
			request.setHttpMethod(HttpMethod.PUT);
			if (payload != null)
				request.setRequestBody(payload.toString());
			break;
		}
		case HEAD: {
			break;
		}
		case OPTIONS: {
			break;
		}
		case DELETE: {
			request.setHttpMethod(HttpMethod.DELETE);
			break;
		}
		default:
			break;
		}

		Iterator<String> headerIter = headers.keySet().iterator();
		while (headerIter.hasNext()) {
			String key = headerIter.next();
			String val = headers.get(key);
			request.setAdditionalHeader(key, val);
		}

		// if ((method == HTTP_METHOD.POST || method == HTTP_METHOD.PUT) &&
		// !context.isAttachParamsToUrl()){

		Iterator<String> paramIter = params.keySet().iterator();
		List<NameValuePair> lst = new ArrayList<NameValuePair>();
		while (paramIter.hasNext()) {
			String key = paramIter.next();
			String val = params.get(key);
			NameValuePair nvp = new NameValuePair(key, val);
			lst.add(nvp);
		}
		if (lst.size() > 0)
			request.setRequestParameters(lst);
		// }

		if (ac != null) {
			request.setCredentials(new UsernamePasswordCredentials(ac.getUserName(), ac.getPassword()));
		}

		ResponseObject ro = new ResponseObject(200, "", null);
		//Setting request Body


		Page pg = null;
		//WebResponse webresponse=null;
		try {
//			webresponse = client.loadWebResponse(request);
			pg = client.getPage(request);
			
			//webresponse.get
			
			if (pg == null) {
				ro.setStatusCode(204);
				ro.setMessage("Empty Response");
				ro.setPg(null);
				ro.setReqURL(request.getUrl().toString());
				ro.setReqBody(request.getRequestBody());
			} else {
				ro.setStatusCode(pg.getWebResponse().getStatusCode());
				ro.setPg(pg);
			}
			/* client.getCache().clear(); */
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			//webresponse = client.loadWebResponse(request);			
			e.printStackTrace();
			ro.setMessage(e.getResponse().getContentAsString());
			ro.setStatusCode(e.getStatusCode());
			ro.setReqBody(request.getRequestBody());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ro.setStatusCode(500);
			ro.setMessage("httpCode:500 Encountered IO Excepion");
			//ro.setMessage(webresponse.getContentAsString());
			e.printStackTrace();
			ro.setReqBody(request.getRequestBody());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ro.setStatusCode(500);
			ro.setMessage("httpCode:500 Encountered  Excepion");
			//ro.setMessage(webresponse.getContentAsString());
			e.printStackTrace();
			ro.setReqBody(request.getRequestBody());
		}

		return ro;

	}

	private static class ResponseObject {
		int statusCode;
		String message;
		Page pg;
		String reqURL;
		String reqBody;

		/**
		 * @return the reqBody
		 */
		public String getReqBody() {
			return reqBody;
		}

		/**
		 * @param reqBody the reqBody to set
		 */
		public void setReqBody(String reqBody) {
			this.reqBody = reqBody;
		}

		/**
		 * @return the reqURL
		 */
		public String getReqURL() {
			return reqURL;
		}

		/**
		 * @param reqURL
		 *            the reqURL to set
		 */
		public void setReqURL(String reqURL) {
			this.reqURL = reqURL;
		}

		public ResponseObject(int statusCode, String message, Page pg) {
			this.statusCode = statusCode;
			this.message = message;
			this.pg = pg;
		}

		/**
		 * @return the statusCode
		 */
		public int getStatusCode() {
			return statusCode;
		}

		/**
		 * @param statusCode
		 *            the statusCode to set
		 */
		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		/**
		 * @return the pg
		 */
		public Page getPg() {
			return pg;
		}

		/**
		 * @param pg
		 *            the pg to set
		 */
		public void setPg(Page pg) {
			this.pg = pg;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @param message
		 *            the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}
	}

}

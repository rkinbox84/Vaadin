package com.sap.sf.sdk;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.sap.sf.odata.meta.EntityMetadata;
import com.sap.sf.odata.meta.MetadataParser;
import com.sap.sf.rest.client.AgentContext;
import com.sap.sf.rest.client.AgentContext.HTTP_METHOD;
import com.sap.sf.rest.client.AgentCredential;
import com.sap.sf.rest.client.AppException;
import com.sap.sf.rest.client.HttpResponse;
import com.sap.sf.rest.client.HuAgent;
import com.sap.sf.rest.client.IHttpAgent;

public class OdataServerSDK {

	IHttpAgent agent = null;
	private String url = null;
	private String company = null;
	private String username = null;
	private String password = null;
	private String proxyHost = null;
	private int proxyPort;
	private AgentCredential ac;
	private List<EntityMetadata> lstMetaData;
	Logger logger = null;
	private Map<String, String> metaMap;
	private boolean refreshMeta;

	private static Map<String, List<EntityMetadata>> metaDatas;
	
	static {
		metaDatas = new HashMap<String, List<EntityMetadata>>();
	}
	
	public OdataServerSDK() {
		logger = Logger.getLogger("OdataSDK");
		metaMap = new HashMap<String,String>();
	}

	public OdataServerSDK(String url, String company, String username, String password) {
		this();
		this.url = url;
		this.company = company;
		this.username = username;
		this.password = password;
	}

	public OdataServerSDK(String url, String company, String username, String password, String proxyHost, int proxyPort, boolean refreshMeta) {
		this(url, company, username, password);
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		this.refreshMeta = refreshMeta;
		// BAODataLog.info("Odata sdk instantiated");
	}

	// Constructor with DB Details

	public OdataServerSDK(String url, String company, String username, String password, String proxyHost, int proxyPort,
			String dbHostName, String dbUserName) {
		this(url, company, username, password);
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		// BAODataLog.info("Odata sdk instantiated");
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	public List<EntityMetadata> getMetadataAsObjects()
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, AppException {
		String urlString = url + ":" + company;

/*		if (lstMetaData != null)
			return lstMetaData;*/

		if (metaDatas.containsKey(urlString) && !this.refreshMeta) {
			lstMetaData = metaDatas.get(urlString);
			return lstMetaData;
		}

		MetadataParser parser = new MetadataParser();
		// BAODataLog.info("Metadata as objects is prepared");
		lstMetaData = parser.getMetatdata(getMetadataAsString());
		Collections.sort(lstMetaData);
		metaDatas.put(urlString, lstMetaData);
		System.out.println("Successfully Executed GetMetatData as Objects..");
		return lstMetaData;
	}

	public void refreshMetadata() throws AppException {

		logger.info("**Executing MetaData Refresh...");
		String refreshMetaDataURL = url + "/refreshMetadata";
		logger.info("**Going to Execute URL: " + refreshMetaDataURL);
		AgentContext context = new AgentContext(refreshMetaDataURL, HTTP_METHOD.GET, null, null, null);
		context.setCredentials(ac);

		HttpResponse response = agent.getResponse(context);
		if (response.getStatus() == 204) {
			logger.info("**Metadata refresh done sucessfully");
		} else {
			logger.info("**Metadata refresh failed. Unable to proceed further");
			throw new AppException("Refresh Metadata Failed", new Throwable());
		}

	}
	
	private void prepareUserAgent() throws AppException {
		// 1. Prepare agent
		if (agent == null) {
			// agent = new JerseyAgent();
			if (proxyHost != null && proxyHost.length() > 0)
				agent = new HuAgent(proxyHost, proxyPort);
			else
				agent = new HuAgent();

			ac = new AgentCredential(username + "@" + company, password);
			refreshMetadata();
		}

		// BAODataLog.info("Useragent for Odata invocation is created");
	}
	
	public String getMetadataAsString() throws AppException {

		if (metaMap.get(company + url) == null || this.refreshMeta) {
			prepareUserAgent();
			logger.info("** Executing Get Metadata");
			System.out.println("** Executing Get Metadata");
			String metaUrl = url + "/$metadata";

			logger.info("** The URL:" + metaUrl);
			System.out.println("** The URL:" + metaUrl);
			AgentContext context = new AgentContext(metaUrl, HTTP_METHOD.GET, null, null, null);
			// context.setCredentials(ac);

			HttpResponse response = agent.getResponse(context);

			if (response.getStatus() != 200) {
				logger.info("**Get Metadata failed. Status code returned :" + response.getStatus()
						+ "Unable to proceed further");
				throw new AppException("Get Metadata Failed", new Throwable());

			} else if (response.getPayload() == null) {
				logger.info("**Get Metadata failed. Empty Response Returned");
				throw new AppException("Get Metadata Failed. Empty Response Returned", new Throwable());

			}

			logger.info("** Succcessfully Executed Get MetaData");
			metaMap.put(company + url, (String) response.getPayload());

		}

		return metaMap.get(company + url);
	}

}

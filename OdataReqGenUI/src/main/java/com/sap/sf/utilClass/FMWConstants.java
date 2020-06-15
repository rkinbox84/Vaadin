/**
 * 
 */
package com.sap.sf.utilClass;

/**
 * @author I309192
 *
 */
public class FMWConstants {

	public static final String QACAND = "qacand";
	public static final String PREVIEW = "preview";
	public static final String AUTOCAND = "autocand";
	
	public static final String QACAND_URL ="https://qacand-api.lab-rot.ondemand.com/odata/v2/";
	public static final String PREVEW_URL="https://qapatchpreview-api.lab-rot.ondemand.com/odata/v2/";
	public static final String AUTOCAND_URL="https://qaautocand-api.lab-rot.ondemand.com/odata/v2/";
	
	//"10.137.110.8"
	public static final String PROXY_HOST="proxy.sin.sap.corp";
	public static final String PROXY_PORT="8080";
	
	public static final String ODATA_CREATE ="Create";
	public static final String ODATA_UPDATE ="Update";
	public static final String ODATA_UPSERT ="Upsert";
	public static final String ODATA_DELETE ="Delete";
	
	//Data types
	public static final String STRING ="Edm.String";
	public static final String DATE ="Edm.DateTime";
	public static final String INTEGER ="Edm.Int64";
	public static final String BOOLEAN ="Edm.Boolean";
	
	//Upsert Purge types
	public static final String PURGE_FULL ="full";
	public static final String PURGE_INC ="incremental";
}

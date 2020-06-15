package com.sap.sf.utilClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sap.sf.beanClass.EntityFieldAttributes;
import com.sap.sf.beanClass.EntityList;
import com.sap.sf.beanClass.EntityOperation;
import com.sap.sf.beanClass.EnvDetails;
import com.sap.sf.beanClass.FieldsTypeList;
import com.sap.sf.odata.meta.EntityMetadata;
import com.sap.sf.rest.client.AppException;
import com.sap.sf.sdk.OdataServerSDK;

public class FrameworkUtils {

	public static List<EntityMetadata> getMetadata(EnvDetails env)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, AppException {
		EntityList eL = new EntityList();

		// get entity MetaData
		String url = FrameworkUtils.getEnvURL(env.getEnvironment());
		String instance = env.getInstanceName();
		String userName = env.getUserName();
		String pwd = env.getPwd();
		boolean refreshMeta = env.isRefreshMeta();

		System.out.println("Env URL:" + url);
		System.out.println("Instance Name:" + instance);
		System.out.println("UserName:" + userName);
		System.out.println("pwd:" + pwd);
		System.out.println("RefreshMeta:" + refreshMeta);

		OdataServerSDK sdk = new OdataServerSDK(url, instance, userName, pwd, FMWConstants.PROXY_HOST,
				Integer.parseInt(FMWConstants.PROXY_PORT), refreshMeta);

		List<EntityMetadata> metaList = sdk.getMetadataAsObjects();

		return metaList;
	}

	public static List<EntityFieldAttributes> readFields(EntityOperation entityOpr, String field) {

		FieldsTypeList eList = new FieldsTypeList();
		EntityList el = null;
		String entity;
		String operation;

		entity = entityOpr.getEntity();
		operation = entityOpr.getOperation();
		el = entityOpr.getEl();
		eList = el.getEntityList().get(entity);
		return eList.getFieldTypeList().get(field);
	}

	public static String getEnvURL(String env) {

		if (env.toLowerCase().equals(FMWConstants.QACAND)) {
			return FMWConstants.QACAND_URL.trim();
		} else if (env.toLowerCase().equals(FMWConstants.PREVIEW)) {
			return FMWConstants.PREVEW_URL.trim();
		} else if (env.toLowerCase().equals(FMWConstants.AUTOCAND)) {
			return FMWConstants.AUTOCAND_URL.trim();
		}

		return null;
	}

	public static void writeObjectToJsonFile(String folder, String fileName, Object obj) {
		Gson gs = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gs.toJson(obj);

		String outputFolder = folder;
		File opFolder = new File(outputFolder);
		if (!opFolder.exists()) {
			opFolder.mkdirs();
		}

		String opFile = opFolder + "/" + fileName;

		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File(opFile)));
			bw.write(jsonString);

			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeJsonStringToFile(String folder, String fileName, String data) {

		String outputFolder = folder;
		File opFolder = new File(outputFolder);
		if (!opFolder.exists()) {
			opFolder.mkdirs();
		}

		String opFile = opFolder + "/" + fileName;

		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File(opFile)));
			bw.write(data);

			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method to convert the date into epoc format
	 * 
	 * @param date
	 *            Date in normal date format(MM-dd-yyyy)
	 * @return Date in epoc format
	 */
	public static String convertToEpocDate(String date) {
		final String dateType = "MM-dd-yyyy";
		final String timeZone = "India/Chennai";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		long time = 0;
		String epochTime = "";
		try {
			Date parse = dateFormat.parse(date);
			time = parse.getTime();
			epochTime = epochTime + time;
		} catch (ParseException ex) {
			System.out.println("**Exception while parsing the date" + ex);
		} finally {

		}
		System.out.println("**EPOCH Time: " + epochTime);
		return epochTime;
	}

	/**
	 * Converting date picker value to specific date format
	 * 
	 * @throws ParseException
	 */
	public static String formatDate(String dpickerValue) {

		String pattern = "EEE MMM dd HH:mm:ss z yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date;
		String date1 = null;
		try {
			date = (Date) simpleDateFormat.parse(dpickerValue);
			String pattern1 = "MM-dd-yyyy";
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);

			date1 = simpleDateFormat1.format(date);
			System.out.println(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date1;
	}
	
	public static String convertDateToQuery(String input_date) throws ParseException{

        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        Date date = dt.parse(input_date);

        // *** same for the format String below
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        
        String formattedDate =dt1.format(date)+ "T00:00:00";
        		
        System.out.println(formattedDate);
        
        return formattedDate;
	}
}

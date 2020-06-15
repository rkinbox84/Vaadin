package com.sap.sf.UI;

public class DataHandler {

	private static final String TEMPORARY_VARIABLE = "temp";

	public static void putData(com.vaadin.ui.UI ui, Object object)
	{
	ui.getSession().setAttribute(TEMPORARY_VARIABLE, object);
	}

	public static Object readData(com.vaadin.ui.UI ui)
	{
	Object object = ui.getSession().getAttribute(TEMPORARY_VARIABLE);
	ui.getSession().setAttribute(TEMPORARY_VARIABLE, null);
	return object;
	}
}

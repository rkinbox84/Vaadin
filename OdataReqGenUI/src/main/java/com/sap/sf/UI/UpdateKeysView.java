/**
 * 
 */
package com.sap.sf.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sap.sf.beanClass.EntityFieldAttributes;
import com.sap.sf.beanClass.EntityOperation;
import com.sap.sf.utilClass.FMWConstants;
import com.sap.sf.utilClass.FrameworkUtils;
import com.sap.sf.utilClass.OdataReqGenHelper;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author I309192
 *
 */
public class UpdateKeysView extends VerticalLayout implements View {

	public static EntityOperation entityOpr = null;
	public static EntityOperation entityOpr_temp = null;
	List<EntityFieldAttributes> keys = null;

	// Fields declaration
	GridLayout gridLayout = new GridLayout(3, 3);
	final Panel pnl_keys = new Panel("Enter Key Values");
	FormLayout fLayout = new FormLayout();

	TextField txt_Entity = new TextField("Entity");
	TextField[] txt_String = null;
	DateField[] date_field = null;

	Button btn_getFields = new Button("Get Update Fields", this::getUpdateFields);
	Button btn_getQuery = new Button("Get Query", this::getQuery);

	Window subWindow = new Window("Request String");
	VerticalLayout subContent = new VerticalLayout();
	Label lbl_reqURL = new Label("<b>Request URL:</b>", ContentMode.HTML);
	Label reqURL = new Label();

	// Button PIC
	String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	Button btn_home = new Button("", this::homePage);
	Button btn_back = new Button("Back", this::oprPage);

	HorizontalLayout hlayout_btns = new HorizontalLayout();

	boolean isReqValuePresent = true;

	public void setComponentProperties() {
		// Grid
		gridLayout.setSizeFull();
		// panel
		pnl_keys.setSizeUndefined();
		// Layout
		fLayout.setMargin(true);
		// components
		btn_getFields.setEnabled(true);
		btn_getFields.setDisableOnClick(true);

		txt_Entity.setReadOnly(false);

		// Sub Window
		subContent.setMargin(true);
		subContent.setSizeUndefined();
		subWindow.setWidth("80%");
		subWindow.setContent(subContent);
		subWindow.center();

		hlayout_btns.setWidth("100%");
		hlayout_btns.setHeight("50%");
		hlayout_btns.setDefaultComponentAlignment(Alignment.BOTTOM_RIGHT);

		btn_home.setStyleName("v-button-home");
		btn_home.setWidth("60px");
		btn_home.setIcon(new FileResource(new File(basepath + "/VAADIN/themes/mytheme/icons/Home-40.png")));

		btn_back.setStyleName("v-button-home");
		btn_back.setWidth("70px");
		btn_back.setIcon(new FileResource(new File(basepath + "/VAADIN/themes/mytheme/icons/Back-20.png")));
	}

	public void clearOnLoad() {
		gridLayout.removeAllComponents();
		fLayout.removeAllComponents();
	}

	public void readParamData() {
		entityOpr = (EntityOperation) DataHandler.readData(getUI());

		if (entityOpr != null) {
			entityOpr_temp = entityOpr;
		} else {
			entityOpr = entityOpr_temp;
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

		clearOnLoad();
		setComponentProperties();
		readParamData();

		keys = FrameworkUtils.readFields(entityOpr, "keys");

		int keyCount = 0;

		txt_String = new TextField[keys.size()];
		date_field = new DateField[keys.size()];

		txt_Entity.setValue(entityOpr.getEntity());
		txt_Entity.setReadOnly(true);
		fLayout.addComponent(txt_Entity);

		for (EntityFieldAttributes ea : keys) {

			if (ea.getDataType().equals("Edm.String")) {
				txt_String[keyCount] = new TextField(ea.getFieldName());
				txt_String[keyCount].setRequired(true);
				fLayout.addComponent(txt_String[keyCount]);
			} else if (ea.getDataType().equals("Edm.DateTime")) {
				date_field[keyCount] = new DateField(ea.getFieldName());
				date_field[keyCount].setValue(new Date());
				date_field[keyCount].setRequired(true);
				fLayout.addComponent(date_field[keyCount]);
			}

			keyCount++;
		}

		if (entityOpr.getOperation().equals(FMWConstants.ODATA_DELETE)) {
			fLayout.addComponent(btn_getQuery);
			hlayout_btns.addComponent(btn_home);
			hlayout_btns.addComponent(btn_back);
		} else {
			fLayout.addComponent(btn_getFields);
		}

		pnl_keys.setContent(fLayout);

		gridLayout.addComponent(new Label(""));
		gridLayout.addComponent(pnl_keys);
		if (entityOpr.getOperation().equals(FMWConstants.ODATA_DELETE)) {
			gridLayout.addComponent(hlayout_btns);
		}
		this.addComponent(gridLayout);

	}

	private List<EntityFieldAttributes> getKeyValues() {

		try {
			int keyCount = 0;

			List<EntityFieldAttributes> keyAttribList = new ArrayList<EntityFieldAttributes>();

			for (EntityFieldAttributes ea : keys) {
				String fieldName = ea.getFieldName();
				String dataType = ea.getDataType();
				String value = "";

				if (ea.getDataType().contentEquals("Edm.DateTime")) {
					// String date_Val = date_field[keyCount].getValue();
					if (date_field[keyCount].getValue() != null) {
						value = FrameworkUtils.formatDate(date_field[keyCount].getValue().toString());
					} else {
						isReqValuePresent = false;
					}

				} else {
					value = txt_String[keyCount].getValue().toString();
					if (value.equals("")) {
						isReqValuePresent = false;
					}
				}

				EntityFieldAttributes entityAttrib = new EntityFieldAttributes(fieldName, dataType, value);
				keyAttribList.add(entityAttrib);
				keyCount++;
			}

			return keyAttribList;

		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin", Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}

		return null;

	}

	private void getUpdateFields(ClickEvent event) {

		try {
			List<EntityFieldAttributes> keyAttribList = getKeyValues();

			if (isReqValuePresent && keyAttribList != null) {
				OdataReqGenHelper helper = new OdataReqGenHelper();
				String url = helper.getUpdateURL(entityOpr.getEnvURL(), entityOpr.getEntity(), keyAttribList);

				entityOpr.setEnvURL(url);
				DataHandler.putData(getUI(), entityOpr);
				getUI().getNavigator().navigateTo(MainViewControl.FIELDS_VIEW);

			} else {
				btn_getFields.setEnabled(true);
				System.out.println("Required Fields value is Empty");
				Notification.show("Required field values are Mandatory", "Please enter Values in all required fields",
						Notification.Type.ERROR_MESSAGE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin", Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}

	}

	private void homePage(ClickEvent event) {
		getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
	}

	private void oprPage(ClickEvent event) {
		getUI().getNavigator().navigateTo(MainViewControl.OPERATION_VIEW);
	}

	private void getQuery(ClickEvent event) {
		try {
			List<EntityFieldAttributes> keyAttribList = getKeyValues();

			if (isReqValuePresent && keyAttribList != null) {
				OdataReqGenHelper helper = new OdataReqGenHelper();
				String url = helper.getUpdateURL(entityOpr.getEnvURL(), entityOpr.getEntity(), keyAttribList);
				subContent.addComponent(lbl_reqURL);
				subContent.addComponent(reqURL);
				reqURL.setValue(url);
				getUI().getCurrent().addWindow(subWindow);

			} else {
				btn_getFields.setEnabled(true);
				System.out.println("Required Fields value is Empty");
				Notification.show("Required field values are Mandatory", "Please enter Values in all required fields",
						Notification.Type.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin", Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}

}

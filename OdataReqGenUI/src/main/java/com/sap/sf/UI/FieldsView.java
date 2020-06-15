/**
 * 
 */
package com.sap.sf.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sap.sf.beanClass.EntityFieldAttributes;
import com.sap.sf.beanClass.EntityList;
import com.sap.sf.beanClass.EntityOperation;
import com.sap.sf.beanClass.FieldsTypeList;
import com.sap.sf.utilClass.FMWConstants;
import com.sap.sf.utilClass.FrameworkUtils;
import com.sap.sf.utilClass.OdataReqGenHelper;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
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
@PreserveOnRefresh
public class FieldsView extends VerticalLayout implements View {

	public static EntityOperation entityOpr = null;
	public static EntityOperation entityOpr_temp = null;
	FieldsTypeList eList = new FieldsTypeList();
	EntityList el = null;
	String entity;
	String operation;

	// Page Grid
	GridLayout grid_Parent = new GridLayout(2, 2);
	// HorizontalLayout hlayout = new HorizontalLayout();
	HorizontalLayout hlayout_btns = new HorizontalLayout();

	// Button btn_getReq = new Button("Get Request");
	Button btn_getReq = new Button("Get Request", this::getRequest);

	Button btn_home = new Button("", this::homePage);
	Button btn_back = new Button("Back", this::oprPage);

	// Required Fields declaration
	List<EntityFieldAttributes> required = null;
	final Panel pnl_Req = new Panel("Required Fields");
	Label[] lbl_fieldNamesReq = null;
	Label[] lbl_dataTypeReq = null;
	TextField[] txt_dataTypeReq = null;
	DateField[] txt_dateFieldValReq = null;
	GridLayout grid_reqFields = null;

	// Optional Fields declaration
	List<EntityFieldAttributes> optional = null;
	final Panel pnl_Opt = new Panel("Optional Fields");
	GridLayout optFieldsgrid = null;

	Label[] fieldNamesOpt = null;
	Label[] dataTypeOpt = null;

	TextField[] valueOpt = null;
	DateField[] dateFieldValOpt = null;

	// Sub Window Definition

	Window subWindow = new Window("Request String");
	VerticalLayout subContent = new VerticalLayout();
	Label lbl_reqURL = new Label("<b>Request URL:</b>",ContentMode.HTML);
	Label reqURL = new Label();
	Label lbl_reqBody = new Label("<b>Request Body:</b>",ContentMode.HTML);
	Label reqStringLbl = new Label();
	
	//Button PIC
	String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	
	//Upsert Fields
	ComboBox cbx_purgeType = new ComboBox("Purge Type");
	
	public void setComponentProperties() {

		grid_Parent.setSizeFull();
		grid_Parent.setSpacing(true);

		hlayout_btns.setWidth("100%");
		hlayout_btns.setHeight("50%");
		hlayout_btns.setDefaultComponentAlignment(Alignment.BOTTOM_RIGHT);

		btn_home.setStyleName("v-button-home");
		btn_home.setWidth("60px");
		btn_home.setIcon(new FileResource(new File(basepath + "/VAADIN/themes/mytheme/icons/Home-40.png")));
		
		btn_back.setStyleName("v-button-home");
		btn_back.setWidth("70px");
		btn_back.setIcon(new FileResource(new File(basepath + "/VAADIN/themes/mytheme/icons/Back-20.png")));
	 
		// Sub Window
		subContent.setMargin(true);
		subContent.setSizeUndefined();
		subWindow.setWidth("80%");
		subWindow.setContent(subContent);
		subWindow.center();

		reqStringLbl.setStyleName("v-label-resp");

		//Upsert Fields
		cbx_purgeType.setRequired(true);
	}

	public void clearOnLoad() {
		this.removeAllComponents();
		grid_Parent.removeAllComponents();
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

		try {
			clearOnLoad();
			setComponentProperties();

			if (required != null || optional != null) {
				required = null;
				optional = null;
			}

			required = new ArrayList<EntityFieldAttributes>();
			optional = new ArrayList<EntityFieldAttributes>();

			readParamData();

			required = FrameworkUtils.readFields(entityOpr, "required");

			grid_Parent.addComponent(new Label(""));
			hlayout_btns.addComponent(btn_getReq);
			/*
			 * hlayout_btns.addComponent(lnk_home);
			 * hlayout_btns.addComponent(lnk_back);
			 */
			hlayout_btns.addComponent(btn_home);
			hlayout_btns.addComponent(btn_back);
			grid_Parent.addComponent(hlayout_btns);

			// Required Fields

			setRequiredFields();

			// Optional Fields

			setOptionalFields();

			this.addComponent(grid_Parent);

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

	private void getRequest(ClickEvent event) {
		try {

			boolean showMsg = true;
			boolean isReqValuePresent = true;
			FieldsTypeList fTypeList = new FieldsTypeList();
			List<EntityFieldAttributes> reqAttribList = new ArrayList<EntityFieldAttributes>();

			int reqCountVal = 0;
			String reqValue = "";
			for (EntityFieldAttributes attrib : required) {

				String fieldName = lbl_fieldNamesReq[reqCountVal].getValue().toString();
				String dataType = lbl_dataTypeReq[reqCountVal].getValue().toString();
				if (attrib.getDataType().contentEquals("Edm.DateTime")) {
					Object date_field = txt_dateFieldValReq[reqCountVal].getValue();
					if (date_field != null) {
						reqValue = FrameworkUtils.formatDate(date_field.toString());
					} else {
						isReqValuePresent = false;
					}

				} else {
					reqValue = txt_dataTypeReq[reqCountVal].getValue().toString();
					if (reqValue.equals("")) {
						isReqValuePresent = false;
					}
				}

				EntityFieldAttributes entityAttrib = new EntityFieldAttributes(fieldName, dataType, reqValue);
				reqAttribList.add(entityAttrib);
				reqCountVal++;
			}

			fTypeList.setFieldTypeList("required", reqAttribList);
			
			if(entityOpr.getOperation().equals(FMWConstants.ODATA_UPSERT) && cbx_purgeType.getValue()==null){
				isReqValuePresent = false;
			}

			if (isReqValuePresent) {
				// -----------------------------------------------------------------------------------------------------
				// Optional List

				List<EntityFieldAttributes> optAttribList = new ArrayList<EntityFieldAttributes>();

				int optCountVal = 0;
				String optValue;
				for (EntityFieldAttributes attrib : optional) {

					String fieldName = fieldNamesOpt[optCountVal].getValue().toString();
					String dataType = dataTypeOpt[optCountVal].getValue().toString();
					if (attrib.getDataType().contentEquals("Edm.DateTime")) {
						optValue = FrameworkUtils.formatDate(dateFieldValOpt[optCountVal].getValue().toString());

					} else {
						optValue = valueOpt[optCountVal].getValue().toString();
					}

					EntityFieldAttributes entityAttrib = new EntityFieldAttributes(fieldName, dataType, optValue);
					optAttribList.add(entityAttrib);
					optCountVal++;
				}

				fTypeList.setFieldTypeList("optional", optAttribList);

				EntityList elVal = new EntityList();

				elVal.setEntityList(entity, fTypeList);

				OdataReqGenHelper reqGen = new OdataReqGenHelper();
				
				operation = entityOpr.getOperation();

				String jsonReq = reqGen.getReq(entity, operation, elVal);

				if(entityOpr.getOperation().equals(FMWConstants.ODATA_UPSERT)){
					String url_Upsert = entityOpr.getEnvURL()+cbx_purgeType.getValue().toString();
					reqStringLbl.setValue(jsonReq);
					String reqBody = "{ \"__metadata\": { \"uri\": \""+entityOpr.getEntity()+"\"},";
					reqBody = reqBody + jsonReq.substring(1);
					
					reqURL.setValue(url_Upsert);
					reqStringLbl.setValue(reqBody);
					subContent.addComponent(lbl_reqURL);
					subContent.addComponent(reqURL);
					subContent.addComponent(lbl_reqBody);
					subContent.addComponent(reqStringLbl);
				}else {
					reqStringLbl.setValue(jsonReq);
					reqURL.setValue(entityOpr.getEnvURL());
					subContent.addComponent(lbl_reqURL);
					subContent.addComponent(reqURL);
					subContent.addComponent(lbl_reqBody);
					subContent.addComponent(reqStringLbl);
				}
				
				getUI().getCurrent().addWindow(subWindow);

			} else {
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

	public void setOptionalFields() {

		// --------------------------------------------------------------------
		// Optional

		optional = FrameworkUtils.readFields(entityOpr, "optional");

		int optFieldsCount = optional.size();
		// Count number of required fields
		/*
		 * for (EntityFieldAttributes attrib : optional) { optFieldsCount++; }
		 */

		fieldNamesOpt = new Label[optFieldsCount];
		dataTypeOpt = new Label[optFieldsCount];

		valueOpt = new TextField[optFieldsCount];
		dateFieldValOpt = new DateField[optFieldsCount];

		if (optFieldsCount > 0) {

			optFieldsgrid = new GridLayout(3, optFieldsCount);
			optFieldsgrid.setSizeUndefined();
			optFieldsgrid.addStyleName("v-gridlayout-grid");
			optFieldsgrid.setMargin(true);
			optFieldsgrid.setSpacing(true);

			int optCount = 0;
			for (EntityFieldAttributes attrib : optional) {
				fieldNamesOpt[optCount] = new Label(attrib.getFieldName());
				dataTypeOpt[optCount] = new Label(attrib.getDataType());
				fieldNamesOpt[optCount].setStyleName("v-field");
				dataTypeOpt[optCount].setStyleName("v-dtype");
				optFieldsgrid.addComponent(fieldNamesOpt[optCount]);
				optFieldsgrid.addComponent(dataTypeOpt[optCount]);
				if (attrib.getDataType().contentEquals("Edm.DateTime")) {
					dateFieldValOpt[optCount] = new DateField();
					dateFieldValOpt[optCount].setValue(new Date());
					optFieldsgrid.addComponent(dateFieldValOpt[optCount]);
				} else {
					valueOpt[optCount] = new TextField();
					optFieldsgrid.addComponent(valueOpt[optCount]);
				}

				optCount++;
			}

			pnl_Opt.setContent(optFieldsgrid);
			grid_Parent.addComponent(pnl_Opt);

			// parentGrid.addComponent(optFieldsgrid);

		} else {
			pnl_Opt.setContent(new Label("No Fields to Display"));
			grid_Parent.addComponent(pnl_Opt);
		}

	}

	public void setRequiredFields() {

		// Center it in the browser window

		int reqFieldsCount = required.size();

		if (reqFieldsCount > 0) {

			lbl_fieldNamesReq = new Label[reqFieldsCount];
			lbl_dataTypeReq = new Label[reqFieldsCount];
			txt_dataTypeReq = new TextField[reqFieldsCount];
			txt_dateFieldValReq = new DateField[reqFieldsCount];

			// Required Fields Grid
			grid_reqFields = new GridLayout(3, reqFieldsCount);
			grid_reqFields.setSizeUndefined();
			grid_reqFields.addStyleName("v-gridlayout-grid");
			grid_reqFields.setMargin(true);
			grid_reqFields.setSpacing(true);

			int reqCount = 0;
			for (EntityFieldAttributes attrib : required) {

				lbl_fieldNamesReq[reqCount] = new Label(attrib.getFieldName());
				lbl_dataTypeReq[reqCount] = new Label(attrib.getDataType());
				lbl_fieldNamesReq[reqCount].setStyleName("v-field");
				lbl_dataTypeReq[reqCount].setStyleName("v-dtype");

				grid_reqFields.addComponent(lbl_fieldNamesReq[reqCount]);
				grid_reqFields.addComponent(lbl_dataTypeReq[reqCount]);

				if (attrib.getDataType().contentEquals("Edm.DateTime")) {
					txt_dateFieldValReq[reqCount] = new DateField();
					txt_dateFieldValReq[reqCount].setValue(new Date());
					grid_reqFields.addComponent(txt_dateFieldValReq[reqCount]);
				} else {
					txt_dataTypeReq[reqCount] = new TextField();
					grid_reqFields.addComponent(txt_dataTypeReq[reqCount]);
				}

				reqCount++;
			}

			if(entityOpr.getOperation().equals(FMWConstants.ODATA_UPSERT)){
				cbx_purgeType.addItem(FMWConstants.PURGE_FULL);
				cbx_purgeType.addItem(FMWConstants.PURGE_INC);
				grid_reqFields.addComponent(cbx_purgeType);
			}
			pnl_Req.setContent(grid_reqFields);
			grid_Parent.addComponent(pnl_Req);

		} else {
			pnl_Req.setContent(new Label("No Fields to display"));
			grid_Parent.addComponent(pnl_Req);
		}

	}

}

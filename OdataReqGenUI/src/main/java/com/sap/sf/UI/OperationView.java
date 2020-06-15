package com.sap.sf.UI;

import java.util.List;

import com.sap.sf.beanClass.EntityList;
import com.sap.sf.beanClass.EntityOperation;
import com.sap.sf.beanClass.EnvDetails;
import com.sap.sf.odata.meta.EntityMetadata;
import com.sap.sf.utilClass.FMWConstants;
import com.sap.sf.utilClass.FetchFields;
import com.sap.sf.utilClass.FrameworkUtils;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@PreserveOnRefresh
public class OperationView extends VerticalLayout implements View {

	GridLayout grid = new GridLayout(3, 3);

	// Create Panel
	final Panel pnl_opr = new Panel("Please Select Entity and Operation");

	ComboBox cbx_entity = new ComboBox("Select Entity");

	ComboBox cbx_opr = new ComboBox("Select operation");

	Button btn_getFields = new Button("Get Fields", this::getFields);

	FetchFields fFields = new FetchFields();
	List<EntityMetadata> entityMetaAll = null;

	// Create a layout inside the panel
	final FormLayout fLayout = new FormLayout();

	public static EnvDetails env = null;
	public static EnvDetails env_temp = null;
	// CreateEntityMetaFiles metaList = null;
	EntityList el = null;

	public void setComponentProperties() {

		grid.setWidth("100%");
		grid.setHeight("100%");

		// panel
		pnl_opr.setSizeUndefined();
		// Layout
		fLayout.setMargin(true);
		// components
		cbx_entity.setRequired(true);
		cbx_opr.setRequired(true);

		btn_getFields.setDisableOnClick(true);
		btn_getFields.setEnabled(true);
	}

	public void readParamData() {
		env = (EnvDetails) DataHandler.readData(getUI());

		if (env != null) {
			env_temp = env;
		} else {
			env = env_temp;
			env.setRefreshMeta(false);
		}
	}

	public void clearOnLoad() {
		this.removeAllComponents();
		grid.removeAllComponents();
		cbx_entity.removeAllItems();
	}

	@Override
	public void enter(ViewChangeEvent event) {

		try {

			// TODO Auto-generated method stub

			clearOnLoad();

			setComponentProperties();

			grid.addComponent(new Label(""));

			readParamData();

			// Get Metadata for the instance
			entityMetaAll = FrameworkUtils.getMetadata(env);

			// Load all entitys available in the instance
			for (EntityMetadata metaObj : entityMetaAll) {

				cbx_entity.addItem(metaObj.getEntityType().getEntityTypeName());

			}
			// cbx_opr.addItems("Create","Update","Upsert","Delete");
			cbx_opr.addItem(FMWConstants.ODATA_CREATE);
			cbx_opr.addItem(FMWConstants.ODATA_UPDATE);
			cbx_opr.addItem(FMWConstants.ODATA_UPSERT);
			cbx_opr.addItem(FMWConstants.ODATA_DELETE);

			fLayout.addComponent(cbx_entity);
			fLayout.addComponent(cbx_opr);
			fLayout.addComponent(btn_getFields);
			pnl_opr.setContent(fLayout);

			grid.addComponent(pnl_opr);

			this.addComponent(grid);

		} catch (Exception e) {
			env = null;
			e.printStackTrace();
			Notification.show("Unable to fetch Metadata", "Try after some time or contact admin",
					Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}

	private void getFields(ClickEvent event) {
		try {
			String url = FrameworkUtils.getEnvURL(env.getEnvironment());

			if (cbx_entity.getValue() == null || cbx_opr.getValue() == null) {
				System.out.println("Value is Empty");
				Notification.show("All fields are Mandatory", "Please enter Values", Notification.Type.ERROR_MESSAGE);
				event.getButton().setEnabled(true);

			} else if (cbx_opr.getValue().toString().equals(FMWConstants.ODATA_CREATE)) {

				el = fFields.getQualifiedFields(entityMetaAll, FMWConstants.ODATA_CREATE);
				EntityOperation entityOpr = new EntityOperation(cbx_entity.getValue().toString(),
						cbx_opr.getValue().toString(), el, url + cbx_entity.getValue().toString() + "?");
				DataHandler.putData(getUI(), entityOpr);
				getUI().getNavigator().navigateTo(MainViewControl.FIELDS_VIEW);
			} else if (cbx_opr.getValue().toString().equals(FMWConstants.ODATA_UPDATE)) {

				el = fFields.getQualifiedFields(entityMetaAll, FMWConstants.ODATA_UPDATE);
				EntityOperation entityOpr = new EntityOperation(cbx_entity.getValue().toString(),
						cbx_opr.getValue().toString(), el, url);
				DataHandler.putData(getUI(), entityOpr);
				getUI().getNavigator().navigateTo(MainViewControl.UPDATE_KEYS_VIEW);
			} else if (cbx_opr.getValue().toString().equals(FMWConstants.ODATA_UPSERT)) {

				el = fFields.getQualifiedFields(entityMetaAll, FMWConstants.ODATA_UPSERT);
				EntityOperation entityOpr = new EntityOperation(cbx_entity.getValue().toString(),
						cbx_opr.getValue().toString(), el, url + "upsert?purgeType=");
				DataHandler.putData(getUI(), entityOpr);
				getUI().getNavigator().navigateTo(MainViewControl.FIELDS_VIEW);
			} else if (cbx_opr.getValue().toString().equals(FMWConstants.ODATA_DELETE)) {

				el = fFields.getQualifiedFields(entityMetaAll, FMWConstants.ODATA_DELETE);
				EntityOperation entityOpr = new EntityOperation(cbx_entity.getValue().toString(),
						cbx_opr.getValue().toString(), el, url);
				DataHandler.putData(getUI(), entityOpr);
				getUI().getNavigator().navigateTo(MainViewControl.UPDATE_KEYS_VIEW);
			} else {
				Notification.show("Service not available", "Please contact admin", Notification.Type.WARNING_MESSAGE);
				event.getButton().setEnabled(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin", Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}

}

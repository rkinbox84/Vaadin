/**
 * 
 */
package com.sap.sf.UI;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;
import com.sap.sf.beanClass.EnvDetails;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author I309192
 *
 */
public class NewInstanceView extends VerticalLayout implements View {

	public static List<String> instanceList = new ArrayList<String>();
	
	// Component Initialization
	GridLayout gridLayout = new GridLayout(3, 1);
	Panel pnl_instance = new Panel("Please Enter Instance Details");
	FormLayout fLayout = new FormLayout();

	ComboBox cbx_env = new ComboBox("Select Environment");
	TextField txt_instance = new TextField("Instance Name");
	TextField txt_userName = new TextField("User Name");
	PasswordField txt_password = new PasswordField("Password");
	CheckBox chkbox_refreshMeta = new CheckBox("Refresh Metadata");
	Button btn_LoadEntities = new Button("Load Entities", this::LoadEntities);

	//Instance combo
	ComboBox cbx_instance = new ComboBox("Select Instance");
	Button btn_newInstance = new Button("Add Instance", this::newInstance);
	
	public void setComponentProperties() {
		// Grid
		gridLayout.setSizeFull();
		// panel
		pnl_instance.setSizeUndefined();
		// Layout
		fLayout.setMargin(true);
		// components
		cbx_env.setRequired(true);
		txt_instance.setRequired(true);
		txt_userName.setRequired(true);
		txt_password.setRequired(true);

		btn_LoadEntities.setDisableOnClick(true);
		
		cbx_instance.setRequired(true);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		try{

			

			this.removeAllComponents();
			gridLayout.removeAllComponents();
			btn_LoadEntities.setEnabled(true);
			
			setComponentProperties();
			
			//Common Settings
			gridLayout.addComponent(new Label());
			
			
			
			if(instanceList.size() > 0){
				for(String val: instanceList){
					cbx_instance.addItem(val);
				}
				fLayout.addComponent(cbx_instance);
				fLayout.addComponent(chkbox_refreshMeta);
				
			}else {
				cbx_env.addItems("QACAND", "PREVIEW","AUTOCAND");
				fLayout.addComponents(cbx_env, txt_instance, txt_userName, txt_password, chkbox_refreshMeta);

			}
			fLayout.addComponents(btn_LoadEntities);
			pnl_instance.setContent(fLayout);
			gridLayout.addComponent(pnl_instance);

			this.addComponent(gridLayout);
			
			chkbox_refreshMeta.addValueChangeListener(new Property.ValueChangeListener() {
		        @Override
		        public void valueChange(Property.ValueChangeEvent event) {
		        	
					if (chkbox_refreshMeta.getValue()) {
						
						Notification.show("Enabled Refresh Metadata", "This process will take some time to refresh and load metadata from server",
								Notification.Type.WARNING_MESSAGE);
						}
						
		        	
		        } });

		
		}catch (Exception e){
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin",
					Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}

	private void LoadEntities(ClickEvent event) {
		try{


			// Fields validation

			if (cbx_env.getValue() == null || txt_instance.getValue().equals("") || txt_userName.getValue().equals("")
					|| txt_password.getValue().equals("")) {
				System.out.println("Value is Empty");
				Notification.show("All fields are Mandatory", "Please enter Values", Notification.Type.ERROR_MESSAGE);
				event.getButton().setEnabled(true);

			} else {			
				String envName = cbx_env.getValue().toString();
				String instName = txt_instance.getValue();

				instanceList.add(envName+":"+instName);
				EnvDetails env = new EnvDetails(envName, instName, txt_userName.getValue(),
						txt_password.getValue(),chkbox_refreshMeta.getValue());
				DataHandler.putData(getUI(), env);
				getUI().getCurrent().getNavigator().navigateTo(MainViewControl.OPERATION_VIEW);
			}

			// set the UI values to obj and forward to next view
			
		
		}catch (Exception e){
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin",
					Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}
	
	private void newInstance(ClickEvent event) {
		try{
			
		}catch (Exception e){
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin",
					Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}

}

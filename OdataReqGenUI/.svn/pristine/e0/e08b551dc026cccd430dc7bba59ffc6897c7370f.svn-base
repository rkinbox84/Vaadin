/**
 * 
 */
package com.sap.sf.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;
import com.sap.sf.beanClass.EnvDetails;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
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
@PreserveOnRefresh
public class InstanceView extends VerticalLayout implements View {

	//View Change Controller
	
	static boolean changeView = false;
	public static List<String> instanceList = new ArrayList<String>();
	public static Map<String,String> instanceMap = new HashMap<String,String>();

	// Component Initialization
	GridLayout gridLayout = new GridLayout(3, 1);
	Panel pnl_instance = new Panel("Enter Instance Details");
	FormLayout fLayout = new FormLayout();

	ComboBox cbx_env = new ComboBox("Select Environment");
	TextField txt_instance = new TextField("Instance Name");
	TextField txt_userName = new TextField("User Name");
	PasswordField txt_password = new PasswordField("Password");
	CheckBox chkbox_refreshMeta = new CheckBox("Refresh Metadata");
	Button btn_LoadEntities = new Button("Load Entities", this::LoadEntities);

	// Instance combo
	ComboBox cbx_instance = new ComboBox("Select Instance");
	Button btn_LoadExtEntities = new Button("Load Entities", this::LoadExtEntities);

	//Add instance button Icon
	Button btn_newInstance = new Button("", this::newInstance);
	HorizontalLayout hlayout_btns = new HorizontalLayout();
	String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	
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
		
		hlayout_btns.setWidth("100%");
		hlayout_btns.setHeight("100%");
		hlayout_btns.setSpacing(true);
		hlayout_btns.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
		
		btn_newInstance.setStyleName("v-button-inst");
		btn_newInstance.setWidth("30px");
		btn_newInstance.setDescription("Add New Instance");
		btn_newInstance.setIcon(new FileResource(new File(basepath + "/VAADIN/themes/mytheme/icons/add.png")));
	}

	public void clearOnLoad() {
		this.removeAllComponents();
		gridLayout.removeAllComponents();
		fLayout.removeAllComponents();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		try {

			clearOnLoad();
			btn_LoadEntities.setEnabled(true);

			setComponentProperties();

			changeView = false;
			
			// Common Settings
			gridLayout.addComponent(new Label());

			if (instanceMap.size() > 0) {
				pnl_instance.setCaption("Select Instance");
				Set<String> keysInst = instanceMap.keySet();
				
				for (String val : keysInst) {
					cbx_instance.addItem(val);
				}
				fLayout.addComponent(cbx_instance);
				fLayout.addComponent(chkbox_refreshMeta);
				hlayout_btns.addComponent(btn_LoadExtEntities);
				hlayout_btns.addComponent(btn_newInstance);
				fLayout.addComponent(hlayout_btns);
/*				fLayout.addComponents(btn_LoadExtEntities);
				fLayout.addComponent(btn_newInstance);*/
			} else {
				//pnl_instance.setCaption("Enter Instance Details");
				cbx_env.addItems("QACAND", "PREVIEW", "AUTOCAND");
				fLayout.addComponents(cbx_env, txt_instance, txt_userName, txt_password, chkbox_refreshMeta);
				fLayout.addComponents(btn_LoadEntities);
			}

			pnl_instance.setContent(fLayout);
			gridLayout.addComponent(pnl_instance);

			this.addComponent(gridLayout);

			chkbox_refreshMeta.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(Property.ValueChangeEvent event) {

					if (chkbox_refreshMeta.getValue()) {

						Notification.show("Enabled Refresh Metadata",
								"This process will take some time to refresh and load metadata from server",
								Notification.Type.WARNING_MESSAGE);
					}

				}
			});
			
			getUI().getNavigator().addViewChangeListener(new ViewChangeListener() {
	            public boolean beforeViewChange(ViewChangeEvent event) {
	            	return changeView;
	            }

				@Override
				public void afterViewChange(ViewChangeEvent event) {
					// TODO Auto-generated method stub
					
				}});

		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin", Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}

	private void LoadExtEntities(ClickEvent event) {
		try {
			changeView = true;
			EnvDetails env = null;
			if (cbx_instance.getValue() != null) {
				String instKey = cbx_instance.getValue().toString();
				String instVal = instanceMap.get(instKey.trim());
				
				String[] env_inst = instKey.split(":");
				String[] usr_pwd = instVal.split(":");
				
				env = new EnvDetails(env_inst[1], env_inst[0], usr_pwd[0], usr_pwd[1], chkbox_refreshMeta.getValue());
				DataHandler.putData(getUI(), env);
				getUI().getCurrent().getNavigator().navigateTo(MainViewControl.OPERATION_VIEW);
			} else {
				Notification.show("All fields are Mandatory", "Please enter Values", Notification.Type.ERROR_MESSAGE);
				event.getButton().setEnabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin", Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}

	}

	private void LoadEntities(ClickEvent event) {
		try {
			// Fields validation
			changeView = true;
			if (cbx_env.getValue() == null || txt_instance.getValue().equals("") || txt_userName.getValue().equals("")
					|| txt_password.getValue().equals("")) {
				System.out.println("Value is Empty");
				Notification.show("All fields are Mandatory", "Please enter Values", Notification.Type.ERROR_MESSAGE);
				event.getButton().setEnabled(true);

			} else {
				EnvDetails env = null;
				String envName = cbx_env.getValue().toString();
				String instName = txt_instance.getValue();
				String usrName = txt_userName.getValue();
				String pwd =  txt_password.getValue();
				
				String instKey = instName + ":" + envName;
				String instVal = usrName + ":" + pwd;
				instanceMap.put(instKey, instVal);
				
				env = new EnvDetails(envName, instName, usrName, pwd, chkbox_refreshMeta.getValue());

				DataHandler.putData(getUI(), env);
				getUI().getCurrent().getNavigator().navigateTo(MainViewControl.OPERATION_VIEW);
			}

			// set the UI values to obj and forward to next view

		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin", Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}

	private void newInstance(ClickEvent event) {
		try {
			clearOnLoad();
			changeView = false;
			btn_LoadEntities.setEnabled(true);
			setComponentProperties();

			//pnl_instance.setCaption("Enter Instance Details");
			// Common Settings
			gridLayout.addComponent(new Label());
			cbx_env.addItems("QACAND", "PREVIEW", "AUTOCAND");
			fLayout.addComponents(cbx_env, txt_instance, txt_userName, txt_password, chkbox_refreshMeta);
			fLayout.addComponents(btn_LoadEntities);
			pnl_instance.setContent(fLayout);
			gridLayout.addComponent(pnl_instance);

			this.addComponent(gridLayout);

			chkbox_refreshMeta.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(Property.ValueChangeEvent event) {

					if (chkbox_refreshMeta.getValue()) {

						Notification.show("Enabled Refresh Metadata",
								"This process will take some time to refresh and load metadata from server",
								Notification.Type.WARNING_MESSAGE);
					}

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Error Occurred", "Please Contact admin", Notification.Type.ERROR_MESSAGE);
			getUI().getNavigator().navigateTo(MainViewControl.INSTANCE_VIEW);
		}
	}

}

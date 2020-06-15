package com.sap.sf.UI;

import java.util.Arrays;
import java.util.Date;

import com.vaadin.annotations.PreserveOnRefresh;
//import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
@PreserveOnRefresh
public class TestView extends VerticalLayout implements View{

	// Create a text field
		TextField tf1 = new TextField("Instance");
		TextField tf2 = new TextField("UserName");
		TextField tf3 = new TextField("Instance");
		TextField tf4 = new TextField("UserName");
		TextField tf5 = new TextField("Instance");
		TextField tf6 = new TextField("UserName");
		TextField tf7 = new TextField("Instance");
		TextField tf8 = new TextField("UserName");	

		TextField tf11 = new TextField("Instance");
		TextField tf21 = new TextField("UserName");
		TextField tf31 = new TextField("Instance");
		TextField tf41 = new TextField("UserName");
		TextField tf51 = new TextField("Instance");
		TextField tf61 = new TextField("UserName");
		TextField tf71 = new TextField("Instance");
		TextField tf81 = new TextField("UserName");	
		
	@Override
	public void enter(ViewChangeEvent event) {
		
		String home = Page.getCurrent().getUriFragment();
		
		if(home.equals("home")){
			getUI().getNavigator().navigateTo(MainViewControl.Test_VIEW1);
		}
		
		GridLayout gl = new GridLayout(2,2);
		gl.setWidth("100%");
		gl.setHeightUndefined();
		
		Button btn_back = new Button("Next", this::oprPage);
		
		Panel panel = new Panel("Panel1");
		//panel.setStyleName("v-panel-nocaption");
		panel.setWidth("100%");
		panel.setHeightUndefined();
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeUndefined();
		hl.addComponent(tf1);
		hl.addComponent(tf2);
		hl.addComponent(tf3);
		hl.addComponent(tf4);
		hl.addComponent(tf5);
		hl.addComponent(tf6);
		hl.addComponent(tf7);
		hl.addComponent(tf8);
		
		panel.setContent(hl); 
		
		gl.addComponent(panel);
		
		Panel panel1 = new Panel("Panel2");
		//panel.setStyleName("v-panel-nocaption");
		panel1.setWidth("100%");
		panel1.setHeightUndefined();
		
		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.setSizeUndefined();
		hl1.addComponent(tf11);
		hl1.addComponent(tf21);
		hl1.addComponent(tf31);
		hl1.addComponent(tf41);
		hl1.addComponent(tf51);
		hl1.addComponent(tf61);
		hl1.addComponent(tf71);
		hl1.addComponent(tf81);
		
		panel1.setContent(hl1); 
		gl.addComponent(panel1);
		
		gl.addComponent(btn_back);
		this.addComponent(gl);
		
	}
	
	private void oprPage(ClickEvent event) {
		getUI().getNavigator().navigateTo(MainViewControl.Test_VIEW_Fields);
	}
}
package com.sap.sf.UI;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@PreserveOnRefresh
public class TestViewFields extends VerticalLayout implements View{

	// Page Grid
		GridLayout grid_Parent = new GridLayout(2, 2);
		HorizontalLayout hlayout = new HorizontalLayout();
		HorizontalLayout hlayout_btns = new HorizontalLayout();
		
		Button btn_getReq = new Button("Back",this::back);
		
		final Panel pnl_Req = new Panel("Required Fields");
		final Panel pnl_Opt = new Panel("Optional Fields");
		
		GridLayout grid_Req = new GridLayout(10, 20);
		GridLayout grid_Opt = new GridLayout(10, 20);
		
		TextField req[] = new TextField[60];
		TextField Opt[] = new TextField[60];
		
		public void setComponentProperties() {
			grid_Parent.setSizeFull();
			grid_Parent.setSpacing(true);
			//grid_Parent.setMargin(true);
			
			//hlayout.setSizeUndefined();
			hlayout.setWidth("100%");
			hlayout.setHeight("50%");
			//hlayout.setSpacing(true);
			//hlayout.setMargin(true);
			hlayout.setDefaultComponentAlignment(Alignment.BOTTOM_RIGHT);
			
			grid_Req.setMargin(true);
			grid_Req.setSpacing(true);
			
			grid_Opt.setMargin(true);
			grid_Opt.setSpacing(true);
			
		}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
		setComponentProperties();
		
		if(Page.getCurrent().getUriFragment().equals("home")){
			Page.getCurrent().setUriFragment("home");
		}
		
		
		grid_Parent.addComponent(new Label(""));
		
		hlayout.addComponent(btn_getReq);

		grid_Parent.addComponent(hlayout);
		

		
		for(int i=0; i<60; i++){
			req[i] = new TextField(Integer.toString(i));
			grid_Req.addComponent(req[i]);
		}
		
		pnl_Req.setContent(grid_Req);
		grid_Parent.addComponent(pnl_Req);
		
		for(int i=0; i<60; i++){
			Opt[i] = new TextField(Integer.toString(i));
			grid_Opt.addComponent(Opt[i]);
		}
		
		pnl_Opt.setContent(grid_Opt);
		
		grid_Parent.addComponent(pnl_Opt);
		this.addComponent(grid_Parent);
		
	}
	
	private void back(ClickEvent event) {
		Page.getCurrent().setUriFragment("back");
		getUI().getNavigator().navigateTo(MainViewControl.Test_VIEW);
	}

}

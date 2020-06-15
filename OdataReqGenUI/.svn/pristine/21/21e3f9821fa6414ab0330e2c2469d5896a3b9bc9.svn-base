/**
 * 
 */
package com.sap.sf.UI;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author I309192
 *
 */
@Theme("mytheme")
@PreserveOnRefresh
public class MainViewControl extends UI {

	public Navigator navigator;
	public static final String Test_VIEW = "TestView";
	public static final String Test_VIEW1 = "TestView1";
	public static final String Test_VIEW_Fields = "TestViewFields";
	public static final String INSTANCE_VIEW = "InstanceView";
	public static final String OPERATION_VIEW = "OperationView";
	public static final String FIELDS_VIEW = "FieldsView";
	public static final String UPDATE_KEYS_VIEW = "UpdateKeysView";
	public static final String Query_KEYS_VIEW = "QueryKeysView";
	public static final String NEW_INSTANCE_VIEW = "NewInstanceView";


	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub

		setTheme("mytheme");

		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		
/*		navigator.addView("", new TestView1());
		navigator.addView(Test_VIEW, new TestView());
		navigator.addView(Test_VIEW_Fields, new TestViewFields());*/
		navigator.addView("", new InstanceView());
		navigator.addView(NEW_INSTANCE_VIEW, new NewInstanceView());
		navigator.addView(OPERATION_VIEW, new OperationView());
		navigator.addView(FIELDS_VIEW, new FieldsView());
		navigator.addView(INSTANCE_VIEW, new InstanceView());
		navigator.addView(UPDATE_KEYS_VIEW, new UpdateKeysView());

		

	}
	
	
    @WebServlet(urlPatterns = "/*", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainViewControl.class, productionMode = false)
    public static class MainViewControlServlet extends VaadinServlet {
    }

}

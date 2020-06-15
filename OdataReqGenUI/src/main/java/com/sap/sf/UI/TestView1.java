/**
 * 
 */
package com.sap.sf.UI;

import java.io.File;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author I309192
 *
 */
@PreserveOnRefresh
public class TestView1 extends VerticalLayout implements View{

	static boolean changeView=false;
	Window subWindow = null;
	VerticalLayout subContent = new VerticalLayout();
	Label lbl_data = new Label();
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		changeView=false;
		
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		 
		Button btn_home = new Button("Next", this::ok);
		btn_home.setStyleName("v-button-home");
		btn_home.setWidth("60px");
		btn_home.setIcon(new FileResource(new File(basepath + "/VAADIN/themes/mytheme/icons/Home-40.png")));
		 
			Button btn_back = new Button("Back", this::ok);
			btn_back.setStyleName("v-button-home");
			btn_back.setWidth("70px");
    	 btn_back.setIcon(new FileResource(new File(basepath + "/VAADIN/themes/mytheme/icons/Back-20.png")));

		 
    	subWindow = new Window("Request String");
    	
    	subContent.setMargin(true);
    	subContent.setSizeUndefined();
		subWindow.setContent(subContent);
		subWindow.setWidth("80%");
		subWindow.center();
		
		
		lbl_data.setValue("sadhfiusafhsaehfpiwherihweiurhwpeq hrpwiehrpihwepirhqwpiehpiwhefphwehf pwahe"
				+ ";aejf;ljaefj;lksajf;lkjdsaljfiajespoifjepoifoihefoihjrefiruhgiuhdsdf"
				+ ";lrjf;lkrdflkjdslkjjfdgkjhfdgqiuregfinregvjhreiugfheiurgfrehgfhreiuvfhreiuvgfiureh"
				+ "lkerjglkejrlkjrelkgjlkremg;lkjreoigfremgfireogfijregjreoijgeirwjgklfdgoihjregoinfegvoiue"
				+ "rkdgnkreng;krengnreglknerknglkrenglkreng;krengknregknrew;ngkfdng;irenignrejgnrei");
		
		
		getUI().getNavigator().addViewChangeListener(new ViewChangeListener() {
	            public boolean beforeViewChange(ViewChangeEvent event) {
	            	return changeView;
	            }

				@Override
				public void afterViewChange(ViewChangeEvent event) {
					// TODO Auto-generated method stub
					
				}});


		
		
		this.addComponent(btn_home);
		this.addComponent(btn_back);



		
	}
	
    @SuppressWarnings("static-access")
	private void ok(ClickEvent event) {
    	changeView=true;
    	getUI().getNavigator().navigateTo(MainViewControl.Test_VIEW);
    }

    private void cancel(ClickEvent event) {
       //event.getButton().setCaption ("Not OK!");
    }

    
}

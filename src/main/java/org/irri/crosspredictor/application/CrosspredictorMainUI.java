package org.irri.crosspredictor.application;

import org.irri.crosspredictor.component.CrossFormComponent;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("crosspredictorweb")
public class CrosspredictorMainUI extends UI {
    	
    private TabSheet tabSheetCrossResult;
	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
//		Accordion mainLayoutAccordion= new Accordion();
		TabSheet tabSheetMain = new TabSheet();
		VerticalLayout tableCrossResultLayout= new VerticalLayout();
		tabSheetCrossResult = new TabSheet();
		tabSheetCrossResult.setSizeFull();
		tableCrossResultLayout.addComponent(tabSheetCrossResult);
		tableCrossResultLayout.setMargin(true);
		
		tabSheetMain.addTab(new CrossFormComponent(tabSheetMain,tabSheetCrossResult),"Cross Form");
		tabSheetMain.addTab(tableCrossResultLayout,"Cross Results");
		
		layout.addComponent(tabSheetMain);
		
	}

}
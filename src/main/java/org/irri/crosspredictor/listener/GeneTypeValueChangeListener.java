package org.irri.crosspredictor.listener;

import org.irri.crosspredictor.component.CrossFormComponent;
import org.irri.crosspreditor.helper.CrossFormHelper;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;


public class GeneTypeValueChangeListener implements ValueChangeListener{
	/*
	 * 
	 * 
	 * NOT BEING USED AS OF THIS TIME
	 * 
	 * 
	 */

    private CrossFormHelper helper;
    private CrossFormComponent crossForm;


    public GeneTypeValueChangeListener(CrossFormComponent crossForm, CrossFormHelper helper) {
	// TODO Auto-generated constructor stub
	
	this.crossForm=crossForm;
	
    }

    @Override
    public void valueChange(ValueChangeEvent event) {

	final String valueString = String.valueOf(event.getProperty()
		.getValue());
	Notification.show("Gene Type Value changed:", valueString,Type.TRAY_NOTIFICATION);
	String filePath = helper.BASE_PATH +"/WEB-INF/files/input/"+valueString+"list.csv";
    }

}

package org.irri.crosspredictor.listener;

import org.irri.crosspredictor.component.CrossFormComponent;
import org.irri.crosspreditor.helper.CrossFormHelper;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;


public class PListValueChangeListener implements ValueChangeListener{


    private ListSelect listParent;
	private ListSelect listP1;
	private ListSelect listP2;
    private Button buttonRemove;
    private Button buttonReset;


    public PListValueChangeListener(ListSelect listParent, ListSelect listP1, ListSelect listP2, Button buttonRemove, Button buttonReset) {
	// TODO Auto-generated constructor stub
	this.listParent = listParent;
	this.listP1 = listP1;
	this.listP2 = listP2;
	this.buttonRemove = buttonRemove;
	this.buttonReset = buttonReset;
    }

    @Override
	public void valueChange(final ValueChangeEvent event) {
    	try{
    		if(!listParent.getItemIds().isEmpty()){
    			buttonRemove.setEnabled(true);
    			buttonReset.setEnabled(true);
    		}
    		listP1.setValue(null);
    		listP2.setValue(null);
		
    	}catch(NullPointerException npe){
    		System.out.println("Caught Null Exception at PListValueChangeListener");
    	}
	}

}

package org.irri.crosspredictor.listener;

import org.irri.crosspredictor.component.CrossFormComponent;
import org.irri.crosspreditor.helper.CrossFormHelper;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Upload;


public class OptionGroupValueChangeListener implements ValueChangeListener{
	/*
	 * 
	 * 
	 * NOT BEING USED AS OF THIS TIME
	 * 
	 * 
	 */

    private OptionGroup optionGroup;
    private Upload upload;
    private ComboBox comboBoxGeneType;


    public OptionGroupValueChangeListener(OptionGroup optionGroup, Upload upload, ComboBox comboBoxGeneType) {
	// TODO Auto-generated constructor stub
    	this.optionGroup = optionGroup;
    	this.upload = upload;
    	this.comboBoxGeneType = comboBoxGeneType;
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
    	String selected = optionGroup.getValue().toString();
		if(selected.equals("1")){
			if(comboBoxGeneType.getValue()==null){
				new Notification("Please specify Gene Type.",
						"",
						Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());

				optionGroup.select(0);
			}
			else upload.setEnabled(true);

		}else{
			upload.setEnabled(false);
			optionGroup.setItemCaption(1, "Specified");
		}
    }

}

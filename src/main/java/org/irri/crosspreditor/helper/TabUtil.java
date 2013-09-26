package org.irri.crosspreditor.helper;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;


public class TabUtil{

    public static Tab getTabToFocus(TabSheet tabSheet, String tabCaption) {
	Tab tabToFocus=tabSheet.getTab(0);
	boolean rightTab=false;
	for (int i = 0; i < tabSheet.getComponentCount(); i++) {
	    Tab tab = tabSheet.getTab(i);
	    if(rightTab){
		tabToFocus=tab;
		return tabToFocus;
	    }
	    if (tab.getCaption().equals(tabCaption)) {
		if(i==(tabSheet.getComponentCount()-1)){
		    return tabToFocus;
		}else{
		    rightTab=true;
		}
	    }

	    tabToFocus=tab;
	}
	return null;
    }
    

}

package org.irri.crosspredictor.handler;


import org.irri.crosspreditor.helper.TabUtil;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;


public class SelectedTabCloseHandler implements CloseHandler{

    @Override
    public void onTabClose(TabSheet tabsheet, Component tabContent) {
	if(tabsheet.getComponentCount() > 1){
            String tabCaption=tabsheet.getTab(tabContent).getCaption();
            Tab tab = TabUtil.getTabToFocus(tabsheet, tabCaption);
            tabsheet.removeTab(tabsheet.getTab(tabContent));
            tabsheet.setSelectedTab(tab.getComponent());
        }else{
            tabsheet.removeTab(tabsheet.getTab(tabContent));
        }
        tabsheet.requestRepaintAll();

    }

}

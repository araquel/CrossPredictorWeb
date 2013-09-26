package org.irri.crosspredictor.component;

import org.irri.crosspredictor.container.GluIndexContainer;
import org.irri.crosspredictor.model.CrossFormInputModel;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


public class GluCrossResultComponent extends VerticalLayout{


    private Table tableResult;
    private Table tableParentInfo;

    public GluCrossResultComponent(CrossFormInputModel crossFormInputModel){
	setMargin(true);
	
	tableParentInfo = new Table("Parent Information");
	GluIndexContainer dataIndexContainer= new GluIndexContainer();
	IndexedContainer dataSourceGlu = dataIndexContainer.getParentInfoGlu(crossFormInputModel);
	tableParentInfo.setContainerDataSource(dataSourceGlu);
	addComponent(tableParentInfo);

//
//	tableResult = new Table("Result of all Crosses");
//	tableResult.addContainerProperty("CID", String.class,null);
//	tableResult.addContainerProperty("CName", String.class, null);
//	tableResult.addContainerProperty("Genotype", String.class,null);
//	tableResult.addContainerProperty("Percent", double.class,null);
//	tableResult.addContainerProperty("PS95", double.class, null);
//	tableResult.addContainerProperty("PS99", double.class, null);
//	tableResult.addContainerProperty("RMax", double.class, null);
//	tableResult.addContainerProperty("Ext", double.class, null);
//	tableResult.addContainerProperty("WA", double.class, null);
//	
//	addComponent(tableResult);
	
    }

    private void populateTableParent() {
	// TODO Auto-generated method stub
	
    }
    
    

}

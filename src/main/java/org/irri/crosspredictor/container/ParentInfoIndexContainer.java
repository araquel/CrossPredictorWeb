package org.irri.crosspredictor.container;

import java.util.ArrayList;

import org.irri.crosspredictor.model.CrossFormInputModel;
import org.irri.crosspredictor.model.CrossResultTableModel;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;


public class ParentInfoIndexContainer{

    // GermplasmNamesAttribute Model
    private static final String PARENT_INFO_ID = "ID";
    private static final String PARENT_INFO_NAME = "Name";
    private static final String PARENT_INFO_GENOTYPE = "Genotype";
    private static final String PARENT_INFO_RMAX = "RMax";
    private static final String PARENT_INFO_EXT = "Ext";
    private static final String PARENT_INFO_WA = "WA";
    private static final String PARENT_INFO_DDT = "DT";  
    private static final String PARENT_INFO_DA = "DA"; 
  

    public IndexedContainer getParentInfoGlu(CrossFormInputModel crossFormInputModel) {
	IndexedContainer container = new IndexedContainer();

	// Create the container properties
	addContainerPropertiesGlu(container);

	ArrayList<CrossResultTableModel> rows = crossFormInputModel.getCrossResultTableModel();

	for(CrossResultTableModel row:rows){
	    addTableGluContainer(container, row.getId(),row.getName(),row.getGenotype(),String.valueOf(row.getRmax()),String.valueOf(row.getExt()),String.valueOf(row.getWa()));
	}

	return container;
    }

    
    public IndexedContainer getParentInfoGluPin(CrossFormInputModel crossFormInputModel) {
	IndexedContainer container = new IndexedContainer();

	// Create the container properties
	addContainerPropertiesGluPin(container);

	ArrayList<CrossResultTableModel> rows = crossFormInputModel.getCrossResultTableModel();

	for(CrossResultTableModel row:rows){
	    addTableGluPinContainer(container, row.getId(),row.getName(),row.getGenotype(),String.valueOf(row.getRmax()),String.valueOf(row.getExt()),String.valueOf(row.getDdt()),String.valueOf(row.getWa()));
	}

	return container;
    }
    
    public IndexedContainer getParentInfoGluPinSpn(CrossFormInputModel crossFormInputModel) {
	IndexedContainer container = new IndexedContainer();

	// Create the container properties
	addContainerPropertiesGluPinSpn(container);

	ArrayList<CrossResultTableModel> rows = crossFormInputModel.getCrossResultTableModel();

	for(CrossResultTableModel row:rows){
	    addTableGluPinSpnContainer(container, row.getId(),row.getName(),row.getGenotype(),String.valueOf(row.getRmax()),String.valueOf(row.getExt()),String.valueOf(row.getDdt()),String.valueOf(row.getDa()));
	}

	return container;
    }
    

    private void addContainerPropertiesGlu(Container container) {
	container.addContainerProperty(PARENT_INFO_ID, Integer.class,null);
	container.addContainerProperty(PARENT_INFO_NAME, String.class, null);
	container.addContainerProperty(PARENT_INFO_GENOTYPE, String.class,null);
	container.addContainerProperty(PARENT_INFO_RMAX, String.class,null);
	container.addContainerProperty(PARENT_INFO_EXT, String.class, null);
	container.addContainerProperty(PARENT_INFO_WA, String.class, null);
    }

    
    private void addContainerPropertiesGluPin(Container container) {
	container.addContainerProperty(PARENT_INFO_ID, Integer.class,null);
	container.addContainerProperty(PARENT_INFO_NAME, String.class, null);
	container.addContainerProperty(PARENT_INFO_GENOTYPE, String.class,null);
	container.addContainerProperty(PARENT_INFO_RMAX, String.class,null);
	container.addContainerProperty(PARENT_INFO_EXT, String.class, null);
	container.addContainerProperty(PARENT_INFO_DDT, String.class, null);
	container.addContainerProperty(PARENT_INFO_WA, String.class, null);
    }
    
    private void addContainerPropertiesGluPinSpn(Container container) {
	container.addContainerProperty(PARENT_INFO_ID, Integer.class,null);
	container.addContainerProperty(PARENT_INFO_NAME, String.class, null);
	container.addContainerProperty(PARENT_INFO_GENOTYPE, String.class,null);
	container.addContainerProperty(PARENT_INFO_RMAX, String.class,null);
	container.addContainerProperty(PARENT_INFO_EXT, String.class, null);
	container.addContainerProperty(PARENT_INFO_DDT, String.class, null);
	container.addContainerProperty(PARENT_INFO_DA, String.class, null);
    }
    

    @SuppressWarnings("unchecked")
    private static void addTableGluContainer(Container container, Integer i, String name, String genotype, String rmax,String ext,String wa) {
	Object itemId = container.addItem();
	Item item = container.getItem(itemId);
	item.getItemProperty(PARENT_INFO_ID).setValue(i);
	item.getItemProperty(PARENT_INFO_NAME).setValue(name);
	item.getItemProperty(PARENT_INFO_GENOTYPE).setValue(genotype);
	item.getItemProperty(PARENT_INFO_RMAX).setValue(rmax);
	item.getItemProperty(PARENT_INFO_EXT).setValue(ext);
	item.getItemProperty(PARENT_INFO_WA).setValue(wa);

    }
    
    @SuppressWarnings("unchecked")
    private static void addTableGluPinContainer(Container container, Integer i, String name, String genotype, String rmax,String ext,String dda,String wa) {
	Object itemId = container.addItem();
	Item item = container.getItem(itemId);
	item.getItemProperty(PARENT_INFO_ID).setValue(i);
	item.getItemProperty(PARENT_INFO_NAME).setValue(name);
	item.getItemProperty(PARENT_INFO_GENOTYPE).setValue(genotype);
	item.getItemProperty(PARENT_INFO_RMAX).setValue(rmax);
	item.getItemProperty(PARENT_INFO_EXT).setValue(ext);
	item.getItemProperty(PARENT_INFO_DDT).setValue(dda);
	item.getItemProperty(PARENT_INFO_WA).setValue(wa);

    }
    
    @SuppressWarnings("unchecked")
    private static void addTableGluPinSpnContainer(Container container, Integer i, String name, String genotype, String rmax,String ext,String ddt,String da) {
	Object itemId = container.addItem();
	Item item = container.getItem(itemId);
	item.getItemProperty(PARENT_INFO_ID).setValue(i);
	item.getItemProperty(PARENT_INFO_NAME).setValue(name);
	item.getItemProperty(PARENT_INFO_GENOTYPE).setValue(genotype);
	item.getItemProperty(PARENT_INFO_RMAX).setValue(rmax);
	item.getItemProperty(PARENT_INFO_EXT).setValue(ext);
	item.getItemProperty(PARENT_INFO_DDT).setValue(ddt);
	item.getItemProperty(PARENT_INFO_DA).setValue(da);

    }
    

}

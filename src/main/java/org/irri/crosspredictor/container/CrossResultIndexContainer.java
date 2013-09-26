package org.irri.crosspredictor.container;

import java.util.ArrayList;
import org.irri.crosspredictor.model.CrossResultTableModel;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;


public class CrossResultIndexContainer{

    // GermplasmNamesAttribute Model
    private static final String CROSS_RESULT_CID = "CID";
    private static final String CROSS_RESULT_NAME = "Name";
    private static final String CROSS_RESULT_GENOTYPE = "Genotype";
    private static final String CROSS_RESULT_PERCENT = "Percent";
    private static final String CROSS_RESULT_PS95 = "PS95";
    private static final String CROSS_RESULT_PS99 = "PS99";
    private static final String CROSS_RESULT_RMAX = "RMax";
    private static final String CROSS_RESULT_EXT = "Ext";
    private static final String CROSS_RESULT_WA = "WA";
    private static final String CROSS_RESULT_DDT = "DT";  
    private static final String CROSS_RESULT_DA = "DA";
    private ArrayList<CrossResultTableModel> crossResultTable; 


    public CrossResultIndexContainer(ArrayList<CrossResultTableModel> crossResultTable) {
	this.crossResultTable=crossResultTable;
    }

    public IndexedContainer getCrossResultGlu( ) {

	IndexedContainer container = new IndexedContainer();
	addContainerPropertiesGlu(container);

	for(CrossResultTableModel row:this.crossResultTable){
	    addTableGluContainer(container, 
		    row.getCid(),
		    row.getName(),
		    row.getGenotype(),
		    String.valueOf(row.getFrePercentage()),
		    String.valueOf(row.getPs95()),
		    String.valueOf(row.getPs99()),
		    String.valueOf(row.getRmax()),
		    String.valueOf(row.getExt()),
		    String.valueOf(row.getWa()));
	}

	return container;
    }


    public IndexedContainer getCrossResultGluPin() {

	IndexedContainer container = new IndexedContainer();
	addContainerPropertiesGluPin(container);

	for(CrossResultTableModel row:this.crossResultTable){
	    addTableGluPinContainer(container, 
		    row.getCid(),
		    row.getName(),
		    row.getGenotype(),
		    String.valueOf(row.getFrePercentage()),
		    String.valueOf(row.getPs95()),
		    String.valueOf(row.getPs99()),
		    String.valueOf(row.getRmax()),
		    String.valueOf(row.getExt()),
		    String.valueOf(row.getDdt()),
		    String.valueOf(row.getWa()));
	}

	return container;
    }

    public IndexedContainer getCrossResultGluPinSpn() {

	IndexedContainer container = new IndexedContainer();
	addContainerPropertiesGluPinSpn(container);

	for(CrossResultTableModel row:this.crossResultTable){
	    addTableGluPinSpnContainer(container, 
		    row.getCid(),
		    row.getName(),
		    row.getGenotype(),
		    String.valueOf(row.getFrePercentage()),
		    String.valueOf(row.getPs95()),
		    String.valueOf(row.getPs99()),
		    String.valueOf(row.getRmax()),
		    String.valueOf(row.getExt()),
		    String.valueOf(row.getDdt()),
		    String.valueOf(row.getDa()));
	}

	return container;
    }


    private void addContainerPropertiesGlu(Container container) {
	container.addContainerProperty(CROSS_RESULT_CID, Integer.class,null);
	container.addContainerProperty(CROSS_RESULT_NAME, String.class, null);
	container.addContainerProperty(CROSS_RESULT_GENOTYPE, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PERCENT, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PS95, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PS99, String.class,null);
	container.addContainerProperty(CROSS_RESULT_RMAX, String.class,null);
	container.addContainerProperty(CROSS_RESULT_EXT, String.class, null);
	container.addContainerProperty(CROSS_RESULT_WA, String.class, null);
    }


    private void addContainerPropertiesGluPin(Container container) {
	container.addContainerProperty(CROSS_RESULT_CID, Integer.class,null);
	container.addContainerProperty(CROSS_RESULT_NAME, String.class, null);
	container.addContainerProperty(CROSS_RESULT_GENOTYPE, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PERCENT, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PS95, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PS99, String.class,null);
	container.addContainerProperty(CROSS_RESULT_RMAX, String.class,null);
	container.addContainerProperty(CROSS_RESULT_EXT, String.class, null);
	container.addContainerProperty(CROSS_RESULT_DDT, String.class, null);
	container.addContainerProperty(CROSS_RESULT_WA, String.class, null);
    }

    private void addContainerPropertiesGluPinSpn(Container container) {
	container.addContainerProperty(CROSS_RESULT_CID, Integer.class,null);
	container.addContainerProperty(CROSS_RESULT_NAME, String.class, null);
	container.addContainerProperty(CROSS_RESULT_GENOTYPE, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PERCENT, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PS95, String.class,null);
	container.addContainerProperty(CROSS_RESULT_PS99, String.class,null);
	container.addContainerProperty(CROSS_RESULT_RMAX, String.class,null);
	container.addContainerProperty(CROSS_RESULT_EXT, String.class, null);
	container.addContainerProperty(CROSS_RESULT_DDT, String.class, null);
	container.addContainerProperty(CROSS_RESULT_DA, String.class, null);
    }


    @SuppressWarnings("unchecked")
    private static void addTableGluContainer(Container container, Integer i, String name, String genotype,
	    String percent, String ps95,String ps99, String rmax,String ext,String wa) {
	Object itemId = container.addItem();
	Item item = container.getItem(itemId);
	item.getItemProperty(CROSS_RESULT_CID).setValue(i);
	item.getItemProperty(CROSS_RESULT_NAME).setValue(name);
	item.getItemProperty(CROSS_RESULT_GENOTYPE).setValue(genotype);
	item.getItemProperty(CROSS_RESULT_PERCENT).setValue(percent);
	item.getItemProperty(CROSS_RESULT_PS95).setValue(ps95);
	item.getItemProperty(CROSS_RESULT_PS99).setValue(ps99);
	item.getItemProperty(CROSS_RESULT_RMAX).setValue(rmax);
	item.getItemProperty(CROSS_RESULT_EXT).setValue(ext);
	item.getItemProperty(CROSS_RESULT_WA).setValue(wa);

    }

    @SuppressWarnings("unchecked")
    private static void addTableGluPinContainer(Container container, Integer i, String name, String genotype,
	    String percent, String ps95,String ps99, String rmax,String ext,String dda,String wa) {
	Object itemId = container.addItem();
	Item item = container.getItem(itemId);
	item.getItemProperty(CROSS_RESULT_CID).setValue(i);
	item.getItemProperty(CROSS_RESULT_NAME).setValue(name);
	item.getItemProperty(CROSS_RESULT_GENOTYPE).setValue(genotype);
	item.getItemProperty(CROSS_RESULT_PERCENT).setValue(percent);
	item.getItemProperty(CROSS_RESULT_PS95).setValue(ps95);
	item.getItemProperty(CROSS_RESULT_PS99).setValue(ps99);
	item.getItemProperty(CROSS_RESULT_RMAX).setValue(rmax);
	item.getItemProperty(CROSS_RESULT_EXT).setValue(ext);
	item.getItemProperty(CROSS_RESULT_DDT).setValue(dda);
	item.getItemProperty(CROSS_RESULT_WA).setValue(wa);

    }

    @SuppressWarnings("unchecked")
    private static void addTableGluPinSpnContainer(Container container, Integer i, String name, String genotype,
	    String percent, String ps95,String ps99, String rmax,String ext,String ddt,String da) {
	Object itemId = container.addItem();
	Item item = container.getItem(itemId);
	item.getItemProperty(CROSS_RESULT_CID).setValue(i);
	item.getItemProperty(CROSS_RESULT_NAME).setValue(name);
	item.getItemProperty(CROSS_RESULT_GENOTYPE).setValue(genotype);
	item.getItemProperty(CROSS_RESULT_PERCENT).setValue(percent);
	item.getItemProperty(CROSS_RESULT_PS95).setValue(ps95);
	item.getItemProperty(CROSS_RESULT_PS99).setValue(ps99);
	item.getItemProperty(CROSS_RESULT_RMAX).setValue(rmax);
	item.getItemProperty(CROSS_RESULT_EXT).setValue(ext);
	item.getItemProperty(CROSS_RESULT_DDT).setValue(ddt);
	item.getItemProperty(CROSS_RESULT_DA).setValue(da);

    }


}

package org.irri.crosspredictor.component;

import java.util.ArrayList;

import org.irri.crosspredictor.container.CrossResultIndexContainer;
import org.irri.crosspredictor.container.ParentInfoIndexContainer;
import org.irri.crosspredictor.model.CrossFormInputModel;
import org.irri.crosspredictor.model.CrossResultTableModel;
import org.irri.crosspreditor.helper.ConstantValue;
import org.irri.crosspreditor.helper.CrossResultHandler;
import org.vaadin.haijian.CSVExporter;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;


public class CrossResultComponent extends VerticalLayout{

    private static final long serialVersionUID = 1822195747725176490L;
    private static final float BUTTON_SIZE = 10;
    private static final float TEXTFIELD_SIZE = 100;
    private Table tableResult;
    private Table tableParentInfo;
    private ArrayList<CrossResultTableModel> crossResultTable;
    private Button btnSC;
    private CSVExporter btnExport;
    private TextField txtRMaxMax;;
    private TextField txtRMaxMin;
    private TextField txtExtMin;
    private TextField txtExtMax;
    private TextField txtWaMin;
    private TextField txtWaMax;
    private TextField txtDdtMin;
    private TextField txtDdtMax;
    private TextField txtDaMin;
    private TextField txtDaMax;


    private Button btnOk;
    private Button btnCancel;
    private CrossFormInputModel input;

    public CrossResultComponent(CrossFormInputModel input){
	this.input=input;

	HorizontalLayout hlayout= new HorizontalLayout();
	hlayout.setMargin(true);
	hlayout.setSpacing(true);

	tableParentInfo = new Table("Parent Information");
	ParentInfoIndexContainer dataIndexContainer= new ParentInfoIndexContainer();

	if(this.input.getGeneType().equals(ConstantValue.GENETYPE_GLU)){
	    IndexedContainer dataSourceGlu = dataIndexContainer.getParentInfoGlu(input);
	    tableParentInfo.setContainerDataSource(dataSourceGlu);
	}else if(this.input.getGeneType().equals(ConstantValue.GENETYPE_GLUPIN)){
	    IndexedContainer dataSourceGluPin = dataIndexContainer.getParentInfoGluPin(input);
	    tableParentInfo.setContainerDataSource(dataSourceGluPin);
	}else{
	    IndexedContainer dataSourceGluPinSpn = dataIndexContainer.getParentInfoGluPinSpn(input);
	    tableParentInfo.setContainerDataSource(dataSourceGluPinSpn); 
	}

	hlayout.addComponent(tableParentInfo);

	tableResult = new Table("Result of all Crosses");
	crossResultTable = new CrossResultHandler(input).getCrossResultTable();
	CrossResultIndexContainer dataIndexCrossResultContainer= new CrossResultIndexContainer(crossResultTable);

	if(this.input.getGeneType().equals(ConstantValue.GENETYPE_GLU)){
	    IndexedContainer dataSourceGlu = dataIndexCrossResultContainer.getCrossResultGlu();
	    tableResult.setContainerDataSource(dataSourceGlu);
	}else if(this.input.getGeneType().equals(ConstantValue.GENETYPE_GLUPIN)){
	    IndexedContainer dataSourceGluPin = dataIndexCrossResultContainer.getCrossResultGluPin();
	    tableResult.setContainerDataSource(dataSourceGluPin);
	}else{
	    IndexedContainer dataSourceGluPinSpn = dataIndexCrossResultContainer.getCrossResultGluPinSpn();
	    tableResult.setContainerDataSource(dataSourceGluPinSpn); 
	}

	hlayout.addComponent(tableResult);


	btnSC = new Button("Selection Criteria");
	btnSC.setWidth(BUTTON_SIZE, Unit.EM);
	btnSC.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(final ClickEvent event) {
		//		Notification.show("Selection Criteria button was clicked", Type.TRAY_NOTIFICATION);
		getUI().addWindow(openSelectionCriteriaSubWindow());
	    }
	});

	// Exporter Button - Add-on
	btnExport = new CSVExporter();
	btnExport.setCaption("Export to CSV");
	btnExport.setContainerToBeExported(tableResult.getContainerDataSource());
	btnExport.setVisibleColumns(tableResult.getVisibleColumns());
	btnExport.setDownloadFileName(tableResult.getCaption());

	hlayout.addComponents(btnSC, btnExport);

	addComponent(hlayout);

    }

    private Window openSelectionCriteriaSubWindow(){
	final Window subWindow = new Window("Selection Criteria");

	VerticalLayout subWindowVertical = new VerticalLayout();
	subWindowVertical.setMargin(true);
	subWindowVertical.setSpacing(true);
	subWindow.setResizable(false);
	subWindow.setContent(subWindowVertical);

	HorizontalLayout layoutButton= selectionCriteriaButton(subWindow);
	
	if(this.input.getGeneType().equals(ConstantValue.GENETYPE_GLU)){
	    subWindowVertical.addComponents(gluSelectionCriteriaComponent());
	}else if(input.getGeneType().equals(ConstantValue.GENETYPE_GLUPIN)){
	    subWindowVertical.addComponents(gluPinSelectionCriteriaComponent());
	}else{
	    subWindowVertical.addComponents(gluPinSpnSelectionCriteriaComponent());
	}
	
	subWindowVertical.addComponents(layoutButton);
	subWindowVertical.setComponentAlignment(layoutButton, Alignment.BOTTOM_RIGHT);
	subWindow.center();

	return subWindow;
    }


    private Component gluSelectionCriteriaComponent(){
	VerticalLayout layout = new VerticalLayout();


	GridLayout grid = new GridLayout(5, 4);
	grid.setSpacing(true);

	txtRMaxMin= new TextField();
	txtRMaxMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtExtMin= new TextField();
	txtExtMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtWaMin= new TextField();
	txtWaMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);

	txtRMaxMax= new TextField();
	txtRMaxMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtExtMax= new TextField();
	txtExtMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtWaMax = new TextField();
	txtWaMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);

	grid.addComponent(new Label("RMax"), 1, 1);
	grid.addComponent(new Label("Ext"), 2, 1);
	grid.addComponent(new Label("WA"), 3, 1);

	grid.addComponent(new Label("Minimum"), 0, 2);
	grid.addComponent(new Label("Maximum"), 0, 3);

	grid.addComponent(txtRMaxMin, 1, 2);
	grid.addComponent(txtExtMin, 2, 2);
	grid.addComponent(txtWaMin, 3, 2);

	grid.addComponent(txtRMaxMax, 1, 3);
	grid.addComponent(txtExtMax, 2, 3);
	grid.addComponent(txtWaMax, 3, 3);

	layout.addComponents(grid);

	return layout;

    }
    
    
    private Component gluPinSelectionCriteriaComponent(){
	VerticalLayout layout = new VerticalLayout();
	GridLayout grid = new GridLayout(5, 4);
	grid.setSpacing(true);

	txtRMaxMin= new TextField();
	txtRMaxMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtExtMin= new TextField();
	txtExtMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtDdtMin= new TextField();
	txtDdtMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtWaMin= new TextField();
	txtWaMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);

	txtRMaxMax= new TextField();
	txtRMaxMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtExtMax= new TextField();
	txtExtMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtDdtMax= new TextField();
	txtDdtMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtWaMax = new TextField();
	txtWaMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);

	grid.addComponent(new Label("RMax"), 1, 1);
	grid.addComponent(new Label("Ext"), 2, 1);
	grid.addComponent(new Label("DDT"), 3, 1);
	grid.addComponent(new Label("WA"), 4, 1);

	grid.addComponent(new Label("Minimum"), 0, 2);
	grid.addComponent(new Label("Maximum"), 0, 3);

	grid.addComponent(txtRMaxMin, 1, 2);
	grid.addComponent(txtExtMin, 2, 2);
	grid.addComponent(txtDdtMin, 3, 2);
	grid.addComponent(txtWaMin, 4, 2);

	grid.addComponent(txtRMaxMax, 1, 3);
	grid.addComponent(txtExtMax, 2, 3);
	grid.addComponent(txtDdtMax, 3, 3);
	grid.addComponent( txtWaMax, 4, 3);

	layout.addComponents(grid);

	return layout;

    }
    
    private Component gluPinSpnSelectionCriteriaComponent(){
	
	VerticalLayout layout = new VerticalLayout();
	GridLayout grid = new GridLayout(5, 4);
	grid.setSpacing(true);

	txtRMaxMin= new TextField();
	txtRMaxMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtExtMin= new TextField();
	txtExtMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtDdtMin= new TextField();
	txtDdtMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtDaMin= new TextField();
	txtDaMin.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);

	txtRMaxMax= new TextField();
	txtRMaxMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtExtMax= new TextField();
	txtExtMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtDdtMax= new TextField();
	txtDdtMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);
	txtDaMax = new TextField();
	txtDaMax.setWidth(TEXTFIELD_SIZE,Unit.PIXELS);

	grid.addComponent(new Label("RMax"), 1, 1);
	grid.addComponent(new Label("Ext"), 2, 1);
	grid.addComponent(new Label("DDT"), 3, 1);
	grid.addComponent(new Label("DA"), 4, 1);

	grid.addComponent(new Label("Minimum"), 0, 2);
	grid.addComponent(new Label("Maximum"), 0, 3);

	grid.addComponent(txtRMaxMin, 1, 2);
	grid.addComponent(txtExtMin, 2, 2);
	grid.addComponent(txtDdtMin, 3, 2);
	grid.addComponent(txtDaMin, 4, 2);

	grid.addComponent(txtRMaxMax, 1, 3);
	grid.addComponent(txtExtMax, 2, 3);
	grid.addComponent(txtDdtMax, 3, 3);
	grid.addComponent( txtDaMax, 4, 3);

	layout.addComponents(grid);
	return layout;

    }
    

    private HorizontalLayout selectionCriteriaButton(final Window subWindow){
	HorizontalLayout layoutButton= new HorizontalLayout();
	layoutButton.setSpacing(true);

	btnOk = new Button("Ok");
	btnOk.setWidth(BUTTON_SIZE, Unit.EM);
	btnOk.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(final ClickEvent event) {
		Notification.show("TODO: Code for filter selection criteria when button is clicked here!", Type.TRAY_NOTIFICATION);
		subWindow.close(); // close SubWindow
	    }
	});

	layoutButton.addComponent(btnOk);

	btnCancel = new Button("Cancel");
	btnCancel.setWidth(BUTTON_SIZE, Unit.EM);
	btnCancel.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(final ClickEvent event) {
		Notification.show("Selection Criteria Closed ", Type.TRAY_NOTIFICATION);
		subWindow.close();
	    }
	});

	layoutButton.addComponent(btnCancel);
	return layoutButton;
    }

}

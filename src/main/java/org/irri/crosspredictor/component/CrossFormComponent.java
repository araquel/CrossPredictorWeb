package org.irri.crosspredictor.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.irri.crosspredictor.handler.SelectedTabCloseHandler;
import org.irri.crosspredictor.listener.OptionGroupValueChangeListener;
import org.irri.crosspredictor.listener.PListValueChangeListener;
import org.irri.crosspredictor.model.CrossFormInputModel;
import org.irri.crosspreditor.helper.CrossFormHelper;
import org.irri.crosspreditor.helper.CrossResultHandler;
import org.irri.crosspreditor.helper.CrossResultHelper;
import org.irri.crosspreditor.helper.ConstantValue;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;


public class CrossFormComponent extends VerticalLayout{

    private static final long serialVersionUID = 1L;
    private static final int PARENTLIST_NUM_ROW = 15;
    private static final int PARENTLIST_WIDTH = 250;
    private static final int PARENTLIST_HEIGHT = 460;
    private static final int PARENT_CROSS_HEIGHT=150;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_SIZE = 10;
    private ComboBox comboBoxGeneType;
    private CrossFormHelper crossFormHelper;
    private OptionGroup parentFileOptionGroup;
    private OptionGroup inBreedingOptionGroup;
    private OptionGroup estimateSourceOptionGroup;
    private OptionGroup crossTypeOptionGroup;
    private ListSelect listParents;
    private CheckBox checkBoxP1;
    private CheckBox checkBoxP2;
    private CheckBox checkBoxP3;
    private ListSelect listParent1;
    private ListSelect listParent2;
    private ListSelect listParent3;
    private Button buttonReset;
    private Button buttonRemove;
    private Button buttonAdd;
    private Upload uploadParentFile;
    private Upload uploadEstimateSource;
    private Button buttonGenerateCross;
    private TabSheet tabSheetCrossResult;
    private CrossFormInputModel crossFormInputModel;
    private TabSheet mainLayoutTabSheet;
    private ConstantValue contantValue;
	private String tmpEstimatesFile;
	private String tmpParentFile;
	private File uploadedEstimatesFile;
	private File uploadedParentFile;

    public CrossFormComponent(TabSheet mainLayoutTabSheet, TabSheet tabSheetCrossResult){

	setMargin(true);
	setSpacing(true);
	setSizeFull();
	crossFormHelper= new CrossFormHelper();
	contantValue= new ConstantValue();
	crossFormInputModel= new CrossFormInputModel();
	this.mainLayoutTabSheet=mainLayoutTabSheet;
	this.tabSheetCrossResult=tabSheetCrossResult;
	this.crossFormInputModel.setInBreedOption(contantValue.INBREEDING_RIL);
	this.crossFormInputModel.setCrossType(contantValue.TWO_WAY_CROSSTYPE);
	assembleComponent();

    }

    public void assembleComponent(){

	setSpacing(true);
	setMargin(true);
	addComponent(geneTypeComboBoxComponent());
	addComponent(parentFileOptionGroupComponent());
	addComponent(estimateSourceOptionGroupComponent());

	HorizontalLayout layoutOtherOption=new HorizontalLayout();
	layoutOtherOption.setSpacing(true);
	layoutOtherOption.addComponent(inbreedingOptionOptionGroupComponent());
	layoutOtherOption.addComponent(crossTypeOptionGroupComponent());
	addComponent(layoutOtherOption);

	HorizontalLayout parentCrossingLayout=new HorizontalLayout();
	parentCrossingLayout.addComponent(parentListSelectComponent());
	parentCrossingLayout.addComponent(parentToBeCrossedComponent());

	addComponent(parentCrossingLayout);
	addComponent(generateCrossButtonComponent());
    }

    private Component geneTypeComboBoxComponent(){
	comboBoxGeneType = new ComboBox("Gene Type");
	comboBoxGeneType.setTextInputAllowed(false);
	comboBoxGeneType.setNullSelectionAllowed(false);
	comboBoxGeneType.setImmediate(true); // send the change to the server at once
	crossFormHelper.populateGeneType(comboBoxGeneType);

	comboBoxGeneType.addValueChangeListener(new ValueChangeListener() {
	    @Override
	    public void valueChange(ValueChangeEvent event) {
		final String valueString = String.valueOf(event.getProperty()
			.getValue());
		//		Notification.show("Gene Type Value changed:", valueString,Type.TRAY_NOTIFICATION);
		String filePath = ConstantValue.BASE_PATH_INPUT +valueString+"list.csv";
		crossFormHelper.populateParentList(new File(filePath),listParents);

		buttonAdd.setEnabled(true);
		crossFormInputModel.setGeneType(valueString); // set geneType;
		crossFormInputModel.setRf(crossFormHelper.getRfOfParent(new File(filePath))); // set rf
		listParent1.removeAllItems();
		listParent2.removeAllItems();
		listParent3.removeAllItems();

		crossFormHelper.resetOptionGroup(parentFileOptionGroup);
		crossFormHelper.resetOptionGroup(estimateSourceOptionGroup);
	    }
	});

	return comboBoxGeneType;
    }

    @SuppressWarnings("unused")
    private Component parentFileOptionGroupComponent(){

	VerticalLayout layout = new VerticalLayout();
	parentFileOptionGroup = new OptionGroup("Parent File");
	parentFileOptionGroup.addItem(0);
	parentFileOptionGroup.setItemCaption(0, "Default");
	parentFileOptionGroup.addItem(1);
	parentFileOptionGroup.setItemCaption(1, "Specified");
	parentFileOptionGroup.select(0);
	parentFileOptionGroup.setNullSelectionAllowed(false);
	parentFileOptionGroup.setHtmlContentAllowed(true);
	parentFileOptionGroup.setImmediate(true);
	layout.addComponent(parentFileOptionGroup);
	layout.addComponent(parentFileUploadFileComponent());

	return layout;
    }


    @SuppressWarnings("deprecation")
	private Component parentFileUploadFileComponent(){
	uploadParentFile = new Upload(null, new Upload.Receiver() {
	    @Override
	    public OutputStream receiveUpload(String filename, String mimeType) {
		try {
		    /* Here, we'll stored the uploaded file as a temporary file. No doubt there's
                    a way to use a ByteArrayOutputStream, a reader around it, use ProgressListener (and
                    a progress bar) and a separate reader thread to populate a container *during*
                    the update.

                    This is quick and easy example, though.
		     */
		    tmpParentFile = new File(filename).getName();
		    uploadedParentFile= new File(crossFormHelper.getUploadedFilePath(filename));
		    //		          tempFile = File.createTempFile("temp", ".csv");
		    return new FileOutputStream(uploadedParentFile);
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		}
	    }
	});
	uploadParentFile.addListener(new Upload.FinishedListener() {
		@Override
		public void uploadFinished(Upload.FinishedEvent finishedEvent) {
			if(crossFormHelper.checkParentFileContentIfCompatible(comboBoxGeneType, uploadedParentFile)){
				Notification.show("Uploaded file: ", tmpParentFile,
						Type.TRAY_NOTIFICATION);
				listParents.removeAllItems();
				buttonAdd.setEnabled(true);
				crossFormHelper.populateParentList(uploadedParentFile, listParents);
				parentFileOptionGroup.setItemCaption(1, "Specified: "+tmpParentFile);
			}
			else{
				if(!parentFileOptionGroup.getItemCaption(1).contains(".")) parentFileOptionGroup.select(0);
				new Notification("Invalid file.",
						"",
						Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
			}
		}
	});
    uploadParentFile.setEnabled(false);
	parentFileOptionGroup.addValueChangeListener(new OptionGroupValueChangeListener(parentFileOptionGroup, uploadParentFile, comboBoxGeneType));
	return uploadParentFile;
    }
    
    @SuppressWarnings("unused")
    private Component inbreedingOptionOptionGroupComponent(){
	inBreedingOptionGroup = new OptionGroup("Inbreeding Option");
	inBreedingOptionGroup.addItem(0);
	inBreedingOptionGroup.setItemCaption(0, "RIL");
	inBreedingOptionGroup.addItem(1);
	inBreedingOptionGroup.setItemCaption(1, "DH");
	inBreedingOptionGroup.select(0);
	inBreedingOptionGroup.setNullSelectionAllowed(false);
	inBreedingOptionGroup.setHtmlContentAllowed(true);
	inBreedingOptionGroup.setImmediate(true);
	inBreedingOptionGroup.setWidth("250px");

	inBreedingOptionGroup.addValueChangeListener(new ValueChangeListener(){
	    @Override
	    public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		String selected = inBreedingOptionGroup.getValue().toString();
		if(selected.equals("0")){
		    crossFormInputModel.setCrossType(contantValue.INBREEDING_RIL);
		}else{
		    crossFormInputModel.setCrossType(contantValue.INBREEDING_DH);
		}
	    }
	});

	return inBreedingOptionGroup;
    }

    @SuppressWarnings("unused")
    private Component estimateSourceOptionGroupComponent(){
	VerticalLayout layout = new VerticalLayout();
	estimateSourceOptionGroup = new OptionGroup("Estimate Source");
	estimateSourceOptionGroup.addItem(0);
	estimateSourceOptionGroup.setItemCaption(0, "Default");
	estimateSourceOptionGroup.addItem(1);
	estimateSourceOptionGroup.setItemCaption(1, "Specified");
	estimateSourceOptionGroup.select(0);
	estimateSourceOptionGroup.setNullSelectionAllowed(false);
	estimateSourceOptionGroup.setHtmlContentAllowed(true);
	estimateSourceOptionGroup.setImmediate(true);
	layout.addComponent(estimateSourceOptionGroup);
	layout.addComponent(estimateSourceUploadFileComponent());

	return layout;
    }

    @SuppressWarnings("deprecation")
	private Component estimateSourceUploadFileComponent(){
	uploadEstimateSource = new Upload(null, new Upload.Receiver() {
	    @Override
	    public OutputStream receiveUpload(String filename, String mimeType) {
		try {
		    /* Here, we'll stored the uploaded file as a temporary file. No doubt there's
                    a way to use a ByteArrayOutputStream, a reader around it, use ProgressListener (and
                    a progress bar) and a separate reader thread to populate a container *during*
                    the update.

                    This is quick and easy example, though.
		     */
		    tmpEstimatesFile = new File(filename).getName();
		    uploadedEstimatesFile= new File(crossFormHelper.getUploadedFilePath(filename));
		    return new FileOutputStream(uploadedEstimatesFile);
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		}
	    }
	});
	uploadEstimateSource.addListener(new Upload.FinishedListener() {
		@Override
		public void uploadFinished(Upload.FinishedEvent finishedEvent) {
			if(crossFormHelper.checkEstimatesFileContentIfCompatible(comboBoxGeneType, uploadedEstimatesFile)){
				Notification.show("Uploaded file: ", tmpParentFile,
						Type.TRAY_NOTIFICATION);
				buttonAdd.setEnabled(true);
				crossFormHelper.populateParentList(uploadedParentFile, listParents);
				estimateSourceOptionGroup.setItemCaption(1, "Specified: "+tmpEstimatesFile);
			}
			else{
				if(!	estimateSourceOptionGroup.getItemCaption(1).contains(".")) 	estimateSourceOptionGroup.select(0);
				new Notification("Invalid file.",
						"",
						Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
			}
		}
	});
	estimateSourceOptionGroup.addValueChangeListener(new OptionGroupValueChangeListener(estimateSourceOptionGroup, uploadEstimateSource, comboBoxGeneType));
	uploadEstimateSource.setEnabled(false);
	return uploadEstimateSource;
    }

    @SuppressWarnings("unused")
    private Component crossTypeOptionGroupComponent(){
	crossTypeOptionGroup = new OptionGroup("Cross Type");
	crossTypeOptionGroup.addItem(0);
	crossTypeOptionGroup.setItemCaption(0, "Two Way P1/P2");
	crossTypeOptionGroup.addItem(1);
	crossTypeOptionGroup.setItemCaption(1, "Three Way P1/P2/P3");
	crossTypeOptionGroup.select(0);
	crossFormInputModel.setCrossType(contantValue.TWO_WAY_CROSSTYPE);
	crossTypeOptionGroup.setNullSelectionAllowed(false);
	crossTypeOptionGroup.setHtmlContentAllowed(true);
	crossTypeOptionGroup.setImmediate(true);

	crossTypeOptionGroup.addValueChangeListener(new ValueChangeListener(){
	    @Override
	    public void valueChange(ValueChangeEvent event){
		// TODO Auto-generated method stub
		String selected = crossTypeOptionGroup.getValue().toString();
		if(selected.equals("0")){
		    listParent3.setVisible(false);
		    checkBoxP3.setVisible(false);
		    if (listParent3.size()>0){
			listParent3.removeAllItems();
		    }
		    crossFormInputModel.setCrossType(contantValue.TWO_WAY_CROSSTYPE);
		}else{
		    listParent3.setVisible(true);
		    checkBoxP3.setVisible(true);
		    crossFormInputModel.setCrossType(contantValue.THREE_WAY_CROSSTYPE);
		}
	   }
	});
	return crossTypeOptionGroup;
    }


    private Component parentListSelectComponent(){

	HorizontalLayout layoutHorizontal= new HorizontalLayout();
	layoutHorizontal.setMargin(true);

	VerticalLayout layoutButton = new VerticalLayout();
	layoutButton.setMargin(true);


	listParents = new ListSelect("Parents List");
	listParents.setMultiSelect(true);
	listParents.setRows(PARENTLIST_NUM_ROW); // perfect length in out case
	listParents.setNullSelectionAllowed(false); // user can not 'unselect'
	listParents.setImmediate(true); // send the change to the server at once
	listParents.setWidth(PARENTLIST_WIDTH, Unit.PIXELS);
	listParents.setHeight(PARENTLIST_HEIGHT,Unit.PIXELS);

	buttonReset = new Button("Reset");
	buttonReset.setWidth(BUTTON_WIDTH,Unit.PIXELS);
	buttonReset.setEnabled(false);
	buttonReset.addClickListener(new ClickListener() {
		@Override
		public void buttonClick(final ClickEvent event) {
			buttonRemove.setEnabled(false);
			crossFormHelper.resetParentListForm(listParent1, listParent2, listParent3);
			Notification.show("Parent Cross Form was reset",
					Type.TRAY_NOTIFICATION);
		}
	});

	buttonAdd = new Button("Add");
	buttonAdd.setWidth(BUTTON_WIDTH, Unit.PIXELS);
	buttonAdd.setEnabled(false);
	buttonAdd.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(final ClickEvent event) {
		try{
		    crossFormHelper.setSelectedItemToCross(listParents,getParentToCrossSelectList());
		    buttonReset.setEnabled(true);
		}catch(NullPointerException npe){
		    new Notification("Please specify P1/P2/P3","",Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
		}
	    }

	});

	buttonRemove = new Button("Remove");
	buttonRemove.setWidth(BUTTON_WIDTH, Unit.PIXELS);
	buttonRemove.setEnabled(false);
	buttonRemove.addClickListener(new ClickListener() {
		@Override
		public void buttonClick(final ClickEvent event) {
			try{
				crossFormHelper.removeSelectedItemsFromList(getParentToCrossSelectList());
				buttonRemove.setEnabled(false);
			}catch(NullPointerException npe){
				new Notification("Please specify P1/P2/P3",
						"",
						Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
			}
		}
	});
	
	layoutButton.addComponent(buttonAdd);
	layoutButton.addComponent(buttonRemove);
	layoutButton.addComponent(buttonReset);

	layoutHorizontal.addComponent(listParents);
	layoutHorizontal.addComponent(layoutButton);

	return layoutHorizontal;
    }


    private Component parentToBeCrossedComponent(){

	VerticalLayout layoutParentToCross= new VerticalLayout();
	checkBoxP1 = new CheckBox("P1", true);
	checkBoxP1.setValue(true);
	checkBoxP2 = new CheckBox("P2", true);
	checkBoxP3 = new CheckBox("P3", true);
	checkBoxP3.setVisible(false);
	crossFormHelper.setOtherParentCheckboxUnchecked(checkBoxP2,checkBoxP3);

	checkBoxP1.addValueChangeListener(new ValueChangeListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void valueChange(final ValueChangeEvent event) {
		if(checkBoxP1.getValue()==true){
		    crossFormHelper.setOtherParentCheckboxUnchecked(checkBoxP2,checkBoxP3);
		}
		else listParent1.setValue(null);
	    }
	});

	checkBoxP2.addValueChangeListener(new ValueChangeListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void valueChange(final ValueChangeEvent event) {
		if(checkBoxP2.getValue()==true){
		    crossFormHelper.setOtherParentCheckboxUnchecked(checkBoxP1,checkBoxP3);
		}
		else listParent2.setValue(null);
	    }
	});

	checkBoxP3.addValueChangeListener(new ValueChangeListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void valueChange(final ValueChangeEvent event) {
		if(checkBoxP3.getValue()==true){
		    crossFormHelper.setOtherParentCheckboxUnchecked(checkBoxP1,checkBoxP2);
		}
		else listParent3.setValue(null);
	    }
	});

	listParent1 = new ListSelect();
	listParent2 = new ListSelect();
	listParent3 = new ListSelect();
	
	listParent1.setMultiSelect(true);
	listParent1.setRows(PARENTLIST_NUM_ROW); // perfect length in out case
	listParent1.setNullSelectionAllowed(false); // user can not 'unselect'
	listParent1.setImmediate(true); // send the change to the server at once
	listParent1.setWidth(PARENTLIST_WIDTH, Unit.PIXELS);
	listParent1.setHeight(PARENT_CROSS_HEIGHT, Unit.PIXELS);
	listParent1.addValueChangeListener(new PListValueChangeListener(listParent1, listParent2, listParent3, buttonRemove, buttonReset));


	listParent2.setMultiSelect(true);
	listParent2.setRows(PARENTLIST_NUM_ROW); // perfect length in out case
	listParent2.setNullSelectionAllowed(false); // user can not 'unselect'
	listParent2.setImmediate(true); // send the change to the server at once
	listParent2.setWidth(PARENTLIST_WIDTH, Unit.PIXELS);
	listParent2.setHeight(PARENT_CROSS_HEIGHT, Unit.PIXELS);
	listParent2.addValueChangeListener(new PListValueChangeListener(listParent2, listParent1, listParent3, buttonRemove, buttonReset));

	listParent3.setMultiSelect(true);
	listParent3.setRows(PARENTLIST_NUM_ROW); // perfect length in out case
	listParent3.setNullSelectionAllowed(false); // user can not 'unselect'
	listParent3.setImmediate(true); // send the change to the server at once
	listParent3.setWidth(PARENTLIST_WIDTH, Unit.PIXELS);
	listParent3.setHeight(PARENT_CROSS_HEIGHT, Unit.PIXELS);
	listParent3.setVisible(false);
	listParent3.addValueChangeListener(new PListValueChangeListener(listParent3, listParent2, listParent1, buttonRemove, buttonReset));

	layoutParentToCross.addComponent(checkBoxP1);
	layoutParentToCross.addComponent(listParent1);
	layoutParentToCross.addComponent(checkBoxP2);
	layoutParentToCross.addComponent(listParent2);
	layoutParentToCross.addComponent(checkBoxP3);
	layoutParentToCross.addComponent(listParent3);

	return layoutParentToCross;

    }   
    private Component generateCrossButtonComponent(){
	buttonGenerateCross = new Button("Generate Cross");
	buttonGenerateCross.setWidth(BUTTON_WIDTH, Unit.PIXELS);
	buttonGenerateCross.setEnabled(true);
	buttonGenerateCross.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(final ClickEvent event) {
		try{
		    CrossResultHelper crossResultHelper;
		    if(crossFormInputModel.getGeneType().equals(ConstantValue.GENETYPE_GLU)){
			crossResultHelper= new CrossResultHelper(ConstantValue.GLU_EST_FILE,crossFormInputModel.getGeneType());
		    }else if(crossFormInputModel.getGeneType().equals(ConstantValue.GENETYPE_GLUPIN)){
			crossResultHelper= new CrossResultHelper(ConstantValue.GLUPIN_EST_FILE,crossFormInputModel.getGeneType());
		    }else{
			crossResultHelper= new CrossResultHelper(ConstantValue.GLUPINSPN_EST_FILE,crossFormInputModel.getGeneType());
		    }


		    crossFormInputModel.setCrossResultTableModel(crossResultHelper.getParentInfoData(listParent1,listParent2,listParent3));
		    crossFormInputModel.setListParent1(crossResultHelper.getParent(listParent1));
		    crossFormInputModel.setListParent2(crossResultHelper.getParent(listParent2));
		    crossFormInputModel.setListParent3(crossResultHelper.getParent(listParent3));
		    String tabName=crossFormHelper.getCrossResultTabName(crossFormInputModel);


		    Tab tab = tabSheetCrossResult.addTab(new CrossResultComponent(crossFormInputModel), tabName, null);
		    tab.setClosable(true);
		    tab.setDescription("Tab");
		    //	                tabSheetCrossResult.setSelectedTab(detailLayout);
		    tabSheetCrossResult.setCloseHandler(new SelectedTabCloseHandler());
		    mainLayoutTabSheet.setSelectedTab(1);
		    tabSheetCrossResult.setSelectedTab(tabSheetCrossResult.getComponentCount()-1);
		}catch(NullPointerException npe){
		    new Notification("","",Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
		}
	    }

	});

	return buttonGenerateCross;
    }

    private ListSelect getParentToCrossSelectList() {
	if(checkBoxP1.getValue())  
	    return listParent1;
	else if (checkBoxP2.getValue())  
	    return listParent2;
	else if(checkBoxP3.getValue() && listParent3.isVisible()) 
	    return listParent3;
	else 
	    return null;
    }

}


package org.irri.crosspreditor.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.irri.crosspredictor.model.CrossFormInputModel;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;


public class CrossFormHelper{
    private String BSLASH = "\\";
    private String FSLASH = "/";
    public String BASE_PATH=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath().replace(BSLASH, FSLASH);

    public enum GeneType{

	Glu("Glu"),GluPin("GluPin"),GluPinSpn("GluPinSpn");

	private String geneType;

	private GeneType(String s){
	    geneType=s;

	}

	public String getGeneType(){
	    return geneType;
	}

    }    


    public CrossFormHelper(){


    }


    public void populateGeneType(ComboBox comboBoxGeneType){
	comboBoxGeneType.addItem(GeneType.Glu);
	comboBoxGeneType.addItem(GeneType.GluPin);
	comboBoxGeneType.addItem(GeneType.GluPinSpn);
    }


    public void populateParentList(File file, ListSelect listParents) {

	Scanner opnScanner = null;
	int ctr=0;
	listParents.removeAllItems();
	try {
	    opnScanner = new Scanner(file);
	    while(opnScanner.hasNext()) {
		    String lineValue="";

		    String newLine = opnScanner.nextLine();
		    if(ctr>1){      // Read each line and display its value
			int c = 0;
			for(String s: newLine.split(",")){
			    if(c==0){
				lineValue=s+"-";
			    }else{
				lineValue=lineValue+s+",";
			    }
			    c++;
			}
			listParents.addItem(lineValue);
		    }
		    ctr++;
		}
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println("file not found.");
	}finally{
	    opnScanner.close(); 
	}
    }
    
    public String getRfOfParent(File file){
	String toreturn = null;
	Scanner opnScanner = null;
	 try {
	    opnScanner = new Scanner(file);
	    int counter=0;
	    while(opnScanner.hasNext()) {
		String row = opnScanner.nextLine();
		    if(counter==1){
			
			String[] tmpPList=row.split(",");
			String freValue="";
			for(int i=1;i<tmpPList.length;i++){
				freValue+=tmpPList[i]+",";
			}
			toreturn=freValue;
			
			break;
		    }
		    counter++;
	    }
	    
	    
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}finally{
	    opnScanner.close(); 
	}
	return toreturn.substring(0,toreturn.length()-1);
    }


    public void resetParentList(ListSelect listParents) {
	listParents.removeAllItems();
    }


    public static String getTimeStamp() {
	Calendar now = Calendar.getInstance();
	return Long.toString(now.getTimeInMillis());
    }


    public void setSelectedItemToCross(ListSelect fromList, ListSelect toList) {
	try{
	    Object values = fromList.getValue();
	    if (values instanceof Set) {
		// Multiple selected values returned as a Set
		List<String> list = new ArrayList<String>((Set) values);
		for(String s: list){
			toList.addItem(s);
		}
	    } else {
	    	toList.addItem(fromList.getValue());
	    }
	}catch(NullPointerException npe){
	    new Notification("Please specify P1/P2/P3"+fromList.getCaption(),"",Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
	}
    }


    public void setOtherParentCheckboxUnchecked(CheckBox checkBox1, CheckBox checkBox2) {
	checkBox1.setValue(false);
	checkBox2.setValue(false);
    }

    public String getCrossResultTabName(CrossFormInputModel crossFormInputModel){
	return crossFormInputModel.getGeneType()+"-"+crossFormInputModel.getCrossType();
    }


	public void resetParentListForm(ListSelect listParent1,
			ListSelect listParent2, ListSelect listParent3) {
		// TODO Auto-generated method stub
		listParent1.removeAllItems();
		listParent2.removeAllItems();
		listParent3.removeAllItems();
	}


	public void removeSelectedItemsFromList(ListSelect list) {
		// TODO Auto-generated method stub
			try{
				Object values = list.getValue();
				if (values instanceof Set) {
					List<String> stringList = new ArrayList<String>((Set) values);
					for(String s: stringList){
						list.removeItem(s);
					}
				} else {
					list.removeItem(list.getValue());
				}
			}catch(NullPointerException npe){
				new Notification("Select the parent you want to remove from "+list.getCaption(),
						"",
						Notification.TYPE_WARNING_MESSAGE, true).show(Page.getCurrent());
			}
	}


	public boolean checkParentFileContentIfCompatible(
			ComboBox comboBoxGeneType, File uploadedParentFile) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			boolean compareHeaders = false;
			if(comboBoxGeneType.getValue().equals(GeneType.Glu)){
				compareHeaders = compare(ConstantValue.GLU_LIST_HEADER, uploadedParentFile);
			}
			else if(comboBoxGeneType.getValue().equals(GeneType.GluPin)) compareHeaders = compare(ConstantValue.GLUPIN_LIST_HEADER, uploadedParentFile);
			else compareHeaders = compare(ConstantValue.GLUPINSPN_LIST_HEADER, uploadedParentFile);
			return compareHeaders;
	}
	
	public boolean checkEstimatesFileContentIfCompatible(
			ComboBox comboBoxGeneType, File uploadedEstimatesFile) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				boolean compareHeaders = false;
				if(comboBoxGeneType.getValue().equals(GeneType.Glu)){
					compareHeaders = compare(ConstantValue.GLU_EST_HEADER, uploadedEstimatesFile);
				}
				else if(comboBoxGeneType.getValue().equals(GeneType.GluPin)) compareHeaders = compare(ConstantValue.GLUPIN_EST_HEADER, uploadedEstimatesFile);
				else compareHeaders = compare(ConstantValue.GLUPINSPN_EST_HEADER, uploadedEstimatesFile);
				return compareHeaders;
	}
	
	private boolean compare(ArrayList<String> list, File uploadedFile){
		// TODO Auto-generated method stub
				Scanner opnScanner = null;
				int ctr=0;
			
				try {
					opnScanner = new Scanner(uploadedFile);
					String[] fileHeader = null;
					
					try{
						fileHeader = opnScanner.nextLine().split(",");
//						System.out.println("list size="+ Integer.toString(list.size()) + "  fileHeader length:"+Integer.toString(fileHeader.length));
						if(fileHeader.length != list.size()) return false;
						else{
							for(String s: list){
							System.out.print("Compare "+ fileHeader[ctr] +": "+ s +"\n");
							if(!s.equals(fileHeader[ctr])) break;
								ctr++;
							}
						System.out.println("End of for ctr:"+Integer.toString(ctr)+" size:"+Integer.toString(fileHeader.length));
						}
					}catch(NullPointerException npe){
						System.out.println("Empty file.");
					}
					opnScanner.close();
//					System.out.println("End of scanner");
//					System.out.println("ctr="+ Integer.toString(ctr) + "  fileHeader length:"+Integer.toString(fileHeader.length));
					
					if(ctr==fileHeader.length) return true;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("file not found: "+ uploadedFile.getAbsolutePath());
				}

				System.out.println("end try");
				return false;
	}


	public String getUploadedFilePath(String filename) {
		// TODO Auto-generated method stub
		
		File outputFolder = new File(ConstantValue.BASE_PATH_USERDEFINED);
		
		if(!outputFolder.exists()){//check if "userdefined" folder exists
			outputFolder.mkdir();
			System.out.println("Created folder: "+outputFolder.getAbsolutePath());
		}
		
	    String filePath = ConstantValue.BASE_PATH_USERDEFINED+filename;
	    filePath = filePath.replace(".csv",CrossFormHelper.getTimeStamp()+".csv");
		return filePath;
	}


	public void resetOptionGroup(OptionGroup OptionGroup) {
		// TODO Auto-generated method stub
		OptionGroup.setItemCaption(1, "Specified");
		OptionGroup.select(0);

	}
}

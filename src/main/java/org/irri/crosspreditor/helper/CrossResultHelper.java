package org.irri.crosspreditor.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import org.irri.crosspredictor.model.CrossResultTableModel;

import com.vaadin.ui.ListSelect;


public class CrossResultHelper implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private File file;
    private String geneType;
    private int id;

    public CrossResultHelper(String file,String geneType){
	this.file =new File(file);
	this.geneType=geneType;
	this.id=0;
    }

    public ArrayList<CrossResultTableModel> getParentInfoData(ListSelect listParent1,
	    ListSelect listParent2, ListSelect listParent3) {
	ArrayList<CrossResultTableModel> toreturn= new ArrayList<CrossResultTableModel>();

	populateParentInfoData(listParent1,toreturn);
	populateParentInfoData(listParent2,toreturn);
	populateParentInfoData(listParent3,toreturn);

	return toreturn;
    }

    private void populateParentInfoData(ListSelect listParent, ArrayList<CrossResultTableModel> toreturn){
	Object[] listParentItems=listParent.getItemIds().toArray();

	int listParentItemsSize=listParentItems.length;
	int counter=0;
	if(listParentItemsSize > 0){

	    while(counter <  listParentItemsSize){
		CrossResultTableModel tmodel= new CrossResultTableModel();
		String s=(String) listParentItems[counter];
		String[] temp= s.split("-");
		tmodel.setId(this.id);
		tmodel.setName(temp[0]);
		String genotypeTemp= temp[1].substring(0, temp[1].length()-1);
		String genoType=genotypeTemp.replace(",", " ");
		tmodel.setGenotype(genoType);
		getOtherInfo(genoType,tmodel);
		toreturn.add(tmodel);
		counter++;
		this.id++;
	    }
	}

    }

    private void getOtherInfo(String genoType,CrossResultTableModel tmodel){

	Scanner opnScanner = null;
	try {
	    opnScanner = new Scanner(file);

	    while(opnScanner.hasNext()) {

		String newLine = opnScanner.nextLine();
		String[] row= newLine.split(",") ;

		if(row[0].trim().equals(genoType.trim())){
		    tmodel.setRmax(Double.valueOf(row[1]));
		    tmodel.setExt(Double.valueOf(row[2]));

		    if(geneType.equals(ConstantValue.GENETYPE_GLU)){
			tmodel.setWa(Double.valueOf(row[3]));
		    }else if(geneType.equals(ConstantValue.GENETYPE_GLUPIN)){
			tmodel.setDdt(Double.valueOf(row[3]));
			tmodel.setWa(Double.valueOf(row[4]));
		    }else{
			tmodel.setDdt(Double.valueOf(row[3]));
			tmodel.setDa(Double.valueOf(row[4]));
		    }
		    break;
		}
	    }
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    System.out.println("file not found.");
	}finally{
	    opnScanner.close();
	}
    }

    public ArrayList<String> getParent(ListSelect listParent) {

	ArrayList<String> parent = new ArrayList<String>();
	Object[] listParentItems=listParent.getItemIds().toArray();

	int listParentItemsSize=listParentItems.length;
	int counter=0;
	if(listParentItemsSize > 0){

	    while(counter <  listParentItemsSize){
		String s=(String) listParentItems[counter];
		parent.add(s.substring(0,s.length()-1));
		counter++;
	    }
	}
	return parent;
    }

}

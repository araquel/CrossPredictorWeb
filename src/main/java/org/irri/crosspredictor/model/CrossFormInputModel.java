package org.irri.crosspredictor.model;

import java.util.ArrayList;



public class CrossFormInputModel{
    
    String geneType;
    String inBreedOption;
    String crossType;
    String rf;
    ArrayList<String> listParent1;
    ArrayList<String> listParent2;
    ArrayList<String> listParent3;
    ArrayList<CrossResultTableModel> crossResultTableModel;
    
    
    public String getGeneType() {
        return geneType;
    }
    
    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }
    
    public String getInBreedOption() {
        return inBreedOption;
    }
    
    public void setInBreedOption(String inBreedOption) {
        this.inBreedOption = inBreedOption;
    }
    

    
    public String getCrossType() {
        return crossType;
    }

    
    public void setCrossType(String crossType) {
        this.crossType = crossType;
    }

    
    public ArrayList<CrossResultTableModel> getCrossResultTableModel() {
        return crossResultTableModel;
    }

    
    public void setCrossResultTableModel(ArrayList<CrossResultTableModel> crossResultTableModel) {
        this.crossResultTableModel = crossResultTableModel;
    }

    
    public String getRf() {
        return rf;
    }

    
    public void setRf(String rf) {
        this.rf = rf;
    }

    
    public ArrayList<String> getListParent1() {
        return listParent1;
    }

    
    public void setListParent1(ArrayList<String> listParent1) {
        this.listParent1 = listParent1;
    }

    
    public ArrayList<String> getListParent2() {
        return listParent2;
    }

    
    public void setListParent2(ArrayList<String> listParent2) {
        this.listParent2 = listParent2;
    }

    
    public ArrayList<String> getListParent3() {
        return listParent3;
    }

    
    public void setListParent3(ArrayList<String> listParent3) {
        this.listParent3 = listParent3;
    }

    @Override
    public String toString() {
	return "CrossFormInputModel [geneType=" + geneType + ", inBreedOption="
		+ inBreedOption + ", crossType=" + crossType + ", rf=" + rf
		+ ", listParent1=" + listParent1 + ", listParent2="
		+ listParent2 + ", listParent3=" + listParent3
		+ ", crossResultTableModel=" + crossResultTableModel + "]";
    }

    
    

}

package org.irri.crosspreditor.helper;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.irri.crosspredictor.component.CrossFormComponent;
import org.irri.crosspredictor.model.CrossFormInputModel;
import org.irri.crosspredictor.model.CrossResultTableModel;

import com.vaadin.ui.Button.ClickListener;



public class CrossResultHandler implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String otherFieldTblCrosses;
    private String inBreed;
    private String rf;
    private String geneType="Glu";
    private String crossType;
    private String parentFileSpecified;
    private String sourceFileSpecified="";
    private ArrayList<String> listParent1;
    private ArrayList<String> listParent2;
    private ArrayList<String> listParent3;
    private CrossFormInputModel input;
    private CrossFormComponent crossFormComponent;

    private ArrayList<CrossResultTableModel> fxResult=new ArrayList<CrossResultTableModel>();
    private ArrayList<CrossResultTableModel> xResult= new  ArrayList<CrossResultTableModel>();
    private ArrayList<CrossResultTableModel> parentList=new ArrayList<CrossResultTableModel>();
    private ArrayList<CrossResultTableModel> crossResultTable=new ArrayList<CrossResultTableModel>();
    private PopulationSize psComputation;
    private  ConstantValue constantValue;
    private File fileEstimate;


    public  CrossResultHandler(CrossFormInputModel input){
	constantValue= new ConstantValue();
	this.input=input;
	this.geneType=input.getGeneType();
	this.inBreed=input.getInBreedOption();
	this.crossType= input.getCrossType();
	this.listParent1=input.getListParent1();
	this.listParent2=input.getListParent2();
	this.listParent3=input.getListParent3();
	this.rf=input.getRf();
	this.psComputation= new PopulationSize();
	ProcessCross();
    }

    public CrossResultHandler(CrossFormInputModel crossFormInputModel,
	    ClickListener clickListener) {
	// TODO Auto-generated constructor stub
    }

    private  void ProcessCross() {

	crossResultTable=null;
	parentList.clear();

	String otherColumnNames;
	if(geneType.equals(constantValue.GENETYPE_GLU)){

	    if(sourceFileSpecified.length()> 0){
		fileEstimate=new File(ConstantValue.GLU_EST_FILE);
	    }else{
		fileEstimate=new File(ConstantValue.GLU_EST_FILE);
	    }


	}else if(geneType.equals(constantValue.GENETYPE_GLUPIN)){

	    if(sourceFileSpecified.length()> 0){
		fileEstimate=new File(ConstantValue.GLU_EST_FILE);
	    }else{
		fileEstimate=new File(ConstantValue.GLUPIN_EST_FILE);
	    }

	}else{
	    if(sourceFileSpecified.length()> 0){
		fileEstimate=new File(ConstantValue.GLU_EST_FILE);
	    }else{
		fileEstimate=new File(ConstantValue.GLUPINSPN_EST_FILE);
	    }

	}

	if(input.getCrossType().equals(constantValue.TWO_WAY_CROSSTYPE)){
	    crossResultTable=getCrossResultTwoWay(this.inBreed,this.geneType);
	    	    displayResultTest();

	}else{
	    crossResultTable=getCrossResultThreeWay(this.inBreed,this.geneType);
	    	    displayResultTest();
	}

	// create dynamic table
	//		CrossResultEditorInput input = new CrossResultEditorInput(ParentList,CrossResult,columnFieldTblParentInfo,columnFieldTblCrosses,geneType,inBreed,crossType);

    }

    private ArrayList<CrossResultTableModel> getCrossResultTwoWay(String inBreed, String geneType){
	// TODO Auto-generated method stub

	double [] r = new double[5];
	String[]xFre=rf.split(",");
	if(geneType.equals("Glu")){
	    r[0]=Double.valueOf(xFre[1]);
	    r[1]=Double.valueOf(xFre[3]);
	    r[2]=Double.valueOf(xFre[5]);
	}else{
	    r[0]=Double.valueOf(xFre[1]);
	    r[1]=Double.valueOf(xFre[3]);
	    r[2]=Double.valueOf(xFre[5]);
	    r[3]=Double.valueOf(xFre[6]);
	}

	fxResult.clear();
	int cid=1;

	for(int i=0;i<listParent1.size();i++){
	    String parent1=listParent1.get(i);
	    for(int j=0;j<listParent2.size();j++){
		String parent2=listParent2.get(j);

		ArrayList<CrossResultTableModel>  xResult= new CrossComputation(parent1, parent2,1, r,inBreed,geneType,cid).getCrossTwoResult();
		fxResult.addAll(xResult);
		cid++;
	    }
	}


	return setCrossResultTable();
    }

    private ArrayList<CrossResultTableModel> getCrossResultThreeWay(String inBreed2,String geneType) {
	// TODO Auto-generated method stub

	double [] r = new double[5];
	String[]xFre=rf.split(",");
	if(geneType.equals("Glu")){
	    r[0]=Double.valueOf(xFre[1]);
	    r[1]=Double.valueOf(xFre[3]);
	    r[2]=Double.valueOf(xFre[5]);
	}else{
	    r[0]=Double.valueOf(xFre[1]);
	    r[1]=Double.valueOf(xFre[3]);
	    r[2]=Double.valueOf(xFre[5]);
	    r[3]=Double.valueOf(xFre[6]);
	}

	fxResult.clear();
	int cid=1;


	for(int i=0;i<listParent1.size();i++){
	    for(int j=0;j<listParent1.size();j++){
		ArrayList<CrossResultTableModel>  tempCrossTwoResult= new CrossComputation(listParent1.get(i).toString(), listParent2.get(j).toString(),1, r,inBreed,geneType,0).getCrossTwoResult();
		for(int k=0;k<listParent3.size();k++){
		    for(CrossResultTableModel x: tempCrossTwoResult){
			String[] F1=x.getGenotype().split(" ");
			String PF1=x.getName();
			double F1fre=x.getFre();
			ArrayList<CrossResultTableModel>   x3Result=new CrossComputation(F1,PF1, listParent3.get(k).toString(),F1fre, r,inBreed,geneType,cid).getCrossThreeResult();
			fxResult.addAll(x3Result);
		    }
		    cid++;
		}
	    }
	}

	return setCrossResultTable();
    }


    private ArrayList<CrossResultTableModel> setCrossResultTable(){
	ArrayList<CrossResultTableModel> xResult=new ArrayList<CrossResultTableModel>();
	double frePercentage;

	ArrayList<String> s = new ArrayList<String>();

	if(crossType.equals(constantValue.TWO_WAY_CROSSTYPE)){
	    //testOutputResultToCsv(fXResut,"c:\\twoWayTest.csv");
	    for(CrossResultTableModel x:fxResult ){
		CrossResultTableModel p= new CrossResultTableModel();
		frePercentage=x.getFre()*100;
		//System.out.println(x.getCid()+ "\t " +x.getParentName() + "\t "+x.getGenotype() + "\t % "+Pfre+"\tPS95: "+ps95 + "\tPS99: "+ps99);
		p.setCid(x.getCid());
		p.setName(x.getName());
		String fgenotype=x.getGenotype();

		if(geneType.equals("GluPinSpn")){
		    fgenotype=fgenotype.substring(0, fgenotype.length()-3)+fgenotype.substring(fgenotype.length()-2, fgenotype.length());
		}
		p.setGenotype(fgenotype);
		p.setFrePercentage(frePercentage);
		p.setPs95(psComputation.getPs95(frePercentage));
		p.setPs99(psComputation.getPs99(frePercentage));
		//other data
		String est= getEstimateData(fgenotype,fileEstimate);
		xResult.add(populateTableData(p,est));
	    }
	    return xResult;
	}
	else if(crossType.equals(constantValue.THREE_WAY_CROSSTYPE)){

	    for(CrossResultTableModel x:fxResult ){
		String t=null;
		NumberFormat formatter = new DecimalFormat("000000");
		String xcid=(formatter.format((int)x.getCid()));
		t=xcid+","+x.getName() + ","+x.getGenotype() + ":"+x.getFre();
		s.add(t);
	    }

	    Collections.sort(s);

	    String[] _vP=s.iterator().next().split(":");
	    String sC=_vP[0];
	    double xFre=0.0;
	    double aFre=0.0;


	    for(String sS:s){
		CrossResultTableModel tempP= new CrossResultTableModel();
		String[] vP=sS.split(":");
		//System.out.println("ss"+sS);
		if(vP[0].equals(sC)){
		    double fre=Double.valueOf(vP[1]);
		    xFre+=Double.valueOf(vP[1]);
		    aFre=xFre;

		}else{
		    //System.out.println(sC+ "\t "+aFre);
		    frePercentage=aFre*100;

		    tempP.setCid(Integer.valueOf(sC.split(",")[0]));
		    tempP.setName(sC.split(",")[1]);
		    String fgenotype=sC.split(",")[2];

		    if(geneType.equals("GluPinSpn")){
			fgenotype=fgenotype.substring(0, fgenotype.length()-3)+fgenotype.substring(fgenotype.length()-2, fgenotype.length());
		    }

		    tempP.setGenotype(fgenotype);
		    tempP.setFrePercentage(frePercentage);
		    String est= getEstimateData(fgenotype,fileEstimate);
		    xResult.add(populateTableData(tempP,est));
		    xFre=Double.valueOf(vP[1]);
		    aFre=xFre;
		}
		sC=vP[0];

	    }

	    frePercentage=aFre*100;
	    CrossResultTableModel p= new CrossResultTableModel();
	    p.setCid(Integer.valueOf(sC.split(",")[0]));
	    p.setName(sC.split(",")[1]);
	    String fgenotype=sC.split(",")[2];

	    if(geneType.equals("GluPinSpn")){
		fgenotype=fgenotype.substring(0, fgenotype.length()-3)+fgenotype.substring(fgenotype.length()-2, fgenotype.length());
	    }

	    System.out.println(fgenotype);
	    p.setGenotype(fgenotype);
	    p.setFrePercentage(frePercentage);
	    String est= getEstimateData(fgenotype,fileEstimate);
	    xResult.add(populateTableData(p,est));

	    return xResult;
	}
	return xResult;
    }


    private CrossResultTableModel populateTableData(CrossResultTableModel p,String s) {
	// TODO Auto-generated method stub

	if(geneType.equals(constantValue.GENETYPE_GLU)){
	    String[] est = s.split(",");
	    p.setRmax(Double.valueOf(est[1]));
	    p.setExt(Double.valueOf(est[2]));
	    p.setWa(Double.valueOf(est[3]));
	}else if(geneType.equals(constantValue.GENETYPE_GLUPIN)){
	    String[] est = s.split(",");
	    p.setRmax(Double.valueOf(est[1]));
	    p.setExt(Double.valueOf(est[2]));
	    p.setDdt(Double.valueOf(est[3]));
	    p.setWa(Double.valueOf(est[4]));

	}else if(geneType.equals(constantValue.GENETYPE_GLUPINSPN)){
	    String[] est = s.split(",");
	    p.setRmax(Double.valueOf(est[1]));
	    p.setExt(Double.valueOf(est[2]));
	    p.setDdt(Double.valueOf(est[3]));
	    p.setDa(Double.valueOf(est[4]));
	}
	return p;

    }

    private  String getEstimateData(String genoType,File estimateFile){
	Scanner opnScanner = null;
	String toreturn = null;
	try {
	    try {
		opnScanner = new Scanner(estimateFile);
	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    while(opnScanner.hasNext()) {

		String newLine = opnScanner.nextLine();
		String[] row= newLine.split(",") ;

		if(row[0].trim().equals(genoType.trim())){
		    toreturn=newLine;
		    break;
		}
	    }

	}finally{
	    opnScanner.close(); 
	}
	return toreturn;
    }

    private void displayResultTest(){

	for(CrossResultTableModel s:crossResultTable){
	    System.out.println("CID: "+s.getCid());
	    System.out.println("CName: "+s.getName());
	    System.out.println("GenoType: "+s.getGenotype());
	    System.out.println("Percent: "+s.getFrePercentage());
	    System.out.println("PS95: "+psComputation.getPs95(s.getFrePercentage()));
	    System.out.println("PS99: "+psComputation.getPs99(s.getFrePercentage()));
	    System.out.println("RMax: "+s.getRmax());
	    System.out.println("Ext: "+s.getExt());
	    System.out.println("GLU/GLUPIN WA: "+s.getWa());
	    System.out.println("GLUPINSPN DDT: "+s.getDdt());
	    System.out.println("GLUPINSPN/GLUPIN DA: "+s.getDa());

	    System.out.println("******************************");
	}
    }

    
    public ArrayList<CrossResultTableModel> getCrossResultTable() {
        return crossResultTable;
    }
    
    

}

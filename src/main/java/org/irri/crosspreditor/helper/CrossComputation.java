package org.irri.crosspreditor.helper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.irri.crosspredictor.model.CrossResultTableModel;

import com.google.gwt.i18n.server.testing.Parent;




public class CrossComputation {

    private static String  parentName;
    private ArrayList<CrossResultTableModel> crossTwoResult;
    private ArrayList<CrossResultTableModel> crossThreeResult;


    public ArrayList<CrossResultTableModel> getCrossTwoResult() {
	return crossTwoResult;
    }

    public ArrayList<CrossResultTableModel> getCrossThreeResult() {
	return crossThreeResult;
    }


    public CrossComputation(String parent1,String parent2,double F1fre,double[] r,String inBreed,String geneType,int cid){

//	System.out.println("xP1"+parent1);
//	System.out.println("xP2"+parent2);

	ArrayList<String> grpGene=new ArrayList<String>();
	String[] tp1=parent1.split("-");
	String[] tp2=parent2.split("-");
	parentName=tp1[0]+"/"+tp2[0];

	String[] p1=tp1[1].split(",");
	String[] p2=tp2[1].split(",");

	String[] gP1=CustomToChro(p1,geneType);  // genotype P1
	String[] gP2=CustomToChro(p2,geneType); // genotype P2

	grpGene= GroupCrossParent(gP1,gP2,r,inBreed,geneType);
	this.crossTwoResult=GenerateGenotype(parentName,grpGene,geneType,F1fre,cid);
    }


    //ThreeWay Cross


    public CrossComputation(String[] f1, String pF1, String xp3,double F1fre, double[] r,
	    String inBreed, String geneType,int cid) {
	// TODO Auto-generated constructor stub

	ArrayList<String> grpGene=new ArrayList<String>();

	String[] tp3=xp3.split("-");
	String[] p3=tp3[1].split(",");
	parentName=pF1+"//"+tp3[0];

	String[] gF1=CustomToChro(f1,geneType);
	String[] gP3=CustomToChro(p3,geneType);

	grpGene= GroupCrossParent(gF1,gP3,r,inBreed,geneType);
	this.crossThreeResult=GenerateGenotype(parentName,grpGene,geneType,F1fre,cid);
    }



    private  ArrayList<CrossResultTableModel> GenerateGenotype(String parentName,ArrayList<String> grpGene,String geneType,double F1fre,int cid) {
	// TODO Auto-generated method stub
	String[] grp1=grpGene.get(0).split(":");
	String[] grp2=grpGene.get(1).split(":");
	String[] grp3=grpGene.get(2).split(":");

	double fre;
	ArrayList<CrossResultTableModel> xResult= new  ArrayList<CrossResultTableModel>();

	int counter=1;
	if(geneType.equals("Glu")){
	    for(int i=0;i<grp1.length;i++){
		for(int j=0;j<grp2.length;j++){
		    for(int k=0;k<grp3.length;k++){
			CrossResultTableModel tempCR=new CrossResultTableModel();
			String[] g1=grp1[i].split("-");
			String[] g2=grp2[j].split("-");
			String[] g3=grp3[k].split("-");
			//computation
			fre=(Double.valueOf(g1[1])*Double.valueOf(g2[1])*Double.valueOf(g3[1])*F1fre);

			//						fre=roundTwoDecimals((Double.valueOf(g1[1]))*roundTwoDecimals(Double.valueOf(g2[1]))*roundTwoDecimals(Double.valueOf(g3[1])))*roundTwoDecimals(F1fre);
			String genotype=ChroToCustom(g1[0]+","+g2[0]+","+g3[0],geneType);

			//CrossResultModel Set Data
			tempCR.setCid(cid);
			tempCR.setName(parentName);
			tempCR.setGenotype(genotype);
			tempCR.setFre(fre);

			xResult.add(tempCR);
			counter++;
		    }
		}
	    }
	}

	if(geneType.equals("GluPin")|| geneType.equals("GluPinSpn")){
	    String[] grp4=grpGene.get(3).split(":");
	    for(int i=0;i<grp1.length;i++){
		for(int j=0;j<grp2.length;j++){
		    for(int k=0;k<grp3.length;k++){
			for(int l=0;l<grp4.length;l++){
			    CrossResultTableModel tempCR=new CrossResultTableModel();
			    String[] g1=grp1[i].split("-");
			    String[] g2=grp2[j].split("-");
			    String[] g3=grp3[k].split("-");
			    String[] g4=grp4[l].split("-");
			    fre=(Double.valueOf(g1[1])*Double.valueOf(g2[1])*Double.valueOf(g3[1])*Double.valueOf(g4[1])*F1fre);
			    String genotype=ChroToCustom(g1[0]+","+g2[0]+","+g3[0]+","+g4[0],geneType);
			    tempCR.setCid(cid);
			    tempCR.setName(this.parentName);
			    tempCR.setGenotype(genotype);
			    tempCR.setFre(fre);
			    xResult.add(tempCR);
			    //System.out.println(counter+" Genotype " +genotype + " %Fre:"+Double.toString(fre) + " PS95: "+ps95 + " PS99: "+ps99);
			    counter++;
			}
		    }
		}
	    }
	}
	return xResult;
    }


    private static ArrayList<String> GroupCrossParent(String[] Cp1,String[] Cp2,double[] r,String inbreed,String genetype){

	ArrayList<String> GrpGene= new ArrayList<String>();
	String g1,g2,g3,g4;
	String tmp=null;
	int geneGrpCount=0;
	int x=0,y=1;
	String d=":";
	double R0,R1,R2,R3;
	double fre;

	if(genetype.equals("GluPinSpn")){
	    geneGrpCount=4;
	}else{
	    geneGrpCount=3;
	}

	if(inbreed.equals("RIL")){
	    for(int i=0;i<geneGrpCount;i++){
		fre=(1-r[i])/2;
		g1=Cp1[x]+","+Cp1[y]+"-"+Double.toString(fre);
		fre=r[i]/2;
		g2=Cp1[x]+","+Cp2[y]+"-"+Double.toString(fre);
		fre=r[i]/2;
		g3=Cp2[x]+","+Cp1[y]+"-"+Double.toString(fre);
		fre=(1-r[i])/2;
		g4=Cp2[x]+","+Cp2[y]+"-"+Double.toString(fre);
		tmp=g1+d+g2+d+g3+d+g4;
		//System.out.println("Gamete "+removeDuplicate(tmp));
		GrpGene.add(removeDuplicate(tmp));
		x=x+2;y=y+2;
	    }


	}else if(inbreed.equals("DH")){

	    for(int i=0;i<geneGrpCount;i++){
		R0=2*r[i]/(1+2*r[i]);
		fre=(1-R0)/2;
		g1=Cp1[x]+","+Cp1[y]+"-"+Double.toString(fre);

		R1=2*r[i]/(1+2*r[i]);
		fre=R1/2;
		g2=Cp1[x]+","+Cp2[y]+"-"+Double.toString(fre);

		R2=2*r[i]/(1+2*r[i]);
		fre=R2/2;
		g3=Cp2[x]+","+Cp1[y]+"-"+Double.toString(fre);

		R3=2*r[i]/(1+2*r[i]);
		fre=(1-R3)/2;
		g4=Cp2[x]+","+Cp2[y]+"-"+Double.toString(fre);

		tmp=g1+d+g2+d+g3+d+g4;
		//System.out.println("Gamete "+removeDuplicate(tmp));
		GrpGene.add(removeDuplicate(tmp));
		x=x+2;y=y+2;
	    }
	}

	if(genetype.equals("GluPin")){
	    if(Cp1[6].equals(Cp2[6])){
		tmp=Cp1[6]+"-1";
		GrpGene.add(tmp);
	    }else{
		tmp=Cp1[6]+"-0.5"+":"+Cp2[6]+"-0.5";
		GrpGene.add(tmp);
	    }
	}

	return GrpGene;

    }

    private static String removeDuplicate(String tmp){

	String[] tmpGene=tmp.split(":");
	ArrayList<String> gamete = new ArrayList<String>();

	for(int i=0;i<tmpGene.length;i++){
	    gamete.add(tmpGene[i]);
	}
	Collections.sort(gamete);
	String first="";
	String[] current=null;
	String gameteFourCrossResult="";
	String gameteTwoCrossResult="";
	String gameteOneCrossResult="";
	double twoGameteFre=.5;
	double oneGameteFre=1;


	Iterator<String> gameteItem = gamete.iterator();
	while (gameteItem.hasNext()) {
	    current = gameteItem.next().split("-");

	    if(!first.equals(current[0].trim())){
		gameteFourCrossResult+=current[0]+"-"+current[1]+":";
	    }
	    first=current[0].trim();
	}
	//line a,b-0.225:d,b-0.225:

	String[] gameteResult = gameteFourCrossResult.split(":");

	if(gameteResult.length==2){
	    for(int i=0;i<gameteResult.length;i++){
		String[] tmpGamete=gameteResult[i].split("-");
		gameteTwoCrossResult+=tmpGamete[0]+"-"+Double.toString(twoGameteFre)+":";
	    }
	    return gameteTwoCrossResult;
	}else if(gameteResult.length==1){
	    String[] tmpGamete=gameteResult[0].split("-");
	    gameteOneCrossResult+=tmpGamete[0]+"-"+Double.toString(oneGameteFre)+":";
	    return gameteOneCrossResult;
	}

	return gameteFourCrossResult;
    }

    private static  String[] CustomToChro(String[] g,String geneType){

	if(geneType.equals("Glu")){
	    String[] px={g[0],g[3],g[1],g[4],g[2],g[5]};
	    return px;
	}else if(geneType.equals("GluPin")){
	    String[] px={g[0],g[3],g[1],g[4],g[2],g[5],g[6]};
	    return px;
	}else if(geneType.equals("GluPinSpn")){
	    String[] px={g[0],g[3],g[1],g[4],g[2],g[5],g[6],g[7]};
	    return px;
	}
	return null;
    }

    private static  String ChroToCustom(String g,String geneType){

	if(geneType.equals("Glu")){
	    String[] gen=g.split(",");
	    String[] p={gen[0],gen[2],gen[4],gen[1],gen[3],gen[5]};
	    return charArrayToString(p);
	}else if(geneType.equals("GluPin")){
	    String[] gen=g.split(",");
	    String[] p={gen[0],gen[2],gen[4],gen[1],gen[3],gen[5],gen[6]};
	    return charArrayToString(p);
	}else if(geneType.equals("GluPinSpn")){
	    String[] gen=g.split(",");
	    String[] p={gen[0],gen[2],gen[4],gen[1],gen[3],gen[5],gen[6],gen[7]};
	    return charArrayToString(p);
	}
	return null;
    }

    public static String charArrayToString(String[] c)
    {
	String cS = "";
	for(int b=0; b<c.length; b++)
	{
	    cS = cS + c[b]+" " ;
	}
	return cS;
    } 

}

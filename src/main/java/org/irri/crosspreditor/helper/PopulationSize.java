package org.irri.crosspreditor.helper;

public class PopulationSize {
	int ps95;
	int ps99;
	
	
	public static void main(String[] args){
		double fre;
		fre=1.00;
		int ps=new PopulationSize().getPs95(fre);
		int ps99=new PopulationSize().getPs99(fre);
		System.out.println(ps + " "+ps99);
	}
	
	public int getPs95( double fre) {
		int ps95=(int) Math.ceil(Math.log(1-0.95)/Math.log(1-fre/100));
		if(ps95==0){
			return 1;
		}else{
			return ps95;
		}
		
		
	}
	
	public int getPs99(double fre) {
		int ps99= (int) Math.ceil(Math.log(1-0.99)/Math.log(1-fre/100));
		if(ps99==0){
			return 1;
		}else{
			return ps99;
		}
	}


}

package org.irri.crosspredictor.model;


public class CrossResultTableModel{
    
    int cid;
    int id;
    String name;
    String genotype;
    private double rmax;
    private double ext;
    private double wa;
    private double da;
    private double ddt;
    private double ps95;
    private double ps99;
    private double fre;
    private double frePercentage;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGenotype() {
        return genotype;
    }
    
    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    
    public double getRmax() {
        return rmax;
    }

    
    public void setRmax(double rmax) {
        this.rmax = rmax;
    }

    
    public double getExt() {
        return ext;
    }

    
    public void setExt(double ext) {
        this.ext = ext;
    }

    
    public double getWa() {
        return wa;
    }

    
    public void setWa(double wa) {
        this.wa = wa;
    }

    
    public double getDa() {
        return da;
    }

    
    public void setDa(double da) {
        this.da = da;
    }

    
    public double getDdt() {
        return ddt;
    }

    
    public void setDdt(double ddt) {
        this.ddt = ddt;
    }

    
    public double getPs95() {
        return ps95;
    }

    
    public void setPs95(double ps95) {
        this.ps95 = ps95;
    }

    
    public double getPs99() {
        return ps99;
    }

    
    public void setPs99(double ps99) {
        this.ps99 = ps99;
    }

    
    public double getFre() {
        return fre;
    }

    
    public void setFre(double fre) {
        this.fre = fre;
    }

    
    public double getFrePercentage() {
        return frePercentage;
    }

    
    public void setFrePercentage(double frePercentage) {
        this.frePercentage = frePercentage;
    }

    
    public int getCid() {
        return cid;
    }

    
    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
	return "CrossResultTableModel [cid=" + cid + ", id=" + id + ", name="
		+ name + ", genotype=" + genotype + ", rmax=" + rmax + ", ext="
		+ ext + ", wa=" + wa + ", da=" + da + ", ddt=" + ddt
		+ ", ps95=" + ps95 + ", ps99=" + ps99 + ", fre=" + fre
		+ ", frePercentage=" + frePercentage + "]";
    }

    
    
    

}

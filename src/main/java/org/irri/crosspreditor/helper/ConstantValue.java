package org.irri.crosspreditor.helper;

import java.util.ArrayList;
import java.util.Arrays;

import com.vaadin.server.VaadinService;


public class ConstantValue{
    
    private static final String BSLASH = "\\";
    private static final String FSLASH = "/";
    public static final String BASE_PATH=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath().replace(BSLASH, FSLASH)+"/WEB-INF/files/";
    public static final String BASE_PATH_INPUT=BASE_PATH+"/input/";
    public static final String BASE_PATH_USERDEFINED=BASE_PATH+"/userdefined/";
    
    public static final String GLU_EST_FILE= BASE_PATH_INPUT+"GluEst.csv";
    public static final String GLUPIN_EST_FILE= BASE_PATH_INPUT+"GluPinEst.csv";
    public static final String GLUPINSPN_EST_FILE= BASE_PATH_INPUT+"GluPinSpnEst.csv";

    public static final ArrayList<String> GLU_LIST_HEADER=  new ArrayList<String>(Arrays.asList(
			"Name", "A1", "B1", "D1", "A3", "B3", "D3"));
    public static final ArrayList<String> GLUPIN_LIST_HEADER= new ArrayList<String>(
			Arrays.asList("Name", "A1", "B1", "D1", "A3", "B3", "D3", "Pin"));
    public static final ArrayList<String> GLUPINSPN_LIST_HEADER = new ArrayList<String>(
			Arrays.asList("Name", "A1", "B1", "D1", "A3", "B3", "D3", "Pin",
					"Spn"));
        
    public static final ArrayList<String> GLU_EST_HEADER=  new ArrayList<String>(Arrays.asList(
			"Genotype", "Rmax", "Ext", "WA"));
    public static final ArrayList<String> GLUPIN_EST_HEADER= new ArrayList<String>(Arrays.asList(
			"Genotype", "Rmax", "Ext", "DDT", "WA"));
    public static final ArrayList<String> GLUPINSPN_EST_HEADER= new ArrayList<String>(
			Arrays.asList("Genotype", "Rmax", "Ext", "DDT", "DA"));

    public static final String GENETYPE_GLU="Glu";
    public static final String GENETYPE_GLUPIN="GluPin";
    public static final String GENETYPE_GLUPINSPN="GluPinSpn";
    public static final String TWO_WAY_CROSSTYPE="P1/P2";
    public static final String THREE_WAY_CROSSTYPE="P1/P2/P3";
    public static final String INBREEDING_RIL="RIL";
    public static final String INBREEDING_DH="DH";

    

}

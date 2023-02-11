/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rna_ac_compress_general_probabilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 *
 * @author Juluis
 */
public class ENCODE_GENERIC_PROB {
    String rnaPS;
    String dotBrack;
    BigDecimal[] finalIntv;
    String binaryCode;
    boolean DEBUG = false;
    BigDecimal lnReciprocal;
    
    public ENCODE_GENERIC_PROB (String rnaPS1, String dotBrack1){//initialising variables
        rnaPS=rnaPS1;
        dotBrack = dotBrack1;
                 
        DERIVATION_4_GENERIC_PROB rnaDerivative = new DERIVATION_4_GENERIC_PROB();//creates object to read through the rna primary and secondary structure and start the subdivisions
        
        
        finalIntv= rnaDerivative.getFinalSubInterval(rnaPS, dotBrack);// the final subInterval is obtained
        lnReciprocal=rnaDerivative.getLnOfFinalInterval();
        //System.out.println(lnReciprocal);
        binaryCode= interval2Binary(finalIntv);
        
        //System.out.println("\n-------------ENTERED HERE 22---------\n");
    }  
    
    public String getBinaryCode(){
        return binaryCode;//returns binary code
    }
    
    public int getIntvlLength(){
       double ln2 = Math.log(2);//finds ln2
        
        //divides ln of reciprocal by ln 2
        BigDecimal precision = lnReciprocal.divide(BigDecimal.valueOf(ln2), RoundingMode.FLOOR);
        double precisionAsDouble = precision.doubleValue();
        int precision1 = (int)Math.floor(precisionAsDouble)+2;//2 additional bits is needed to hit the right interval
        return precision1;        
    }
   public int getPrecision( BigDecimal intvl){//this method obtains the digits of precision for the arithmetic encoding
                                        //using the formula log2(1/intervallength)+2
        //BigDecimal reciprocal = BigDecimal.ONE.divide(intvl,RoundingMode.FLOOR);
        
        // BigFunctions bigFunction = new BigFunctions();//creates object to use big functions
        
        
       // int scale1 =5;  
       
        
        //************FOR DEBUGGING************
        if(DEBUG){
            System.out.println("\n--------------VALUE OF LN_RECIPROCAL IS-------------");
            System.out.print(lnReciprocal);
            System.out.println();
        }
        
       double ln2 = Math.log(2);//finds ln2
        
        //divides ln of reciprocal by ln 2
        BigDecimal precision = lnReciprocal.divide(BigDecimal.valueOf(ln2), RoundingMode.FLOOR);
        //************FOR DEBUGGING************
        if(DEBUG){
            System.out.println("\n--------------VALUE OF PRECISION IS-------------");
            System.out.print(precision);
            System.out.println();
        }
        
        double precisionAsDouble = precision.doubleValue();
        int precision1 = (int)Math.floor(precisionAsDouble)+2;//2 additional bits is needed to hit the right interval
        
        //************FOR DEBUGGING************
        if(DEBUG){
            System.out.println("\n--------------VALUE OF precision IS-------------");
            System.out.print(precision1);
            System.out.println();
        }
                        
        return precision1;
   }
   
    String interval2Binary (BigDecimal[] finalSubIntvl){//method to obtain the binary representation for the final subinterval
         
                     
        BigDecimal intvlLength = finalSubIntvl[1].subtract(finalSubIntvl[0]);//the reciprocal of the length of the interval is obtained
        
        int precision =getPrecision(intvlLength);
        
        //************FOR DEBUGGING************
        if(DEBUG){
            System.out.println("\n ---------------Value of Precision is---------------------");
            System.out.print(precision);
        }  
        
        
        BigDecimal midpoint;//this variable stores the midpoint of the final interval
        
        midpoint =  finalSubIntvl[0].add(finalSubIntvl[1]).divide(BigDecimal.valueOf(2.0));
       
        //************FOR DEBUGGING************
        if(DEBUG){
        System.out.println();
            //display the binary value for lower bound
            System.out.println("\n---------------------------------------------BINARY EQUIVALENT OF LOWER BOUND IS-------------\n");
            System.out.print(decimalToBinary(precision,finalSubIntvl[0]));
            //displays the binary value for the upper bound
            System.out.println("\n---------------------------------------------BINARY EQUIVALENT OF UPPER BOUND IS-------------\n");
            System.out.print(decimalToBinary(precision,finalSubIntvl[1]));
            System.out.println("\n---------------------------------------------MID-POINT VALUE IS-------------\n");
            System.out.print(midpoint);

            System.out.println();
        }
        return decimalToBinary(precision, midpoint);//converts midpoint to binary       
    }
    
    
    String decimalToBinary(int precision, BigDecimal decimal){
        int k=0;
            
        String binValue="0.";//variable to store the binary value 
        while (k<precision)
        {
            //************FOR DEBUGGING************
            if(DEBUG){
                System.out.println("\n-------------ENTERED HERE 19---------\n");
            }
            int wholeNumber;//stores the whole number part during the conversion
            wholeNumber = decimal.multiply(BigDecimal.valueOf(2.0)).intValue();//takes the whole number part
            decimal = decimal.multiply(BigDecimal.valueOf(2.0));//the value of midpoint is doubled
            if (wholeNumber%2==0)//checks if the value is even
            {
                //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 20---------\n");
                }
                binValue=binValue+"0";//appends a zero to the binary value
                
            }
            else 
                 binValue=binValue+"1";//appends a one to the binary value
          
            k++;
        }
        
        return binValue;//returns the binary number representation for the interval
    }
    
}

package AcCompress_StaticFreq_PerRNA_Count_1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 *
 * @author Juluis
 */
public class ENCODE_STATIC_PROB {
    String rnaPS;
    String dotBrack;
    BigDecimal[] finalIntv;
    String binaryCode;
    String binaryCode2;//stores binary code without the elias code for the frequency counts
    boolean DEBUG = false;
    BigDecimal lnReciprocal;
    
    public ENCODE_STATIC_PROB (String rnaPS1, String dotBrack1){//initialising variables
        rnaPS=rnaPS1;
        dotBrack = dotBrack1;
                 
        DERIVATION_4_STATIC_PROB rnaDerivative = new DERIVATION_4_STATIC_PROB(rnaPS,dotBrack);//creates object to read through the rna primary and secondary structure and start the subdivisions
        
        
        finalIntv= rnaDerivative.getFinalSubInterval(rnaPS, dotBrack);// the final subInterval is obtained
        lnReciprocal=rnaDerivative.getLnOfFinalInterval();
        
    } 
    //returns the encoded binary string
    public String getBinaryCode(){
        return binaryCode;//returns binary code
    }
    public int getIntvlLengthWithFrequency(){
       double ln2 = Math.log(2);//finds ln2
        
        //divides ln of reciprocal by ln 2
        BigDecimal precision = lnReciprocal.divide(BigDecimal.valueOf(ln2), RoundingMode.FLOOR);
        double precisionAsDouble = precision.doubleValue();
        int precision1 = (int)Math.floor(precisionAsDouble)+2;//2 additional bits is needed to hit the right interval
        return precision1+encodeCounter().length();        
    }
    
    public int getIntvlLength(){
       double ln2 = Math.log(2);//finds ln2
        
        //divides ln of reciprocal by ln 2
        BigDecimal precision = lnReciprocal.divide(BigDecimal.valueOf(ln2), RoundingMode.FLOOR);
        double precisionAsDouble = precision.doubleValue();
        int precision1 = (int)Math.floor(precisionAsDouble)+2;//2 additional bits is needed to hit the right interval
        return precision1;        
    }
    public String getBinaryCodeWithoutFrequency()
    {   return binaryCode2;     }
    
   String encodeCounter (){//counter is encoded using elias gamma codes
       Production_Rule_Frequency PRF = new Production_Rule_Frequency(rnaPS, dotBrack);
       int[] counterArray= PRF.getCount();//returns the number of counts per nucleotide and secondary bond
       
      // printArray(counterArray);
       String eliasCode ="";
       for (int j=0; j<10; j++){//we encode only [0]L->a [1]L->c [2]L->g [3] L->u 
        //[4] L->aSu [5]L->uSa [6] L->cSg [7]L->gSc [8]L->uSg [9]L-> gSu  and leave  out S->LS and S->e events
           eliasCode=eliasCode+decimalToBinary_elias(counterArray[j]);//converts the counter to elias code
       }
       return eliasCode;
   }
    void printArray(int[] array){
        
        for(int i=0; i< array.length; i++){
            System.out.print(array[i]+" ");//displays the contents of array
        }
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
            
        String binValue="";//variable to store the binary value 
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
    String decimalToBinary_elias(int decimal){
        int flag=-1; 
        String leadingZeros="";
        String binValue="";//variable to store the binary value 
        while (decimal!=0)
        {           
            int remainder = decimal%2;
            decimal = decimal/2;//takes the whole number part
            
            if (remainder==0)//checks if the value is even
            {                
                binValue="0"+binValue;//appends a zero to the binary value                
            }
            else 
                 binValue="1"+binValue;//appends a one to the binary value
            if(flag>=0)
                leadingZeros=leadingZeros+"0";//adds to the leading zeros in the elias encoding
            
            flag++;
        }
        
        return leadingZeros+binValue;//returns the elias endoding for the decimal integer
    }//end of decimalToBinary method
    
}

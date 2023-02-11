package Adaptive_AC_Compressor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author EVA
 */
public class DECODE_ADAPTIVE_PROB {
    //DECODE A BINARY STRING TO RETURN THE RNA STRING AND THE DOT BRACKET SEQUENCE
    String binaryCode;
    String RNAPrimarySequence;
    String DotBracket;
    DECODE_ADAPTIVE_PROBABILITIES RNA_Decode;
    
    boolean DEBUG=false;
    static BigDecimal decValue;
    
        double[] LProbs2;//LProbs2 contains the probabilities of occurence of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
        double[] sumLProbs2;//sumLProbs2 contains the boundaries of the intervals of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
        double[] SProbs2 ;//SProbs2 contains the probabilities of occurence of the events
                                       // S->LS and S->e
        double[] sumSProbs2;//sumLProbs2 contains the boundaries of the intervals of the events
                                        //S->LS and S->e
    
    DECODE_ADAPTIVE_PROB(String binCode){//constructor initialised binCode# 
        binaryCode=binCode;
         RNA_Decode = new DECODE_ADAPTIVE_PROBABILITIES(binaryCode);
         
         this.setProbabilities();
         //RNA_Decode.printProbabilities();
    }
    
    void setProbabilities(){
        LProbs2=RNA_Decode.getLProbs();//LProbs1 contains the probabilities of occurence of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
         sumLProbs2=RNA_Decode.getSumLProbs();//sumLProbs1 contains the boundaries of the intervals of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
         SProbs2 = RNA_Decode.getSProbs();//SProbs1 contains the probabilities of occurence of the events
                                       // S->LS and S->e
         sumSProbs2=RNA_Decode.getSumSProbs();//sumLProbs1 contains the boundaries of the intervals of the events
                                        //S->LS and S->e
        
    }
    
    void setCountAndProbabilities(int eventIndex){
        RNA_Decode.setCounter(eventIndex);
        RNA_Decode.setProbabilities();
        this.setProbabilities(); //sets the probabilities in this class
        //RNA_Decode.printProbabilities();
        //RNA_Decode.printCount();
    }
    //this method decodes the binary to RNA   
    String[] decode(){
        String RNAPS, DBRAC;
        String[] COMPLETERNA={"",""} ;
        RNAPS=DBRAC="";
        int counter =0;
        //int digitsOfPrecision = binaryCode.length();
        
        decValue = binaryToDecimal(binaryCode);//converts the binary string to a big decimal
       
        BigDecimal roundedDecimal;//variable is rounded to reduce the processing time of big decimal
        
        //System.out.println(decValue);
        //System.out.println("---------------------------------------ENTERED HERE!!!!!!!------------------");
        //RNA_Decode.printProbabilities();
        //RNA_Decode.printCount();
        double decimalFloat;//stores a truncated version of decValue to quicken the speed of comparison
        String Nucleotide;         //stores the letters of the nucleotide                                       //block of codes
        roundedDecimal =decValue.setScale(9, BigDecimal.ROUND_UP);//stores and rounds up decimalValue to 9 decimal places
        decimalFloat= roundedDecimal.floatValue();//converts the rounded value to float, this will help to make comparison easier in the next
        //creates object to obtain the probabilities
        //decoding is limited by the number of digits of precision and the counter
        while(decimalFloat!=0)
        {
       
          //  double decimalFloatRounded = java.lang.Math.round(decimalFloat*100);
            if (counter>RNAPS.length() && counter!=0 /*|| decimalFloatRounded==99*/)
                    break;
            
            //************FOR DEBUGGING************
            if(DEBUG){                
                System.out.print(decValue);
                System.out.println();
            }
            //This if block checks for  the events in the rule S->LS|e
            if(decimalFloat<sumSProbs2[1])//S->LS event
            {                
               
               decValue= decValue.divide(BigDecimal.valueOf(SProbs2[0]),RoundingMode.UP);//rescale to value between 0 and 1
              this.setCountAndProbabilities(10);//increments the count for LS event and probabilities  the e 
               //System.out.println(decValue);
               roundedDecimal =decValue.setScale(9, BigDecimal.ROUND_UP);//stores and rounds up decimalValue to 9 decimal places
               decimalFloat= roundedDecimal.floatValue();//converts the rounded value to float, this will help to make comparison easier in the next
               //System.out.printf("VALUE OF DECIMAL FLOAT AFTER SCALING IS %f \n", decimalFloat); //[[[for debugging]]]
               Nucleotide = getRNA_DBrac(decimalFloat);//gets the decoded RNA char(s) 
               //System.out.printf("VALUE OF Nucleotide IS %s\n\n ", Nucleotide);
               
                if(Nucleotide.length()<2){//if we have  single rna char
                   // System.out.println("Entered here 30!!!!!!!!!!!!"); [[[for debugging]]]
                    if (RNAPS.length()==counter)
                    {
                        //************FOR DEBUGGING************
                         if(DEBUG){
                            System.out.println("Entered here 31!!!!!!!!!!!!");
                            System.out.printf("value of counter %d value of RNAPS\n ", counter);
                            System.out.println(RNAPS);
                            System.out.println(DBRAC);
                            System.out.println();  
                         }
                        
                         RNAPS = RNAPS+Nucleotide;//concatenates the new character
                         DBRAC = DBRAC+".";//concatenates the new string
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.printf("\nValue of nucleotide is %s \n", Nucleotide);
                            System.out.printf("value of RNAPS after concatenation\n");
                            System.out.println(RNAPS);
                            System.out.println(DBRAC);
                            System.out.println();
                        }
                    }
                    else{
                        //************FOR DEBUGGING************
                         if(DEBUG){
                            System.out.println("Entered here 32!!!!!!!!!!!!");
                            System.out.printf("value of counter %d value of RNAPS ", counter); 
                            System.out.println(RNAPS);
                            System.out.println(DBRAC);
                            System.out.println(); 
                         }
                        
                        if(counter==1){
                             //System.out.printf("\nValue of nucleotide is %s \n", Nucleotide);[[[for debugging]]]*/
                             RNAPS = RNAPS.charAt(0)+Nucleotide+ RNAPS.substring(counter);//concatenates the new string
                             DBRAC = DBRAC.charAt(0)+"."+ DBRAC.substring(counter);//concatenates the new string
                        }
                        
                        else
                        {  
                            //************FOR DEBUGGING************
                            if(DEBUG){
                                System.out.println("Entered here 322!!!!!!!!!!!!");
                                System.out.printf("value of counter %d value of RNAPS ", counter);
                                System.out.println();
                                System.out.println(RNAPS);
                                System.out.println(DBRAC);
                                System.out.printf("\nValue of nucleotide is %s \n", Nucleotide);
                            }
                             RNAPS = RNAPS.substring(0, counter)+Nucleotide+ RNAPS.substring(counter);//concatenates the new string
                             DBRAC = DBRAC.substring(0, counter)+"."+ DBRAC.substring(counter);//concatenates the new string
                             
                             //************FOR DEBUGGING************
                             if(DEBUG){
                                System.out.printf("value of RNAPS after concatenation\n");
                                System.out.println(RNAPS);
                                System.out.println(DBRAC);
                                System.out.println();
                            }
                        }
                    }
                    
                }
                else
                {
                    if (RNAPS.length()==counter)
                    {
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println("Entered here 33!!!!!!!!!!!!");
                            System.out.printf("value of counter %d value of RNAPS ", counter);

                            System.out.println();
                            System.out.printf("\nValue of nucleotide is %s \n", Nucleotide);
                         
                        }
                         RNAPS = RNAPS+Nucleotide;//concatenates the new string
                         DBRAC = DBRAC+"()";//concatenates the new string
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println(RNAPS);
                            System.out.println(DBRAC);
                            System.out.println();
                        }
                    }
                    else
                    {
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println("Entered here 35!!!!!!!!!!!!");
                            System.out.printf("value of counter %d value of RNAPS ", counter);
                            System.out.println(RNAPS);
                            System.out.println(DBRAC);
                            System.out.println();
                        }
                        
                        if(counter==1){
                             //System.out.printf("\nValue of nucleotide is %s \n", Nucleotide);
                             RNAPS = RNAPS.charAt(0)+Nucleotide+ RNAPS.substring(counter);//concatenates the new string
                             DBRAC = DBRAC.charAt(0)+"()"+ DBRAC.substring(counter);//concatenates the new string
                        }
                        
                        else
                        {
                            //System.out.printf("\nValue of nucleotide is %s \n", Nucleotide);
                            RNAPS = RNAPS.substring(0, counter)+Nucleotide+ RNAPS.substring(counter);//concatenates the new string
                            DBRAC = DBRAC.substring(0, counter)+"()"+ DBRAC.substring(counter);//concatenates the new string
                        }
                        //************FOR DEBUGGING************
                            if(DEBUG){
                                System.out.printf("VALUE OF  RNAPS AFTER CONCATENATION\n");
                                System.out.println(RNAPS);
                                System.out.println(DBRAC);
                                System.out.println(); 
                            }

                    }
                   
                }
                
            }
            else//S->e event
            {   /*
                System.out.println("--------Entered here 36 i.e. S->e event !!!!!!!!!!!!");
                System.out.printf("value of counter %d value of RNAPS ", counter);
                System.out.println(RNAPS);
                System.out.println(DBRAC);
                System.out.println(); [[[for debugging]]]*/
                        
                
                decValue=decValue.subtract(BigDecimal.valueOf(sumSProbs2[1]));
                decValue= decValue.divide(BigDecimal.valueOf(SProbs2[1]),RoundingMode.UP);//rescale to value between 0 and 1
                this.setCountAndProbabilities(11);//increments the count for LS event and probabilities  the e
               roundedDecimal =decValue.setScale(9, BigDecimal.ROUND_UP);//stores and rounds up decimalValue to 9 decimal places
               decimalFloat= roundedDecimal.floatValue();//converts the rounded value to float, this will help to make comparison easier in the next
                        
              
            }
             roundedDecimal =decValue.setScale(9, BigDecimal.ROUND_UP);//stores and rounds up decimalValue to 9 decimal places
             decimalFloat = roundedDecimal.floatValue();//converts the rounded value to float, this will help to make comparison easier in the next
             
          
               ++ counter;
               //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.printf("\nAfter incrementing counter, value of counter is %d \n", counter);
                    System.out.printf("\nLength of RNAPS is %d \n", RNAPS.length());
                    System.out.println();
                    System.out.println("DecValue is:");
                    System.out.print(decValue);
                    System.out.println();
                }
                            
        }//end while
        
       COMPLETERNA[0]= RNAPS;
       COMPLETERNA[1]= DBRAC;
       
       return COMPLETERNA;
       
    }
    String getRNA_DBrac(double decFloat){
        String RNA_DBrac;
        //System.out.printf("VALUE OF DECIMAL FLOAT IS %f\n", decFloat);
       //RNA_Decode.printProbabilities(); 
       //RNA_Decode.printCount();
        //This if block checks for  the events in the rule L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
        if(decFloat<sumLProbs2[5]){//this block of code is desgined to determine the subdivisions for decoding
           if(decFloat<sumLProbs2[2]){//values between 0.0 and sumLProbs2[2](excluded)
                    if(decFloat<sumLProbs2[1]){//values between 0.0 and sumLProbs2[1](excluded)
                        
                        //System.out.println("Entered here 37!!!!!!!!!!!!for A!!!!");//[[[for debugging]]]
                        decValue= decValue.divide(BigDecimal.valueOf(LProbs2[0]),RoundingMode.UP);//rescale to value between 0 and 1
                        RNA_DBrac="A";
                        this.setCountAndProbabilities(0);//increments the count for L->a  event and probabilities  the e
                        
                        
                        /*System.out.printf("\n\n---------------------VALUE OF LPROBS2[0] IS %f\n\n", LProbs2[0]);[[[for debugging]]]*/
                    }
                    else 
                    {
                        
                        decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[1]));
                        decValue= decValue.divide(BigDecimal.valueOf(LProbs2[1]),RoundingMode.UP);//rescale to value between 0 and 1
                       // System.out.println("Entered here 38!!!!!!!!!!!! for C!!!!");//[[[for debugging]]]
                        RNA_DBrac="C";
                        this.setCountAndProbabilities(1);//increments the count for L->c event and probabilities  the 
                       /* System.out.printf("\n\n---------------------VALUE OF LPROBS2[1] IS %f sumLProbs2[1] %f\n\n",LProbs2[1], sumLProbs2[1]);[[[for debugging]]]*/
                        
                    }//end else for values between 0.1 and 0.2(excluded)
                }
                else //values between sumLProbs2[2] and sumLProbs2[3](excluded)
                {
                 if(decFloat<sumLProbs2[3]){//values between sumLProbs2[2] AND sumLProbs2[3](excluded)
                        
                        
                        decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[2]));
                        decValue = decValue.divide(BigDecimal.valueOf(LProbs2[2]),RoundingMode.UP);//rescale to value between 0 and 1
                        RNA_DBrac="G";
                        this.setCountAndProbabilities(2);//increments the count for L->g event and probabilities  the e
                        
                        //System.out.println("Entered here 39!!!!!!!!!!!! for G!!!!");//[[[for debugging]]]
                        
                       /* System.out.printf("\n\n---------------------VALUE OF LPROBS2[2] IS %f sumLProbs2[2] %f\n\n",LProbs2[2], sumLProbs2[2]);[[[for debugging]]]*/
                 }      
                 else{
                        if(decFloat<sumLProbs2[4]){//values between sumLProbs2[3] and sumLProbs2[4](excluded)
                        
                        
                        decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[3]));
                        decValue = decValue.divide(BigDecimal.valueOf(LProbs2[3]),RoundingMode.UP);//rescale to value between 0 and 1
                        RNA_DBrac="U";
                        this.setCountAndProbabilities(3);//increments the count for L->u event and probabilities  the e 
                       // System.out.println("Entered here 40!!!!!!!!!!!! for U!!!");//[[[for debugging]]]
                        
                        /*System.out.printf("\n\n---------------------VALUE OF LPROBS2[3] IS %f sumLProbs2[3] %f\n\n",LProbs2[3], sumLProbs2[3]);[[[for debugging]]]*/
                        }
                        else
                        {
                            
                            decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[4]));
                            decValue= decValue.divide(BigDecimal.valueOf(LProbs2[4]),RoundingMode.UP);//rescale to value between 0 and 1
                            this.setCountAndProbabilities(4);//increments the count for L->aSu event and probabilities  the e
                            RNA_DBrac="AU";
                            
                            //System.out.println("Entered here 41!!!!!!!!!!!! for AU");//[[[for debugging]]]
                            /*System.out.printf("\n\n---------------------VALUE OF LPROBS2[4] IS %f sumLProbs2[1] %f\n\n",LProbs2[4], sumLProbs2[4]);[[[for debugging]]]*/
                        }//end else for values from sumLProbs2[4] AND sumLProbs2[5](excluded)   
                      }//end else for values from sumLProbs2[3] AND sumLProbs2[5](excluded)   
                
                }//end else for values from sumLProbs2[2] AND sumLProbs2[5](excluded)            
        }
        else 
        {
            if(decFloat<sumLProbs2[7])
            {
                if(decFloat<sumLProbs2[6]){//values between sumLProbs2[5] AND sumLProbs2[6](excluded)
                            
                            
                            decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[5]));
                            decValue= decValue.divide(BigDecimal.valueOf(LProbs2[5]),RoundingMode.UP);//rescale to value between 0 and 1
                            RNA_DBrac="UA";
                            this.setCountAndProbabilities(5);//increments the count for L->uSa event and probabilities  the e
                            //System.out.println("Entered here 42!!!!!!!!!!!! for UA  ");
                            
                            /*[[[for debugging]]]*/
                }
                else{//end else for values from sumLProbs2[6] AND sumLProbs2[7](excluded)
                            
                            
                            decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[6]));
                            decValue= decValue.divide(BigDecimal.valueOf(LProbs2[6]),RoundingMode.UP);//rescale to value between 0 and 1
                            RNA_DBrac="CG";
                            this.setCountAndProbabilities(6);//increments the count for L->cSg event and probabilities  the e
                            //System.out.println("Entered here 43!!!!!!!!!!!! FOR CG   ");//[[[for debugging]]]
                            
                            /*System.out.printf("\n\n---------------------VALUE OF LPROBS2[6] IS %f sumLProbs2[6] %f\n\n",LProbs2[6], sumLProbs2[6]);[[[for debugging]]]*/ 
                }//end else for values from sumLProbs2[6] AND sumLProbs2[7](excluded)
            }
            else{   
                if (decFloat<sumLProbs2[8]){//values between sumLProbs2[7] AND sumLProbs2[8](excluded)
                            
                            
                            decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[7]));
                            decValue= decValue.divide(BigDecimal.valueOf(LProbs2[7]),RoundingMode.UP);//rescale to value between 0 and 1
                            RNA_DBrac="GC";
                            this.setCountAndProbabilities(7);//increments the count for LS event and probabilities  the e
                           // System.out.println("Entered here 44!!!!!!!!!!!! FOR GC   ");//[[[for debugging]]]
                            
                            /*System.out.printf("\n\n---------------------VALUE OF LPROBS2[7] IS %f sumLProbs2[1] %f\n\n",LProbs2[7], sumLProbs2[7]);[[[for debugging]]]*/
                }
                else 
                {
                    if(decFloat<sumLProbs2[9])//values between sumLProbs2[8] AND sumLProbs2[9](excluded)
                    {
                            
                            decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[8]));
                            decValue= decValue.divide(BigDecimal.valueOf(LProbs2[8]),RoundingMode.UP);//rescale to value between 0 and 1
                            RNA_DBrac="UG";
                            this.setCountAndProbabilities(8);//increments the count for L->uSg event and probabilities  the e
                           // System.out.println("Entered here 45!!!!!!!!!!!!    FOR UG   ");
                            
                            //System.out.printf("\n\n---------------------VALUE OF LPROBS2[8] IS %f sumLProbs2[1] %f\n\n",LProbs2[8], sumLProbs2[8]);
                    }    
                    
                    else //values between sumLProbs2[9] AND sumLProbs2[10](excluded)
                    {
                            
                            decValue=decValue.subtract(BigDecimal.valueOf(sumLProbs2[9]));
                             decValue= decValue.divide(BigDecimal.valueOf(LProbs2[9]),RoundingMode.UP);//rescale to value between 0 and 1
                            RNA_DBrac="GU";
                           this.setCountAndProbabilities(9);//increments the count for L->gSu event and probabilities  the e
                           // System.out.println("Entered here 46!!!!!!!!!!!! FOR GU     ");
                             
                             //System.out.printf("\n\n---------------------VALUE OF LPROBS2[9] IS %f sumLProbs2[1] %f\n\n",LProbs2[9], sumLProbs2[9]);
                    }  
                    
                }//end else for values greater than sumLProbs2[8] 
                
            }//end else for values greater than sumLProbs2[7] 
        }//end for else for values greater than sumLProbs2[5] 
        return RNA_DBrac;
    }
    //method to convert the binary code back to a decimal value
    BigDecimal binaryToDecimal(String bCode){
        
        BigDecimal decimal= BigDecimal.ZERO;//stores the decimal equivalent of the binary code
        BigDecimal binaryplace=BigDecimal.valueOf(0.5);
         for (int i=0; i<bCode.length();i++)//starts reading the string after the decimal point
         {
             //System.out.print((int)(bCode.charAt(i)));//reads each character of the binary string  and converts to an integer
            // System.out.println();
             if((int)(bCode.charAt(i))==49){//49 is the integer equivalent for character '1'
             decimal=decimal.add(binaryplace);//adds the fractional place value
             }
             binaryplace=binaryplace.divide(BigDecimal.valueOf(2.0));//divides by 2 to get the next binary fraction place value
         }
         //System.out.print(decimal);
        return decimal;//returns the decimal  value
    }
    
}

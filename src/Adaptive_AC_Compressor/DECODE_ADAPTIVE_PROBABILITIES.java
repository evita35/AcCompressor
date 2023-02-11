/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adaptive_AC_Compressor;



/**
 *
 * @author EVA
 */
//this class decodes the probabilities of single nucleotides and secondary bonds from the compressed bits
public class DECODE_ADAPTIVE_PROBABILITIES {
    String compressedBits;
    int[] counterArray;
    double[] LProbs=new double[10];//LProbs contains the probabilities of occurence of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
    double[] sumLProbs= new double[11];//sumLProbs contains the boundaries of the intervals of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
    double[] SProbs=new double[2];//SProbs contains the probabilities of occurence of the events
                                       // S->LS and S->e
    double[] sumSProbs= new double[3];//sumLProbs contains the boundaries of the intervals of the events
                                        //S->LS and S->e
    ADAPTIVE_Production_Rule_Frequency PRF2;
    //int delimeter=0;
    DECODE_ADAPTIVE_PROBABILITIES(String compressedBits){
        this.compressedBits = compressedBits;
        PRF2 = new ADAPTIVE_Production_Rule_Frequency();
        counterArray=PRF2.getCount();
    }
    void setCounter(int index){//increments the counter array by one, for the given index
        counterArray=PRF2.setCount(index);
       // printArray(counterArray);
        //System.out.println();
    }
    public void setProbabilities(){
                
        SProbs[0]= (double)(counterArray[10]/(double)(counterArray[10]+counterArray[11]));//initialising SProbs for the event S->LS
        
        SProbs[1]=(double)(1-SProbs[0]);//initialising SProbs for the event S->e
        
        sumSProbs[0]=0.0;//initialising sumSProbs
        sumSProbs[1]=SProbs[0];
        sumSProbs[2]=1.0; 
        
       
        double Denominator=0.0;//stores the total number of occurences of L->a|c|g|u|aSu|uSa|cSg|gSc|uSg|gSu
        
        for (int i=0; i<10; i++){//sum up total frequencies for L->a|c|g|u|aSu|uSa|cSg|gSc|uSg|gSu
            Denominator = counterArray[i] + Denominator;
        }
        
        //System.out.print("Value of denominator is: "+ Denominator);
        //L probabilities are calculated and stored in LProbs array
        LProbs[0]= (double)counterArray[0]/Denominator; //L->a
        LProbs[1]= (double)counterArray[1]/Denominator; //L->c
        LProbs[2]= (double)counterArray[2]/Denominator; //L->g
        LProbs[3]= (double)counterArray[3]/Denominator; //L->u
        LProbs[4]= (double)counterArray[4]/Denominator; //L->aSu
        LProbs[5]= (double)counterArray[5]/Denominator; //L->uSa
        LProbs[6]= (double)counterArray[6]/Denominator; //L->cSg
        LProbs[7]= (double)counterArray[7]/Denominator; //L->gSc
        LProbs[8]= (double)counterArray[8]/Denominator; //L->uSg
        LProbs[9] =(double)1 -(LProbs[0]+LProbs[1]+LProbs[2]+LProbs[3]+LProbs[4]+LProbs[5]+LProbs[6]+LProbs[7]+LProbs[8]);//L->gSu
                
         //initialising sumSProbs       
         sumLProbs[0]=0.0;
         for (int i=1; i<11; i++){
             sumLProbs[i]=sumLProbs[i-1]+LProbs[i-1];
         }//end for
         /*
         System.out.println();
         System.out.println("LProbs");
         printArray(LProbs);
         System.out.println();
         System.out.println("SProbs");
         printArray(SProbs);
         System.out.println();
         System.out.println("sumSProbs");
         printArray(sumSProbs);
          System.out.println();
         System.out.println("sumLProbs");
         printArray(sumLProbs);*/
    }   //end method initialisingProbabilities
    
    
    void printProbabilities(){
        System.out.println();
        System.out.println("LProbs");
        System.out.println();
        printArray(LProbs);
        System.out.println();
        System.out.println("sumLProbs");
        System.out.println();
        printArray(sumLProbs);
        System.out.println();
        System.out.println("SProbs");
        System.out.println();
        printArray(SProbs);
        System.out.println();
        System.out.println("sumSProbs");
        System.out.println();
        printArray(sumSProbs);
        System.out.println();
    }
    double[] getLProbs(){
        
        double Denominator=0.0;//stores the total number of occurences of L->a|c|g|u|aSu|uSa|cSg|gSc|uSg|gSu
        
        for (int i=0; i<10; i++){//sum up total frequencies for L->a|c|g|u|aSu|uSa|cSg|gSc|uSg|gSu
            Denominator = counterArray[i] + Denominator;
        }
        
       
        LProbs[0]= (double)counterArray[0]/Denominator; //L->a
        LProbs[1]= (double)counterArray[1]/Denominator; //L->c
        LProbs[2]= (double)counterArray[2]/Denominator; //L->g
        LProbs[3]= (double)counterArray[3]/Denominator; //L->u
        LProbs[4]= (double)counterArray[4]/Denominator; //L->aSu
        LProbs[5]= (double)counterArray[5]/Denominator; //L->uSa
        LProbs[6]= (double)counterArray[6]/Denominator; //L->cSg
        LProbs[7]= (double)counterArray[7]/Denominator; //L->gSc
        LProbs[8]= (double)counterArray[8]/Denominator; //L->uSg
        LProbs[9] =1 -(LProbs[0]+LProbs[1]+LProbs[2]+LProbs[3]+LProbs[4]+LProbs[5]+LProbs[6]+LProbs[7]+LProbs[8]);
        
        //printArray(LProbs);
        return LProbs;
        
    }
    
    public void printCount(){
         printArray(counterArray);
     }
    
    void printArray(double[] array){
        
        for(int i=0; i< array.length; i++){
            System.out.print(array[i]+" ");//displays the contents of array
        }
    }
    
        void printArray(int[] array){
        
        for(int i=0; i< array.length; i++){
            System.out.print(array[i]+" ");//displays the contents of array
        }
    }
    double[] getSumLProbs(){//LProbs contains the probabilities of occurence of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
        sumLProbs[0]=0.0;
         for (int i=1; i<11; i++){
             sumLProbs[i]=sumLProbs[i-1]+LProbs[i-1];
         }//end for
         return sumLProbs;
    }
    
    double[] getSProbs(){
         SProbs[0]= (double)(counterArray[10]/(double)(counterArray[10]+counterArray[11]));//initialising SProbs for the event S->LS
        //System.out.printf("\n*************************************************************************************THE VALUE OF SPROBS[0] IS : %f\n", SProbs[0] );
        SProbs[1]=(double)(1-SProbs[0]);//initialising SProbs for the event S->e
        return SProbs;
        
    }
    
    double[] getSumSProbs(){
        
        sumSProbs[0]=0.0;//initialising sumSProbs
        sumSProbs[1]=SProbs[0];
        sumSProbs[2]=1.0;         
        return sumSProbs;
    }
    
    
}

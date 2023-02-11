/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcCompress_StaticFreq_PerRNA_Count_1;


/**
 *
 * @author EVA
 */
//this class decodes the probabilities of single nucleotides and secondary bonds from the compressed bits
public class DECODE_STATIC_PROBABILITIES {
    String compressedBits;
    int[] counterArray={0,0,0,0,0,0,0,0,0,0};
    double[] LProbs=new double[10];//LProbs contains the probabilities of occurence of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
    double[] sumLProbs= new double[11];//sumLProbs contains the boundaries of the intervals of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
    double[] SProbs=new double[2];//SProbs contains the probabilities of occurence of the events
                                       // S->LS and S->e
    double[] sumSProbs= new double[3];//sumLProbs contains the boundaries of the intervals of the events
                                        //S->LS and S->e
    int delimeter=0;
    DECODE_STATIC_PROBABILITIES(String compressedBits){
        this.compressedBits = compressedBits;
    }
    
    double[] getLProbs(){
        int counter=0;//counts the number of leading zeros in the elias code for each count of the occurrences
        //of each nucleotide or secondary bond in the RNA string
        
        
        for(int i=0;i<10;i++){
            while(compressedBits.charAt(delimeter)=='0')//checks for leading zeros in elias code
            {                  
                counter++; delimeter++; }//end of first while loop
            
            //this block converts the elias code to dernary
                while(counter>-1){
                    if(compressedBits.charAt(delimeter)=='1')
                    {                        
                        counterArray[i]= counterArray[i] + (int)Math.pow(2, counter);
                        
                    }//sums of the count
                    
                    counter--;
                    delimeter++;
                }//end of second while block
               
                counter=0;//resets counter
        }//end for loop
        
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
    
    void printArray(double[] array){
        
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
        SProbs[0]= 0.665;//this is the near general probability for the event S->LS
       
        SProbs[1]=0.335;//this is the near general probability the event S->e
        return SProbs;
    }
    double[] getSumSProbs(){
        sumSProbs[0]=0.0;//initialising sumSProbs
        sumSProbs[1]=SProbs[0];
        sumSProbs[2]=1.0; 
        
        return sumSProbs;
    }
    
    int getDelimeter()
    {
        return delimeter;
    }
}

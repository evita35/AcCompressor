package AcCompress_StaticFreq_PerRNA_Count_1;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Eva
 */
public class STATIC_Probabilities {
    
    String PrimarySeq;//stores RNA primary sequency
    String DotBracket;    //stores RNA secondary structure
    static int[] count; //stores the frequency of occurence of all the events in the RNA string
    
     
     double[] SProbs = new double[2];//array stores the probability of the occurence of S->LS and S->e 
     double[] sumSProbs = new double[3];//array sums of the probabilities of Sprobs like a series 
                                        //e.g. sumSProbs[0]=0
                                          //    sumSProbs[1]=sumSProbs[0]+SProbs[1]
                                          //    sumSprobs[2]=sumSProbs[1]+SProbs[2]
     double[] LProbs = new double[11];// this array stores the probabilities of occurrence of the 
                                        //events L->a,L->u,L->c, L->g, L-> aSu, L->uSa, L->cSg, L->gSc,L->uSg,L->gSu
                                         //in the order above
     double[] sumLProbs = new double[11];//this array sums up the probabilities in LProbs like a series. This array helps to subdivide interval [0,1) 
                                            //into subintervals with the boundaries defined by SumLProbs the 
                                            //e.g. 
                                            //e.g. sumLProbs[0]=0
                           
     //e.g. sumLProbs[1]=LProbs[0]
                                            //e.g. sumLProbs[2]=sumLProbs[1]+LProbs[1]
                                            //e.g. sumLProbs[3]=sumLProbs[2]+LProbs[2]
     STATIC_Probabilities(String PrySeq, String DotBrack)
     { 
         PrimarySeq=PrySeq; DotBracket=DotBrack;
         Production_Rule_Frequency PRF = new Production_Rule_Frequency(PrimarySeq, DotBracket);
         //System.out.printf("%s\n %s\n", PrySeq, DotBrack);
         count = PRF.getCount();//initialises the counter array which stores frequency of each event in the RNA string
         //printArray(count);
         initialiseProbabilities();//constructor calls method to initialise probabilities
         //printProbabilities();
     }
     
    public void initialiseProbabilities(){
                
        SProbs[0]= 0.665;//initialising SProbs for the event S->LS
       
        SProbs[1]=0.335;//initialising SProbs for the event S->e
        
        sumSProbs[0]=0.0;//initialising sumSProbs
        sumSProbs[1]=SProbs[0];
        sumSProbs[2]=1.0; 
        
       
        double Denominator=0.0;//stores the total number of occurences of L->a|c|g|u|aSu|uSa|cSg|gSc|uSg|gSu
        
        for (int i=0; i<10; i++){//sum up total frequencies for L->a|c|g|u|aSu|uSa|cSg|gSc|uSg|gSu
            Denominator = count[i] + Denominator;
        }
        
        //System.out.print("Value of denominator is: "+ Denominator);
        //L probabilities are calculated and stored in LProbs array
        LProbs[0]= (double)count[0]/Denominator; //L->a
        LProbs[1]= (double)count[1]/Denominator; //L->c
        LProbs[2]= (double)count[2]/Denominator; //L->g
        LProbs[3]= (double)count[3]/Denominator; //L->u
        LProbs[4]= (double)count[4]/Denominator; //L->aSu
        LProbs[5]= (double)count[5]/Denominator; //L->uSa
        LProbs[6]= (double)count[6]/Denominator; //L->cSg
        LProbs[7]= (double)count[7]/Denominator; //L->gSc
        LProbs[8]= (double)count[8]/Denominator; //L->uSg
        LProbs[9] =1 -(LProbs[0]+LProbs[1]+LProbs[2]+LProbs[3]+LProbs[4]+LProbs[5]+LProbs[6]+LProbs[7]+LProbs[8]);//L->gSu
                
         //initialising sumSProbs       
         sumLProbs[0]=0.0;
         for (int i=1; i<11; i++){
             sumLProbs[i]=sumLProbs[i-1]+LProbs[i-1];
         }//end for
    }   //end method initialisingProbabilities
    
    double[] getLProbs(){//returns LProbs 
        return LProbs;
    }
    
    void printArray(int[] integerArray){//displays the contents of the array
        for(int i=0; i<integerArray.length; i++)
        {
            System.out.print(integerArray[i]+" ");
        }
    }
    
    void printProbabilities(){//prints out the probabilities of occurence of the various nucleotides and RNA bonds
    
         System.out.println("\n---------------------------THE PROBABILITIES ARE:------------");
        for (int i=0; i<11; i++){
             System.out.printf("SumLProbs[%d]:%.6f\tLProbs[%d]:%.6f\n",i, sumLProbs[i],i,LProbs[i]);
         }
    }
    
    
    
    double[] getSumLProbs(){//returns sumLProbs
        return sumLProbs;
    }
    
    double[] getSProbs(){//returns SProbs
        return SProbs;
    }
    
    double[] getSumSProbs(){//returns sumSProbs
        return sumSProbs;
    }
    
}

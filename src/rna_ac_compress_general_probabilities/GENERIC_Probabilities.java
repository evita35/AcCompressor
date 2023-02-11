/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rna_ac_compress_general_probabilities;

/**
 *
 * @author Eva
 */
public class GENERIC_Probabilities {
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
     GENERIC_Probabilities(){
         initialiseProbabilities();//constructor calls method to initialise probabilities
         //printProbabilities();
     }
    public void initialiseProbabilities(){
        SProbs[0]=0.665;//initialising SProbs for the event S->LS
        SProbs[1]=0.335;//initialising SProbs for the event S->e
        
        sumSProbs[0]=0.0;//initialising sumSProbs
        sumSProbs[1]=SProbs[0];
        sumSProbs[2]=1.0; 
        
       
        LProbs[0]=  0.1831000;//0.183075 L->a   note: reduced this probability by 0.000001 because of 0.000002 summation error     
        LProbs[1]=  0.0878875;//0.087875;//L->c   note reduced this probability by 0.000001 because of 0.000002 summation error
        LProbs[2]=  0.1016875;//0.101709;//L->g
        LProbs[3]=  0.1586625;//0.158666;//L->u
        LProbs[4]=  0.0715875;//0.071603;//L->aSu
        LProbs[5]=  0.0943125;//0.094386;//L->uSa
        LProbs[6]=  0.1442000;//0.144020//L->cSg
        LProbs[7]=  0.1137875;//0.113914//L->gSc
        LProbs[8]=  0.0268875;//0.026851//L->uSg
        LProbs[9] = 0.0178875;//0.017901//L->gSu
                
         //initialising sumSProbs       
         sumLProbs[0]=0.0;
         for (int i=1; i<11; i++){
             sumLProbs[i]=sumLProbs[i-1]+LProbs[i-1];
         }//end for
    }   //end method initialisingProbabilities
    
    double[] getLProbs(){//returns LProbs 
        return LProbs;
    }
    void printProbabilities(){
    
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

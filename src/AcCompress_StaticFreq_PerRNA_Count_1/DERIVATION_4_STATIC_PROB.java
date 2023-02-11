package AcCompress_StaticFreq_PerRNA_Count_1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.math.BigDecimal;


/**
 *
 * @author EVA
 */
public class DERIVATION_4_STATIC_PROB {
    /**RNA grammar rules
     * S-> LS|e
     * (assigning each rule a position LS is 1st,  e is 2nd)
     * (assigning each rule an interval length, for the arithmetic encoding the length for
     * LS and e are 0.5 each)
     * L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
     * (aSu is 1st, uSa is 2nd, ...,g is 10th)
     * (the interval length here vary depending on the probabilities of their occurrence)
     * 
     */
    BigDecimal lnOfInterval=BigDecimal.ZERO;
    String PrySeq;
    String DotBrack;
    BigDecimal[] Interval= {BigDecimal.ZERO, BigDecimal.ONE};//LOWER BOUND IS INITIALISED
    double[] LProbs1;
    double[] sumLProbs1;
    double[] SProbs1;
    double[] sumSProbs1;
    
    boolean DEBUG = false; //variable is set to false initially, but set to true for debugging purposes
    
    DERIVATION_4_STATIC_PROB(String Primary, String DBracket){
        PrySeq= Primary;
        DotBrack= DBracket;
        STATIC_Probabilities RNAProb = new  STATIC_Probabilities(PrySeq, DotBrack);//creates o bject of the class
        //System.out.println(PrySeq);
        LProbs1=RNAProb.getLProbs();//LProbs1 contains the probabilities of occurence of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
        sumLProbs1=RNAProb.getSumLProbs();//sumLProbs1 contains the boundaries of the intervals of the events
                                        // L-> aSu | uSa | cSg | gSc | uSg | gSu | a| u| c| g
        SProbs1 = RNAProb.getSProbs();//SProbs1 contains the probabilities of occurence of the events
                                        // S->LS and S->e
        sumSProbs1=RNAProb.getSumSProbs();//sumLProbs1 contains the boundaries of the intervals of the events
                                        //S->LS and S->e
    }   
    
   
    //this method reads the rna primary sequence and dot bracket sequence
    //and determines the derivation. Which is here represented by the Rule Pairs stores in the arrayList
    BigDecimal[] getFinalSubInterval(String rnaPSeq, String rnaDBracket){
       // System.out.println("\n-------------ENTERED HERE 2---------\n");
        int i=0;//initialising index which reads through the rnasequence
      //List of rule pairs
         double[] S_LS ={sumSProbs1[0],SProbs1[0]};//for S->LS event
         double[] S_e= {sumSProbs1[1],SProbs1[1]};//for S->e event
           char nucleotide;
        while (i<=rnaPSeq.length()-1)
        {
            nucleotide = rnaPSeq.charAt(i);//some text files have nucleotides as lower case
            //************FOR DEBUGGING************
            if(DEBUG){
                System.out.println("\n-------------ENTERED HERE 3---------\n");
            }
            //since an LS event always occurs before a . and (
            //we subdivde using S_LS event
            //searching for 
            if(rnaDBracket.charAt(i)=='.'){//single nucleotide
                //since an LS event always occurs before a . and (
                //we include the pair for LS i.e. sumSProbs1[0] and SProbs1[0]
                
                //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 4---------\n");
                }
                switch(nucleotide){
                    case ('A')://if the rna nuclueotide is an A
                    {
                        //System.out.println("\n-------------ENTERED HERE A---------\n");[[[for debugging]]]*/
                        
                        Interval = subDivide(S_LS[0],S_LS[1],Interval); //S->LS event
                        Interval= subDivide(sumLProbs1[0],LProbs1[0],Interval);//L->a event
                        
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println("\n ---------------LOWER BOUND---------------------");
                            System.out.print(Interval[0]);
                            System.out.println("\n ---------------UPPER BOUND---------------------");
                            System.out.print(Interval[1]);
                            System.out.println("SumLProbs and LProbs\n");
                            System.out.print(sumLProbs1[0]);
                            System.out.println();
                            System.out.print(LProbs1[0]);
                        }
                        break;
                    }
                    case ('C')://if the rna nuclueotide is a C
                    {
                       
                        Interval = subDivide(S_LS[0],S_LS[1],Interval); //S->LS event
                        Interval= subDivide(sumLProbs1[1],LProbs1[1],Interval);//L->C event;
                        
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println("\n-------------ENTERED HERE C---------\n");
                            System.out.println("\n ---------------LOWER BOUND---------------------");
                            System.out.print(Interval[0]);
                            System.out.println("\n ---------------UPPER BOUND---------------------");
                            System.out.print(Interval[1]);
                            System.out.println("SumLProbs and LProbs\n");
                            System.out.print(sumLProbs1[1]);
                            System.out.println();
                            System.out.print(LProbs1[1]);
                        }
                        
                        break;
                    }
                    case ('G')://if the rna nuclueotide is a G
                    {
                        
                        Interval = subDivide(S_LS[0],S_LS[1],Interval); //S->LS event
                        Interval= subDivide(sumLProbs1[2],LProbs1[2],Interval);//L->G event
                        
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println("\n-------------ENTERED HERE G---------\n");
                            System.out.println("\n ---------------LOWER BOUND---------------------");
                            System.out.print(Interval[0]);
                            System.out.println("\n ---------------UPPER BOUND---------------------");
                            System.out.print(Interval[1]);
                            System.out.println("SumLProbs and LProbs\n");
                            System.out.print(sumLProbs1[2]);
                            System.out.println();
                            System.out.print(LProbs1[2]);
                        }
                        break;
                    }
                    case ('U')://if the rna nuclueotide is a U
                    {
                        
                        Interval = subDivide(S_LS[0],S_LS[1],Interval); //S->LS event
                        Interval= subDivide(sumLProbs1[3],LProbs1[3],Interval);//L->U event
                        
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println("\n-------------ENTERED HERE U---------\n");
                            System.out.println("\n ---------------LOWER BOUND---------------------");
                            System.out.print(Interval[0]);
                            System.out.println("\n ---------------UPPER BOUND---------------------");
                            System.out.print(Interval[1]);
                            System.out.println("SumLProbs and LProbs\n");
                            System.out.print(sumLProbs1[3]);
                            System.out.println();
                            System.out.print(LProbs1[3]);
                        }
                        break;
                    }
                    default:
                        
                        break;
                }//end switch to search for single nucleotides
                
            }//end if for .
            else
                if (rnaDBracket.charAt(i)=='('){//secondary bond
                    
                    //an LS event always precedes an (
                    // we include the pair for LS i.e. SProb1[0] and sumSProb1[0]
                    // pair[0]=sumSProb1[0]; pair[1]=SProb1[0];
                    Interval = subDivide(S_LS[0],S_LS[1],Interval); //S->LS event
                                       
                    double[] L_S = getRulePair4SecBond (i,rnaPSeq,rnaDBracket);//calls the method which finds the secondary bond either aSu | uSa | cSg | gSc | uSg | gSu 
                    //returns the boundary and probability 
                    
                    Interval = subDivide(L_S[0],L_S[1],Interval);//L->(S)
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println("\n-------------ENTERED HERE 5---------\n");
                            System.out.println("\n ---------------LOWER BOUND---------------------");
                            System.out.print(Interval[0]);
                            System.out.println("\n ---------------UPPER BOUND---------------------");
                            System.out.print(Interval[1]);
                        }
                }
                else
                    if (rnaDBracket.charAt(i)==')'){//and e event always occurs before a )
                                                                       
                        Interval = subDivide(S_e[0],S_e[1],Interval); //adds the subdivision for an e event
                        //************FOR DEBUGGING************
                        if(DEBUG){
                            System.out.println("\n-------------ENTERED HERE 6---------\n");
                            System.out.println("\n ---------------LOWER BOUND---------------------");
                            System.out.print(Interval[0]);
                            System.out.println("\n ---------------UPPER BOUND---------------------");
                            System.out.println(Interval[1]);
                        }
                    }
                       
            i++;
        }
        Interval = subDivide(S_e[0],S_e[1],Interval);//an e event occurs at the end of every rna string 
            //************FOR DEBUGGING************
            if(DEBUG){
               System.out.println("\n-------------ENTERED HERE 6a---------\n");
               System.out.println("\n ---------------LOWER BOUND---------------------");
               System.out.print(Interval[0]);
               System.out.println("\n ---------------UPPER BOUND---------------------");
               System.out.print(Interval[1]);
            }
        return Interval;
    }//end of  method to determine rule
    
    double[] getRulePair4SecBond(int i1, String rnaPSeq1, String rnaDBrack1){
        //************FOR DEBUGGING************
        if(DEBUG){
            System.out.println("\n-------------ENTERED HERE 7---------\n");
        }
        
        int w=i1;
        int a=0;
        double[] pair1= new double[2];
        String secBond;//string variable to store the secondary bond pair
        
        while (i1<rnaPSeq1.length()){
            //************FOR DEBUGGING************
            if(DEBUG){
                 System.out.println("\n-------------ENTERED HERE 8---------\n");
            }
            
            if(rnaDBrack1.charAt(i1)=='('){
                //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 9---------\n");
                }
                 a++;//keeping a count of the open brackets
            }
            else
                if(rnaDBrack1.charAt(i1)==')'){
                    //************FOR DEBUGGING************
                    if(DEBUG){
                        System.out.println("\n-------------ENTERED HERE 10---------\n");
                    }
                      a--;//cancelling each ( with its corresponding )
                }
               
               
               if (a==0){
                   //************FOR DEBUGGING************
                    if(DEBUG){
                            System.out.println("\n-------------ENTERED HERE 11---------\n");
                    }
                   
                   secBond= ""+ Character.toUpperCase(rnaPSeq1.charAt(w)) + Character.toUpperCase(rnaPSeq1.charAt(i1));
                   return getRP4SB(secBond);//gets the rule pair for the secondary bond
               }
               i1++;     
             }//end of while loop
        
        //************FOR DEBUGGING************
        if(DEBUG){
             System.out.println("\n-------------ERROR---------\n");
        }
        pair1[0]=0.0; pair1[1]=0.0;
        return pair1;
        }//end of method to obtain rule pairs for secondary bond
    
    
    double[] getRP4SB(String sBond){//switches the 6 possible secondary bonds and returns the c
        //corresponding rule pair
        double[] pair2= new double[2];
        //************FOR DEBUGGING************
        if(DEBUG){
        
        System.out.println("\n--------------Entered getRP4SB--------------------------\n");
        System.out.print(sBond);
        }
        switch(sBond)
        {
            case ("AU"):
            { 
                
                 pair2[0] = sumLProbs1[4]; pair2[1]= LProbs1[4];//L->aSu event
                 //************FOR DEBUGGING************
                 if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 12 AU---------\n");
                    System.out.print(sumLProbs1[4]);
                    System.out.println();
                    System.out.print(LProbs1[4]);
                 }
                 return pair2;
                
            }
            case ("UA"):
            { 
                
                pair2[0] = sumLProbs1[5]; pair2[1]= LProbs1[5];//L->uSa event
                //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 13 UA---------\n");
                    System.out.print(sumLProbs1[5]);
                    System.out.println();
                    System.out.print(LProbs1[5]);
                }
                return pair2;
                
            }
            case ("CG"):
            { 
                
                pair2[0] = sumLProbs1[6]; pair2[1]= LProbs1[6];//L->cSg event
               //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 14 CG---------\n"); 
                    System.out.print(sumLProbs1[6]);
                    System.out.println();
                    System.out.print(LProbs1[6]);
                }

                return pair2;
            }
            case ("GC"):
            { 
                
                pair2[0] = sumLProbs1[7]; pair2[1]= LProbs1[7];//L->aSu event
                //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 15  GC---------\n");
                    System.out.print(sumLProbs1[7]);
                    System.out.println();
                    System.out.print(LProbs1[7]);
                }
                return pair2;
             
            }
            case ("UG"):
            { 
                
                pair2[0] = sumLProbs1[8]; pair2[1]= LProbs1[8];//L->aSu event
               //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 16  UG---------\n");
                    System.out.print(sumLProbs1[8]);
                    System.out.println();
                    System.out.print(LProbs1[8]);
                }
                return pair2;
                
            }
            case ("GU"):
            { 
                
                pair2[0] = sumLProbs1[9]; pair2[1]= LProbs1[9];//L->aSu event
                //************FOR DEBUGGING************
                if(DEBUG){
                    System.out.println("\n-------------ENTERED HERE 17  GU---------\n");
                    System.out.print(sumLProbs1[9]);
                    System.out.println();
                    System.out.print(LProbs1[9]);
                }
                return pair2;
               
            }
            default:
            { 
                System.out.println("-----------------ERROR: UNUSUAL BOND ENCOUNTERED!!-----------------------------");
                pair2[0]=0.0; pair2[1]=0.0;
                return pair2;
                
            }
        }
    }
   //method to carry out the division and subdivision of [0,1)
    BigDecimal[] subDivide(double Boundary, double Probability, BigDecimal[] oldSubInterval){
       
        //System.out.printf("\nValue of Boundary %.6f   Value of Probability %.6f\n", Boundary, Probability);[[[for debugging]]]*/
        
        lnOfInterval = lnOfInterval.add(BigDecimal.valueOf(Math.log(Probability)));//the ln of the final interval is broken
        //into the sum of the ln of its composite factors
        return oldSubInterval;
   }     
    public BigDecimal getLnOfFinalInterval(){
        return BigDecimal.ZERO.subtract(lnOfInterval);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcCompress_StaticFreq_PerRNA_Count_1;

/**
 *
 * @author Eva
 */
public class Production_Rule_Frequency {// counts the number of occurence of each production rule in a given sequence of RNA strings
    int[] count={1,1,1,1,1,1,1,1,1,1,1,1}; //array count stores the frequency of occurence of each production rule for a 
    String PrimarySequence;
    String DotBracket;
    Production_Rule_Frequency(String PrySeq, String DotBrack)
    {
        PrimarySequence=PrySeq;
        DotBracket = DotBrack;
        //For a given RNA string. 
        // the count of the rules are stored in the array in this order: [0]L->a [1]L->c [2]L->g [3] L->u 
        //[4] L->aSu [5]L->uSa [6] L->cSg [7]L->gSc [8]L->uSg [9]L-> gSu  
        //[10]S->LS [11] S->e
        
    }
    
    int[] getCount(){
        int i=0;//initialising index which reads through the rnasequence

        while (i<=PrimarySequence.length()-1)
        {
            if(DotBracket.charAt(i)=='.'){//single nucleotide
                //since an LS event always occurs before a . and (
                count[10]++;//increment counter for S->LS event
                switch(Character.toUpperCase(PrimarySequence.charAt(i))){
                    case ('A')://if the rna nuclueotide is an A
                    {
                        count[0]++;//increment counter for occurence of single nucleotide A
                       
                        break;
                    }
                    case ('C')://if the rna nuclueotide is a C
                    {                     
                       count[1]++;//increment counter for occurence of single nucleotide C
                        break;
                    }
                    case ('G')://if the rna nuclueotide is a G
                    {                        
                        count[2]++;//increment counter for occurence of single nucleotide G
                        break;
                    }
                    case ('U')://if the rna nuclueotide is a U
                    {
                        count[3]++;//increment counter for occurence of single nucleotide U
                        break;
                    }
                    default:
                        
                        break;
                }//end switch to search for single nucleotides
                
            }//end if for .
            else
                if (DotBracket.charAt(i)=='('){//secondary bond
                    
                    //an LS event always precedes an (
                    count[10]++;     //increment counter for occurence of S->LS event
                    incrementCounter4SecBond (i,PrimarySequence,DotBracket);
                    
                }
                else
                    if (DotBracket.charAt(i)==')'){//an e event always occurs before a )
                        count[11]++;  //increment counter for occurence of S->e event
                    }
            i++;
            
        }
        return count;
    }//end of  method to determine rule
    
    void incrementCounter4SecBond(int i1, String PrimarySequence1, String DotBracket1){
       
        int w=i1;//variables to determine position of corresponding ) close bracket for a particular ( open bracket
        int a=0;
        
        String secBond;//string variable to store the secondary bond pair
        
        while (i1<PrimarySequence1.length()){
            
            if(DotBracket1.charAt(i1)=='('){
                
                 a++;//keeping a count of the open brackets
            }
            else
                if(DotBracket1.charAt(i1)==')'){
                    
                      a--;//cancelling each ( with its corresponding )
                }
               
               
               if (a==0){
                   
                   secBond= ""+ Character.toUpperCase(PrimarySequence1.charAt(w)) + Character.toUpperCase(PrimarySequence1.charAt(i1));
                   
                   incrementCount4SecBond(secBond);//calls method to increment the frequency count for the secondary bond
                   return;
               }
               i1++;     
             }//end of while loop
       
        return;
        }//end of method to obtain rule pairs for secondary bond
    
    
    void incrementCount4SecBond(String sBond){//switches the 6 possible secondary bonds and returns the 
        //corresponding rule pair
       
       
        switch(sBond)
        {
            case ("AU"):
            { 
                //L->aSu event
                count[4]++;
               break;
                
            }
            case ("UA"):
            { 
                
                count[5]++;
                break;
            }
            case ("CG"):
            { 
                count[6]++;
                break;
            }
            case ("GC"):
            { 
                count[7]++;
                break;
            }
            case ("UG"):
            { 
               count[8]++;
               break;
            }
            case ("GU"):
            { 
                count[9]++;
                break;
            }
            default:
            { 
                System.out.println("-----------------ERROR: UNUSUAL BOND ENCOUNTERED!!-----------------------------");
                
                break;
                
            }
        }
        return;
    }
            
}

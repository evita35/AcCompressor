/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adaptive_AC_Compressor;

//import AcCompress_StaticFreq_PerRNA_Count0.*;

/**
 *
 * @author Eva
 */
public class ADAPTIVE_Production_Rule_Frequency {// counts the number of occurence of each production rule in a given sequence of RNA strings
    int[] count={1,1,1,1,1,1,1,1,1,1,1,1}; //array count stores the frequency of occurence of each production rule. INITIIALISED TO 1 TO AVOID DIVISION BY ZERO
    //String PrimarySequence;
   // String DotBracket;
    ADAPTIVE_Production_Rule_Frequency()
    {
        //PrimarySequence=PrySeq;
        //DotBracket = DotBrack;
        //For a given RNA string. 
        // the count of the rules are stored in the array in this order: [0]L->a [1]L->c [2]L->g [3] L->u 
        //[4] L->aSu [5]L->uSa [6] L->cSg [7]L->gSc [8]L->uSg [9]L-> gSu  
        //[10]S->LS [11] S->e
        
    }
    
    int[] setCount(int index){
        switch(index){
        case(0):
        {
            count[0]++;//increment counter for occurence of single nucleotide A
            
            break;
        }
        
        case(1):
        {
            count[1]++;//increment counter for occurence of single nucleotide C
            
            break;
        }
        case(2):
        {
            count[2]++;//increment counter for occurence of single nucleotide G
            
            break;
        }
        case(3):
        {
            count[3]++;//increment counter for occurence of single nucleotide u
            
            break;
        }
        case(4):
        {
            count[4]++;//increment counter for occurence of single nucleotide au
            break;
        }
        case(5):
        {
            count[5]++;//increment counter for occurence of single nucleotide ua
            break;
        }
        case(6):
        {
            count[6]++;//increment counter for occurence of single nucleotide gc
            break;
        }
        case(7):
        {
            count[7]++;//increment counter for occurence of single nucleotide cg
            break;
        }
        case(8):
        {
            count[8]++;//increment counter for occurence of single nucleotide ug
            break;
        }
        case(9):
        {
            count[9]++;//increment counter for occurence of single nucleotide gu
            break;
        }
        case(10):
        {
            count[10]++;//increment counter for S->LS event
            break;
        }
        case(11):
        {
            count[11]++;//increment counter for S->e event
            break;
        }
        default:
            System.out.println("---------------------Wrong Index Inserted------------------");
        }
        return count;
    }
 
    
    int[] setCount(String nucleotide){
        
                   
                switch(nucleotide){
                    case ("A")://if the rna nuclueotide is an A
                    {
                        count[0]++;//increment counter for occurence of single nucleotide A
                        
                        break;
                    }
                    case ("C")://if the rna nuclueotide is a C
                    {                     
                       count[1]++;//increment counter for occurence of single nucleotide C
                       
                        break;
                    }
                    case ("G")://if the rna nuclueotide is a G
                    {                        
                        count[2]++;//increment counter for occurence of single nucleotide G
                        
                        break;
                    }
                    case ("U")://if the rna nuclueotide is a U
                    {
                        count[3]++;//increment counter for occurence of single nucleotide U
                        
                        break;
                    }
                   case ("AU")://if the rna BOND  is AU
                    {
                        count[4]++;//increment counter for occurence of BOND AU
                        break;
                    }
                   case ("UA")://if the rna BOND  is UA
                    {
                        count[5]++;//increment counter for occurence of BOND UA
                        break;
                    }
                    case ("CG")://if the rna BOND  is CG
                    {
                        count[6]++;//increment counter for occurence of BOND CG
                        break;
                    }
                    case ("GC")://if the rna BOND  is GC
                    {
                        count[7]++;//increment counter for occurence of BOND GC
                        break;
                    }
                    case ("UG")://if the rna BOND IS UG
                    {
                        count[8]++;//increment counter for occurence of BOND UG
                        break;
                    }
                    case ("GU")://if the rna BOND  is GU
                    {
                        count[9]++;//increment counter for occurence of BOND GU
                        break;
                    }
                    case("LS"):
                    {
                        count[10]++;//increment counter for S->LS event, because an e event occurs at the end of an RNA string
                        break;
                    }
                     case("e"):
                    {
                        count[11]++;//increment counter for S->e event, because an e event occurs at the end of an RNA string
                        break;
                    }
                    default:
                    { 
                        System.out.println("-----------------ERROR: UNUSUAL BOND ENCOUNTERED!!-----------------------------");
                
                        break;
                
                    }
                }//end switch to search for single nucleotides
                
        return count;
    }//end of  method to determine rule
    
    int[] getCount(){
        return count;
    }
    
    
            
}

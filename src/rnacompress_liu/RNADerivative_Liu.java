/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnacompress_liu;
import java.util.Arrays;

/**
 *
 * @author Juluis
 */
public class RNADerivative_Liu {
    String rnaPSeq,rnaDBracket;
    
    public RNADerivative_Liu(String PrimarySequence, String DotBracket){
        rnaPSeq = PrimarySequence;
        rnaDBracket = DotBracket;
    }
    
    public String encodeRNA(){
        
        String code = "";
        
        int i=0;//initialising index which reads through the rnasequence
        //System.out.println(rnaDBracket.length());
        while (i<rnaPSeq.length())
        {
           //System.out.println("====================entered here 1!!!!!!!!!!!!!!! ");
            //reading the dot bracket sequence from left to right
            if(rnaDBracket.charAt(i)=='.'){//single nucleotide
                //since an LS event always occurs before a . and (
               //the huffman code for S->LS is 0
                code = code +"0";//the char 0 is concatenated
                switch(rnaPSeq.charAt(i)){
                    case ('A')://if the rna nuclueotide is an A
                    {
                        //System.out.println("\n-------------ENTERED HERE A---------\n");
                        //the huffman code for L->A is 00
                        code = code +"00";//the string 00 is concatenated
                        break;
                    }
                    case ('C')://if the rna nuclueotide is a C
                    {
                        //System.out.println("\n-------------ENTERED HERE C---------\n");
                        //the huffman code for L->C is 1111
                        code = code +"1111";//the string 1111 is concatenated
                        
                        break;
                    }
                    case ('G')://if the rna nuclueotide is a G
                    {
                        //System.out.println("\n-------------ENTERED HERE G---------\n");
                       //the huffman code for L->G is 010
                        code = code +"010";//the string 00 is concatenated
                        
                        break;
                    }
                    case ('U')://if the rna nuclueotide is a U
                    {
                      //  System.out.println("\n-------------ENTERED HERE U---------\n");
                        //the huffman code for L->U is 100
                        code = code +"100";//the string 100 is concatenated
                        
                        break;
                    }
                    default:
                        break;
                }//end switch to search for single nucleotides
                
            }//end if for .
            else
                if (rnaDBracket.charAt(i)=='('){//secondary bond
                    //System.out.println("\n-------------ENTERED HERE 5---------\n");
                    //an LS event always precedes an (
                    //the huffman code for S->LS is 0
                code = code +"0";//the char 0 is concatenated
                code = code + getHuffmancode4SecBond(i,rnaPSeq,rnaDBracket);//
                                  
                }
                else
                    if (rnaDBracket.charAt(i)==')'){//and e event always occurs before a )
                       // System.out.println("\n-------------ENTERED HERE 6---------\n");
                       //the huffman code for S->e is 1
                         code = code +"1";//the char 1 is concatenated
                    
                    }
                       
            i++;
        }
        //at the end of the rna string if the last nucleotide is unpaired i.e. a . we need to include 
        // a closing event s->e
        //for the which the huffman code is 1
        if(rnaDBracket.charAt(rnaDBracket.length()-1)=='.')
        {   code = code+"1"; }
        
        return code;
    }//end of  method to determine rule
    
    String getHuffmancode4SecBond(int i1, String rnaPSeq1, String rnaDBrack1){
        //System.out.println("\n-------------ENTERED HERE 7 ---------\n");
        int w=i1;
        int a=0;
        
        String secBond;//string variable to store the  secondary bond pair
        String SecBondCode="";// string variable to store the huffman code for the secondary bond pair
        
        while (i1<rnaPSeq1.length()){
           //System.out.println("\n-------------ENTERED HERE 8---------\n");
            if(rnaDBrack1.charAt(i1)=='('){
                //System.out.println("\n-------------ENTERED HERE 9---------\n");
                 a++;//keeping a count of the open brackets
            }
            else
                if(rnaDBrack1.charAt(i1)==')'){
                    //System.out.println("\n-------------ENTERED HERE 10---------\n");
                      a--;//cancelling each ( with its corresponding )
                }
               
               
               if (a==0){
                   //System.out.println("\n-------------ENTERED HERE 11---------\n");
                   secBond= ""+ rnaPSeq1.charAt(w) + rnaPSeq1.charAt(i1);
                   SecBondCode= getH4SB(secBond);
                   return SecBondCode ;//gets the huffman code for the sec bond
               }
               i1++;     
             }//end of while loop
        
        //System.out.println("--------------------------ERROR-----------------------");
     
        return SecBondCode;
   }//end of method to obtain rule pairs for secondary bond
    
    
    String getH4SB(String sBond){//switches the 6 possible secondary bonds and returns the c
        //corresponding huffman code
        String secCode;
        //System.out.println("\n--------------Entered getH4SB--------------------------\n");
        //System.out.print(sBond);
        switch(sBond)
        {
            case ("AU"):
            { 
                //System.out.println("\n-------------ENTERED HERE 12 AU---------\n");
                
               
                return "0110";
                
                
            }
            case ("UA"):
            { 
                //System.out.println("\n-------------ENTERED HERE 13 UA---------\n");
                 return "1110";
                
            }
            case ("CG"):
            { 
                //System.out.println("\n-------------ENTERED HERE 14 CG---------\n");
                 return "110";
            }
            case ("GC"):
            { 
                //System.out.println("\n-------------ENTERED HERE 15  GC---------\n");
                 return "101";
             
            }
            case ("GU"):
            { 
                //System.out.println("\n-------------ENTERED HERE 16  GU---------\n");
                 return "01111";
                
            }
            case ("UG"):
            { 
                //System.out.println("\n-------------ENTERED HERE 17   UG---------\n");
                return "01110";
            }
            default:
            { 
                System.out.println("-----------------ERROR-----------------------------");
                System.out.printf("UNUSUAL BOND %S FOUND", sBond);
                
                return "";
                
            }
        }
    }
   
    
}

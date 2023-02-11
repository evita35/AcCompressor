/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparecompressions;
import rnacompress_liu.*;//import rna compress to use derivative

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Juluis
 */
public class NonCanonicalConversion {
    String PrimarySequence;
    String DotBracket;
    boolean nonCanon = false;
    
    public NonCanonicalConversion(String PS, String DB){
        PrimarySequence=PS;//initialises primary sequence
        DotBracket=DB;//initialised dotbracket string
    }
    boolean findEmptyBracket(String rnaDBracket){
        int i=0;
        while (i< rnaDBracket.length()-1){
            if(rnaDBracket.charAt(i)=='(' && rnaDBracket.charAt(i+1)==')'){
                return true;
            }
            i++;
        }
        return false;
    }

    boolean findNonCanonBonds(String rnaPSeq, String rnaDBracket){
       nonCanon=false;
        
        int i=0;//initialising index which reads through the rnasequence
           
        while (i<rnaPSeq.length())
        {
           
            //reading the dot bracket sequence from left to right
            if(rnaDBracket.charAt(i)=='.'){
                //do nothing
                //to single nucleotides
                
            }//end if for .
            else
                if (rnaDBracket.charAt(i)=='('){//secondary bond
                    //System.out.println("\n-------------ENTERED HERE 5---------\n");
                    
                    nonCanon=searchForNonCanon(i,rnaPSeq,rnaDBracket);
                                  
                }
                else
                    if (rnaDBracket.charAt(i)==')'){//and e event always occurs before a )
                       // System.out.println("\n-------------ENTERED HERE 6---------\n");
                       
                         //do nothing
                    
                    }
                       
            i++;
        }
        //at the end of the rna string if the last nucleotide is unpaired i.e. a . we need to include 
        // a closing event s->e
        //for the which the huffman code is 1
       
        
        return nonCanon;
    }//end of  method to determine rule

    String[] convertToDotBracket4rmJackRipFormat(String Prim, String Sec){

          String newPrim = "";
          String newSec="";

            for (char i : Prim.toCharArray()) {
                if (i == '['|| i == ','|| i == ']') {
                    newPrim = newPrim;
                } else  newPrim = newPrim + i;
            }

            for (char i: Sec.toCharArray()){
                if(i=='['|| i==','|| i == ']'){
                    newSec=newSec;
                } else if(i=='|')
                {
                    newSec=newSec+'.';
                }else newSec= newSec+i;
            }

            String[] PrimAndSec={newPrim, newSec};
            return PrimAndSec;

    }

    boolean searchForNonCanon(int i1, String rnaPSeq1, String rnaDBrack1){

        //System.out.println("\n-------------ENTERED HERE 7 ---------\n");
        int w=i1;//counter to keep track of position of nucleotide bond in the primary sequence and 
        //dot bracket string
        int a=0;
        
        String secBond;//string variable to store the  secondary bond pair
       // String SecBondCode="";// string variable to store the huffman code for the secondary bond pair
        
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
                   nonCanon=alterDotBracket(secBond,w,i1);
                   //System.out.println("VALUE OF NONCANON IS: "+ nonCanon);
                   return nonCanon ;//
               }
               i1++;     
             }//end of while loop
        
        //System.out.println("--------------------------ERROR-----------------------");
     
        return false;
   }//end of method to obtain rule pairs for secondary bond
    
    
    boolean alterDotBracket(String sBond,int d1,int d2){//switches the 6 possible secondary bonds and returns the c

        //System.out.println("\n--------------Entered getH4SB--------------------------\n");
        //System.out.print(sBond);
        switch(sBond)
        {
            case ("AU"):
            { 
                //System.out.println("\n-------------ENTERED HERE 12 AU---------\n");
                
               
              //  System.out.println("OK\n");
                break;
                
            }
            case ("UA"):
            { 
                //System.out.println("\n-------------ENTERED HERE 13 UA---------\n");
               // System.out.println("OK\n");
                break;
            }
            case ("CG"):
            { 
                //System.out.println("\n-------------ENTERED HERE 14 CG---------\n");
                 //System.out.println("OK\n");
                 break;
            }
            case ("GC"):
            { 
                //System.out.println("\n-------------ENTERED HERE 15  GC---------\n");
                // System.out.println("OK\n");
                 break;
            }
            case ("UG"):
            { 
                //System.out.println("\n-------------ENTERED HERE 16  GU---------\n");
               //  System.out.println("OK\n");
                break;
            }
            case ("GU"):
            { 
                //System.out.println("\n-------------ENTERED HERE 17   UG---------\n");
               // System.out.println("OK\n");
                break;
            }
            default:
            { 
                //System.out.println("-----------------ERROR-----------------------------");
                //System.out.printf("UNUSUAL BOND %S FOUND", sBond);
                //System.out.println("ALTERING DOT BRACKET\n");
                //conversion of noncanonical bond to single nucleotides
                
                    //DotBracket= DotBracket.substring(0, d1)+'.'+DotBracket.substring(d1+1);
                
                    //DotBracket= DotBracket.substring(0, d2)+'.'+DotBracket.substring(d2+1);
                

                return true;
                //break ;
                
            }
        }
        return false;
    }

    
}

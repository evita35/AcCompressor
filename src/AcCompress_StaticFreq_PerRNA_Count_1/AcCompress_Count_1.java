/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcCompress_StaticFreq_PerRNA_Count_1;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author Eva
 */
public class AcCompress_Count_1 {

    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        // This is the main method that starts the RNA compress program
        String rnaPrimSeq;
        String dotBrackString;
        Scanner newString = new Scanner(System.in);
        System.out.println("_-------------------This program compresseses RNA secondary structure to simple sequence of binary code------------- \n");
        // System.out.println("CAREFULLY TYPE AND ENTER THE RNA PRIMARY SEQUENCE\n");//prompts user to enter value
        //  rnaPrimSeq=newString.nextLine();
        rnaPrimSeq="GCCUACGGCCAUAUCACGUUGAGUACACCCGAUCUCGUUCGAUCUCGGAAGUUAAGCAACGUCGAGUCCGGUCAGUACUUGGAUGGGAGACCGCCUGGGAACACCGGGAGUUGUAGGCAU";
        dotBrackString="(((((((((....((((((((...((..((((..((....))..))))..))....)))))).)).((((((..((.((..(.(((....))).)..)).)))))))).)))))))))..";
        //--------------------------TEST DATA--------------------------------------------------------
       // rnaPrimSeq="AUCAUACCGUAU";
        //dotBrackString="((..()..()))";
        //rnaPrimSeq="GGCGUACGUUUCGUACGCC";
        //dotBrackString="(((((((.....)))))))";
        
        // rnaPrimSeq="GGGUACGUUUCGUACCC";
        //dotBrackString="((((((.....))))))";
        //rnaPrimSeq="GGCGUACGUUUCGUACGCC";
        //dotBrackString="(((((((.....)))))))";
        //dotBrackString="(((((((((....((((((((...((..((((..((....))..))))..))....)))))).)).((((((..((.((..(.(((....))).)..)).)))))))).)))))))))..";
        // rnaPrimSeq= "UAUAG";
        //dotBrackString="..().";
        //  rnaPrimSeq= "ACGUAUCGUGUGCGAU";
        // dotBrackString="....(((((())))))";
        
        
        System.out.println("........................Processing\n");//starts process
         System.out.printf("%s\n %s\n", rnaPrimSeq,dotBrackString);
        BigDecimal[] finalSubIntervalObtained; //stores final subInterval
        //creates object to encode rna string
        RNA_AC_ENCODE_ADAPT_PROB rnaEncode2 = new RNA_AC_ENCODE_ADAPT_PROB(rnaPrimSeq, dotBrackString);
       
        String binaryCode2=rnaEncode2.binaryCode;
        System.out.printf("\nLength of binary code is: %d \n", (binaryCode2.length()));
        System.out.println(binaryCode2);
        
        //creates object to decode binary string
        RNA_AC_DECODE_ADAPT_PROB rnaDecode2 = new RNA_AC_DECODE_ADAPT_PROB(binaryCode2);
        String[] RnaDecoded=rnaDecode2.decode();
        
        //decoded RNA is displayed
        System.out.println();
        System.out.println("The decoded RNA string and dotbracket sequence");
        
        System.out.print(RnaDecoded[0]);//displays the RNA primary sequence
        
        System.out.println();
        System.out.print(RnaDecoded[1]);//displays the dotbracket notation     
        //end
        
    }*/
    
}

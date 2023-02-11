/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparecompressions;
import rna_ac_compress_general_probabilities.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.lang.String;

import rnacompress_liu.*;
import Adaptive_AC_Compressor.*;


/**
 *
 * @author Eva
*/


public class CompareCompressions {

        public static void main(String[] args) throws IOException {
            System.out.print("Number of Bits used to encode each RNA string using:\n");
            System.out.println("RNA file\t\t LIU COMPRESSOR\t\tAC COMPRSR USING GENERAL PROB /* \t\tAC COMPRSR USING RNA SPECIFIC PROB\t\tAC COMPRSR USING RNA SPECIFIC PROB WITHOUT FREQ\t\tADAPTIVE_AC_COMPRESSOR*/");

            String nextFile;
            File folder = new File("C:\\Users\\evita\\Downloads\\friemel_Modified_nonCanon_free_nonEmptyHairpin\\friemel\\");

            File compressionFile= new File("C:\\Users\\evita\\Downloads\\"+"rnaCompress_Vs_AcCompress");
            BufferedWriter bf = null;
            bf = new BufferedWriter(new FileWriter(compressionFile));
            bf.flush();

            bf.write("FileName\t\tRNASize\t\tRNAcompressbitLength\t\tAcCompressbitLength");
            bf.newLine();

            for (File file : folder.listFiles())
            {
                String[] compressionOutput = compress(file);
                String filename=file.toString();
                bf.write(filename.substring(filename.lastIndexOf("\\")+1)+"\t\t"+compressionOutput[0].length()+"\t\t" + compressionOutput[1].length()+"\t\t"+compressionOutput[2].length());
                bf.newLine();


            }
            bf.close();

     
    }

    public static String[] compress(File file){
        BufferedReader br = null;
        try {//exception for handlng non existent text file
            br = new BufferedReader(new FileReader(file));

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist!!!!!!!");
            System.exit(0);

        }
        try{

            if(file!=null){


                    Scanner rnaFile= new Scanner(file);//reads next file containing
                    //sample RNA string
                    String RNA_PrimarySequence=rnaFile.nextLine().toUpperCase();
                    String RNA_DotBracket=rnaFile.nextLine().toUpperCase();
                    //System.out.println("RNA structure before breaking up noncanonical bonds\n");
                    //System.out.println(RNA_PrimarySequence);
                    //System.out.println(RNA_DotBracket);
                    //System.out.println(RNA_PrimarySequence.length());
                    // System.out.println(RNA_DotBracket.length());
                    //creates object to break up noncanonical bonds
                    //NonCanonicalConversion Ncc = new NonCanonicalConversion(RNA_PrimarySequence, RNA_DotBracket);
                    //control enters this block if a non canonical bond exists in the RNA string
             /*
            if( Ncc.findNonCanonBonds( RNA_PrimarySequence, RNA_DotBracket))
            {  //deletes contents of the file
             FileOutputStream writer = new FileOutputStream(nextFile);
             writer.write(("").getBytes());
             writer.close();
             //writes the broken noncanonical bonds to the teext file
             String CanonicalRNA = (Ncc.PrimarySequence+"\n"+Ncc.DotBracket);

              try(FileOutputStream fos = new FileOutputStream(nextFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                     //convert string to byte array
                    byte[] bytes = CanonicalRNA.getBytes();
                    //write byte array to file
                    bos.write(bytes);
                    bos.close();
                    fos.close();
                    System.out.print("Data written to file successfully.");
                    } catch (IOException e) {
                            e.printStackTrace();
                    }

             rnaFile= new Scanner(new File(nextFile));//reads next file containing
             //sample RNA string
             RNA_PrimarySequence=rnaFile.nextLine();
             RNA_DotBracket=rnaFile.nextLine();
             //System.out.println("RNA structure after breaking up noncanonical bonds\n");

             //System.out.println(RNA_PrimarySequence);
            // System.out.println(RNA_DotBracket);
            }*/
                    // call to Liu compressor

                    RNADerivative_Liu rnaDerivative = new RNADerivative_Liu(RNA_PrimarySequence, RNA_DotBracket);//creates object of class which obtains derivative and encodes the RNA
                    String compressedCode1= rnaDerivative.encodeRNA();//stores the compressed RNA by liu et al compresssion method

                    //call to Arithmetic Coding Compressor using general probabilities for RNA bonds and single nucleotides
                    ENCODE_GENERIC_PROB rnaEncode = new ENCODE_GENERIC_PROB(RNA_PrimarySequence, RNA_DotBracket);
                    String compressedCode2 =rnaEncode.getBinaryCode();
            /*
            //call to Arithmetic Coding Compressor using RNA specific probabilities-
            ENCODE_STATIC_PROB rnaEncode2 = new ENCODE_STATIC_PROB(RNA_PrimarySequence, RNA_DotBracket);
            String compressedCode3=rnaEncode2.getBinaryCode();
            String compressedCode5=rnaEncode2.getBinaryCodeWithoutFrequency();
            */
                    //CALL TO ADAPTIVE ARITHMETIC CODING
                    //ENCODE_ADAPTIVE_PROB rnaEncode3 = new ENCODE_ADAPTIVE_PROB(RNA_PrimarySequence, RNA_DotBracket);
                    //String compressedCode4 = rnaEncode3.getBinaryCode();

                    //CALL TO Semi_Adaptive_AC_Compressor
                    //ENCODE_SEMI_ADAPTIVE_PROB rnaEncode6= new ENCODE_SEMI_ADAPTIVE_PROB(RNA_PrimarySequence, RNA_DotBracket);
                    //String compressedCode6 = rnaEncode6.getBinaryCode();
                    // System.out.println(compressedCode4.length());
                    //RNA_AC_DECODE_GENERAL_PROB rnaDecode=new RNA_AC_DECODE_GENERAL_PROB();
                    // String[] rnaStructureDecoded = rnaDecode.decode(compressedCode2);
                    //System.out.println(rnaStructureDecoded[0]);
                    //System.out.println(rnaStructureDecoded[1]);
                    //System.out.println(compressedCode1.length()+"\t\t\t\t"+compressedCode2.length()+"\t\t\t\t\t\t"+compressedCode3.length()+"\t\t\t\t\t\t"+compressedCode5.length()+"\t\t\t\t\t\t"+compressedCode4.length());

                    return new String[] {RNA_PrimarySequence,compressedCode1,compressedCode2};

            }
        }
        catch (IOException e){
            System.out.println(e);
        }
        return null;
    }
    
}   

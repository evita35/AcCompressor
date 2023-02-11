        /*
         * To change this license header, choose License Headers in Project Properties.
         * To change this template file, choose Tools | Templates
         * and open the template in the editor.
         */

        package comparecompressions;

        //import AcCompress_StaticFreq_PerRNA_Count_1.ENCODE_STATIC_PROB;
        import java.io.*;
        import java.io.IOException;
        import java.time.LocalDateTime;
        import java.util.Comparator;
        import java.util.Map;
        import java.util.Scanner;
        import java.lang.String;
        import java.util.TreeSet;

        import rnacompress_liu.*;


        /**
         *
         * @author Eva
        */



        public class ComparingFriemelDataSet {
            public static void main (String[] args){
                //File folder = new File("C:\\Users\\USER\\Documents\\GitHub\\clone\\clone\\datasets\\JackRIP-SecStrucPrediction-BenchMarkDataSet-rule-probs-dowell-mixed80-G7Bound-withNCR-true");
                File folder = new File("C:\\Users\\evita\\OneDrive\\Documents\\GitHub\\compressed-rna\\datasets\\friemel-modified\\");
                scanAllFiles(folder);
            }

            static void scanAllFiles(File folder){
                System.out.println("-----------------SCANNING ALL FILES----------------");
                int nonParsableRNAs=0;
                int countRNAs=0;

                System.out.println("LIU COMPRESSOR\t\tAC COMPRSR USING GENERAL PROB  \t\tAC COMPRSR USING RNA SPECIFIC PROB\t\tAC COMPRSR USING RNA SPECIFIC PROB WITHOUT FREQ\t\tADAPTIVE_AC_COMPRESSOR\t\tSEQUENCE LENGTH");
                File[] fileNames = folder.listFiles();//return array of all the files in the folder fileNames   \
                System.out.println("NUMBER OF FILES IN FOLDER IS: "+ fileNames.length);
                //for(File file: fileNames){

                try {
                    File file = new File("C:\\Users\\evita\\OneDrive\\Documents\\GitHub\\compressed-rna\\rnaCompress.txt");
                    BufferedWriter bf = null;

                    bf = new BufferedWriter(new FileWriter(file));

                    bf.flush();

                for(int i=0; i<fileNames.length;i++){


                    try{

                        //System.out.printf("%s\n", fileNames[i].toString());
                        Scanner rnaFile= new Scanner(new File(fileNames[i].toString()));//reads next file containing
                        countRNAs++;
                        //sample RNA string
                        //System.out.println("File Name is: "+ fileNames[i].toString());
                        String RNA_PrimarySequence=rnaFile.nextLine().toUpperCase().trim();
                        String RNA_DotBracket=rnaFile.nextLine().trim();
                        //int RNA_Length = RNA_PrimarySequence.length()*2;
                        //System.out.println(RNA_PrimarySequence);
                        //System.out.println(RNA_DotBracket);

                       // NonCanonicalConversion NCC = new NonCanonicalConversion(RNA_PrimarySequence, RNA_DotBracket);
        /*
                        if(NCC.findEmptyBracket(RNA_DotBracket))
                        {
                            nonParsableRNAs++;
                            System.out.println("File name is: "+ file.toString());
                        }


                        if(NCC.findNonCanonBonds(RNA_PrimarySequence,RNA_DotBracket))
                        {
                            System.out.println("File name is: "+ file.toString());
                            nonParsableRNAs++;
                        }*/
                        String[] primAndSec;
                        //primAndSec=NCC.convertToDotBracket4rmJackRipFormat(RNA_PrimarySequence,RNA_DotBracket);

                        //NCC.findNonCanonBonds(RNA_PrimarySequence,RNA_DotBracket);
        /*
                        FileOutputStream writer = new FileOutputStream(file.toString());
                        writer.write(("").getBytes());
                        writer.close();

                        BufferedWriter newWriter = new BufferedWriter(new FileWriter(file));
                        newWriter.write(primAndSec[0]);
                        newWriter.newLine();
                        newWriter.write(primAndSec[1]);
                        newWriter.close();
                        System.out.println("DONE!!!");
        */
                        // call to Liu compressor

                     RNADerivative_Liu rnaDerivative = new RNADerivative_Liu(RNA_PrimarySequence, RNA_DotBracket);//creates object of class which obtains derivative and encodes the RNA
                     String compressedCode1= rnaDerivative.encodeRNA();//stores the compressed RNA by liu et al compresssion method
                    // double Liu_ratio =((double)compressedCode1.length())/RNA_Length;
                     //call to Arithmetic Coding Compressor using general probabilities for RNA bonds and single nucleotides
                        // ENCODE_GENERIC_PROB rnaEncode = new ENCODE_GENERIC_PROB(RNA_PrimarySequence, RNA_DotBracket);
                     //double Generic_ratio =((double)rnaEncode.getIntvlLength())/RNA_Length;
                        //call to Arithmetic Coding Compressor using RNA specific probabilities with frequency count
                     //ENCODE_STATIC_PROB rnaEncode2 = new ENCODE_STATIC_PROB(RNA_PrimarySequence, RNA_DotBracket);
                     //double Static_ratio_with_frequency = ((double)rnaEncode2.getIntvlLengthWithFrequency())/RNA_Length;
                    // double Static_ratio_without_frequency = ((double)rnaEncode2.getIntvlLength())/RNA_Length;
                        //CALL TO ADAPTIVE ARITHMETIC CODING
                   // ENCODE_ADAPTIVE_PROB rnaEncode3 = new ENCODE_ADAPTIVE_PROB(RNA_PrimarySequence, RNA_DotBracket);
                   // double adaptive_ratio  =((double)rnaEncode3.getIntvlLength())/RNA_Length;

                  /*

                     System.out.printf("%d\t\t\t%d\t\t\t\t%d\t\t\t"
                             + "\t%d\t\t\t\t%d\t\t\t\t%d\n",compressedCode1.length(),rnaEncode.getIntvlLength(),rnaEncode2.getIntvlLengthWithFrequency(),rnaEncode2.getIntvlLength(),rnaEncode3.getIntvlLength(),RNA_Length);
                    */
                       // System.out.printf("%s\t\t%d\n", file.toString(),compressedCode1.length());
                        bf.write(fileNames[i].toString() + "\t" + compressedCode1.length());
                        bf.newLine();

                        rnaFile.close();

                    }
                    catch (IOException e){
                        System.out.println(e);
                        System.out.println("ERROR ERROR ERROR");
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                }
                bf.flush();

                }
                catch(Exception e){
                    System.out.println(e);
                }
                System.out.printf("total number of RNAs is %d", countRNAs);
               // System.out.printf("THERE ARE %d Non-Parsable RNAs\n", nonParsableRNAs);
            }
        }

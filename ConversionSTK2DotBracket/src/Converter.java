import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    String primarySequence;
    String secondaryStructure;
    CreateRnaFile rnaFile;
    String RNAname;
    Matcher m;
    Scanner scannedFile;
    String line;//string to store each new rna string read from file
    String location = "C:\\Users\\USER\\Documents\\Benchmark-raw";//folder to store RNAs

    Map<Character, String[]> codeToBasePair;
    Map<Character, String> codeToBase;

    public Converter(String fileName) throws IOException, STKFileException {
        File RNAFile = new File(fileName);

        codeToBase = new HashMap<>();
        codeToBase.put('A', "A");
        codeToBase.put('C', "C");
        codeToBase.put('G', "G");
        codeToBase.put('U', "U");
        codeToBase.put('R', "AG");
        codeToBase.put('Y', "CU");
        codeToBase.put('K', "GU");
        codeToBase.put('M', "AC");
        codeToBase.put('S', "CG");
        codeToBase.put('W', "AU");
        codeToBase.put('B', "CGU");
        codeToBase.put('D', "AGU");
        codeToBase.put('H', "ACU");
        codeToBase.put('V', "ACG");
        codeToBase.put('N', "ACGU");

        codeToBasePair=new HashMap<>();
        codeToBasePair.put('K', new String[]{"GU","UG"});
        codeToBasePair.put('S', new String[]{"CG","GC"});
        codeToBasePair.put('W', new String[]{"AU","UA"});
        codeToBasePair.put('B', new String[]{"UG","GU","CG","GC"});
        codeToBasePair.put('D', new String[]{"AU","UA","GU","UG"});
        codeToBasePair.put('H', new String[]{"AU","UA"});
        codeToBasePair.put('V', new String[]{"CG","GC"});
        codeToBasePair.put('N', new String[]{"AU","UA","CG","GC","GU","UG"});

        try {
            scannedFile = new Scanner(RNAFile);
            System.out.println("scanning file...");
            getNextNonBlankLine();
            System.out.println("line is " + line);
        } catch (Exception e) {
            throw e;
        }

        while (line.contains("# STOCKHOLM 1.0")) {
            RNAname = "";
            primarySequence = "";
            secondaryStructure = "";

            getNextNonBlankLine();
            if (line.contains("#=GF"))
                getNextNonBlankLine();

            Pattern p = Pattern.compile("#=GS ");//string #=GS identifies the name of RNA
            m = p.matcher(line);
            //System.out.println("m is "+m.find());
            if (m.find()) {
                try {
                    System.out.println("m.end() is " + m.end());

                    String substr = line.substring(m.end(), line.length());
                    System.out.println("*********************************************value of substring is: " + substr);
                    RNAname = substr.substring(0, substr.indexOf(" DE"));
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!RNAname is: " + RNAname);
                    if(RNAname.isBlank())
                        throw new STKFileException("RNA Name not found!");
                    }
                catch (STKFileException e)
                    { System.out.println(e);}

            }



            while (!line.equals("//")) {
                while (Pattern.matches("\\s*", line))//skips blank lines
                    line = scannedFile.nextLine();

                if (checkEndOfRNA()) {
                    getNextNonBlankLine();

                    break;
                }

                p = Pattern.compile("#=GR");
                m = p.matcher(line);
                boolean matched = m.find();
                if (matched) {
                    System.out.println("GOT HERE!");
                    p = Pattern.compile("SS" + "\\s");
                    m = p.matcher(line);
                    matched = m.find();
                    if (matched) {
                        System.out.println("GOT HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!s");
                        secondaryStructure = secondaryStructure + line.substring(m.end());
                    }
                    else
                        throw new STKFileException("Substring of secondary structure not found!");

                } 
                else 
                {
                    p = Pattern.compile("#=GS");
                    Pattern q = Pattern.compile("#=GF");
                    m = p.matcher(line);
                    Matcher m2 = q.matcher(line);
                    matched = m.find();
                    boolean matched2 = m2.find();


                    if (matched || matched2) {/*do nothing*/} else {
                        System.out.println("RNAname is " + RNAname + " line is " + line);
                        p = Pattern.compile(RNAname + "\\s");
                        m = p.matcher(line);
                        System.out.println("trying to debug primary sequence line is " + line);
                        System.out.println("m.find() is " + m.find());

                        String substring =line.substring(m.end());
                        if(substring.isBlank())
                            throw new STKFileException("substring of primary sequence not found!");

                        primarySequence = primarySequence + substring;
                        System.out.println("primary sequence is: " + primarySequence);
                    }
                }

                if (checkEndOfRNA()) {
                    getNextNonBlankLine();

                    break;
                }
                getNextNonBlankLine();
            }
            if (checkEndOfRNA()) {
                getNextNonBlankLine();


            } else
                getNextNonBlankLine();
        }


    }

    public String deleteWhiteSpace(String string) {
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(string);
        string = m.replaceAll("");
        System.out.println("string without white space" + string);
        return string;
    }

    public String replaceAngleBrackets(String string) {
        String newString = "";
        for (char i : string.toCharArray()) {
            if (i == '<') {
                newString = newString + ')';
            } else if (i == '>') {
                newString = newString + '(';
            } else newString = newString + i;
        }
        return newString;
    }
    public String loosenPseudoknots(String secondaryStruc){

        char i;
        for(i='a';i<='z';i++) {
            //System.out.println("Found "+i+" at index "+secondaryStruc.indexOf(i));
            secondaryStruc = secondaryStruc.replace(i, '.');
        }

        for(i='A';i<='Z';i++)
            secondaryStruc=secondaryStruc.replace(i,'.');

        System.out.println("SECONDARY STRUCTURE AFTER LOOSENING PSEUDOKNOTS "+secondaryStruc);

        return secondaryStruc;

    }

    public void getNextNonBlankLine() {

            line = scannedFile.nextLine();
            System.out.println("line is: " + line);
            while (Pattern.matches("\\s*", line))//skips blank lines
                line = scannedFile.nextLine();
            System.out.println("line is: " + line);

    }
    public String replaceUnknownBases(String primary, String secondary) throws STKFileException{
        String bases = "ACGU";
        String[] canonicalBasePair ={"AU","UA","CG","GC","GU","UG"};
        StringBuilder newPrimarySeq=new StringBuilder(primary);

        String BaseCodes ="NKYSMRWHBVD";
        Random rand = new Random();
        //int[] intArray =new int[2];
        int[] zeroArray={0,0};
        //System.out.println("Value of zeroArray is: "+ zeroArray);
        for(int i=0; i<primary.length(); i++)
        {

            //System.out.println("Pattern is matched "+ BaseCodes.contains(""+primary.charAt(i)) +" value of i is: "+ i+
            //"\n Sequence length is: "+ primary.length());
            char baseSymbol=primary.charAt(i);
            System.out.println("Base Symbol is: "+ baseSymbol);

            if(!(BaseCodes.contains(""+baseSymbol)||bases.contains(""+baseSymbol)))
                throw new STKFileException("FOUND UNKNOWN SYMBOL");
            if(BaseCodes.contains(""+primary.charAt(i)))
            {
                int numberOfReplacements = codeToBase.get(baseSymbol).length();


                System.out.println("*******************************************************GOT HERE");
                if(secondary.charAt(i)=='.')
                {
                    newPrimarySeq.setCharAt(i,codeToBase.get(baseSymbol).charAt(rand.nextInt(numberOfReplacements)));//replaces unknown base with one of A,C,G,U

                }
                else
                if(secondary.charAt(i)=='(')//replace unknown pair with a canonical base pair
                {
                    int[] intArray=getPair(i,secondary);
                    System.out.println("Value of intarray is: "+ intArray[0]+","+intArray[1]);
                    System.out.println("Value of charachters at the array positions are: "+primary.charAt(intArray[0])+" "+primary.charAt(intArray[1]));
                    if (Arrays.equals(intArray,zeroArray)) {
                        throw new STKFileException("pair for base not found!");
                    }
                    if(Objects.isNull(codeToBasePair.get(baseSymbol)))//symbols for which replaceables are non-canonical
                    {
                        String canon;
                        char baseSymbol2=primary.charAt(intArray[1]);
                        int numberOfReplacements2=codeToBase.get(baseSymbol2).length();
                        canon= ""+codeToBase.get(baseSymbol).charAt(rand.nextInt(numberOfReplacements))
                                +codeToBase.get(baseSymbol2).charAt(rand.nextInt(numberOfReplacements2));

                        System.out.println("VALUE OF CANON IS: "+canon +" VALUE OF  SEARCH IS:" +Arrays.asList(canonicalBasePair).contains(canon));
                        while(!Arrays.asList(canonicalBasePair).contains(canon))//ensures the pair for a canonical base pair using rejection sampling some how
                        {
                            canon= ""+codeToBase.get(baseSymbol).charAt(rand.nextInt(numberOfReplacements))
                                    +codeToBase.get(baseSymbol2).charAt(rand.nextInt(numberOfReplacements2));//
                            System.out.println("VALUE OF CANON IS: "+canon +" VALUE OF BINARY SEARCH IS:" +Arrays.asList(canonicalBasePair).contains(canon));
                        }
                        newPrimarySeq.setCharAt(intArray[0], canon.charAt(0));//hopefully a canonical pair has been obtained
                    }
                    else {
                        int noOfReplacementPair = codeToBasePair.get(baseSymbol).length - 1;
                        String bp = codeToBasePair.get(baseSymbol)[rand.nextInt(noOfReplacementPair)];
                        //System.out.println("Value of bp is: "+ bp+" value of int array is: "+intArray);
                        newPrimarySeq.setCharAt(intArray[0], bp.charAt(0));
                        newPrimarySeq.setCharAt(intArray[1], bp.charAt(1));
                    }

                }
                else if(secondary.charAt(i)==')'){//the unknown base already has a known base as pair
                    newPrimarySeq.setCharAt(i,codeToBase.get(baseSymbol).charAt(rand.nextInt(numberOfReplacements)));//replaces unknown base with one of A,C,G,U
                }

            }else
                if(bases.contains(""+primary.charAt(i))){
                    //do nothing
                }
                else
                {
                    System.out.println("----------------------------------------------------------found new unknown character "+primary.charAt(i)+" in " +
                            "primary sequence");
                    //throw new STKFileException("FOUND NEW CHARACTER "+primary.charAt(i)+" IN PRIMARY SEQUENCE ");
                }

        }
        return newPrimarySeq.toString();
    }

    public int[] getPair(int index, String secondaryStruct){//gets matching parenthesis

            int w=index;
            int a=0;
            int[] pair= {0,0};

            while (w<secondaryStruct.length()){

                    //System.out.println("\n-------------ENTERED HERE 1---------\n");


                if(secondaryStruct.charAt(w)=='('){

                       // System.out.println("\n-------------ENTERED HERE 2---------\n");

                    a++;//keeping a count of the open brackets
                }
                else
                if(secondaryStruct.charAt(w)==')'){

                      //System.out.println("\n-------------ENTERED HERE 10---------\n");

                    a--;//cancelling each ( with its corresponding )
                }
                if (a==0){

                        System.out.println("\n-------------ENTERED HERE 11---------\n");

                    int[] newPair={index,w};
                    System.out.println("Value of pair is: "+ Arrays.toString(newPair));
                    return newPair;//gets the rule pair for the secondary bond
                }
                w++;
            }//end of while loop
                System.out.println("\n-------------ERROR---------\n");

              return pair;
        }//

    public boolean checkEndOfRNA() throws IOException, STKFileException {
        if (line.contains("//")) {
            //getNextNonBlankLine();

            rnaFile = new CreateRnaFile(location, RNAname);
            String newPrimarySequence = deleteWhiteSpace(primarySequence);

            secondaryStructure = replaceAngleBrackets(secondaryStructure);
            secondaryStructure = deleteWhiteSpace(secondaryStructure);
            String newSecondaryStructure= loosenPseudoknots(secondaryStructure);
            newPrimarySequence= replaceUnknownBases(newPrimarySequence,newSecondaryStructure);

            System.out.println("primary sequence without unknowns is: "+ newPrimarySequence);
            System.out.println("secondary bonds in dot bracket form : "+ newSecondaryStructure);

            //do checks
            if(RNAname.isBlank())
                throw new STKFileException("RNA name Not found");
            if(!newPrimarySequence.matches("(A*|G*|C*|U*)*")) {
                //System.out.println("==================================================================================================================Weird chararcter found: "+getWeirdBase(newPrimarySequence));
                throw new STKFileException("Primary sequence contains letters other than A, C, G, U");

            }
                //System.out.println("Primary sequence contains letters other than A, C, G, U, N");
            if(newPrimarySequence.length()!=newSecondaryStructure.length())
            {
                System.out.println("primary sequence length "+newPrimarySequence.length());
                System.out.println("secondary sequence length "+ newSecondaryStructure.length());
                throw new STKFileException("Primary sequence and secondary sequence have different lengths");
            }

            rnaFile.writeToFile(newPrimarySequence, newSecondaryStructure);

            return true;
        }
        else return false;
    }
}

package compression;


import compression.coding.ArithmeticEncoder;
import compression.coding.BitSizeOnlyArithmeticEncoder;
import compression.data.Dataset;
import compression.data.TrainingDataset;
import compression.grammar.RNAWithStructure;
import compression.model.AdaptiveRuleProbModel;
import compression.model.RuleProbModel;
import compression.model.SemiAdaptiveRuleProbModel;
import compression.model.StaticRuleProbModel;
import compression.samplegrammars.SampleGrammar;
import compression.util.AllGrammars;
import compression.util.CSVFile;
import org.leibnizcenter.cfg.rule.Rule;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Main class for compression experiments
 */
public class Compressions {


    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd-HH-mm-ss";
    public static final DateTimeFormatter DATE_TIME_FOMATTER
                      = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {


        System.out.println(Arrays.toString(args));
        if (args.length < 4) {
             // just to have names
            System.out.println("Compressions <dataset> <training-dataset> <with-NCR> <with-Hairpin-one> <grammar1> <grammar2> ...");
            System.out.println("\t where <dataset> is the name of a subfolder in datasets ");
            System.out.println("\t where <training-dataset> is the name for the rule probabilities for the static model ");
            System.out.println("\t where <with-NCR> [true|false] whether or not to include rules for noncanonical base pairs ");
            System.out.println("\t where <with-Hairpin-one> [true|false] whether or not to include rules for hairpin size 1 (only for some grammars)");
            System.out.println("\t where <grammar1>, ... is a list of grammars from " + AllGrammars.allGrammarNames());
            System.exit(98);
        }



        Dataset dataset = new Dataset(args[0]);
        TrainingDataset trainingDataset = new TrainingDataset(args[1]);
        boolean withNonCanonicalRules = Boolean.parseBoolean(args[2]);
        boolean withHairpinLengthOne = Boolean.parseBoolean(args[3]);
        List<SampleGrammar> listOfGrammars =
                AllGrammars.getGrammarsFromCmdLine(args, 4, withNonCanonicalRules, withHairpinLengthOne);

        System.out.println("dataset = " + dataset);
        System.out.println("trainingDataset = " + trainingDataset);
        System.out.println("listOfGrammars = " + listOfGrammars.stream().map(G -> G.getName()).collect(Collectors.joining(", ")));
        System.out.println("withNonCanonicalRules = " + withNonCanonicalRules);
        System.out.println("withHairpinLengthOne = " + withHairpinLengthOne);



        // Construct CSV headers

        List<String> grammarHeader = new ArrayList<>();
        //List<String> modelsHeader = new ArrayList<>();
        grammarHeader.add("File Name");
        //grammarHeader.add("");
        //modelsHeader.add("");
        grammarHeader.add("File Size");
        //modelsHeader.add("");
        //grammarHeader.add("");



        for (SampleGrammar grammar : listOfGrammars) {
            grammarHeader.add(grammar.getName()+" STATIC");
            grammarHeader.add(grammar.getName()+" SEMIADAPTIVE");
            grammarHeader.add(grammar.getName()+" ADAPTIVE");

            //modelsHeader.add("STATIC MODEL");
            //modelsHeader.add("ADAPTIVE MODEL");
        }

        String filename = "compression-ratios-"
                + dataset.name
                + "-training-data-" + trainingDataset.name
                + "-withNCR-" + withNonCanonicalRules
                + "-" + DATE_TIME_FOMATTER.format(LocalDateTime.now());
        CSVFile out = new CSVFile(new File(filename + ".csv"), grammarHeader);
        // Print all parameters to a file
        PrintWriter paramsOut = new PrintWriter(filename + ".txt");
        // Print actual call
        paramsOut.println(Compressions.class.getName() + " " + String.join(" ", args));
        paramsOut.println();
        paramsOut.println("dataset = " + dataset);
        paramsOut.println("trainingDataset = " + trainingDataset);
        paramsOut.println("listOfGrammars = " + listOfGrammars.stream().map(G -> G.getName()).collect(Collectors.joining(", ")));
        paramsOut.println("withNonCanonicalRules = " + withNonCanonicalRules);
        paramsOut.println("withHairpinLengthOne = " + withHairpinLengthOne);
        paramsOut.close();


        //System.out.println(grammarHeader);
        //out.appendRow(modelsHeader);

        String RNA = "";
        String DB = "";


        for (RNAWithStructure RNAWS : dataset) {

            List<String> cells = new ArrayList<>();
            cells.add(RNAWS.name+"");//name of file

            cells.add(String.format("%d",RNAWS.getNumberOfBases())+"");
            for (SampleGrammar G : listOfGrammars) {
                //System.gc(); // Added to avoid seeing heap grow huge
                //System.out.println("String Before Encoding");
                //System.out.println(RNAWS);
                //compression using static prob model
                Map<Rule, Double> ruleProbs = G.readRuleProbs(trainingDataset.getRuleProbsForGrammar(G));
//                    G.setAssignProbs(ruleProbs);
                    //System.out.println("Assign probabilities is: "+ G.getAssignProbs());
                //G.setAssignProbs(G.getAssignProbs());
                //System.out.println("Grammar is: "+ G.getName());
                //System.out.println("Got Here!!!!!!!!!!");
                //System.out.println(file.toString());
                //System.out.println(String.valueOf(Files.size(Paths.get(file.toString()))));

                ///////////***************compression using static model
                ArithmeticEncoder AE = new BitSizeOnlyArithmeticEncoder();
                RuleProbModel RPMStatic = new StaticRuleProbModel(G.getGrammar(), ruleProbs);
                GenericRNAEncoderForPrecision GRAStatic = new GenericRNAEncoderForPrecision(RPMStatic, AE, G.getGrammar(), G.getStartSymbol());
                int encodedLength = GRAStatic.getPrecisionForRNACode(RNAWS);

               // System.out.print("ENCODED LENGTH IS"+encodedLength + "\t");
                cells.add(encodedLength + "");

                ///////////*************compression using SemiAdaptiveRuleProbModel
                ArithmeticEncoder AE3 = new BitSizeOnlyArithmeticEncoder();
                RuleProbModel RPMSemiAdaptive = new SemiAdaptiveRuleProbModel(G.getGrammar(), G.getStartSymbol(), RNAWS);
                GenericRNAEncoderForPrecision GRASemiAdaptive = new GenericRNAEncoderForPrecision(RPMSemiAdaptive, AE3, G.getGrammar(), G.getStartSymbol());
                 encodedLength = GRASemiAdaptive.getPrecisionForRNACode(RNAWS);

                // System.out.print("ENCODED LENGTH IS"+encodedLength + "\t");
                cells.add(encodedLength + "");

                ///////////*************compression using adaptive model
                ArithmeticEncoder AE2 = new BitSizeOnlyArithmeticEncoder();
                RuleProbModel RPMAdaptive = new AdaptiveRuleProbModel(G.getGrammar());
               // GenericRNAEncoder GRAdaptive = new GenericRNAEncoder(RPMAdaptive, AE2, G.getGrammar(), G.getStartSymbol());
                GenericRNAEncoderForPrecision GRAdaptive = new GenericRNAEncoderForPrecision(RPMAdaptive, AE2, G.getGrammar(), G.getStartSymbol());
                //String encodedStringAdaptive = GRAdaptive.encodeRNA(RNAWS);
                encodedLength = GRAdaptive.getPrecisionForRNACode(RNAWS);
               //System.out.print("ENCODED LENGTH IS"+encodedLength + "\t");
                cells.add(encodedLength + "");
                //System.out.println("Length of cells is: " +cells.size());


            }
            out.appendRow(cells);
            System.out.println();

        }

    }
}

package compression.structureprediction;

import compression.Compressions;
import compression.data.Dataset;
import compression.grammar.RNAWithStructure;
import compression.samplegrammars.SampleGrammar;
import compression.util.AllGrammars;
import compression.util.CSVFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PPVNSensitivityWriter {
    public static void main(String[] args) throws Exception {

        Map<String, SampleGrammar> ALL_GRAMMARS;
        System.out.println(Arrays.toString(args));
        if (args.length < 4) {
            System.out.println("PPVNSensitivityWriter <realDataset> <Predicted-dataset> <with-NCR> <grammar");
            System.out.println("\t where <dataset> is the name of a subfolder in datasets from trusted source ");
            System.out.println("\t where <Predicted-dataset> is the subfolder in datasets for the predicted rna secondary structure");
            System.out.println("\t where <with-NCR> [true|false] whether or not to include rules for noncanonical base pairs ");
            System.out.println("\t where <grammar> ... is a grammars from " + AllGrammars.allGrammarNames());
            System.exit(98);
        }
        System.out.println("GOT HERE!!");

        Dataset realDataset = new Dataset(args[0]);//args[0] this is the dataset from the data set from the trusted source
        Dataset PredictedDataset = new Dataset(args[1]);//args[1] this the  predicted data set
        String trainingDataset = args[2];
        boolean withNonCanonicalRules = Boolean.parseBoolean(args[3]);
        String grammarName= args[4];
        String PredictionProgram=args[5];

        List<String> grammarHeader = new ArrayList<>();
        //List<String> modelsHeader = new ArrayList<>();
        grammarHeader.add("File Name");


            grammarHeader.add( " No Of Pairs in Real Structure");
            grammarHeader.add( " No Of Pairs in Predicted Structure");
            grammarHeader.add( " false Positive");
            grammarHeader.add( " false Negative");

        CSVFile out = new CSVFile(
                new File("ppv-sensitivity-"
                        + realDataset.name
                        + "-training-data-" + trainingDataset
                        + "-withNCR-" + withNonCanonicalRules
                        + "-grammar-"+grammarName+"-predictor-" +PredictionProgram+ Compressions.DATE_TIME_FOMATTER.format(LocalDateTime.now()) + ".csv"),
                grammarHeader);

        for (RNAWithStructure RNAWS : realDataset) {


            List<String> cells = new ArrayList<>();
            System.out.println(RNAWS.name);
            cells.add(RNAWS.name);//name of file

            PPVNSensitivity ppvnSensitivity= new PPVNSensitivity(RNAWS, PredictedDataset.getRNA(RNAWS.name).secondaryStructure);
            cells.add(ppvnSensitivity.getNumberOfPairsReal()+"");
            cells.add(ppvnSensitivity.getNumberOfPairsPredicted()+"");
            cells.add(ppvnSensitivity.getFalsePositive()+"");
            cells.add(ppvnSensitivity.getFalseNegative()+"");
            out.appendRow(cells);
        }

    }
}

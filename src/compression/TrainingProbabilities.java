package compression;

import compression.data.TrainingDataset;
import compression.samplegrammars.SampleGrammar;
import compression.util.AllGrammars;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Main class for computing rule probabilities from rule counts.
 */
public class TrainingProbabilities {

    public static void main(String[] args) throws Exception {

        System.out.println(Arrays.toString(args));
        if (args.length < 3) {
            // just to have names
            System.out.println("TrainingProbabilities <training-data> <with-NCR> <with-Hairpin-one> <grammar1> <grammar2> ...");
            System.out.println("\t where <training-dataset> is the name for the rule probabilities ");
            System.out.println("\t where <with-NCR> [true|false] whether or not to include rules for noncanonical base pairs ");
            System.out.println("\t where <with-Hairpin-one> [true|false] whether or not to include rules for hairpin size 1 (only for some grammars)");
            System.out.println("\t where <grammar1>, ... is a list of grammars from " + AllGrammars.allGrammarNames());
            System.exit(98);
        }

        TrainingDataset trainingDataset = new TrainingDataset(args[0]);
        boolean withNonCanonicalRules = Boolean.parseBoolean(args[1]);
        boolean withHairpinLengthOne = Boolean.parseBoolean(args[2]);
        List<SampleGrammar> listOfGrammars = AllGrammars.getGrammarsFromCmdLine(
                args, 3, withNonCanonicalRules, withHairpinLengthOne);

        for (SampleGrammar grammar : listOfGrammars) {
            File file = trainingDataset.getRuleProbsForGrammar(grammar);
            // System.gc(); // Added to avoid seeing heap grow huge
            grammar.writeRuleProbs(file,
                    grammar.computeRulesToProbs(
                            grammar.readRuleCounts(
                                    trainingDataset.getRuleCountsForGrammar(grammar)
                            )
                    )
            );

            System.out.println("Displayed SUCCESSFULLY");
        }
    }
}

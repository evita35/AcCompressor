package compression;


import compression.data.Dataset;
import compression.grammar.RNAWithStructure;
import compression.samplegrammars.SampleGrammar;
import compression.util.AllGrammars;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.rule.Rule;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main class to test correctness of Java Earler parser
 */

public class EarleyParserHarness {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        System.out.println(Arrays.toString(args));
        if (args.length < 4) {
             // just to have names
            System.out.println("EarleyParserHarness <dataset> <with-NCR> <with-Hairpin-one> <grammar1> <grammar2> ...");
            System.out.println("\t where <dataset> is the name of a subfolder in datasets ");
            System.out.println("\t where <with-NCR> [true|false] whether or not to include rules for noncanonical base pairs ");
            System.out.println("\t where <with-Hairpin-one> [true|false] whether or not to include rules for hairpin size 1 (only for some grammars)");
            System.out.println("\t where <grammar1>, ... is a list of grammars from " + AllGrammars.allGrammarNames());
            System.exit(98);
        }



        Dataset dataset = new Dataset(args[0]);
        boolean withNonCanonicalRules = Boolean.parseBoolean(args[1]);
        boolean withHairpinLengthOne = Boolean.parseBoolean(args[2]);
        List<SampleGrammar> listOfGrammars =
                AllGrammars.getGrammarsFromCmdLine(args, 3, withNonCanonicalRules, withHairpinLengthOne);


        System.out.println("dataset = " + dataset);
        System.out.println("listOfGrammars = " + listOfGrammars.stream().map(G -> G.getName()).collect(Collectors.joining(", ")));
        System.out.println("withNonCanonicalRules = " + withNonCanonicalRules);
        System.out.println("withHairpinLengthOne = " + withHairpinLengthOne);



        for (RNAWithStructure RNAWS : dataset) {

            System.out.println("RNAWS = " + RNAWS);
            System.out.println("RNAWS.getNumberOfBases() = " + RNAWS.getNumberOfBases());
            for (SampleGrammar G : listOfGrammars) {
                System.out.println("G.getName() = " + G.getName());
                GenericRNAEncoderForPrecision encoder = new GenericRNAEncoderForPrecision(
                        null, null, G.getGrammar(), G.getStartSymbol());
                List<Rule> rules = encoder.leftmostDerivationFor(RNAWS);
                // Remove the first rule since this one is always <start> -> S from Earley parser
                rules.remove(0);
//                System.out.println("rules = " + rules);
                GenericRNADecoder decoder = new GenericRNADecoder(
                        null, null, G.getGrammar(), G.getStartSymbol());
                RNAWithStructure decoded = decoder.decodeFromRules(rules);
                System.out.println("decoded = " + decoded);
                if (!RNAWS.equals(decoded)) {
                    System.err.println("DECODED STRUCTURE DIFFERS!!!");
                    System.exit(1);
                }
            }
            System.out.println();
        }

    }

}

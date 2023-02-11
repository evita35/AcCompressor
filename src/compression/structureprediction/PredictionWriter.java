package compression.structureprediction;

import compression.Compressions;
import compression.data.Dataset;
import compression.data.TrainingDataset;
import compression.samplegrammars.LeftmostDerivation;
import compression.samplegrammars.SampleGrammar;
import compression.util.AllGrammars;
import compression.util.CSVFile;
import org.leibnizcenter.cfg.algebra.semiring.dbl.LogSemiring;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.terminal.Terminal;
import org.leibnizcenter.cfg.earleyparser.ParseTree;
import org.leibnizcenter.cfg.earleyparser.Parser;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;
import org.leibnizcenter.cfg.token.Token;

import java.io.File;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import compression.grammar.IgnoringSecondPartPairOfChar;
import compression.grammar.IgnoringSecondPartPairOfCharTerminal;
import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import compression.grammar.RNAWithStructure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 *
 * Currently based on broken {@link Parser}; don't use for predictions.
 */
@Deprecated
public class PredictionWriter {

    public static Grammar<IgnoringSecondPartPairOfChar> convertSampleGrammar(SampleGrammar sampleGrammar, final Map<Rule, Double> ruleProbs) {
        // Use same nonterminals, but map terminals
        Grammar<PairOfChar> G = sampleGrammar.getGrammar();
        Map<Terminal<PairOfChar>, Terminal<IgnoringSecondPartPairOfChar>> terminalMap = new HashMap<>();
        for (Terminal<PairOfChar> terminal : G.terminals) {
            PairOfCharTerminal t = (PairOfCharTerminal) terminal;
            terminalMap.put(terminal, new IgnoringSecondPartPairOfChar(t.getChars().getPry(), t.getChars().getSec()).asTerminal());
        }
        Grammar.Builder<IgnoringSecondPartPairOfChar> builder = new Grammar.Builder<>(G.name);
        builder.withSemiring(LogSemiring.get());
        for (Rule rule : G.getAllRules()) {
            builder.addRule(ruleProbs.get(rule), rule.left,
                    Arrays.stream(rule.right)
                            .map(category -> {
                                if (Category.isTerminal(category)) {
                                    //noinspection unchecked
                                    return terminalMap.get((Terminal<PairOfChar>) category);
                                } else {
                                    return category;
                                }
                            }).toArray(Category[]::new));
        }
        return builder.build();
    }

    public static List<Token<IgnoringSecondPartPairOfChar>> convertTokens(List<Token<PairOfChar>> tokens) {
        return tokens.stream().map(
                        pairOfCharToken ->
                                new IgnoringSecondPartPairOfChar(
                                        pairOfCharToken.obj.getPry(),
                                        pairOfCharToken.obj.getSec()
                                ).asToken())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {

        System.out.println(Arrays.toString(args));
        if (args.length < 4) {
            System.out.println("ViterbiPredictor <dataset> <training-dataset> <with-NCR> <grammar1> <grammar2> ...");
            System.out.println("\t where <dataset> is the name of a subfolder in datasets ");
            System.out.println("\t where <training-dataset> is the name for the rule probabilities for the static model ");
            System.out.println("\t where <with-NCR> [true|false] whether or not to include rules for noncanonical base pairs ");
            System.out.println("\t where <grammar1>, ... is a list of grammars from " + AllGrammars.allGrammarNames());
            System.exit(98);
        }

        Dataset dataset = new Dataset(args[0]);
        TrainingDataset trainingDataset = new TrainingDataset(args[1]);
        boolean withNonCanonicalRules = Boolean.parseBoolean(args[2]);
        List<SampleGrammar> listOfGrammars = AllGrammars.getGrammarsFromCmdLine(args, 3,withNonCanonicalRules, false);

        System.out.println("dataset = " + dataset);
        System.out.println("trainingDataset = " + trainingDataset);
        System.out.println("listOfGrammars = " + listOfGrammars.stream().map(G -> G.getName()).collect(Collectors.joining(", ")));
        System.out.println("withNonCanonicalRules = " + withNonCanonicalRules);


        List<String> grammarHeader = new ArrayList<>();
        //List<String> modelsHeader = new ArrayList<>();
        grammarHeader.add("File Name");

        for (SampleGrammar grammar : listOfGrammars) {
            grammarHeader.add(grammar.getName() + " No Of Pairs in Real Structure");
            grammarHeader.add(grammar.getName() + " No Of Pairs in Predicted Structure");
            grammarHeader.add(grammar.getName() + " false Positive");
            grammarHeader.add(grammar.getName() + " false Negative");

            //modelsHeader.add("STATIC MODEL");
            //modelsHeader.add("ADAPTIVE MODEL");
        }

        CSVFile out = new CSVFile(
                new File("ppv-sensitivity-"
                        + dataset.name
                        + "-training-data-" + trainingDataset.name
                        + "-withNCR-" + withNonCanonicalRules
                        + "-" + Compressions.DATE_TIME_FOMATTER.format(LocalDateTime.now()) + ".csv"),
                grammarHeader);


        for (RNAWithStructure RNAWS : dataset) {


            List<String> cells = new ArrayList<>();
            System.out.println(RNAWS.name);
            cells.add(RNAWS.name);//name of file

            List<Token<IgnoringSecondPartPairOfChar>> tokens = convertTokens(RNAWS.asTokens());

            for (SampleGrammar G : listOfGrammars) {
//				System.gc();
                //System.out.println("String Before Encoding");
                //System.out.println(RNAWS);
                //compression using static prob model
                Map<Rule, Double> ruleProbs = G.readRuleProbs(trainingDataset.getRuleProbsForGrammar(G));
                //System.out.println("Assign probabilities is: "+ G.getAssignProbs());

                Grammar<IgnoringSecondPartPairOfChar> predictionGrammar = convertSampleGrammar(G, ruleProbs);
                Parser<IgnoringSecondPartPairOfChar> parser = new Parser<>(predictionGrammar);
                //System.out.println("tokens = " + tokens+ "start ="+ startSymbol);
                ParseTree der = parser.getViterbiParse(G.getStartSymbol(), tokens);

                final StringBuilder builder = new StringBuilder();
                LeftmostDerivation.traverseParseTree(der, node -> {
                    if (Category.isTerminal(node.getCategory())) {
                        IgnoringSecondPartPairOfCharTerminal t = (IgnoringSecondPartPairOfCharTerminal) node.getCategory();
                        builder.append(t.getChars().getSec());
                    }
                });

                String predictedSecondaryStructure = builder.toString();//

                CreateRNAFile RF = new CreateRNAFile("C:\\Users\\USER\\Documents\\GitHub\\clone\\clone\\datasets\\Earley-SecStrucPrediction-BenchMarkDataSet-rule-probs-dowell-mixed80-G6Bound-withNCR-true", RNAWS.name);
                RF.writeToFile(RNAWS.primaryStructure, predictedSecondaryStructure);


            }

        }
    }
}

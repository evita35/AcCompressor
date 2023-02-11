package compression.structureprediction;

import compression.Compressions;
import compression.samplegrammars.LeftmostDerivation;
import compression.samplegrammars.SampleGrammar;
import compression.data.Dataset;
import compression.data.TrainingDataset;
import compression.grammar.IgnoringSecondPartPairOfChar;
import compression.grammar.IgnoringSecondPartPairOfCharTerminal;
import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import compression.grammar.RNAWithStructure;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 *
 * DO NOT USE.
 * This is built on top of the broken stochastic parser in {@link Parser}
 * and will silently produce nonsensical predictions.
 *
 */
@Deprecated
public class ViterbiPredictor {

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

	public static void main(String[] args) throws Exception{
		Map<String, SampleGrammar> ALL_GRAMMARS;
		System.out.println(Arrays.toString(args));
		if (args.length < 4) {
			System.out.println("ViterbiPredictor <dataset> <training-dataset> <with-NCR> <grammar1> <grammar2> ...");
			System.out.println("\t where <dataset> is the name of a subfolder in datasets ");
			System.out.println("\t where <training-dataset> is the name for the rule probabilities for the static model ");
			System.out.println("\t where <with-NCR> [true|false] whether or not to include rules for noncanonical base pairs ");
			System.out.println("\t where <grammar1>, ... is a list of grammars from " + AllGrammars.allGrammarNames());
			//System.exit(98);
		}
		System.out.println("GOT HERE!!");

		Dataset dataset = new Dataset(args[0]);
        TrainingDataset trainingDataset = new TrainingDataset(args[1]);
        boolean withNonCanonicalRules = Boolean.parseBoolean(args[2]);

		ALL_GRAMMARS = AllGrammars.allGrammars(withNonCanonicalRules, false);
		System.out.println("GOT HERE!!");

		List<SampleGrammar> listOfGrammars = new ArrayList<>();
		System.out.println(Arrays.toString(args));

		System.out.println("GOT HERE!!");

		if ("ALL".equalsIgnoreCase(args[3])) {
			listOfGrammars.addAll(ALL_GRAMMARS.values());
		} else {
			for (int i = 3; i < args.length; i++) {
				final String arg = args[i];
				if (ALL_GRAMMARS.containsKey(arg)) {
					listOfGrammars.add(ALL_GRAMMARS.get(arg));
				} else {
					System.err.println("Don't know grammar " + arg);
				}
			}
		}
		System.out.println("GOT HERE!!");

		if (listOfGrammars.size() == 0) {
			System.out.println("No valid grammars given");
			System.exit(97);
		}


		System.out.println("dataset = " + dataset);
		System.out.println("trainingDataset = " + trainingDataset);
		System.out.println("listOfGrammars = " + listOfGrammars.stream().map(G -> G.getName()).collect(Collectors.joining(", ")));
		System.out.println("withNonCanonicalRules = " + withNonCanonicalRules);


		List<String> grammarHeader = new ArrayList<>();
		//List<String> modelsHeader = new ArrayList<>();
		grammarHeader.add("File Name");

		for (SampleGrammar grammar : listOfGrammars) {
			grammarHeader.add(grammar.getName()+" No Of Pairs in Real Structure");
			grammarHeader.add(grammar.getName()+" No Of Pairs in Predicted Structure");
			grammarHeader.add(grammar.getName()+" false Positive");
			grammarHeader.add(grammar.getName()+" false Negative");

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
//				System.out.println("predictionGrammar = " + predictionGrammar);
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

				CreateRNAFile RF= new CreateRNAFile("C:\\Users\\USER\\Documents\\GitHub\\clone\\clone\\datasets",RNAWS.name);
				RF.writeToFile(RNAWS.primaryStructure,predictedSecondaryStructure);

				PPVNSensitivity ppvnSensitivity= new PPVNSensitivity(RNAWS, predictedSecondaryStructure);
				cells.add(ppvnSensitivity.getNumberOfPairsReal()+"");
				cells.add(ppvnSensitivity.getNumberOfPairsPredicted()+"");
				cells.add(ppvnSensitivity.getFalsePositive()+"");
				cells.add(ppvnSensitivity.getFalseNegative()+"");
				System.out.println("RNA.primaryStructure        = " + RNAWS.primaryStructure);
				System.out.println("predictedSecondaryStructure = " + predictedSecondaryStructure);
				System.out.println("RNA.secondaryStructure      = " + RNAWS.secondaryStructure);

				System.out.println("number of Pairs in Real Structure      = " + ppvnSensitivity.getNumberOfPairsReal());
				System.out.println("number of Pairs in predicted Structure      = " + ppvnSensitivity.getNumberOfPairsPredicted());
				System.out.println("false Positve      = " + ppvnSensitivity.getFalsePositive());
				System.out.println("false negative      = " + ppvnSensitivity.getFalseNegative());
				System.out.println("common pairs " + ppvnSensitivity.returnCommonPairs());

				System.out.println("LENGTH OF CELLS IS: "+ cells.size());
			}

			System.out.println("LENGTH OF CELLS IS: "+ cells.size());
			out.appendRow(cells);
			System.out.println();
		}

	}
}

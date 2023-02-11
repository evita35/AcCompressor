package compression;

import compression.data.TrainingDataset;
import compression.samplegrammars.SampleGrammar;
import compression.util.AllGrammars;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Main class for computing rule frequencies.
 */
public class Training {

	public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd-HH-mm-ss";
	public static final DateTimeFormatter DATE_TIME_FOMATTER
			= DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {

		System.out.println(Arrays.toString(args));
		if (args.length < 3) {
			System.out.println("Training <training-dataset> <with-NCR> <with-Hairpin-one> <grammar1> <grammar2> ...");
			System.out.println("\t where <training-dataset> is the name for the rule probabilities for the static model ");
			System.out.println("\t where <with-NCR> [true|false] whether or not to include rules for noncanonical base pairs ");
			System.out.println("\t where <with-Hairpin-one> [true|false] whether or not to include rules for hairpin size 1 (only for some grammars)");
			System.out.println("\t where <grammar1>, ... is a list of grammars from ");
			System.out.println("\t " + AllGrammars.allGrammarNames());
			System.exit(98);
		}


		TrainingDataset trainingDataset = new TrainingDataset(args[0]);
		boolean withNonCanonicalRules = Boolean.parseBoolean(args[1]);
		boolean withHairpinLengthOne = Boolean.parseBoolean(args[2]);
		List<SampleGrammar> listOfGrammars = AllGrammars.getGrammarsFromCmdLine(
				args, 3, withNonCanonicalRules, withHairpinLengthOne);

		for (SampleGrammar grammar : listOfGrammars) {
			grammar.writeRuleCounts(
					trainingDataset.getRuleCountsForGrammar(grammar),
					grammar.computeRuleCounts(trainingDataset));

			System.out.println("WRITTEN SUCCESSFULLY");
		}

	}
}

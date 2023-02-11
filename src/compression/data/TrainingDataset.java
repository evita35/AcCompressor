package compression.data;

import compression.LocalConfig;
import compression.samplegrammars.SampleGrammar;

import java.io.File;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class TrainingDataset extends Dataset {
	public final String name;

	public TrainingDataset(final String name) {
		super(name);
		this.name = name;
	}


	public File getRuleProbsForGrammar(SampleGrammar grammar) {
		return new File(LocalConfig.GIT_ROOT
				+ "/src/compression/samplegrammars/"
				+ "rule-probs-" + name + "-" + grammar.getName()
				+ "-withNCR-" + grammar.isWithNoncanonicalRules() + ".txt");
	}

	public File getRuleCountsForGrammar(SampleGrammar grammar) {
		return new File(LocalConfig.GIT_ROOT
				+ "/src/compression/samplegrammars/"
				+ "rule-counts-" + name + "-" + grammar.getName()
				+ "-withNCR-" + grammar.isWithNoncanonicalRules() + ".txt");
	}

	@Override
	public String toString() {
		return "TrainingDataset(" +
				"name='" + name + '\'' +
				')';
	}
}

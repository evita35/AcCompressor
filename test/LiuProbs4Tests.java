import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import compression.samplegrammars.LiuGrammar;
import compression.samplegrammars.SampleGrammar;
import org.junit.Assert;
import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Sebastian Wild (wild@liverpool.ac.uk)
 */
public class LiuProbs4Tests {

	SampleGrammar Liu = new LiuGrammar(false);

	NonTerminal S = NonTerminal.of("S");
	NonTerminal T = NonTerminal.of("T");

	PairOfCharTerminal ao = new PairOfChar('A', '(').asTerminal();
	PairOfCharTerminal co = new PairOfChar('C', '(').asTerminal();
	PairOfCharTerminal go = new PairOfChar('G', '(').asTerminal();
	PairOfCharTerminal uo = new PairOfChar('U', '(').asTerminal();
	PairOfCharTerminal ac = new PairOfChar('A', ')').asTerminal();
	PairOfCharTerminal cc = new PairOfChar('C', ')').asTerminal();
	PairOfCharTerminal gc = new PairOfChar('G', ')').asTerminal();
	PairOfCharTerminal uc = new PairOfChar('U', ')').asTerminal();
	PairOfCharTerminal au = new PairOfChar('A', '.').asTerminal();
	PairOfCharTerminal cu = new PairOfChar('C', '.').asTerminal();
	PairOfCharTerminal gu = new PairOfChar('G', '.').asTerminal();
	PairOfCharTerminal uu = new PairOfChar('U', '.').asTerminal();

	ExpressionSemiring semiring = Liu.getGrammar().semiring;

	public Map<Rule, Double> LiuEtAlRuleProbs() {

		HashMap<Rule, Double> res = new HashMap<>();

		res.put(Rule.create(semiring, 0.5, S, T, S), 0.66);
		res.put(Rule.create(semiring, 0.5, S, T), 0.34);
		res.put(Rule.create(semiring, 0.1, T, ao, S, uc), 0.071602);
		res.put(Rule.create(semiring, 0.1, T, uo, S, ac), 0.094385);
		res.put(Rule.create(semiring, 0.1, T, co, S, gc), 0.144020);
		res.put(Rule.create(semiring, 0.1, T, go, S, cc), 0.113914);
		res.put(Rule.create(semiring, 0.1, T, uo, S, gc), 0.026851);
		res.put(Rule.create(semiring, 0.1, T, go, S, uc), 0.017901);
		res.put(Rule.create(semiring, 0.1, T, au), 0.183076);
		res.put(Rule.create(semiring, 0.1, T, cu), 0.087876);
		res.put(Rule.create(semiring, 0.1, T, gu), 0.101709);
		res.put(Rule.create(semiring, 0.1, T, uu), 0.158666);

		return res;
	}
	public Map<Rule, Double> UniformRuleProbs() {
		HashMap<Rule, Double> res = new HashMap<>();

		res.put(Rule.create(semiring, 0.5, S, T, S), 0.5);
		res.put(Rule.create(semiring, 0.5, S, T), 0.5);
		res.put(Rule.create(semiring, 0.1, T, ao, S, uc), 0.1);
		res.put(Rule.create(semiring, 0.1, T, uo, S, ac), 0.1);
		res.put(Rule.create(semiring, 0.1, T, co, S, gc), 0.1);
		res.put(Rule.create(semiring, 0.1, T, go, S, cc), 0.1);
		res.put(Rule.create(semiring, 0.1, T, uo, S, gc), 0.1);
		res.put(Rule.create(semiring, 0.1, T, go, S, uc), 0.1);
		res.put(Rule.create(semiring, 0.1, T, au), 0.1);
		res.put(Rule.create(semiring, 0.1, T, cu), 0.1);
		res.put(Rule.create(semiring, 0.1, T, gu), 0.1);
		res.put(Rule.create(semiring, 0.1, T, uu), 0.1);

		return res;
	}


}

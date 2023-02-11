import compression.coding.BigDecimalInterval;
import compression.coding.Interval;
import compression.model.AdaptiveRuleProbModel;
import compression.samplegrammars.LiuGrammar;
import compression.samplegrammars.SampleGrammar;
import compression.grammar.PairOfChar;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class AdaptiveRuleProbModelTest extends TestCase {

	BigDecimal fractionWithScale(int numerator, int denominator, int scale) {
		BigDecimal n = BigDecimal.valueOf(numerator);
		BigDecimal d = BigDecimal.valueOf(denominator);
		return n.divide(d, scale,
				RoundingMode.DOWN);
	}

	@Test
	public void testAdaptiveModel() {

		int scale = 30;

		SampleGrammar liuGrammar = new LiuGrammar(false);

		Grammar<PairOfChar> G = liuGrammar.getGrammar();
		Set<NonTerminal> nonTerminals = G.getNonTerminals();
//		System.out.println("nonTerminals = " + nonTerminals);
		NonTerminal S = NonTerminal.of("S");
		NonTerminal T = NonTerminal.of("T");
		Assert.assertTrue(nonTerminals.contains(S));
		Assert.assertTrue(nonTerminals.contains(T));
		Assert.assertTrue(G.containsRules(S));
		Assert.assertTrue(G.containsRules(T));

		Rule[] SRules = G.getRules(S).toArray(Rule[]::new);
		Rule[] TRules = G.getRules(T).toArray(Rule[]::new);
		Assert.assertEquals(2, SRules.length);
		Assert.assertEquals(10, TRules.length);

		System.out.println("SRules = " + Arrays.toString(SRules));
		System.out.println("TRules = " + Arrays.toString(TRules));

		AdaptiveRuleProbModel model = new AdaptiveRuleProbModel(G, scale);


		Interval I = model.getIntervalFor(SRules[0]);
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.valueOf(0.5)), I);
		I = model.getIntervalFor(SRules[0]);
		I = model.getIntervalFor(SRules[1]);
		I = model.getIntervalFor(SRules[0]);
		I = model.getIntervalFor(SRules[1]);
		I = model.getIntervalFor(SRules[0]);
		// Have used 3x rule 0, so count 4
		// Have used 2x rule 1, so count 3
		// Should see rule 0 mapped to [0,4/7)
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.ZERO, fractionWithScale(4, 7, scale)), I);

		I = model.getIntervalFor(TRules[6]);
		Assert.assertEquals(new BigDecimalInterval(6.0/10, 1.0/10), I);
		I = model.getIntervalFor(TRules[6]);
		Assert.assertEquals(new BigDecimalInterval(
				fractionWithScale(6,11,scale),
				fractionWithScale(2,11,scale)), I);
		I = model.getIntervalFor(TRules[6]);
		Assert.assertEquals(new BigDecimalInterval(
				fractionWithScale(6,12,scale),
				fractionWithScale(3,12,scale)), I);
		I = model.getIntervalFor(TRules[6]);
		Assert.assertEquals(new BigDecimalInterval(
				fractionWithScale(6,13,scale),
				fractionWithScale(4,13,scale)), I);


		// Test decoding
		AdaptiveRuleProbModel model2 = new AdaptiveRuleProbModel(G, scale);
		// simulate decoding from I
		List<Interval> Is = model2.getIntervalList(S);
		Is.sort(Comparator.comparing(interval -> interval.getLowerBound()));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.valueOf(0.5)), Is.get(0));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5)), Is.get(1));
		List<Category> rhs = model2.getRhsFor(new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.valueOf(0.5)), S);
		// should get back SRules[0]
		Assert.assertEquals(Arrays.asList(SRules[0].getRight()), rhs);

		// next rule again SRules[0]
		Is = model2.getIntervalList(S);
		Is.sort(Comparator.comparing(interval -> interval.getLowerBound()));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.ZERO, fractionWithScale(2,3,scale)), Is.get(0));
		Assert.assertEquals(new BigDecimalInterval(fractionWithScale(2,3,scale), fractionWithScale(1,3,scale)), Is.get(1));
		rhs = model2.getRhsFor(new BigDecimalInterval(BigDecimal.ZERO, fractionWithScale(2,3,scale)), S);
		Assert.assertEquals(Arrays.asList(SRules[0].getRight()), rhs);

		// next rule again SRules[1]
		Is = model2.getIntervalList(S);
		Is.sort(Comparator.comparing(interval -> interval.getLowerBound()));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.ZERO, fractionWithScale(3,4,scale)), Is.get(0));
		Assert.assertEquals(new BigDecimalInterval(fractionWithScale(3,4,scale), fractionWithScale(1,4,scale)), Is.get(1));
		rhs = model2.getRhsFor(new BigDecimalInterval(fractionWithScale(3,4,scale), fractionWithScale(1,4,scale)), S);
		Assert.assertEquals(Arrays.asList(SRules[1].getRight()), rhs);

	}
}

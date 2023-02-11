package compression.samplegrammars;

import compression.LocalConfig;
import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import org.leibnizcenter.cfg.algebra.semiring.dbl.CountingExprSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DowellGrammar3 implements SampleGrammar {

	private boolean withNonCanonicalRules =false;
    private Grammar<PairOfChar> G=null;

    private final NonTerminal S;
    public String name = "DowellGrammar3";

    public DowellGrammar3(boolean withNonCanonicalRules) {
		this.withNonCanonicalRules = withNonCanonicalRules;
        ExpressionSemiring semiring = CountingExprSemiring.get();//used for Grammars with assignProbs automated

        //fileName= LocalConfig.G3File;

        S = new NonTerminal("S");
        NonTerminal L = new NonTerminal("L");
        NonTerminal R = new NonTerminal("R");


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

		Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy 3")
                .withSemiring(semiring)//found in g1, g2, g3, g7 and g8
                //-----------line 1
                .addRule(S, L, S)

                //-------------line 2
                .addRule(S, ao, S, uc)
                .addRule(S, uo, S, ac)
                .addRule(S, go, S, cc)
                .addRule(S, co, S, gc)
                .addRule(S, uo, S, gc)
                .addRule(S, go, S, uc)

                .addRule(S, au, L)
                .addRule(S, cu, L)
                .addRule(S, gu, L)
                .addRule(S, uu, L)

                .addRule(S, R, au)
                .addRule(S, R, cu)
                .addRule(S, R, gu)
                .addRule(S, R, uu)

                .addRule(S, au)
                .addRule(S, cu)
                .addRule(S, gu)
                .addRule(S, uu)


                //------------line 2

                .addRule(L, ao, S, uc)
                .addRule(L, uo, S, ac)
                .addRule(L, go, S, cc)
                .addRule(L, co, S, gc)
                .addRule(L, uo, S, gc)
                .addRule(L, go, S, uc)

                .addRule(L, au, L)
                .addRule(L, cu, L)
                .addRule(L, gu, L)
                .addRule(L, uu, L)

                .addRule(R, R, au)
                .addRule(R, R, cu)
                .addRule(R, R, gu)
                .addRule(R, R, uu)

                .addRule( R, au)
                .addRule( R, cu)
                .addRule( R, gu)
                .addRule( R, uu);



        if (withNonCanonicalRules) {
			Gb
				    .addRule(S, ao, S, ac)
                    .addRule(S, ao, S, gc)
                    .addRule(S, ao, S, cc)
                    .addRule(S, co, S, ac)
                    .addRule(S, co, S, cc)
                    .addRule(S, co, S, uc)
                    .addRule(S, go, S, ac)
                    .addRule(S, go, S, gc)
                    .addRule(S, uo, S, cc)
                    .addRule(S, uo, S, uc)

                    .addRule(L, ao, S, ac)
                    .addRule(L, ao, S, gc)
                    .addRule(L, ao, S, cc)
                    .addRule(L, co, S, ac)
                    .addRule(L, co, S, cc)
                    .addRule(L, co, S, uc)
                    .addRule(L, go, S, ac)
                    .addRule(L, go, S, gc)
                    .addRule(L, uo, S, cc)
                    .addRule(L, uo, S, uc);

				// etc
				// same for L
		}

                G=Gb.build();
        /*
        System.out.println(
                "G = " + G);*/

    }

    @Override
    public boolean isWithNoncanonicalRules() {
        return withNonCanonicalRules;
    }


    //public String getFileName(){return fileName;}
    public NonTerminal getStartSymbol() {
        return S;
    }

    public Grammar<PairOfChar> getGrammar() {
        return G;
    }

    public String getName (){
        return name;
    }
}

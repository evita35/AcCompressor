package compression.samplegrammars;

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

public class DowellGrammar3Bound implements SampleGrammar {

	private boolean withNonCanonicalRules =false;
    private Grammar<PairOfChar> G=null;


    private final NonTerminal S;
    public String name = "DowellGrammar3Bound";

    public DowellGrammar3Bound(boolean withNonCanonicalRules) {
		this.withNonCanonicalRules = withNonCanonicalRules;
        ExpressionSemiring semiring = CountingExprSemiring.get();//used for Grammars with assignProbs automated

        //fileName= LocalConfig.G3File;

        S = new NonTerminal("S");
        NonTerminal L = new NonTerminal("L");
        NonTerminal R = new NonTerminal("R");
        NonTerminal B = new NonTerminal("B");
        NonTerminal U = new NonTerminal("U");


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

		Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy 3 Bound")
                .withSemiring(semiring)//found in g1, g2, g3, g7 and g8
                //-----------line 1
                .addRule(S, L, S)

                //-------------line 2
                .addRule(S, B)
                .addRule(S, U, L)
                .addRule(S, R, U)
                .addRule(S, U)

                //------------line 2

                .addRule(L, B)
                .addRule(L, U, L)

                .addRule(R, U, R)
                .addRule(R, U)

                .addRule(B, ao, S, uc)
                .addRule(B, uo, S, ac)
                .addRule(B, co, S, gc)
                .addRule(B, go, S, cc)
                .addRule(B, uo, S, gc)
                .addRule(B, go, S, uc)

                .addRule(U, au)
                .addRule(U, cu)
                .addRule(U, gu)
                .addRule(U, uu);

        if (withNonCanonicalRules) {
			Gb
				    .addRule(B, ao, S, ac)
                    .addRule(B, ao, S, gc)
                    .addRule(B, ao, S, cc)
                    .addRule(B, co, S, ac)
                    .addRule(B, co, S, cc)
                    .addRule(B, co, S, uc)
                    .addRule(B, go, S, ac)
                    .addRule(B, go, S, gc)
                    .addRule(B, uo, S, cc)
                    .addRule(B, uo, S, uc);



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

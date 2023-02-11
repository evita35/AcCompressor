/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression.samplegrammars;

import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import org.leibnizcenter.cfg.algebra.semiring.dbl.CountingExprSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;


public class DowellGrammar4 implements SampleGrammar {

    private final Grammar<PairOfChar> G;
    private final NonTerminal S;
    public String name="DowellGrammar4";

    private boolean withNonCanonicalRules=false;

    public DowellGrammar4(boolean withNonCanonicalRules) {
        this.withNonCanonicalRules=withNonCanonicalRules;
        //LogSemiring semiring = LogSemiring.get();
        ExpressionSemiring semiring = CountingExprSemiring.get();

        S = new NonTerminal("S");
        NonTerminal T = new NonTerminal("T");

        //fileName= LocalConfig.G4File;

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

        Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy 4")
                               .withSemiring(semiring) // this is the default anyways
                // .addRule(NonTerminal.START, S) // We are not supposed to add this; the library does this automatically
                .addRule(S, au, S)
                .addRule(S, cu, S)
                .addRule(S, gu, S)
                .addRule(S, uu, S)

                .addRule(S, au)
                .addRule(S, cu)
                .addRule(S, gu)
                .addRule(S, uu)
                .addRule(S, T)

                .addRule(T, ao, S, uc)
                .addRule(T, uo, S, ac)
                .addRule(T, co, S, gc)
                .addRule(T, go, S, cc)
                .addRule(T, uo, S, gc)
                .addRule(T, go, S, uc)

                .addRule(T, T, au)
                .addRule(T, T, cu)
                .addRule(T, T, gu)
                .addRule(T, T, uu)
                    /*
                .addRule(T, au)
                .addRule(T, cu)
                .addRule(T, gu)
                .addRule(T, uu)
                    */
                .addRule(T, T, ao, S, uc)
                .addRule(T, T, uo, S, ac)
                .addRule(T, T, co, S, gc)
                .addRule(T, T, go, S, cc)
                .addRule(T, T, uo, S, gc)
                .addRule(T, T, go, S, uc);

        if (withNonCanonicalRules) {
            Gb
                    .addRule(T, ao, S, ac)
                    .addRule(T, ao, S, gc)
                    .addRule(T, ao, S, cc)
                    .addRule(T, co, S, ac)
                    .addRule(T, co, S, cc)
                    .addRule(T, co, S, uc)
                    .addRule(T, go, S, ac)
                    .addRule(T, go, S, gc)
                    .addRule(T, uo, S, cc)
                    .addRule(T, uo, S, uc)

                    .addRule(T, T, ao, S, ac)
                    .addRule(T, T, ao, S, gc)
                    .addRule(T, T, ao, S, cc)
                    .addRule(T, T, co, S, ac)
                    .addRule(T, T, co, S, cc)
                    .addRule(T, T, co, S, uc)
                    .addRule(T, T, go, S, ac)
                    .addRule(T, T, go, S, gc)
                    .addRule(T, T, uo, S, cc)
                    .addRule(T, T, uo, S, uc);
            // etc
            // same for L
        }

        G=Gb.build();


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

    public String getName() {
        return name;
    }
}

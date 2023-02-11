 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression.samplegrammars;

import compression.LocalConfig;
import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.leibnizcenter.cfg.algebra.semiring.dbl.CountingExprSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

public class DowellGrammar5 implements SampleGrammar {

    private  Grammar<PairOfChar> G;
    private final NonTerminal S;
    public String name = "DowellGrammar5";

    boolean withNonCanonicalRules=false;

    public DowellGrammar5(boolean withNonCanonicalRules) {
        //LogSemiring semiring = LogSemiring.get();
        this.withNonCanonicalRules=withNonCanonicalRules;

        ExpressionSemiring semiring = CountingExprSemiring.get();

        S = new NonTerminal("S");

        //fileName= LocalConfig.G5File;

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

        Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy 5")
                               .withSemiring(semiring) // this is the default anyways
                //.addRule(NonTerminal.START, S) // We are not supposed to add this; the library does this automatically

                .addRule(S, ao, S, uc, S)
                .addRule(S, uo, S, ac, S)
                .addRule(S, co, S, gc, S)
                .addRule(S, go, S, cc, S)
                .addRule(S, uo, S, gc, S)
                .addRule(S, go, S, uc, S)
                .addRule(S, go, S, uc)
                .addRule(S, ao, S, uc)
                .addRule(S, uo, S, ac)
                .addRule(S, co, S, gc)
                .addRule(S, go, S, cc)
                .addRule(S, uo, S, gc)
                .addRule(S, go, S, uc)

                .addRule(S, au, S)
                .addRule(S, cu, S)
                .addRule(S, gu, S)
                .addRule(S, uu, S)
                .addRule(S, au)
                .addRule(S, cu)
                .addRule(S, gu)
                .addRule(S, uu);

        if (withNonCanonicalRules) {
            Gb
                    .addRule(S, ao, S, ac, S)
                    .addRule(S, ao, S, gc, S)
                    .addRule(S, ao, S, cc, S)
                    .addRule(S, co, S, ac, S)
                    .addRule(S, co, S, cc, S)
                    .addRule(S, co, S, uc, S)
                    .addRule(S, go, S, ac, S)
                    .addRule(S, go, S, gc, S)
                    .addRule(S, uo, S, cc, S)
                    .addRule(S, uo, S, uc, S)

                    .addRule(S, ao, S, ac)
                    .addRule(S, ao, S, gc)
                    .addRule(S, ao, S, cc)
                    .addRule(S, co, S, ac)
                    .addRule(S, co, S, cc)
                    .addRule(S, co, S, uc)
                    .addRule(S, go, S, ac)
                    .addRule(S, go, S, gc)
                    .addRule(S, uo, S, cc)
                    .addRule(S, uo, S, uc);
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

    @Override
    public NonTerminal getStartSymbol() {
        return S;
    }

    @Override
    public Grammar<PairOfChar> getGrammar() {
        return G;
    }

    @Override
    public String getName (){
        return name;
    }
}

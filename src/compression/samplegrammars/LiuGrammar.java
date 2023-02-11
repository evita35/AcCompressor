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

/**
 * Grammar from Liu et al 2008 with epsilon rules removed.
 */
public class LiuGrammar implements SampleGrammar {

    private final Grammar<PairOfChar> G;
    private final NonTerminal S;
    public String name="LiuGrammar";

    boolean withNonCanonicalRules;

    public LiuGrammar(boolean withNonCanonicalRules) {
        this.withNonCanonicalRules= withNonCanonicalRules;

        ExpressionSemiring semiring = CountingExprSemiring.get();
        S = new NonTerminal("S");
        NonTerminal T = new NonTerminal("T");


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

        //NON TERMINALS FOR NON CANONICAL RULES
        NonTerminal Paa = new NonTerminal("Paa");
        NonTerminal Pac = new NonTerminal("Pac");
        NonTerminal Pag= new NonTerminal("Pag");
        NonTerminal Pca = new NonTerminal("Pca");
        NonTerminal Pcc = new NonTerminal("Pcc");
        NonTerminal Pcu = new NonTerminal("Pcu");
        NonTerminal Pga = new NonTerminal("Pga");
        NonTerminal Pgg = new NonTerminal("Pgg");
        NonTerminal Puc = new NonTerminal("Puc");
        NonTerminal Puu = new NonTerminal("Puu");
        //NON TERMINALS FOR NON CANONICAL RULES


        Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("LiuGrammar")
                             .withSemiring(semiring) // this is the default anyways
                // .addRule(NonTerminal.START, S) // We are not supposed to add this; the library does this automatically
                .addRule(S, T, S)
                .addRule(S, T)
                .addRule(T, ao, S, uc)
                .addRule(T, uo, S, ac)
                .addRule(T, co, S, gc)
                .addRule(T, go, S, cc)
                .addRule(T, uo, S, gc)
                .addRule(T, go, S, uc)
                /* .addRule(S, ao, uc)
                .addRule(S, uo, ac)
                .addRule(S, co, gc)
                .addRule(S, go, cc)
                .addRule(S, uo, gc)
                .addRule(S, go, uc)*/
                .addRule(T, au)
                .addRule(T, cu)
                .addRule(T, gu)
                .addRule(T, uu);

            if (withNonCanonicalRules) {
                Gb
                        .addRule(T, ao, S, ac)
                        .addRule(T, ao, S, cc)
                        .addRule(T, ao, S, gc)
                        .addRule(T, co, S, ac)
                        .addRule(T, co, S, cc)
                        .addRule(T, co, S, uc)
                        .addRule(T, go, S, ac)
                        .addRule(T, go, S, gc)
                        .addRule(T, uo, S, cc)
                        .addRule(T, uo, S, uc);
            }

                    G = Gb.build();

    }

    @Override
    public boolean isWithNoncanonicalRules() {
        return withNonCanonicalRules;
    }


    public NonTerminal getStartSymbol() {
        return S;
    }

    public Grammar<PairOfChar> getGrammar() {
        return G;
    }

    @Override
    public String getName() {
        return name;
    }
}

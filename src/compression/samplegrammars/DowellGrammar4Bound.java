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
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DowellGrammar4Bound implements SampleGrammar {

    private final Grammar<PairOfChar> G;
    private final NonTerminal S;
    public String name="DowellGrammar4Bound";

    private boolean withNonCanonicalRules=false;

    public DowellGrammar4Bound(boolean withNonCanonicalRules) {
        this.withNonCanonicalRules=withNonCanonicalRules;
        //LogSemiring semiring = LogSemiring.get();
        ExpressionSemiring semiring = CountingExprSemiring.get();

        S = new NonTerminal("S");
        //NonTerminal T = new NonTerminal("T");
        NonTerminal Q = new NonTerminal("Q");//replaced T with Q to maintain lexicographical ordering
        //since Q appears on RHS of some rules in which S is on the LHS
        NonTerminal B = new NonTerminal("B");
        NonTerminal U = new NonTerminal("U");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");

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

        Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy 4 bound")
                               .withSemiring(semiring) // this is the default anyways
                // .addRule(NonTerminal.START, S) // We are not supposed to add this; the library does this automatically
                .addRule(S, U, S)

                .addRule(S, U)

                .addRule(S, Q)

                .addRule(U, au)
                .addRule(U, cu)
                .addRule(U, gu)
                .addRule(U, uu)

                .addRule(B, ao, S, uc)
                .addRule(B, uo, S, ac)
                .addRule(B, co, S, gc)
                .addRule(B, go, S, cc)
                .addRule(B, uo, S, gc)
                .addRule(B, go, S, uc)

                .addRule(Q, B)
                .addRule(Q, B, D)

                .addRule(D, C)
                .addRule(D, C, D)

                .addRule(C, B)
                .addRule(C, U);


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

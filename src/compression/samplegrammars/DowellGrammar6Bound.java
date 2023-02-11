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

public class DowellGrammar6Bound implements SampleGrammar {

    private  Grammar<PairOfChar> G;
    private final NonTerminal S;
    public String name = "DowellGrammar6Bound";

    boolean withNonCanonicalRules;
    boolean withHairpinLengthOne;


    public DowellGrammar6Bound(boolean withNonCanonicalRules, boolean withHairpinLengthOne) {
        //LogSemiring semiring = LogSemiring.get();
        this.withNonCanonicalRules=withNonCanonicalRules;
        this.withHairpinLengthOne = withHairpinLengthOne;

        ExpressionSemiring semiring = CountingExprSemiring.get();

        //fileName = LocalConfig.G6File;

        S = new NonTerminal("S");
        NonTerminal T = new NonTerminal("T");
        NonTerminal M = new NonTerminal("M");
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

        Grammar.Builder Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy G6")
                .withSemiring(semiring) // this is the default anyways
                // .addRule(NonTerminal.START, S) // We are not supposed to add this; the library does this automatically
                .addRule(S, T, S)
                .addRule(S, T)

                .addRule(T, U)
                .addRule(T, B)

                .addRule(U, au)
                .addRule(U, cu)
                .addRule(U, gu)
                .addRule(U, uu)

/*
                .addRule(F, T, S)
                .addRule(F, B)
*/
                .addRule(M, T, S); //DECIDED TO REPLACE F WITH M SO THAT M IS ORDERED
                if (withHairpinLengthOne)
                    Gb.addRule(M, T); // not in DE04
                Gb.addRule(M, B)//LEXICOGRAPHICALLY USING TREE MAP,

                .addRule(B, ao, M, uc)
                .addRule(B, uo, M, ac)
                .addRule(B, co, M, gc)
                .addRule(B, go, M, cc)
                .addRule(B, uo, M, gc)
                .addRule(B, go, M, uc);


        if (withNonCanonicalRules) {
            Gb
                    .addRule(B, ao, M, ac)
                    .addRule(B, ao, M, gc)
                    .addRule(B, ao, M, cc)
                    .addRule(B, co, M, ac)
                    .addRule(B, co, M, cc)
                    .addRule(B, co, M, uc)
                    .addRule(B, go, M, ac)
                    .addRule(B, go, M, gc)
                    .addRule(B, uo, M, cc)
                    .addRule(B, uo, M, uc);

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

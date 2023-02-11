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

public class DowellGrammar6BoundMirror implements SampleGrammar {

    private  Grammar<PairOfChar> G;
    private final NonTerminal S;
    public String name = "DowellGrammar6BoundMirror";

    boolean withNonCanonicalRules;
    boolean withHairpinLengthOne;

    public DowellGrammar6BoundMirror(boolean withNonCanonicalRules, boolean withHairpinLengthOne) {
        //LogSemiring semiring = LogSemiring.get();
        this.withNonCanonicalRules=withNonCanonicalRules;

        ExpressionSemiring semiring = CountingExprSemiring.get();

        //fileName = LocalConfig.G6File;

        S = new NonTerminal("S");
        NonTerminal T = new NonTerminal("T");
        NonTerminal F = new NonTerminal("M");
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

        Grammar.Builder Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy G6 mirrored")
                .withSemiring(semiring) // this is the default anyways
                // .addRule(NonTerminal.START, S) // We are not supposed to add this; the library does this automatically
                .addRule(S, S, T)
                .addRule(S, T)

                .addRule(T, U)
                .addRule(T, B)

                .addRule(U, au)
                .addRule(U, cu)
                .addRule(U, gu)
                .addRule(U, uu)


                .addRule(F, S, T)
                .addRule(F, B);
                if (withHairpinLengthOne)
                    Gb.addRule(F, T); // not in DE04

                Gb.addRule(B, ao, F, uc)
                .addRule(B, uo, F, ac)
                .addRule(B, co, F, gc)
                .addRule(B, go, F, cc)
                .addRule(B, uo, F, gc)
                .addRule(B, go, F, uc);


        if (withNonCanonicalRules) {
            Gb
                    .addRule(B, ao, F, ac)
                    .addRule(B, ao, F, gc)
                    .addRule(B, ao, F, cc)
                    .addRule(B, co, F, ac)
                    .addRule(B, co, F, cc)
                    .addRule(B, co, F, uc)
                    .addRule(B, go, F, ac)
                    .addRule(B, go, F, gc)
                    .addRule(B, uo, F, cc)
                    .addRule(B, uo, F, uc);

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

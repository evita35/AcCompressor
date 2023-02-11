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

public class DowellGrammar1Bound implements SampleGrammar {

    private final Grammar<PairOfChar> G;
    public Map<String, NonTerminal> stringNonTerminalMap;
    public Map< String, Category> stringCategoryMap;

    boolean withNonCanonicalRules;

    private final NonTerminal S;
    private String name = "DowellGrammar1Bound";
    //private String fileName;

    public DowellGrammar1Bound(boolean withNonCanonicalRules) {

        this.withNonCanonicalRules=withNonCanonicalRules;

        ExpressionSemiring semiring = CountingExprSemiring.get();//used for Grammars with assignProbs automated

        S = new NonTerminal("S");
        NonTerminal U = new NonTerminal("U");
        NonTerminal C = new NonTerminal("C");
        NonTerminal X = new NonTerminal("X");
        NonTerminal B = new NonTerminal("B");

        //fileName= LocalConfig.G1File;
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
/*
        stringNonTerminalMap = new HashMap<>();
        stringCategoryMap = new HashMap<>();

        stringNonTerminalMap.put("S", S);

        stringCategoryMap.put("S", S);
        stringCategoryMap.put( "<A|(>", ao);

        stringCategoryMap.put("<A|)>",ac);
        stringCategoryMap.put("<A|.>",au);
        stringCategoryMap.put("<U|(>",uo);
        stringCategoryMap.put("<U|)>",uc);
        stringCategoryMap.put("<U|.>",uu);
        stringCategoryMap.put("<G|(>",go);
        stringCategoryMap.put("<G|)>",gc);
        stringCategoryMap.put("<G|.>",gu);
        stringCategoryMap.put("<C|(>",co);
        stringCategoryMap.put("<C|)>",cc);
        stringCategoryMap.put("<C|.>",cu);
*/

        Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy 1")
                .withSemiring(semiring)//found in g1, g2, g3, g7 and g8
                //-----------line 1
                .addRule(S, C)

                //-------------line 2
                .addRule(S, C, X)
                .addRule(S, U, S)
                .addRule(S, U, S, X)

                .addRule(C, B)
                .addRule(C, U)

                .addRule(X, U, X)
                .addRule(X, S, X)
                .addRule(X, U)
                .addRule(X,S)

                .addRule(B, ao, S, uc)
                .addRule(B, uo, S, ac)
                .addRule(B, go, S, cc)
                .addRule(B, co, S, gc)
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

        }
                 G= Gb.build();

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

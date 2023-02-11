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

public class DowellGrammar1 implements SampleGrammar {

    private final Grammar<PairOfChar> G;
    public Map<String, NonTerminal> stringNonTerminalMap;
    public Map< String, Category> stringCategoryMap;

    boolean withNonCanonicalRules;

    private final NonTerminal S;
    private String name = "DowellGrammar1";
    //private String fileName;

    public DowellGrammar1(boolean withNonCanonicalRules) {

        this.withNonCanonicalRules=withNonCanonicalRules;

        ExpressionSemiring semiring = CountingExprSemiring.get();//used for Grammars with assignProbs automated

        S = new NonTerminal("S");

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
                .addRule(S, S, S)

                //-------------line 2

                .addRule(S, ao, S, uc)
                .addRule(S, uo, S, ac)
                .addRule(S, go, S, cc)
                .addRule(S, co, S, gc)
                .addRule(S, uo, S, gc)
                .addRule(S, go, S, uc)

                .addRule(S, au)
                .addRule(S, cu)
                .addRule(S, gu)
                .addRule(S, uu)

                .addRule(S, au, S)
                .addRule(S, cu, S)
                .addRule(S, gu, S)
                .addRule(S, uu, S)

                .addRule(S, S, au)
                .addRule(S, S, cu)
                .addRule(S, S, gu)
                .addRule(S, S, uu);

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
                    .addRule(S, uo, S, uc);

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

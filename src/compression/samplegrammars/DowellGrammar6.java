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

public class DowellGrammar6 implements SampleGrammar {

    private  Grammar<PairOfChar> G;
    private final NonTerminal S;
    public String name = "DowellGrammar6";

    boolean withNonCanonicalRules;
    boolean withHairpinLengthOne;

    public DowellGrammar6(boolean withNonCanonicalRules, boolean withHairpinLengthOne) {
        //LogSemiring semiring = LogSemiring.get();
        this.withNonCanonicalRules = withNonCanonicalRules;
        this.withHairpinLengthOne = withHairpinLengthOne;

        ExpressionSemiring semiring = CountingExprSemiring.get();

        //fileName = LocalConfig.G6File;

        S = new NonTerminal("S");
        NonTerminal T = new NonTerminal("T");
        NonTerminal F = new NonTerminal("F");

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
                .addRule(T, ao, F, uc)
                .addRule(T, uo, F, ac)
                .addRule(T, co, F, gc)
                .addRule(T, go, F, cc)
                .addRule(T, uo, F, gc)
                .addRule(T, go, F, uc)
                .addRule(T, au)
                .addRule(T, cu)
                .addRule(T, gu)
                .addRule(T, uu)
                
                .addRule(F, T, S);
                if (withHairpinLengthOne)
                    Gb.addRule(F, T); // this rule does not exist in DE04's G6
                Gb.addRule(F, ao, F, uc)
                .addRule(F, uo, F, ac)
                .addRule(F, co, F, gc)
                .addRule(F, go, F, cc)
                .addRule(F, uo, F, gc)
                .addRule(F, go, F, uc);

        if (withNonCanonicalRules) {
            Gb
                    .addRule(T, ao, F, ac)
                    .addRule(T, ao, F, gc)
                    .addRule(T, ao, F, cc)
                    .addRule(T, co, F, ac)
                    .addRule(T, co, F, cc)
                    .addRule(T, co, F, uc)
                    .addRule(T, go, F, ac)
                    .addRule(T, go, F, gc)
                    .addRule(T, uo, F, cc)
                    .addRule(T, uo, F, uc)

                    .addRule(F, ao, F, ac)
                    .addRule(F, ao, F, gc)
                    .addRule(F, ao, F, cc)
                    .addRule(F, co, F, ac)
                    .addRule(F, co, F, cc)
                    .addRule(F, co, F, uc)
                    .addRule(F, go, F, ac)
                    .addRule(F, go, F, gc)
                    .addRule(F, uo, F, cc)
                    .addRule(F, uo, F, uc);
            // same for L
        }

        G=Gb.build();

        /*
        System.out.println(
                "G = " + G);*/
        /*
        assignProbs = new HashMap<>();
        //assignProbs.put(Rule.create(semiring, 1.0, NonTerminal.START, S), 1.0); // should not be needed

        assignProbs.put(Rule.create(semiring, 0.5, S, T, S), 0.8149625580540456);
        assignProbs.put(Rule.create(semiring, 0.5, S, T), 0.1850374419459545);
        assignProbs.put(Rule.create(semiring, (double)1.0/8, F, T, S), 0.06184753921682212);
        
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(ao), Category.nonTerminal("F"), Category.terminal(uc)), 0.022898653275053544);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(uo), Category.nonTerminal("F"), Category.terminal(ac)), 0.01717318134409758);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(co), Category.nonTerminal("F"), Category.terminal(gc)), 0.03430207562041704);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(go), Category.nonTerminal("F"), Category.terminal(cc)), 0.05632270045620656);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(uo), Category.nonTerminal("F"), Category.terminal(gc)), 0.009525700432321399);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(go), Category.nonTerminal("F"), Category.terminal(uc)), 0.016453889698330428);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(au)), 0.3485145918424217);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(cu)), 0.11679718911473636);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(gu)), 0.19566772955629333);
        assignProbs.put(Rule.create(semiring, 0.1, T, Category.terminal(uu)), 0.18234428866012214);
        
        
        assignProbs.put(Rule.create(semiring, (double)1.0/8, F, Category.terminal(ao), Category.nonTerminal("F"), Category.terminal(uc)), 0.09840240647285403);
        assignProbs.put(Rule.create(semiring, (double)1.0/8, F, Category.terminal(uo), Category.nonTerminal("F"), Category.terminal(ac)), 0.10201355612007802);
        assignProbs.put(Rule.create(semiring, (double)1.0/8, F, Category.terminal(co), Category.nonTerminal("F"), Category.terminal(gc)), 0.22721170123166473);
        assignProbs.put(Rule.create(semiring, (double)1.0/8, F, Category.terminal(go), Category.nonTerminal("F"), Category.terminal(cc)), 0.2182658900818659);
        assignProbs.put(Rule.create(semiring, (double)1.0/8, F, Category.terminal(uo), Category.nonTerminal("F"), Category.terminal(gc)), 0.06396097668956849);
        assignProbs.put(Rule.create(semiring, (double)1.0/8, F, Category.terminal(go), Category.nonTerminal("F"), Category.terminal(uc)), 0.05145480775280891);
        assignProbs.put(Rule.create(semiring, (double)1.0/8, F, T), 0.00316046664832095);

         */
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

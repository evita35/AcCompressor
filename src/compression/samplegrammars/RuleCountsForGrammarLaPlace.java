/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression.samplegrammars;

import compression.data.Dataset;
import compression.grammar.RNAWithStructure;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compute the frequency of each rule in a grammar for a given dataset,
 * starting with 1 (LaPlace smoothing)
 */
public final class RuleCountsForGrammarLaPlace {


    Map<Rule, Integer> RulesToFrequency;
    List<Rule> rules;
    Grammar grammar;

    int noOfRNASamples;

    public RuleCountsForGrammarLaPlace(Grammar g, NonTerminal S, Dataset dataset) {
        RulesToFrequency = new HashMap<>();
        LeftmostDerivation APC = new LeftmostDerivation(g, S);
        noOfRNASamples=dataset.getSize();

        grammar = g;

        this.initialiseRTF(new ArrayList<>(grammar.getAllRules()));

        for (RNAWithStructure RNAWS : dataset) {
            try {
                rules = APC.rules(RNAWS);
                incrementMap(rules);
                System.out.println("file: "+  RNAWS.name +" ran correctly");
            } catch (RuntimeException runtimeException){
                System.out.println(RNAWS.name +" HAS PARSING ISSUE");
                throw runtimeException;
            }
        }
    }

    public void incrementMap(List<Rule> listOfRules) {
        listOfRules.forEach((rule) -> {
            if (rule.left.name.equals(NonTerminal.START.name)) {
                //do nothing for start symbol
            } else {
                RulesToFrequency.replace(rule, RulesToFrequency.get(rule) + 1);
            }
        });

    }

    public void initialiseRTF(List<Rule> rules) {
        // initialise to 1 to avoid 0 probabilities
        rules.forEach((rule) -> RulesToFrequency.put(rule, 1));
    }


    public Map<Rule, Integer> getRuleToCounts()  {
        return RulesToFrequency;
    }


}

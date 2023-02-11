/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression.samplegrammars;

import compression.grammar.RNAWithStructure;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compute the frequency of each rule in a grammar for a given RNA,
 * starting with 0 (no LaPlace smoothing since used only on one RNA)
 */
public final class RuleProbsForGrammarSemiAdaptive
{

    Map<Rule, Integer> RulesToFrequency;
    List<Rule> rules;
    Grammar grammar;

    public RuleProbsForGrammarSemiAdaptive(Grammar g, NonTerminal S, RNAWithStructure rna) {
        RulesToFrequency = new HashMap<>();
        grammar = g;

        this.initialiseRTF(new ArrayList<>(grammar.getAllRules()));

        LeftmostDerivation APC = new LeftmostDerivation(grammar, S);
        rules = APC.rules(rna);
        incrementMap(rules);
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
        rules.forEach((rule) -> {
            RulesToFrequency.put(rule, 0);
        });
    }


    public Map<Rule, Double> getRulesToProbs() {

        Map<Rule, Double> RulesToProbs = new HashMap<>();

        List<NonTerminal> NTList = new ArrayList<>(grammar.getNonTerminals());
        Map<NonTerminal, Double> NonTerminalFreq= new HashMap<>();

        for(NonTerminal NT: NTList)
            NonTerminalFreq.put(NT, 0.0);

        for (Rule rule : RulesToFrequency.keySet()) {
            NonTerminalFreq.replace(rule.left, NonTerminalFreq.get(rule.left) + RulesToFrequency.get(rule));
        }

        for (Rule rule : RulesToFrequency.keySet()) {
            if (NonTerminalFreq.get(rule.left) == 0.0 || RulesToFrequency.get(rule) == 0) {
                RulesToProbs.put(rule, 0.0);
            } else {
                RulesToProbs.put(rule, (double) RulesToFrequency.get(rule) / NonTerminalFreq.get(rule.left));
            }
        }

        return RulesToProbs;
    }


}

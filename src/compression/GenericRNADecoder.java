/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression;

import compression.coding.ArithmeticDecoder;
import compression.coding.Interval;
import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import compression.grammar.RNAGrammar;
import compression.grammar.RNAWithStructure;
import compression.model.RuleProbModel;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.rule.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class implementing our decoder for RNA with structure.
 */
public class GenericRNADecoder {

    ArithmeticDecoder acDecoder;
    RuleProbModel model;
    org.leibnizcenter.cfg.grammar.Grammar<PairOfChar> grammar;

   
    NonTerminal startSymbol;

    List<Category> leftmostDerivation;


    public GenericRNADecoder(RuleProbModel model, ArithmeticDecoder acDecoder, org.leibnizcenter.cfg.grammar.Grammar<PairOfChar> grammar, NonTerminal startSymbol) {
        this.acDecoder = acDecoder;
        this.model = model;
        this.grammar = grammar;
        this.startSymbol= startSymbol;
        leftmostDerivation = new LinkedList<>();
    }

    /** decode from given ArithmeticDecoder */
    public RNAWithStructure decode() {
        leftmostDerivation.clear();
        NonTerminal leftmostNT = startSymbol;
        leftmostDerivation.add(leftmostNT);
        while (derivationHasNonTerminal()) {
            final List<Interval> options = model.getIntervalList(leftmostNT);
            Interval interval = acDecoder.decodeNext(options);
            List<Category> rhs = model.getRhsFor(interval, leftmostNT);
            replaceNonTerminalInDerivation(rhs);
            leftmostNT = RNAGrammar.getLeftMostNT(leftmostDerivation);
        }
        return getRNAString(decodeCategoryList(leftmostDerivation));
    }

    /** decode from given list of rules; mostly for debugging */
    public RNAWithStructure decodeFromRules(Iterable<Rule> rules) {
        leftmostDerivation.clear();
        leftmostDerivation.add(startSymbol);
        NonTerminal leftmostNT = RNAGrammar.getLeftMostNT(leftmostDerivation);
        for (Rule rule : rules) {
            if (!derivationHasNonTerminal()) throw new IllegalArgumentException();
            if (!rule.left.equals(leftmostNT))
                throw new IllegalArgumentException();
            List<Category> rhs = Arrays.asList(rule.getRight());
            replaceNonTerminalInDerivation(rhs);
            leftmostNT = RNAGrammar.getLeftMostNT(leftmostDerivation);
        }
        if (derivationHasNonTerminal()) throw new IllegalArgumentException();
        return getRNAString(decodeCategoryList(leftmostDerivation));

    }


    boolean derivationHasNonTerminal() {
        for (Category cat : leftmostDerivation) {
            if (!Category.isTerminal(cat)) {
                return true;
            }
        }
        return false;
    }

    void replaceNonTerminalInDerivation(List<Category> rhs) {
        
        for (Category cat : leftmostDerivation) {
            if (!Category.isTerminal(cat)) {
                int ind = leftmostDerivation.indexOf(cat);
                leftmostDerivation.remove(ind);
                for (Category category : rhs) {
                    leftmostDerivation.add(ind, category);
                    ind++;
                }
               // System.out.println("VALUE OF LEFT TO RIGHT DERIVATION IN METHOD REPLACENonterminal is: "+ LeftToRightDer);
                return;
            }
        }
    }

    ArrayList<PairOfChar> decodeCategoryList(List<Category> catList) {
        ArrayList<PairOfChar> pairOfCharList = new ArrayList<>();
        for (Category cat : catList) {
            if (!Category.isTerminal(cat)) throw new IllegalArgumentException("only terminals allowed here");
            pairOfCharList.add(((PairOfCharTerminal) cat).getChars());
        }
        return pairOfCharList;
    }

    public RNAWithStructure getRNAString(ArrayList<PairOfChar> POCList) {
        StringBuilder primary = new StringBuilder(POCList.size()),
                secondary = new StringBuilder(POCList.size());
        for (PairOfChar POC : POCList) {
            primary.append(POC.getPry());
            secondary.append(POC.getSec());
        }
        return new RNAWithStructure(primary.toString(), secondary.toString());
    }

}

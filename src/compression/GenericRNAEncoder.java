package compression;

import compression.coding.ArithmeticEncoder;
import compression.model.RuleProbModel;
import compression.samplegrammars.LeftmostDerivation;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import compression.grammar.*;

import java.util.List;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class GenericRNAEncoder {

    ArithmeticEncoder acEncoder;
    RuleProbModel model;
    Grammar<PairOfChar> grammar;
    NonTerminal startSymbol;

    public GenericRNAEncoder(RuleProbModel model, ArithmeticEncoder acEncoder, Grammar<PairOfChar> grammar, NonTerminal startSymbol) {
        this.acEncoder = acEncoder;
        this.model = model;
        this.grammar = grammar;
        this.startSymbol = startSymbol;
    }

    public List<Rule> leftmostDerivationFor(RNAWithStructure RNA){
        return new LeftmostDerivation(this.grammar, this.startSymbol).rules(RNA);
    }


    public String encodeRNA(RNAWithStructure RNA) {
        for (Rule rule : leftmostDerivationFor(RNA)) {
            acEncoder.encodeNext(model.getIntervalFor(rule));
        }
        return acEncoder.getFinalEncoding();
    }

}

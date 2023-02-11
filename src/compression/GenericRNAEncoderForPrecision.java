package compression;

import compression.coding.ArithmeticEncoder;
import compression.grammar.PairOfChar;
import compression.grammar.RNAWithStructure;
import compression.model.RuleProbModel;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;


public class GenericRNAEncoderForPrecision extends GenericRNAEncoder {

    public GenericRNAEncoderForPrecision(RuleProbModel model, ArithmeticEncoder acEncoder, Grammar<PairOfChar> grammar, NonTerminal startSymbol) {
        super(model, acEncoder, grammar, startSymbol);
    }

    public int getPrecisionForRNACode(RNAWithStructure RNA) {
        for (Rule rule : leftmostDerivationFor(RNA )) {
            acEncoder.encodeNext(model.getIntervalFor(rule));
        }
        return acEncoder.getFinalPrecision();
    }

}

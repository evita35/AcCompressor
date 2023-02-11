/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import compression.model.AdaptiveRuleProbModel;
import compression.coding.ArithmeticEncoder;
import compression.coding.ExactArithmeticDecoder;
import compression.coding.ExactArithmeticEncoder;
import compression.GenericRNADecoder;
import compression.GenericRNAEncoder;
import compression.model.RuleProbModel;
import compression.model.SemiAdaptiveRuleProbModel;
import compression.model.StaticRuleProbModel;
import compression.data.TrainingDataset;
import compression.grammar.RNAWithStructure;
import compression.samplegrammars.SampleGrammar;
import org.junit.Assert;

import java.io.IOException;

public class SampleInstance4Tests {

    SampleGrammar G;


    public SampleInstance4Tests(SampleGrammar NewG) {

        G = NewG;

    }

    public void runEncodeNDecodeStatic(RNAWithStructure rnaws, TrainingDataset tDataset) throws IOException {
        //Encoding
        ArithmeticEncoder AE = new ExactArithmeticEncoder();
        RuleProbModel RPMStatic = new StaticRuleProbModel(G.getGrammar(), G.readRuleProbs(tDataset.getRuleProbsForGrammar(G)));
        GenericRNAEncoder GRAStatic = new GenericRNAEncoder(RPMStatic, AE, G.getGrammar(), G.getStartSymbol());
        String encodedStringStatic = GRAStatic.encodeRNA(rnaws);

        //Decoding
        ExactArithmeticDecoder AD = new ExactArithmeticDecoder(encodedStringStatic);
        GenericRNADecoder GRAD = new GenericRNADecoder(RPMStatic, AD, G.getGrammar(), G.getStartSymbol());
        RNAWithStructure decoded = GRAD.decode();

        Assert.assertEquals(rnaws, decoded);
    }

    public void runEncodeNDecode4Adaptive(RNAWithStructure rnaws) {
        //Encoding
        ArithmeticEncoder AE = new ExactArithmeticEncoder();
        RuleProbModel RPMAdaptive = new AdaptiveRuleProbModel(G.getGrammar());
        GenericRNAEncoder GRAdaptive = new GenericRNAEncoder(RPMAdaptive, AE, G.getGrammar(), G.getStartSymbol());
        String encodedStringAdaptive = GRAdaptive.encodeRNA(rnaws);

        //Decoding
        RPMAdaptive = new AdaptiveRuleProbModel(G.getGrammar());//resets the Model
        ExactArithmeticDecoder AD = new ExactArithmeticDecoder(encodedStringAdaptive);
        GenericRNADecoder GRAD = new GenericRNADecoder(RPMAdaptive, AD, G.getGrammar(), G.getStartSymbol());
        RNAWithStructure decoded = GRAD.decode();

        Assert.assertEquals(rnaws, decoded);
    }
    public void runEncodeNDecode4SemiAdaptive(RNAWithStructure rnaws){
        //Encoding
        ArithmeticEncoder AE = new ExactArithmeticEncoder();
        RuleProbModel RPMSemiAdaptive = new SemiAdaptiveRuleProbModel(G.getGrammar(), G.getStartSymbol(), rnaws);
        GenericRNAEncoder GRASemiAdaptive = new GenericRNAEncoder(RPMSemiAdaptive, AE, G.getGrammar(), G.getStartSymbol());
        String encodedStringStatic = GRASemiAdaptive.encodeRNA(rnaws);

        //Decoding
        ExactArithmeticDecoder AD = new ExactArithmeticDecoder(encodedStringStatic);
        GenericRNADecoder GRAD = new GenericRNADecoder(RPMSemiAdaptive, AD, G.getGrammar(), G.getStartSymbol());
        RNAWithStructure decoded = GRAD.decode();

        Assert.assertEquals(rnaws, decoded);

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import compression.data.Dataset;
import compression.data.TrainingDataset;
import compression.grammar.RNAWithStructure;
import compression.samplegrammars.DowellGrammar1Bound;
import compression.samplegrammars.SampleGrammar;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Encode_N_Decode_CorrectlyTest {
    Dataset dataset = new Dataset("TestDataSet");
    TrainingDataset trainingDataset = new TrainingDataset("TestTrainingData");
    boolean withNonCanonicalRules = true;
    boolean withHairpinLengthOne = true;
    List<SampleGrammar> listOfGrammars = Arrays.asList(
            new DowellGrammar1Bound(withNonCanonicalRules)
    );

    @Test
    public void testCorrectness() throws IOException {
        for (SampleGrammar grammar : listOfGrammars) {
            for (RNAWithStructure RNAWS : dataset) {
                SampleInstance4Tests SI4T = new SampleInstance4Tests(grammar);

                System.out.println(RNAWS);
                SI4T.runEncodeNDecodeStatic(RNAWS, trainingDataset);
                SI4T.runEncodeNDecode4Adaptive(RNAWS);
                SI4T.runEncodeNDecode4SemiAdaptive(RNAWS);
            }
        }
    }

}

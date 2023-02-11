import compression.grammar.RNAWithStructure;
import compression.structureprediction.*;
import org.junit.Assert;
import org.junit.Test;

public class PPVNSensitivityTest {

    RNAWithStructure realRNA;
    String predictedStructure;





    @Test
    public void testPPVNSensitivity() throws Exception {
        RNAWithStructure rRNA = new RNAWithStructure("acguuguccgg","((((.)))..)");
        String pStructure = "((()...)())";
        PPVNSensitivity pNsTest= new PPVNSensitivity(rRNA,pStructure);
        Assert.assertEquals(pNsTest.returnCommonPairs(),2);
    }


    @Test
    public void testPPVNSensitivity2() throws Exception {
        RNAWithStructure rRNA = new RNAWithStructure("acguuguccggauau","((((.)))..)()()");
        String pStructure =                                            "((()...)())()..";
        PPVNSensitivity pNsTest= new PPVNSensitivity(rRNA,pStructure);
        Assert.assertEquals(pNsTest.returnCommonPairs(), 3);
        Assert.assertEquals(pNsTest.getFalsePositive(), 2);
        Assert.assertEquals(pNsTest.getFalseNegative(), 3);
        Assert.assertEquals(pNsTest.getNumberOfPairsPredicted(), 5);
        Assert.assertEquals(pNsTest.getNumberOfPairsReal(), 6);
    }
}

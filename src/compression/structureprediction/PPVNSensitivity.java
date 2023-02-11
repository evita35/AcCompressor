package compression.structureprediction;

import compression.grammar.RNAWithStructure;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PPVNSensitivity {
    RNAWithStructure realStructure;
    String predictedStructure;

    int pairsInRealStructure, pairsInPredictedStructure, falsePositive, falseNegative,commonPairs;
    public PPVNSensitivity(RNAWithStructure realStructure, String predictedStructure){
        this.realStructure=realStructure;
        this.predictedStructure=predictedStructure;
    }
    private int countPairs(RNAWithStructure rnaws){
        int count =0;
        for( int i=0; i<rnaws.getNumberOfBases(); i++)
            if(rnaws.secondaryStructure.charAt(i)=='(')
                count++;
            return  count;
    }

    private int countPairs(String predictedStructure){
        int count =0;
        for( int i=0; i<predictedStructure.length(); i++)
            if(predictedStructure.charAt(i)=='(')
                count++;
        return  count;

    }

    public int returnCommonPairs() throws Exception {return getCommonPairs(realStructure,predictedStructure);}

    private int getCommonPairs (RNAWithStructure real, String predicted) throws Exception{
        int count =0;
        int leftIndexReal, rightIndexReal,leftIndexPredicted,rightIndexPredicted;

        for( int i=0; i<real.getNumberOfBases(); i++)
            if(real.secondaryStructure.charAt(i)=='(' && predicted.charAt(i)=='(') {
                leftIndexReal=i;
                rightIndexReal=getPair(real.secondaryStructure,leftIndexReal);
                rightIndexPredicted=getPair(predicted, i);

                if(rightIndexReal==rightIndexPredicted)
                    count++;
            }
        return  count;
    }
    public int getNumberOfPairsReal(){
        return countPairs(realStructure);

    }


    public int getNumberOfPairsPredicted(){
        return countPairs(predictedStructure);
    }

    public int getFalsePositive() throws Exception{
        return getNumberOfPairsPredicted()-getCommonPairs(realStructure,predictedStructure);
    }
    public int getFalseNegative() throws Exception{
        return getNumberOfPairsReal()-getCommonPairs(realStructure,predictedStructure);
    }
    private int getPair(String secondaryStructure, int leftIndex)throws Exception{

        int a=0;
        int rightIndex=leftIndex;

        while (rightIndex<secondaryStructure.length()){

            if(secondaryStructure.charAt(rightIndex)=='('){

                a++;//keeping a count of the open brackets
            }
            else
            if(secondaryStructure.charAt(rightIndex)==')'){

                a--;//cancelling each ( with its corresponding )
            }


            if (a==0){
                return rightIndex;
            }
            rightIndex++;
        }
        throw new Exception("Can't Find closing parenthesis");
    }

}

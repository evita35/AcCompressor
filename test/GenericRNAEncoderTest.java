/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import compression.coding.ExactArithmeticEncoder;
import compression.model.StaticRuleProbModel;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.earleyparser.ParseTree;
import org.leibnizcenter.cfg.earleyparser.Parser;
import org.leibnizcenter.cfg.token.Token;

import compression.grammar.*;
import compression.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

import compression.samplegrammars.*;
import org.junit.Assert;


public class GenericRNAEncoderTest {

    String pry = "G";
    String sec = ".";
    RNAWithStructure RNAWS;
    LiuGrammar Liu;
    Parser<PairOfChar> parser;
    NonTerminal S;
    
    public GenericRNAEncoderTest() {
        RNAWS = new RNAWithStructure(pry, sec);
        Liu= new LiuGrammar(false);
        parser = new Parser<>(Liu.getGrammar());
        S = new NonTerminal("S");
    }

    @Test
    public void testTraverseParseTree() {      

        // split string into list of tokens (terminals)
        List<Token<PairOfChar>> tokens = RNAWS.asTokens();
        List<ParseTree> trees = new ArrayList<>();
        List<ParseTree> trees2 = new ArrayList<>();
        //System.out.println("startSymbol " +startSymbol);         
        //System.out.println("tokens = " + tokens);

        
        NonTerminal T = new NonTerminal("T");
        PairOfCharTerminal gu = new PairOfChar('G', '.').asTerminal();
        ParseTree der = parser.getViterbiParse(S, tokens);


        ParseTree.Leaf<PairOfChar> child4 = new ParseTree.Leaf<PairOfChar>(tokens.get(0), Category.terminal(gu));
        ParseTree.NonLeaf child3 = new ParseTree.NonLeaf(NonTerminal.of(T.name), new ArrayList<>(Collections.singletonList(child4)));
        ParseTree.NonLeaf child2 = new ParseTree.NonLeaf(NonTerminal.of(S.name), new ArrayList<>(Collections.singletonList(child3)));
        ParseTree.NonLeaf child1 = new ParseTree.NonLeaf(NonTerminal.START, new ArrayList<>(Collections.singletonList(child2)));

       
        
       
        System.out.println(child4);
        

        trees2.add(child1);
        trees2.add(child2);
        trees2.add(child3);
        trees2.add(child4);

         LeftmostDerivation.traverseParseTree(der,
                new Consumer<ParseTree>() {
                    @Override
                    public void accept(final ParseTree tree) {
                        trees.add(tree);
                    }
                });

        Assert.assertEquals(trees2, trees);
        
    
    }

    @Test
    public void testencodeRNA() {

        GenericRNAEncoder GRA = new GenericRNAEncoder(
                new StaticRuleProbModel(Liu.getGrammar(),
                        new LiuProbs4Tests().LiuEtAlRuleProbs()),
                new ExactArithmeticEncoder(), Liu.getGrammar(), S);
        String encodedString = GRA.encodeRNA(RNAWS);
        Assert.assertEquals("11100011", encodedString);
    }
}

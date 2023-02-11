package compression.model;

import compression.coding.BigDecimalInterval;
import compression.coding.Interval;
import compression.grammar.PairOfChar;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A rule probability model that uses fixed rule probabilities.
 */
public class StaticRuleProbModel implements RuleProbModel {

    /** store right hand side as list, not entire rule to avoid failed
     * lookups when used with rules of different probabilities   */
    private final Map<NonTerminal, Map<List<Category>, Interval>> ruleProbs
            = new HashMap<>();
    org.leibnizcenter.cfg.grammar.Grammar<PairOfChar> grammar;
    
    private NonTerminal nextNonTerminal;

    /** Requires the rules in G and in probs to match in probability! */
    public StaticRuleProbModel(Grammar<PairOfChar> G, Map<Rule, Double> probs) {
        grammar=G;
        //System.out.println(probs.toString());
        for (NonTerminal nonTerminal : G.getNonTerminals()) {
            ruleProbs.put(nonTerminal, new HashMap<>());
            BigDecimal left = BigDecimal.ZERO; //left stands for lower bound of Interval
            for (org.leibnizcenter.cfg.rule.Rule rule : G.getRules(nonTerminal)) {
                
                //System.out.println("VALUE OF PROBS IS: "+probs.toString());
                //System.out.println("VALUE OF RULE IS: "+rule);
                //System.out.println(rule+" "+ probs.containsKey(rule));
                
                double p = probs.get(rule);
                BigDecimal pBD = BigDecimal.valueOf(p);//
                ArrayList<Category> rhs = new ArrayList<>(Arrays.asList(rule.getRight()));
                ruleProbs.get(nonTerminal).put(rhs, new BigDecimalInterval(left, pBD));//maps each rule to its initial sub Interval of [0, 1)
                left = left.add(pBD);
            }
        }
        
       // printRuleProbs();
    }
    
    void printRuleProbs(){
        //System.out.println("PRINTING OUT RULE PROBS");
        for (NonTerminal nt: ruleProbs.keySet()){
            //System.out.println(nt);
            for(List<Category> catlist: ruleProbs.get(nt).keySet())
                System.out.println(catlist.toString()+" " +ruleProbs.get(nt).get(catlist).toString());
        }
    }
    @Override
    public List<Interval> getIntervalList(NonTerminal lhs){
        //System.out.println("VALUE OF NT IS: "+nt);
        return new ArrayList<Interval> (ruleProbs.get(lhs).values());
    }
    
    public List<Category> getRhsFor(Interval intvl, NonTerminal lhs){


            for (List<Category> rhs : ruleProbs.get(lhs).keySet()) {

                if(intvl.equals(ruleProbs.get(lhs).get(rhs))){

                    return rhs;
                }
            }
                     
        
        return null;
    }
       
    @Override
    public Interval getIntervalFor(final org.leibnizcenter.cfg.rule.Rule rule) {
       
        if(rule.getLeft().name.equalsIgnoreCase(NonTerminal.START.name)){           
            return new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.ONE, 0.0);
        }
        else
            //System.out.println(ruleProbs.get(rule.getLeft()).get(Arrays.asList(rule.getRight())));
            return ruleProbs.get(rule.getLeft()).get(Arrays.asList(rule.getRight())) ;
    }
    
    

}

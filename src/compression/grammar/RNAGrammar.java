/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression.grammar;
import java.util.List;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;

public class RNAGrammar {

    public static NonTerminal getLeftMostNT(List<Category> sententialForm) {

        for (Category cat : sententialForm) {
             //System.out.println("PRINTING RHS in getLeftMostNT " + cat.toString());
            if (!Category.isTerminal(cat)) {
                return (NonTerminal) cat;
            }
        }
        return null;
    }
    
}

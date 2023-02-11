package compression.samplegrammars;

import compression.grammar.PairOfChar;
import compression.grammar.RNAWithStructure;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.earleyparser.ParseTree;
import org.leibnizcenter.cfg.earleyparser.Parser;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;
import org.leibnizcenter.cfg.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Parse RNA with grammar {@link #G} and return list of rules used in the parse.
 */
public class LeftmostDerivation {

    Grammar<PairOfChar> G;
    NonTerminal S;

    public LeftmostDerivation(Grammar<PairOfChar> G, NonTerminal S) {
        this.G = G;
        this.S = S;
    }

    public static void traverseParseTree(ParseTree tree, Consumer<ParseTree> visit) {

        Stack<ParseTree> stackTrees = new Stack<>();//stores trees in a stack
        ParseTree currentTree;//initialises current tree
        stackTrees.push(tree);//pushes tree to stack
        List<ParseTree> children;

        while (!stackTrees.isEmpty()) {//loop through to traverse tree
            currentTree = stackTrees.pop();
            visit.accept(currentTree);
            if (!Category.isTerminal(currentTree.getCategory())) {
                children = currentTree.getChildren();//gets the list of children
                for (int i = children.size() - 1; i >= 0; i--) {
                    stackTrees.push(children.get(i));
                }
            }

        }//end while
    }


    /**
     * Create {@link Rule} object corresponding to the grammar rule
     * used at the given parse node node
     */
    private Rule ruleFor(ParseTree node) {
        List<ParseTree> children = node.getChildren();
        Category[] rhs = children.stream().map(ParseTree::getCategory).toArray(Category[]::new);
        if (node.category.equals(NonTerminal.START)) {
            return Rule.create(G.semiring, Category.nonTerminal(node.category.toString()), rhs);
        } else {
            // Need to create rule with uniform probability to compare equal to other rules
            double normProb = 1.0 / G.getRules(Category.nonTerminal(node.category.toString())).size();
            return Rule.create(G.semiring, normProb,Category.nonTerminal(node.category.toString()), rhs);
        }
    }

    /**
     * Compute the leftmost derivation for the given RNA sequence
     * using grammar {@link #G}
     */
    public List<Rule> rules(RNAWithStructure RNA) {
        Parser<PairOfChar> parser = new Parser<>(G);

        List<Token<PairOfChar>> tokens = RNA.asTokens();
        ParseTree der = parser.getViterbiParse(S, tokens);
        List<Rule> rules = new ArrayList<>();
        traverseParseTree(der, tree -> {
            if (!Category.isTerminal(tree.getCategory())) {
                rules.add(ruleFor(tree));
            }
        });
        return rules;
    }


}

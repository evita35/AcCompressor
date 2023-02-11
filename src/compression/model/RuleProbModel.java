package compression.model;

import compression.coding.Interval;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.rule.Rule;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import java.util.*;

/**
 * Interface for our general abstraction of a rule probability model.
 *
 * The model is responsible for computing the probability of each rule and
 * for selecting the right-hand side of a rule given the left-hand side and
 * a subinterval of [0,1).
 */
public interface RuleProbModel {

    /**
     * Compute the probability for the given rule and use it as the length of
     * the interval; the starting point of the interval is the total probability
     * of all rules preceding this rule.
     *
     * If the model adapts to the input, this method will change future probabilities.
     *
     * @param rule
     * @return
     */
    Interval getIntervalFor(final Rule rule);

    /**
     * Compute the list of Intervals for all rules with the given left-hand side.
     *
     * This method does not change future probabilities.
     *
     * @param lhs
     * @return
     */
    List<Interval> getIntervalList(NonTerminal lhs);

    /**
     * Selects the right-hand side of the rule with given left-hand side (lhs)
     * whose interval is the given interval.
     *
     * If the model adapts to the input, this method will change future probabilities.
     *
     * @param interval
     * @param lhs
     * @return
     */
    List<Category> getRhsFor(Interval interval, NonTerminal lhs);

}

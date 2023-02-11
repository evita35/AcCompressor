package compression.samplegrammars;

import compression.data.Dataset;
import compression.grammar.CategoryMaps;
import compression.grammar.PairOfChar;
import org.leibnizcenter.cfg.algebra.semiring.dbl.CountingExprSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public interface SampleGrammar {

    String getName();

    boolean isWithNoncanonicalRules();

    NonTerminal getStartSymbol();

    Grammar<PairOfChar> getGrammar();

    default List<Rule> getGrammarRules() {
        return new ArrayList<>(getGrammar().getAllRules());
    }

    ExpressionSemiring semiring = CountingExprSemiring.get();


    default public Map<Rule, Integer> computeRuleCounts(Dataset dataset) {
        RuleCountsForGrammarLaPlace PNG =
                new RuleCountsForGrammarLaPlace(this.getGrammar(),this.getStartSymbol(),dataset);
        return PNG.getRuleToCounts();

    }

    default public Map<Rule,Double> computeRulesToProbs(Map<Rule,Long> ruleCounts) throws Exception {
        Map<Rule, Double> RulesToProbs = new HashMap<>();
        List<NonTerminal> nonterminals = ruleCounts.keySet().stream().map(rule -> rule.getLeft()).collect(Collectors.toList());

        Map<NonTerminal, Long> NonTerminalFreq= new HashMap<>();
        for(NonTerminal NT: nonterminals)
            NonTerminalFreq.put(NT, 0l);

        for (Rule rule : ruleCounts.keySet()) {
            NonTerminalFreq.replace(rule.left, ruleCounts.get(rule) + NonTerminalFreq.get(rule.left));
        }

        for (Rule rule : ruleCounts.keySet()) {
            RulesToProbs.put(rule, (double) ruleCounts.get(rule) / NonTerminalFreq.get(rule.left));
        }
        System.out.println("GOT HERE " +RulesToProbs.toString());

        if(SampleGrammar.checkRulesToProbs(RulesToProbs, nonterminals))
            System.out.println("Probabilities are Good!");
        else
            throw new  Exception("Probabilities do not sum up to 1");

        return RulesToProbs;
    }

    public static boolean checkRulesToProbs(Map<Rule, Double> r2P) {
        List<NonTerminal> nonterminals = r2P.keySet().stream().map(rule -> rule.getLeft()).collect(Collectors.toList());
        return checkRulesToProbs(r2P, nonterminals);
    }

    public static boolean checkRulesToProbs(Map<Rule, Double> r2P, List<NonTerminal> NT) {
        Map<NonTerminal, Double> sumOfProbabilities = new HashMap<>();

        for (NonTerminal nt : NT)
            sumOfProbabilities.put(nt, 0.0);

        for (Rule rule : r2P.keySet()) {//obtains the sum of probabilities for rules with same LHS
            sumOfProbabilities.replace(rule.left, r2P.get(rule) + sumOfProbabilities.get(rule.left));
        }
        System.out.println(" SUM OF PROBABILITIES IS: " + sumOfProbabilities);
        for (NonTerminal nt : NT) {
            double sum = sumOfProbabilities.get(nt);
            if (sum < 0.999 || sum > 1.001) {
                System.out.println("SUM OF PROBABILITIES IS: " + Math.round(sum));
                return false;
            }
        }
        return true;
    }

    private static Map.Entry<Rule, Double> addToAssignProbs(String line){
        CategoryMaps CM = new CategoryMaps();
        NonTerminal nt = CM.stringNonTerminalMap.get(line.substring(0, line.indexOf('\u2192') - 1));
        // obtain the index position after the right arrow character
        int i = line.indexOf('\u2192') + 1;
        ArrayList<Category> rhs= new ArrayList<>();

        double probForRule = 0.0;
        double probForAssign = 0.0;


        while(i<line.length()){
            if(line.charAt(i)=='(')//to read terminal symbols e.g. <A\(>
            {
                probForRule=Double.parseDouble( line.substring(i+1, line.lastIndexOf(')')));
                i=line.lastIndexOf(')');
            }
            else if(line.charAt(i)=='<')
            {
                rhs.add(CM.stringCategoryMap.get(line.substring(i,i+5)));

                i+=5;
            }
            else if(line.charAt(i)==':'){
                probForAssign=Double.parseDouble(line.substring(i+1));
                i=line.length();
                break;
            }
            else if(line.charAt(i)==' ' || line.charAt(i)==')'){
                i++;
            }
            else
            {
                int r=0;


                while(line.charAt(i+r)!=' '){r++;}//used to obtain the length of the non-terminal symbol
                rhs.add(CM.stringNonTerminalMap.get(line.substring(i,i+r)));//
                i=i+r+1;

            }
        }
        Category[] catArray = rhs.toArray(new Category[0]);
        return Map.entry (Rule.create(semiring,probForRule, nt, catArray), probForAssign);

    }

    default public void writeRuleCounts(File file, Map<Rule, Integer> ruleCounts ) throws IOException {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))) {
            TreeSet<Map.Entry<Rule, Integer>> entries = new TreeSet<>(
                    Comparator.comparing(o -> o.getKey().toString()));
            entries.addAll(ruleCounts.entrySet());
            for (Map.Entry<Rule, Integer> entry : entries) {
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }
            bf.flush();
            System.out.println("Successfully wrote to the file.");
        }
    }

    default public void writeRuleProbs(File file, Map<Rule,Double> assignProbs) throws IOException {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))) {
            TreeSet<Map.Entry<Rule, Double>> entries = new TreeSet<>(
                    Comparator.comparing(o -> o.getKey().toString()));
            entries.addAll(assignProbs.entrySet());
            for (Map.Entry<Rule, Double> entry : entries) {
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }
            bf.flush();
            System.out.println("Successfully wrote to the file.");
        }
    }


    /**
     * Read rule counts from the text file
     * @param file
     * @return map of rules to counts
     * @throws IOException
     */
    default public Map<Rule,Long> readRuleCounts(File file) throws IOException {//
        Map<Rule, Long> ruleCounts = new HashMap<>();
        try (BufferedReader in = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file), "UTF-8"));){
            String line;
            while ((line = in.readLine()) != null) {
                Map.Entry<Rule, Long> entry = addToRuleCounts(line);
                ruleCounts.put(entry.getKey(), entry.getValue());
            }
            return ruleCounts;
        }
    }

    default public Map.Entry<Rule, Long> addToRuleCounts(String line){
        CategoryMaps CM = new CategoryMaps();
        NonTerminal nt= CM.stringNonTerminalMap.get(line.substring(0,line.indexOf('\u2192')-1));
        int i=line.indexOf('\u2192')+1;//obtains the index position after the right arrow character
        ArrayList<Category> rhs= new ArrayList<>();

        double probForRule = 0.0;
        long frequencyForCounts = 0L;

        while(i<line.length()){
            if(line.charAt(i)=='(')//to read terminal symbols e.g. <A\(>
            {
                probForRule=Double.parseDouble( line.substring(i+1, line.lastIndexOf(')')));
                i=line.lastIndexOf(')');
            }
            else if(line.charAt(i)=='<')
            {
                rhs.add(CM.stringCategoryMap.get(line.substring(i,i+5)));

                i+=5;
            }
            else if(line.charAt(i)==':'){
                frequencyForCounts=Long.parseLong(line.substring(i+1));
                i=line.length();
                break;
            }
            else if(line.charAt(i)==' ' || line.charAt(i)==')'){
                i++;
            }
            else
            {
                int r=0;

                while(line.charAt(i+r)!=' '){r++;}//used to obtain the length of the non-terminal symbol
                System.out.println("PRINTING OUT SUBSTRING "+line.substring(i,i+r));
                rhs.add(CM.stringNonTerminalMap.get(line.substring(i,i+r)));//
                //System.out.println(CM.stringNonTerminalMap.get(line.substring(i,i+r)));
                i=i+r+1;

            }
        }
        Category[] catArray = rhs.toArray(new Category[0]);
        return Map.entry (Rule.create(semiring,probForRule, nt, catArray), frequencyForCounts);

    }


    /**
     * Reads the rule probabilities from a file.
     *
     * @param file
     * @return a map from rules to their probabilities
     * @throws IOException
     */
    default public Map<Rule,Double> readRuleProbs(File file) throws IOException {

        Map<Rule, Double> ruleProbs = new HashMap<>();
        System.out.println("Dowell probabilities file name is: "+ file);
        PrintWriter printWriter = new PrintWriter(System.out,true);
        char aa = '\u2192';
        printWriter.println("aa = " + aa);
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), "UTF-8"));

            String line;
            while ((line = in.readLine()) != null) {
                Map.Entry<Rule, Double> entry = addToAssignProbs(line);
                ruleProbs.put(entry.getKey(),entry.getValue());
            }
            in.close();
            //System.out.println("printing assign probs"+ruleProbs);
        }
        catch (IOException e) {
            System.out.println(e);
            throw e;
        }
        if (!checkRulesToProbs(ruleProbs)) {
            throw new IllegalArgumentException("Probabilities are not correctly distributed");
        }
        return ruleProbs;
    }


}

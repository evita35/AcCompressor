package compression;

import org.leibnizcenter.cfg.rule.Rule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class PrintC {

    public static void main(String[] args) throws Exception {

        // TODO introduce proper command line interface
	    // TODO use TrainingDataset instead of file name
        File grammarProbs = new File(LocalConfig.GIT_ROOT + "/src/compression/samplegrammars/rule-probs-dowell-mixed80-"+args[1] +"-withNCR-true.txt");

        Map<String, Double> RulesWithTerminals = new HashMap<>();
        Map<String, Double> RulesWithoutTerminals = new HashMap<>();
        String grammarCode=args[0];
        FileInputStream fis = new FileInputStream(grammarProbs);

        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader in = new BufferedReader(isr);

        String line;

        while ((line = in.readLine()) != null) {
            //System.out.println("IN FIRST WHILE LOOP");
            if(checkRuleType(line)){
                Map.Entry<String, Double> entry=splitRule(line);
                RulesWithTerminals.put(entry.getKey(),entry.getValue());
            }
            else
            {
                Map.Entry<String, Double> entry=splitRule(line);
                RulesWithoutTerminals.put(entry.getKey(),entry.getValue());
            }
        }

        TreeSet<Map.Entry<String, Double>> entriesTerminals = new TreeSet<>(//treeset is for ordering
                Comparator.comparing(o -> o.getKey().toString()));
        TreeSet<Map.Entry<String, Double>> entriesWithoutTerminals = new TreeSet<>(//treeset is for ordering
                Comparator.comparing(o -> o.getKey().toString()));

        entriesTerminals.addAll(RulesWithTerminals.entrySet());
        entriesWithoutTerminals.addAll(RulesWithoutTerminals.entrySet());

        printInitialPartOfCode(getListOfNonTerminals(RulesWithoutTerminals,RulesWithTerminals),grammarCode);
        PrintC(entriesTerminals,entriesWithoutTerminals);

        PrintMap4Probs(entriesTerminals, entriesWithoutTerminals);
        printLastPartOfCode(grammarCode);
    }
    static void printLastPartOfCode(String g){
        System.out.println(" boost::shared_ptr<one_grammar> const G(\n" +
                "            new one_grammar(n, sigma, r, Sprime, p));\n" +
                "\n" +
                "    cout << *G << endl;\n" +
                "\n" +
                "    return G;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "#endif  /* _DOWELL_EDDY_"+g+"_H */\n");
    }
    static ArrayList<String> getListOfNonTerminals(Map<String,Double> rulesWithT,Map<String,Double>rulesWithoutT){
        ArrayList<String> listOfNonTerminals=new ArrayList<>();
        for(String s: rulesWithT.keySet()){
            int start = s.indexOf('\u2192') - 1;

            String lhs = s.substring(0, start);
            if(!listOfNonTerminals.contains(lhs)){
                listOfNonTerminals.add(lhs);
            }
        }

        for(String s: rulesWithoutT.keySet()){
            int start = s.indexOf('\u2192') - 1;

            String lhs = s.substring(0, start);
            if(!listOfNonTerminals.contains(lhs)){
                listOfNonTerminals.add(lhs);
            }
        }
        return listOfNonTerminals;
    }
    static Map.Entry<String, Double> splitRule(String str){

        int i=0;
        Double probability=0.0;
        String Rule="";
        while(i<str.length()){
            if(str.charAt(i)==':'){
                probability=Double.parseDouble(str.substring(i+1));
                Rule = str.substring(0,i-1);
                break;
            }
            i++;
        }
        return Map.entry(Rule,probability);
    }

    static boolean checkRuleType(String str){
        if(str.contains("<"+"")){
            return true;
        }
        else return false;
    }

    static boolean similarRules(String lhs, ArrayList<String> rhs, String previousLhs, ArrayList<String> previousRhs){
        //System.out.println("GOT HERE 0"+lhs.compareTo(previousLhs)+" "+rhs.size()+" "+ previousRhs.size()+" ");

        int index=0;
        if(lhs.compareTo(previousLhs)==0 && rhs.size()== previousRhs.size()){

            //System.out.println("GOT HERE 1");
            for (String s: rhs){
                //System.out.println("value of s is: ="+s+"= value of previous RHS is ="+previousRhs.get(index)+"= ");

                if(!s.contains("<U")&&!s.contains("<G")&&!s.contains("<C")&&!s.contains("<A")){//filtering out the terminals
                    //System.out.println("GOT HERE 2");
                    if(s.compareTo(previousRhs.get(index))!=0){//checks if the nonterminals in the rule are same
                        //System.out.println("GOT HERE 3");
                        return false;
                    }

                }

                index++;


            }
            return true;//control reaches this point for similar rules with just a single terminal on the RHS
        }
        return false;//for all other dissimilar rules
    }

    static ArrayList<String> getRhsWithoutTerminals(String str) {
        ArrayList<String> rhs = new ArrayList<>();
        int i = 0;
        while (str.charAt(i) != '0' && i < str.length()) {
            //System.out.println("IN WHILE LOOP");
            int stop;
            if (str.charAt(i) == '<') {
                System.out.println("Found a terminal in set of rules for nonTerminals");

                stop = i + 5;
                rhs.add(str.substring(i, stop));
                //System.out.println("printing out substring "+str.substring(start,stop));
                i = stop;
            }
            if (i >= str.length())
                break;
            while (str.charAt(i) == ' ') {
                i++;
                if (i >= str.length())
                    break;
            }//end inner while
            stop = i;
            while (!(str.charAt(stop) == '<' || str.charAt(stop) == '|' || str.charAt(stop) == ' ' || str.charAt(stop) == '0')) {
                stop++;
                if (stop >= str.length())
                    break;
            }
            if (i != stop)
                rhs.add(str.substring(i, stop));
            i = stop;
            //System.out.println(stop+" "+ str.length());
            if (stop >= str.length()) {
                break;
            }

        }
        return rhs;
    }
    static ArrayList<String> getRhsWithTerminals(String str){

            String lhs;
            ArrayList<String> rhs=new ArrayList<>();

        str = str.substring(0,str.lastIndexOf('(')-1);//removes the digits for prob after the rule
        //str = str.substring(0, str.length() - 5);//removes the 2 digit prob after the rule

        int start = str.indexOf('\u2192') - 1;
        lhs = str.substring(0, start);
        start += 2;
        while (str.charAt(start) != '0' && start < str.length()) {
            //System.out.println("IN WHILE LOOP");
            if (str.charAt(start) == '<') {
                rhs.add(str.substring(start, start + 5));
                start = start + 5;
            }

            if (start >= str.length()) {
                break;
            }

            if (!(str.charAt(start) == '<' || str.charAt(start) == '|' || str.charAt(start) == ' ')) {
                int stop = start;
                while (!(str.charAt(stop) == '<' || str.charAt(stop) == '|' || str.charAt(stop) == ' ')) {
                    stop++;

                }//end inner while
                rhs.add(str.substring(start, stop));
                start = stop;
            }

            start++;


        }//end outer while
        return rhs;
    }

    static void PrintC(TreeSet<Map.Entry<String,Double>> terminals, TreeSet<Map.Entry<String,Double>> nonterminals){
        ///PRINTING OUT THE DEFINITION FOR EACH RULE
        int index=0;
        String  previousLhs="";
        ArrayList<String> previousRhs=new ArrayList<>();
        for (Map.Entry<String, Double> entry : terminals) {

            String str = entry.getKey();

            ArrayList<String> rhs = new ArrayList<>();
            rhs=getRhsWithTerminals(str);
            str = str.substring(0,str.lastIndexOf('(')-1);//removes the digits for prob after the rule
            //str = str.substring(0, str.length() - 5);//removes the 2 digit prob after the rule
            int start = str.indexOf('\u2192') - 1;
            String lhs = str.substring(0, start);


            if (similarRules(lhs, rhs, previousLhs, previousRhs)){
                //do nothing
            }
            else {
                //check for  grouped rules

                System.out.print("//" + lhs + " -> ");
                for (String s : rhs) {
                    if (s.contains( "(")) {
                        System.out.print("(");
                    } else if (s.contains(")")) {
                        System.out.print(")");
                    }
                    else if (s.contains("|")){
                        System.out.print("|");
                    }
                    else
                        System.out.print(s);
                }

                System.out.println();
                if (rhs.size() > 1) {

                    for (String string : rhs)
                        if (string.contains("<") || string.contains(">")) {
                        } else
                            System.out.println("middle.push_back(" + string + ");");

                    System.out.println("one_rule r" + index + "= one_rule(" + lhs + ",  1, 1, middle,createOneBond(0,0));");
                    System.out.println("middle.clear();");
                    System.out.println();
                } else {
                    // We know have one terminal on the right hand side, and rhs.size() == 1
                    System.out.println("one_rule r" + index + "= one_rule(" + lhs + ",  1, 0, middle);");
                    System.out.println("middle.clear();");
                    System.out.println();
                }
                previousLhs=lhs;
                previousRhs=rhs;
                index++;
            }
        }

        for (Map.Entry<String, Double> entry : nonterminals) {
            String str= entry.getKey();

            ArrayList<String> rhs=new ArrayList<>();
            //int index=0;
            String lhs;
            str = str.substring(0,str.lastIndexOf('(')-1);//removes the digits for prob after the rule
            //str = str.substring(0, str.length() - 5);//removes the 2 digit prob after the rule

            //str=str.substring(0,str.length()-5);//removes the 4 characters digit prob after the rule

            int start= str.indexOf('\u2192')-1;
            lhs=str.substring(0,start);

            start+=2;
            str=str.substring(start,str.length());
            rhs=getRhsWithoutTerminals(str);


            System.out.print("//"+ lhs+" -> " );
            for(String string: rhs)
                if(string.contains("("))
                    System.out.print("(");
                else if(string.contains(")"))
                    System.out.print(")");
                else System.out.print(string);

            System.out.println();
            if(rhs.size()>1){
                for(String string: rhs){
/*
                    if(string.contains("(")||string.contains(")"))
                    {}
                    else
                        System.out.println("middle.push_back("+string+");");*/
                    if(string.contains("(")||string.contains(")"))
                    {}else
                    {

                        System.out.println("middle.push_back("+string +");");
                    }
                }
                System.out.println("one_rule r" + index + "= one_rule(" + lhs + ", 0, 0, middle);");
                System.out.println("middle.clear();");
                System.out.println();
            }else{

                System.out.println("middle.push_back("+rhs.get(0)+");");
                System.out.println("one_rule r"+index+ "= one_rule("+lhs+", 0, 0, middle);");
                System.out.println("middle.clear();");
                System.out.println();
            }
            index++;
        }
        //for S'-> S
        System.out.println("// S' -> S\n" +
                "    middle.push_back(S);\n" +
                "    one_rule r"+index+" = one_rule(Sprime, 0, 0, middle);\n" +
                "    middle.clear();\n\n");
        printInsert(index+1);//we add one for the last
        index=0;


    }
    static void printInsert(int m){
        System.out.println("set<one_rule> r;");
        for (int i=0; i<m; i++)
            System.out.println("r.insert(r"+i+");");
    }

    static void printInitialPartOfCode(ArrayList<String> Nonterminals, String GrammarCode){
        System.out.print("#ifndef DOWELL_EDDY_"+GrammarCode+"_H\n" +
                "#define DOWELL_EDDY_"+GrammarCode+"_H\n" +
                "\n" +
                "\n" +
                "#include <boost/filesystem.hpp>\n" +
                "#include <boost/filesystem/fstream.hpp>\n" +
                "#include <boost/filesystem/operations.hpp>\n" +
                "#include <iostream>\n" +
                "#include <fstream>\n" +
                "#include <boost/shared_ptr.hpp>\n" +
                "#include <sstream>\n" +
                "#include <boost/lexical_cast.hpp>\n" +
                "#include \"frontend.h\"\n" +
                "#include \"one_grammar.h\"\n" +
                "#include \"two_grammar.h\"\n" +
                "#include \"secondary_structure_one_grammar.h\"\n" +
                "#include \"rna_rna_interaction_grammar.h\"\n" +
                "#include \"utilitility.h\"\n" +
                "#include <vector>\n" +
                "\n" +
                "#include <dirent.h>\n" +
                "\n" +
                "using std::cout;\n" +
                "using std::endl;\n" +
                "using std::ostringstream;\n" +
                "using std::string;\n" +
                "using std::vector;\n" +
                "using boost::filesystem::path;\n" +
                "//using boost::filesystem::ifstream;\n" +
                "//using boost::filesystem::ofstream;\n" +
                "using boost::shared_ptr;\n" +
                "using boost::lexical_cast;\n" +
                "using namespace frontend;\n" +
                "using namespace grammar;\n" +
                "using namespace util;\n" +
                "using namespace symbols;\n" +
                "using namespace std;\n" +
                "\n" +
                "\n" +
                "\n" +
                "boost::shared_ptr<one_grammar> const createDowellEddy"+GrammarCode+"() {\n");

        System.out.println("set<terminal> sigma;\n" +
                "    const terminal a = symbols::terminal::valueOf(\"a\");\n" +
                "    const terminal g = symbols::terminal::valueOf(\"g\");\n" +
                "    const terminal c = symbols::terminal::valueOf(\"c\");\n" +
                "    const terminal u = symbols::terminal::valueOf(\"u\");\n" +
                "    sigma.insert(a);\n" +
                "    sigma.insert(c);\n" +
                "    sigma.insert(g);\n" +
                "    sigma.insert(u);\n\n");

        System.out.println("nonterminal Sprime = nonterminal( \"S'\", nonterminal::ONE);");

        for(String s:Nonterminals)
            System.out.println("nonterminal "+s+" = nonterminal(\""+s+ "\", nonterminal::ONE);");

        System.out.println("\nset<nonterminal> n;\n");

        System.out.println("n.insert(Sprime);\n\n");
        for(String s:Nonterminals)
            System.out.println("n.insert("+s+");\n\n");
        System.out.println("vector<nonterminal> middle;\n");
    }

    static void PrintMap4Probs(TreeSet<Map.Entry<String,Double>> terminals, TreeSet<Map.Entry<String,Double>> nonterminals){
        int index=0;
        //MAPPING THE PROBABILITIES TO EACH RULE
        System.out.println("\nmap<one_rule, map<terminal_string, prob_t> > p;\n" +
                "    map<terminal_string, prob_t> t;\n" +
                "    vector<terminal> terminals;\n");

        int lastIteration=0;

        String  previousLhs="";
        ArrayList<String> previousRhs=new ArrayList<>();
        Double previousDouble=0.0;
        String previousString="";

        for (Map.Entry<String, Double> entry : terminals) {
            lastIteration++;
            String str = entry.getKey();

            ArrayList<String> rhs = new ArrayList<>();
            rhs=getRhsWithTerminals(str);
            str = str.substring(0,str.lastIndexOf('(')-1);//removes the digits for prob after the rule
            //str = str.substring(0, str.length() - 5);//removes the 2 digit prob after the rule

            int start = str.indexOf('\u2192') - 1;
            String lhs = str.substring(0, start);


            if (previousLhs==""|| previousLhs.compareTo(lhs)>0){
                //do nothing
            }
            else{

                for(String s: previousRhs){
                    //System.out.println("Value of rhs is: "+rhs);
                    if(s.contains("<U")||s.contains("<C")||s.contains("<G")||s.contains("<A")){
                        System.out.println("terminals.push_back("+Character.toLowerCase(s.charAt(1))+");");
                    }

                }
                if(similarRules(lhs,rhs,previousLhs,previousRhs)){

                    System.out.println("t.insert(make_pair(terminal_string(terminals)," + previousDouble+"));//probability of "+previousString+"");
                    System.out.println("terminals.clear();");
                }
                else{
                    System.out.println("t.insert(make_pair(terminal_string(terminals)," + previousDouble+"));//probability of "+previousString+"\n");
                    System.out.println( "p.insert(make_pair(r"+index+", t));\n" +
                            "t.clear();\n" +
                            "terminals.clear();\n");
/*
                            System.out.println("t.insert(make_pair(terminal_string(terminals)," + entry.getValue()+"));//probability of "+str+"\n" +
                                    "p.insert(make_pair(r"+index+", t));\n" +
                                    "t.clear();\n" +
                                    "terminals.clear();\n");*/

                    System.out.println();
                    index++;    }
            }


            previousLhs=lhs;
            previousRhs=rhs;
            previousDouble= entry.getValue();
            previousString=str;
            if(lastIteration==terminals.size()){//last iteration
                for(String s: previousRhs){
                    //System.out.println("Value of rhs is: "+rhs);
                    if(s.contains("<U")||s.contains("<C")||s.contains("<G")||s.contains("<A")){
                        System.out.println("terminals.push_back("+Character.toLowerCase(s.charAt(1))+");");
                    }

                }
                System.out.println("t.insert(make_pair(terminal_string(terminals)," + previousDouble+"));//probability of "+previousString+"\n");
                System.out.println( "p.insert(make_pair(r"+index+", t));\n" +
                        "t.clear();\n" +
                        "terminals.clear();\n");
                index++;
            }


        }

        for (Map.Entry<String, Double> entry : nonterminals) {
            String str= entry.getKey();

            ArrayList<String> rhs=new ArrayList<>();
            //int index=0;
            String lhs;


            str = str.substring(0,str.lastIndexOf('(')-1);//removes the digits for prob after the rule
            //str = str.substring(0, str.length() - 5);//removes the 2 digit prob after the rule

            //str=str.substring(0,str.length()-5);//removes the 4 characters digit prob after the rule

            int start= str.indexOf('\u2192')-1;
            lhs=str.substring(0,start);

            start+=2;
            String string = entry.getKey();
            string = string.substring(0,string.length()-5);

            str=str.substring(start,str.length());
            rhs=getRhsWithoutTerminals(str);


            for (String stri: rhs)
            {
                if(stri.contains("<")||stri.contains(">")){
                    {
                        char base=stri.charAt(1);
                        System.out.println("terminals.push_back("+Character.toLowerCase(base)+");\n");
                    }
                }
            }
            System.out.println(
                    "t.insert(make_pair(terminal_string(terminals), "+entry.getValue()+"));//probability of "+string+"\n" +
                            "p.insert(make_pair(r"+index+", t));\n" +
                            "t.clear();\n" +
                            "terminals.clear();");

            System.out.println();
            index++;
        }
        ///for S->S'
        System.out.println("t.insert(make_pair(terminal_string(terminals)," + 1.0+"));//probability of S'->S");
        System.out.println( "p.insert(make_pair(r"+index+", t));\n" +
                "t.clear();\n" +
                "terminals.clear();\n");

    }

}

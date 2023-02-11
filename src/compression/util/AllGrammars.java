package compression.util;

import compression.samplegrammars.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AllGrammars {

    public static Map<String, SampleGrammar> allGrammars(boolean withNonCanonicalRules, boolean withHairpinLengthOne) {

        Map<String, SampleGrammar> allGrammars = new TreeMap<>();

        allGrammars.put("G1", new DowellGrammar1(withNonCanonicalRules));
        allGrammars.put("G1B", new DowellGrammar1Bound(withNonCanonicalRules));
        allGrammars.put("G2", new DowellGrammar2(withNonCanonicalRules));
        allGrammars.put("G2B", new DowellGrammar2Bound(withNonCanonicalRules));
        allGrammars.put("G3", new DowellGrammar3(withNonCanonicalRules));
        allGrammars.put("G3B", new DowellGrammar3Bound(withNonCanonicalRules));
        allGrammars.put("G4", new DowellGrammar4(withNonCanonicalRules));
        allGrammars.put("G4B", new DowellGrammar4Bound(withNonCanonicalRules));
        allGrammars.put("G5", new DowellGrammar5(withNonCanonicalRules));
        allGrammars.put("G5B", new DowellGrammar5Bound(withNonCanonicalRules));
        allGrammars.put("G6", new DowellGrammar6(withNonCanonicalRules, withHairpinLengthOne));
        allGrammars.put("G6B", new DowellGrammar6Bound(withNonCanonicalRules, withHairpinLengthOne));
        allGrammars.put("G6BM", new DowellGrammar6BoundMirror(withNonCanonicalRules, withHairpinLengthOne));
        allGrammars.put("G7", new DowellGrammar7Modified(withNonCanonicalRules));
        allGrammars.put("G7B", new DowellGrammar7ModifiedNBound(withNonCanonicalRules));
        allGrammars.put("G8",new DowellGrammar8Modified(withNonCanonicalRules));
        allGrammars.put("G8B", new DowellGrammar8ModifiedNBound(withNonCanonicalRules));
        allGrammars.put("AG", new ArbitraryGrammar(withNonCanonicalRules));
        //allGrammars.put("AGB", new ArbitraryGrammarBound(withNonCanonicalRules));
        allGrammars.put("LiuGrammar", new LiuGrammar(withNonCanonicalRules));
        //allGrammars.put("LiuGrammarB", new LiuGrammarBound(withNonCanonicalRules));
        allGrammars.put("SchulzGrammar", new SchulzGrammar(withNonCanonicalRules));
        //sALL_GRAMMARS.put("SchulzGrammarB", new SchulzGrammarBound(withNonCanonicalRules));
        return allGrammars;
    }

    public static List<SampleGrammar> getGrammarsFromCmdLine(final String[] args, int firstGrammarArg, final boolean withNonCanonicalRules, final boolean withHairpinLengthOne) throws IOException {
        Map<String, SampleGrammar> allGrammars = allGrammars(withNonCanonicalRules, withHairpinLengthOne);
        List<SampleGrammar> listOfGrammars = new ArrayList<>();
        if ("ALL".equalsIgnoreCase(args[firstGrammarArg])) {
            listOfGrammars.addAll(allGrammars.values());
        } else {
            for (int i = firstGrammarArg; i < args.length; i++) {
                final String arg = args[i];
                if (allGrammars.containsKey(arg)) {
                    listOfGrammars.add(allGrammars.get(arg));
                } else {
                    System.err.println("Don't know grammar " + arg);
                }
            }
        }

        if (listOfGrammars.size() == 0) {
            System.out.println("No valid grammars given");
            System.exit(97);
        }
        return listOfGrammars;
    }

    public static Collection<String> allGrammarNames() {
        return allGrammars(false, false).keySet();
    }

}

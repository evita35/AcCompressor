package compression.grammar;

import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;

import java.util.HashMap;
import java.util.Map;


/**
 * Unified names for Nonterminals for writing rule probabilities to file
 */
public class CategoryMaps {

    public  Map<String, NonTerminal> stringNonTerminalMap;
    public  Map<String, Category> stringCategoryMap;

    public CategoryMaps() {
        NonTerminal S = new NonTerminal("S");
        NonTerminal L = new NonTerminal("L");
        NonTerminal N = new NonTerminal("N");
        NonTerminal T = new NonTerminal("T");
        NonTerminal F = new NonTerminal("F");
        NonTerminal R = new NonTerminal("R");

        NonTerminal Sp = new NonTerminal("Sp");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");
        NonTerminal E = new NonTerminal("E");


        NonTerminal GN = new NonTerminal("GN");
        NonTerminal H = new NonTerminal("H");
        NonTerminal I = new NonTerminal("I");
        NonTerminal J = new NonTerminal("J");
        NonTerminal K = new NonTerminal("K");

        NonTerminal M = new NonTerminal("M");

        NonTerminal P= new NonTerminal("P");
        NonTerminal Q = new NonTerminal("Q");

        NonTerminal U = new NonTerminal("U");
        NonTerminal X = new NonTerminal("X");

        NonTerminal XC= new NonTerminal("X^C");
        NonTerminal XB = new NonTerminal("X^B");
        NonTerminal XF = new NonTerminal("X^F");
        NonTerminal XH = new NonTerminal("X^H");
        NonTerminal XI = new NonTerminal("X^I");
        NonTerminal XU = new NonTerminal("X^U");


        NonTerminal Pau = new NonTerminal("Pau");
        NonTerminal Pua = new NonTerminal("Pua");
        NonTerminal Pcg = new NonTerminal("Pcg");
        NonTerminal Pgc = new NonTerminal("Pgc");
        NonTerminal Pgu = new NonTerminal("Pgu");
        NonTerminal Pug = new NonTerminal("Pug");

        //NON TERMINALS FOR NON CANONICAL RULES
        NonTerminal Paa = new NonTerminal("Paa");
        NonTerminal Pac = new NonTerminal("Pac");
        NonTerminal Pag= new NonTerminal("Pag");
        NonTerminal Pca = new NonTerminal("Pca");
        NonTerminal Pcc = new NonTerminal("Pcc");
        NonTerminal Pcu = new NonTerminal("Pcu");
        NonTerminal Pga = new NonTerminal("Pga");
        NonTerminal Pgg = new NonTerminal("Pgg");
        NonTerminal Puc = new NonTerminal("Puc");
        NonTerminal Puu = new NonTerminal("Puu");
        //NON TERMINALS FOR NON CANONICAL RULES

        NonTerminal Vau = new NonTerminal("Vau");
        NonTerminal Vua = new NonTerminal("Vua");
        NonTerminal Vcg = new NonTerminal("Vcg");
        NonTerminal Vgc = new NonTerminal("Vgc");
        NonTerminal Vgu = new NonTerminal("Vgu");
        NonTerminal Vug = new NonTerminal("Vug");

        //NON TERMINALS FOR NON CANONICAL RULES
        NonTerminal Vaa = new NonTerminal("Vaa");
        NonTerminal Vac = new NonTerminal("Vac");
        NonTerminal Vag= new NonTerminal("Vag");
        NonTerminal Vca = new NonTerminal("Vca");
        NonTerminal Vcc = new NonTerminal("Vcc");
        NonTerminal Vcu = new NonTerminal("Vcu");
        NonTerminal Vga = new NonTerminal("Vga");
        NonTerminal Vgg = new NonTerminal("Vgg");
        NonTerminal Vuc = new NonTerminal("Vuc");
        NonTerminal Vuu = new NonTerminal("Vuu");
        //NON TERMINALS FOR NON CANONICAL RULES

        NonTerminal Bau = new NonTerminal("Bau");
        NonTerminal Bua = new NonTerminal("Bua");
        NonTerminal Bcg = new NonTerminal("Bcg");
        NonTerminal Bgc = new NonTerminal("Bgc");
        NonTerminal Bgu = new NonTerminal("Bgu");
        NonTerminal Bug = new NonTerminal("Bug");

        //NON TERMINALS FOR NON CANONICAL RULES
        NonTerminal Baa = new NonTerminal("Baa");
        NonTerminal Bac = new NonTerminal("Bac");
        NonTerminal Bag= new NonTerminal("Bag");
        NonTerminal Bca = new NonTerminal("Bca");
        NonTerminal Bcc = new NonTerminal("Bcc");
        NonTerminal Bcu = new NonTerminal("Bcu");
        NonTerminal Bga = new NonTerminal("Bga");
        NonTerminal Bgg = new NonTerminal("Bgg");
        NonTerminal Buc = new NonTerminal("Buc");
        NonTerminal Buu = new NonTerminal("Buu");
        //NON TERMINALS FOR NON CANONICAL RULES

        PairOfCharTerminal ao = new PairOfChar('A', '(').asTerminal();
        PairOfCharTerminal co = new PairOfChar('C', '(').asTerminal();
        PairOfCharTerminal go = new PairOfChar('G', '(').asTerminal();
        PairOfCharTerminal uo = new PairOfChar('U', '(').asTerminal();
        PairOfCharTerminal ac = new PairOfChar('A', ')').asTerminal();
        PairOfCharTerminal cc = new PairOfChar('C', ')').asTerminal();
        PairOfCharTerminal gc = new PairOfChar('G', ')').asTerminal();
        PairOfCharTerminal uc = new PairOfChar('U', ')').asTerminal();
        PairOfCharTerminal au = new PairOfChar('A', '.').asTerminal();
        PairOfCharTerminal cu = new PairOfChar('C', '.').asTerminal();
        PairOfCharTerminal gu = new PairOfChar('G', '.').asTerminal();
        PairOfCharTerminal uu = new PairOfChar('U', '.').asTerminal();

        stringNonTerminalMap = new HashMap<>();
        stringCategoryMap = new HashMap<>();

        stringNonTerminalMap.put("S", S);
        stringNonTerminalMap.put("N", N);
        stringNonTerminalMap.put("T", T);
        stringNonTerminalMap.put("F", F);
        stringNonTerminalMap.put("L", L);
        stringNonTerminalMap.put("R",R);
        stringNonTerminalMap.put("E", E);

        stringNonTerminalMap.put("Sp", Sp);
        stringNonTerminalMap.put("A", A);
        stringNonTerminalMap.put("B", B);
        stringNonTerminalMap.put("C", C);
        stringNonTerminalMap.put("D", D);
        stringNonTerminalMap.put("GN", GN);
        stringNonTerminalMap.put("H",H);

        stringNonTerminalMap.put("I", I);
        stringNonTerminalMap.put("J", J);
        stringNonTerminalMap.put("K", K);
        stringNonTerminalMap.put("L", L);
        stringNonTerminalMap.put("M", M);
        stringNonTerminalMap.put("P",P);

        stringNonTerminalMap.put("Q", Q);
        stringNonTerminalMap.put("U", U);
        stringNonTerminalMap.put("X", X);

        stringNonTerminalMap.put("X^B", XB);
        stringNonTerminalMap.put("X^C", XC);
        stringNonTerminalMap.put("X^F", XF);
        stringNonTerminalMap.put("X^H", XH);
        stringNonTerminalMap.put("X^I", XI);
        stringNonTerminalMap.put("X^U", XU);

        stringNonTerminalMap.put("Pau", Pau);
        stringNonTerminalMap.put("Pua", Pua);
        stringNonTerminalMap.put("Pcg", Pcg);
        stringNonTerminalMap.put("Pgc", Pgc);
        stringNonTerminalMap.put("Pug", Pug);
        stringNonTerminalMap.put("Pgu", Pgu);


        stringNonTerminalMap.put("Paa", Paa);
        stringNonTerminalMap.put("Pac", Pac);
        stringNonTerminalMap.put("Pag", Pag);
        stringNonTerminalMap.put("Pca", Pca);
        stringNonTerminalMap.put("Pcc", Pcc);
        stringNonTerminalMap.put("Pcu", Pcu);
        stringNonTerminalMap.put("Pga", Pga);
        stringNonTerminalMap.put("Pgg", Pgg);
        stringNonTerminalMap.put("Puc", Puc);
        stringNonTerminalMap.put("Puu", Puu);

        stringNonTerminalMap.put("Vau", Vau);
        stringNonTerminalMap.put("Vua", Vua);
        stringNonTerminalMap.put("Vcg", Vcg);
        stringNonTerminalMap.put("Vgc", Vgc);
        stringNonTerminalMap.put("Vug", Vug);
        stringNonTerminalMap.put("Vgu", Vgu);


        stringNonTerminalMap.put("Vaa", Vaa);
        stringNonTerminalMap.put("Vac", Vac);
        stringNonTerminalMap.put("Vag", Vag);
        stringNonTerminalMap.put("Vca", Vca);
        stringNonTerminalMap.put("Vcc", Vcc);
        stringNonTerminalMap.put("Vcu", Vcu);
        stringNonTerminalMap.put("Vga", Vga);
        stringNonTerminalMap.put("Vgg", Vgg);
        stringNonTerminalMap.put("Vuc", Vuc);
        stringNonTerminalMap.put("Vuu", Vuu);

        stringNonTerminalMap.put("Bau", Bau);
        stringNonTerminalMap.put("Bua", Bua);
        stringNonTerminalMap.put("Bcg", Bcg);
        stringNonTerminalMap.put("Bgc", Bgc);
        stringNonTerminalMap.put("Bug", Bug);
        stringNonTerminalMap.put("Bgu", Bgu);


        stringNonTerminalMap.put("Baa", Baa);
        stringNonTerminalMap.put("Bac", Bac);
        stringNonTerminalMap.put("Bag", Bag);
        stringNonTerminalMap.put("Bca", Bca);
        stringNonTerminalMap.put("Bcc", Bcc);
        stringNonTerminalMap.put("Bcu", Bcu);
        stringNonTerminalMap.put("Bga", Bga);
        stringNonTerminalMap.put("Bgg", Bgg);
        stringNonTerminalMap.put("Buc", Buc);
        stringNonTerminalMap.put("Buu", Buu);


        stringCategoryMap.put("S", S);
        stringCategoryMap.put("N", N);
        stringCategoryMap.put("T", T);
        stringCategoryMap.put("F", F);
        stringCategoryMap.put("L", L);
        stringCategoryMap.put("R", R);

        stringCategoryMap.put("Pau", Pau);
        stringCategoryMap.put("Pua", Pua);
        stringCategoryMap.put("Pcg", Pcg);
        stringCategoryMap.put("Pgc", Pgc);
        stringCategoryMap.put("Pug", Pug);
        stringCategoryMap.put("Pgu", Pgu);


        stringCategoryMap.put("<A|(>", ao);
        stringCategoryMap.put("<A|)>", ac);
        stringCategoryMap.put("<A|.>", au);
        stringCategoryMap.put("<U|(>", uo);
        stringCategoryMap.put("<U|)>", uc);
        stringCategoryMap.put("<U|.>", uu);
        stringCategoryMap.put("<G|(>", go);
        stringCategoryMap.put("<G|)>", gc);
        stringCategoryMap.put("<G|.>", gu);
        stringCategoryMap.put("<C|(>", co);
        stringCategoryMap.put("<C|)>", cc);
        stringCategoryMap.put("<C|.>", cu);

    }

}

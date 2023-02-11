package compression.samplegrammars;

import compression.LocalConfig;
import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import org.leibnizcenter.cfg.algebra.semiring.dbl.*;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;
import org.leibnizcenter.cfg.category.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchulzGrammar implements SampleGrammar {

    private Grammar<PairOfChar> G ;
    private boolean withNonCanonicalRules;

    private NonTerminal S;
    private String name = "SchulzGrammar";

    public SchulzGrammar(boolean withNonCanonicalRules) {

        this.withNonCanonicalRules = withNonCanonicalRules;

        ExpressionSemiring semiring = CountingExprSemiring.get();//used for Grammars with assignProbs automated

         S = new NonTerminal("S");
        NonTerminal Sp = new NonTerminal("Sp");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        //NonTerminal D = new NonTerminal("D");
        //NonTerminal E = new NonTerminal("E");
        NonTerminal F = new NonTerminal("F");
        NonTerminal GN = new NonTerminal("GN");
        NonTerminal H = new NonTerminal("H");
        NonTerminal I = new NonTerminal("I");
        NonTerminal J = new NonTerminal("J");
        NonTerminal K = new NonTerminal("K");
        NonTerminal L = new NonTerminal("L");
        NonTerminal M = new NonTerminal("M");
        NonTerminal N = new NonTerminal("N");
        //NonTerminal O = new NonTerminal("O");
        NonTerminal P= new NonTerminal("P");
        NonTerminal Q = new NonTerminal("Q");
        NonTerminal R = new NonTerminal("R");

        NonTerminal T = new NonTerminal("T");
        NonTerminal U = new NonTerminal("U");

        NonTerminal XC= new NonTerminal("X^C");
        NonTerminal XB = new NonTerminal("X^B");
        NonTerminal XF = new NonTerminal("X^F");
        NonTerminal XH = new NonTerminal("X^H");
        NonTerminal XI = new NonTerminal("X^I");
        NonTerminal XU = new NonTerminal("X^U");

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


        //NonTerminal UP= new NonTerminal("UP");//for unpaired bases


        //fileName= LocalConfig.SchulzFile;
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

        Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("SchulzGrammar")
                .withSemiring(semiring)//found in g1, g2, g3, g7 and g8

                //-----------Sp LHS
                .addRule(S, Sp)
                //-----------S LHS
                .addRule(Sp, A)
                .addRule(Sp, C)
                .addRule(Sp, A, C)
                .addRule(Sp, T, A)
                .addRule(Sp, T, A, C)
                //----------T LHS
                .addRule(T, A)
                .addRule(T, A, C)
                .addRule(T, T, A)
                .addRule(T, T, A, C)
                .addRule(T, C)


                //-----------C LHS
                .addRule(C, XC)
                .addRule(C, C, XC)
                //-----------XC LHS
                .addRule(XC, au)
                .addRule(XC, cu)
                .addRule(XC, gu)
                .addRule(XC, uu)

                //-----------A LHS
                .addRule(A, ao, L, uc)
                .addRule(A, uo, L, ac)
                .addRule(A, co, L, gc)
                .addRule(A, go, L, cc)
                .addRule(A, uo, L, gc)
                .addRule(A, go, L, uc)
                //-------------L LHS
                .addRule(L, ao, L, uc)
                .addRule(L, uo, L, ac)
                .addRule(L, co, L, gc)
                .addRule(L, go, L, cc)
                .addRule(L, uo, L, gc)
                .addRule(L, go, L, uc)
                .addRule(L, M)
                .addRule(L, P)
                .addRule(L, Q)
                .addRule(L, R)
                .addRule(L, F)
                .addRule(L, GN)

                //------------GN LHS
                .addRule(GN, I, au)
                .addRule(GN, I, cu)
                .addRule(GN, I, gu)
                .addRule(GN, I, uu)

                .addRule(GN, au,I)
                .addRule(GN, cu,I)
                .addRule(GN, gu,I)
                .addRule(GN, uu,I)

                .addRule(GN, I, XB,XB)
                .addRule(GN, I, B, XB, XB)
                .addRule(GN, XB, XB, I)
                .addRule(GN, XB, XB, B, I)
                //-----------XB LHS
                .addRule(XB, au)
                .addRule(XB, cu)
                .addRule(XB, gu)
                .addRule(XB, uu)

                //-----------B LHS
                .addRule(B, XB)
                .addRule(B, B, XB)

                //-----------F LHS
                .addRule(F, XF)
                .addRule(F, XF, XF)
                .addRule(F, XF, XF, XF)
                .addRule(F, XF, XF, XF, XF)
                .addRule(F, XF, XF, XF, XF, XF)
                .addRule(F, XF, XF, XF, XF, XF, H)
                //----------XF LHS
                .addRule(XF, au)
                .addRule(XF, cu)
                .addRule(XF, gu)
                .addRule(XF, uu)
                //----------XH LHS
                .addRule(XH, au)
                .addRule(XH, cu)
                .addRule(XH, gu)
                .addRule(XH, uu)
                //----------H LHS
                .addRule(H, XH)
                .addRule(H, H, XH)
                //----------- P LHS
                .addRule(P, au, I, au)
                .addRule(P, au, I, cu)
                .addRule(P, au, I, gu)
                .addRule(P, au, I, uu)
                .addRule(P, cu, I, au)
                .addRule(P, cu, I, cu)
                .addRule(P, cu, I, gu)
                .addRule(P, cu, I, uu)
                .addRule(P, gu, I, au)
                .addRule(P, gu, I, cu)
                .addRule(P, gu, I, gu)
                .addRule(P, gu, I, uu)
                .addRule(P, uu, I, au)
                .addRule(P, uu, I, cu)
                .addRule(P, uu, I, gu)
                .addRule(P, uu, I, uu)

                .addRule(P, XI, I, XI,XI)
                .addRule(P, XI, XI, I, XI)
                .addRule(P, XI, XI, I, XI, XI)
                //-----------DI LHS
                .addRule(XI, au)
                .addRule(XI, cu)
                .addRule(XI, gu)
                .addRule(XI, uu)
                //-----------I LHS
                .addRule(I, ao, L, uc)
                .addRule(I, uo, L, ac)
                .addRule(I, co, L, gc)
                .addRule(I, go, L, cc)
                .addRule(I, uo, L, gc)
                .addRule(I, go, L, uc)

                //------------Q LHS
                .addRule(Q, XI,XI, I, XI,XI,XI)
                .addRule(Q, XI,XI, I,K, XI,XI,XI)
                .addRule(Q, XI,XI, XI,I, XI, XI)
                .addRule(Q, XI,XI, XI,I, XI,XI)
                .addRule(Q, XI,XI, XI,J, I, XI, XI)
                .addRule(Q, XI,XI, XI, I,K, XI,XI)
                .addRule(Q, XI,XI,XI,J, I,K,XI,XI)
                //-----------R LHS
                .addRule(R, XI, I,XI,XI,XI)
                .addRule(R, XI, I,K,XI,XI,XI)
                .addRule(R, XI, XI,XI, I,XI)
                .addRule(R, XI, XI,XI, J, I,XI)
                //----------J LHS
                .addRule(J,XI)
                .addRule(J,J,XI)
                //----------K LHS
                .addRule(K,XI)
                .addRule(K,K,XI)
                //----------M LHS
                .addRule(M, A, A)
                .addRule(M, U, A, A)
                .addRule(M, U, A, U, A)
                .addRule(M, A, U, A)
                .addRule(M, A, A, N)
                .addRule(M, U, A, A, N)
                .addRule(M, A, U, A, N)
                .addRule(M, U, A, U, A, N)
                //-----------N LHS
                .addRule(N, A)
                .addRule(N, U, A)
                .addRule(N, A, N)
                .addRule(N, U, A, N)
                .addRule(N, U)

                //----------U LHS
                .addRule(U, XU)
                .addRule(U, U, XU)

                //----------X
                // ]// U LHS
                .addRule(XU, au)
                .addRule(XU, cu)
                .addRule(XU, gu)
                .addRule(XU, uu);

        if (withNonCanonicalRules) {
            Gb
                    .addRule(A, ao, L, ac)
                    .addRule(A, ao, L, cc)
                    .addRule(A, ao, L, gc)
                    .addRule(A, co, L, ac)
                    .addRule(A, co, L, cc)
                    .addRule(A, co, L, uc)
                    .addRule(A, go, L, ac)
                    .addRule(A, go, L, gc)
                    .addRule(A, uo, L, cc)
                    .addRule(A, uo, L, uc)

                    .addRule(L, ao, L, ac)
                    .addRule(L, ao, L, cc)
                    .addRule(L, ao, L, gc)
                    .addRule(L, co, L, ac)
                    .addRule(L, co, L, cc)
                    .addRule(L, co, L, uc)
                    .addRule(L, go, L, ac)
                    .addRule(L, go, L, gc)
                    .addRule(L, uo, L, cc)
                    .addRule(L, uo, L, uc)

                    .addRule(I, ao, L, ac)
                    .addRule(I, ao, L, cc)
                    .addRule(I, ao, L, gc)
                    .addRule(I, co, L, ac)
                    .addRule(I, co, L, cc)
                    .addRule(I, co, L, uc)
                    .addRule(I, go, L, ac)
                    .addRule(I, go, L, gc)
                    .addRule(I, uo, L, cc)
                    .addRule(I, uo, L, uc);
        }

                    G=Gb.build();

    }

    @Override
    public boolean isWithNoncanonicalRules() {
        return withNonCanonicalRules;
    }

    //public String getFileName(){return fileName;}
    public NonTerminal getStartSymbol() {
        return S;
    }

    public Grammar<PairOfChar> getGrammar() {
        return G;
    }

    public String getName (){
        return name;
    }
}

package compression.samplegrammars;

import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import org.leibnizcenter.cfg.algebra.semiring.dbl.CountingExprSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;

public class DowellGrammar8ModifiedNBound implements SampleGrammar{

    private final Grammar<PairOfChar> G;
    private final NonTerminal S;

    boolean withNonCanonicalRules=false;

    public String name="DowellGrammar8ModifiedNBound";

    public DowellGrammar8ModifiedNBound(boolean withNonCanonicalRules) {

        this.withNonCanonicalRules=withNonCanonicalRules;
        ExpressionSemiring semiring = CountingExprSemiring.get();//used for Grammars with assignProbs automated
        //fileName= LocalConfig.G8File;
        S = new NonTerminal("S");

        NonTerminal N = new NonTerminal("N");
        NonTerminal E = new NonTerminal("E");
        NonTerminal U = new NonTerminal("U");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");



        NonTerminal Bau = new NonTerminal("Bau");
        NonTerminal Bua = new NonTerminal("Bua");
        NonTerminal Bcg= new NonTerminal("Bcg");
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


        NonTerminal Vau = new NonTerminal("Vau");
        NonTerminal Vua = new NonTerminal("Vua");
        NonTerminal Vcg= new NonTerminal("Vcg");
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



        Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("Dowell Eddy G8 Bound")
                //-----------line 1
                .withSemiring(semiring)//found in g1, g2, g3, g7 and g8
                .addRule(S, U)
                .addRule(S, U, S)
                .addRule(S, E)

                //.addRule(E, E, U)
                .addRule(E, B)
                .addRule(E, B, D)

                .addRule(C, U)
                .addRule(C, B)

                .addRule(D, C, D)
                .addRule(D, C)

                .addRule(N, U, S)
                .addRule(N, U)
                .addRule(N, E)
                .addRule(N, E, U)
                .addRule(N, E, B)

                .addRule(U, au)
                .addRule(U, cu)
                .addRule(U, gu)
                .addRule(U, uu)

                .addRule(B, ao, Vau, uc)
                .addRule(B, uo, Vua, ac)
                .addRule(B, go, Vgc, cc)
                .addRule(B, co, Vcg, gc)
                .addRule(B, uo, Vug, gc)
                .addRule(B, go, Vgu, uc)




                //-------------line4
                .addRule(Bau, ao, Vau, uc)
                .addRule(Bau, uo, Vua, ac)
                .addRule(Bau, co, Vcg, gc)
                .addRule(Bau, go, Vgc, cc)
                .addRule(Bau, uo, Vug, gc)
                .addRule(Bau, go, Vgu, uc)

                .addRule(Bua, ao, Vau, uc)
                .addRule(Bua, uo, Vua, ac)
                .addRule(Bua, co, Vcg, gc)
                .addRule(Bua, go, Vgc, cc)
                .addRule(Bua, uo, Vug, gc)
                .addRule(Bua, go, Vgu, uc)

                .addRule(Bgc, ao, Vau, uc)
                .addRule(Bgc, uo, Vua, ac)
                .addRule(Bgc, co, Vcg, gc)
                .addRule(Bgc, go, Vgc, cc)
                .addRule(Bgc, uo, Vug, gc)
                .addRule(Bgc, go, Vgu, uc)

                .addRule(Bcg, ao, Vau, uc)
                .addRule(Bcg, uo, Vua, ac)
                .addRule(Bcg, co, Vcg, gc)
                .addRule(Bcg, go, Vgc, cc)
                .addRule(Bcg, uo, Vug, gc)
                .addRule(Bcg, go, Vgu, uc)

                .addRule(Bgu, ao, Vau, uc)
                .addRule(Bgu, uo, Vua, ac)
                .addRule(Bgu, co, Vcg, gc)
                .addRule(Bgu, go, Vgc, cc)
                .addRule(Bgu, uo, Vug, gc)
                .addRule(Bgu, go, Vgu, uc)

                .addRule(Bug, ao, Vau, uc)
                .addRule(Bug, uo, Vua, ac)
                .addRule(Bug, co, Vcg, gc)
                .addRule(Bug, go, Vgc, cc)
                .addRule(Bug, uo, Vug, gc)
                .addRule(Bug, go, Vgu, uc)


                .addRule(Vau,  N)

                .addRule(Vua,  N)

                .addRule(Vgc,  N)

                .addRule(Vcg,  N)

                .addRule(Vgu,  N)

                .addRule(Vug, N)


                .addRule(Vau,  Bau)

                .addRule(Vua,  Bua)

                .addRule(Vgc,  Bgc)

                .addRule(Vcg,  Bcg)

                .addRule(Vgu,  Bgu)

                .addRule(Vug, Bug);

        if (withNonCanonicalRules) {
            Gb
                    .addRule(B, ao, Vaa, ac)
                    .addRule(B, ao, Vac, cc)
                    .addRule(B, ao, Vag, gc)
                    .addRule(B, co, Vca, ac)
                    .addRule(B, co, Vcc, cc)
                    .addRule(B, co, Vcu, uc)
                    .addRule(B, go, Vga, ac)
                    .addRule(B, go, Vgg, gc)
                    .addRule(B, uo, Vuc, cc)
                    .addRule(B, uo, Vuu, uc)

                    .addRule(Baa, ao, Vaa, ac)//non canonical lhs to non canonical rhs
                    .addRule(Baa, ao, Vac, cc)
                    .addRule(Baa, ao, Vag, gc)
                    .addRule(Baa, co, Vca, ac)
                    .addRule(Baa, co, Vcc, cc)
                    .addRule(Baa, co, Vcu, uc)
                    .addRule(Baa, go, Vga, ac)
                    .addRule(Baa, go, Vgg, gc)
                    .addRule(Baa, uo, Vuc, cc)
                    .addRule(Baa, uo, Vuu, uc)

                    .addRule(Baa, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Baa, uo, Vua, ac)
                    .addRule(Baa, co, Vcg, gc)
                    .addRule(Baa, go, Vgc, cc)
                    .addRule(Baa, uo, Vug, gc)
                    .addRule(Baa, go, Vgu, uc)

                    .addRule(Bac, ao, Vaa, ac)//non canonical lhs to non canonical rhs
                    .addRule(Bac, ao, Vac, cc)
                    .addRule(Bac, ao, Vag, gc)
                    .addRule(Bac, co, Vca, ac)
                    .addRule(Bac, co, Vcc, cc)
                    .addRule(Bac, co, Vcu, uc)
                    .addRule(Bac, go, Vga, ac)
                    .addRule(Bac, go, Vgg, gc)
                    .addRule(Bac, uo, Vuc, cc)
                    .addRule(Bac, uo, Vuu, uc)

                    .addRule(Bac, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Bac, uo, Vua, ac)
                    .addRule(Bac, co, Vcg, gc)
                    .addRule(Bac, go, Vgc, cc)
                    .addRule(Bac, uo, Vug, gc)
                    .addRule(Bac, go, Vgu, uc)

                    .addRule(Bag, ao, Vaa, ac)//non canonical lhs to non canonical rhs
                    .addRule(Bag, ao, Vac, cc)
                    .addRule(Bag, ao, Vag, gc)
                    .addRule(Bag, co, Vca, ac)
                    .addRule(Bag, co, Vcc, cc)
                    .addRule(Bag, co, Vcu, uc)
                    .addRule(Bag, go, Vga, ac)
                    .addRule(Bag, go, Vgg, gc)
                    .addRule(Bag, uo, Vuc, cc)
                    .addRule(Bag, uo, Vuu, uc)

                    .addRule(Bag, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Bag, uo, Vua, ac)
                    .addRule(Bag, co, Vcg, gc)
                    .addRule(Bag, go, Vgc, cc)
                    .addRule(Bag, uo, Vug, gc)
                    .addRule(Bag, go, Vgu, uc)

                    .addRule(Bau, ao, Vaa, ac)//canonical lhs, non canonical rhs
                    .addRule(Bau, ao, Vac, cc)
                    .addRule(Bau, ao, Vag, gc)
                    .addRule(Bau, co, Vca, ac)
                    .addRule(Bau, co, Vcc, cc)
                    .addRule(Bau, co, Vcu, uc)
                    .addRule(Bau, go, Vga, ac)
                    .addRule(Bau, go, Vgg, gc)
                    .addRule(Bau, uo, Vuc, cc)
                    .addRule(Bau, uo, Vuu, uc)

                    .addRule(Bca, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Bca, uo, Vua, ac)
                    .addRule(Bca, co, Vcg, gc)
                    .addRule(Bca, go, Vgc, cc)
                    .addRule(Bca, uo, Vug, gc)
                    .addRule(Bca, go, Vgu, uc)

                    .addRule(Bca, ao, Vaa, ac)//non canonical lhs, non canonical rhs
                    .addRule(Bca, ao, Vac, cc)
                    .addRule(Bca, ao, Vag, gc)
                    .addRule(Bca, co, Vca, ac)
                    .addRule(Bca, co, Vcc, cc)
                    .addRule(Bca, co, Vcu, uc)
                    .addRule(Bca, go, Vga, ac)
                    .addRule(Bca, go, Vgg, gc)
                    .addRule(Bca, uo, Vuc, cc)
                    .addRule(Bca, uo, Vuu, uc)

                    .addRule(Bcc, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Bcc, uo, Vua, ac)
                    .addRule(Bcc, co, Vcg, gc)
                    .addRule(Bcc, go, Vgc, cc)
                    .addRule(Bcc, uo, Vug, gc)
                    .addRule(Bcc, go, Vgu, uc)

                    .addRule(Bcc, ao, Vaa, ac)//non canonical lhs, non canonical rhs
                    .addRule(Bcc, ao, Vac, cc)
                    .addRule(Bcc, ao, Vag, gc)
                    .addRule(Bcc, co, Vca, ac)
                    .addRule(Bcc, co, Vcc, cc)
                    .addRule(Bcc, co, Vcu, uc)
                    .addRule(Bcc, go, Vga, ac)
                    .addRule(Bcc, go, Vgg, gc)
                    .addRule(Bcc, uo, Vuc, cc)
                    .addRule(Bcc, uo, Vuu, uc)

                    .addRule(Bcg, ao, Vaa, ac)// canonical lhs, non canonical rhs
                    .addRule(Bcg, ao, Vac, cc)
                    .addRule(Bcg, ao, Vag, gc)
                    .addRule(Bcg, co, Vca, ac)
                    .addRule(Bcg, co, Vcc, cc)
                    .addRule(Bcg, co, Vcu, uc)
                    .addRule(Bcg, go, Vga, ac)
                    .addRule(Bcg, go, Vgg, gc)
                    .addRule(Bcg, uo, Vuc, cc)
                    .addRule(Bcg, uo, Vuu, uc)

                    .addRule(Bcu, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Bcu, uo, Vua, ac)
                    .addRule(Bcu, co, Vcg, gc)
                    .addRule(Bcu, go, Vgc, cc)
                    .addRule(Bcu, uo, Vug, gc)
                    .addRule(Bcu, go, Vgu, uc)

                    .addRule(Bcu, ao, Vaa, ac)//non canonical lhs, non canonical rhs
                    .addRule(Bcu, ao, Vac, cc)
                    .addRule(Bcu, ao, Vag, gc)
                    .addRule(Bcu, co, Vca, ac)
                    .addRule(Bcu, co, Vcc, cc)
                    .addRule(Bcu, co, Vcu, uc)
                    .addRule(Bcu, go, Vga, ac)
                    .addRule(Bcu, go, Vgg, gc)
                    .addRule(Bcu, uo, Vuc, cc)
                    .addRule(Bcu, uo, Vuu, uc)

                    .addRule(Bga, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Bga, uo, Vua, ac)
                    .addRule(Bga, co, Vcg, gc)
                    .addRule(Bga, go, Vgc, cc)
                    .addRule(Bga, uo, Vug, gc)
                    .addRule(Bga, go, Vgu, uc)

                    .addRule(Bga, ao, Vaa, ac)//non canonical lhs, non canonical rhs
                    .addRule(Bga, ao, Vac, cc)
                    .addRule(Bga, ao, Vag, gc)
                    .addRule(Bga, co, Vca, ac)
                    .addRule(Bga, co, Vcc, cc)
                    .addRule(Bga, co, Vcu, uc)
                    .addRule(Bga, go, Vga, ac)
                    .addRule(Bga, go, Vgg, gc)
                    .addRule(Bga, uo, Vuc, cc)
                    .addRule(Bga, uo, Vuu, uc)

                    .addRule(Bgc, ao, Vaa, ac)//canonical lhs, non canonical rhs
                    .addRule(Bgc, ao, Vac, cc)
                    .addRule(Bgc, ao, Vag, gc)
                    .addRule(Bgc, co, Vca, ac)
                    .addRule(Bgc, co, Vcc, cc)
                    .addRule(Bgc, co, Vcu, uc)
                    .addRule(Bgc, go, Vga, ac)
                    .addRule(Bgc, go, Vgg, gc)
                    .addRule(Bgc, uo, Vuc, cc)
                    .addRule(Bgc, uo, Vuu, uc)

                    .addRule(Bgg, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Bgg, uo, Vua, ac)
                    .addRule(Bgg, co, Vcg, gc)
                    .addRule(Bgg, go, Vgc, cc)
                    .addRule(Bgg, uo, Vug, gc)
                    .addRule(Bgg, go, Vgu, uc)

                    .addRule(Bgg, ao, Vaa, ac)//non canonical lhs, non canonical rhs
                    .addRule(Bgg, ao, Vac, cc)
                    .addRule(Bgg, ao, Vag, gc)
                    .addRule(Bgg, co, Vca, ac)
                    .addRule(Bgg, co, Vcc, cc)
                    .addRule(Bgg, co, Vcu, uc)
                    .addRule(Bgg, go, Vga, ac)
                    .addRule(Bgg, go, Vgg, gc)
                    .addRule(Bgg, uo, Vuc, cc)
                    .addRule(Bgg, uo, Vuu, uc)

                    .addRule(Bgu, ao, Vaa, ac)//canonical lhs, non canonical rhs
                    .addRule(Bgu, ao, Vac, cc)
                    .addRule(Bgu, ao, Vag, gc)
                    .addRule(Bgu, co, Vca, ac)
                    .addRule(Bgu, co, Vcc, cc)
                    .addRule(Bgu, co, Vcu, uc)
                    .addRule(Bgu, go, Vga, ac)
                    .addRule(Bgu, go, Vgg, gc)
                    .addRule(Bgu, uo, Vuc, cc)
                    .addRule(Bgu, uo, Vuu, uc)

                    .addRule(Bua, ao, Vaa, ac)//canonical lhs, non canonical rhs
                    .addRule(Bua, ao, Vac, cc)
                    .addRule(Bua, ao, Vag, gc)
                    .addRule(Bua, co, Vca, ac)
                    .addRule(Bua, co, Vcc, cc)
                    .addRule(Bua, co, Vcu, uc)
                    .addRule(Bua, go, Vga, ac)
                    .addRule(Bua, go, Vgg, gc)
                    .addRule(Bua, uo, Vuc, cc)
                    .addRule(Bua, uo, Vuu, uc)


                    .addRule(Buc, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Buc, uo, Vua, ac)
                    .addRule(Buc, co, Vcg, gc)
                    .addRule(Buc, go, Vgc, cc)
                    .addRule(Buc, uo, Vug, gc)
                    .addRule(Buc, go, Vgu, uc)

                    .addRule(Buc, ao, Vaa, ac)//non canonical lhs, non canonical rhs
                    .addRule(Buc, ao, Vac, cc)
                    .addRule(Buc, ao, Vag, gc)
                    .addRule(Buc, co, Vca, ac)
                    .addRule(Buc, co, Vcc, cc)
                    .addRule(Buc, co, Vcu, uc)
                    .addRule(Buc, go, Vga, ac)
                    .addRule(Buc, go, Vgg, gc)
                    .addRule(Buc, uo, Vuc, cc)
                    .addRule(Buc, uo, Vuu, uc)

                    .addRule(Bug, ao, Vaa, ac)//canonical lhs, non canonical rhs
                    .addRule(Bug, ao, Vac, cc)
                    .addRule(Bug, ao, Vag, gc)
                    .addRule(Bug, co, Vca, ac)
                    .addRule(Bug, co, Vcc, cc)
                    .addRule(Bug, co, Vcu, uc)
                    .addRule(Bug, go, Vga, ac)
                    .addRule(Bug, go, Vgg, gc)
                    .addRule(Bug, uo, Vuc, cc)
                    .addRule(Bug, uo, Vuu, uc)

                    .addRule(Buu, ao, Vau, uc)//non canonical lhs to canonical rhs
                    .addRule(Buu, uo, Vua, ac)
                    .addRule(Buu, co, Vcg, gc)
                    .addRule(Buu, go, Vgc, cc)
                    .addRule(Buu, uo, Vug, gc)
                    .addRule(Buu, go, Vgu, uc)

                    .addRule(Buu, ao, Vaa, ac)//non canonical lhs, non canonical rhs
                    .addRule(Buu, ao, Vac, cc)
                    .addRule(Buu, ao, Vag, gc)
                    .addRule(Buu, co, Vca, ac)
                    .addRule(Buu, co, Vcc, cc)
                    .addRule(Buu, co, Vcu, uc)
                    .addRule(Buu, go, Vga, ac)
                    .addRule(Buu, go, Vgg, gc)
                    .addRule(Buu, uo, Vuc, cc)
                    .addRule(Buu, uo, Vuu, uc)


                    .addRule(Vaa,  Baa)

                    .addRule(Vac,  Bac)

                    .addRule(Vag,  Bag)

                    .addRule(Vca,  Bca)

                    .addRule(Vcc, Bcc)

                    .addRule(Vcu, Bcu)

                    .addRule(Vcg,  Bcg)

                    .addRule(Vga, Bga)

                    .addRule(Vgg, Bgg)

                    .addRule(Vuc,  Buc)

                    .addRule(Vuu,  Buu)


                    .addRule(Vaa,  N)

                    .addRule(Vac,  N)

                    .addRule(Vag,  N)

                    .addRule(Vca,  N)

                    .addRule(Vcc,  N)

                    .addRule(Vcu, N)

                    .addRule(Vcg,  N)

                    .addRule(Vga, N)

                    .addRule(Vgg,  N)

                    .addRule(Vuc,  N)

                    .addRule(Vuu,  N);
        }
                G= Gb.build();

        /*
        System.out.println(
                "G = " + G);*/
            //assignProbs.put(Rule.create(semiring, 1.0, NonTerminal.START, S), 1.0); // should not be needed

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


    public String getName()
    {
        return name;
    }
}

package compression.samplegrammars;

import compression.LocalConfig;
import compression.grammar.PairOfChar;
import compression.grammar.PairOfCharTerminal;
import org.leibnizcenter.cfg.algebra.semiring.dbl.CountingExprSemiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;
import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;
import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArbitraryGrammar implements SampleGrammar {

	private final Grammar<PairOfChar> G;
	//private final Map<Rule, Double> assignProbs;
	private final NonTerminal S;

	boolean withNonCanonicalRules;

	public Map<String, NonTerminal> stringNonTerminalMap;
	public Map< String, Category> stringCategoryMap;

	//public String fileName;
	public String name="ArbitraryGrammar";

	public ArbitraryGrammar(boolean withNonCanonicalRules)  {
		this.withNonCanonicalRules=withNonCanonicalRules;

		ExpressionSemiring semiring = CountingExprSemiring.get();

		//fileName = LocalConfig.ArbitraryFile;

		S = new NonTerminal("S");
		NonTerminal L = new NonTerminal("L");

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


		Grammar.Builder<PairOfChar> Gb = new Grammar.Builder<PairOfChar>("Arbitrary Grammar")
				.withSemiring(semiring)
				//-----------line 1
				.addRule(S, L, S)
				.addRule(S, L)

				//-------------line 2
				.addRule(L, ao, Pau, uc)
				.addRule(L, uo, Pua, ac)
				.addRule(L, go, Pgc, cc)
				.addRule(L, co, Pcg, gc)
				.addRule(L, uo, Pug, gc)
				.addRule(L, go, Pgu, uc)

				.addRule(L, au)
				.addRule(L, cu)
				.addRule(L, gu)
				.addRule(L, uu)

				//------------line 3
				.addRule(Pau, ao, Pau, uc)
				.addRule(Pau, uo, Pua, ac)
				.addRule(Pau, co, Pcg, gc)
				.addRule(Pau, go, Pgc, cc)
				.addRule(Pau, uo, Pug, gc)
				.addRule(Pau, go, Pgu, uc)

				.addRule(Pua, ao, Pau, uc)
				.addRule(Pua, uo, Pua, ac)
				.addRule(Pua, co, Pcg, gc)
				.addRule(Pua, go, Pgc, cc)
				.addRule(Pua, uo, Pug, gc)
				.addRule(Pua, go, Pgu, uc)

				.addRule(Pgc, ao, Pau, uc)
				.addRule(Pgc, uo, Pua, ac)
				.addRule(Pgc, co, Pcg, gc)
				.addRule(Pgc, go, Pgc, cc)
				.addRule(Pgc, uo, Pug, gc)
				.addRule(Pgc, go, Pgu, uc)

				.addRule(Pcg, ao, Pau, uc)
				.addRule(Pcg, uo, Pua, ac)
				.addRule(Pcg, co, Pcg, gc)
				.addRule(Pcg, go, Pgc, cc)
				.addRule(Pcg, uo, Pug, gc)
				.addRule(Pcg, go, Pgu, uc)

				.addRule(Pgu, ao, Pau, uc)
				.addRule(Pgu, uo, Pua, ac)
				.addRule(Pgu, co, Pcg, gc)
				.addRule(Pgu, go, Pgc, cc)
				.addRule(Pgu, uo, Pug, gc)
				.addRule(Pgu, go, Pgu, uc)

				.addRule(Pug, ao, Pau, uc)
				.addRule(Pug, uo, Pua, ac)
				.addRule(Pug, co, Pcg, gc)
				.addRule(Pug, go, Pgc, cc)
				.addRule(Pug, uo, Pug, gc)
				.addRule(Pug, go, Pgu, uc)

				.addRule(Pau, S)
				.addRule(Pua, S)
				.addRule(Pgc, S)
				.addRule(Pcg, S)
				.addRule(Pug, S)
				.addRule(Pgu, S);

		if(withNonCanonicalRules){
			Gb
					.addRule(L, ao, Paa, ac)
					.addRule(L, ao, Pac, cc)
					.addRule(L, ao, Pag, gc)
					.addRule(L, co, Pca, ac)
					.addRule(L, co, Pcc, cc)
					.addRule(L, co, Pcu, uc)
					.addRule(L, go, Pga, ac)
					.addRule(L, go, Pgg, gc)
					.addRule(L, uo, Puc, cc)
					.addRule(L, uo, Puu, uc)

					.addRule(Paa, ao, Paa, ac)//non canonical lhs to non canonical rhs
					.addRule(Paa, ao, Pac, cc)
					.addRule(Paa, ao, Pag, gc)
					.addRule(Paa, co, Pca, ac)
					.addRule(Paa, co, Pca, ac)
					.addRule(Paa, co, Pcc, cc)
					.addRule(Paa, co, Pcu, uc)
					.addRule(Paa, go, Pga, ac)
					.addRule(Paa, go, Pgg, gc)
					.addRule(Paa, uo, Puc, cc)
					.addRule(Paa, uo, Puu, uc)

					.addRule(Paa, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Paa, uo, Pua, ac)
					.addRule(Paa, co, Pcg, gc)
					.addRule(Paa, go, Pgc, cc)
					.addRule(Paa, uo, Pug, gc)
					.addRule(Paa, go, Pgu, uc)

					.addRule(Pac, ao, Paa, ac)//non canonical lhs to non canonical rhs
					.addRule(Pac, ao, Pac, cc)
					.addRule(Pac, ao, Pag, gc)
					.addRule(Pac, co, Pca, ac)
					.addRule(Pac, co, Pcc, cc)
					.addRule(Pac, co, Pcu, uc)
					.addRule(Pac, go, Pga, ac)
					.addRule(Pac, go, Pgg, gc)
					.addRule(Pac, uo, Puc, cc)
					.addRule(Pac, uo, Puu, uc)

					.addRule(Pac, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Pac, uo, Pua, ac)
					.addRule(Pac, co, Pcg, gc)
					.addRule(Pac, go, Pgc, cc)
					.addRule(Pac, uo, Pug, gc)
					.addRule(Pac, go, Pgu, uc)

					.addRule(Pag, ao, Paa, ac)//non canonical lhs to non canonical rhs
					.addRule(Pag, ao, Pac, cc)
					.addRule(Pag, ao, Pag, gc)
					.addRule(Pag, co, Pca, ac)
					.addRule(Pag, co, Pcc, cc)
					.addRule(Pag, co, Pcu, uc)
					.addRule(Pag, go, Pga, ac)
					.addRule(Pag, go, Pgg, gc)
					.addRule(Pag, uo, Puc, cc)
					.addRule(Pag, uo, Puu, uc)

					.addRule(Pag, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Pag, uo, Pua, ac)
					.addRule(Pag, co, Pcg, gc)
					.addRule(Pag, go, Pgc, cc)
					.addRule(Pag, uo, Pug, gc)
					.addRule(Pag, go, Pgu, uc)

					.addRule(Pau, ao, Paa, ac)//canonical lhs, non canonical rhs
					.addRule(Pau, ao, Pac, cc)
					.addRule(Pau, ao, Pag, gc)
					.addRule(Pau, co, Pca, ac)
					.addRule(Pau, co, Pcc, cc)
					.addRule(Pau, co, Pcu, uc)
					.addRule(Pau, go, Pga, ac)
					.addRule(Pau, go, Pgg, gc)
					.addRule(Pau, uo, Puc, cc)
					.addRule(Pau, uo, Puu, uc)

					.addRule(Pca, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Pca, uo, Pua, ac)
					.addRule(Pca, co, Pcg, gc)
					.addRule(Pca, go, Pgc, cc)
					.addRule(Pca, uo, Pug, gc)
					.addRule(Pca, go, Pgu, uc)

					.addRule(Pca, ao, Paa, ac)//non canonical lhs, non canonical rhs
					.addRule(Pca, ao, Pac, cc)
					.addRule(Pca, ao, Pag, gc)
					.addRule(Pca, co, Pca, ac)
					.addRule(Pca, co, Pcc, cc)
					.addRule(Pca, co, Pcu, uc)
					.addRule(Pca, go, Pga, ac)
					.addRule(Pca, go, Pgg, gc)
					.addRule(Pca, uo, Puc, cc)
					.addRule(Pca, uo, Puu, uc)

					.addRule(Pcc, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Pcc, uo, Pua, ac)
					.addRule(Pcc, co, Pcg, gc)
					.addRule(Pcc, go, Pgc, cc)
					.addRule(Pcc, uo, Pug, gc)
					.addRule(Pcc, go, Pgu, uc)

					.addRule(Pcc, ao, Paa, ac)//non canonical lhs, non canonical rhs
					.addRule(Pcc, ao, Pac, cc)
					.addRule(Pcc, ao, Pag, gc)
					.addRule(Pcc, co, Pca, ac)
					.addRule(Pcc, co, Pcc, cc)
					.addRule(Pcc, co, Pcu, uc)
					.addRule(Pcc, go, Pga, ac)
					.addRule(Pcc, go, Pgg, gc)
					.addRule(Pcc, uo, Puc, cc)
					.addRule(Pcc, uo, Puu, uc)

					.addRule(Pcg, ao, Paa, ac)// canonical lhs, non canonical rhs
					.addRule(Pcg, ao, Pac, cc)
					.addRule(Pcg, ao, Pag, gc)
					.addRule(Pcg, co, Pca, ac)
					.addRule(Pcg, co, Pcc, cc)
					.addRule(Pcg, co, Pcu, uc)
					.addRule(Pcg, go, Pga, ac)
					.addRule(Pcg, go, Pgg, gc)
					.addRule(Pcg, uo, Puc, cc)
					.addRule(Pcg, uo, Puu, uc)

					.addRule(Pcu, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Pcu, uo, Pua, ac)
					.addRule(Pcu, co, Pcg, gc)
					.addRule(Pcu, go, Pgc, cc)
					.addRule(Pcu, uo, Pug, gc)
					.addRule(Pcu, go, Pgu, uc)

					.addRule(Pcu, ao, Paa, ac)//non canonical lhs, non canonical rhs
					.addRule(Pcu, ao, Pac, cc)
					.addRule(Pcu, ao, Pag, gc)
					.addRule(Pcu, co, Pca, ac)
					.addRule(Pcu, co, Pcc, cc)
					.addRule(Pcu, co, Pcu, uc)
					.addRule(Pcu, go, Pga, ac)
					.addRule(Pcu, go, Pgg, gc)
					.addRule(Pcu, uo, Puc, cc)
					.addRule(Pcu, uo, Puu, uc)

					.addRule(Pga, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Pga, uo, Pua, ac)
					.addRule(Pga, co, Pcg, gc)
					.addRule(Pga, go, Pgc, cc)
					.addRule(Pga, uo, Pug, gc)
					.addRule(Pga, go, Pgu, uc)

					.addRule(Pga, ao, Paa, ac)//non canonical lhs, non canonical rhs
					.addRule(Pga, ao, Pac, cc)
					.addRule(Pga, ao, Pag, gc)
					.addRule(Pga, co, Pca, ac)
					.addRule(Pga, co, Pcc, cc)
					.addRule(Pga, co, Pcu, uc)
					.addRule(Pga, go, Pga, ac)
					.addRule(Pga, go, Pgg, gc)
					.addRule(Pga, uo, Puc, cc)
					.addRule(Pga, uo, Puu, uc)

					.addRule(Pgc, ao, Paa, ac)//canonical lhs, non canonical rhs
					.addRule(Pgc, ao, Pac, cc)
					.addRule(Pgc, ao, Pag, gc)
					.addRule(Pgc, co, Pca, ac)
					.addRule(Pgc, co, Pcc, cc)
					.addRule(Pgc, co, Pcu, uc)
					.addRule(Pgc, go, Pga, ac)
					.addRule(Pgc, go, Pgg, gc)
					.addRule(Pgc, uo, Puc, cc)
					.addRule(Pgc, uo, Puu, uc)

					.addRule(Pgg, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Pgg, uo, Pua, ac)
					.addRule(Pgg, co, Pcg, gc)
					.addRule(Pgg, go, Pgc, cc)
					.addRule(Pgg, uo, Pug, gc)
					.addRule(Pgg, go, Pgu, uc)

					.addRule(Pgg, ao, Paa, ac)//non canonical lhs, non canonical rhs
					.addRule(Pgg, ao, Pac, cc)
					.addRule(Pgg, ao, Pag, gc)
					.addRule(Pgg, co, Pca, ac)
					.addRule(Pgg, co, Pcc, cc)
					.addRule(Pgg, co, Pcu, uc)
					.addRule(Pgg, go, Pga, ac)
					.addRule(Pgg, go, Pgg, gc)
					.addRule(Pgg, uo, Puc, cc)
					.addRule(Pgg, uo, Puu, uc)

					.addRule(Pgu, ao, Paa, ac)//canonical lhs, non canonical rhs
					.addRule(Pgu, ao, Pac, cc)
					.addRule(Pgu, ao, Pag, gc)
					.addRule(Pgu, co, Pca, ac)
					.addRule(Pgu, co, Pcc, cc)
					.addRule(Pgu, co, Pcu, uc)
					.addRule(Pgu, go, Pga, ac)
					.addRule(Pgu, go, Pgg, gc)
					.addRule(Pgu, uo, Puc, cc)
					.addRule(Pgu, uo, Puu, uc)

					.addRule(Pua, ao, Paa, ac)//canonical lhs, non canonical rhs
					.addRule(Pua, ao, Pac, cc)
					.addRule(Pua, ao, Pag, gc)
					.addRule(Pua, co, Pca, ac)
					.addRule(Pua, co, Pcc, cc)
					.addRule(Pua, co, Pcu, uc)
					.addRule(Pua, go, Pga, ac)
					.addRule(Pua, go, Pgg, gc)
					.addRule(Pua, uo, Puc, cc)
					.addRule(Pua, uo, Puu, uc)


					.addRule(Puc, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Puc, uo, Pua, ac)
					.addRule(Puc, co, Pcg, gc)
					.addRule(Puc, go, Pgc, cc)
					.addRule(Puc, uo, Pug, gc)
					.addRule(Puc, go, Pgu, uc)

					.addRule(Puc, ao, Paa, ac)//non canonical lhs, non canonical rhs
					.addRule(Puc, ao, Pac, cc)
					.addRule(Puc, ao, Pag, gc)
					.addRule(Puc, co, Pca, ac)
					.addRule(Puc, co, Pcc, cc)
					.addRule(Puc, co, Pcu, uc)
					.addRule(Puc, go, Pga, ac)
					.addRule(Puc, go, Pgg, gc)
					.addRule(Puc, uo, Puc, cc)
					.addRule(Puc, uo, Puu, uc)

					.addRule(Pug, ao, Paa, ac)//canonical lhs, non canonical rhs
					.addRule(Pug, ao, Pac, cc)
					.addRule(Pug, ao, Pag, gc)
					.addRule(Pug, co, Pca, ac)
					.addRule(Pug, co, Pcc, cc)
					.addRule(Pug, co, Pcu, uc)
					.addRule(Pug, go, Pga, ac)
					.addRule(Pug, go, Pgg, gc)
					.addRule(Pug, uo, Puc, cc)
					.addRule(Pug, uo, Puu, uc)

					.addRule(Puc, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Puc, uo, Pua, ac)
					.addRule(Puc, co, Pcg, gc)
					.addRule(Puc, go, Pgc, cc)
					.addRule(Puc, uo, Pug, gc)
					.addRule(Puc, go, Pgu, uc)

					.addRule(Puc, ao, Paa, ac)//non canonical lhs, non canonical rhs
					.addRule(Puc, ao, Pac, cc)
					.addRule(Puc, ao, Pag, gc)
					.addRule(Puc, co, Pca, ac)
					.addRule(Puc, co, Pcc, cc)
					.addRule(Puc, co, Pcu, uc)
					.addRule(Puc, go, Pga, ac)
					.addRule(Puc, go, Pgg, gc)
					.addRule(Puc, uo, Puc, cc)
					.addRule(Puc, uo, Puu, uc)

					.addRule(Puu, ao, Pau, uc)//non canonical lhs to canonical rhs
					.addRule(Puu, uo, Pua, ac)
					.addRule(Puu, co, Pcg, gc)
					.addRule(Puu, go, Pgc, cc)
					.addRule(Puu, uo, Pug, gc)
					.addRule(Puu, go, Pgu, uc)

					.addRule(Puu, ao, Paa, ac)//non canonical lhs, non canonical rhs
					.addRule(Puu, ao, Pac, cc)
					.addRule(Puu, ao, Pag, gc)
					.addRule(Puu, co, Pca, ac)
					.addRule(Puu, co, Pcc, cc)
					.addRule(Puu, co, Pcu, uc)
					.addRule(Puu, go, Pga, ac)
					.addRule(Puu, go, Pgg, gc)
					.addRule(Puu, uo, Puc, cc)
					.addRule(Puu, uo, Puu, uc)

					.addRule(Paa, S)
					.addRule(Pac, S)
					.addRule(Pag, S)
					.addRule(Pca, S)
					.addRule(Pcc, S)
					.addRule(Pcu, S)

					.addRule(Pga, S)
					.addRule(Pgg, S)
					.addRule(Puc, S)
					.addRule(Puu, S);

		}
		G=Gb.build();


	}


	/*
	public void addToAssignProbs(String str){
		//System.out.println("printing non Terminal map"+stringNonTerminalMap);
		//System.out.println("printing string category map"+stringCategoryMap);
		System.out.print(str);
		CategoryMaps CM = new CategoryMaps();
		System.out.println("non terminal substring is: "+str.substring(0,str.indexOf('\u2192')-1)+"length: "+str.substring(0,str.indexOf('\u2192')-1).length());
		NonTerminal nt= CM.stringNonTerminalMap.get(str.substring(0,str.indexOf('\u2192')-1));
		System.out.println("printing nt"+ nt);
		int i=str.indexOf('\u2192')+1;//obtains the index position after the right arrow character
		ArrayList<Category> rhs= new ArrayList<>();

		Double probForRule=Double.valueOf(0.0);
		Double probForAssign=Double.valueOf(0.0);

		//System.out.println(str);

		while(i<str.length()){
			//System.out.println("VALUE OF i is "+i);
			if(str.charAt(i)=='(')
			{
				probForRule=Double.parseDouble( str.substring(i+1, str.lastIndexOf(')')));
				//System.out.println(probForRule);
				i=str.lastIndexOf(')');
			}
			else if(str.charAt(i)=='<')
			{
				//System.out.println(str.substring(i,i+5));
				//System.out.println(stringCategoryMap.get(str.substring(i,i+5)));
				rhs.add(CM.stringCategoryMap.get(str.substring(i,i+5)));

				i+=5;
			}
			else if(str.charAt(i)==':'){
				probForAssign=Double.parseDouble(str.substring(i+1));
				i=str.length();
				//System.out.println(probForAssign);
				break;
			}
			else if(str.charAt(i)==' ' || str.charAt(i)==')'){
				i++;
			}
			else
			{
				//System.out.println("PRINTING OUT SUBSTRING"+str.substring(i,i+1));
				int r=0;
				while(str.charAt(i+r)!=' '){r++;}//used to obtain the length of the non-terminal symbol

				rhs.add(CM.stringNonTerminalMap.get(str.substring(i,i+r)));//
				i=i+r+1;

			}
		}
		System.out.println(rhs+" "+nt+" "+probForRule+" "+probForAssign);
		Category[] catArray = rhs.toArray(new Category[0]);
		assignProbs.put(Rule.create(G.semiring,probForRule, nt, catArray), probForAssign);
	}*/

	@Override
	public boolean isWithNoncanonicalRules() {
		return withNonCanonicalRules;
	}

	@Override
	public NonTerminal getStartSymbol() {
		return S;
	}

	public Grammar<PairOfChar> getGrammar() {
		return G;
	}



	//public String getFileName(){return fileName;}
	public String getName (){
		return name;
	}

}

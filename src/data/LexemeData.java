package data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import others.GramaticRegex;

public class LexemeData {
	List<String> executionData;
	GramaticData gramaticData;
	List<String> lexemeData;
	List<Integer> tokensResult;

	public LexemeData(List<String> pExecutionData, GramaticData pGramaticData) {
		executionData = pExecutionData;
		gramaticData = pGramaticData;
		findTokens();
		transformeTokensInCods();
	}

	private void findTokens() {
		lexemeData = new ArrayList<>();

		Pattern gramaticPattern = GramaticRegex.GenericGramaticRegex;

		for (String linha : executionData) {
			Matcher matcher = gramaticPattern.matcher(linha);

			while (matcher.find()) {
				lexemeData.add(matcher.group());
			}
		}
	}

	private void transformeTokensInCods() {
		tokensResult = new ArrayList<Integer>();

		Pattern identPattern = GramaticRegex.IdentGramaticRegex;
		Pattern nintPattern = GramaticRegex.NintGramaticRegex;
		Pattern nrealPattern = GramaticRegex.NrealGramaticRegex;
		
		System.out.println(lexemeData);
		
		for (int i = 0; i < lexemeData.size(); i++) {
			String lexema = lexemeData.get(i);
			int cod = gramaticData.SearchToken(lexema);

			switch (cod) {
			case 0:
				Matcher identMatcher = identPattern.matcher(lexema);
				Matcher nintMatcher = nintPattern.matcher(lexema);
				
				if (identMatcher.find()) {
					tokensResult.add(gramaticData.SearchToken("ident"));
				} else if(nintMatcher.find()){
					String nextlexema = lexemeData.get(i+1);
					int nextcod = gramaticData.SearchToken(nextlexema);
					if(nextcod == 35) {
						nextlexema = lexema.concat(lexemeData.get(i+1) + lexemeData.get(i+2));
						Matcher nrealMatcher = nrealPattern.matcher(nextlexema);
						if(nrealMatcher.find()) {
							tokensResult.add(gramaticData.SearchToken("nreal"));
							i = i + 2;
							continue;
						} else {
							System.err.println("Erro! - Lexema nao encotrado " + nextlexema);
						}
					}
					tokensResult.add(gramaticData.SearchToken("nint"));
				} else {
					System.err.println("Erro! - Lexema nao encotrado " + lexema);
				}
				break;
			case 25:
			case 29:
			case 33:
				String nextlexema = lexema.concat(lexemeData.get(i+1));
				int codlexema = gramaticData.SearchToken(nextlexema);
				if(codlexema != 0) {
					tokensResult.add(codlexema);
					i++;
					continue;
				}
			default:
				tokensResult.add(cod);
				break;
			}
		}
	}
	
	public List<Integer> getCods(){
		return tokensResult;
	}
}
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

		for (String lexema : lexemeData) {
			int cod = gramaticData.SearchToken(lexema);

			switch (cod) {
			case 0:
				Matcher identMatcher = identPattern.matcher(lexema);
				if (identMatcher.find()) {
					tokensResult.add(gramaticData.SearchToken("ident"));
				}
				break;
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
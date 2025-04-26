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
	List<Integer> tokensResultInteger = new ArrayList<Integer>();;
	List<String> tokensResultString = new ArrayList<String>();;

	public LexemeData(List<String> pExecutionData, GramaticData pGramaticData) {
		executionData = pExecutionData;
		gramaticData = pGramaticData;
		InformaExecFile();
		findTokens();
		InformaTransformCodToTokens();
	}

	private void findTokens() {
		lexemeData = new ArrayList<>();

		Pattern gramaticPattern = GramaticRegex.GenericGramaticRegex;

		for (int i = 0; i < executionData.size(); i++) {
		    String linha = executionData.get(i);
		    Matcher matcher = gramaticPattern.matcher(linha);
		    while (matcher.find()) {
		        lexemeData.add(matcher.group());
		    }
		    transformeTokensInCods(i+1);
		    lexemeData.clear();
		}
	}

	private void transformeTokensInCods(int line) {
		Pattern identPattern = GramaticRegex.IdentGramaticRegex;
		Pattern nintPattern = GramaticRegex.NintGramaticRegex;
		Pattern nrealPattern = GramaticRegex.NrealGramaticRegex;
		
		for (int i = 0; i < lexemeData.size(); i++) {
			String lexema = lexemeData.get(i);
			int cod = gramaticData.SearchToken(lexema);

			switch (cod) {
			case 0:
				Matcher identMatcher = identPattern.matcher(lexema);
				Matcher nintMatcher = nintPattern.matcher(lexema);
				
				if (identMatcher.find()) {
					int codIdent = gramaticData.SearchToken("ident");
					tokensResultInteger.add(codIdent);
					tokensResultString.add("Token: " + codIdent + " - lexema: " + lexema + " - linha: " + line);
				} else if(nintMatcher.find()){
					String nextlexema = lexemeData.get(i+1);
					int nextcod = gramaticData.SearchToken(nextlexema);
					if(nextcod == 35) {
						nextlexema = lexema.concat(lexemeData.get(i+1) + lexemeData.get(i+2));
						Matcher nrealMatcher = nrealPattern.matcher(nextlexema);
						if(nrealMatcher.find()) {
							int codNreal = gramaticData.SearchToken("nreal");
							tokensResultInteger.add(codNreal);
							tokensResultString.add("Token: " + codNreal + " - lexema: " + nextlexema + " - linha: " + line);
							i = i + 2;
							continue;
						} else {
							System.err.println("Erro! - Lexema nao encotrado " + nextlexema + "  - linha: " + line);
						}
					}
					int codNit = gramaticData.SearchToken("nint");
					tokensResultInteger.add(codNit);
					tokensResultString.add("Token: " + codNit + " - lexema: " + nextlexema + " - linha: " + line);
				} else {
					System.err.println("Erro! - Lexema nao encotrado " + lexema + " linha: " + line);
				}
				break;
			case 25:
			case 29:
			case 33:
				String nextlexema = lexema.concat(lexemeData.get(i+1));
				int codlexema = gramaticData.SearchToken(nextlexema);
				if(codlexema != 0) {
					tokensResultInteger.add(codlexema);
					tokensResultString.add("Token: " + codlexema + " - lexema: " + nextlexema + " - linha: " + line);
					i++;
					continue;
				}
			default:
				tokensResultInteger.add(cod);
				tokensResultString.add("Token: " + cod + " - lexema: " + lexema + " - linha: " + line);
				break;
			}
		}
	}
	public void InformaExecFile() {
		System.out.println("Seguinte arquivo de execução foi lido: \n");
		for (String string : executionData) {
			System.out.println(string);
		}
		System.out.println();
	}
	public void InformaTransformCodToTokens() {
		System.out.println("O arquivo de execução foi transformado na sequinte lista de tokens: \n");
		for (String string : tokensResultString) {
			System.out.println(string);
		}
		System.out.println();
	}
	public List<Integer> getCods(){
		return tokensResultInteger;
	}
}
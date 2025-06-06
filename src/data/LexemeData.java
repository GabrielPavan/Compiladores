package data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import others.GramaticRegex;

public class LexemeData {
	private List<String> executionData;
	private GramaticData gramaticData;
	private List<String> lexemeData;
	
	private List<Token> tokens;

	public LexemeData(List<String> pExecutionData, GramaticData pGramaticData) {
		this.executionData = pExecutionData;
		this.gramaticData = pGramaticData;
		
		this.tokens = new ArrayList<>();
		
		System.out.println("--- Iniciando Análise Léxica ---");
		findTokens();
		System.out.println("--- Análise Léxica Concluída ---\n");
	}

	public List<Token> getTokens() {
		this.tokens.add(new Token("$", "$", executionData.size())); 
		return this.tokens;
	}

	private void findTokens() {
		this.lexemeData = new ArrayList<>();
		Pattern gramaticPattern = GramaticRegex.GenericGramaticRegex;

		for (int i = 0; i < executionData.size(); i++) {
		    String linha = executionData.get(i);
		    Matcher matcher = gramaticPattern.matcher(linha);
		    while (matcher.find()) {
		        lexemeData.add(matcher.group());
		    }
		    transformeTokensInCods(i + 1);
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
				
					this.tokens.add(new Token("ident", lexema, line));
				} else if(nintMatcher.find()){
					if ((i + 2) < lexemeData.size() && lexemeData.get(i + 1).equals(".")) {
						String lexemaRealCompleto = lexema + lexemeData.get(i + 1) + lexemeData.get(i + 2);
						Matcher nrealMatcher = nrealPattern.matcher(lexemaRealCompleto);
						if (nrealMatcher.find()) {
							this.tokens.add(new Token("nreal", lexemaRealCompleto, line));
							i += 2;
						} else {
							
							this.tokens.add(new Token("nint", lexema, line));
						}
					} else {
						
						this.tokens.add(new Token("nint", lexema, line));
					}
				} else {
					
					System.err.println("Erro Léxico! - Lexema '" + lexema + "' não reconhecido na linha: " + line);
				}
				break;
			
			
			case 25: // >
			case 29: // <
			case 33: // :
				if ((i + 1) < lexemeData.size()) {
					String nextChar = lexemeData.get(i + 1);
					String lexemaComposto = lexema + nextChar;
					int codComposto = gramaticData.SearchToken(lexemaComposto);
					if (codComposto != 0) {
						this.tokens.add(new Token(lexemaComposto, lexemaComposto, line));
						i++;
					} else {
						
						this.tokens.add(new Token(lexema, lexema, line));
					}
				} else {
					this.tokens.add(new Token(lexema, lexema, line));
				}
				break;
			
			default:
				this.tokens.add(new Token(lexema, lexema, line));
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
}
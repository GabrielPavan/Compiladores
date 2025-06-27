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
		Pattern literalPattern = GramaticRegex.LiteralGramaticRegex;

		for (int i = 0; i < lexemeData.size(); i++) {
			String lexema = lexemeData.get(i);
			int cod = gramaticData.SearchToken(lexema);

			if (cod != 0) {
				// É uma palavra reservada ou um símbolo simples.
				this.tokens.add(new Token(lexema, lexema, line));
			} else {
				// Não é palavra reservada, vamos classificar. A ordem de verificação é crucial.

				// 1. É um número real? Requer olhar para frente (lookahead).
				if (nintPattern.matcher(lexema).matches() && (i + 2) < lexemeData.size() && lexemeData.get(i + 1).equals(".")) {
					// Pega a parte fracionária e verifica se também é um número
					String parteFracionaria = lexemeData.get(i + 2);
					if (nintPattern.matcher(parteFracionaria).matches()) {
						String possibleNreal = lexema + "." + parteFracionaria;
						// Agora verifica se a string completa corresponde ao padrão nreal
						if (nrealPattern.matcher(possibleNreal).matches()) {
							this.tokens.add(new Token("nreal", possibleNreal, line));
							i += 2; // Crucial: Pula os tokens '.' e a parte fracionária que já foram consumidos.
							continue; 
						}
					}
				}

				if (literalPattern.matcher(lexema).matches()) {
					this.tokens.add(new Token("literal", lexema, line));
					continue;
				}

				if (nintPattern.matcher(lexema).matches()) {
					this.tokens.add(new Token("nint", lexema, line));
					continue;
				}

				if (identPattern.matcher(lexema).matches()) {
					this.tokens.add(new Token("ident", lexema, line));
					continue;
				}
				
				System.err.println("Erro Léxico! - Lexema '" + lexema + "' não reconhecido na linha: " + line);
			}
		}
	}
}
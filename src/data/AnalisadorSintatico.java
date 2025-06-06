package data;

import java.util.List;
import java.util.Stack;

public class AnalisadorSintatico {

    private final List<Token> tokens;
    private int tokenIndex;
    private final Stack<String> pilha;
    private final TabelaAnaliseSintatica tabela;

    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenIndex = 0;
        this.pilha = new Stack<>();
        this.tabela = new TabelaAnaliseSintatica();
        
        this.pilha.push("$");
        this.pilha.push("<PROGRAMA>");
    }

    public void analisar() {
        System.out.println("--- Iniciando Análise Sintática ---");

        while (!pilha.isEmpty()) {
            imprimirEstado();

            String topoPilha = pilha.peek();
            Token tokenAtual = tokens.get(tokenIndex);

            if (topoPilha.equals("$") && tokenAtual.codigo.equals("$")) {
                break;
            }

            if (isTerminal(topoPilha)) {
                if (topoPilha.equals(tokenAtual.codigo)) {
                    pilha.pop();
                    tokenIndex++;
                } else {
                    reportarErro("Terminal esperado '" + topoPilha + "', mas encontrado '" + tokenAtual.codigo + "'.");
                    break;
                }
            } else { 
                Integer numeroRegra = tabela.getRegra(topoPilha, tokenAtual.codigo);

                if (numeroRegra != null) {
                    pilha.pop();
                    String[] producao = RegrasProducao.getProducao(numeroRegra);
                    
                    for (int i = producao.length - 1; i >= 0; i--) {
                        if (!producao[i].isEmpty()) {
                            pilha.push(producao[i]);
                        }
                    }
                } else {
                    reportarErro("Não há regra para o não-terminal '" + topoPilha + "' com o token de entrada '" + tokenAtual.codigo + "'.");
                    break;
                }
            }
        }
        
        System.out.println("\n--- Fim da Análise Sintática ---");
        if (pilha.peek().equals("$") && tokens.get(tokenIndex).codigo.equals("$")) {
             System.out.println("Análise concluída com SUCESSO!");
        } else {
             System.out.println("Análise concluída com ERROS.");
        }
    }

    private boolean isTerminal(String simbolo) {
        return !simbolo.startsWith("<") || !simbolo.endsWith(">");
    }

    private void reportarErro(String mensagem) {
        Token tokenErro = tokens.get(tokenIndex);
        System.err.printf("\nERRO SINTÁTICO na linha %d: %s\n", tokenErro.linha, mensagem);
    }
    
    private void imprimirEstado() {
        Token tokenAtual = tokens.get(tokenIndex);
        System.out.println("------------------------------------");
        System.out.printf("Pilha:   %s\n", formatarPilha());
        System.out.printf("Entrada: Código='%s', Lexema='%s', Linha=%d\n", 
            tokenAtual.codigo, tokenAtual.lexema, tokenAtual.linha);
    }
    
    private String formatarPilha() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < pilha.size(); i++) {
            sb.append(pilha.get(i));
            if (i < pilha.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
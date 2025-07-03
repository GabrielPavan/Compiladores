package app;

import data.Token;
import data.TabelaAnaliseSintatica;
import data.RegrasProducao;

import java.util.List;
import java.util.Stack;

public class AnalisadorSintatico {

    private final List<Token> tokens;
    private int tokenIndex;
    private final Stack<String> pilha;
    private final TabelaAnaliseSintatica tabela;
    private AnalisadorSemantico semantico;
    private boolean erroSintaticoEncontrado = false;
    private boolean erroSemanticoEncontradoFlag = false; 

    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenIndex = 0;
        this.pilha = new Stack<>();
        this.tabela = new TabelaAnaliseSintatica();
        this.semantico = new AnalisadorSemantico();
        
        this.pilha.push("$");
        this.pilha.push("<PROGRAMA>");
    }

    public void analisar() {
        System.out.println("--- Iniciando Análise Sintática e Semântica ---");

        while (!pilha.isEmpty()) {
            imprimirEstado();

            String topoPilha = pilha.peek();
            
            if (tokenIndex >= tokens.size()) {
                reportarErro("Fim de arquivo inesperado.", 0);
                break;
            }
            Token tokenAtual = tokens.get(tokenIndex);

            if (topoPilha.equals("$") && tokenAtual.codigo.equals("$")) {
                break;
            }
            
            if (topoPilha.startsWith("#")) {
                executarAcaoSemantica(topoPilha);
                pilha.pop();
                continue; 
            }

            if (isTerminal(topoPilha)) {
                if (topoPilha.equals(tokenAtual.codigo)) {
                    pilha.pop();
                    tokenIndex++;
                } else {
                    reportarErro("Terminal esperado '" + topoPilha + "', mas encontrado '" + tokenAtual.codigo + "'.", tokenAtual.linha);
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
                    reportarErro("Não há regra para o não-terminal '" + topoPilha + "' com o token de entrada '" + tokenAtual.codigo + "'.", tokenAtual.linha);
                    break;
                }
            }
        }
        
        System.out.println("\n--- Fim da Análise ---");
        if (erroSintaticoEncontrado) {
             System.out.println("Análise concluída com ERROS SINTÁTICOS.");
        } else if (erroSemanticoEncontradoFlag) {
             System.out.println("Análise concluída com ERROS SEMÂNTICOS.");
        } else if (pilha.peek().equals("$") && tokens.get(tokenIndex).codigo.equals("$")) {
             System.out.println("Análise concluída com SUCESSO!");
        } else {
             System.out.println("Análise concluída com ERROS INESPERADOS.");
        }
    }
    
    private void executarAcaoSemantica(String acao) {
        Token tokenAnterior = this.tokenIndex > 0 ? tokens.get(tokenIndex - 1) : null;
        Token tokenAtual = tokens.get(tokenIndex);

        if(semantico.executar(acao, tokenAnterior, tokenAtual)){
            this.erroSemanticoEncontradoFlag = true;
        }
    }
    
    private boolean isTerminal(String simbolo) {
        return !simbolo.startsWith("<") || !simbolo.endsWith(">");
    }

    private void reportarErro(String mensagem, int linha) {
        this.erroSintaticoEncontrado = true;
        System.err.printf("\nERRO SINTÁTICO na linha %d: %s\n", linha, mensagem);
    }
    
    private void imprimirEstado() {
        if(erroSintaticoEncontrado) return;
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
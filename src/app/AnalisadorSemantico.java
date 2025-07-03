package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import data.Simbolo;
import data.TabelaDeSimbolos;
import data.Tipo;
import data.Token;

public class AnalisadorSemantico {

    private TabelaDeSimbolos tabelaDeSimbolos;
    private Stack<Tipo> pilhaDeTipos; 
    
    private List<String> listaDeIdentificadores;
    private Tipo tipoAtual;

    public AnalisadorSemantico() {
        this.tabelaDeSimbolos = new TabelaDeSimbolos();
        this.pilhaDeTipos = new Stack<>();
        this.listaDeIdentificadores = new ArrayList<>();
    }
    
    public boolean executar(String acao, Token tokenAnterior, Token tokenAtual) {
        switch (acao) {
            case "#1":
                adicionarIdParaDeclaracao(tokenAnterior.lexema);
                break;
            case "#2":
                setTipoDeclaracao(Tipo.INTEGER);
                break;
            case "#3":
                setTipoDeclaracao(Tipo.REAL);
                break;
            case "#4":
                if (processarDeclaracaoVariavel(tokenAnterior.linha)) {
                    return true;
                }
                break;
            case "#5":
                if (verificarIdentificador(tokenAnterior.lexema, tokenAnterior.linha)) {
                    return true;
                }
                break;
            case "#6_INT":
                empilharTipo(Tipo.INTEGER);
                break;
            case "#6_REAL":
                empilharTipo(Tipo.REAL);
                break;
            case "#6_STR":
                empilharTipo(Tipo.STRING);
                break;
            case "#7":
                if (checarOperacaoAritmetica(tokenAtual.linha)) {
                    return true;
                }
                break;
            case "#8":
                if (checarAtribuicao(tokenAtual.linha)) {
                    return true;
                }
                break;
        }
        return false;
    }

    private void adicionarIdParaDeclaracao(String nomeId) {
        this.listaDeIdentificadores.add(nomeId);
    }

    private void setTipoDeclaracao(Tipo tipo) {
        this.tipoAtual = tipo;
    }

    private boolean processarDeclaracaoVariavel(int linha) {
        boolean erro = false;
        for (String nomeId : listaDeIdentificadores) {
            Simbolo novoSimbolo = new Simbolo(nomeId, this.tipoAtual);
            if (!tabelaDeSimbolos.adicionarSimbolo(novoSimbolo)) {
                System.err.printf("ERRO SEMÂNTICO na linha %d: Identificador '%s' já declarado neste escopo.\n", linha, nomeId);
                erro = true;
            }
        }
        listaDeIdentificadores.clear();
        return erro;
    }
    
    private boolean verificarIdentificador(String nomeId, int linha) {
        Simbolo s = tabelaDeSimbolos.buscarSimbolo(nomeId);
        if (s == null) {
            System.err.printf("ERRO SEMÂNTICO na linha %d: Identificador '%s' não declarado.\n", linha, nomeId);
            pilhaDeTipos.push(Tipo.UNDEFINED);
            return true;
        } else {
            pilhaDeTipos.push(s.getTipo());
        }
        return false;
    }
    
    private void empilharTipo(Tipo tipo) {
        pilhaDeTipos.push(tipo);
    }

    private boolean checarOperacaoAritmetica(int linha) {
        if (pilhaDeTipos.size() < 2) return false;

        Tipo tipo2 = pilhaDeTipos.pop();
        Tipo tipo1 = pilhaDeTipos.pop();

        if ((tipo1 == Tipo.INTEGER || tipo1 == Tipo.REAL) && (tipo2 == Tipo.INTEGER || tipo2 == Tipo.REAL)) {
            if (tipo1 == Tipo.REAL || tipo2 == Tipo.REAL) {
                pilhaDeTipos.push(Tipo.REAL);
            } else {
                pilhaDeTipos.push(Tipo.INTEGER);
            }
        } else {
            System.err.printf("ERRO SEMÂNTICO na linha %d: Tipos incompatíveis em operação aritmética. Encontrado %s e %s.\n", linha, tipo1, tipo2);
            pilhaDeTipos.push(Tipo.UNDEFINED);
            return true;
        }
        return false;
    }

    private boolean checarAtribuicao(int linha) {
        if (pilhaDeTipos.size() < 2) return false;

        Tipo tipoExpressao = pilhaDeTipos.pop();
        Tipo tipoVariavel = pilhaDeTipos.pop();  

        if (tipoVariavel == Tipo.REAL && tipoExpressao == Tipo.INTEGER) {
            // OK
        } else if (tipoVariavel != tipoExpressao) {
            System.err.printf("ERRO SEMÂNTICO na linha %d: Tipos incompatíveis para atribuição. Variável é %s, mas expressão resulta em %s.\n", linha, tipoVariavel, tipoExpressao);
            return true;
        }
        return false;
    }
}
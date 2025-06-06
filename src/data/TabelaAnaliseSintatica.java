package data;

import java.util.HashMap;
import java.util.Map;

public class TabelaAnaliseSintatica {
    private final Map<String, Map<String, Integer>> tabela;

    public TabelaAnaliseSintatica() {
        this.tabela = new HashMap<>();
        inicializarTabela();
    }

    public Integer getRegra(String naoTerminal, String terminal) {
        Map<String, Integer> regrasNaoTerminal = tabela.get(naoTerminal);
        if (regrasNaoTerminal != null) {
            return regrasNaoTerminal.get(terminal);
        }
        return null;
    }

    private void inicializarTabela() {
        Map<String, Integer> r;

        r = new HashMap<>(); r.put("program", 1); tabela.put("<PROGRAMA>", r);
        r = new HashMap<>(); r.put("const", 2); r.put("var", 2); r.put("procedure", 2); r.put("begin", 2); tabela.put("<DECLARACOES>", r);
        r = new HashMap<>(); r.put("const", 3); r.put("var", 4); r.put("procedure", 4); r.put("begin", 4); tabela.put("<CONSTANTES>", r);
        r = new HashMap<>(); r.put("var", 5); r.put("procedure", 10); r.put("begin", 10); tabela.put("<VARIAVEIS>", r);
        r = new HashMap<>(); r.put("ident", 6); tabela.put("<LISTAVARIAVEIS>", r);
        r = new HashMap<>(); r.put(":", 7); r.put(",", 8); tabela.put("<REPIDENT>", r);
        r = new HashMap<>(); r.put("ident", 9); r.put("procedure", 10); r.put("begin", 10); tabela.put("<LDVAR>", r);
        r = new HashMap<>(); r.put("integer", 11); r.put("real", 12); r.put("string", 13); tabela.put("<TIPO>", r);
        r = new HashMap<>(); r.put("procedure", 14); r.put("begin", 15); tabela.put("<PROCEDIMENTOS>", r);
        r = new HashMap<>(); r.put("(", 16); r.put(";", 17); tabela.put("<PARAMETROS>", r);
        r = new HashMap<>(); r.put(",", 18); r.put(")", 19); tabela.put("<REPPARAMETROS>", r);
        r = new HashMap<>(); r.put("begin", 20); tabela.put("<BLOCO>", r);
        r = new HashMap<>(); r.put("print", 21); r.put("if", 21); r.put("ident", 21); r.put("for", 21); r.put("while", 21); r.put("read", 21); r.put("end", 22); tabela.put("<COMANDOS>", r);
        r = new HashMap<>(); r.put("print", 23); r.put("if", 41); r.put("ident", 51); r.put("for", 52); r.put("while", 63); r.put("read", 64); tabela.put("<COMANDO>", r);
        r = new HashMap<>(); r.put("(", 24); r.put("ident", 24); r.put("nint", 24); r.put("nreal", 24); r.put("literal", 24); r.put("vstring", 24); tabela.put("<ITEMSAIDA>", r);
        r = new HashMap<>(); r.put("}", 25); r.put(",", 26); tabela.put("<REPITEM>", r);
        r = new HashMap<>(); r.put("(", 27); r.put("ident", 27); r.put("nint", 27); r.put("nreal", 27); r.put("literal", 27); r.put("vstring", 27); tabela.put("<EXPRESSAO>", r);
        r = new HashMap<>(); r.put("+", 28); r.put("-", 29); r.put(")", 30); r.put("}", 30); r.put(",", 30); r.put("then", 30); r.put("to", 30); r.put("do", 30); r.put(";", 30); r.put("=", 30); r.put("<>", 30); r.put("<", 30); r.put(">", 30); r.put("<=", 30); r.put(">=", 30); r.put("end", 30); tabela.put("<EXPR>", r);
        r = new HashMap<>(); r.put("(", 31); r.put("ident", 31); r.put("nint", 31); r.put("nreal", 31); r.put("literal", 31); r.put("vstring", 31); tabela.put("<TERMO>", r);
        r = new HashMap<>(); r.put("*", 32); r.put("/", 33); r.put("+", 34); r.put("-", 34); r.put(")", 34); r.put("}", 34); r.put(",", 34); r.put("then", 34); r.put("to", 34); r.put("do", 34); r.put(";", 34); r.put("=", 34); r.put("<>", 34); r.put("<", 34); r.put(">", 34); r.put("<=", 34); r.put(">=", 34); r.put("end", 34); tabela.put("<TER>", r);
        r = new HashMap<>(); r.put("(", 35); r.put("ident", 36); r.put("nint", 37); r.put("nreal", 38); r.put("literal", 39); r.put("vstring", 40); tabela.put("<FATOR>", r);
        r = new HashMap<>(); r.put("(", 42); r.put("ident", 42); r.put("nint", 42); r.put("nreal", 42); r.put("literal", 42); r.put("vstring", 42); tabela.put("<EXPRELACIONAL>", r);
        r = new HashMap<>(); r.put("=", 43); r.put("<>", 44); r.put("<", 45); r.put(">", 46); r.put("<=", 47); r.put(">=", 48); tabela.put("<OPREL>", r);
        r = new HashMap<>(); r.put("else", 49); r.put(";", 50); r.put("end", 50); tabela.put("<ELSEOPC>", r);
        r = new HashMap<>(); r.put("(", 55); r.put(";", 56); tabela.put("<LISTAPARAMETROS>", r);
        r = new HashMap<>(); r.put("ident", 57); r.put("nint", 58); r.put("nreal", 59); r.put("vstring", 60); r.put("literal", 65); tabela.put("<PAR>", r);
        r = new HashMap<>(); r.put(",", 61); r.put(")", 62); tabela.put("<REPPAR>", r);
        r = new HashMap<>(); r.put("(", 53); r.put(":=", 54); tabela.put("<RESTO_COMANDO_IDENT>", r);
        r = new HashMap<>(); r.put("print", 66); r.put("if", 66); r.put("ident", 66); r.put("for", 66); r.put("while", 66); r.put("read", 66); tabela.put("<LISTA_COMANDOS>", r);
        r = new HashMap<>(); r.put(";", 67); r.put("end", 68); tabela.put("<MAIS_COMANDOS>", r);
    }
}
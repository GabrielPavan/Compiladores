package data;

import java.util.HashMap;
import java.util.Map;

public class RegrasProducao {
    private static final Map<Integer, String[]> regras = new HashMap<>();

    static {
        regras.put(1, new String[]{"program", "ident", ";", "<DECLARACOES>", "<BLOCO>", "."});
        regras.put(2, new String[]{"<CONSTANTES>", "<VARIAVEIS>", "<PROCEDIMENTOS>"});
        regras.put(3, new String[]{"const", "ident", "=", "nint", ";", "<CONSTANTES>"});
        regras.put(4, new String[]{});
        regras.put(5, new String[]{"var", "<LISTAVARIAVEIS>", ":", "<TIPO>", ";", "<LDVAR>"});
        regras.put(6, new String[]{"ident", "<REPIDENT>"});
        regras.put(7, new String[]{});
        regras.put(8, new String[]{",", "ident", "<REPIDENT>"});
        regras.put(9, new String[]{"<LISTAVARIAVEIS>", ":", "<TIPO>", ";", "<LDVAR>"});
        regras.put(10, new String[]{});
        regras.put(11, new String[]{"integer"});
        regras.put(12, new String[]{"real"});
        regras.put(13, new String[]{"string"});
        regras.put(14, new String[]{"procedure", "ident", "<PARAMETROS>", ";", "<BLOCO>", ";", "<PROCEDIMENTOS>"});
        regras.put(15, new String[]{});
        regras.put(16, new String[]{"(", "<LISTAVARIAVEIS>", ":", "<TIPO>", "<REPPARAMETROS>", ")"});
        regras.put(17, new String[]{});
        regras.put(18, new String[]{",", "<LISTAVARIAVEIS>", ":", "<TIPO>", "<REPPARAMETROS>"});
        regras.put(19, new String[]{});
        regras.put(20, new String[]{"begin", "<COMANDOS>", "end"});
        regras.put(21, new String[]{"<LISTA_COMANDOS>"});
        regras.put(22, new String[]{});
        regras.put(23, new String[]{"print", "{", "<ITEMSAIDA>", "<REPITEM>", "}"});
        regras.put(24, new String[]{"<EXPRESSAO>"});
        regras.put(25, new String[]{});
        regras.put(26, new String[]{",", "<ITEMSAIDA>", "<REPITEM>"});
        regras.put(27, new String[]{"<TERMO>", "<EXPR>"});
        regras.put(28, new String[]{"+", "<TERMO>", "<EXPR>"});
        regras.put(29, new String[]{"-", "<TERMO>", "<EXPR>"});
        regras.put(30, new String[]{});
        regras.put(31, new String[]{"<FATOR>", "<TER>"});
        regras.put(32, new String[]{"*", "<FATOR>", "<TER>"});
        regras.put(33, new String[]{"/", "<FATOR>", "<TER>"});
        regras.put(34, new String[]{});
        regras.put(35, new String[]{"(", "<EXPRESSAO>", ")"});
        regras.put(36, new String[]{"ident"});
        regras.put(37, new String[]{"nint"});
        regras.put(38, new String[]{"nreal"});
        regras.put(39, new String[]{"literal"});
        regras.put(40, new String[]{"vstring"});
        regras.put(41, new String[]{"if", "<EXPRELACIONAL>", "then", "<BLOCO>", "<ELSEOPC>"});
        regras.put(42, new String[]{"<EXPRESSAO>", "<OPREL>", "<EXPRESSAO>"});
        regras.put(43, new String[]{"="});
        regras.put(44, new String[]{"<>"});
        regras.put(45, new String[]{"<"});
        regras.put(46, new String[]{">"});
        regras.put(47, new String[]{"<="});
        regras.put(48, new String[]{">="});
        regras.put(49, new String[]{"else", "<BLOCO>"});
        regras.put(50, new String[]{});
        regras.put(51, new String[]{"ident", "<RESTO_COMANDO_IDENT>"});
        regras.put(52, new String[]{"for", "ident", ":=", "<EXPRESSAO>", "to", "<EXPRESSAO>", "do", "<BLOCO>"});
        regras.put(53, new String[]{"<LISTAPARAMETROS>"});
        regras.put(54, new String[]{":=", "<EXPRESSAO>"});
        regras.put(55, new String[]{"(", "<PAR>", "<REPPAR>", ")"});
        regras.put(56, new String[]{});
        regras.put(57, new String[]{"ident"});
        regras.put(58, new String[]{"nint"});
        regras.put(59, new String[]{"nreal"});
        regras.put(60, new String[]{"vstring"});
        regras.put(61, new String[]{",", "<PAR>", "<REPPAR>"});
        regras.put(62, new String[]{});
        regras.put(63, new String[]{"while", "<EXPRELACIONAL>", "do", "<BLOCO>"});
        regras.put(64, new String[]{"read", "(", "ident", ")"});
        regras.put(65, new String[]{"literal"});
        regras.put(66, new String[]{"<COMANDO>", "<MAIS_COMANDOS>"});
        regras.put(67, new String[]{";", "<LISTA_COMANDOS>"});
        regras.put(68, new String[]{});
    }

    public static String[] getProducao(int numeroRegra) {
        return regras.get(numeroRegra);
    }
}
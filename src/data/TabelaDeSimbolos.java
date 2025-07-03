package data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TabelaDeSimbolos {
    
    private LinkedList<Map<String, Simbolo>> pilhaDeEscopos;

    public TabelaDeSimbolos() {
        this.pilhaDeEscopos = new LinkedList<>();
        novoEscopo();
    }

    public void novoEscopo() {
        pilhaDeEscopos.push(new HashMap<>());
    }

    public void fecharEscopo() {
        if (!pilhaDeEscopos.isEmpty()) {
            pilhaDeEscopos.pop();
        }
    }

    public boolean adicionarSimbolo(Simbolo simbolo) {
        Map<String, Simbolo> escopoAtual = pilhaDeEscopos.peek();
        if (escopoAtual.containsKey(simbolo.getNome())) {
            return false;
        }
        escopoAtual.put(simbolo.getNome(), simbolo);
        return true;
    }

    
    public Simbolo buscarSimbolo(String nome) {
        for (Map<String, Simbolo> escopo : pilhaDeEscopos) {
            if (escopo.containsKey(nome)) {
                return escopo.get(nome);
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "Tabela de Símbolos: " + pilhaDeEscopos;
    }
}
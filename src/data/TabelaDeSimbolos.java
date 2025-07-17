package data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ListIterator;

/**
 * Gerencia os escopos e os símbolos declarados.
 * Agora imprime seu estado a cada modificação e na ordem correta.
 */
public class TabelaDeSimbolos {
    private LinkedList<Map<String, Simbolo>> pilhaDeEscopos;

    public TabelaDeSimbolos() {
        this.pilhaDeEscopos = new LinkedList<>();
    }

    public void novoEscopo() {
        pilhaDeEscopos.push(new HashMap<>());
        System.out.println("\n>>> Ação Semântica: Escopo aberto. <<<");
        imprimir();
    }

    public void fecharEscopo() {
        if (!pilhaDeEscopos.isEmpty()) {
            pilhaDeEscopos.pop();
            System.out.println("\n>>> Ação Semântica: Escopo fechado. <<<");
            imprimir();
        }
    }

    public boolean adicionarSimbolo(Simbolo simbolo) {
        if (pilhaDeEscopos.isEmpty()) {
            novoEscopo();
        }
        Map<String, Simbolo> escopoAtual = pilhaDeEscopos.peek();
        if (escopoAtual.containsKey(simbolo.getNome())) {
            return false;
        }
        escopoAtual.put(simbolo.getNome(), simbolo);
        System.out.println("\n>>> Ação Semântica: Símbolo '" + simbolo.getNome() + "' inserido na Tabela. <<<");
        imprimir();
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
    
    // CORREÇÃO: Imprime a tabela na ordem correta (Global -> Local)
    public void imprimir() {
        System.out.println("--- ESTADO ATUAL DA TABELA DE SÍMBOLOS ---");
        if (pilhaDeEscopos.isEmpty()) {
            System.out.println("   (Tabela vazia)");
        } else {
            ListIterator<Map<String, Simbolo>> iterator = pilhaDeEscopos.listIterator(pilhaDeEscopos.size());
            int nivel = pilhaDeEscopos.size() - 1;
            while (iterator.hasPrevious()) {
                Map<String, Simbolo> escopo = iterator.previous();
                System.out.println("  Nível " + nivel + " (Escopo " + (nivel == 0 ? "Global" : "Local") + "):");
                if (escopo.isEmpty()) {
                    System.out.println("    (vazio)");
                } else {
                    for (Simbolo s : escopo.values()) {
                        System.out.println("    -> " + s);
                    }
                }
                nivel--;
            }
        }
        System.out.println("------------------------------------------\n");
    }
}
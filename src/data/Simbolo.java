package data;

public class Simbolo {
    private String nome;
    private Tipo tipo;
    
    public Simbolo(String nome, Tipo tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return this.nome;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    @Override
    public String toString() {
        return "Simbolo [ Nome: " + nome + " | Tipo: " + tipo + " ]";
    }
}
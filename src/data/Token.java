package data;

public class Token {
    public final String codigo;
    public final String lexema;
    public final int linha;

    public Token(String codigo, String lexema, int linha) {
        this.codigo = codigo;
        this.lexema = lexema;
        this.linha = linha;
    }

    @Override
    public String toString() {
        return String.format("Token[código=%-12s lexema=%-18s linha=%d]",
                             "'" + codigo + "'", "'" + lexema + "'", linha);
    }
}
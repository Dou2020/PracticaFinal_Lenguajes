package domain;

/**
 *
 * @author douglas2021
 */
public class Lexema {
    private String texto;
    private int columna;
    private int fila;
    private String tokens;
    private int cantidad;

    public Lexema(String texto, String tokens, int cantidad) {
        this.texto = texto;
        this.tokens = tokens;
        this.cantidad = cantidad;
    }
    
    public Lexema(String texto) {
        this.texto = texto;
    }

    public Lexema(String texto, int columna, int fila, String tokens) {
        this.texto = texto;
        this.columna = columna;
        this.fila = fila;
        this.tokens = tokens;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Lexema: " + "texto=" + texto + ", columna=" + columna + ", fila=" + fila + ", tokens=" + tokens;
    }
    
}

package domain;

/**
 *
 * @author douglas2021
 */
public class LineaEntrada {
    private String texto;
    private int noLinea;

    public LineaEntrada(String texto, int noLinea) {
        this.texto = texto;
        this.noLinea = noLinea;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getNoLinea() {
        return noLinea;
    }

    public void setNoLinea(int noLinea) {
        this.noLinea = noLinea;
    }
    
}

package domain;

import java.util.ArrayList;

/**
 *
 * @author douglas2021
 */
public class conjuntoSintactico {
    private String tipo;
    private ArrayList<Lexema> conjunto;
    private String estado;
    
    public conjuntoSintactico(ArrayList<Lexema> conjunto,String estado,String tipo) {
        this.conjunto = conjunto;
        this.estado = estado;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public ArrayList<Lexema> getConjunto() {
        return conjunto;
    }

    public void setConjunto(ArrayList<Lexema> conjunto) {
        this.conjunto = conjunto;
    }

    @Override
    public String toString() {
        return "conjuntoSintactico:" + "\n Conjunto Tipo=" + tipo + ", estado=" + estado;
    }
    
    
}

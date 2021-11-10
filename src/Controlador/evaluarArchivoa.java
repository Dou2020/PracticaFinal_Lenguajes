package Controlador;
import domain.Lexema;
import domain.LineaEntrada;
import domain.conjuntoSintactico;
import java.util.ArrayList;

/**
 *
 * @author douglas2021
 */
public class evaluarArchivoa {
    
    private ArrayList<conjuntoSintactico> sin;
    
    public evaluarArchivoa(ArrayList<conjuntoSintactico> sin) {
        this.sin = sin;
    }
    public ArrayList<LineaEntrada> retornarArchivo(){
        ArrayList<LineaEntrada> evaluado = new ArrayList<>();
        String texto = "";
        
        for (conjuntoSintactico c: sin) {
            switch(c.getTipo()){
                case "Literal":
                    texto= escribir(c.getConjunto());
                    break;
                case "SI":
                    
                    break;
                    
                default:
                    
                    break;
            }
            evaluado.add(new LineaEntrada(texto,0));
        }
        return evaluado;
        
    }
    public void estados(){
        
    }
    public String si(ArrayList<Lexema> l){
        String text = "";
        ArrayList<Lexema> le = new ArrayList<>();
        boolean estado = true;
        boolean esta = false;
        for (Lexema ll: l) {
            if (estado) {
                if (ll.getTokens().equals("VERDADERO")) {
                    esta = true;
                }
                if (ll.getTokens().equals("FALSO")) {
                    esta = false;
                }
                estado = false;
            }
            if (!estado && esta && !ll.getTokens().equals("FIN")) {
                le.add(ll);
            }
        }
        text += escribir(le);
        return text;
    }
    public String escribir(ArrayList<Lexema> l){
        String text = "";
        for (Lexema ll: l) {
            if (ll.getTokens().equals("Literal")) {
                String [] a = ll.getTexto().split("\"");
                for (int i = 0; i < a.length; i++) {
                    if (!a[i].equals("\"")) {
                        text += a;
                    }
                    
                }
            }
        }
        return text;
    }
   
}

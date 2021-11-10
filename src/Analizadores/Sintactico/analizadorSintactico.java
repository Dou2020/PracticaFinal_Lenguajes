package Analizadores.Sintactico;

import domain.Lexema;
import domain.Pila;
import domain.conjuntoSintactico;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author douglas2021
 */
public class analizadorSintactico {

    /*     OP=Operacion  Entero  Identificador  FIN
     *       0          1    2    3    4     5      6        7        8         9       10     11
     *  Identificador   Op   (    )  Entero  SI  VERDADERO  FALSO  REPETIR  ESCRIBIR  LITERAL  FIN
     *
    \*
                                            0                        1                                   2            3         4                  5            6          7                    8               9           10         11    */
    private String Matriz[][] = {{"Identificador-Igual-T",         "Error",                               "Error",  "Error",  "Error", "SI-C-ENTONCES-ES",    "Error", "Error", "REPETIR-Entero-INICIAR-ES","ESCRIBIR-S",  "Error", "Error"},  // E
                                 {                 "F-T'",         "Error",                                "F-T'",  "Error",   "F-T'",            "Error",    "Error", "Error",                    "Error",     "Error",  "Error", "Error"},  // T
                                 {                "Error","Operacion-F-T'",                               "Error",     "Ep",  "Error",            "Error",    "Error", "Error",                    "Error",     "Error",  "Error",   "FIN"},  // T'
                                 {        "Identificador",         "Error", "AgrupacionInicial-T-AgrupacionFinal",  "Error", "Entero",            "Error",    "Error", "Error",                    "Error",     "Error",  "Error", "Error"},  // F
                                 {                "Error",         "Error",                               "Error",  "Error",  "Error",            "Error","VERDADERO", "FALSO",                    "Error",     "Error",  "Error", "Error"},  // C
                                 {                 "E-ES",         "Error",                               "Error",  "Error",   "Error",            "E-ES",    "Error", "Error",                     "E-ES",      "E-ES",  "Error",   "FIN"},  // ES
                                 {                "Error",         "Error",                               "Error",  "Error",   "Error",           "Error",    "Error", "Error",                    "Error",     "Error","Literal-S",   "FIN"}}; // S

    private String NoTerminales[] = {"E", "T", "T'", "F","C","ES","S"};
    String Terminales[] = {"Identificador","Operacion", "AgrupacionInicial", "AgrupacionFinal", "Entero","SI","VERDADERO","FALSO","REPETIR","ESCRIBIR","Literal","FIN","Igual","ENTONCES","INICIAR"};    

    //este debuelve si es un estado de terminal, no terminal y si no es valido uno de esos un error 
    public String queEstado(String c) {
        String estado = "error";
        for (int i = 0; i < NoTerminales.length; i++) {
            if (c.equals(NoTerminales[i])) {
                estado = "NoTerminal";
            }
        }
        for (int i = 0; i < Terminales.length; i++) {
            if (c.equals(Terminales[i])) {
                estado = "Terminal";
            }
        }
        System.out.println("valor estado"+estado);
        return estado;
    }

    //realiza la particion del texto del automata de pila
    public String[] particion(String c) {
        String[] par = c.split("-");
        for (int i = 0; i < par.length; i++) {
            System.out.print(i + par[i]);
        }
        System.out.println("");
        return par;
    }

    public int posicionNoTerminales(String token) {
        int valor = -1;
        for (int i = 0; i < NoTerminales.length; i++) {
            if (NoTerminales[i].equals(token)) {
                valor = i;
                break;
            }
        }
        return valor;
    }

    public int posicionTerminales(String token) {
        int valor = -1;
        for (int i = 0; i < Terminales.length; i++) {
            if (Terminales[i].equals(token)) {
                valor = i;
                break;
            }
        }
        return valor;
    }

    public void getSiguienteEstado(int estadoActual, int caracter, Pila p) {
        String[] cadena;
        //ingresar la longitud maxima de la matriz
        if (caracter >= 0 && caracter <= 11) {
            cadena = particion(Matriz[estadoActual][caracter]);
            //invertir cadena
            for (int i = cadena.length-1; 0 <= i; i--) {
                p.apilar(cadena[i]);
            }
        }
    }

    //evaluara cada token y le asignara un valor en la matriz
    public ArrayList<conjuntoSintactico> analizar(ArrayList<Lexema> lexema) throws Exception {
        ArrayList<conjuntoSintactico> sintactico = new ArrayList<>(); // Lista que se va a enviar
        int estadoActual = 0;  // almacena el valor del token con el que inicia
        boolean iniciar = true; //valor para el reinicio de la lista con lexema
        ArrayList<Lexema> conjunto= null;//este es la lista que guarda lista de tokens
        Pila pila = new Pila();// es el controlador de la pila

        //Lee todo token por token al momento de no haber ningun error
        int contador = 0;
        String tipo = "Error";
        
        for (Lexema le : lexema) {
            System.out.println("evaluar lexema: "+le.getTokens());
            boolean estado = true;
            if (iniciar) {
                tipo = le.getTokens();
                getSiguienteEstado(estadoActual, posicionTerminales(le.getTokens()), pila);
                System.out.println("limpiando conjunto \n\n");
                conjunto = new ArrayList<>();//limpiar la lista 
                iniciar = false;
            }     
            while (estado) {
                System.out.println("/////////PILA////////");
                pila.listar();
                
                if (!pila.esVacia() && !pila.cima().equals("Error")) {
                    String esta = pila.cima();
                    pila.retirar();
                    if (!esta.equals("FIN") || !pila.esVacia()) {
                        if (!esta.equals("Ep")) {
                            if (queEstado(esta).equals("Terminal")) {
                                if (esta.equals(le.getTokens())) {
                                    conjunto.add(le);
                                }
                                System.out.println("se realizo un reduce "+le.getTokens());
                                estado = false;
                            }
                            if (queEstado(esta).equals("NoTerminal")) {
                                estadoActual = posicionNoTerminales(esta);
                                System.out.println("Token "+le.getTokens()+" NoTerminal y estado; "+esta+"transicion a:"+estadoActual+" y "+posicionTerminales(le.getTokens()));
                                getSiguienteEstado(estadoActual, posicionTerminales(le.getTokens()), pila);
                            }
                        }
                    } else {
                        System.out.println("llego a estado Terminal");
                        System.out.println(conjunto.toString());
                        sintactico.add(new conjuntoSintactico(conjunto,"Aceptado",tipo));
                        pila.eliminar();
                        estadoActual = 0;
                        estado = false;
                        iniciar = true;
                    }
                } else {
                    System.out.println("----------Error en la pila-----------------");
                    sintactico.add(new conjuntoSintactico(conjunto,"NoAceptado",tipo));
                    pila.eliminar();
                    estadoActual = 0;
                    estado = false;
                    iniciar = true;
                }
            }
            
            
        }
        return sintactico;
    }
}

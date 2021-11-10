package Analizadores.Lexico;
import java.util.ArrayList;
import javax.swing.JTextArea;
import java.util.ArrayList;
import javax.swing.JTextArea;
import domain.Lexema;
import domain.LineaEntrada;

/**
 *
 * @author douglas2021
 */
public class Automata {

    private String palabra;
    private int posicion = 0;
    private int estadoActual = 0;
    ArrayList<Lexema> palabras;
    /*
    tabla de matriz de tansiciones  -1 no esta en el alfabeto
    SP=signo puntuacion  AP=Agrupacion  OP=Operadores  sigC=Signo De comparacion  D=Digito  L=letra
         0     1         2       3     4     5     6      7       8     9     10   11
      { \L , [1-9] , \Espacio , \SP , \OP , \AP , '_' , 'SigC' , '-' , '/' , '"' , 0} 
    
                        0  1  2  3  4  5  6  7  8  9 10 11    */
    int matriz[][] = {{ 5, 3,-1,-1,10,11, 5, 4, 1, 8, 6, 2},   //S0
                      {-1, 3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},   //S1
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},   //S2
                      {-1, 3,-1,-1,-1,-1,-1,-1,-1,-1,-1, 3},   //S3
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},   //S4
                      { 5, 5,-1,-1,-1,-1, 5,-1, 5,-1,-1, 5},   //S5
                      { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6},   //S6
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},   //S7
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1, 9,-1,-1},   //S8
                      { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},   //S9
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},   //S10
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};  //11
    
    // Estados de aceptacion con su valor de finalizaciòn
    int[] estadosFinalizacion = {4,3, 2, 5,7, 9, 11, 10};
    String[] descripcionFinalizacion = {"Comparacion","Entero", "Entero", "Identificador", "Literal", "Comentario", "Agrupacion", "Operacion"};
    //Alfabeto de las palabras reservadas 
    String[] palabrasReservadas = {"ESCRIBIR","FIN","REPETIR","INICIAR","SI","VERDADERO","FALSO","ENTONCES"};
    //los numeros excepcion del 0
    char[] numero = {'1','2','3','4','5','6','7','8','9'};
    //alfabeto de todos los signos agrupados
    //char[] letras = {'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L',
    //    'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z'};
    char[] SignosComparacion = {'<','>','='};
    char[] agrupacion = {'{', '}', '[', ']', '(', ')'};
    char[] signos = {'.', ',', ';', ':','\''};
    char[] operadores = {'+', '-', '*', '/', '%'};
    
    JTextArea Mo;
    public Automata(JTextArea Mo) {
        this.Mo = Mo;
        this.Mo.removeAll();
        palabras = new ArrayList<>();
        //for (int i = 0; i < 9; i++) {
        //    for (int j = 0; j < 6; j++) { System.out.print(""+matriz[i][j]+", "); }  //visualizar el arreglo bidimencional
        //    System.out.println("");  // el salto de linea 
        //}
        /*
         * for (char caracter : palabra.toCharArray()) { System.out.println(caracter); }
         */
    }

    public ArrayList<Lexema> evaluando(LineaEntrada linea) {
        String textoMo ="**********Ingreso al automata logitud: " + linea.getTexto().length()+"****************";
        System.out.println(textoMo);
        posicion = 0;
        palabra = linea.getTexto();
        int no = linea.getNoLinea();
        while (posicion < palabra.length()) {
            getToken(no);  // interaccion con el tokens
        }
        return palabras;
    }

    // revisa movimiento den la matriz
    public int getSiguienteEstado(int estadoActual, int caracter) {
        int resultado = -1;
        //ingresar la longitud maxima de la matriz
        if (caracter >= 0 && caracter <= 11) {
            resultado = matriz[estadoActual][caracter];
        }
        return resultado;
    }

    //establece aqui el alfabeto 
    public int getIntCaracter(char caracter) {
        int resultado = -1; // es instancia en un valor de no aceptacion
        //for(int i=0; i < letras.length; i++){ // verificar si es una letra
        //    if( letras[i] == caracter )
        //        return resultado = 0;
        //}
        if (Character.isLetter(caracter)) {
            if (!(caracter == ' ')) {
                return resultado = 0;
            }
        }
        if (caracter == '0') { // verificar si es un 0
            return resultado = 11;
            
        } else {
            if (Character.isDigit(caracter)) // verificar que sea un digito
            {
                return resultado = 1;
            }
        }
        if (caracter == '-') {  // verifica signo -
            return resultado = 8;
        }
        if (caracter == '_') {  // verifica signo _
            return resultado = 6;
        }
        if (caracter == '/') {  // verifica signo /
            return resultado = 9;
        }
        if (caracter == '"' ) {  // verifica signo "
            return resultado = 10;
        }
        if (caracter == ' ') {  // verifica si es espacio
            return resultado = 2;
        }
        for (int i = 0; i < agrupacion.length; i++) { // verificar si es un estado de agrupacion '\AP'
            if (agrupacion[i] == caracter) {
                return resultado = 5;
            }
        }
        // verificar que es un estado de signo de puntuacion  '\SP'
        for (int i = 0; i < signos.length; i++) {
            if (signos[i] == caracter) {
                return resultado = 3;
            }
        }
        // verificar que es un estado de operadores  
        for (int i = 0; i < operadores.length; i++) {
            if (operadores[i] == caracter) {
                return resultado = 4;
            }
        }
        //verificando signo de agrupacion
        for (int i = 0; i < SignosComparacion.length; i++) {
            if (SignosComparacion[i] == caracter) {
                return resultado = 7;
            }
        }
        return resultado;
    }

    public String getEstadoAceptacion(int i,String token) {
        String res = "Error";
        int indice = 0;
        for (int estadoAceptacion : estadosFinalizacion) {
            if (estadoAceptacion == i) {
                res = descripcionFinalizacion[indice];
                break;
            }
            indice++;
        }
        if (token.equals("FIN")) {
            res = "FIN";
        }
        if (token.equals("=")) {
            res = "Igual";
        }
        if (token.equals("(")) {
            res = "AgrupacionInicial";
        }
        if (token.equals("REPETIR")) {
            res = "REPETIR";
        }
        if (token.equals("SI")) {
            res = "SI";
        }
        if (token.equals("VERDADERO")) {
            res = "VERDADERO";
        }
        if (token.equals("FALSO")) {
            res = "FALSO";
        }
        if (token.equals("ENTONCES")) {
            res = "ENTONCES";
        }
        if (token.equals("INICIAR")) {
            res = "INICIAR";
        }
        if (token.equals(")")) {
            res = "AgrupacionFinal";
        }
        if (token.equals("ESCRIBIR")) {
            res = "ESCRIBIR";
        }
        
        if (res.equals("Identificador")) {
            for (String reservada: palabrasReservadas) {
                if (reservada.equals(token)) {
                    res = "Reservada";
                }
            }
        }
        
        return res;
    }

    /*
     * get token funciona para para validar cada uno de los lexemas y darle un token si existe o error
     */
    public void getToken(int fila) {
        estadoActual = 0;  // almacena el valor del token con el que inicia
        boolean seguirLeyendo = true; //establece un estado de verdadero para ingresar como minimo una vez
        String textoMo; // texto momentaneo
        char tmp; //este nos permitira almacenar caracter por caracter
        String token = ""; // el token completo
        int literal = 0;
        int comentario = 0;
        boolean e = (Character.isSpaceChar(palabra.charAt(posicion))) || (palabra.charAt(posicion) == '\t') || (palabra.charAt(posicion) == '\r') ;
        //si viene de inicio un espacio realice un salto o 
        if ( (posicion < palabra.length()) && e ) {
            posicion++;
            return;
        }
        while ((seguirLeyendo) && (posicion < palabra.length())) {    // posicion es global nos permitir verificar si leemos todo el string
            
            // seguir evaluando aunque haya un error en el automata
            if (!Character.isSpaceChar(tmp = palabra.charAt(posicion)) && (estadoActual == -1)) {
                posicion--;
            }
            if (palabra.charAt(posicion) == '"') { literal++; } //para no tener dificultad con las literales
            if (palabra.charAt(posicion) == '/') { comentario++; }// para no tener dificultad con un comentario
            //estado para verificar que habra un parentesis pero que no sea un comentario o una literal donde si se aceptaria cualquier instancia
            boolean comentarioLiteral= (comentario < 2) && (literal != 1); 
            boolean eva = (palabra.charAt(posicion)== '(')&& comentarioLiteral;
            boolean valor = (posicion+1 < palabra.length()) && comentarioLiteral && (palabra.charAt(posicion+1)== ')');
            if ((comentarioLiteral && Character.isSpaceChar(tmp = palabra.charAt(posicion))) || (estadoActual == -1) || (palabra.charAt(posicion)== '\n') ) { // verificar si es un espacio 
                seguirLeyendo = false; // cambia el estado si es un espacio
            } else {// establece que no es un espacio segira leyendo el la cadena
                // para mi automata  (establece el automata)
                if (eva) { seguirLeyendo = false; }//este es para evaluar cuando se abre un signo de agrupacion (
                if (valor) { seguirLeyendo = false; }
                int estadoTemporal = getSiguienteEstado(estadoActual, getIntCaracter(tmp)); // establece un estado temporal  para el posicionamiento
                textoMo = "posicion:" + posicion + " Estado actual " + estadoActual + " caracter " + tmp + " transicion a " + estadoTemporal;
                System.out.println(textoMo);
                token += tmp;
                estadoActual = estadoTemporal;
                if (evaluarEstado() || !seguirLeyendo) {
                    int va  = token.length() - 1;
                    String v = posibleEstado(va,token);
                    palabras.add(new Lexema(v+token /*+ " " + token.length()*/, posicion - va, fila, getEstadoAceptacion(estadoActual,token)));
                }
                System.out.println(tmp);
            }
            posicion++;
        }
        //if (estadoActual != 0) {
        textoMo = "posicion:" + posicion + "********* Termino en el estado " + getEstadoAceptacion(estadoActual,token) + " token actual : " + token+"  ***********";
        System.out.println(textoMo);
        //}
        // verificar el estado de aceptación
    }
    private String posibleEstado(int va,String token) {
        int v = posicion-va;
        if (!getEstadoAceptacion(estadoActual,token).equals("Error")) {
            if (v == 0) {
                return "";
            }
            if ( (0 < v) && Character.isSpaceChar(palabra.charAt(v-1))) {
                return "";
            }
            int a = palabras.size();
            
            if (0 < a && palabras.get(a-1).getTokens().equals("Error")) {
                return"Posible ";
            }
        }
        return "";
    }
    private boolean evaluarEstado() {
        int a = posicion;
        if (a + 1 < palabra.length()) {
            a = posicion + 1;
        }
        char l = palabra.charAt(a);

        if (Character.isSpaceChar(l) || posicion == (palabra.length() - 1)) {
            for (int i = 0; i < estadosFinalizacion.length; i++) {
                if (estadosFinalizacion[i] == estadoActual) {
                    return true;
                }
            }
        }
        return estadoActual == -1;
    }

}

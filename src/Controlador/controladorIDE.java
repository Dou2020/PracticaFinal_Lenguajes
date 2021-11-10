package Controlador;
import Analizadores.Lexico.Automata;
import Analizadores.Sintactico.analizadorSintactico;
import Archivo.LectorDeCondicionesEnTexto;
import Vista.IDE;
import domain.Lexema;
import domain.LineaEntrada;
import domain.conjuntoSintactico;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author douglas2021
 */
public class controladorIDE {
    private IDE visual;
    private ArrayList<Lexema> expreciones = new ArrayList<>();
    private ArrayList<conjuntoSintactico> exprecionesSintacticas= new ArrayList<>();
    
    private ArrayList<LineaEntrada> linea = new ArrayList<>();

    public controladorIDE(IDE visual) {
        this.visual = visual;

        
    }
    
    public void guardarArchivo() {
        boolean estado = true;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int seleccion = fileChooser.showSaveDialog(visual);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            
            File f = fileChooser.getSelectedFile();
            
            File fichero = new File(f.getAbsolutePath()+"/ArchivoEntrada.txt");
            try {
                System.out.println(fichero.getAbsolutePath()+" "+fichero.getPath()+" "+fichero.getParent()+" "+fichero.getCanonicalPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (fichero.createNewFile()) {
                    System.out.println("File creado: " + fichero.getAbsolutePath());
                } else {
                    System.out.println("ya exsite el archivo "+fichero.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("Un error no identificado.");
                e.printStackTrace();
                estado = false;
            }
// Aquí debemos abrir el fichero para escritura
            if (estado) {
                try {
                    FileWriter myWriter = new FileWriter(fichero.getAbsolutePath());
                    myWriter.write(visual.getAreaPrograma().getText());
                    myWriter.close();
                    System.out.println("Se escribio el file");
                } catch (IOException e) {
                    System.out.println("hay un error en la carga.");
                    e.printStackTrace();
                }
            }
            // y salvar nuestros datos.

        }
    }

    public void subirArchivo() throws IOException {
        LectorDeCondicionesEnTexto lector = new LectorDeCondicionesEnTexto();
        JFileChooser fileChosser = new JFileChooser();
        int seleccion = fileChosser.showOpenDialog(visual);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            //aqui selecciono y guardo el FILE que va a utilizar el FileReader
            File fichero = fileChosser.getSelectedFile();
            linea = lector.leerFichero(fichero, this.visual.getAreaPrograma());
            //this.escritorDeTextoBinarios.guardarVehiculo(expreciones);
        }
        for (LineaEntrada l : linea) {
            System.out.println(l.getTexto() + " " + l.getNoLinea());
        }
    }

    public void recibirCambio() {

    }

    public void actualizarLinea() {
        linea.clear();
        String text = "";
        int fila = 0;
        String p = visual.getAreaPrograma().getText();
        System.out.println(p);
        for (int i = 0; i < p.length(); i++) {
            char a = p.charAt(i);
            if (i == (p.length() - 1)) {
                text += a;
            }
            if ((a != '\n') && (i != (p.length() - 1))) {
                text += a;
            } else {
                linea.add(new LineaEntrada(text, fila));
                text = "";
                fila++;
            }
        }
    }

    public void evaluar() {
        actualizarLinea();
        visual.getConsola().append("--------------------------------Realizando el analisis lexico---------------------------------\n");
        Automata automata = new Automata(visual.getConsola());
        for (LineaEntrada entra : linea) {
            System.out.println(entra.getTexto() + " " + entra.getNoLinea());
            expreciones = automata.evaluando(entra);
        }
        
        if (evaluandoLexico()) {
        visual.getConsola().append("-----------------------------Hay un error en el analisis Lexico--------------------------------\n\n");
        }else{
            visual.getConsola().append("------------------------------------------Analisis Lexico Aceptado-------------------------------------\n\n");
        }
        visual.getConsola().append("-------------------------------------------Realizando analisis Sintactico----------------------------------\n");
        analizadorSintactico sin = new analizadorSintactico();
        try {
            exprecionesSintacticas = sin.analizar(expreciones);
        } catch (Exception ex) {
            visual.getConsola().append("---------------------------------------Error no identificado-------------------------------\n");
        }
        visual.getConsola().append("\n\n");
        if (evaluandoSintactico()) {
            visual.getConsola().append("-----------------------------------------------Error en el analisis Sintactico----------------------------\n");
        }else{
            visual.getConsola().append("-------------------------------------------------Analisis Sintactico Aceptado-----------------------------\n");
        }
        
        for (conjuntoSintactico s: exprecionesSintacticas) {
            
               visual.getConsola().append(s.toString()+"\n");
                for (Lexema l: s.getConjunto()) {
                    visual.getConsola().append(l.toString()+"\n");
                }
                visual.getConsola().append("\n");
                
            }
    
    }
    public void ArchivoGuardar(){
        for (conjuntoSintactico s: exprecionesSintacticas) {
            switch(s.getTipo()){
                case "Identificador":
                    
                    break;
                case "SI":
                    
                    break;
                case "REPETIR":
                    
                    break;
                case "":
                    
                    break;
                default:
                    break;
            }
        }
    }

    public boolean evaluandoLexico() {
        boolean estado = false;
        for (Lexema le : expreciones) {
            if (le.getTokens().equals("Error")) {
                visual.getConsola().append("Lexema: "+le.getTexto()+" Columna-> "+le.getColumna()+" Fila-> "+le.getFila()+"\n");
                estado = true;
            }
        }
        return estado;
    }
    public boolean evaluandoSintactico(){
        boolean estado = false;
        for (conjuntoSintactico s: exprecionesSintacticas) {
           
            if (s.getEstado().equals("NoAceptado")) {
                visual.getConsola().append("\n\n Error "+s.toString()+" \n\n");
                for (Lexema l: s.getConjunto()) {
                    visual.getConsola().append(l.toString()+"\n");
                }
                estado = true;
            }
        }
        return estado;
    }
    
}

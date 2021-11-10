package Archivo;

import domain.LineaEntrada;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author douglas2021
 */
public class LectorDeCondicionesEnTexto {
    public ArrayList<LineaEntrada> leerFichero(File archivo,JTextArea text) throws FileNotFoundException, IOException {
        // crea el arreglo para ingresar el vehiculo
        ArrayList<LineaEntrada> condiciones = new ArrayList<>();
        System.out.println("ingreso aqui leer");
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int contar = 0;
        while ((linea = br.readLine()) != null) {
            //agregarlo al array para luego enviarlo
            condiciones.add(new LineaEntrada(linea,contar));
            contar++;
            //con la linea leida, separamos los campos
            text.append("\n"+linea);
        }
        fr.close(); 
        return condiciones;
    }
}

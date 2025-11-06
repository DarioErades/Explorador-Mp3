package exploradorMp3;

import com.google.gson.Gson;

import java.io.File;
import java.util.List;

public class ExploradorMP3 {
	
    public static void buscarMp3Recursivo(File dir, List<Cancion> lista) {
        File[] ficheros = dir.listFiles();
        if (ficheros == null) return;
        for (File f : ficheros) {
            if (f.isDirectory()) {
                buscarMp3Recursivo(f, lista);
            } else if (f.isFile() && f.getName().toLowerCase().endsWith(".mp3")) { // Compruebo que sea un mp3
                // Leo la cabecera con el método que he creado 
                Cancion c = LectorID3.leerID3v1(f);

                if (c != null) {
                    lista.add(c);
                    System.out.println("Añadida: " + f.getAbsolutePath() + " -> " + c.getTitulo());
                } else {
                    // El archivo no tiene cabecera, creo un archivo con el path y con datos vacíos, y saco alerta por consola
                	System.out.println("El archivo "+f.getName()+" no tiene cabecera");
                    Cancion ca = new Cancion(f.getAbsolutePath(), "", "", "", "", "",-1);
                    lista.add(ca);
                }
            }
        }
    }
    

}

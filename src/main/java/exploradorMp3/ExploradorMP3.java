package exploradorMp3;

import exploradorMp3.Model.Cancion;

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
                // Este metodo ya lo almacena en la BD
                LectorID3.leerID3v1(f);
            }
        }
    }
}

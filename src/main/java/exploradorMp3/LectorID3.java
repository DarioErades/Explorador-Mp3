package exploradorMp3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class LectorID3 {
    // Con este método voy a leer la cabecera y crear un objeto de Canción a partir de esa cabecera
    public static Cancion leerID3v1(File mp3) {
        if (mp3.length() < 128) {
            return null; // El archivo es más pequeño de lo que debería ocupar la cabecera
        }
        try (RandomAccessFile lector = new RandomAccessFile(mp3, "r")) { // Lo abro solo con lectura
            lector.seek(lector.length() - 128); // Pongo el lector en los últimos 128 bytes
            byte[] buffer = new byte[128];
            lector.readFully(buffer);

            String tag = new String(buffer, 0, 3, StandardCharsets.ISO_8859_1);
            if (!"TAG".equals(tag)) {
                return null; // no hay cabecera ID3v1
            }

            String titulo = new String(buffer, 3, 30, StandardCharsets.ISO_8859_1).trim();
            String artista = new String(buffer, 33, 30, StandardCharsets.ISO_8859_1).trim();
            String album = new String(buffer, 63, 30, StandardCharsets.ISO_8859_1).trim();
            String anio = new String(buffer, 93, 4, StandardCharsets.ISO_8859_1).trim();
            String comentario = new String(buffer, 97, 30, StandardCharsets.ISO_8859_1).trim();
            int genero = buffer[127] & 0xFF;

            

            return new Cancion(mp3.getAbsolutePath(), titulo, artista, album, anio, comentario, genero);
        } catch (IOException e) {
            System.err.println("Error leyendo ID3v1 de: " + mp3.getAbsolutePath());
            e.printStackTrace();
            return null;
        }
    }

}

package exploradorMp3;

import exploradorMp3.BD.UtilsBD;
import exploradorMp3.Model.Album;
import exploradorMp3.Model.Artista;
import exploradorMp3.Model.Cancion;
import exploradorMp3.Model.Genre;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class LectorID3 {
    // Con este método voy a leer la cabecera y crear un objeto de Canción a partir
    // de esa cabecera
    public static void leerID3v1(File mp3) {
        if (mp3.length() < 128) {
            return; // El archivo es más pequeño de lo que debería ocupar la cabecera
        }
        try (RandomAccessFile lector = new RandomAccessFile(mp3, "r")) { // Lo abro solo con lectura
            lector.seek(lector.length() - 128); // Pongo el lector en los últimos 128 bytes
            byte[] buffer = new byte[128];
            lector.readFully(buffer);

            String tag = new String(buffer, 0, 3, StandardCharsets.ISO_8859_1);
            if (!"TAG".equals(tag)) {
                return; // no hay cabecera ID3v1
            }

            String titulo = new String(buffer, 3, 30, StandardCharsets.ISO_8859_1).trim();
            String artista = new String(buffer, 33, 30, StandardCharsets.ISO_8859_1).trim();
            String album = new String(buffer, 63, 30, StandardCharsets.ISO_8859_1).trim();
            String anio = new String(buffer, 93, 4, StandardCharsets.ISO_8859_1).trim();
            int trackNumber = buffer[126] & 0xFF;

            // Aquí empieza la creación de los 4 objetos

            Artista artistaModel = new Artista(artista);
            Album albumModel;
            try {
                albumModel = new Album(album, Integer.parseInt(anio), artistaModel);
            } catch (NumberFormatException e) {
                System.out.println("Ha ocurrido un error intentando pasar el año de String a Int");
                return;
            }
            ResultadoItunes itunes = ClienteiTunes.obtenerResultadoItunes(titulo + " " + artista);

            Genre genre = new Genre(itunes.primaryGenreName);

            // String filePath, String fileName, String title, int trackNumber,
            // int albumId, int genreId, String itunesUrl, String itunesArtworkUrl
            Cancion cancion = new Cancion(mp3.getAbsolutePath(), mp3.getName(), titulo, trackNumber, albumModel, genre,
                    itunes.trackViewUrl, itunes.artworkUrl100);
            UtilsBD.actualizarBD(cancion, artistaModel, albumModel, genre);

        } catch (IOException e) {
            System.err.println("Error leyendo ID3v1 de: " + mp3.getAbsolutePath());
            e.printStackTrace();
        }
    }

}

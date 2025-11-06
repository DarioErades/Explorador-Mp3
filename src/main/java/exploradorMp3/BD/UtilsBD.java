package exploradorMp3.BD;

import exploradorMp3.Model.Album;
import exploradorMp3.Model.Artista;
import exploradorMp3.Model.Cancion;
import exploradorMp3.Model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilsBD {
    // En esta clase tendré todos los métodos que incluyan consultas sql a la BD
    // Para no tener que estar haciendo SQL por todo el código, usaré solo estos métodos


    /*
        He buscado en internet cual es la forma más óptima de trabajar con la conexiones,
        y he encontrado que lo mejor es abrir una conexión y cerrarla por cada vez que se haga
        una consulta, por lo que haré métodos estáticos desde los que abriré y cerraré la conexión por cada consulta
     */
    public static void actualizarBD(Cancion cancion, Artista artista, Album album, Genre genre){
        Connection con = AccesoABD.obtenerConexionPgSql();
        try {
            int idArtista = existeArtista(con,artista.getNombre());
            if(idArtista == -1 ){
                idArtista = insertarArtista(con, artista.getNombre());
            }
            int idAlbum = existeAlbum(con,album.getTitle(),album.getArtistId().getId());
            if(idAlbum == -1 ) {
                // No existe
                idAlbum = insertarAlbum(con, album.getTitle(), album.getYear(),idArtista);
            }

            // Mismo procedimiento para genero y artista
            int idGenre = existeGenero(con,genre.getName());
            if(idGenre == -1){
                idGenre = insertarGenero(con,genre.getName());
            }


            
            if(existeCancion(con,cancion.getFilePath())){
                actualizarCancion(con,cancion,idAlbum,idGenre);
            }else{
                insertarCancion(con,cancion,idAlbum,idGenre);
            }




            con.close();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    private static int existeArtista(Connection con, String nombre) throws SQLException {
        String sql = "SELECT id FROM artist WHERE name = ?";
        // Si ya existe devuelvo su ID, sino devuelvo -1
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, nombre);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        }
    }
    private static int insertarArtista(Connection con, String nombre) throws SQLException {
        String sql = "INSERT INTO artist(name) VALUES (?) RETURNING id";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, nombre);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("id");
        }
    }

    private static int existeGenero(Connection con, String nombreGenero) throws SQLException {
        String sql = "SELECT * FROM genre WHERE name = ?";
        // Si ya existe devuelvo su ID, sino devuelvo -1
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, nombreGenero);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        }
    }

    private static int insertarGenero(Connection con, String nombre) throws SQLException {
        String sql = "INSERT INTO genre(name) VALUES (?) RETURNING id";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, nombre);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("id");
        }
    }

    private static int existeAlbum(Connection con, String titulo, int artistaId) throws SQLException {
        String sql = "SELECT * FROM album WHERE title = ?";
        // Si ya existe devuelvo su ID, sino devuelvo -1
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, titulo);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }else{
                return -1;
            }
        }
    }

    private static int insertarAlbum(Connection con, String titulo, int year, int artistaId) throws SQLException {
        String sql = "INSERT INTO album(title, year, artist_id) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, titulo);
            statement.setInt(2, year);
            statement.setInt(3, artistaId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("id");
        }
    }

    private static boolean existeCancion(Connection con, String filePath) throws SQLException {
        String sql = "SELECT * FROM track WHERE file_path = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, filePath);
            return statement.executeQuery().next();
        }
    }
    private static void insertarCancion(Connection con, Cancion c,int idAlbum, int idGenre) throws SQLException {
        String sql = "INSERT INTO track(file_path, file_name, title, track_number, album_id, genre_id, itunes_url, itunes_artwork_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, c.getFilePath());
            statement.setString(2, c.getFileName());
            statement.setString(3, c.getTitle());
            statement.setInt(4, c.getTrackNumber());
            statement.setInt(5, idAlbum);
            statement.setInt(6, idGenre);
            statement.setString(7, c.getItunesUrl());
            statement.setString(8, c.getItunesArtworkUrl());
            statement.executeUpdate();
        }
    }

    private static void actualizarCancion(Connection con, Cancion c, int idAlbum, int idGenre) throws SQLException {
        String sql = "UPDATE track SET file_name = ?, title = ?, track_number = ?, album_id = ?, genre_id = ?, itunes_url = ?, itunes_artwork_url = ? " +
                "WHERE file_path = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, c.getFileName());
            statement.setString(2, c.getTitle());
            statement.setInt(3, c.getTrackNumber());
            statement.setInt(4, idAlbum);
            statement.setInt(5, idGenre);
            statement.setString(6, c.getItunesUrl());
            statement.setString(7, c.getItunesArtworkUrl());
            statement.setString(8, c.getFilePath());
            statement.executeUpdate();
        }
    }

    private static String obtenerNombreGeneroPorId(Connection con, int idGenero) throws SQLException {
        String sql = "SELECT name FROM genre WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGenero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            } else {
                return "Género desconocido";
            }
        }
    }
    private static String obtenerTituloAlbumPorId(Connection con, int idAlbum) throws SQLException {
        String sql = "SELECT title FROM album WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlbum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("title");
            } else {
                return "Álbum desconocido";
            }
        }
    }

    public static void mostrarCancionesPorConsola(){
        try {
            Connection con = AccesoABD.obtenerConexionPgSql();

            PreparedStatement statement = con.prepareStatement("SELECT * FROM track");
            ResultSet rs = statement.executeQuery();
            System.out.println("CANCIONES GUARDADAS EN LA BD");
            System.out.println("ID\tRUTA\t\tTrack Number\tId Album\tId Género\tItunes url\tItunes artwork");

            while(rs.next()){
                int id = rs.getInt("id");
                String filePath = rs.getString("file_path");
                int trackNumber = rs.getInt("track_number");
                int albumId = rs.getInt("album_id");
                int genreId = rs.getInt("genre_id");
                String itunesUrl = rs.getString("itunes_url");
                String artworkUrl = rs.getString("itunes_artwork_url");

                String linea = id + "\t" +
                        filePath + "\t" +
                        trackNumber + "\t" +
                        obtenerTituloAlbumPorId(con,albumId) + "\t" +
                        obtenerNombreGeneroPorId(con,genreId) + "\t" +
                        (itunesUrl != null ? itunesUrl : "N/A") + "\t" +
                        (artworkUrl != null ? artworkUrl : "N/A");

                System.out.println(linea);
            }

            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void mostrarAlbumesPorConsola() {
        try (Connection con = AccesoABD.obtenerConexionPgSql()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM album");
            ResultSet rs = ps.executeQuery();

            System.out.println("ÁLBUMES GUARDADOS EN LA BD");
            System.out.println("ID\tTítulo\t\tAño\tID Artista");

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("title");
                int year = rs.getInt("year");
                int idArtista = rs.getInt("artist_id");

                System.out.println(id + "\t" + titulo + "\t" + year + "\t" + obtenerNombreArtistaPorId(con,idArtista));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static String obtenerNombreArtistaPorId(Connection con, int idArtista) throws SQLException {
        String sql = "SELECT name FROM artist WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idArtista);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            } else {
                return "Artista desconocido";
            }
        }
    }

    public static void mostrarGenerosPorConsola() {
        try (Connection con = AccesoABD.obtenerConexionPgSql()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM genre");
            ResultSet rs = ps.executeQuery();

            System.out.println("GÉNEROS GUARDADOS EN LA BD");
            System.out.println("ID\tNombre");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("name");

                System.out.println(id + "\t" + nombre);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarArtistasPorConsola() {
        try (Connection con = AccesoABD.obtenerConexionPgSql()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM artist");
            ResultSet rs = ps.executeQuery();

            System.out.println("ARTISTAS GUARDADOS EN LA BD");
            System.out.println("ID\tNombre");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("name");

                System.out.println(id + "\t" + nombre);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}

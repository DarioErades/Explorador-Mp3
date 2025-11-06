package exploradorMp3.Model;

public class Album {
    private int id;
    private String title;
    private int year;
    private Artista artista;


    public Album(String title, int year, Artista artista) {
        this.title = title;
        this.year = year;
        this.artista = artista;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Artista getArtistId() { return artista; }
    public void setArtistId(Artista artistId) { this.artista = artistId; }
}
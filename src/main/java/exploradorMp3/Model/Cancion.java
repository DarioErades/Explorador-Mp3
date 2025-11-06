package exploradorMp3.Model;

public class Cancion {
	private int id;
	private String filePath;
	private String fileName;
	private String title;
	private int trackNumber;
	private Album album;
	private Genre genre;
	private String itunesUrl;
	private String itunesArtworkUrl;


	public Cancion(String filePath, String fileName, String title, int trackNumber,
				   Album album, Genre genre, String itunesUrl, String itunesArtworkUrl) {
		this.filePath = filePath;
		this.fileName = fileName;
		this.title = title;
		this.trackNumber = trackNumber;
		this.album = album;
		this.genre = genre;
		this.itunesUrl = itunesUrl;
		this.itunesArtworkUrl = itunesArtworkUrl;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getFilePath() { return filePath; }
	public void setFilePath(String filePath) { this.filePath = filePath; }

	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public int getTrackNumber() { return trackNumber; }
	public void setTrackNumber(int trackNumber) { this.trackNumber = trackNumber; }

	public Album getAlbum() { return album; }
	public void setAlbum(Album album) { this.album = album; }

	public Genre getGenre() { return genre; }
	public void setGenre(Genre genre) { this.genre = genre; }

	public String getItunesUrl() { return itunesUrl; }
	public void setItunesUrl(String itunesUrl) { this.itunesUrl = itunesUrl; }

	public String getItunesArtworkUrl() { return itunesArtworkUrl; }
	public void setItunesArtworkUrl(String itunesArtworkUrl) { this.itunesArtworkUrl = itunesArtworkUrl; }
}
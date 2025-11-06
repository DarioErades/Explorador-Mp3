package exploradorMp3;

import java.io.Serializable;


public class Cancion implements Serializable { // Esta es la clase que se serializará con la información de la canción


	private static final long serialVersionUID = 1L;
	

	public String rutaArchivo;
    public String titulo;
    public String artista;
    public String album;
    public String anio;
    public String comentario;

    public int codigoGenero;


	// Campos adicionales de ICLOUD
	public String previewURL;

	public String collectionCensoredName;

	public String imagenUrl;

	public float trackPrice;


    
    //  Todos los getter y setters
    public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}



	public String getPreviewURL() {
		return previewURL;
	}

	public void setPreviewURL(String previewURL) {
		this.previewURL = previewURL;
	}

	public String getCollectionCensoredName() {
		return collectionCensoredName;
	}

	public void setCollectionCensoredName(String collectionCensoredName) {
		this.collectionCensoredName = collectionCensoredName;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public float getTrackPrice() {
		return trackPrice;
	}

	public void setTrackPrice(float trackPrice) {
		this.trackPrice = trackPrice;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}



	public int getCodigoGenero() {
		return codigoGenero;
	}

	public void setCodigoGenero(int codigoGenero) {
		this.codigoGenero = codigoGenero;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public Cancion(String rutaArchivo, String titulo, String artista, String album,
                   String ano, String comentario, int codigoGenero) {
        this.rutaArchivo = rutaArchivo;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.anio = ano;
        this.comentario = comentario;

        this.codigoGenero = codigoGenero;

		anadirDatosItunes(); // Cada vez que se crea una clase se añaden los datos del itunes
    }

	public void anadirDatosItunes(){
		ResultadoItunes ritunes = ClienteiTunes.obtenerResultadoItunes(artista+" "+titulo);
		collectionCensoredName = ritunes.getCollectionCensoredName();
		previewURL = ritunes.getTrackViewUrl();
		imagenUrl = ritunes.getArtworkUrl100();;
		trackPrice = ritunes.getTrackPrice();


	}

	@Override
	public String toString() {
		String n = System.lineSeparator();
		return "------------------------" + n +
				"Título: " + titulo + n +
				"Artista: " + artista + n +
				"Álbum: " + album + n +
				"Año: " + anio + n +
				"Ruta: " + rutaArchivo + n +
				"Enlace iTunes: " + previewURL + n +
				"Nombre colección: " + collectionCensoredName + n +
				"Carátula: " + imagenUrl + n +
				"Precio: " + trackPrice + " €" + n +
				"------------------------";
	}




}

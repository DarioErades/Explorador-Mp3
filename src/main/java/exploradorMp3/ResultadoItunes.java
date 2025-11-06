package exploradorMp3;


// Clase para deserializar con Gson, son los datos que necesito unicamente
public class ResultadoItunes {
    public String trackViewUrl;
    public String collectionCensoredName;
    public String artworkUrl100;
    public float trackPrice;

    public ResultadoItunes(String trackViewUrl, String collectionCensoredName, String artworkUrl100, float trackPrice) {
        this.trackViewUrl = trackViewUrl;
        this.collectionCensoredName = collectionCensoredName;
        this.artworkUrl100 = artworkUrl100;
        this.trackPrice = trackPrice;
    }

    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    public void setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
    }

    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    public void setCollectionCensoredName(String collectionCensoredName) {
        this.collectionCensoredName = collectionCensoredName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public float getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(float trackPrice) {
        this.trackPrice = trackPrice;
    }
}
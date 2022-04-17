package id.co.hope.app.artikel.model;

public class ArtikelModel {
    String id_artikel, judul_artikel, url_artikel, tgl_artikel, deleted_artikel, foto_artikel;


    public ArtikelModel() {
    }

    public String getFoto_artikel() {
        return foto_artikel;
    }

    public String getId_artikel() {
        return id_artikel;
    }

    public String getTgl_artikel() {
        return tgl_artikel;
    }

    public String getJudul_artikel() {
        return judul_artikel;
    }

    public String getUrl_artikel() {
        return url_artikel;
    }

    public String getDeleted_artikel() {
        return deleted_artikel;
    }
}

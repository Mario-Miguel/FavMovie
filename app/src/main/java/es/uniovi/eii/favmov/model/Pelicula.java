package es.uniovi.eii.favmov.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pelicula implements Parcelable {

    private int id;
    private String titulo;
    private String argumento;
    private Categoria categoria;
    private String duracion;
    private String fecha;

    private String urlCaratula;
    private String urlFondo;
    private String urlTrailer;

    public Pelicula(String titulo, String argumento, Categoria categoria, String duracion, String fecha) {
        this.titulo = titulo;
        this.argumento = argumento;
        this.categoria = categoria;
        this.duracion = duracion;
        this.fecha = fecha;
    }

    public Pelicula(String titulo, String argumento, Categoria categoria, String duracion, String fecha, int id) {
        this.titulo = titulo;
        this.argumento = argumento;
        this.categoria = categoria;
        this.duracion = duracion;
        this.fecha = fecha;
        this.id=id;
    }

    public Pelicula(){

    }

    protected Pelicula(Parcel in) {
        titulo = in.readString();
        argumento = in.readString();
        categoria = in.readParcelable(Categoria.class.getClassLoader());
        duracion = in.readString();
        fecha = in.readString();
        urlCaratula = in.readString();
        urlFondo = in.readString();
        urlTrailer = in.readString();
        id = in.readInt();
    }

    public static final Creator<Pelicula> CREATOR = new Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel in) {
            return new Pelicula(in);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };

    public Pelicula(String titulo, String argumento, Categoria categoria, String duracion, String fecha, String urlCaratula, String urlFondo, String urlTrailer) {
        this(titulo, argumento, categoria, duracion, fecha);
        this.urlCaratula=urlCaratula;
        this.urlFondo=urlFondo;
        this.urlTrailer=urlTrailer;
    }

    public Pelicula(int id, String titulo, String argumento, Categoria categoria, String duracion, String fecha,  String urlCaratula, String urlFondo, String urlTrailer) {
        this(titulo, argumento, categoria, duracion, fecha, id);
        this.urlCaratula=urlCaratula;
        this.urlFondo=urlFondo;
        this.urlTrailer=urlTrailer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(argumento);
        parcel.writeParcelable(categoria, i);
        parcel.writeString(duracion);
        parcel.writeString(fecha);
        parcel.writeString(urlCaratula);
        parcel.writeString(urlFondo);
        parcel.writeString(urlTrailer);
        parcel.writeInt(id);

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArgumento() {
        return argumento;
    }

    public void setArgumento(String argumento) {
        this.argumento = argumento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrlCaratula() {
        return urlCaratula;
    }

    public void setUrlCaratula(String urlCaratula) {
        this.urlCaratula = urlCaratula;
    }

    public String getUrlFondo() {
        return urlFondo;
    }

    public void setUrlFondo(String urlFondo) {
        this.urlFondo = urlFondo;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "nombre='" + titulo + '\'' +
                ", argumento='" + argumento + '\'' +
                ", categoria=" + categoria +
                ", duracion='" + duracion + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

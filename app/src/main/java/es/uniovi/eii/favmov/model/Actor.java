package es.uniovi.eii.favmov.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Actor implements Parcelable {

    private int id;
    private String nombre;
    private String nombrePersonaje;
    private String urlImagenActor;
    private String urlIMDB;


    public Actor(){

    }

    public Actor(int id, String nombre, String urlImagenActor, String urlIMDB) {
        this.id = id;
        this.nombre = nombre;
        this.urlImagenActor = urlImagenActor;
        this.urlIMDB = urlIMDB;
        this.nombrePersonaje = "";
    }

    protected Actor(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        urlImagenActor = in.readString();
        urlIMDB = in.readString();
        nombrePersonaje=in.readString();
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    public Actor(String nombre, String nombrePersonaje, String urlImagenActor, String urlIMDB) {
        this.nombre = nombre;
        this.urlImagenActor = urlImagenActor;
        this.urlIMDB = urlIMDB;
        this.nombrePersonaje = nombrePersonaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagenActor() {
        return urlImagenActor;
    }

    public void setUrlImagenActor(String urlImagenActor) {
        this.urlImagenActor = urlImagenActor;
    }

    public String getUrlIMDB() {
        return urlIMDB;
    }

    public void setUrlIMDB(String urlIMDB) {
        this.urlIMDB = urlIMDB;
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public void setNombrePersonaje(String nombrePersonaje) {
        this.nombrePersonaje = nombrePersonaje;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", urlImagenActor='" + urlImagenActor + '\'' +
                ", urlIMDB='" + urlIMDB + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeString(urlImagenActor);
        parcel.writeString(urlIMDB);
        parcel.writeString(nombrePersonaje);
    }
}

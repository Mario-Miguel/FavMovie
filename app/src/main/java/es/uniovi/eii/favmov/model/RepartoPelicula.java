package es.uniovi.eii.favmov.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RepartoPelicula implements Parcelable {

    private int idPelicula;
    private int idActor;
    private String personaje;

    public RepartoPelicula(){}

    public RepartoPelicula( int idActor, int idPelicula, String personaje) {
        this.idPelicula = idPelicula;
        this.idActor = idActor;
        this.personaje = personaje;
    }

    protected RepartoPelicula(Parcel in) {
        idPelicula = in.readInt();
        idActor = in.readInt();
        personaje = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPelicula);
        dest.writeInt(idActor);
        dest.writeString(personaje);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RepartoPelicula> CREATOR = new Creator<RepartoPelicula>() {
        @Override
        public RepartoPelicula createFromParcel(Parcel in) {
            return new RepartoPelicula(in);
        }

        @Override
        public RepartoPelicula[] newArray(int size) {
            return new RepartoPelicula[size];
        }
    };

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    @Override
    public String toString() {
        return "RepartoPelicula{" +
                "idPelicula='" + idPelicula + '\'' +
                ", idActor='" + idActor + '\'' +
                ", personaje='" + personaje + '\'' +
                '}';
    }
}

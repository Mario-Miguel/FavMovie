package es.uniovi.eii.favmov.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Conexion {
    private Context context;

    public Conexion(Context context){
        this.context=context;
    }

    public boolean compruebaConexion(){
        boolean connected = false;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        connected = activeNetwork !=null && activeNetwork.isConnectedOrConnecting();

        return connected;
    }
}

package es.uniovi.eii.favmov.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.uniovi.eii.favmov.R;


public class InfoFragment extends Fragment {

    private static final String BASE_URL_IMG= "https://image.tmdb.org/t/p/";
    private static final String IMG_W342= "w342";
    private static final String IMG_ORIGINAL= "original";

    public static final String ESTRENO = "estreno";
    public static final String DURACION = "duracion";
    public static final String CARATULA = "caratula";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info, container, false);

        final TextView txtEstreno = root.findViewById(R.id.estreno);
        final TextView txtDuracion = root.findViewById(R.id.duracion);
        final ImageView imgCaratula = root.findViewById(R.id.caratula);

        Bundle args = getArguments();
        if(args != null){
            txtEstreno.setText(args.getString(ESTRENO));
            txtDuracion.setText(args.getString(DURACION));
            String urlCaratula= BASE_URL_IMG + IMG_W342 + args.getString(CARATULA);
            Picasso.get().load(urlCaratula).into(imgCaratula);
        }


        // Inflate the layout for this fragment
        return root;
    }
}
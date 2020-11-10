package es.uniovi.eii.favmov.fragments;

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

            Picasso.get().load(args.getString(CARATULA)).into(imgCaratula);
        }


        // Inflate the layout for this fragment
        return root;
    }
}
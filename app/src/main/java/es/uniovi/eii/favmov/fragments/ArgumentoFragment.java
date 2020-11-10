package es.uniovi.eii.favmov.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import es.uniovi.eii.favmov.R;

public class ArgumentoFragment extends Fragment {

    public static final String ARGUMENTO = "argumento";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_argumento, container, false);

        final TextView txtArgumento = root.findViewById(R.id.argumento);


        Bundle args = getArguments();
        if(args != null){
            txtArgumento.setText(args.getString(ARGUMENTO));

        }


        // Inflate the layout for this fragment
        return root;
    }
}
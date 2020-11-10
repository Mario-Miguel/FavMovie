package es.uniovi.eii.favmov.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmov.adapters.ListaActoresAdapter;
import es.uniovi.eii.favmov.R;
import es.uniovi.eii.favmov.datos.ActorsDataSource;
import es.uniovi.eii.favmov.model.Actor;

public class ActoresFragment extends Fragment {

    public static final String ACTORES = "actores";

    List<Actor> listActores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_actores, container, false);

        final RecyclerView recyclerActors = root.findViewById(R.id.recyclerViewActores);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerActors.setLayoutManager(layoutManager);

        recyclerActors.setHasFixedSize(true);

        Bundle args = getArguments();
        int id_pelicula =args.getInt("id_pelicula");
        listActores = new ArrayList<Actor>();


        ActorsDataSource actorsDataSource = new ActorsDataSource(root.getContext());
        actorsDataSource.open();
        listActores = actorsDataSource.actoresParticipantes(id_pelicula);
        actorsDataSource.close();


        ListaActoresAdapter laAdapter = new ListaActoresAdapter(listActores,
                new ListaActoresAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Actor actor) {
                        //Si pulsamos sobre un actor nos llevará a su ficha en Imdb
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(actor.getUrlIMDB())));
                    }
                });
        recyclerActors.setAdapter(laAdapter);


        // Inflate the layout for this fragment
        return root;
    }

}
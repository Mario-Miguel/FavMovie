package es.uniovi.eii.favmov.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.uniovi.eii.favmov.MainRecycler;
import es.uniovi.eii.favmov.R;
import es.uniovi.eii.favmov.ShowMovieActivity;
import es.uniovi.eii.favmov.adapters.ListaPeliculaAdapter;
import es.uniovi.eii.favmov.datos.bd.MovieDataSource;
import es.uniovi.eii.favmov.model.Pelicula;

public class FavFragment extends Fragment {

    public static final String PELICULA_SELECCIONADA = "pelicula_seleccionada";
    public static final String PELICULA_CREADA = "pelicula_creada";

    List<Pelicula> listPelicula;
    RecyclerView listaPeliculasView;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_fav, container, false);

        listaPeliculasView = (RecyclerView) root.findViewById(R.id.recyclerView);
        listaPeliculasView.setHasFixedSize(true);

        cargarBD();

        return root;
    }


    protected void cargarBD() {

        MovieDataSource movieDataSource = new MovieDataSource(root.getContext());
        movieDataSource.open();

        listPelicula = movieDataSource.getAllValorations();

        movieDataSource.close();

        mostrarListaPeliculas();
    }

    private void mostrarListaPeliculas() {
        listaPeliculasView = (RecyclerView) root.findViewById(R.id.recyclerView);
        listaPeliculasView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        listaPeliculasView.setLayoutManager(layoutManager);
        ListaPeliculaAdapter peliculaAdapter = new ListaPeliculaAdapter(listPelicula, new ListaPeliculaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pelicula pelicula) {
                clickOnItem(pelicula);
            }
        });
        listaPeliculasView.setAdapter(peliculaAdapter);

    }

    public void clickOnItem(Pelicula pelicula) {
        //Intent intent = new Intent(MainRecycler.this, MainActivity.class);
        Intent intent = new Intent(root.getContext(), ShowMovieActivity.class);
        intent.putExtra(PELICULA_SELECCIONADA, pelicula);
        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        startActivity(intent);

    }
}
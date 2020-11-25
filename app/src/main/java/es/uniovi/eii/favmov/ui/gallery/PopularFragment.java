package es.uniovi.eii.favmov.ui.gallery;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.uniovi.eii.favmov.MainRecycler;
import es.uniovi.eii.favmov.R;
import es.uniovi.eii.favmov.ShowMovieActivity;
import es.uniovi.eii.favmov.adapters.ListaPeliculaAdapter;
import es.uniovi.eii.favmov.datos.server.ServerDataMapper;
import es.uniovi.eii.favmov.datos.server.movielist.MovieData;
import es.uniovi.eii.favmov.datos.server.movielist.MovieListResult;
import es.uniovi.eii.favmov.model.Pelicula;
import es.uniovi.eii.favmov.remote.ApiUtils;
import es.uniovi.eii.favmov.remote.TheMovieDBApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static es.uniovi.eii.favmov.remote.ApiUtils.API_KEY;
import static es.uniovi.eii.favmov.remote.ApiUtils.LANGUAGE;

public class PopularFragment extends Fragment {

    public static final String PELICULA_SELECCIONADA = "pelicula_seleccionada";
    public static final String PELICULA_CREADA = "pelicula_creada";

    View root;
    private RecyclerView listaPeliculasView;
    private TheMovieDBApi peticionApi;
    List<Pelicula> listPelicula;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_popular, container, false);

        listaPeliculasView = (RecyclerView) root.findViewById(R.id.recyclerView);
        listaPeliculasView.setHasFixedSize(true);

        peticionApi = ApiUtils.createTheMovieDBApi();
        realizarPeticionPeliculasPopulares(peticionApi);

        return root;
    }

    private void realizarPeticionPeliculasPopulares(TheMovieDBApi peticionApi) {
        Call<MovieListResult> call = peticionApi.getMoviesList("popular", API_KEY, LANGUAGE, 1);

        call.enqueue(new Callback<MovieListResult>(){

            @Override
            public void onResponse(Call<MovieListResult> call, Response<MovieListResult> response) {
                switch(response.code()){
                    case 200: //Respuesta válida
                        MovieListResult data = response.body();
                        List<MovieData> listMovieData = data.getMovieData();
                        Log.d("realizarPetPopulares", "ListaDatosPeliculas: "+listMovieData);

                        //Convertir datos de la api a clases del modelo

                        listPelicula = ServerDataMapper.convertMovieListToDomain(listMovieData);

                        //Esto ye super cutre pero ye pa presentar los datos
                        mostrarListaPeliculas();

                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<MovieListResult> call, Throwable t) {
                Log.e("ErrorPetPopulares", String.valueOf(t.getStackTrace()));
            }
        });
    }

    private void mostrarListaPeliculas() {
        listaPeliculasView = root.findViewById(R.id.recyclerView);
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
package es.uniovi.eii.favmov.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import es.uniovi.eii.favmov.datos.bd.ActorsDataSource;
import es.uniovi.eii.favmov.datos.server.ServerDataMapper;
import es.uniovi.eii.favmov.datos.server.credits.Credits;
import es.uniovi.eii.favmov.datos.server.moviedetails.MovieDetail;
import es.uniovi.eii.favmov.model.Actor;
import es.uniovi.eii.favmov.remote.ApiUtils;
import es.uniovi.eii.favmov.remote.TheMovieDBApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static es.uniovi.eii.favmov.datos.server.ServerDataMapper.convertMovieDetailToDomain;
import static es.uniovi.eii.favmov.remote.ApiUtils.API_KEY;
import static es.uniovi.eii.favmov.remote.ApiUtils.LANGUAGE;

public class ActoresFragment extends Fragment {

    public static final String ACTORES = "actores";

    View root;
    List<Actor> listActores;

    TheMovieDBApi peticionApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_actores, container, false);


        Bundle args = getArguments();
        int id_pelicula = args.getInt("id_pelicula");
        listActores = new ArrayList<Actor>();


//        ActorsDataSource actorsDataSource = new ActorsDataSource(root.getContext());
//        actorsDataSource.open();
//        listActores = actorsDataSource.actoresParticipantes(id_pelicula);
//        actorsDataSource.close();

//        listActores = args.getParcelableArrayList("listActores");


        // Inflate the layout for this fragment

        peticionApi = ApiUtils.createTheMovieDBApi();
        realizarPeticionReparto(peticionApi, id_pelicula);
        return root;
    }


    private void realizarPeticionReparto(TheMovieDBApi peticionApi, int id) {
        Call<Credits> call = peticionApi.getMovieCredits(id, API_KEY, LANGUAGE);

        call.enqueue(new Callback<Credits>() {

            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                switch (response.code()) {
                    case 200: //Respuesta válida
                        Credits data = response.body();


                        //Convertir datos de la api a clases del modelo

//                        convertMovieDetailToDomain(data, pelicula);
//                        mostrarDatos(pelicula);



                        listActores = ServerDataMapper.convertCreditsToDomain(data);
                        //Log.d("peticionActores", "Credits: "+listActores);
//
                        ListaActoresAdapter laAdapter = new ListaActoresAdapter(listActores,
                                new ListaActoresAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Actor actor) {
                                        //Si pulsamos sobre un actor nos llevará a su ficha en Imdb
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(actor.getUrlIMDB())));
                                    }
                                });
//

                        final RecyclerView recyclerActors = root.findViewById(R.id.recyclerViewActores);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
                        recyclerActors.setLayoutManager(layoutManager);

                        recyclerActors.setHasFixedSize(true);
                        recyclerActors.setAdapter(laAdapter);

                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Log.e("ErrorPetPopulares", String.valueOf(t.getStackTrace()));
            }
        });
    }

}
package es.uniovi.eii.favmov;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.uniovi.eii.favmov.datos.server.ServerDataMapper;
import es.uniovi.eii.favmov.datos.server.moviedetails.MovieDetail;
import es.uniovi.eii.favmov.datos.server.moviedetails.Result;
import es.uniovi.eii.favmov.datos.server.moviedetails.Videos;
import es.uniovi.eii.favmov.datos.server.movielist.MovieData;
import es.uniovi.eii.favmov.datos.server.movielist.MovieListResult;
import es.uniovi.eii.favmov.fragments.ActoresFragment;
import es.uniovi.eii.favmov.fragments.ArgumentoFragment;
import es.uniovi.eii.favmov.fragments.InfoFragment;
import es.uniovi.eii.favmov.model.Pelicula;
import es.uniovi.eii.favmov.remote.ApiUtils;
import es.uniovi.eii.favmov.remote.TheMovieDBApi;
import es.uniovi.eii.favmov.util.Conexion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static es.uniovi.eii.favmov.remote.ApiUtils.API_KEY;
import static es.uniovi.eii.favmov.remote.ApiUtils.LANGUAGE;

public class ShowMovieActivity extends AppCompatActivity {

    private Pelicula pelicula;

    private ImageView imagenFondo;
    private TextView categoria;
    private TextView estreno;
    private TextView duracion;
    private TextView argumento;
    private ImageView caratula;

    CollapsingToolbarLayout toolBarLayout;
    TheMovieDBApi peticionApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        //Capturar el intent
        Intent intentPelicula = getIntent();
        pelicula = intentPelicula.getParcelableExtra(MainRecycler.PELICULA_SELECCIONADA);

        //Peticion al servicio para completar datos como activity secundaria
        peticionApi = ApiUtils.createTheMovieDBApi();
        realizarPeticionDetallesPeliculas(peticionApi, pelicula.getId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        imagenFondo = findViewById(R.id.imagenFondo);
        categoria = findViewById(R.id.categoria);
        estreno = findViewById(R.id.estreno);
        duracion = findViewById(R.id.duracion);
        argumento = findViewById(R.id.argumento);
        caratula = findViewById(R.id.caratula);

        if (pelicula != null)
            mostrarDatos(pelicula);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("urlTrailer", "urlTrailer" + pelicula.getUrlTrailer());
                verTrailer(pelicula.getUrlTrailer());
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void realizarPeticionDetallesPeliculas(TheMovieDBApi peticionApi, int id) {
        Call<MovieDetail> call = peticionApi.getMovieDetail(id, API_KEY, LANGUAGE, "videos");

        call.enqueue(new Callback<MovieDetail>() {

            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                switch (response.code()) {
                    case 200: //Respuesta válida
                        MovieDetail data = response.body();

                        Log.d("realizarPetPopulares", "DetallesPelicula: " + data.getReleaseDate());

                        //Convertir datos de la api a clases del modelo

                        String urlTrailer = null;
                        List<Result> videos = data.getVideos().getResults();
                        for (Result video : videos) {
                            if (video.getSite().toLowerCase().equals("youtube")) {
                                urlTrailer = "https://www.youtube.com/watch?v=" + video.getKey();
                                break;
                            }
                        }

                        pelicula.setUrlTrailer(urlTrailer);


//                        listPelicula = ServerDataMapper.convertMovieListToDomain(listMovieData);
//
//                        //Esto ye super cutre pero ye pa presentar los datos
//                        listaPeliculasView = (RecyclerView) findViewById(R.id.recyclerView);
//                        listaPeliculasView.setHasFixedSize(true);
//
//                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                        listaPeliculasView.setLayoutManager(layoutManager);


                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.e("ErrorPetPopulares", String.valueOf(t.getStackTrace()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if(id==R.id.action_settings){
//            return true;
//        }

        if (id == R.id.compartir) {
            Conexion conexion = new Conexion(getApplicationContext());
            if (conexion.compruebaConexion()) {
                compartirPeli();
            } else {
                Toast.makeText(getApplicationContext(), R.string.error_conexion, Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void compartirPeli() {
        /* es necesario hacer un intent con la constate ACTION_SEND */
        /*Llama a cualquier app que haga un envío*/
        Intent itSend = new Intent(Intent.ACTION_SEND);
        /* vamos a enviar texto plano */
        itSend.setType("text/plain");
        // itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{para});
        itSend.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.subject_compartir) + ": " + pelicula.getTitulo());
        itSend.putExtra(Intent.EXTRA_TEXT, getString(R.string.HintTitulo)
                + ": " + pelicula.getTitulo() + "\n" +
                getString(R.string.Argumento)
                + ": " + pelicula.getArgumento());
        /* iniciamos la actividad */
            /* puede haber más de una aplicacion a la que hacer un ACTION_SEND,
               nos sale un ventana que nos permite elegir una.
               Si no lo pongo y no hay activity disponible, pueda dar un error */
        Intent shareIntent = Intent.createChooser(itSend, null);
        startActivity(shareIntent);

    }

    public void mostrarDatos(Pelicula pelicula) {
        if (!pelicula.getTitulo().isEmpty()) {
            String fecha = pelicula.getFecha();
            toolBarLayout.setTitle(pelicula.getTitulo() + " (" + fecha.substring(fecha.lastIndexOf('/') + 1) + ")");

            Picasso.get().load(pelicula.getUrlFondo()).into(imagenFondo);

            InfoFragment info = new InfoFragment();
            Bundle args = new Bundle();
            args.putString(InfoFragment.ESTRENO, pelicula.getFecha());
            args.putString(InfoFragment.DURACION, pelicula.getDuracion());
            args.putString(InfoFragment.CARATULA, pelicula.getUrlCaratula());
            info.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info).commit();


        }
    }

    private void verTrailer(String urlTrailer) {
        if (urlTrailer != null)
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlTrailer)));
        else
            Toast.makeText(getApplicationContext(), R.string.error_trailer, Toast.LENGTH_LONG).show();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle args = new Bundle();

            if (pelicula != null) {
                switch (item.getItemId()) {
                    case R.id.navigation_info:
                        //Creamos el framento de información
                        InfoFragment info = new InfoFragment();

                        args.putString(InfoFragment.ESTRENO, pelicula.getFecha());
                        args.putString(InfoFragment.DURACION, pelicula.getDuracion());
                        args.putString(InfoFragment.CARATULA, pelicula.getUrlCaratula());
                        info.setArguments(args);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info).commit();
                        return true;

                    case R.id.navigation_actores:
                        //Creamos el framento de actores
                        ActoresFragment actores = new ActoresFragment();
                        //args.putString(ActoresFragment.ACTORES, "La pelicula no tiene actores de momoento");
                        args.putInt("id_pelicula", pelicula.getId());
                        actores.setArguments(args);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, actores).commit();
                        return true;

                    case R.id.navigation_argumento:
                        //Creamos el framento de argumento
                        ArgumentoFragment argumento = new ArgumentoFragment();
                        args.putString(ArgumentoFragment.ARGUMENTO, pelicula.getArgumento());
                        argumento.setArguments(args);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, argumento).commit();
                        return true;
                }
            }
            return false;

        }
    };
}
package es.uniovi.eii.favmov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmov.adapters.ListaPeliculaAdapter;
import es.uniovi.eii.favmov.datos.bd.ActorMovieDataSource;
import es.uniovi.eii.favmov.datos.bd.ActorsDataSource;
import es.uniovi.eii.favmov.datos.bd.MovieDataSource;
import es.uniovi.eii.favmov.datos.server.ServerDataMapper;
import es.uniovi.eii.favmov.datos.server.movielist.MovieData;
import es.uniovi.eii.favmov.datos.server.movielist.MovieListResult;
import es.uniovi.eii.favmov.model.Actor;
import es.uniovi.eii.favmov.model.Categoria;
import es.uniovi.eii.favmov.model.Pelicula;
import es.uniovi.eii.favmov.model.RepartoPelicula;
import es.uniovi.eii.favmov.remote.ApiUtils;
import es.uniovi.eii.favmov.remote.TheMovieDBApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static es.uniovi.eii.favmov.remote.ApiUtils.API_KEY;
import static es.uniovi.eii.favmov.remote.ApiUtils.LANGUAGE;

public class MainRecycler extends AppCompatActivity {

    public static final String PELICULA_SELECCIONADA = "pelicula_seleccionada";
    public static final String PELICULA_CREADA = "pelicula_creada";

    //Identificador activity
    private static final int GESTION_PELICULA = 2;

    //Fitro para las categorias
    public static String filtroCategoria = null;

    List<Pelicula> listPelicula;
    RecyclerView listaPeliculasView;
//    FloatingActionButton btnCrear;

    String caratula_por_defecto = "https://image.tmdb.org/t/p/original/jnFCk7qGGWop2DgfnJXeKLZFuBq.jpg\n";
    String fondo_por_defecto = "https://image.tmdb.org/t/p/original/xJWPZIYOEFIjZpBL7SVBGnzRYXp.jpg\n";
    String trailer_por_defecto = "https://www.youtube.com/watch?v=lpEJVgysiWs\n";

    SharedPreferences sharedPreferencesMainRecycler;

    //Objetos notificaciones
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;
    //Cliente para hacer las peticiones
    private TheMovieDBApi peticionApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        //rellenarLista();
//        cargarPeliculas();
//
//        listaPeliculasView = (RecyclerView) findViewById(R.id.recyclerView);
//        listaPeliculasView.setHasFixedSize(true);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        listaPeliculasView.setLayoutManager(layoutManager);
//
//
//        updateListaPeliculas();

//        btnCrear = findViewById(R.id.fbtnCrearPelicula);
//        btnCrear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainRecycler.this, MainActivity.class);
//                startActivityForResult(intent, GESTION_PELICULA);
//            }
//        });

//        ConstruirNotificacion(getString(R.string.app_name), "Acceso a la BD de peliculas");
//        DownloadFilesTask task = new DownloadFilesTask();
//        task.execute();

        peticionApi = ApiUtils.createTheMovieDBApi();
        realizarPeticionPeliculasPopulares(peticionApi);

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
                        listaPeliculasView = (RecyclerView) findViewById(R.id.recyclerView);
                        listaPeliculasView.setHasFixedSize(true);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        listaPeliculasView.setLayoutManager(layoutManager);


                        updateListaPeliculas();
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


    @Override
    protected void onResume() {
        super.onResume();

//        cargarPeliculas();
//        cargarReparto();
//        cargarRepartoPelicula();

//        sharedPreferencesMainRecycler = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
//        filtroCategoria = sharedPreferencesMainRecycler.getString("keyCategoria", "");
//
//        MovieDataSource movieDataSource = new MovieDataSource(getApplicationContext());
//        movieDataSource.open();
//
//        if (filtroCategoria == null) {
//            listPelicula = movieDataSource.getAllValorations();
//        } else {
//            listPelicula = movieDataSource.getFilteredValorations(filtroCategoria);
//        }
//
//        movieDataSource.close();
//
//        listaPeliculasView = (RecyclerView) findViewById(R.id.recyclerView);
//        listaPeliculasView.setHasFixedSize(true);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        listaPeliculasView.setLayoutManager(layoutManager);
//
//
//        updateListaPeliculas();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GESTION_PELICULA) {
            if (resultCode == RESULT_OK) {
                Pelicula peliculaAux = data.getParcelableExtra(PELICULA_CREADA);
                if (peliculaAux.getUrlCaratula() == null)
                    peliculaAux.setUrlCaratula(caratula_por_defecto);
                if (peliculaAux.getUrlFondo() == null)
                    peliculaAux.setUrlFondo(fondo_por_defecto);
                if (peliculaAux.getUrlTrailer() == null)
                    peliculaAux.setUrlTrailer(trailer_por_defecto);
                listPelicula.add(peliculaAux);
                updateListaPeliculas();
            }
        }
    }


    public void clickOnItem(Pelicula pelicula) {
        //Intent intent = new Intent(MainRecycler.this, MainActivity.class);
        Intent intent = new Intent(MainRecycler.this, ShowMovieActivity.class);
        intent.putExtra(PELICULA_SELECCIONADA, pelicula);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    private void rellenarLista() {
        listPelicula = new ArrayList<>();
        Categoria categoria = new Categoria("Acción", "Película de accion to chachi");
        Pelicula peli = new Pelicula("Tenet", "Una acción épica que gira en torno al espionaje internacional, los viajes en el tiempo y la evolución, en la que un agente secreto debe prevenir la Tercera Guerra Mundial.",
                categoria, "150", "26/8/2020");
        listPelicula.add(peli);
    }

    private void updateListaPeliculas() {
        ListaPeliculaAdapter peliculaAdapter = new ListaPeliculaAdapter(listPelicula, new ListaPeliculaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pelicula pelicula) {
                clickOnItem(pelicula);
            }
        });
        listaPeliculasView.setAdapter(peliculaAdapter);

    }

    protected void cargarView() {
        sharedPreferencesMainRecycler = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        filtroCategoria = sharedPreferencesMainRecycler.getString("keyCategoria", "");

        MovieDataSource movieDataSource = new MovieDataSource(getApplicationContext());
        movieDataSource.open();

        if (filtroCategoria == null) {
            listPelicula = movieDataSource.getAllValorations();
        } else {
            listPelicula = movieDataSource.getFilteredValorations(filtroCategoria);
        }

        movieDataSource.close();

        listaPeliculasView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPeliculasView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaPeliculasView.setLayoutManager(layoutManager);


        updateListaPeliculas();
    }

//    protected void cargarPeliculas() {
//        /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro estos campos en las películas*/
//        Pelicula peli;
//        listPelicula = new ArrayList<Pelicula>();
//        InputStream file = null;
//        InputStreamReader reader = null;
//        BufferedReader bufferedReader = null;
//        try {
//            //file = getAssets().open("lista_peliculas_url_utf8.csv");
//            file = getAssets().open("peliculas.csv");
//            reader = new InputStreamReader(file);
//            bufferedReader = new BufferedReader(reader);
//            bufferedReader.readLine();
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] data = line.split(";");
//                if (data != null && data.length >= 5) {
//
//                    peli = new Pelicula(Integer.parseInt(data[0]), data[1], data[2], new Categoria(data[3], ""), data[4], data[5], data[6], data[7], data[8]);
//
//                    Log.d("cargarPeliculas", peli.toString());
//                    listPelicula.add(peli);
//
//                    MovieDataSource movieDataSource = new MovieDataSource(this);
//                    movieDataSource.open();
//                    movieDataSource.createpelicula(peli);
//                    movieDataSource.close();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    protected void cargarRepartoPelicula() {
//        /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro estos campos en las películas*/
//        RepartoPelicula repartoPeli;
//        InputStream file = null;
//        InputStreamReader reader = null;
//        BufferedReader bufferedReader = null;
//        try {
//            //file = getAssets().open("lista_peliculas_url_utf8.csv");
//            file = getAssets().open("peliculas-reparto.csv");
//            reader = new InputStreamReader(file);
//            bufferedReader = new BufferedReader(reader);
//            bufferedReader.readLine();
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] data = line.split(";");
//                if (data != null && data.length >= 3) {
//
//                    repartoPeli = new RepartoPelicula(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]);
//
//                    Log.d("cargarRepartoPeliculas", repartoPeli.toString());
//
//                    ActorMovieDataSource actorMovieDataSource = new ActorMovieDataSource(this);
//                    actorMovieDataSource.open();
//                    actorMovieDataSource.createrepartoPelicula(repartoPeli);
//                    actorMovieDataSource.close();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    protected void cargarReparto() {
//        /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro estos campos en las películas*/
//        Actor actor;
//        InputStream file = null;
//        InputStreamReader reader = null;
//        BufferedReader bufferedReader = null;
//        try {
//            //file = getAssets().open("lista_peliculas_url_utf8.csv");
//            file = getAssets().open("reparto.csv");
//            reader = new InputStreamReader(file);
//            bufferedReader = new BufferedReader(reader);
//            bufferedReader.readLine();
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] data = line.split(";");
//                if (data != null && data.length >= 4) {
//                    actor = new Actor(Integer.parseInt(data[0]), data[1], data[2], data[3]);
//
//                    Log.d("cargarActores", actor.toString());
//
//                    ActorsDataSource actorsDataSource = new ActorsDataSource(this);
//                    actorsDataSource.open();
//                    actorsDataSource.createActor(actor);
//                    actorsDataSource.close();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    //Menu ajustes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(MainRecycler.this, SettingsActivity.class);
            startActivity(intentSettings);


        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Crear notificaciones
     */
    private void crearNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CANAL";
            String description = "DESCRIPCION CANAL";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("M_CH_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void ConstruirNotificacion(String titulo, String contenido) {
        crearNotificationChannel(); //Para la versión Oreo es necesario primero crear el canal
        //Instancia del servicio de notificaciones
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //construcción de la notificación
        mBuilder = new NotificationCompat.Builder(getApplicationContext(), "M_CH_ID");
        mBuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(titulo)
                .setContentText(contenido);

    }



    private class DownloadFilesTask extends AsyncTask<Void, Integer, String> {

        //Barra de progreso
        private ProgressDialog progressDialog;


        private float linesToRead = 0.0f;
        float numberOfReadLines = 0.0f;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(MainRecycler.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();

            linesToRead = (float) (lineasFichero("peliculas.csv"));
            linesToRead = (float) (linesToRead + lineasFichero("peliculas-reparto.csv"));
            linesToRead = (float) (linesToRead + lineasFichero("reparto.csv"));
        }


        protected void onProgressUpdate(Integer... progress) {
            progressDialog.setProgress(progress[0]);

        }

        //Método principal que se ejecutará en segundo plano.
        //El Void se corresponde al parámetro indicado en el encabezado de la clase.
        @Override
        protected String doInBackground(Void... voids) {
            //El mensaje que vamos a mostrar como notificación
            String mensaje = "";
            try {
                //Cargamos la base de datos.
                cargarPeliculas();
                cargarReparto();
                cargarRepartoPelicula();
                //Si la carga no da ningún error inesperado...
                mensaje = "Lista de películas actualizada";
            } catch (Exception e) {
                //Si la carga da algún error
                mensaje = "Error en la actualización de la lista de películas";
            }
            //Lanzamos notificación.
            mNotificationManager.notify(001, mBuilder.build());
            return mensaje;
        }

        //Método que se ejecuta tras doInBackground.
        //El mensaje que recibe es el que devolvemos en la ejecución principal.
        protected void onPostExecute(String message) {
            //descartar el mensaje después de que la base de datos haya sido actualizada
            this.progressDialog.dismiss();
            //Avisamos que la base de datos se cargó satisfactoriamente (o hubo error, según lo que haya ocurrido)
            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            //Y cargamos el recyclerview por primera vez.
            //Este método ya no tiene sentido llamarlo desde el onCreate u onResume, pues necesitamos asegurarnos
            //de haber cargado la base de datos antes de lanzarlo.
            cargarView();
        }


        protected int lineasFichero(String nombreFichero) {
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            int lineas = 0;
            try {
                file = getAssets().open(nombreFichero);
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);
                //Pase rápido mirando el total de líneas,
                // sin perder tiempo de procesamiento en nada más
                //Necesario para el progressbar
                while (bufferedReader.readLine() != null)
                    lineas++;
                bufferedReader.close();
            } catch (Exception e) {
            }
            ;
            return lineas;
        }

        protected void cargarPeliculas() {
            /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro estos campos en las películas*/
            Pelicula peli;
            listPelicula = new ArrayList<Pelicula>();
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                //file = getAssets().open("lista_peliculas_url_utf8.csv");
                file = getAssets().open("peliculas.csv");
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);
                bufferedReader.readLine();

                numberOfReadLines++;
                publishProgress((int)(numberOfReadLines/linesToRead)*100);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data != null && data.length >= 5) {

                        peli = new Pelicula(Integer.parseInt(data[0]), data[1], data[2], new Categoria(data[3], ""), data[4], data[5], data[6], data[7], data[8]);

                        Log.d("cargarPeliculas", peli.toString());
                        listPelicula.add(peli);

                        MovieDataSource movieDataSource = new MovieDataSource(getApplicationContext());
                        movieDataSource.open();
                        movieDataSource.createpelicula(peli);
                        movieDataSource.close();

                        numberOfReadLines++;
                        publishProgress((int)(numberOfReadLines/linesToRead)*100);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        protected void cargarRepartoPelicula() {
            /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro estos campos en las películas*/
            RepartoPelicula repartoPeli;
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                //file = getAssets().open("lista_peliculas_url_utf8.csv");
                file = getAssets().open("peliculas-reparto.csv");
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);
                bufferedReader.readLine();

                numberOfReadLines++;
                publishProgress((int)(numberOfReadLines/linesToRead)*100);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data != null && data.length >= 3) {

                        repartoPeli = new RepartoPelicula(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]);

                        Log.d("cargarRepartoPeliculas", repartoPeli.toString());

                        ActorMovieDataSource actorMovieDataSource = new ActorMovieDataSource(getApplicationContext());
                        actorMovieDataSource.open();
                        actorMovieDataSource.createrepartoPelicula(repartoPeli);
                        actorMovieDataSource.close();

                        numberOfReadLines++;
                        publishProgress((int)(numberOfReadLines/linesToRead)*100);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        protected void cargarReparto() {
            /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro estos campos en las películas*/
            Actor actor;
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                //file = getAssets().open("lista_peliculas_url_utf8.csv");
                file = getAssets().open("reparto.csv");
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);
                bufferedReader.readLine();

                numberOfReadLines++;
                publishProgress((int)(numberOfReadLines/linesToRead)*100);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data != null && data.length >= 4) {
                        actor = new Actor(Integer.parseInt(data[0]), data[1], data[2], data[3]);

                        Log.d("cargarActores", actor.toString());

                        ActorsDataSource actorsDataSource = new ActorsDataSource(getApplicationContext());
                        actorsDataSource.open();
                        actorsDataSource.createActor(actor);
                        actorsDataSource.close();

                        numberOfReadLines++;
                        publishProgress((int)(numberOfReadLines/linesToRead)*100);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


}
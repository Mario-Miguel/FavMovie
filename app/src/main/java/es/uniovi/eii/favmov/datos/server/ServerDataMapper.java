package es.uniovi.eii.favmov.datos.server;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmov.datos.server.credits.Cast;
import es.uniovi.eii.favmov.datos.server.credits.Credits;
import es.uniovi.eii.favmov.datos.server.moviedetails.MovieDetail;
import es.uniovi.eii.favmov.datos.server.movielist.MovieData;
import es.uniovi.eii.favmov.model.Actor;
import es.uniovi.eii.favmov.model.Categoria;
import es.uniovi.eii.favmov.model.Pelicula;


public class ServerDataMapper {
    private static final String BASE_URL_IMG= "https://image.tmdb.org/t/p/";
    private static final String IMG_W342= "w342";
    private static final String IMG_ORIGINAL= "original";
    public static final String SITE_YOUTUBE="youtube";
    public static final String BASE_URL_YOUTUBE="https://www.youtube.com/watch?v=";

    /**
     * Convierte datos de la API de cada película a datos del dominio
     * Pelicula     <--     MovieData
     *
     * int id;              <-- Integer id
     * String titulo;       <-- title
     * String argumento;    <-- overview
     * Categoria categoria; No lo rellenamos ya que sólo aparece id
     * String duracion;     No tenemos este dato
     * String fecha;        <-- releaseDate (yyyy-mm-dd)
     *
     * String urlCaratula;  <-- posterPath, hay que completar la url
     * String urlFondo;     <-- backdropPath
     * String urlTrailer;   No tenemos este dato
     *
     * @param movieData
     * @return
     */
    public static List<Pelicula> convertMovieListToDomain(List<MovieData> movieData) {
        ArrayList<Pelicula> lpeliculas= new ArrayList<>();

        for (MovieData peliApi: movieData) {
            String urlCaratula;
            String urlFondo;

            if (peliApi.getPosterPath()==null) {
                urlCaratula= "";
            } else {
                urlCaratula= peliApi.getPosterPath();
            }
            if (peliApi.getBackdropPath()==null) {
                urlFondo= "";
            } else {
                urlFondo = peliApi.getBackdropPath();
            }

            lpeliculas.add(new Pelicula(peliApi.getId(),
                    peliApi.getTitle(),
                    peliApi.getOverview(),
                    new Categoria("",""),
                    "",
                    peliApi.getReleaseDate(),
                    urlCaratula,
                    urlFondo,
                    ""
            ));
        }

        return lpeliculas;
    }

    public static void convertMovieDetailToDomain(MovieDetail data, Pelicula pelicula){
        if(data.getGenres().size()>0){
            pelicula.setCategoria(new Categoria(data.getGenres().get(0).getName(), ""));
        }

        int duracion = data.getRuntime();

        String duracionCad =(data.getRuntime()/60>0?(data.getRuntime()/60+"h"):"")+data.getRuntime()%60+"m";

        pelicula.setDuracion(duracionCad);

        String youtubeUrl = "";

        if(data.getVideos().getResults().size()>0){
            if(data.getVideos().getResults().get(0).getSite().toLowerCase().equals(SITE_YOUTUBE)){
                youtubeUrl = BASE_URL_YOUTUBE + data.getVideos().getResults().get(0).getKey();
            }
        }
        pelicula.setUrlTrailer(youtubeUrl);

    }


    public static List<Actor> convertCreditsToDomain(Credits data){
        List<Actor> listActor = new ArrayList<>();


        for(Cast actor: data.getCast()){
            Actor actorToAdd=new Actor();
            actorToAdd.setNombre(actor.getName());
            actorToAdd.setNombrePersonaje(actor.getCharacter());
            if(actor.getProfilePath()!=null)
                actorToAdd.setUrlImagenActor(BASE_URL_IMG +IMG_ORIGINAL+ actor.getProfilePath().toString());
//            actorToAdd.setUrlIMDB();
            listActor.add(actorToAdd);
        }

        return listActor;
    }
}

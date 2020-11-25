package es.uniovi.eii.favmov.remote;

import es.uniovi.eii.favmov.datos.server.credits.Credits;
import es.uniovi.eii.favmov.datos.server.moviedetails.MovieDetail;
import es.uniovi.eii.favmov.datos.server.movielist.MovieListResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBApi {

    String BASE_URL="https://api.themoviedb.org/3/";

    //Petición de lista de películas populares
    //https://api.themoviedb.org/3/movie/popular?api_key=1ac424562ae19f956fa25dbccda9bd75&language=es-ES&page=1
    @GET("movie/{lista}")
    Call<MovieListResult> getMoviesList(
            @Path("lista") String lista,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    //Detalles de una pelicula
    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("append_to_response") String videos
    );

    //Actores de una pelicula
    @GET("movie/{movie_id}/credits")
    Call<Credits> getMovieCredits(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

}

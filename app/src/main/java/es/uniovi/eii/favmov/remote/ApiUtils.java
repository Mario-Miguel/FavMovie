package es.uniovi.eii.favmov.remote;

import retrofit2.Retrofit;

public class ApiUtils {
    public static final String LANGUAGE = "es-ES";
    public static final String API_KEY = "1ac424562ae19f956fa25dbccda9bd75";

    public static TheMovieDBApi createTheMovieDBApi(){
        Retrofit retrofit = RetrofitClient.getRertofitClient(TheMovieDBApi.BASE_URL);

        return retrofit.create(TheMovieDBApi.class);
    }
}

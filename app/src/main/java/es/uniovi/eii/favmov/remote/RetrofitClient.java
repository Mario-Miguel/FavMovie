package es.uniovi.eii.favmov.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;


    private RetrofitClient(){}

    public static Retrofit getRertofitClient(String baseUrl){
        if(retrofit==null){
            //Crear el interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            //Establecemos el nivel
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            //Añadir el interceptor
            httpClient.addInterceptor(logging);

            retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
        }

        return retrofit;
    }


}

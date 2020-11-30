package net.learn2develop.moviedata_finaltask10.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * U okviru cetvrtog koraka (takodje u paketu Net) potrebno je da definisemo retrofit instancu preko koje ce komunikacija ici. Retrofit nije sastavni
 * deo Androida, i zato moramo da uvezemo biblioteku. On nam osigurava da nas zahtev bude pozvan! ApiService nam je objekat
 * nad kojim cemo raditi pozive (metode)!
 * */
public class RESTService {
    public static final String BASE_URL = "https://www.omdbapi.com";
    public static final String API_KEY = "c1b22ec7";

    public static Retrofit getRetrofitInstance(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static OMDBApiEndpointInterface apiInterface(){
        OMDBApiEndpointInterface apiService = getRetrofitInstance().create(OMDBApiEndpointInterface.class);

        return apiService;
    }
}

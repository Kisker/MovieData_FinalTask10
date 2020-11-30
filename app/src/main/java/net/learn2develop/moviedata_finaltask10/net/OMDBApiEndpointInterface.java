package net.learn2develop.moviedata_finaltask10.net;

//Drugi korak posle Modela jeste kreiranje paketa Net i unutar njega podpaket WebServices interface, gde kreiramo nase Resurse - komunikaciju izmedju clienta i Web Service-a
//Nad EndPoint-om zahtevamo sta zelimo da uradimo nad tim WS; gadjamo odredjenu informaciju. Resursi nam u ovom slucaju predstavljaju
//tabelu u bazi, gde nas zanima nesto specificno kroz samo filtriranje
//@GET nam predstavlja zahtev; adresa weba; EndPoint - su nam resursi - a u ovom primeru ih nemamo, nego samo filtriranje.
//Filtriranje uvek pocinje sa ? upitom za WS i njenim krajnjim upitom (@QueryMap)
//Da bismo na ovom primeru imali resurse, uvek ispred ? bi trebalo da stoji, kao na primer:
//filmovi?apiley=...!!!

import net.learn2develop.moviedata_finaltask10.model.Movie;
import net.learn2develop.moviedata_finaltask10.model.Result;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface OMDBApiEndpointInterface {

    @GET("/")
    Call<Result> getMoviesByTitle(@QueryMap Map<String, String> options);

    @GET("/")
    Call<Movie> getMoviesDetails(@QueryMap Map<String, String> options);


}

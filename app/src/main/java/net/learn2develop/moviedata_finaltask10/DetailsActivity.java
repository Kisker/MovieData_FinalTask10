package net.learn2develop.moviedata_finaltask10;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import net.learn2develop.moviedata_finaltask10.model.Movie;
import net.learn2develop.moviedata_finaltask10.net.RESTService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Sedmi korak, DetailsActivity iliti sta koje features zelimo vidjeti ispisane u drugom prozoru.
public class DetailsActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private Movie movie;
  //1.
    private void getDetail(String imdbKey) {
        HashMap<String, String> query = new HashMap<>();
        //Bez ovoga mi ne bi otvorio drugi prozor sa detaljima jednog filma
        query.put("apikey", RESTService.API_KEY);
        query.put("i", imdbKey);

        Call<Movie> call = RESTService.apiInterface().getMoviesDetails(query);
        call.enqueue(new Callback<Movie>(){

            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200){
                    Movie resp = response.body();
                    if(resp != null){
                        TextView title = DetailsActivity.this.findViewById(R.id.dtitle);
                        title.setText(resp.getTitle());

                        TextView year = DetailsActivity.this.findViewById(R.id.dyear);
                        year.setText(resp.getYear());

                        TextView genre = DetailsActivity.this.findViewById(R.id.dgenre);
                        genre.setText(resp.getGenre());

                        TextView plot = DetailsActivity.this.findViewById(R.id.dplot);
                        plot.setText(resp.getPlot());

                        TextView rated = DetailsActivity.this.findViewById(R.id.drated);
                        rated.setText(resp.getRated());

                        ratingBar = DetailsActivity.this.findViewById(R.id.rating);
                        ratingBar.setRating(resp.getRating());

                        ImageView poster = DetailsActivity.this.findViewById(R.id.dposter);
                        Picasso.get().load(resp.getPoster()).into(poster);
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Pozivom na getDetail metodu, odnosno na imdbKey koji nam znaci kljuc naseg pretrazivanja - ispis filma
        //u drugom prozoru, moramo pre toga kreirati konstantu KEY (ili kako god to zelimo nazvati) u Main-u
        String imdbKey = getIntent().getStringExtra(MainActivity.KEY);
        getDetail(imdbKey);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
    }
}

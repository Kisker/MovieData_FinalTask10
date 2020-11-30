package net.learn2develop.moviedata_finaltask10.adapters;

//Peti korak jeste svakako kreiranje paketa Adapters i unutar njega SearchAdapter.
// Pre nego sto krenemo da radimo, moramo kreirati search_item.xml
//Ova klasa uvek extenduje RecyclerView.Adapter koja implementira RecyclerView.
// Uz pomoc ovoga mi cemo, kada ukucamo i kliknemo na Search dugme
//ispisati sve trazene filmove u RecyclerView modulu!

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.learn2develop.moviedata_finaltask10.R;
import net.learn2develop.moviedata_finaltask10.model.Search;

import java.util.ArrayList;
import java.util.List;

//1. Pre nego sto pozovemo RecyclerView.Adapter, moramo kreirati klasu MyViewHolder
//i tek onda implementirati metode!
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>  {

    private List<Search> movies;

    public SearchAdapter() {
        this.movies = new ArrayList<>();
    }
    //3. Pravimo metodu addItems, sa kojom cemo povezati sa nasom sledecom klasom RecyclerTouchListener
    //Ta klasa se posle poziva unutar Main Activity-ja. Sama klasa RecyclerTouchListener nam omogucava da
    //preko implementiranih interface-a poziva otvaranje drugog prozora sa detaljnim opisom izabranog filma
    public void addItems(List<Search> data) {
        movies.clear();
        movies.addAll(data);
        notifyDataSetChanged();

    }
    //4. Bez metode onCreateViewHolder i poziva na izvrsavanje da se otvori prozor search_item.xml,
    //nas app bi pukao.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Search movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.get().load(movies.get(position).getPoster()).into(holder.poster);
        holder.year.setText(movie.getYear());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    //5. Kreiranjem i pozivanjem metode Search, mi se ustvari pozivamo na listu Search, koja ce nam
    //omoguciti da otvorimo drugi prozor sa detaljnim opisom filma.
    //get - se moram takodje pozvati unutar kreiranog interface-a onClick u okviru Main-a!
    public Search get(int position) {
        return movies.get(position);
    }


  //2. Kreiramo klasu MyViewHolder, koja nam omogucava ispis naseg upita!
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year;
        public ImageView poster;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            poster = (ImageView) view.findViewById(R.id.poster);
            year = (TextView) view.findViewById(R.id.year);
        }
    }
}

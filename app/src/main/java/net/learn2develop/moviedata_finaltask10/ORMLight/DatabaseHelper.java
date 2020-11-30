package net.learn2develop.moviedata_finaltask10.ORMLight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import net.learn2develop.moviedata_finaltask10.model.Movie;

import java.sql.SQLException;

//Treci korak jeste kreiranje i sam poziv ka dopunama/izmenama tabele.
//DatabaseHelper nasledjuje ORMLiteSQLOpenHelper, koja u sebi sadrzi context nase baze,
//ime tabele, ogranicenja i version tabele od koje krece (obicno je to 0 ili 1). Uvek ide ovako!

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    //Dajemo ime bazi
    private static final String DATABASE_NAME = "moviedata.db";
    //i pocetnu verziju baze. Obicno krece od 1
    private static final int DATABASE_VERSION = 1;
    //Data Access Object je poseban, genericki interface za koju tabelu se koristi.
    //Movie je u ovom primeru tip entiteta koji odgovara tabeli na koju se pozivamo, a String primarni kljuc te klase/tabele.
    private Dao<Movie, String> movieDao = null;
    //Potrebno je dodati konstruktor zbog pravilne inicijalizacije biblioteke
    //Izbrisemo automatski ubaceno String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion
    //Dodamo:
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Prilikom kreiranja baze potrebno je da pozovemo odgovarajuce metode biblioteke.
    //onCreate metoda oznacava ako postoji baza, on nista ne instancira, a ako ne kreira novu tabelu.
    //prilikom kreiranja moramo pozvati TableUtils.createTable za svaku tabelu koju imamo
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Movie.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //onUpgrade metoda oznacava upgrade-ovanje tabele sa izmenjenim i unetim novim vrednostima.
    //kada zelimo da izmenomo tabele, moramo pozvati TableUtils.dropTable za sve tabele koje imamo
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Movie.class, true);
            TableUtils.createTable(connectionSource, Movie.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //jedan Dao objekat sa kojim komuniciramo. Ukoliko imamo vise tabela
    //potrebno je napraviti Dao objekat za svaku tabelu. U ovom primeru se pozivamo na Movie.class
    //kako bi nam u drugom prozoru izbacio celokupan opis jednog filma

    public Dao<Movie, String> getMovieDao() throws SQLException {
        if (movieDao == null) {
            movieDao = getDao(Movie.class);
        }

        return movieDao;
    }
    //obavezno prilikom zatvarnja rada sa bazom osloboditi resurse. I uvek ovako ide.
    @Override
    public void close() {
        movieDao = null;

        super.close();
    }
}

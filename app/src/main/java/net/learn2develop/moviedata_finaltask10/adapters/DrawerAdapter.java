package net.learn2develop.moviedata_finaltask10.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.learn2develop.moviedata_finaltask10.R;
import net.learn2develop.moviedata_finaltask10.navigation.NavigationItem;

import java.util.List;

//Deseti korak - posle kreiranja klase NavigationItem, kreiramo klasu DrawerListViewAdapter i pozovemo listu vrednosti iz prethodne klase
//Deveti i sedmi korak obvezno moramo pozvati/setirati u Main-u
public class DrawerAdapter extends BaseAdapter {
    private List<NavigationItem> items = null;
    private Activity activity;

    public DrawerAdapter(List<NavigationItem> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return 0;
    }
    //Umesto Object, stavimo NavigationItem, kako bi sa getItem dobili natrag poziciju items-a (icon, ime i podnaslov fijoke)
    @Override
    public NavigationItem getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //U okviru ove metode obvezno moramo pozvati sta i kako zelimo da vidimo u nasoj fijoci kada otvorimo. Bez ovoga, nasa fijoka
    //bi bila izlistana prazna bez naziva, opisa i slike (Home, Settings, etc.). Pre nego sto krenemo sa pozivima, moramo kreirati
    //drawer_items.xml
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.drawer_items, null);

        }

        ImageView imageIcon = view.findViewById(R.id.iconFijoka);
        TextView tvIme = view.findViewById(R.id.imeFijoke);
        TextView tvPodnaslov = view.findViewById(R.id.podnaslovFijoke);

        tvIme.setText(items.get(i).getImeFijoke());
        tvPodnaslov.setText(items.get(i).getPodnaslovFijoke());

        imageIcon.setImageResource(items.get(i).getIcon());

        return view;

    }
}

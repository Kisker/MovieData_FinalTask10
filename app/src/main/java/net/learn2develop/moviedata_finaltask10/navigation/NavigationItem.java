package net.learn2develop.moviedata_finaltask10.navigation;


import net.learn2develop.moviedata_finaltask10.R;

import java.util.ArrayList;

//Deveti korak - podesavanje klase NavigationItem sa vrednostima title, subtitle i icon, kako
//    //bi kreirali nasu fijoku (drawer), a potom to pozvali u Main-u.
public class NavigationItem {

    private String imeFijoke, podnaslovFijoke;
    private int icon;

    public NavigationItem(String imeFijoke, String podnaslovFijoke, int icon){
        this.imeFijoke = imeFijoke;
        this.podnaslovFijoke = podnaslovFijoke;
        this.icon = icon;
    }

    public String getImeFijoke() {
        return imeFijoke;
    }

    public void setImeFijoke(String imeFijoke) {
        this.imeFijoke = imeFijoke;
    }

    public String getPodnaslovFijoke() {
        return podnaslovFijoke;
    }

    public void setPodnaslovFijoke(String podnaslovFijoke) {
        this.podnaslovFijoke = podnaslovFijoke;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}

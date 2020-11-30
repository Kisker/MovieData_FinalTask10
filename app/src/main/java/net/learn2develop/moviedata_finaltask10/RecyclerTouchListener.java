package net.learn2develop.moviedata_finaltask10;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//Sesti korak, koji ujedno ide zajedno sa sedmim korakom - DetailsActivity - nam omogucava da njihovim pozivom
//dobijemo ispis jednog filma u drugom prozoru u RecyclerView-ju!
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
   //3. Kada implementiramo ClickListener
    private ClickListener clickListener;

   //4. RecyclerTouchListener se poziva na context, recyclerView i ClickListener, kako bi nam omogucila da sa pozivom na
    //da nam this.clickListener = clickListener vrati istu vrednost, odnosno da omoguci otvaranje drugog prozora!
    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.clickListener = clickListener;
        //A convenience class to extend when you only want to listen for a subset of all the gestures- GestureDetector.SimpleOnGestureListener
        //The OnGestureListener callback will notify users when a particular motion event has occurred.
        // This implements all methods in the OnGestureListener, OnDoubleTapListener,
        // and OnContextClickListener but does nothing and return false for all applicable methods.
        // Android provides special types of touch screen events such as pinch , double tap, scrolls , long presses and flinch. These are all known as gestures.
        // Android provides GestureDetector class to receive motion events and tell us that these events correspond to
        // gestures or not.
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            //Bez poziva na ove metode, nas drugi prozor ne bi bio otvoren sa detaljima odabranog filma
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //moramo staviti true, kako bi nam otvorio drugi prozor!
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

   //1. Automatski kada implementiramo  RecyclerView.OnItemTouchListener moramo pozvati sledece tri metode
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
       //5. Ovo obvezno ovako pozvati, jer nam radi u korelaciji sa onLongPress i sa this.clickListener = clickListener;
        //i bez toga ne bismo bili u mogucnosti da otvorimo drugi prozor!
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildLayoutPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    //2.Implementacija interface-a. onClick i onLongClick interface pozovemo i unutar Main-a.To obvezno!
        public interface ClickListener {
            void onClick(View view, int position);

            void onLongClick(View view, int position);
       }
}

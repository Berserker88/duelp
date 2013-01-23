package de.jasiflak.duelp;

import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;



public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    public boolean onTouch(final View v, final MotionEvent event) {
    	
    	return gestureDetector.onTouchEvent(event);
    }

    
    
//##############################  START INNER CLASS  ######################################
    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        
        @Override
        public boolean onDown(MotionEvent e) {
        	// rufe onDown der Superklasse auf, da dies nicht interessiert
        	return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        	// um Problemen vorzubeugen
        	if (e1 == null || e2 == null)
        		return false;

        	// berechne zur�ckgelegte Strecken
        	float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            
            // wenn horizontale bewegung erfolgte
            if (Math.abs(diffX) > Math.abs(diffY)) {
            	// wenn horizontal geswiped wurde
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                	// wenn nach rechts geswiped wurde
                    if (diffX > 0) {
                    	Log.i("gesture", "nach rechts geswiped");
                    	onSwipeRight();
                    } 
                    // wenn nach links geswiped wurde
                    else {
                    	Log.i("gesture", "nach links geswiped");
                    	onSwipeLeft();
                    }
                    return true;
                }
                // sonst rufe onFling der Superklasse auf, da das Event nicht interessiert
                else
                	return super.onFling(e1, e2, velocityX, velocityY);
            } 
            else {
            	// wenn vertical geswiped wurde
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                	// wenn nach unten geswiped wurde
                    if (diffY > 0) {
                    	Log.i("gesture", "nach unten geswiped");
                    	onSwipeBottom();
                    } 
                    // wenn nach oben geswiped wurde
                    else {
                    	Log.i("gesture", "nach oben geswiped");
                    	onSwipeTop();
                    }
                    return true;
                }
            	// sonst rufe onFling der Superklasse auf, da das Event nicht interessiert
                else
                	return super.onFling(e1, e2, velocityX, velocityY);
            }
        }
    }
//######################################  END INNER CLASS  ###################################

    // Methoden die bei Referenz implementiert werden müssen
    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}
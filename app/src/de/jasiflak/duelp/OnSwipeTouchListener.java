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

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 175;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        	if (e1 == null || e2 == null)
        		return false;
        	
            try {
            	Log.i("gesture", "-" + "FLING" + "-\n");
            	Log.i("gesture", "e1: ("+e1.getX()+"|"+e1.getY()+")\ne2: ("+e2.getX()+"|"+e2.getY()+")\nvelocityX: "+velocityX+"\nvelocityY: "+velocityY+"\n");
            	float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                Log.i("gesture", "diffX= "+diffX+"\n");
                Log.i("gesture", "diffY= "+diffY+"\n");
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                        	Log.i("gesture", "nach rechts geswiped");
                        	onSwipeRight();
                        } else {
                        	Log.i("gesture", "nach links geswiped");
                        	onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                        	Log.i("gesture", "nach unten geswiped");
                        } else {
                        	Log.i("gesture", "nach oben geswiped");
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}
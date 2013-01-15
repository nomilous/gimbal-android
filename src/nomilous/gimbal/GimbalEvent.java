package nomilous.gimbal;

public class GimbalEvent {

    public static class Touch {

        public String toString() {

            return "touch";
        
        }

    }

    public static interface Listener {

        public abstract void onTouchEvent( GimbalEvent.Touch event );

    }

}

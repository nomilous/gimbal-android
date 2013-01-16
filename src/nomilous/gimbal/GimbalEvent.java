package nomilous.gimbal;

import android.view.MotionEvent;

import android.content.Context;
import java.util.Hashtable;
import java.util.Enumeration;

public class GimbalEvent {

    public static final int TOUCH = 1;

    public static interface Event {

        public abstract int type();
    
    } 

    public static class Touch implements Event {

        public final int type() { return TOUCH; }


        public Touch( MotionEvent event ) {

            //
            // process touch event
            //    

        }

        public String toString() {

            return "touch";

        }

    }

    public static interface Subscriber {

        public abstract void onTouchEvent( GimbalEvent.Touch event );

    }


    public static class Publisher {

        private Integer beetlejuice = 0;

        private Hashtable<Integer,Subscriber> subscribers = 
            new Hashtable<Integer,Subscriber>();

        public synchronized Integer subscribe( Subscriber subscriber ) {

            Integer id = beetlejuice++;
            subscribers.put(id, subscriber);
            return id;

        }

        public synchronized void publish( Event event ) {
               // 
               // potential bottleneck 
               //

            Enumeration<Integer> e = subscribers.keys();

            switch( event.type() ) {

                case TOUCH:
                
                    while( e.hasMoreElements() ) (

                        (Subscriber) subscribers.get( e.nextElement() )

                    ).onTouchEvent( (GimbalEvent.Touch) event );

                    break;

            }

        } 

    }

    public static class Server {

        private Context context;
        private Publisher publisher;
        protected boolean active = false;

        public Server(Context context, Publisher publisher) {

            this.publisher = publisher;
            this.context = context;

        }

        public void publish( Event event ) {
            publisher.publish( event );
        }

        public void startServer() {
            active = true;
        }

        public void stopServer() {
            active = false;
        }

        public boolean running() {
            return active;
        }

    }

}

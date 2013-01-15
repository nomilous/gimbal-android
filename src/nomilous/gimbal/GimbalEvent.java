package nomilous.gimbal;

import android.content.Context;
import java.util.Hashtable;

public class GimbalEvent {

    public static class Touch {

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

    }

    public static class Server {

        private Context context;
        private Publisher publisher;
        protected boolean active = false;

        public Server(Context context, Publisher publisher) {

            this.publisher = publisher;
            this.context = context;

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

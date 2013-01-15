package nomilous.gimbal;

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

        private Integer idSequence = 0;

        private Hashtable<Integer,Subscriber> subscribers = 
            new Hashtable<Integer,Subscriber>();

        public synchronized Integer subscribe(Subscriber subscriber) {

            Integer id = idSequence++;
            subscribers.put(id, subscriber);
            return id;

        }

    }

}

package nomilous.gimbal;

import android.view.MotionEvent;

import android.content.Context;
import java.util.Hashtable;
import java.util.Enumeration;

public class GimbalEvent {

    public static final int TOUCH = 1;

    public static interface Event {

        public abstract int type();
        public abstract int action();
    
    } 

    public static class Touch implements Event {

        public static class Action {
            //
            // TODO: look into enum? or struct? for java
            //

            public static final int PRESS   = 1;
            public static final int RELEASE = 2;
            public static final int MOVE    = 3;

        }

        private int        action;
        private Position[] pointers;
        private int        pointerCount;

        public final int        type()         { return TOUCH; }
        public final int        action()       { return action; }
        public final int        pointerCount() { return pointerCount; }
        public final Position   pointer()      { return pointers[0]; }
        public final Position   pointer(int i) { return pointers[i]; }
        public final Position[] pointers()     { return pointers; }

        public Touch( MotionEvent event ) {

            //
            // process touch event
            //
            // TODO:
            //
            // - expand to provide pressure  
            // 

            pointerCount = event.getPointerCount();
            pointers = new Position[pointerCount];

            for(int i = 0; i < pointerCount; i++ ) {

                pointers[i] = new Position( event.getX(i), event.getY(i) );

            }

            switch( event.getAction() & MotionEvent.ACTION_MASK ) {

                case MotionEvent.ACTION_DOWN: 
                    action = Action.PRESS;
                    break;

                case MotionEvent.ACTION_UP:
                    action = Action.RELEASE;
                    break;

                case MotionEvent.ACTION_MOVE:
                    action = Action.MOVE;
                    break;

            } 

        }

        public String toString() {

            return String.format( 

                "GimbalEvent.Touch action=%d, count=%d, x=%f, y=%f",
                action,
                pointers.length,
                pointers[0].floatX(),
                pointers[0].floatY()
            );

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

        protected Context context;
        protected Publisher publisher;
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

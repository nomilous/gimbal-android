package nomilous.gimbal.viewport;

import nomilous.Util;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.*;


import android.content.Context;
import org.json.JSONArray;    // grrrrr.
import com.google.gson.Gson;


public class GimbalViewport {

    public static class Viewport {

        private String id;
        private boolean primary = false;

        public Viewport(String id) {
            this.id = id;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }

        public String getId() {
            return id;
        }

        public String toString() {
            return String.format(
                "GimbalViewport.Viewport id:%s, primary:%s", 
                id,
                primary
            );
        }

    }



    public static interface EventHandler {

        public abstract void onViewportRegistered(Viewport viewport);
        public abstract void onViewportReleased(Viewport viewport);

    }


    public static class Controller extends Uplink 

        implements GimbalEvent.Subscriber {

        public static class Config {

            public String pending;

        }

        private Gson gson;
        private EventHandler eventHandler;

        public Controller(Context context,  GimbalEvent.Publisher publisher, EventHandler eventHandler) {
            super(context, publisher);
            this.eventHandler = eventHandler;
            gson = new Gson();
            publisher.subscribe(this);
        }

        @Override
        public void onTouchEvent( GimbalEvent.Touch event ) {

            String json = gson.toJson(event);
            Util.debug( "Pending send to server... " + json );

            if( running() )
                client.send("mooo");

        }


        @Override
        public void onStartClient(Object... payload) {


        }

        @Override
        public void onRegisterController( RegisterControllerOkPayload payload ) {

            eventHandler.onViewportRegistered( payload.viewport );

        }

        @Override
        public void onReleaseController( ReleaseControllerOkPayload payload ) {

            for( int i = 0; i < payload.viewports.length; i++ )

                eventHandler.onViewportReleased( payload.viewports[i] );

        }

    }

}

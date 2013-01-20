package nomilous.gimbal.viewport;

import nomilous.Util;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.Uplink;

import android.content.Context;
import org.json.JSONArray;

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

    }



    public static interface EventHandler {

        public abstract void onViewportRegistered(Viewport viewport);
        public abstract void onViewportReleased(Viewport viewport);

    }


    public static class Controller extends Uplink {

        public static class Config {

            public String pending;

        }

        private EventHandler eventHandler;

        public Controller(Context context,  GimbalEvent.Publisher publisher, EventHandler eventHandler) {
            super(context, publisher);
            this.eventHandler = eventHandler;
        }



        @Override
        public void onStartClient(JSONArray payload) {


        }

        @Override
        public void onRegisterController(JSONArray payload) {


        }

        @Override
        public void onReleaseController(JSONArray payload) {


        }

    }

}

package nomilous.gimbal.viewports;

import nomilous.Util;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.Uplink;

import android.content.Context;

public class GimbalViewport {

    public static interface EventHandler {

        public abstract void onViewportRegistered(String viewportID);
        public abstract void onViewportReleased(String viewportID);

    }

    public static class Controller extends Uplink {

        public Controller(Context context,  GimbalEvent.Publisher publisher) {
            super(context, publisher);
        }

    }

}

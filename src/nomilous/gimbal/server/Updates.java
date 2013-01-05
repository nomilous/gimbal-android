package nomilous.gimbal.server;

import nomilous.gimbal.server.Messenger;
import nomilous.gimbal.client.Subscriber;

import android.content.Context;

public class Updates {

    public static final int NOOP                  = 0;
    public static final int GPS_LOCATION_UPDATE   = 1;
    public static final int ACCELERATION_UPDATE   = 2;
    public static final int MAGNETIC_FIELD_UPDATE = 4;
    public static final int ROTATION_UPDATE       = 8;
    public static final int HIGHEST_EVENT_CODE    = 9;

    public static void subscribe( Context appContext, int event, Subscriber newSubscriber ) {

        m.subscribe( appContext, event, newSubscriber );

    }

    private static Messenger m = new Messenger();

}

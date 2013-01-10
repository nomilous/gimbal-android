package nomilous.gimbal.client;

import nomilous.Util;

public class UI implements SensorSubscriber {

    public void onSensorEvent( int eventCode, Object payload ) {

        Util.debug( String.format (

            "SENSOR: eventCode: %s, payload: %s ",
            eventCode,
            payload.toString()

        ));

    }

}

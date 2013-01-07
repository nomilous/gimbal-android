package nomilous.gimbal.client;

public interface SensorSubscriber {

    public static final int GPS_LOCATION_UPDATE   =  1;
    public static final int ACCELERATION_UPDATE   =  2;
    public static final int MAGNETIC_FIELD_UPDATE =  4;
    public static final int ROTATION_UPDATE       =  8;
    public static final int TOUCH_EVENT           = 16;

    public abstract void onSensorEvent( int eventCode, Object payload );

}

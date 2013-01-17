package nomilous.gimbal.server.sensor;

import nomilous.Util;
import nomilous.gimbal.client.SensorSubscriber;

import java.util.List;
import android.content.Context;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

public class OrientationServer  {

    private SensorSubscriber subscriber;
    private Context appContext;
    private boolean active = false;

    public OrientationServer( Context appContext, Object subscriber ) {

        this.subscriber = (SensorSubscriber) subscriber;
        this.appContext = appContext;

    }

    public void startServer() {

        sensorManager = (SensorManager) appContext.getSystemService( 

            Context.SENSOR_SERVICE 

        );

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);


        for( Sensor sensor : sensorList ) {

            Util.info( "Found Sensor: " + sensor.getName() );

        }

        sensorManager.registerListener( listener,

            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL

        );

        sensorManager.registerListener( listener,

            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME

        );

        active = true;

    }

    public void stopServer() {
   
        if( !active ) return;

        sensorManager = (SensorManager) appContext.getSystemService( Context.SENSOR_SERVICE );
        sensorManager.unregisterListener(listener);

        active = false;

    }


    private SensorManager sensorManager = null;

    private float[] accelerationVector    = new float[3];
    private float[] magneticFieldVector   = new float[3];

    private static float[] rotationMatrix        = new float[9];
    private static float[] remapedRotationMatrix = new float[9];
    private static float[] orientationVector     = new float[3];

    private void sensorUpdate( SensorEvent event ) {

        switch( event.sensor.getType() ) {

            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy( event.values, 0, accelerationVector, 0, 3 );
                //this.subscriber.onSensorEvent( SensorSubscriber.ACCELERATION_UPDATE, accelerationVector);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, magneticFieldVector, 0, 3);
                //this.subscriber.onSensorEvent( SensorSubscriber.MAGNETIC_FIELD_UPDATE, magneticFieldVector);
                break;

            default:
                return;

        }

        //
        // Got either ACCELEROMETER or MAGNETIC FIELD update
        // call to recalculate orientation
        //

        updateRotation();

    }


    private void updateRotation() {

        if( SensorManager.getRotationMatrix( 

            rotationMatrix, null, accelerationVector, magneticFieldVector 

        )) {

            SensorManager.remapCoordinateSystem(

                rotationMatrix,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z, 
                remapedRotationMatrix

            );

            SensorManager.getOrientation(

                remapedRotationMatrix, 
                orientationVector

            );

            //this.subscriber.onSensorEvent( SensorSubscriber.ROTATION_UPDATE, orientationVector);

        }

    }


    private final SensorEventListener listener = new SensorEventListener() {

        @Override
        public final void onSensorChanged( SensorEvent event ) {

            sensorUpdate( event );

        }

        @Override
        public final void onAccuracyChanged( Sensor sensor, int accuracy ) {

            //
            // TODO: Handle changed accuracy
            //

        }

    };

}

// pending delete
package nomilous.gimbal.client;

import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONObject;
import com.codebutler.android_websockets.SocketIOClient;

import android.view.MotionEvent;

public class SensorTranslator {


    // public final int TILT_MODE = 1;

    // // 
    // // TILT_MODE
    // // 
    // // - A calibration sequence sets up a zeropoint vector with surrounding deadzone.
    // // 
    // // - Orrientation updates sent to the server are arc deltas from the zeropoint 
    // //   on the pitch/yaw/roll axii, and are only sent if they arc beyond the deadzone.
    // //

    // private SocketIOClient client;

    // public SensorTranslator() {}

    // public void configure( SocketIOClient client, JSONArray config ) {

    //     this.client = client;

    //     //
    //     // TODO: Configure mode
    //     // TODO: Initiate calibration sequence
    //     //

    // }

    // public void handleSensorEvent( int eventCode, Object payload ) {

    //     if( client == null ) return;

    //     //
    //     // TODO: A huge big pile of trig!
    //     // 
    //     // (unless android provides... )
    //     //

    //     switch( eventCode ) {

    //         case SensorSubscriber.ROTATION_UPDATE:

    //             try {

    //                 JSONArray xyz = new JSONArray();
    //                 xyz.put( ((float[])payload)[0] );
    //                 xyz.put( ((float[])payload)[1] );
    //                 xyz.put( ((float[])payload)[2] );

    //                 JSONObject orientation = new JSONObject();
    //                 orientation.put( "event:orient", xyz );

    //                 JSONArray message = new JSONArray();
    //                 message.put( orientation );

    //                 client.emit( GimbalEventHandler.UPDATE_VIEWPORTS, message );

    //             } 

    //             catch( Exception x ) {} 
    //             break;

    //         case SensorSubscriber.TOUCH_EVENT:

    //             // REPEATED1

    //             try {

    //                 MotionEvent m = (MotionEvent)payload;

    //                 JSONObject event = new JSONObject();

    //                 int action = m.getAction() & MotionEvent.ACTION_MASK;

    //                 if( action == MotionEvent.ACTION_DOWN || 
    //                     action == MotionEvent.ACTION_MOVE || 
    //                     action == MotionEvent.ACTION_UP) {

    //                     event.put( "action", action );

    //                     //
    //                     // for each fingertip
    //                     //

    //                     int pointerCount = m.getPointerCount();
    //                     JSONObject pointers = new JSONObject();
    //                     event.put( "pointers", pointers );

    //                     for( int i = 0; i < pointerCount; i++ ) {

    //                         JSONObject pointer = new JSONObject();
                            
    //                         pointer.put( "x", m.getX(i) );
    //                         pointer.put( "y", m.getY(i) );
    //                         pointer.put( "p", m.getPressure(i) );

    //                         int pointerID = m.getPointerId(i);
    //                         pointers.put( "" + pointerID, pointer );

    //                     }

    //                     JSONObject touch = new JSONObject();
    //                     touch.put( "event:touch", event );

    //                     JSONArray message = new JSONArray();
    //                     message.put( touch );

    //                     client.emit( GimbalEventHandler.UPDATE_VIEWPORTS, message );

    //                 }

    //             }

    //             catch( Exception x ) {} 
    //             break;


    //         case SensorSubscriber.KEYPAD_EVENT:

    //             try {

    //                 Bundle keypadEvent = (Bundle)payload;

    //                 JSONObject change = new JSONObject();
    //                 change.put( "text",  keypadEvent.getCharSequence("text").toString() );
    //                 change.put( "start",  keypadEvent.getInt("start") );
    //                 change.put( "before",  keypadEvent.getInt("before") );
    //                 change.put( "count",  keypadEvent.getInt("count") );

    //                 JSONObject event = new JSONObject();
    //                 event.put( "event:keypad", change );

    //                 JSONArray message = new JSONArray();
    //                 message.put( event );

    //                 client.emit( GimbalEventHandler.UPDATE_VIEWPORTS, message);

    //             }

    //             catch( Exception x ) {} 
    //             break;


    //     }

    // }

}

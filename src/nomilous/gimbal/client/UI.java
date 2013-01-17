//pending delete
package nomilous.gimbal.client;

// import nomilous.Util;
// import nomilous.gimbal.MenuActionSet;
// import nomilous.gimbal.Touchable;
// import nomilous.gimbal.Touchable.PointerEvent;
// import nomilous.gimbal.Position;

// import android.view.MotionEvent;
// import java.util.ArrayList;

public class UI implements SensorSubscriber {

    // private ArrayList<MenuActionSet> menus = new ArrayList<MenuActionSet>();

    // public void add( MenuActionSet menuActionSet ) {

    //     menus.add( menuActionSet );

    // }

    // public void onSensorEvent( int eventCode, Object payload ) {

    //     switch( eventCode ) {

    //         case SensorSubscriber.TOUCH_EVENT:

    //             handleTouchEvent( (MotionEvent) payload );
    //             return;

    //     }

    //     Util.debug( String.format (

    //         "SENSOR: eventCode: %s, payload: %s ",
    //         eventCode,
    //         payload.toString()

    //     ));

    // }


    // private void handleTouchEvent( MotionEvent m ) {

    //     int action = m.getAction() & MotionEvent.ACTION_MASK;
    //     int translated;

    //     switch( action ) {

    //         case MotionEvent.ACTION_DOWN:

    //             translated = PointerEvent.PRESSED;
    //             break;

    //         case MotionEvent.ACTION_UP:
    //             translated = PointerEvent.RELEASED;
    //             break;

    //         case MotionEvent.ACTION_MOVE:

    //             return;

    //         default:

    //             return;


    //     }

    //     generatePointerEvents( translated, m );

    // }

    // private void generatePointerEvents( int event, MotionEvent m ) {

    //     //
    //     // only handling first pointer (for now)
    //     //

    //     Position position = new Position( m.getX(), m.getY() );

    //     for( MenuActionSet menu : menus )

    //         menu.pointerEvent( event, position );

    // }

}

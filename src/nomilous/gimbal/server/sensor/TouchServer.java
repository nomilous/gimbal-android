package nomilous.gimbal.server.sensor;

import nomilous.Util;
import nomilous.gimbal.GimbalEvent;

import android.content.Context;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.View;

public class TouchServer extends GimbalEvent.Server {

    private OnTouchListener onTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if( !active ) return false;

            //subscriber.onSensorEvent( SensorSubscriber.TOUCH_EVENT, event);

            Util.info("onTouch " + event.toString());

            return true;

        }

    };

    public TouchServer( Context context,  GimbalEvent.Publisher publisher, /* Object subscriber, */ View v ) { 

        super(context, publisher);
        v.setOnTouchListener(onTouchListener);

    }

}

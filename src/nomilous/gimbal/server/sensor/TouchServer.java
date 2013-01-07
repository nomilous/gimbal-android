package nomilous.gimbal.server.sensor;

import nomilous.gimbal.client.SensorSubscriber;
import nomilous.Util;

import android.content.Context;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.View;

public class TouchServer {

    private SensorSubscriber subscriber;
    private Context appContext;
    private boolean active = false;

    private OnTouchListener onTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if( !active ) return false;

            subscriber.onSensorEvent( SensorSubscriber.TOUCH_EVENT, event);

            return true;

        }

    };

    public TouchServer( Context appContext, Object subscriber, View v ) { 

        this.subscriber = (SensorSubscriber) subscriber;
        this.appContext = appContext;
        v.setOnTouchListener(onTouchListener);

    }

    public void startServer() { active = true; }

    public void stopServer() { active = false; }

}

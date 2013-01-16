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

            publish( (GimbalEvent.Event) new GimbalEvent.Touch( event ) );

            return true;

        }

    };

    public TouchServer( Context context,  GimbalEvent.Publisher publisher, /* Object subscriber, */ View v ) { 

        super(context, publisher);
        v.setOnTouchListener(onTouchListener);

    }

}

package nomilous.gimbal.server.sensor;

import android.view.inputmethod.InputMethodManager;
// import android.os.ResultReceiver;
// import android.os.Handler;
// import android.os.Handler.Callback;
// import android.os.Message;

import nomilous.gimbal.client.SensorSubscriber;
import nomilous.Util;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.text.TextWatcher;
import android.text.Editable;
import android.os.Bundle;

public class KeypadServer {

    private SensorSubscriber subscriber;
    private Context appContext;
    private boolean active = false;
    private boolean on = false;

    private InputMethodManager imm;
    private View view;
    //private EditText view;

    // private Callback callback = new Callback() {
    //     @Override
    //     public boolean handleMessage( Message msg ) {
    //         Util.info( "Message: " + msg.toString() );
    //         return true;
    //     }
    // };
    // private Handler handler = new Handler( callback );
    // private ResultReceiver receiver = new ResultReceiver(handler);

    private TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable e) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if( !active && !on ) return;


            Bundle keypadEvent = new Bundle();
            keypadEvent.putCharSequence( "text", s );
            keypadEvent.putInt( "start", start );
            keypadEvent.putInt( "before", before );
            keypadEvent.putInt( "count", count );

            subscriber.onSensorEvent( SensorSubscriber.KEYPAD_EVENT, keypadEvent);

            //Util.info("onTextChanged: " + s.toString() + " start:" + start + " before:" + before + " count:" + count);

        }
    
    };
    

    public KeypadServer( Context appContext, Object subscriber, View view ) { 

        this.subscriber = (SensorSubscriber) subscriber;
        this.appContext = appContext;
        this.view = view;

        imm = (InputMethodManager) appContext.getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    public void startServer() { active = true; }

    public void stopServer() { active = false; }


    public void stopKeypad() { 

        Util.info("Keypad OFF");
        on = false;
        //view.removeTextChangedListener(textWatcher);
        //view.setVisibility(View.INVISIBLE);
        //view.setText( "" );
        //imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);

    }

    public void startKeypad() { 

        Util.info("Keypad ON");
        try {


            //imm.showSoftInput( view, InputMethodManager.SHOW_IMPLICIT, receiver );
            //
            // 
            //
            // ...that would have been nice! (but no keyboard appears)
            //
            

            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            //
            // 
            // ...hmmm, starts a keyboard, but nothing is receiving the keystrokes
            //


            // handler.post( new Runnable() {
            //     public void run() {
            //         imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            //     }
            // });
            //
            //
            // ...grrrr!
            // 


            imm.toggleSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);

            //view.setFocusable(true);
            //view.setVisibility(View.VISIBLE);
            //view.setText( "" );
            view.requestFocus();

            //imm.showSoftInput( view, InputMethodManager.SHOW_FORCED );

            view.setOnKeyListener( new OnKeyListener() {
                @Override
                public boolean onKey( View v, int keyCode, KeyEvent event ) {

                    //
                    // does not get the key codes...
                    // 
                    //   ???
                    //

                    Util.info("KEY: " + keyCode + " " + event.toString());
                    if( keyCode == 66 ) stopKeypad();
                    return false;

                }
            });

            //view.addTextChangedListener( textWatcher );

            // imm.showSoftInput( view, InputMethodManager.SHOW_FORCED );
            //
            // ...grrrr!


            Util.info( "Any View? " + imm.isActive() );
            Util.info( "My View? " + imm.isActive(view) );
            // imm.restartInput(view);
            // Util.info( "My View? " + imm.isActive(view) );




        } catch (Exception e) {

            Util.error( e.toString() );

        }
        on = true;

    }


}

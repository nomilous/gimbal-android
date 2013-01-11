package nomilous.gimbal;

import android.app.Activity;
import android.os.Bundle;

import nomilous.Util;

public class Gimbal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gimbal);
        Util.debug("onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Util.debug("onStart()");
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        Util.debug("onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.debug("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Util.debug("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Util.debug("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.debug("onDestroy()");
    }

}

/****************************************

Lifecycle: 

1. First Start App (After install)

    D/=====DEBUG( 6019): onCreate()
    D/=====DEBUG( 6019): onStart()
    D/=====DEBUG( 6019): onResume()

    App in the foreground.


2. Primary Button (hardware)

    
    D/=====DEBUG( 6019): onPause()
    D/=====DEBUG( 6019): onStop()

    App in the background? Not Visible.
    Not listed in running in Settings/Application/Manage/Running


3.  Start App Again

        
    D/=====DEBUG( 6019): onCreate()
    D/=====DEBUG( 6019): onStart()
    D/=====DEBUG( 6019): onResume()

    App in the foreground.


4.  Back button.

    D/=====DEBUG( 6019): onPause()
    D/=====DEBUG( 6019): onStop()
    D/=====DEBUG( 6019): onDestroy()


5.  Restart App

    D/=====DEBUG( 6019): onCreate()
    D/=====DEBUG( 6019): onStart()
    D/=====DEBUG( 6019): onResume()

    App in the foreground.

6.  Navigate away (to another app)


    D/=====DEBUG( 7782): onPause()
    D/=====DEBUG( 7782): onStop()

7.  Resume from menu

    D/=====DEBUG( 7782): onRestart()
    D/=====DEBUG( 7782): onStart()
    D/=====DEBUG( 7782): onResume()


8.  Navigate away, and resume with Back button


    D/=====DEBUG( 7782): onPause()
    D/=====DEBUG( 7782): onStop()


    D/=====DEBUG( 7782): onRestart()
    D/=====DEBUG( 7782): onStart()
    D/=====DEBUG( 7782): onResume()



****************************************/

package nomilous.gimbal;

import android.app.Activity;
import android.os.Bundle;

public class GimbalActivity extends Activity {

    private GimbalUIOverlay ui;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = new GimbalUIOverlay(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        ui.start();
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        ui.restart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ui.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ui.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ui.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ui.destroy();
    }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    //     Util.debug("onCreateOptionsMenu()");
    //     menu.close();
    //     return true;
    // }

    // @Override
    // public boolean onMenuOpened(int featureId, Menu menu) {
    //     Util.debug("onMenuOpened()");
    //     menu.close();
    //     return true;
    // }

}


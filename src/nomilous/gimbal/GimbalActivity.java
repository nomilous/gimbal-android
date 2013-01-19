package nomilous.gimbal;

import nomilous.gimbal.GimbalUIController;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class GimbalActivity extends Activity {

    private GimbalUIController ui;
  
    @Override
    protected void onCreate(Bundle incus) {
        super.onCreate(incus);
        ui = new GimbalUIController(this);
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult qrScanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (qrScanResult != null) {
            String viewportAddressParts[] = qrScanResult.getContents().split(" ");
            String uri = viewportAddressParts[0];
            String viewportID = viewportAddressParts[1];

            ui.connectViewport( uri, viewportID );
            
        }
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


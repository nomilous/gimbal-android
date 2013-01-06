package nomilous.gimbal;

import android.app.Activity;
import android.os.Bundle;

//import android.widget.LinearLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import nomilous.gimbal.client.GimbalController;
import nomilous.gimbal.client.GimbalEventHandler;
import nomilous.Util;


public class Gimbal extends Activity 
    implements GimbalEventHandler {

    //private LinearLayout viewports;
    private TextView instruction;
    private OnClickListener textClickListener;
    private GimbalController gimbal;

    private final String SCAN_TAG = "touch to grab viewport";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gimbal);

        textClickListener = initTextClickListener();

        instruction = (TextView) findViewById(R.id.instruction);
        instruction.setText( SCAN_TAG );
        instruction.setOnClickListener( textClickListener );

        //viewports = (LinearLayout) findViewById(R.id.viewports);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
  
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        
        if (scanResult != null) {

            String viewportAddressParts[] = scanResult.getContents().split(" ");

            gimbal = new GimbalController( 

                getApplicationContext(),
                this,
                viewportAddressParts[0],
                viewportAddressParts[1]

            );

            gimbal.connect();

        }
  
    }

    @Override
    public void gimbalEvent( final String event, final Object payload ) {

        Util.info( 

            String.format( "GimbalEvent %s: %s", event, payload.toString() ) 

        );

        //
        // explicitly run on UIthread because the 'websocket' 
        // client making this call is on another, and so
        // not allowed to manipulate view stuff
        // 

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if( event.equals( GimbalEventHandler.CONTROLLER_CONNECTED ) ) {

                    instruction.setText("connected");

                } else if( event.equals( GimbalEventHandler.ASSIGN_PRIMARY_VIEWPORT ) ) {

                    instruction.setText("controlling viewport " + payload.toString() );

                }

            }
        });

    }


    private OnClickListener initTextClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) { 

                String text = "";
                try {

                    text = ((TextView)v).getText().toString();
                    Util.info( "Clicked on textview with text: " + text );

                } catch (Exception e) {

                    Util.error(e.toString());
                    return;

                }

                if( text.equals( SCAN_TAG ) ) scanTag(); 

                else Util.warn( "Huh?" ); 

            }
        };
    }

    private void scanTag() {
        final Activity scanResultHandler = this;
        IntentIntegrator qrScan = new IntentIntegrator(scanResultHandler);
        qrScan.initiateScan();
    }

}

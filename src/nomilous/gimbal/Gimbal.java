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
    private TextView primary;
    private TextView disconnect;
    private TextView exit;
    private OnClickListener textClickListener;
    private GimbalController gimbal;

    private final String SCAN_TAG = "connect viewport";
    private final String RELEASE_VIEWPORTS = "disconnect";
    private final String EXIT = "exit";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gimbal);

        textClickListener = initTextClickListener();

        primary = (TextView) findViewById(R.id.primary);
        primary.setText( SCAN_TAG );
        primary.setOnClickListener( textClickListener );

        disconnect = (TextView) findViewById(R.id.disconnect);
        disconnect.setText( RELEASE_VIEWPORTS );
        disconnect.setOnClickListener( textClickListener );

        exit = (TextView) findViewById(R.id.exit);
        exit.setText( EXIT );
        exit.setOnClickListener( textClickListener );

        //viewports = (LinearLayout) findViewById(R.id.viewports);

        gimbal = new GimbalController( getApplicationContext(), this );

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
  
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        
        if (scanResult != null) {

            String viewportAddressParts[] = scanResult.getContents().split(" ");
            String uri = viewportAddressParts[0];
            String viewportID = viewportAddressParts[1];

            disconnect.setText( "...attempting connect..." );
            disconnect.setVisibility(View.VISIBLE);

            gimbal.connect( uri, viewportID );

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

                    primary.setText("connected");
                    disconnect.setText( RELEASE_VIEWPORTS );
                    disconnect.setVisibility(View.VISIBLE);

                } else if( event.equals( GimbalEventHandler.ASSIGN_PRIMARY_VIEWPORT ) ) {

                    primary.setText("controlling viewport " + payload.toString() );

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

                else if ( text.equals( RELEASE_VIEWPORTS ) ) releaseViewports(); 

                else if ( text.equals( EXIT ) ) exit(); 

                else Util.warn( "Huh?" ); 

            }
        };
    }

    private void scanTag() {
        final Activity scanResultHandler = this;
        IntentIntegrator qrScan = new IntentIntegrator(scanResultHandler);
        qrScan.initiateScan();
    }

    private void releaseViewports() {

        gimbal.disconnect();

    }

    private void exit() {


    }

}

package nomilous.gimbal;

import android.app.Activity;
import android.os.Bundle;

import android.widget.LinearLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import nomilous.gimbal.client.GimbalController;


public class Gimbal extends Activity {

    private LinearLayout viewports;
    private TextView instruction;
    private GimbalController gimbal;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gimbal);

        final Activity scanResultHandler = this;

        viewports = (LinearLayout) findViewById(R.id.viewports);
        
        instruction = (TextView) findViewById(R.id.instruction);
        instruction.setText("touch to grab viewport");

        viewports.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {

                IntentIntegrator qrScan = new IntentIntegrator(scanResultHandler);
                qrScan.initiateScan();

            }

        });

    }

    @Override
    public void onResume() {

        super.onResume();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
  
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        
        if (scanResult != null) {

            String viewportAddressParts[] = scanResult.getContents().split(" ");

            gimbal = new GimbalController( 

                viewportAddressParts[0],
                viewportAddressParts[1]

            );

            gimbal.connect();

        }
  
    }

}

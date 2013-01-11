package nomilous.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import nomilous.Util;
import nomilous.gimbal.GimbalActivity;

public class MainActivity extends GimbalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);   
    }

}

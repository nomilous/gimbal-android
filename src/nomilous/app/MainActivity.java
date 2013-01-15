package nomilous.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import nomilous.Util;
import nomilous.gimbal.GimbalActivity;
import nomilous.gimbal.GimbalConfig;

public class MainActivity extends GimbalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //
        // Set feature BitFlags
        //

        //GimbalConfig.VISUAL_FEEDBACK  = GimbalConfig.Option.CAMERA;
        GimbalConfig.VISUAL_FEEDBACK = GimbalConfig.Option.GL10;


        //
        // override the default feedback
        // 
        // GimbalConfig.GL10_FEEDBACK = new SomeOrOtherGLRenderer();
            
    }

}

package nomilous.gimbal.overlays;

import android.app.Activity;
import android.content.Context;

public class GimbalOverlay {
        
    protected Activity activity;
    protected Context  context;

    public GimbalOverlay(Object android) {
        this.context  = (Context) android;
        this.activity = (Activity) android;
    }

}

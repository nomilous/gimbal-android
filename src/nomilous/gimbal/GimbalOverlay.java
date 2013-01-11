package nomilous.gimbal;

import android.app.Activity;
import android.content.Context;

class GimbalOverlay {
        
    protected Activity activity;
    protected Context  context;

    GimbalOverlay(Object android) {
        this.context  = (Context) android;
        this.activity = (Activity) android;
    }

}

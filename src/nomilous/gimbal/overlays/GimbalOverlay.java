package nomilous.gimbal.overlays;

import nomilous.gimbal.menu.Menu;

import android.app.Activity;
import android.content.Context;

public abstract class GimbalOverlay 

    implements Menu.SelectionHandler {
        
    protected Activity activity;
    protected Context  context;

    public GimbalOverlay(Object android) {
        this.context  = (Context) android;
        this.activity = (Activity) android;
    }

}

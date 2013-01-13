package nomilous.gimbal;

import android.view.SurfaceView;

import nomilous.Util;

class GimbalCameraOverlay extends GimbalOverlay {

    GimbalCameraOverlay(Object android) {
        super(android);
        Util.debug("START GimbalCameraOverlay");   
    }

    public SurfaceView view() {
        return new SurfaceView(context);
    }

}

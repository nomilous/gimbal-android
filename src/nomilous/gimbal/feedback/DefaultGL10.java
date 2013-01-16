package nomilous.gimbal.feedback;

import nomilous.Util;
import nomilous.gimbal.GimbalGL10Renderer;
import nomilous.gimbal.GimbalEvent;

//
// Renders touch and sensor feedback visualisation 
// into a transluscent overlay.
//

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;


public class DefaultGL10 extends GimbalGL10Renderer {

    @Override
    public void onTouchEvent( GimbalEvent.Touch event ) {

        Util.info("DefaultGL10.onTouchEvent() " + event.toString() );

    }

    @Override
    public void onDrawFrame(GL10 gl) {}
        
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {}

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {}

}

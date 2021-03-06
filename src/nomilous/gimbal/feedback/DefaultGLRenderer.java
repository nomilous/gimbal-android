package nomilous.gimbal.feedback;

import nomilous.Util;
import nomilous.gimbal.GimbalGLRenderer;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.menu.*;

//
// Renders touch and sensor feedback visualisation 
// into a transluscent overlay.
//

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;


public class DefaultGLRenderer extends GimbalGLRenderer {

    private boolean touchFeedbackEnabled = true;

    @Override
    public void onMenuShow(MenuActionGroup menu) {
        Util.debug("DefaultGLRenderer.onMenuShow()");
        touchFeedbackEnabled = false;
    }

    @Override
    public void onMenuHide(MenuActionGroup menu) {
        touchFeedbackEnabled = true;
    }

    @Override
    public void onMenuSelection(MenuAction action) {

    }

    private float width;
    private float height;

    //
    // TODO: make these sprites
    //
    private Shape[] pointers = new Shape[8];
    private int currentCount = 0;

    @Override
    public void onTouchEvent( GimbalEvent.Touch event ) {

        currentCount = event.pointerCount();

        if( touchFeedbackEnabled )

            for( int i = 0; i < currentCount; i++ )

                pointers[i].position( new float[]{

                    event.pointer(i).floatX(),
                    height - event.pointer(i).floatY(),
                    0.0f

                });

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        if( touchFeedbackEnabled )

            for( int i = 0; i < currentCount; i++ )

                pointers[i].draw(gl);

    }
        
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        Util.debug("width " + width + " height " + height);
        this.width = (float) width;
        this.height = (float) height;

        //self.view.bounds.size.width

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        //
        // perspective
        // 
        //float aspect = (float)width / height;
        //GLU.gluPerspective(gl, 35, aspect, 0.1f, 1000.f);

        // 
        // orthographic (center origin)
        // 
        //gl.glOrthof(-width/2, width/2, -height/2, height/2, -1f, 100.0f);


        // 
        // orthographic (bottom left origin)
        // 
        gl.glOrthof(0f, width, 0f, height, -1f, 1000f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        for( int i = 0; i < 8; i++ ) pointers[i] = new Square(10);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepthf(1.0f);             
        // gl.glEnable(GL10.GL_DEPTH_TEST);
        // gl.glDepthFunc(GL10.GL_LEQUAL);
        // //gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        // gl.glShadeModel(GL10.GL_SMOOTH);
        // gl.glDisable(GL10.GL_DITHER);

    }

}

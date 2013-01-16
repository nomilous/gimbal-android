package nomilous.gimbal.feedback;

import nomilous.Util;
import nomilous.gimbal.GimbalGL10Renderer;
import nomilous.gimbal.GimbalEvent;

import nomilous.gimbal.MeshCube;

//
// Renders touch and sensor feedback visualisation 
// into a transluscent overlay.
//

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;


public class DefaultGL10 extends GimbalGL10Renderer {

    private float width;
    private float height;

    //
    // TODO: make these sprites
    //
    private MeshCube[] cubes = new MeshCube[8];
    private float[] x        = new float[8];
    private float[] y        = new float[8];
    private int currentCount = 0;




    @Override
    public void onTouchEvent( GimbalEvent.Touch event ) {

        currentCount = event.pointerCount();

        for( int i = 0; i < currentCount; i++ ) {
            x[i] = event.pointer(i).floatX();
            y[i] = height - event.pointer(i).floatY();
            Util.info( x[i] + " " + y[i] );
        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        for( int i = 0; i < currentCount; i++ ) {
            gl.glPushMatrix();
            gl.glTranslatef(x[i], y[i], 0f);
            cubes[i].draw(gl);
            gl.glPopMatrix();
        }

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

        for( int i = 0; i < 8; i++ ) cubes[i] = new MeshCube(1,1);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepthf(1.0f);             
        // gl.glEnable(GL10.GL_DEPTH_TEST);
        // gl.glDepthFunc(GL10.GL_LEQUAL);
        // //gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        // gl.glShadeModel(GL10.GL_SMOOTH);
        // gl.glDisable(GL10.GL_DITHER);

    }

}

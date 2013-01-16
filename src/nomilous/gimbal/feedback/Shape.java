package nomilous.gimbal.feedback;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class Shape {

    public static final int     FLOAT_SIZE    = 4; //bytes
    public static final int     VERTEX_LENGTH = 3; //floats 
    public static final float[] UNIT_SQUARE = {
        -0.5f, -0.5f, 0.0f,  // bottom left and around anti-clockwise
         0.5f, -0.5f, 0.0f,
         0.5f,  0.5f, 0.0f,
        -0.5f,  0.5f, 0.0f, 
        -0.5f, -0.5f, 0.0f   // back to start  
    };

    protected FloatBuffer vertex;

    protected float[] position = {
        0.0f, 0.0f, 0.0f
    };

    public Shape() {
        buildVertexBuffer();
    }

    public void position(float[] position) {
        for(int i = 0; i < 3; i++) 
            this.position[i] = position[i];
    }

    public void draw(GL10 gl) {
        vertex.position(0);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
        gl.glPushMatrix();

        translate(gl);
        scale(gl);

        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, model().length / VERTEX_LENGTH);
        gl.glPopMatrix();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    protected void buildVertexBuffer() {
        ByteBuffer bb = ByteBuffer.allocateDirect( model().length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        vertex = bb.asFloatBuffer();
        vertex.put(model());
    }

    protected void translate(GL10 gl) {
        gl.glTranslatef(position[0], position[1], position[2]);
    }

    //
    // Override to scale to specified size
    //
    abstract void scale(GL10 gl);

    //
    // Override to return the float[] array of 
    // model vertexes
    //
    abstract float[] model();

}

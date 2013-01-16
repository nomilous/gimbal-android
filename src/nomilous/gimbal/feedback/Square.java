package nomilous.gimbal.feedback;

import javax.microedition.khronos.opengles.GL10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Square {

    public static final int FLOAT_SIZE = 4; //bytes

    private FloatBuffer vertex;
    private float width = 1.0f;

    private float[] unitSquare = {
        -0.5f, -0.5f, 0.0f,  // bottom left and around anti-clockwise
         0.5f, -0.5f, 0.0f,
         0.5f,  0.5f, 0.0f,
        -0.5f,  0.5f, 0.0f, 
        -0.5f, -0.5f, 0.0f   // back to start
    };
    
    public Square() {
        buildVertexBuffer();
    }

    public Square(float width) {
        this.width = width;
        buildVertexBuffer();
    }

    public void draw( GL10 gl ) {

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        vertex.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);

        gl.glPushMatrix();
        gl.glScalef(width, width, 0.0f);
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 5);
        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    private void buildVertexBuffer() {
        ByteBuffer bb = ByteBuffer.allocateDirect( unitSquare.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        vertex = bb.asFloatBuffer();
        vertex.put(unitSquare);
    }

}

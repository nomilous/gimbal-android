package nomilous.gimbal;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class MeshCube {

    private FloatBuffer vertexBuffer;

    private float mWidth;
    private int mSegments;
    private float mDivision;

    private float[] square = {

        -0.5f, -0.5f, 0.5f,  // bottom left and around anti-clockwise
         0.5f, -0.5f, 0.5f,
         0.5f,  0.5f, 0.5f,
        -0.5f,  0.5f, 0.5f, 
        -0.5f, -0.5f, 0.5f   // back to start 

    };

    public MeshCube( float width, int segments ) {

        mWidth = width;
        mSegments = segments;
        mDivision = width / segments;

        Log.v( "==========", "" + mDivision );

        ByteBuffer vbb = ByteBuffer.allocateDirect(square.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(square);

    }


    public void draw( GL10 gl ) {

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        for( int h = 0; h < 3; h++ ) {

            if( h == 1 ) {

                gl.glRotatef( 90.0f, 1.0f, 0.0f, 0.0f );

            } else if( h == 2 ) {

                gl.glRotatef( 90.0f, 0.0f, 1.0f, 0.0f );

            }

            for( int i = 0; i <= mSegments; i++ ) {

                //
                // Rewind to the start of the vertex buffer
                //

                vertexBuffer.position(0); 

                //
                // Push the current modelview matrix
                // preserve already present transforms.
                // 

                gl.glPushMatrix();

                //
                // Apply transforms to the modelview matrix
                //

                // Scale to size, originial lines are unit length
                gl.glScalef( mWidth, mWidth, mWidth ); 

                // Move each drawn square slightly more along the z axis
                gl.glTranslatef( 0.0f, 0.0f, -i * mDivision );

                //
                // Each vertex from the buffer is multiplied
                // through the modelview matrix at the top of 
                // the stack. 
                //
                // This rotates, translates or scales 
                // each vertex with respect to the origin 
                // according to whichever transforms were
                // applied.
                // 

                gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 5);

                //
                // Put the original matrix back ontop
                //

                gl.glPopMatrix();

            }

        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

}

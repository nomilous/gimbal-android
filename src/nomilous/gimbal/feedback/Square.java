package nomilous.gimbal.feedback;

import javax.microedition.khronos.opengles.GL10;

public class Square extends Shape {

    protected float width  = 1.0f;
    protected float height = 1.0f;

    public Square() {
        super();
    }

    public Square(float width) {
        this.width = this.height = width;
        buildVertexBuffer();
    }

    void scale(GL10 gl) {
        gl.glScalef(width, height, 0.0f);
    }

    float[] model() { return UNIT_SQUARE; }

}

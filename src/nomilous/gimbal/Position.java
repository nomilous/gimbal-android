package nomilous.gimbal;

import java.lang.Math;

public class Position {

    public float floatX() { return vx; }
    public float floatY() { return vy; }
    public int   intX() { return Math.round(vx); }
    public int   intY() { return Math.round(vy); }

    private float vx;
    private float vy;

    public Position( float x, float y ) { 

        this.vx = x; 
        this.vy = y; 

    }
        
}

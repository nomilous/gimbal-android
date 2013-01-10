package nomilous.gimbal;

public class Anchor extends Position implements Movable {
    
    public Anchor( int x, int y ) {
        super( (float)x, (float)y );
    }

    public void pointerEvent( int event, Position position ) {}
    public void onPressed() {}
    public void onReleased() {}
    public void onMoved( Position position ) {}

}

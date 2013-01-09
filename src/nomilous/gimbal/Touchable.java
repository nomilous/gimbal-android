package nomilous.gimbal;

public interface Touchable {

    public static class PointerEvent {

        public static final int PRESSED  = 1;
        public static final int RELEASED = 2;

        public static class Position { 
            public int x;
            public int y;
            public Position( int x, int y ) { this.x = x; this.y = y; }
        }

    }


    //
    // Receive the PointerEvent (from the touch screen)
    //

    public abstract void pointerEvent( PointerEvent event );


    //
    // Fire this event if the pointer press was within the boundries of
    // the Object implementing this interface.
    // 

    public abstract void onPressed();


    //
    // Sing to small pebbles if the river they're in is babbling.
    //

    public abstract void onReleased();

}


//
// This may seem pointless to android initiated persons... 
//
// The intension is to place a 'shim' that expresses the 
// functionality as agnostically as possible, 
// 
// To ease the job of porting to other platforms.
//  

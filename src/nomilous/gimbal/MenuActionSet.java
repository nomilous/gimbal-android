package nomilous.gimbal;
import nomilous.Util;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Hashtable;
import android.graphics.Color;
import android.graphics.Typeface;
import java.util.Enumeration;

public class MenuActionSet implements Touchable {

    public static class Config {

        public int enabledColour = Color.LTGRAY;
        public int disabledColour = Color.DKGRAY;
        public int highlightColour = Color.WHITE;

        public Anchor chainStart = new Anchor( 100, 10 );
        public Anchor chainEnd = new Anchor( 100, 300 );

        public Typeface font = Typeface.create( Typeface.MONOSPACE, Typeface.NORMAL );

        // TODO: user defined font from assets()
        //
        // Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Kokila.ttf"); 
        // txtyour.setTypeface(type);
        //
        // complexities may arrise for non-monospace fonts and the 
        // varying width requirements for the layout.

    }

    private ArrayList<MenuAction> actions;
    private Hashtable<String,Integer> actionsIndex;
    private Hashtable<String,TextView> views;
    private Hashtable<String,LayoutParams> params;
    private RelativeLayout layout;

    private Config config;
    private Chain chain;
    private TextView current;

    public MenuActionSet() {

        config = new Config();
        init();

    }

    public MenuActionSet( Config config ) {

        this.config = config;
        init();
        
    }

    public void show( RelativeLayout layout ) {
        Util.debug("MenuActionSet.show()");

        //
        // Insert MenuActions along the Chain
        //

        chain.set( layout, actions.size() );

        for( MenuAction action : actions )

            action.render( chain.next() );

        //
        // Keep ref to layout for .hide()
        //

        this.layout = layout;


    }

    public void hide() {
        Util.debug("MenuActionSet.hide()");

        //
        // Remove each inserted view
        //

        for( MenuAction action : actions ) {

            params.remove( action.label );
            TextView view = (TextView) views.remove( action.label );
            layout.removeView( view );
            view = null;

        }

    }

    public void add( MenuAction action ) {
        actions.add( action );
        actionsIndex.put( action.label, actions.size() - 1 );
    }

    // TODO: fix remove breaks actionsIndex
    // 
    // public void remove( MenuAction action ) {
    //     remove( action.label );
    // }

    // public void remove( String label ) {
    //     int index = -1;
    //     int i = 0;
    //     for( MenuAction action : actions ) {
    //         if( action.label.equals(label) ) {
    //             index = i;
    //             break;
    //         }
    //         i++;
    //     }
    //     if( index == -1 ) return;
    //     actions.remove( index );

    // } 


    public void pointerEvent( int event, Position position ) {
        
        int x = position.intX();
        int y = position.intY();

        Enumeration<String> e = views.keys();
        while(e.hasMoreElements()) {

            current = views.get(e.nextElement());

            if( x < current.getLeft()   ) continue;
            if( x > current.getRight()  ) continue;
            if( y < current.getTop()    ) continue;
            if( y > current.getBottom() ) continue;

            switch( event ) {
                case Touchable.PointerEvent.PRESSED:
                    onPressed();
                    break;

                case Touchable.PointerEvent.RELEASED:
                    onReleased();
                    break;

            }

        }

    }

    public void onPressed() {

        MenuAction action = getCurrent();

        if( !action.enabled ) return;

        current.setTextColor(config.highlightColour);

    }

    public void onReleased() {

        MenuAction action = getCurrent();

        if( !action.enabled ) return;

        current.setTextColor(config.enabledColour);

    }


    private MenuAction getCurrent() {
        return actions.get( 
            actionsIndex.get( 
                current.getText().toString() 
            ) 
        );
    }

    private void init() {
        actions = new ArrayList<MenuAction>();
        actionsIndex = new Hashtable<String,Integer>();
        views = new Hashtable<String,TextView>();
        params = new Hashtable<String,LayoutParams>();
        chain = new Chain( config, views, params );
    }

}

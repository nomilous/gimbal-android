package nomilous.gimbal;
import nomilous.Util;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import android.graphics.Color;
import android.graphics.Typeface;

public class MenuActionSet {

    public static class Config {

        public int enabledColour = Color.WHITE;
        public int disabledColour = Color.DKGRAY;
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
    private HashMap<String,TextView> views;
    private HashMap<String,LayoutParams> params;
    private RelativeLayout layout;

    private Config config;
    private Chain chain;

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

        chain.set( layout );

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
        this.actions.add( action );
    }

    public void remove( MenuAction action ) {
        remove( action.label );
    }

    public void remove( String label ) {
        int index = -1;
        int i = 0;
        for( MenuAction action : actions ) {
            if( action.label.equals(label) ) {
                index = i;
                break;
            }
            i++;
        }
        if( index == -1 ) return;
        actions.remove( index );

    } 

    private void init() {
        actions = new ArrayList<MenuAction>();
        views = new HashMap<String,TextView>();
        params = new HashMap<String,LayoutParams>();
        chain = new Chain( config, views, params );
    }

}

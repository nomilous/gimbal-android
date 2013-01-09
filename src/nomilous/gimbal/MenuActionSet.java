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

    public MenuActionSet() {

        config = new Config();
        init();

    }

    public MenuActionSet( Config config ) {

        this.config = config;
        init();
        
    }

    public void show( Context context, RelativeLayout layout ) {
        Util.debug("MenuActionSet.show()");

        //
        // For each action
        //
        // - insert a text view with action.label into layout
        // - position it
        //

        int left = 100;
        int top = 0;

        for( MenuAction action : actions ) {

            TextView view = new TextView( context );
            view.setText( action.label );
            


            int width = 100;
            int height = 40; // TODO: ensure it fits..

            LayoutParams layoutParams = new LayoutParams(width, height);
            // TODO: span positions proportionally (pending Anchors())
            layoutParams.setMargins(left, top += 50, 0, 0);
            view.setLayoutParams(layoutParams);

            
            if( action.enabled ) view.setTextColor( config.enabledColour );
            else view.setTextColor( config.disabledColour );

            view.setTypeface( config.font );

            layout.addView( view );

            //
            // keep reference to each each view and layout
            //

            views.put( action.label, view );
            params.put( action.label, layoutParams );

        }

        //
        // Keep ref to layout for .hide()
        //

        this.layout = layout;


    }

    public void hide() {
        Util.debug("MenuActionSet.hide()");

        //
        // For each action
        //
        // - remove each associated view from the
        //   hashmap and the layout
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
    }

}

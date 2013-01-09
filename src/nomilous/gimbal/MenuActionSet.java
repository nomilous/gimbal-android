package nomilous.gimbal;
import nomilous.Util;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuActionSet {

    private ArrayList<MenuAction> actions;
    private HashMap<String,TextView> views;
    private RelativeLayout layout;


    public MenuActionSet() {
        actions = new ArrayList<MenuAction>();
        views = new HashMap<String,TextView>();
    }

    public void show( Context context, RelativeLayout layout ) {
        Util.debug("MenuActionSet.show()");

        //
        // For each action
        //
        // - insert a text view with action.label into layout
        // - keep reference to each view
        //

        for( MenuAction action : actions ) {

            TextView view = new TextView( context );
            view.setText( action.label );
            layout.addView( view );
            views.put( action.label, view );

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

}

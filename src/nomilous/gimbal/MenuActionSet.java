package nomilous.gimbal;
import nomilous.Util;

import java.util.ArrayList;

public class MenuActionSet {

    private ArrayList<MenuAction> actions;

    public MenuActionSet() {
        actions = new ArrayList<MenuAction>();
    }

    public void show() {
        Util.debug("MenuActionSet.show()");
    }

    public void hide() {
        Util.debug("MenuActionSet.hide()");
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

        // for( MenuAction action : actions ) {
        //     Util.debug( String.format( 
        //         "Label: %s, Description: %s",
        //         action.label,
        //         action.description
        //     ));
        // }

    } 

}

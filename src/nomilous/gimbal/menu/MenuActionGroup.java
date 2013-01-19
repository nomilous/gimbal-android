package nomilous.gimbal.menu;

import nomilous.Util;
import nomilous.gimbal.GimbalEvent;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Hashtable;
//import android.graphics.Color;
//import android.graphics.Typeface;
import java.util.Enumeration;


public class MenuActionGroup 

    implements GimbalEvent.Subscriber,
               Menu.Touchable {

    private ArrayList<MenuAction> actions;
    private Hashtable<String,Integer> actionsIndex;
    private Hashtable<String,TextView> views;
    private Hashtable<String,LayoutParams> params;
    private RelativeLayout layout;

    private boolean active = false;

    private Menu.Config config;
    private MenuChain chain;
    private TextView current;

    public MenuActionGroup() {

        config = new Menu.Config();
        init();

    }

    public MenuActionGroup( Menu.Config config ) {

        this.config = config;
        init();
        
    }

    public void show( RelativeLayout layout ) {
        Util.debug("MenuActionGroup.show()");

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

        active = true;


    }

    public void hide() {
        Util.debug("MenuActionGroup.hide()");

        //
        // Remove each inserted view
        //

        for( MenuAction action : actions ) {

            params.remove( action.label );
            TextView view = (TextView) views.remove( action.label );
            layout.removeView( view );
            view = null;

        }

        active = false;

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

    public void onTouchEvent( GimbalEvent.Touch event ) {

        if( !active ) return;

        int x = event.pointer().intX();
        int y = event.pointer().intY();

        Enumeration<String> e = views.keys();
        while(e.hasMoreElements()) {

            current = views.get(e.nextElement());

            if( x < current.getLeft()   ) continue;
            if( x > current.getRight()  ) continue;
            if( y < current.getTop()    ) continue;
            if( y > current.getBottom() ) continue;

            switch( event.action() ) {

                case GimbalEvent.Touch.Action.PRESS:
                    onPressed();
                    break;

                case GimbalEvent.Touch.Action.RELEASE:
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
        config.selectionHandler.onMenuSelection(action);

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
        chain = new MenuChain( config, views, params );
    }

}

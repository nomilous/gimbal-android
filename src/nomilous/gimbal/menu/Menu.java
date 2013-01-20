package nomilous.gimbal.menu;

import nomilous.Util;

import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Typeface;
import android.graphics.Color;

public class Menu {

    public static interface Touchable {

        public abstract void onPressed();
        public abstract void onReleased();

    }

    public static interface SelectionHandler {

        public abstract void onMenuShow(MenuActionGroup menu);
        public abstract void onMenuHide(MenuActionGroup menu);
        public abstract void onMenuSelection(MenuAction action);

    }

    public static class Config {

        public SelectionHandler selectionHandler = null;

        public int enabledColour = Color.LTGRAY;
        public int disabledColour = Color.DKGRAY;
        public int highlightColour = Color.WHITE;

        public MenuAnchor togglePosition = new MenuAnchor( 3, -3 );
        public String     toggleText     = "menu";

        public MenuAnchor chainStart = new MenuAnchor( 100, 10 );
        public MenuAnchor chainEnd   = new MenuAnchor( 100, 300 );

        public Typeface font = Typeface.create( Typeface.MONOSPACE, Typeface.NORMAL );

        // TODO: user defined font from assets()
        //
        // Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Kokila.ttf"); 
        // txtyour.setTypeface(type);
        //
        // complexities may arrise for non-monospace fonts and the 
        // varying width requirements for the layout.

    }

    public static MenuActionGroup create(RelativeLayout layout, Menu.Config config) {

        final TextView toggle = new TextView( layout.getContext() );
        final int activeColor = config.highlightColour;
        final int passivColor = config.enabledColour;
        toggle.setText( config.toggleText );
        RelativeLayout.LayoutParams toggleParams = new RelativeLayout.LayoutParams(40, 100);
        toggleParams.setMargins(config.togglePosition.x(), config.togglePosition.y(), 0, 0);
        toggle.setLayoutParams(toggleParams);
        toggle.setTypeface(config.font);
        layout.addView(toggle);

        final MenuActionGroup  menu = new MenuActionGroup(config);
        final RelativeLayout   menuRoot = layout;
        final SelectionHandler selectionHandler = config.selectionHandler;

        menu.setToggle(toggle);

        toggle.setOnClickListener( new OnClickListener() {

            //
            // Toggle menu onClick
            //

            //private boolean active = false; // menu is not visible

            @Override
            public void onClick(View v) {

                if( menu.active() ) {
                    menu.hide();
                    selectionHandler.onMenuHide(menu); // may not need this.
                    return;
                }

                menu.show(menuRoot);
                selectionHandler.onMenuShow(menu); // may not need this.
                return;

            } 

        });

        return menu;

    }

}
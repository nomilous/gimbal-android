package nomilous.gimbal;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.HashMap;

public class MenuAction {

    public String label;
    public String description;
    public boolean enabled = true;

    public MenuAction( String label, String description ) {
        this.label = label;
        this.description = description;
    }

    public MenuAction( String label, String description, boolean enabled ) {
        // MenuAction( label, description ); // No? ...
        this.label = label;
        this.description = description;
        this.enabled = enabled;
    }

    //
    // Render this MenuAction into the layout
    //

    public void render( 

        Chain chain

    ) {

        TextView view = new TextView( chain.layout.getContext() );
        view.setText( label );

        int width = 100;
        int height = 40; // TODO: ensure it fits..

        LayoutParams layoutParams = new LayoutParams(width, height);
        // TODO: span positions proportionally (pending Anchors())
        layoutParams.setMargins(chain.left, chain.top, 0, 0);
        view.setLayoutParams(layoutParams);

        
        if( enabled ) view.setTextColor( chain.config.enabledColour );
        else view.setTextColor( chain.config.disabledColour );

        view.setTypeface( chain.config.font );

        chain.layout.addView( view );

        //
        // keep reference to each each view and layout
        //

        chain.views.put( label, view );
        chain.params.put( label, layoutParams );

    }

}

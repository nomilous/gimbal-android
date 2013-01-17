package nomilous.gimbal.menu;

import nomilous.gimbal.menu.MenuActionGroup.Config;
import nomilous.Util;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.util.Hashtable;


//
// Controls position of insertion of Views along a Path (Chain)
//

public class MenuChain {

    public Config config;
    public RelativeLayout layout;
    public Hashtable<String,TextView> views;
    public Hashtable<String,LayoutParams> params;
    public int left;
    public int top;
    private int leftSpan = 0;
    private int topSpan = 0;

    public MenuChain( 

        
        Config config,
        Hashtable<String,TextView> views,        // maintain a list of views
        Hashtable<String,LayoutParams> params    // maintain a list of their layouts

    ) {

        this.config = config;
        this.views  = views;
        this.params = params; 

    }

    public void set( RelativeLayout layout, int nodeCount ) {

        left = config.chainStart.x();
        top  = config.chainStart.y();

        //
        // Distance between each item in the chain.
        // 
        // For now the chain is a straight line but being
        // that the point is to allow the user to position
        // the menu to comfort - it will be desirable to
        // enable a bezier control to curve the chain to
        // match their thumb's reach arc.
        //

        leftSpan = (config.chainEnd.x() - left) / (nodeCount + 1);
        topSpan = (config.chainEnd.y() - top) / (nodeCount + 1);

        Util.debug( String.format(

            "Set chain left:%d leftSpan:%d top:%d topSpan:%d ", 
            left, leftSpan, top, topSpan

        ));

        this.layout = layout;

    }

    public MenuChain next() {

        left += leftSpan; 
        top += topSpan;

        return this;

    }

}

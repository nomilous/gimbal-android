package nomilous.gimbal;
import nomilous.gimbal.MenuActionSet.Config;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.util.HashMap;


//
// Controls position of insertion of Views along a Path (Chain)
//

public class Chain {

    public Config config;
    public RelativeLayout layout;
    public HashMap<String,TextView> views;
    public HashMap<String,LayoutParams> params;
    public int left = 100;
    public int top  = 0;

    public Chain( 

        
        Config config,
        HashMap<String,TextView> views,        // maintain a list of views
        HashMap<String,LayoutParams> params    // maintain a list of their layouts

    ) {

        this.config = config;
        this.views  = views;
        this.params = params; 

    }

    public void set( RelativeLayout layout ) {

        top = 0;
        this.layout = layout;

    }

    public Chain next() {

        top += 50;
        return this;

    }

}

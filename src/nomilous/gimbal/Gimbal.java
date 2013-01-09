package nomilous.gimbal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import nomilous.Util;

public class Gimbal extends Activity {

    private RelativeLayout view;
    private OnClickListener textClickListener;

    private final String TOGGLE_MENU = "menu";

    private int on;
    private int off;

    private TextView menu;
    private RelativeLayout.LayoutParams menuParams;
    private boolean menuActive = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gimbal);

        on = getResources().getColor(R.color.on);
        off = getResources().getColor(R.color.off);


    }

    @Override
    public void onStart() {

        super.onStart();

        textClickListener = initTextClickListener();

        menu = (TextView) findViewById(R.id.menu);
        menu.setText( TOGGLE_MENU );
        menu.setOnClickListener( textClickListener );
        menuParams = new RelativeLayout.LayoutParams(40, 100);
        menuParams.setMargins(3, -3, 0, 0);
        menu.setLayoutParams(menuParams);


        //
        // Test.. . (really should look into test harness)
        //

        MenuActionSet actions = new MenuActionSet();
        actions.add( new MenuAction( "connect", "Connects a viewport" ) );
        actions.add( new MenuAction( "disconnect", "Disconnects all viewports" ) );

        MenuAction exit = new MenuAction( "exit", "Exits the app." );
        actions.add( exit );

        actions.remove( exit );
        actions.remove( "disconnect" );
        actions.remove( "connect" );


    }

    private void toggleMenu() {

        if( menuActive ) {

            menu.setTextColor(off);
            menuActive = false;
            return;

        }

        menu.setTextColor(on);
        menuActive = true;
        return;

    }


    private OnClickListener initTextClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = "";

                try {

                    text = ((TextView)v).getText().toString();

                } catch (Exception e) {

                    Util.error(e.toString());
                    return;

                }

                if( text.equals( TOGGLE_MENU ) ) toggleMenu(); 

                else Util.warn( "Huh?" ); 

            }
        };
    }

}

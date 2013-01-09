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

    private MenuActionSet mainMenu;

    private final String TOGGLE_MENU = "menu";

    private int on;
    private int off;

    private RelativeLayout mainLayout;
    private TextView menuActivate;
    private RelativeLayout.LayoutParams menuActivateParams;
    private boolean menuActive = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gimbal);

        on = getResources().getColor(R.color.on);
        off = getResources().getColor(R.color.off);

        MenuActionSet.Config mainMenuConfig = new MenuActionSet.Config();
        mainMenuConfig.enabledColour = getResources().getColor(R.color.enabledAction);
        mainMenuConfig.disabledColour = getResources().getColor(R.color.disabledAction);

        mainMenu = new MenuActionSet( mainMenuConfig );

        boolean enabled = false; 
        mainMenu.add( new MenuAction( "connect", "Connect a viewport." ) );
        mainMenu.add( new MenuAction( "disconnect", "Disconnect all viewports.", enabled ) );
        mainMenu.add( new MenuAction( "help", "Toggle tooltips." ) );
        mainMenu.add( new MenuAction( "exit", "Exit the app." ) );

    }

    @Override
    public void onStart() {

        super.onStart();

        textClickListener = initTextClickListener();

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        menuActivate = (TextView) findViewById(R.id.menuActivate);
        menuActivate.setText( TOGGLE_MENU );
        menuActivate.setOnClickListener( textClickListener );
        menuActivateParams = new RelativeLayout.LayoutParams(40, 100);
        menuActivateParams.setMargins(3, -3, 0, 0);
        menuActivate.setLayoutParams(menuActivateParams);

    }

    private void toggleMenu() {

        if( menuActive ) {

            mainMenu.hide();
            menuActivate.setTextColor(off);
            menuActive = false;
            return;

        }

        mainMenu.show( mainLayout );
        menuActivate.setTextColor(on);
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

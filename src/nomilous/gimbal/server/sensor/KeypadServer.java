package nomilous.gimbal.server.sensor;

import nomilous.gimbal.client.SensorSubscriber;
import nomilous.Util;

import android.content.Context;
import android.view.View;

public class KeypadServer {

    private SensorSubscriber subscriber;
    private Context appContext;
    private boolean active = false;
    private boolean on = false;

    public KeypadServer( Context appContext, Object subscriber ) { 

        this.subscriber = (SensorSubscriber) subscriber;
        this.appContext = appContext;

    }

    public void startServer() { active = true; }

    public void stopServer() { active = false; }

    public void toggleKeypad() {

        // java... (LOL) if( on ) return stopKeypad();
        if( on ) { 

            stopKeypad();
            return;

        }
        startKeypad();

    }

    public void startKeypad() { 

        Util.info("Keypad ON");
        on = true;

    }

    public void stopKeypad() { 

        Util.info("Keypad OFF");
        on = false;

    }

}

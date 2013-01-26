package nomilous.gimbal.viewport;

import nomilous.Util;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.GimbalUplink.Event;
import nomilous.gimbal.uplink.*;

import android.content.Context;


public class ViewportController extends Uplink 

    implements

        GimbalEvent.Subscriber,
        GimbalUplink.Handler

{

    public static class Config {

        public String pending;

    }

    private ViewportEventHandler viewportEventHandler;


    public ViewportController(Context context,  GimbalEvent.Publisher publisher, ViewportEventHandler viewportEventHandler) {
        super(context, publisher);
        this.viewportEventHandler = viewportEventHandler;
        publisher.subscribe(this);
    }

    @Override
    public void onTouchEvent( GimbalEvent.Touch event ) {

        Util.debug( "Pending send to server... " + event.toString() );

    }


    // @Override
    // public void onStartClient(Object... payload) {


    // }

    // @Override
    // public void onRegisterController( RegisterControllerOkPayload payload ) {

    //     eventHandler.onViewportRegistered( payload.viewport );

    // }

    // @Override
    // public void onReleaseController( ReleaseControllerOkPayload payload ) {

    //     for( int i = 0; i < payload.viewports.length; i++ )

    //         eventHandler.onViewportReleased( payload.viewports[i] );

    // }

    @Override
    public boolean onClientStart( Event.ClientStart payload ) {
        return true;
    }

    @Override
    public Event.RegisterController getRegisterControllerPayload() {
        Event.RegisterController payload = new Event.RegisterController();
        payload.input_cube = viewportEventHandler.getInputCube();
        return payload;
    }

}

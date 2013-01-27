package nomilous.gimbal.viewport;

import nomilous.Util;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.GimbalUplink.Event;
import nomilous.gimbal.uplink.*;

import android.content.Context;
import java.util.HashMap;


public class ViewportController extends Uplink

    implements

        GimbalEvent.Subscriber,
        GimbalUplink.Handler

{

    private HashMap viewports = new HashMap<String,Viewport>();

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

        if( !running() ) return;

        socket.emit( 

            Event.VIEWPORT_BROADCAST,
            new Event.ViewportBroadcast( Event.TOUCH, event) 

        );

    }

    @Override
    public void onRegisterControllerOk( Event.RegisterControllerOk payload ) {

        viewportEventHandler.onViewportRegistered( (Viewport) payload.viewport );

        active = true;

    }

    @Override
    public void onReleaseControllerOk( Event.ReleaseControllerOk payload ) {

        for( int i = 0; i < payload.viewports.length; i++ )

            viewportEventHandler.onViewportReleased( payload.viewports[i] );

        active = false;

    }


    @Override
    public Event.RegisterController doClientStart( Event.ClientStart payload ) {
        viewports.put( 
            primaryViewportID, 
            new Viewport(primaryViewportID, true)
        );
        return getRegisterControllerPayload();
    }


    public Event.RegisterController getRegisterControllerPayload() {
        Event.RegisterController payload = new Event.RegisterController();
        payload.input_cube = viewportEventHandler.getInputCube();
        payload.viewport = (Viewport) viewports.get( primaryViewportID );
        return payload;
    }

}

package nomilous.gimbal.uplink;

import nomilous.Util;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.GimbalEvent;
//import nomilous.gimbal.uplink.GimbalUplink.Protocol;
//import nomilous.gimbal.uplink.*;

import nomilous.gimbal.viewport.ViewportEventHandler;
import nomilous.gimbal.viewport.ViewportController;
import nomilous.gimbal.viewport.Viewport;

//https://github.com/Gottox/socket.io-java-client
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import android.app.Activity;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;


public abstract class Uplink extends GimbalEvent.Server

    implements GimbalUplink.Handler {

    protected final SocketIO socket = new SocketIO();;
    protected String primaryViewportID;  // TODO: move into GimbalViewport.Controller

    public Uplink(Context context,  GimbalEvent.Publisher publisher) {
        super(context, publisher);
    }

    final public void connect(final String uri, final String viewportID) {
        primaryViewportID = viewportID;

        try {
            doConnect(uri, viewportID);
        }

        catch( java.net.MalformedURLException x ) {}
    }

    final public void disconnect(String viewportID) {
        doDisconnect(viewportID);
    }



    //public abstract void onStartClient(Object... payload);
    // final public void startClient(Object... payload) {
    //     RegisterControllerPayload message = getRegisterPayload();
    //     socket.emit( Protocol.REGISTER_CONTROLLER, message );
    //     onStartClient(payload);
    // }


    // public abstract void onRegisterController(RegisterControllerOkPayload payload);
    // final public void registerController(final Object... payload) {

    //     Util.debug(String.format(

    //         "Uplink.registerController() with payload %s",
    //         payload[0].toString()

    //     ));

    //     final Uplink messageHandler = this;
    //     //RegisterControllerOkPayload decoder = new RegisterControllerOkPayload();
    //     final PayloadContainer decoded = RegisterControllerOkPayload.decodeJSON( 
    //         payload[0].toString(), 
    //         RegisterControllerOkPayload.class
    //     );

    //     ((Activity) context).runOnUiThread( new Runnable() {

    //         //
    //         // Caused by: android.view.ViewRoot$CalledFromWrongThreadException: 
    //         //            Only the original thread that created a view hierarchy 
    //         //            can touch its views.
    //         //

    //         @Override
    //         public void run() {  
    //             messageHandler.onRegisterController( 
    //                 (RegisterControllerOkPayload) decoded
    //             );

    //         }

    //     });
        
    // }


    // public abstract void onReleaseController(ReleaseControllerOkPayload payload);
    // final public void releaseController(final Object... payload) {
    //     Util.debug(String.format(

    //         "Uplink.releaseController() with payload %s",
    //         payload[0].toString()

    //     ));

    //     final Uplink messageHandler = this;
    //     final PayloadContainer decoded = ReleaseControllerOkPayload.decodeJSON( 
    //         payload[0].toString(), 
    //         ReleaseControllerOkPayload.class
    //     );

    //     ((Activity) context).runOnUiThread( new Runnable() {

    //         @Override
    //         public void run() {
    //             messageHandler.onReleaseController( 
    //                 (ReleaseControllerOkPayload) decoded
    //             );

    //         }

    //     });

    // }

    private void doDisconnect(String viewportID) {

        //socket.emit(Protocol.RELEASE_CONTROLLER, new JSONObject());

    }

    private void doConnect(final String uri, final String viewportID)

        throws java.net.MalformedURLException {

        final Uplink uplink = this;

        socket.connect( uri, new IOCallback() {

            @Override
            public void onError(SocketIOException socketIOException) {
                Util.debug("an Error occured");
                socketIOException.printStackTrace();
            }

            @Override
            public void on(String event, IOAcknowledge ack, Object... payload) {
                Util.debug("Server triggered event '" + event + "'");
            }

            @Override public void onMessage(String data, IOAcknowledge ack) {}

            @Override
            public void onMessage(JSONObject json, IOAcknowledge ack) {}

            @Override
            public void onConnect() {
                Util.debug("Connection established");
            }

            @Override
            public void onDisconnect() {
                Util.debug("Connection terminated.");
            }

        });

        active = true;

    }

}

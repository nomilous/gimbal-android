package nomilous.gimbal.uplink;

import nomilous.Util;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.GimbalUplink.Protocol;
import nomilous.gimbal.uplink.*;
import nomilous.gimbal.viewport.GimbalViewport.Viewport;

//https://github.com/Gottox/socket.io-java-client
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.Point;
import org.json.JSONArray;
import org.json.JSONObject;


public abstract class Uplink extends GimbalEvent.Server

    implements GimbalUplink.Protocol {

    protected SocketIO client;
    private String primaryViewportID;  // TODO: move into GimbalViewport.Controller

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



    public abstract void onStartClient(Object... payload);
    final public void startClient(Object... payload) {

        try {

            RegisterControllerPayload message = getRegisterPayload();

            //
            // Extraneous... toString... to JSONObject,,, back down to string on 
            //               the send inside SocketIO
            // 
            //               TODO: make plan... 
            // 

            JSONObject jsonObject = new JSONObject(RegisterControllerPayload.encodeJSON(message));

            client.emit(

                Protocol.REGISTER_CONTROLLER, 
                jsonObject

            );

        } catch( org.json.JSONException x ) {}

        onStartClient(payload);

    }


    public abstract void onRegisterController(RegisterControllerOkPayload payload);
    final public void registerController(final Object... payload) {

        Util.debug(String.format(

            "Uplink.registerController() with payload %s",
            payload[0].toString()

        ));

        final Uplink messageHandler = this;
        final PayloadContainer decoded = RegisterControllerOkPayload.decodeJSON( 
            payload[0].toString(), 
            RegisterControllerOkPayload.class
        );

        ((Activity) context).runOnUiThread( new Runnable() {

            //
            // Caused by: android.view.ViewRoot$CalledFromWrongThreadException: 
            //            Only the original thread that created a view hierarchy 
            //            can touch its views.
            //

            @Override
            public void run() {  
                messageHandler.onRegisterController( 
                    (RegisterControllerOkPayload) decoded
                );

            }

        });
        
    }


    public abstract void onReleaseController(ReleaseControllerOkPayload payload);
    final public void releaseController(final Object... payload) {
        Util.debug(String.format(

            "Uplink.releaseController() with payload %s",
            payload[0].toString()

        ));

        final Uplink messageHandler = this;
        final PayloadContainer decoded = ReleaseControllerOkPayload.decodeJSON( 
            payload[0].toString(), 
            ReleaseControllerOkPayload.class
        );

        ((Activity) context).runOnUiThread( new Runnable() {

            @Override
            public void run() {
                messageHandler.onReleaseController( 
                    (ReleaseControllerOkPayload) decoded
                );

            }

        });

    }


    private RegisterControllerPayload getRegisterPayload() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int width  = 0;
        int height = 0;

        try {

            Point size = new Point();
            display.getSize(size);
            width  = size.x;
            height = size.y;

        } catch( java.lang.NoSuchMethodError x ) {

            //
            // targeting ICS, but running on Gingerbread
            // 
            // fallback
            //

            width  = display.getWidth();
            height = display.getHeight();

        }

        return new RegisterControllerPayload(
            new Viewport(primaryViewportID, true),
            width, height
        );

    }

    private void doDisconnect(String viewportID) {

        client.emit(Protocol.RELEASE_CONTROLLER, new JSONObject());

    }

    private void doConnect(final String uri, final String viewportID) 

        throws java.net.MalformedURLException {

        final Uplink uplink = this;

        client = new SocketIO();
        client.connect( uri, new IOCallback() {

            @Override
            public void onError(SocketIOException socketIOException) {
                Util.debug("an Error occured");
                socketIOException.printStackTrace();
            }

            @Override
            public void on(String event, IOAcknowledge ack, Object... payload) {


                if( event.equals( Protocol.CLIENT_START ) ) {

                    uplink.startClient(payload);
                    return;

                }

                else if( event.equals(Protocol.REGISTER_CONTROLLER_OK ) ) {

                    uplink.registerController(payload);
                    return;

                }

                else if( event.equals(Protocol.RELEASE_CONTROLLER_OK ) ) {

                    uplink.releaseController(payload);
                    return;

                }

                Util.debug("Server triggered event '" + event + "'");
            }

            @Override
            public void onMessage(String data, IOAcknowledge ack) {
                Util.debug("Server said: " + data);
            }

            @Override
            public void onMessage(JSONObject json, IOAcknowledge ack) {
                try {
                    Util.debug("Server said:" + json.toString(2));
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
            }

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

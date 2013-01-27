package nomilous.gimbal.uplink;

import nomilous.Util;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.GimbalUplink;

import nomilous.gimbal.viewport.ViewportEventHandler;
import nomilous.gimbal.viewport.ViewportController;
import nomilous.gimbal.viewport.Viewport;

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

    protected SocketIO socket;
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

    public void doDisconnectAll() {

        socket.emit(

            GimbalUplink.Event.RELEASE_CONTROLLER, 
            new GimbalUplink.Event.ReleaseController()

        );

    }

    private void doConnect(final String uri, final String viewportID)

        throws java.net.MalformedURLException {

        socket = new SocketIO();

        GimbalUplink.bindProtocol(socket, this, context);

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

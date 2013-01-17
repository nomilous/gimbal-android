package nomilous.gimbal.uplink;

import nomilous.Util;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.GimbalEvent;

import com.codebutler.android_websockets.SocketIOClient;
import android.content.Context;
import java.net.URI;

public class Uplink extends GimbalEvent.Server {

    private SocketIOClient client;

    public Uplink(Context context,  GimbalEvent.Publisher publisher) {
        super(context, publisher);
        publisher.subscribe(GimbalConfig.UPLINK);
    }

    public void connect(final String uri, final String viewportID) {
        client = new SocketIOClient(
            URI.create(uri),
            GimbalConfig.UPLINK
        );
        client.connect();
    }

}

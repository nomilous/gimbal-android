package nomilous.gimbal.uplink;

import nomilous.gimbal.GimbalEvent;
import com.codebutler.android_websockets.SocketIOClient;

public abstract class GimbalUplink

    implements

        GimbalEvent.Subscriber, 
        SocketIOClient.Handler 

        {} 

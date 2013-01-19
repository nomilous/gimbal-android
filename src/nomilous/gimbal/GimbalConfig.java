package nomilous.gimbal;

import nomilous.gimbal.feedback.*;
import nomilous.gimbal.uplink.*;
import nomilous.gimbal.client.*;

public class GimbalConfig {

    public static GimbalUplink     UPLINK = new DefaultUplink();
    public static GimbalGLRenderer GL_RENDERER = new DefaultGLRenderer();    

}

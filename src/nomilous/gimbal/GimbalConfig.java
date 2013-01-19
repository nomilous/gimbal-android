package nomilous.gimbal;

import nomilous.gimbal.feedback.*;
import nomilous.gimbal.uplink.*;

public class GimbalConfig {

    public static GimbalUplinkHandler UPLINK      = new DefaultUplinkHandler();
    public static GimbalGLRenderer    GL_RENDERER = new DefaultGLRenderer();    

}

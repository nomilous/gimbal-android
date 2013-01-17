package nomilous.gimbal;

import nomilous.gimbal.feedback.*;
import nomilous.gimbal.GimbalUplink;
import nomilous.gimbal.client.*;

public class GimbalConfig {

    public static int VISUAL_FEEDBACK = Option.NONE;

    public static final class Option {

        public static final int NONE   = 0;
        public static final int CAMERA = 1;
        public static final int GL10   = 2;

    }

    public static GimbalGL10Renderer GL10_FEEDBACK = new DefaultGL10();
    public static GimbalUplink       UPLINK        = new DefaultUplink();

}

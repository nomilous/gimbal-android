package nomilous.gimbal.uplink;

import nomilous.gimbal.viewport.GimbalViewport;

public final class RegisterControllerOkPayload extends PayloadContainer {

    public GimbalViewport.Viewport viewport;
    public GimbalViewport.Controller.Config config;

}

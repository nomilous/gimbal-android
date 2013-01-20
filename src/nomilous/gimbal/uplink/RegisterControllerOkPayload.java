package nomilous.gimbal.uplink;

import nomilous.gimbal.viewport.GimbalViewport;

import com.google.gson.Gson;

public final class RegisterControllerOkPayload extends PayloadContainer {

    protected Gson gson = new Gson();

    @Override
    public PayloadContainer decode(String json) {

        PayloadContainer payload = gson.fromJson(json, RegisterControllerOkPayload.class);
        return payload;

    }

    public GimbalViewport.Viewport viewport;
    public GimbalViewport.Controller.Config config;

    @Override
    public String toString() {

        return String.format(

            "RegisterControllerOk viewport.id = %s, config.pending = %s",
            viewport.getId(),
            config.pending

        );

    }

}

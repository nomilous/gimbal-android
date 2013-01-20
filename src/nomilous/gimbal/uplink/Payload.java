package nomilous.gimbal.uplink;

import nomilous.gimbal.viewport.GimbalViewport;

import org.json.JSONArray;

interface Decodable {

    //public abstract Payload decode(String json);   // ...later

    public abstract Payload.Container decode(JSONArray json);

}

public class Payload {

    public static class Container

        implements Decodable {

            public Payload.Container decode(JSONArray json) {

                return new Payload.Container();

            }

        }

    public static final class RegisterControllerOk extends Container {

        public GimbalViewport.Viewport viewport;
        public GimbalViewport.Controller.Config config;

    }

}


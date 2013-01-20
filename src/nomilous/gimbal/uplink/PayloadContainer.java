package nomilous.gimbal.uplink;

import com.google.gson.Gson;

interface Decodable {

    //public abstract Payload decode(String json);   // ...later

    public abstract PayloadContainer decode(String json, Class klass);

}

public abstract class PayloadContainer

    implements Decodable {

        private Gson gson = new Gson();

        public PayloadContainer decode(String json, Class klass) {
            
            PayloadContainer payload = (PayloadContainer) gson.fromJson(json, klass);
            return payload;

        }

    }

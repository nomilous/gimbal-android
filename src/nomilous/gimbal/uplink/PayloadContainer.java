package nomilous.gimbal.uplink;

interface Decodable {

    //public abstract Payload decode(String json);   // ...later

    public abstract PayloadContainer decode(String json);

}

public abstract class PayloadContainer

    implements Decodable {

        public abstract PayloadContainer decode(String json);
        public abstract String toString();


    }

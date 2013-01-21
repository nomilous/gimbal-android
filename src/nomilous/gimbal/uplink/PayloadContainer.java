package nomilous.gimbal.uplink;

import nomilous.Util;

import com.google.gson.Gson;


public class PayloadContainer {

        public static PayloadContainer decodeJSON(String json, Class klass) {

            Gson gson = new Gson();
            PayloadContainer payload = (PayloadContainer) gson.fromJson(json, klass);
            return payload;

        }

        public static String encodeJSON(PayloadContainer payload) {

            Gson gson = new Gson();
            String json = gson.toJson(payload);
            Util.debug("SEND:" + json);

            return json;
        }

    }

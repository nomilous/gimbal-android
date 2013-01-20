package nomilous.gimbal.uplink;

import org.json.JSONArray;
//import org.json.JSONObject;

public class GimbalUplink {

    public static interface Protocol {

        /*
         *
         * TODO: Replace 'string' event codes with ints known to
         *       both sides of the connection.
         * 
         * TODO: Replace with binary over raw socket (where possible).
         * 
         */


        //
        // START_CLIENT (handshake)
        //  
        //  - This event initiates the application layer handshake.
        // 
        //  - It is sent by the server immediately after the
        //    socket connection is established.
        //               
        //

        public final String CLIENT_START = "event:client:start";
        public abstract void startClient(JSONArray payload);
        public abstract void onStartClient(JSONArray payload);


        
        //
        // REGISTER_CONTROLLER (handshake response)
        // 
        //  - As response to START_CLIENT
        // 
        //  - Sends payload:
        //  
        //       - Primary Viewport ID
        // 
        //       - XYZ Dimensions of gesture sensitive region 
        //         of tablet/phone/kinect/unknown_future_thing
        //
        //       - (PLACEHOLDER) Authentication
        //  
        //       - (PLACEHOLDER) Supported controller modes
        //
        //
        //    eg. { 
        //            "event:register:controller" : [{  <-------------- TODO: is this array really necessary?
        //
        //                 "primary_viewport":"NEsGCsB_K3cmrrzd3B8q",
        //                 "input_cube":[640,400,0]
        //
        //             }]
        //        }
        // 

        public final String REGISTER_CONTROLLER = "event:register:controller";



        
        
        //
        // REGISTER_CONTROLLER_OK (handshake complete)
        //
        //  - Server authorizes this controler to proceed.
        //
        //  - Receives payload: 
        // 
        //        - (PLACEHOLDER) 
        //

        public final String REGISTER_CONTROLLER_OK = "event:register:controller:ok";
        public abstract void registerController(JSONArray payload);
        public abstract void onRegisterController(JSONArray payload);


    }

}


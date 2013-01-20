package nomilous.gimbal.uplink;

import org.json.JSONArray;
//import org.json.JSONObject;

public class GimbalUplink {

    public static interface Protocol {

        public static class Payload {

        }

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
        //            "event:register:controller" : { 
        //
        //                 "primary_viewport":"NEsGCsB_K3cmrrzd3B8q",
        //                 "input_cube":[640,400,0]     <-------------- NOTE: XYZ, 
        //
        //                                                                    width,height,depth 
        //
        //                                                                    Has no units.
        // 
        //                                                                    Used to generate a scaling factor for
        //                                                                    upsampling input to suit the frustum 
        //                                                                    size of the viewport being controlled.
        //                                                                    
        //
        //             }
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
        //        - The Viewport now being controlled
        //        - Configuration for the controller
        // 
        //    eg. {
        // 
        //          "event:register:controller:ok": {
        //            
        //                "viewport": {
        //                   
        //                    "id" : "NEsGCsB_K3cmrrzd3B8q",
        //                    "primary" : true
        //                
        //                },
        //               
        //                "config": {
        // 
        //                    "pending": "pending"
        //              
        //                }
        //        
        //          }
        //     
        //    }
        //

        public final String REGISTER_CONTROLLER_OK = "event:register:controller:ok";
        public abstract void registerController(JSONArray payload);
        public abstract void onRegisterController(JSONArray payload);


        //
        // RELEASE_CONTROLLER
        //
        //  - To initiate the release of a controlled viewport
        //
        //  - Sends payload:
        //
        //     - Viewport ID         (the viewport being released)
        //     - Primary Viewport ID (the controllers current primary viewport)
        //
        //       Both ID strings should be the same for the case of disconnecting
        //       the primary viewport. 
        // 
        //     eg. {
        //              "event:release:controller" : {
        //
        //                   "viewport_id": "NEsGCsB_K3cmrrzd3B8q",
        //                   "primary_viewport":"NEsGCsB_K3cmrrzd3B8q",
        //
        //               }
        //         }
        //

        public final String RELEASE_CONTROLLER = "event:release:controller";


        //
        // RELEASE_CONTROLLER_OK
        // 
        //  - Server reports the release succeeded
        //    

        public final String RELEASE_CONTROLLER_OK = "event:release:controller:ok";
        public abstract void releaseController(JSONArray payload);
        public abstract void onReleaseController(JSONArray payload);



    }

}


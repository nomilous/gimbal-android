package nomilous.gimbal.uplink;


public class GimbalUplink {


    public static final class Event {



        public static final String CLIENT_START = "event:client:start";
        public static final class ClientStart implements IOArg {

            // no payload

        }



        public static final String REGISTER_CONTROLLER = "event:register:controller";
        public static final class RegisterController implements IOArg {

            public int[] input_cube;
            public GimbalViewport.Viewport viewport;

        }



        public static final String REGISTER_CONTROLLER_OK = "event:register:controller:ok";
        public static final class RegisterControllerOk implements IOArg {

            public GimbalViewport.Viewport viewport;
            public GimbalViewport.Controller.Config config;

        }



        public static final String RELEASE_CONTROLLER = "event:release:controller";
        public static final class RegisterController implements IOArg {

            // no payload 

        }



        public static final String RELEASE_CONTROLLER_OK = "event:release:controller:ok";
        public static final class ReleaseControllerOk implements IOArg {

            public GimbalViewport.Viewport[] viewports;

        }
        

    }

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

        // public final String CLIENT_START = "event:client:start";
        // public abstract void startClient(Object... payload);
        // public abstract void onStartClient(Object... payload);


        
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
        //                 "viewport": {
        //                     "id": NEsGCsB_K3cmrrzd3B8q",
        //                     "primary": true
        //                 },
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

        
        // public abstract void registerController(Object... payload);
        // public abstract void onRegisterController(RegisterControllerOkPayload payload);


        //
        // RELEASE_CONTROLLER
        //
        //  - To initiate the disconnection of all controlled viewports and
        //    releases the controller
        //
        //  - Sends no payloadpayload:
        // 
        //     eg. {
        //              "event:release:controller" : {}
        //         }
        //

        


        //
        // RELEASE_CONTROLLER_OK
        // 
        //  - Server reports the release succeeded
        //    

        
        // public abstract void releaseController(Object... payload);
        // public abstract void onReleaseController(ReleaseControllerOkPayload payload);



    }

}


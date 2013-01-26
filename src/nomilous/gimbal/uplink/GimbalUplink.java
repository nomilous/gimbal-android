package nomilous.gimbal.uplink;

import nomilous.gimbal.viewport.ViewportEventHandler;
import nomilous.gimbal.viewport.ViewportController;
import nomilous.gimbal.viewport.Viewport;
import nomilous.Util;

import android.app.Activity;
import android.content.Context;
import io.socket.SocketIO;
import io.socket.IOAcknowledge;
import io.socket.IOEvent;
import io.socket.IOArg;


public class GimbalUplink {


    public static final class Event {



        public static final String CLIENT_START = "event:client:start";
        public static final class ClientStart implements IOArg {

            // no payload

        }



        public static final String REGISTER_CONTROLLER = "event:register:controller";
        public static final class RegisterController implements IOArg {

            public int[] input_cube;
            public Viewport viewport;

        }



        public static final String REGISTER_CONTROLLER_OK = "event:register:controller:ok";
        public static final class RegisterControllerOk implements IOArg {

            public Viewport viewport;
            public ViewportController.Config config;

        }



        public static final String RELEASE_CONTROLLER = "event:release:controller";
        public static final class ReleaseController implements IOArg {

            // no payload 

        }



        public static final String RELEASE_CONTROLLER_OK = "event:release:controller:ok";
        public static final class ReleaseControllerOk implements IOArg {

            public Viewport[] viewports;

        }

    }

    public interface Handler {

        boolean onClientStart( Event.ClientStart payload );
        boolean onRegisterController( Event.RegisterControllerOk payload );
        Event.RegisterController getRegisterControllerPayload();
        void disconnectAll();

    }
                                                                                   /* 
                                                                                    * 
                                                                                    * final context!
                                                                                    * 
                                                                                    * sounds like a bug waiting to happen
                                                                                    *
                                                                                    * also sounds like a song.
                                                                                    * 
                                                                                    */  
    public static void bindProtocol( final SocketIO socket, final Handler handler, final Context context ) {

        socket.when( 

            Event.CLIENT_START, Event.ClientStart.class 

        ).then(  

            new IOEvent.Handler() {

                @Override public void handle( String event, IOAcknowledge ack, Object... args ) {

                    if( handler.onClientStart( (Event.ClientStart) args[0] ) ) 

                        socket.emit( 

                            Event.REGISTER_CONTROLLER, 
                            handler.getRegisterControllerPayload()

                        );

/*                  />                                                         */

                }

            }

        );

        socket.when( 

            Event.REGISTER_CONTROLLER_OK, Event.RegisterControllerOk.class

        ).then( 

            new IOEvent.Handler() {

                @Override public void handle( String event, IOAcknowledge ack, final Object... args ) {

                    //
                    // this callback touches the ui View 
                    // (must therefore run on the main thread) 
                    //

                    ((Activity) context).runOnUiThread( new Runnable() {

                        @Override
                        public void run() {

                            handler.onRegisterController( 

                                (Event.RegisterControllerOk) args[0] 

                            );

                        }

                    });

                }

            }

        );

    }

}


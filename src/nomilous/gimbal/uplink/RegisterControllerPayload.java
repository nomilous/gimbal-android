
package nomilous.gimbal.uplink;

import nomilous.gimbal.viewport.GimbalViewport.Viewport;
import io.socket.IOArg;

public final class RegisterControllerPayload 

    implements IOArg {

        public int[] input_cube;
        public Viewport viewport;

        public RegisterControllerPayload( Viewport viewport, int width, int height ) {

            this.viewport = viewport;
            input_cube = new int[3];
            input_cube[0] = width;
            input_cube[1] = height;
            input_cube[2] = 0;       // touchpad has no depth

        }

    }

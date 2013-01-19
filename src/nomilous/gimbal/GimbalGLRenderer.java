package nomilous.gimbal;

import nomilous.gimbal.menu.Menu;
import android.opengl.GLSurfaceView;

public abstract class GimbalGLRenderer

    implements 

        GLSurfaceView.Renderer,
        GimbalEvent.Subscriber,
        Menu.SelectionHandler

        {}

package nomilous;

import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import android.view.Gravity;


public class Util {

    //
    // $ adb logcat | grep =====
    //

    public static void todo( String message ) {

        Log.i( "=====TODO", message );

    }

    public static void debug( String message ) {

        Log.d( "=====DEBUG", message );

    }

    public static void info( String message ) {

        Log.i( "=====INFO", message );

    }

    public static void warn( String message ) {

        Log.w( "=====WARN", message );

    }

    public static void error( String message ) {

        Log.e( "=====ERROR", message );

    }

    public static void messageUser( Context context, String msg ) {

        Toast toast = Toast.makeText(

            context, msg, Toast.LENGTH_SHORT

        );

        toast.setGravity( Gravity.CENTER, 0, 0 );

        toast.show();

    }

}

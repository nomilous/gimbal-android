package nomilous.gimbal.server.sensor;

import nomilous.Util;
import nomilous.gimbal.client.SensorSubscriber;

/*
 * 
 * REQUIRES:
 * 
 * AndroidManifest.xml
 * 
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 * 
 */

import android.os.Bundle;
import android.content.Context;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;


public class LocationServer  {

    private final int eventCode = SensorSubscriber.GPS_LOCATION_UPDATE;
    //private SensorSubscriber subscriber;

    private static LocationManager locationManager = null;
    private Context appContext;
    private boolean active = false;
    
    public LocationServer( Context appContext, Object subscriber ) {

        //this.subscriber = (SensorSubscriber) subscriber;
        this.appContext = appContext;

    }

    public void startServer() {

        getLocationManager( appContext );

        if( ! ensureGPSEnabled() ) {

            Util.messageUser( appContext, "no GPS or not enabled" );
            return;

        }

        //
        // send last known location 
        //
        // updateLocation( 
        //     locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER )
        // );

        //
        // register listener for location updates
        //

        locationManager.requestLocationUpdates( 

            LocationManager.GPS_PROVIDER,
            10000,  // Every 10 seconds
            10,     // Or every 10 meters 
            listener

        );

        active = true;

    }

    public void stopServer() {

        if( !active ) return;

        getLocationManager( appContext );
        locationManager.removeUpdates( listener );

        active = false;

    }

    private void getLocationManager( Context appContext ) {

        locationManager = (LocationManager) appContext.getSystemService(

            Context.LOCATION_SERVICE

        );

    }

    private boolean ensureGPSEnabled() {

        final boolean gpsEnabled = locationManager.isProviderEnabled(

            LocationManager.GPS_PROVIDER

        );

        //
        // Todo: Launch enable gps dialog fragment
        //

        return gpsEnabled;

    }


    private void updateLocation( Location location ) {

        // this.subscriber.onSensorEvent( 

        //     SensorSubscriber.GPS_LOCATION_UPDATE, 
        //     location 

        // );

    }

    private final LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            //Util.info( "LocationServer listener.onLocationChanged: " + 
            //    location.getLatitude() + " " + location.getLongitude() );

            updateLocation( location );

        }

        @Override
        public void onProviderDisabled(String provider) {

            //Util.info( "LocationServer listener.onProviderDisabled: " + provider );

            //
            // TODO: Handle disabled Location Service
            //

        }

        @Override
        public void onProviderEnabled(String provider) {

            //Util.info( "LocationServer listener.onProviderEnabled: " + provider );

            //
            // TODO: Handle enabled Location Service
            //

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            //Util.info( "LocationServer listener.onStatusChanged: " + provider + " " + status );

            //
            // TODO: Handle whatever this is.
            // 
            //       also: Find out what it is...
            //

        }

    };

}

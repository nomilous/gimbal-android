// package nomilous.gimbal.server;

// import nomilous.gimbal.client.Subscriber;
// import nomilous.gimbal.server.sensor.*;

// import android.os.Handler;
// import android.content.Context;
// import java.util.ArrayList;



// class Messenger implements Subscriber {

//     public void subscribe( Context appContext, int eventFlags, Subscriber newSubscriber ) {

//         initSubscribersArray();

//         //
//         // TODO: only start as necessary
//         //

//         initServer( appContext );

//         //
//         // TODO: check handler not already registered... 
//         // 


//         //
//         // Bitwise 'and' (masks to detect each flag)
//         //

//         int enableLocation      = ( eventFlags & Updates.GPS_LOCATION_UPDATE );
//         int enableAcceleration  = ( eventFlags & Updates.ACCELERATION_UPDATE );
//         int enableMagneticField = ( eventFlags & Updates.MAGNETIC_FIELD_UPDATE );
//         int enableRotation      = ( eventFlags & Updates.ROTATION_UPDATE );

//         if( enableLocation == Updates.GPS_LOCATION_UPDATE ) addSubscriber( 
//             Updates.GPS_LOCATION_UPDATE, newSubscriber
//         );

//         if( enableAcceleration == Updates.ACCELERATION_UPDATE ) addSubscriber( 
//             Updates.ACCELERATION_UPDATE, newSubscriber
//         );

//         if( enableMagneticField == Updates.MAGNETIC_FIELD_UPDATE ) addSubscriber( 
//             Updates.MAGNETIC_FIELD_UPDATE, newSubscriber
//         );

//         if( enableRotation == Updates.ROTATION_UPDATE ) addSubscriber( 
//             Updates.ROTATION_UPDATE, newSubscriber
//         );

//     }

//     public void unsubscribe( Context appContext, int eventFlags, Subscriber subscriber ) {

//         int enableLocation      = ( eventFlags & Updates.GPS_LOCATION_UPDATE );
//         int enableAcceleration  = ( eventFlags & Updates.ACCELERATION_UPDATE );
//         int enableMagneticField = ( eventFlags & Updates.MAGNETIC_FIELD_UPDATE );
//         int enableRotation      = ( eventFlags & Updates.ROTATION_UPDATE );

//         if( enableLocation == Updates.GPS_LOCATION_UPDATE ) removeSubscriber( 
//             Updates.GPS_LOCATION_UPDATE, subscriber
//         );

//         if( enableAcceleration == Updates.ACCELERATION_UPDATE ) removeSubscriber( 
//             Updates.ACCELERATION_UPDATE, subscriber
//         );

//         if( enableMagneticField == Updates.MAGNETIC_FIELD_UPDATE ) removeSubscriber( 
//             Updates.MAGNETIC_FIELD_UPDATE, subscriber
//         );

//         if( enableRotation == Updates.ROTATION_UPDATE ) removeSubscriber( 
//             Updates.ROTATION_UPDATE, subscriber
//         );

//     }

//     //
//     // private
//     //

//     @Override
//     public void onMessage( int eventCode, Object payload ) {

//         ArrayList<Subscriber> subscriberList = 

//             (ArrayList<Subscriber>) this.subscribers.get( eventCode );

//         for( Subscriber subscriber : subscriberList )

//             subscriber.onMessage( eventCode, payload );

//     }

//     private ArrayList<Object> servers = null;

//     private void initServer( Context appContext ) {

//         if( this.servers == null ) {

//             this.servers = new ArrayList<Object>();

//             this.servers.add( new LocationServer( appContext, this ) );
//             this.servers.add( new OrientationServer( appContext, this ) );

//         }

//     }

//     // private void initOrientationServer( Context appContext ) {

//     //     //
//     //     // OrientationServer provides 3 separate events
//     //     // Only maintain one instance
//     //     //

//     //     OrientationServer s = new OrientationServer( appContext, this );

//     //     this.servers.set( Updates.ACCELERATION_UPDATE, s );
//     //     this.servers.set( Updates.MAGNETIC_FIELD_UPDATE, s );
//     //     this.servers.set( Updates.ROTATION_UPDATE, s );

//     // }

//     private ArrayList<Object> subscribers = null;

//     private void initSubscribersArray() {

//         if( this.subscribers != null ) return;

//         this.subscribers = new ArrayList<Object>();

//         for( int i = 0; i < Updates.HIGHEST_EVENT_CODE; i++ ) {

//             this.subscribers.add( new ArrayList<Object>() );

//         }

//     }

//     private void addSubscriber( int eventCode, Subscriber newSubscriber ) {

//         ((ArrayList)
            
//              this.subscribers.get( eventCode ) 

//         ).add( newSubscriber );

//     }

//     private void removeSubscriber( int eventCode, Subscriber newSubscriber ) {

//         ((ArrayList)
            
//              this.subscribers.get( eventCode ) 

//         ).add( newSubscriber );

//     }

// }

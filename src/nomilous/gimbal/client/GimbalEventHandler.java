package nomilous.gimbal.client;

public interface GimbalEventHandler {

    public final String CONTROLLER_CONNECTED    = "event:client:start";
    public final String ASSIGN_PRIMARY_VIEWPORT = "event:register:controller:ok";

    public abstract void gimbalEvent( String event, Object payload );

}

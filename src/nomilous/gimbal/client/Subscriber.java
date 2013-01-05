package nomilous.gimbal.client;

public interface Subscriber {

    public abstract void onMessage( int eventCode, Object payload );

}

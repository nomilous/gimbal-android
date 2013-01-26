package nomilous.gimbal.viewport;

public interface ViewportEventHandler {

    public abstract void onViewportRegistered(Viewport viewport);
    public abstract void onViewportReleased(Viewport viewport);

}

package nomilous.gimbal.viewport;

public interface ViewportEventHandler {

    int[] getInputCube();
    void onViewportRegistered(Viewport viewport);
    void onViewportReleased(Viewport viewport);

}

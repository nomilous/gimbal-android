package nomilous.gimbal.viewport;

public class Viewport {

    private String id;
    private boolean primary = false;

    public Viewport(String id) {
        this.id = id;
    }

    public Viewport(String id, boolean primary) {
        this.id = id;
        this.primary = primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return String.format(
            "GimbalViewport.Viewport id:%s, primary:%s", 
            id,
            primary
        );
        
    }

}

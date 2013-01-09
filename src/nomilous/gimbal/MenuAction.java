package nomilous.gimbal;

public class MenuAction {

    public String label;
    public String description;
    public boolean enabled = true;

    public MenuAction( String label, String description ) {
        this.label = label;
        this.description = description;
    }

    public MenuAction( String label, String description, boolean enabled ) {
        // MenuAction( label, description ); // No? ...
        this.label = label;
        this.description = description;
        this.enabled = enabled;
    }

}

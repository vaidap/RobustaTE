package robustaTE;

public class RobustaManager {
    public static void main(String[] args) {
    	System.setProperty( "apple.awt.application.name", "RobustaTE" ); // set macOS global menu bar app name, must come before createNewWindow() or menu bar will show class name
    	createNewWindow();
    }
    
    public static void createNewWindow() {
    	RobustaMain newWindow = new RobustaMain();
    }
}

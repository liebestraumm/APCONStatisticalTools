/*
 * APCONApp.java
 */

package apcon;

import java.sql.SQLException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class APCONApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        try
        {
            show(new IUPrincipal(this));
        } 
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of APCONApp
     */
    public static APCONApp getApplication() {
        return Application.getInstance(APCONApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        DBCreator crear = new DBCreator();
        launch(APCONApp.class, args);
    }
}

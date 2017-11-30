package application;

import org.testfx.framework.junit.ApplicationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.*;
import org.testfx.api.FxToolkit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test_ExchangeRate extends ApplicationTest {
    /* The widgets of the GUI used for the tests. */
    ComboBox<Color> combobox;
    Parent mainNode;
    
    /* This operation comes from ApplicationTest and loads the GUI to test. */
    @Override
    public void start(Stage stage) throws Exception {
        mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("FXMLGUI.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
    
    @Before
    public void setUp () throws Exception {
    }

    @After
    public void tearDown () throws Exception {
      FxToolkit.hideStage();
    }
    
    @Test
    public void testEnglishInput () {
      clickOn("#txt_Input");
      write("This is a test!");
    }
    
    
}

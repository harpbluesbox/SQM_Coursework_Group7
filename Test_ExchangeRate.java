package application.Tests;

import org.testfx.framework.junit.ApplicationTest;

import application.Main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.*;
import org.testfx.api.FxToolkit;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    	TimeUnit.SECONDS.sleep(1);
    	FxToolkit.hideStage();
    }
    
    @Test
    public void testExchangeRateInput_LessThanZero() {
    	clickOn("#txt_Input");
    	write("-1.0");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lookup("#lbl_ExchangeRateInfo").<Label>query().getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    @Test
    public void testExchangeRateInput_NotNumerical() {
    	clickOn("#txt_Input");
    	write("this is not a number");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lookup("#lbl_ExchangeRateInfo").<Label>query().getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    @Test
    public void testExchangeRateInput_NumericalWithLetters() {
    	clickOn("#txt_Input");
    	write("This string contains the numbers 5, 7 and 8");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lookup("#lbl_ExchangeRateInfo").<Label>query().getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    @Test
    public void testExchangeRateInput_DecimalWithMoreThanOnePoint() {
    	clickOn("#txt_Input");
    	write("0.5.5");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lookup("#lbl_ExchangeRateInfo").<Label>query().getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    @Test
    public void testExchangeRateInput_Integer() {
    	clickOn("#txt_Input");
    	write("1");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lookup("#lbl_ExchangeRateInfo").<Label>query().getText();
    	assertNotEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    @Test
    public void testExchangeRateInput_Decimal() {
    	clickOn("#txt_Input");
    	write("0.56");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lookup("#lbl_ExchangeRateInfo").<Label>query().getText();
    	assertNotEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    @Test
    public void testExchangeRateInput_SameCountries() {
    	clickOn("#txt_Input");
    	write("1");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lookup("#lbl_ExchangeRateInfo").<Label>query().getText();
    	assertNotEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    @Test
    public void testInputComboBox_HasFiveCountries() {
    	assertEquals(lookup("#cbx_Input").<ComboBox<String>>query().getItems().size(), 5);
    }
    
    @Test
    public void testOutputComboBox_HasFiveCountries() {
    	assertEquals(lookup("#cbx_Output").<ComboBox<String>>query().getItems().size(), 5);
    }
    
    @Test
    public void testOutputComboBox_HasCorrectCountries() {
    	
    	//assertEquals(lookup("#cbx_Output").<ComboBox<String>>query().getItems(), );
    }
    
}

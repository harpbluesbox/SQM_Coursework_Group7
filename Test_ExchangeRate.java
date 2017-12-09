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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Test_ExchangeRate extends ApplicationTest {
    /* The widgets of the GUI used for the tests. */
    ComboBox<String> cbx_Input;
    ComboBox<String> cbx_Output;
    TextField txt_Input;
    TextField txt_Output;
    Label lbl_ExchangeRateInfo;
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
    	cbx_Input = lookup("#cbx_Input").<ComboBox<String>>query();
    	cbx_Output = lookup("#cbx_Output").<ComboBox<String>>query();
    	txt_Input = lookup("#txt_Input").<TextField>query();
    	txt_Output = lookup("#txt_Output").<TextField>query();
    	lbl_ExchangeRateInfo = lookup("#lbl_ExchangeRateInfo").<Label>query();
    }

    @After
    public void tearDown () throws Exception {
    	TimeUnit.SECONDS.sleep(1);
    	FxToolkit.hideStage();
    }
    
    /*
     tests that pressing the calculate button with no input, returns no output
     */
    @Test
    public void testExchangeRateInput_Empty() {
    	clickOn("#btn_CalculateExchangeRate");
    	String outputText = txt_Output.getText();
    	assertEquals(outputText, "");
    }
    
    /*
     tests that an input of less than 0 is rejected
     */
    @Test
    public void testExchangeRateInput_LessThanZero() {
    	clickOn("#txt_Input");
    	write("-1.0");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
     tests that an input of 0 returns an output of 0
     */
    @Test
    public void testExchangeRateInput_Zero() {
    	clickOn("#txt_Input");
    	write("0");
    	clickOn("#btn_CalculateExchangeRate");
    	String outputText = txt_Output.getText();
    	assertEquals(outputText, "0.00");
    }
    
    /*
     tests that non-numerical inputs are rejected
     */
    @Test
    public void testExchangeRateInput_NotNumerical() {
    	clickOn("#txt_Input");
    	write("abc");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
     tests that an input consisting of a combination of an integer 
     and non-numerical characters is rejected
     */
    @Test
    public void testExchangeRateInput_IntegerWithLetters() {
    	clickOn("#txt_Input");
    	write("1e1");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
    tests that an input consisting of a combination of a decimal 
    and non-numerical characters is rejected
    */
    @Test
    public void testExchangeRateInput_DecimalWithLetters() {
    	clickOn("#txt_Input");
    	write("0.1e");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
    tests that an input consisting of a decimal with more than one
    decimal point is rejected
    */
    @Test
    public void testExchangeRateInput_DecimalWithMoreThanOnePoint() {
    	clickOn("#txt_Input");
    	write("1..1");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
    tests that standard integer inputs are accepted
    */
    @Test
    public void testExchangeRateInput_Integer() {
    	clickOn("#txt_Input");
    	write("1");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertNotEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
    tests that standard decimal inputs are accepted
    */
    @Test
    public void testExchangeRateInput_Decimal() {
    	clickOn("#txt_Input");
    	write("0.56");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertNotEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
    tests that inputs consisting of a decimal with no number before its
    decimal point are rejected
    */
    @Test
    public void testExchangeRateInput_NoNumberBeforeDecimalPoint() {
    	clickOn("#txt_Input");
    	write(".1");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertNotEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
    tests that inputs consisting of a decimal with no number after its
    decimal point are rejected
    */
    @Test
    public void testExchangeRateInput_NoNumberAfterDecimalPoint() {
    	clickOn("#txt_Input");
    	write("1.");
    	clickOn("#btn_CalculateExchangeRate");
    	String labelText = lbl_ExchangeRateInfo.getText();
    	assertNotEquals(labelText, "An error occured! Input should be a real number only.");
    }
    
    /*
    tests that the input combobox has 5 countries in it
    */
    @Test
    public void testInputComboBox_HasFiveCountries() {
    	assertEquals(cbx_Input.getItems().size(), 5);
    }
    
    /*
    tests that the output combobox has 5 countries in it
    */
    @Test
    public void testOutputComboBox_HasFiveCountries() {
    	assertEquals(cbx_Output.getItems().size(), 5);
    }
    
    /*
     tests that the conversion from AUD to AUD is correct
    */
    @Test
    public void testAUDtoAUD() {
    	cbx_Input.getSelectionModel().select(0);
    	cbx_Output.getSelectionModel().select(0);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.00");
    }
    
    /*
    tests that the conversion from AUD to CAD is correct
   */
    @Test
    public void testAUDtoCAD() {
    	cbx_Input.getSelectionModel().select(0);
    	cbx_Output.getSelectionModel().select(1);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.97");
    }
    
    /*
    tests that the conversion from AUD to EUR is correct
   */
    @Test
    public void testAUDtoEUR() {
    	cbx_Input.getSelectionModel().select(0);
    	cbx_Output.getSelectionModel().select(2);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.64");
    }
    
    /*
    tests that the conversion from AUD to GBP is correct
   */
    @Test
    public void testAUDtoGBP() {
    	cbx_Input.getSelectionModel().select(0);
    	cbx_Output.getSelectionModel().select(3);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.58");
    }
    
    /*
    tests that the conversion from AUD to USD is correct
   */
    @Test
    public void testAUDtoUSD() {
    	cbx_Input.getSelectionModel().select(0);
    	cbx_Output.getSelectionModel().select(4);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.76");
    }
    
    /*
    tests that the conversion from CAD to AUD is correct
   */
    @Test
    public void testCADtoAUD() {
    	cbx_Input.getSelectionModel().select(1);
    	cbx_Output.getSelectionModel().select(0);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.03");
    }
    
    /*
    tests that the conversion from CAD to CAD is correct
   */
    @Test
    public void testCADtoCAD() {
    	cbx_Input.getSelectionModel().select(1);
    	cbx_Output.getSelectionModel().select(1);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.00");
    }
    
    /*
    tests that the conversion from CAD to EUR is correct
   */
    @Test
    public void testCADtoEUR() {
    	cbx_Input.getSelectionModel().select(1);
    	cbx_Output.getSelectionModel().select(2);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.67");
    }
    /*
    tests that the conversion from CAD to GBP is correct
   */
    @Test
    public void testCADtoGBP() {
    	cbx_Input.getSelectionModel().select(1);
    	cbx_Output.getSelectionModel().select(3);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.59");
    }
    
    /*
    tests that the conversion from CAD to USD is correct
   */
    @Test
    public void testCADtoUSD() {
    	cbx_Input.getSelectionModel().select(1);
    	cbx_Output.getSelectionModel().select(4);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.78");
    }
    
    /*
    tests that the conversion from EUR to AUD is correct
   */
    @Test
    public void testEURtoAUD() {
    	cbx_Input.getSelectionModel().select(2);
    	cbx_Output.getSelectionModel().select(0);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.55");
    }
    
    /*
    tests that the conversion from EUR to CAD is correct
   */
    @Test
    public void testEURtoCAD() {
    	cbx_Input.getSelectionModel().select(2);
    	cbx_Output.getSelectionModel().select(1);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.50");
    }
    
    /*
    tests that the conversion from EUR to EUR is correct
   */
    @Test
    public void testEURtoEUR() {
    	cbx_Input.getSelectionModel().select(2);
    	cbx_Output.getSelectionModel().select(2);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.00");
    }
    
    /*
    tests that the conversion from EUR to GBP is correct
   */
    @Test
    public void testEURtoGBP() {
    	cbx_Input.getSelectionModel().select(2);
    	cbx_Output.getSelectionModel().select(3);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.89");
    }
    
    /*
    tests that the conversion from EUR to USD is correct
   */
    @Test
    public void testEURtoUSD() {
    	cbx_Input.getSelectionModel().select(2);
    	cbx_Output.getSelectionModel().select(4);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.18");
    }
    
    /*
    tests that the conversion from GBP to AUD is correct
   */
    @Test
    public void testGBPtoAUD() {
    	cbx_Input.getSelectionModel().select(3);
    	cbx_Output.getSelectionModel().select(0);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.74");
    }
    
    /*
    tests that the conversion from GBP to CAD is correct
   */
    @Test
    public void testGBPtoCAD() {
    	cbx_Input.getSelectionModel().select(3);
    	cbx_Output.getSelectionModel().select(1);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.68");
    }
    
    /*
    tests that the conversion from GBP to EUR is correct
   */
    @Test
    public void testGBPtoEUR() {
    	cbx_Input.getSelectionModel().select(3);
    	cbx_Output.getSelectionModel().select(2);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.12");
    }
    
    /*
    tests that the conversion from GBP to GBP is correct
   */
    @Test
    public void testGBPtoGBP() {
    	cbx_Input.getSelectionModel().select(3);
    	cbx_Output.getSelectionModel().select(3);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.00");
    }
    
    /*
    tests that the conversion from GBP to USD is correct
   */
    @Test
    public void testGBPtoUSD() {
    	cbx_Input.getSelectionModel().select(3);
    	cbx_Output.getSelectionModel().select(4);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.32");
    }
    
    /*
    tests that the conversion from USD to AUD is correct
   */
    @Test
    public void testUSDtoAUD() {
    	cbx_Input.getSelectionModel().select(4);
    	cbx_Output.getSelectionModel().select(0);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.32");
    }
    
    /*
    tests that the conversion from USD to CAD is correct
   */
    @Test
    public void testUSDtoCAD() {
    	cbx_Input.getSelectionModel().select(4);
    	cbx_Output.getSelectionModel().select(1);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.28");
    }
    
    /*
    tests that the conversion from USD to EUR is correct
   */
    @Test
    public void testUSDtoEUR() {
    	cbx_Input.getSelectionModel().select(4);
    	cbx_Output.getSelectionModel().select(2);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.85");
    }
    
    /*
    tests that the conversion from USD to GBP is correct
   */
    @Test
    public void testUSDtoGBP() {
    	cbx_Input.getSelectionModel().select(4);
    	cbx_Output.getSelectionModel().select(3);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "0.76");
    }
    
    /*
    tests that the conversion from USD to USD is correct
   */
    @Test
    public void testUSDtoUSD() {
    	cbx_Input.getSelectionModel().select(4);
    	cbx_Output.getSelectionModel().select(4);
    	clickOn("#txt_Input");
    	write("1.00");
    	clickOn("#btn_CalculateExchangeRate");
    	assertEquals(txt_Output.getText(), "1.00");
    }
}

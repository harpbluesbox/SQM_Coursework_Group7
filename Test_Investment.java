package application;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

import java.time.Year;
import java.util.concurrent.TimeUnit;

public class Test_Investment extends ApplicationTest {

    ComboBox<Color> combobox;
    Parent mainNode;
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
	    	//TimeUnit.SECONDS.sleep(1);
	      FxToolkit.hideStage();
	    }
	    
	    /*
	     tests that investment amount field does not accept negative values (converts to positive)
	     and displays correct output
	    JUST BELOW MINIMUM 
	     */
	    @Test
	    public void test1_NegativeInputInvestmentAmount () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("-0.01");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("0.01", lookup("#txt_FutureValue").<TextField>query().getText());
	    }
	    
	    /*
	     tests that APR field does not accept negative values (converts to positive)
	     and displays correct output
	     JUST BELOW MINIMUM 
	     */
	    @Test
	    public void test2_NegativeInputAPR() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("-0.0001");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("1.00", lookup("#txt_FutureValue").<TextField>query().getText());
	    }
	    
	    /*
	     tests that Years field does not accept negative values (converts to positive)
	     and displays correct output
	     JUST BELOW MINIMUM 
	     */
	    
	    @Test
	    public void test3_NegativeInputYears() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("-0.1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("1.01", lookup("#txt_FutureValue").<TextField>query().getText());
	    }
	    
	    /*
	     tests that an error message is printed 
	     when the investment amount field is left empty
	     */
	    @Test
	    public void test4_EmptyInvestmentAmount () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    /*
	     tests that an error message is printed 
	     when the APR field is left empty
	     */
	    @Test
	    public void test5_EmptyInputAPR() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    /*
	     tests that an error message is printed 
	     when the Years field is left empty
	     */
	    @Test
	    public void test6_EmptyInputYears() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    /*
	     tests that investment amount field does not accept values with more than 2 decimal places 
	     (automatically prevents the user from entering more than 2 )
	     */ 
	    @Test
	    public void test7_DecimalPlaceLimitInvestmentAmount () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("0.111");
	    	assertEquals("0.11", lookup("#txt_InvestmentAmount").<TextField>query().getText());
	    }
	    
	    /*
	     tests that APR value does not accept values with more than 4 decimal places
	     */
	    @Test
	    public void test8_DecimalPlacesAPR() {
	    	clickOn("#txt_APR");
	    	write("0.111111");
	    	assertEquals("0.1111", lookup("#txt_APR").<TextField>query().getText());
	    }
	    
	    /*
	     tests that Years field does not accept decimal values (integers only)
	     */
	    @Test
	    public void test9_DecimalInputYears() {
	    	clickOn("#txt_Years");
	    	write("0.1");
	    	assertEquals("01", lookup("#txt_Years").<TextField>query().getText());
	    }
	    
	    
	    /*
	     tests that an error message is printed 
	     when invalid input (letters) is entered into the investment amount field
	     */
	    @Test
	    public void test10_LetterInputInvestmentAmount () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("abc");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    /*
	     tests that an error message is printed 
	     when invalid input (letters) is entered into the APR field
	     */
	    @Test
	    public void test11_LetterInputAPR () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("abc");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    /*tests that an error message is printed 
	     when invalid input (letters) is entered into the years field
	     */
	    @Test
	    public void test12_LetterInputYears () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("abc");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    /*
	     tests that when a mix of letters and numbers is typed in, only numbers is accepted 
	     in investment amount field
	     */
	    @Test
	    public void test13_ValidAndInvalidInputInvestmentAmount () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1e1");
	    	verifyThat("#txt_InvestmentAmount", NodeMatchers.hasText("11"));
	    }
	    
	    /*
	     tests that when a mix of letters and numbers is typed in, only numbers is accepted 
	     in APR field
	     */
	    @Test
	    public void test14_ValidAndInvalidInputAPR () {
	    	clickOn("#txt_APR");
	    	write("1e1");
	    	verifyThat("#txt_APR", NodeMatchers.hasText("11"));
	    }
	    
	    /*
	     tests that when a mix of letters and numbers is typed in, only numbers is accepted 
	     in Years field
	     */
	    @Test
	    public void test15_ValidAndInvalidInputYears () {
	    	clickOn("#txt_Years");
	    	write("1e1");
	    	verifyThat("#txt_Years", NodeMatchers.hasText("11"));
	    }
	    
	    /*
	     tests that an error message is printed 
	     when a decimal value without a "0" in front is entered into the investmentAmount field
	     */
	    @Test
	    public void test16_DecimalWithout0InfrontInputInvestmentAmount () {
	    	clickOn("#txt_InvestmentAmount");
	    	write(".1");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    /*
	     tests that an error message is printed 
	     when a decimal value without a "0" in front is entered into the APR field
	     */
	    @Test
	    public void test17_DecimalWithout0InfrontAPR () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write(".11");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    /*
	     tests that an error message is printed 
	     when a decimal value without a "0" in front is entered into the years field
	     */
	    @Test
	    public void test18_DecimalWithout0InfrontYears () {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write(".1");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("1.01"));
	    }
	   // Equivalence class partitioning
	    
	    // investment amount
	    // year 20
	    // APR<=50
	    
	    // test that error message is displayed when investment value is over the limit
	    @Test
	    public void  test19_InvestmentAmountTooLarge() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("100000000.00");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    //test that error message is displayed when APR value is over the limit
	    @Test
	    public void  test20_APRTooLarge() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("50.0001");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    //tests that error message is displayed when Years value is over the limit
	    @Test
	    public void  test21_YearsTooLarge() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("1");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("21");
	    	clickOn("#btn_CalculateInterest");
	    	assertEquals("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20", lookup("#lbl_InterestInfo").<Label>query().getText());
	    }
	    
	    //tests that correct output is displayed when largest investment value is used
	    @Test
	    public void test22_MaxInvestmentValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("99999999.99");
	    	clickOn("#txt_APR");
	    	write("2");
	    	clickOn("#txt_Years");
	    	write("2");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("104039999.99"));
	    }
	    
	    //tests that correct output is displayed when largest APR value is used
	    @Test
	    public void test23_MaxAPRValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("50");
	    	clickOn("#txt_APR");
	    	write("50");
	    	clickOn("#txt_Years");
	    	write("2");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("112.50"));
	    }
	    
	    //tests that correct output is displayed when largest APR value is used
	    @Test
	    public void test24_MaxYearsValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("50");
	    	clickOn("#txt_APR");
	    	write("20");
	    	clickOn("#txt_Years");
	    	write("20");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("1916.88"));
	    }
	    
	    //tests that correct output is displayed when largest investment value is used
	    @Test
	    public void test25_LowestInvestmentValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("0");
	    	clickOn("#txt_APR");
	    	write("10");
	    	clickOn("#txt_Years");
	    	write("10");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("0.00"));
	    }
	    
	    //tests that correct output is displayed when largest APR value is used
	    @Test
	    public void test26_LowestAPRValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("50");
	    	clickOn("#txt_APR");
	    	write("0");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("50.00"));
	    }
	    
	    //tests that correct output is displayed when largest APR value is used
	    @Test
	    public void test27_LowestYearsValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("50");
	    	clickOn("#txt_APR");
	    	write("20");
	    	clickOn("#txt_Years");
	    	write("0");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("50.00"));
	    }
	    
	    //tests that correct output is displayed when largest investment value is used
	    @Test
	    public void test28_BelowMaxInvestmentValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("99999999.98");
	    	clickOn("#txt_APR");
	    	write("2");
	    	clickOn("#txt_Years");
	    	write("2");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("104039999.98"));
	    }
	    
	    //tests that correct output is displayed when largest APR value is used
	    @Test
	    public void test29_BelowMaxAPRValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("50");
	    	clickOn("#txt_APR");
	    	write("49.9999");
	    	clickOn("#txt_Years");
	    	write("2");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("112.50"));
	    }
	    
	    //tests that correct output is displayed when largest APR value is used
	    @Test
	    public void test30_BelowMaxYearsValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("50");
	    	clickOn("#txt_APR");
	    	write("20");
	    	clickOn("#txt_Years");
	    	write("19");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("1597.40"));
	    }
	    
	    //tests that correct output is displayed when largest investment value is used
	    @Test
	    public void test31_NominalInvestmentValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("10000");
	    	clickOn("#txt_APR");
	    	write("1");
	    	clickOn("#txt_Years");
	    	write("1");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("10100.00"));
	    }
	    
	    //tests that correct output is when nominal APR value is used
	    @Test
	    public void test32_NominalAPRValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("10");
	    	clickOn("#txt_APR");
	    	write("26");
	    	clickOn("#txt_Years");
	    	write("2");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("15.88"));
	    }
	    
	    //tests that correct output is displayed when nominal APR value is used
	    @Test
	    public void test33_NominalYearsValue() {
	    	clickOn("#txt_InvestmentAmount");
	    	write("50");
	    	clickOn("#txt_APR");
	    	write("2");
	    	clickOn("#txt_Years");
	    	write("10");
	    	clickOn("#btn_CalculateInterest");
	    	verifyThat("#txt_FutureValue", NodeMatchers.hasText("60.95"));
	    }
	    
	    
}

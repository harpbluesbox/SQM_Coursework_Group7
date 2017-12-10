package application;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerC implements Initializable {
	
	@FXML TextField txt_Input, txt_Output;
	@FXML TextField txt_InvestmentAmount, txt_APR, txt_Years, txt_FutureValue;
	@FXML ComboBox<String> cbx_Input, cbx_Output;
	@FXML Button btn_CalculateExchangeRate, btn_CalculateInterest;
	@FXML Label lbl_ExchangeRateInfo, lbl_InterestInfo;
	
	Currency[] currencyList;
	
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1){
		currencyList = generateCurrencyList();
		populateComboBoxes();
		
		//adds a listener to the Exchange Rate input textbox so that invalid inputs are sanitised before they are used by the program
		txt_Input.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
		        if(!arg2.matches("\\d*(\\.\\d{0,2})?")) //regex defining an integer or decimal with up to 2 decimal places
		        {
		            txt_Input.setText(arg1);
		        }
			}});
		
		//adds a listener to the Investment Amount textbox so that invalid inputs are sanitised before they are used by the program
		txt_InvestmentAmount.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
		        if(!arg2.matches("\\d*(\\.\\d{0,2})?")) //regex defining an integer or decimal with up to 2 decimal places
		        {
		            txt_InvestmentAmount.setText(arg1);
		        }
			}});
			
		//adds a listener to the APR textbox so that invalid inputs are sanitised before they are used by the program
		txt_APR.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					// TODO Auto-generated method stub
			        if(!arg2.matches("\\d*(\\.\\d{0,4})?")) //regex defining an integer or decimal with up to 4 decimal places
			        {
			            txt_APR.setText(arg1);
			        }
				}
		});
		
		//adds a listener to the years textbox so that invalid inputs are sanitised before they are used by the program
		txt_Years.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
		        if(!arg2.matches("\\d*?")) //regex defining an integer
		        {
		            txt_Years.setText(arg1);
		        }
			}
	});
	}

	//function that populates both the input and output currency exchange combo-boxes with the correct currnecy strings
	public void populateComboBoxes() {
		ObservableList<String> x = cbx_Input.getItems();
		ObservableList<String> y = cbx_Output.getItems();
		
		//loops through list of currencies and adds them to comboboxes
		for (int i = 0; i < 5; i++) {  
			x.add(currencyList[i].getCurrencyName());
			y.add(currencyList[i].getCurrencyName());
		}
		
		//sets both comboboxes to first element by default
		cbx_Input.setValue(cbx_Input.getItems().get(0));
		cbx_Output.setValue(cbx_Output.getItems().get(0));
	}
	
	//generate an array which contains all 5 currency objects
	public Currency[] generateCurrencyList() {
		Currency[] currencyList = new Currency[5];
		currencyList[0] = new Currency("Australia(AUD)", new float[] {1.0f,0.97f,0.64f,0.58f, 0.76f});
		currencyList[1] = new Currency("Canada(CAD)", new float[] {1.03f,1.0f,0.67f,0.59f,0.78f});
		currencyList[2] = new Currency("European Union(EUR)", new float[] {1.55f,1.5f,1.0f, 0.89f,1.18f});
		currencyList[3] = new Currency("United Kingdom(GBP)", new float[] {1.74f,1.68f,1.12f,1.0f,1.32f});
		currencyList[4] = new Currency("United States(USD)", new float[] {1.32f, 1.28f, 0.85f, 0.76f, 1.0f});
		return currencyList;
	}
	
	//function to calculate the exchange rate from one currency to another
	private float CalculateExchangeRate(){
		float result = -1.0f;
		if(IsExchangeRateInputValid()){
			float rate = 0.0f;
			String inputCurrencyName = cbx_Input.getValue(); //get input currency
			String outputCurrencyName = cbx_Output.getValue(); //get output currency
			int fromIndex = GetIndexFromCurrencyName(inputCurrencyName);
			int toIndex = GetIndexFromCurrencyName(outputCurrencyName);
			
			if(fromIndex != -1 && toIndex != -1){
				rate = currencyList[fromIndex].getCurrencyRate()[toIndex]; //get the currency exchange rate multiplier
			}
			
			result = Float.parseFloat(txt_Input.getText()) * rate; //multiply the input amount by the exchange rate multiplier
		}
		return result; //return the new amount
	}
	
	//function to calculate the interest forecast
	private double CalculateInterest() {
		double result = -1.0f;
		if(IsInterestInputValid()) {
			double investmentAmount = Double.parseDouble(txt_InvestmentAmount.getText()); //get the investment amount
			double apr = Double.parseDouble(txt_APR.getText()); //get the APR
			double years = Double.parseDouble(txt_Years.getText()); //get the number of years of the investment
			result = investmentAmount;
			for(int i = 0; i < years; i++){
				result += ((result/100)*apr); //loop through the number of years, and times by the APR each year
			}
		}
		return result; //return the new amount
	}
	
	//function to test whether the exchange rate amount input is valid
	private boolean IsExchangeRateInputValid(){
		
		boolean result;
		
		String regex_OneDecimalPointOnly = "(\\d+(?:\\.\\d+)?)"; //regex for an integer or a decimal with one decimal point
		String val = txt_Input.getText(); //get the input amount
		
		result = val.matches(regex_OneDecimalPointOnly); //boolean value defining whether input is valid
		
		//test whether inputs is less than 9 digits long
		boolean isLessThan9Digits = false;
		if(Double.parseDouble(val) < 100000000.0f)
		{
			isLessThan9Digits = true;
		}
		
		return result && isLessThan9Digits;
	}
	
	//function to test whether the interest inputs are valid
	private boolean IsInterestInputValid(){
		
		boolean isOnlyNumbers = false;
		String regex_OneDecimalPointOnly = "(\\d+(?:\\.\\d+)?)"; //regex for an integer or a decimal with one decimal point
		String regex_IntegersOnly = "([0-9]+)"; //regex for an integer
		String val1 = txt_InvestmentAmount.getText(); //get investment amount
		String val2 = txt_APR.getText(); //get APR
		String val3 = txt_Years.getText(); //get number of years
		
		//boolean value showing if the inputs are all correct numbers
		isOnlyNumbers = val1.matches(regex_OneDecimalPointOnly) && val2.matches(regex_OneDecimalPointOnly) && val3.matches(regex_IntegersOnly);
		
		if(isOnlyNumbers)
		{
			
		//test whether inputs is less than 9 digits long
			boolean isLessThan9Digits = false;
			if(Double.parseDouble(val1) < 100000000.0f)
			{
				isLessThan9Digits = true;
			}
			
		//test that APR is 50% or below
			boolean aprIs50OrLower = false;
			if(Double.parseDouble(val2) <= 50.0f)
			{
				aprIs50OrLower = true;
			}		
			
		//test that number of years is less than or equal to 20
			boolean lessthanorequalto20years = false;
			if(Integer.parseInt(val3) <= 20)
			{
				lessthanorequalto20years = true;
			}
			return isLessThan9Digits && aprIs50OrLower && lessthanorequalto20years && isOnlyNumbers;
		}
		return false;
	}
	
	//gets the index of the correct currency in the combobox by its string
	private int GetIndexFromCurrencyName(String currencyName){
		int result = -1;
		switch(currencyName)
		{
			case "Australia(AUD)": 
				result = 0;
				break;
			case "Canada(CAD)": 
				result = 1;
				break;
			case "European Union(EUR)": 
				result = 2;
				break;
			case "United Kingdom(GBP)": 
				result = 3;
				break;
			case "United States(USD)": 
				result = 4;
				break;
		}
		return result;
	}
	
	//event handler fired when the calculate exchange rate button is clicked
	public void btn_CalculateExchangeRate_OnClick(){
		if(!txt_Input.getText().isEmpty()) {
			float result = CalculateExchangeRate(); //calculate the exchange rate
			if(result != -1.0f){
				String resultTo2DecimalPlaces = String.format("%.2f", result); //format the result to 2 decimal places
				SetExchangeRateOutput(resultTo2DecimalPlaces);
				
				//print out the result
				SetExchangeRateInfo(
						txt_Input.getText() + " " + cbx_Input.getValue()
						+ " equals " + txt_Output.getText()
						+ " " + cbx_Output.getValue() + ".");
			}
			else {
				//if there is an error, show an error message
				SetExchangeRateOutput("");
				SetExchangeRateInfo("An error occured! Input should be a real number that is less than 9 digits long.");
			}
		}
	}
	
	//event handler fired when the calculate interest button is clicked
	public void btn_CalculateInterest_OnClick() {
		if(!txt_InvestmentAmount.getText().isEmpty()
				&& !txt_APR.getText().isEmpty()
				&& !txt_Years.getText().isEmpty()) {
			double result = CalculateInterest(); //calculate the interest
			if(result != -1.0f) {
				String resultTo2DecimalPlaces = String.format("%.2f",  result); //format the result to 2 decimal places
				SetFutureValue(resultTo2DecimalPlaces);
				SetInterestInfo("");
			}
			else {
				//if there is an error show an error message.
				SetFutureValue("");
				SetInterestInfo("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20");
			}
		}
		else {
			//if there is an error show an error message.
			SetFutureValue("");
			SetInterestInfo("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20");
		}
	}
	
	//sets the output of the investment forecast
	private void SetFutureValue(String text) {
		txt_FutureValue.setText(text);
	}
	
	//sets the text for the message box for the investment forecast
	private void SetInterestInfo(String text) {
		lbl_InterestInfo.setText(text);
	}
	
	//sets the text for the message box for the exchange rate calculator
	private void SetExchangeRateInfo(String text) {
		lbl_ExchangeRateInfo.setText(text);
	}
	
	//sets the output of the exchange rate calculator
	private void SetExchangeRateOutput(String text) {
		txt_Output.setText(text);
	}
	
	//class to hold currency name and currency exchange rates
	public class Currency{
		private String currencyName;
		private float[] currencyRate;
		
		public Currency(String name, float[] rate) {
			this.currencyName = name;
			this.currencyRate = rate;
		}
		
		public String getCurrencyName() {
			return currencyName;
		}
		public float[] getCurrencyRate() {
			return currencyRate;
		}
	}
}

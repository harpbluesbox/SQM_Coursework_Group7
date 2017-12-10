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
		currencyList[0] = new Currency("Australia(AUD)", new Double[] {1.0,0.97,0.64,0.58, 0.76});
		currencyList[1] = new Currency("Canada(CAD)", new Double[] {1.03,1.0,0.67,0.59,0.78});
		currencyList[2] = new Currency("European Union(EUR)", new Double[] {1.55,1.5,1.0, 0.89,1.18});
		currencyList[3] = new Currency("United Kingdom(GBP)", new Double[] {1.74,1.68,1.12,1.0,1.32});
		currencyList[4] = new Currency("United States(USD)", new Double[] {1.32, 1.28, 0.85, 0.76, 1.0});
		return currencyList;
	}
	
	//function to calculate the exchange rate from one currency to another
	private Double CalculateExchangeRate(){
		Double result = -1.0;
		if(IsExchangeRateInputValid()){
			Double rate = 0.0;
			String inputCurrencyName = cbx_Input.getValue(); //get input currency
			String outputCurrencyName = cbx_Output.getValue(); //get output currency
			int fromIndex = GetIndexFromCurrencyName(inputCurrencyName);
			int toIndex = GetIndexFromCurrencyName(outputCurrencyName);
			
			if(fromIndex != -1 && toIndex != -1){
				rate = currencyList[fromIndex].getCurrencyRate()[toIndex]; //get the currency exchange rate multiplier
			}
			
			result = Double.parseDouble(txt_Input.getText()) * rate; //multiply the input amount by the exchange rate multiplier
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
		
		if(!result)
			return result;
		
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
			Double result = CalculateExchangeRate(); //calculate the exchange rate
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
		private Double[] currencyRate;
		
		public Currency(String name, Double[] rate) {
			this.currencyName = name;
			this.currencyRate = rate;
		}
		
		public String getCurrencyName() {
			return currencyName;
		}
		public Double[] getCurrencyRate() {
			return currencyRate;
		}
	}
}

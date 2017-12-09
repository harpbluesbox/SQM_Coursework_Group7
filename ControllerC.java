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
		txt_InvestmentAmount.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
		        if(!arg2.matches("\\d*(\\.\\d{0,2})?")) {
		            txt_InvestmentAmount.setText(arg1);
		        }
			}});
			
		txt_APR.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					// TODO Auto-generated method stub
			        if(!arg2.matches("\\d*(\\.\\d{0,4})?")) {
			            txt_APR.setText(arg1);
			        }
				}
		});
		
		txt_Years.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
		        if(!arg2.matches("\\d*?")) {
		            txt_Years.setText(arg1);
		        }
			}
	});
	}

	public void populateComboBoxes() {
		ObservableList<String> x = cbx_Input.getItems();
		ObservableList<String> y = cbx_Output.getItems();
		for (int i = 0; i < 5; i++) {  
			x.add(currencyList[i].getCurrencyName());
			y.add(currencyList[i].getCurrencyName());
		}
		cbx_Input.setValue(cbx_Input.getItems().get(0));
		cbx_Output.setValue(cbx_Output.getItems().get(0));
	}
	
	public Currency[] generateCurrencyList() {
		Currency[] currencyList = new Currency[5];
		currencyList[0] = new Currency("Australia(AUD)", new float[] {1.0f,0.97f,0.64f,0.58f, 0.76f});
		currencyList[1] = new Currency("Canada(CAD)", new float[] {1.03f,1.0f,0.67f,0.59f,0.78f});
		currencyList[2] = new Currency("European Union(EUR)", new float[] {1.55f,1.5f,1.0f, 0.89f,1.18f});
		currencyList[3] = new Currency("United Kingdom(GBP)", new float[] {1.74f,1.68f,1.12f,1.0f,1.32f});
		currencyList[4] = new Currency("United States(USD)", new float[] {1.32f, 1.28f, 0.85f, 0.76f, 1.0f});
		return currencyList;
	}
	
	private float CalculateExchangeRate(){
		float result = -1.0f;
		if(IsExchangeRateInputValid()){
			float rate = 0.0f;
			String inputCurrencyName = cbx_Input.getValue();
			String outputCurrencyName = cbx_Output.getValue();
			int fromIndex = GetIndexFromCurrencyName(inputCurrencyName);
			int toIndex = GetIndexFromCurrencyName(outputCurrencyName);
			
			if(fromIndex != -1 && toIndex != -1){
				rate = currencyList[fromIndex].getCurrencyRate()[toIndex];
			}
			
			result = Float.parseFloat(txt_Input.getText()) * rate;		
		}
		return result;
	}
	
	private float CalculateInterest() {
		float result = -1.0f;
		if(IsInterestInputValid()) {
			float investmentAmount = Float.parseFloat(txt_InvestmentAmount.getText());
			float apr = Float.parseFloat(txt_APR.getText());
			float years = Float.parseFloat(txt_Years.getText());
			result = investmentAmount;
			for(int i = 0; i < years; i++){
				result += ((result/100)*apr);
			}
		}
		return result;
	}
	
	private boolean IsExchangeRateInputValid(){
		
		boolean result;
		
		String regex_OneDecimalPointOnly = "(\\d+(?:\\.\\d+)?)";
		String val = txt_Input.getText();
		
		result = val.matches(regex_OneDecimalPointOnly);
		
		return result;
	}
	
	private boolean IsInterestInputValid(){
		
		boolean isOnlyNumbers = false;
		String regex_OneDecimalPointOnly = "(\\d+(?:\\.\\d+)?)";
		String regex_IntegersOnly = "([0-9]+)";
		String val1 = txt_InvestmentAmount.getText();
		String val2 = txt_APR.getText();
		String val3 = txt_Years.getText();	
		isOnlyNumbers = val1.matches(regex_OneDecimalPointOnly) && val2.matches(regex_OneDecimalPointOnly) && val3.matches(regex_IntegersOnly);
		
		if(isOnlyNumbers)
		{
		boolean isLessThan11Digits = false;
			if(Float.parseFloat(val1) < 100000000000.0f)
			{
				isLessThan11Digits = true;
				System.out.println("Parsed value" + Float.parseFloat(val1));
			}
			
			boolean aprIs50OrLower = false;
			if(Float.parseFloat(val2) <= 50.0f)
			{
				aprIs50OrLower = true;
			}		
			
			boolean lessthan20years = false;
			if(Integer.parseInt(val3) <= 20)
			{
				lessthan20years = true;
			}
			return isLessThan11Digits && aprIs50OrLower && lessthan20years && isOnlyNumbers;
		}
		return false;
	}
	
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
	
	public void btn_CalculateExchangeRate_OnClick(){
		if(!txt_Input.getText().isEmpty()) {
			float result = CalculateExchangeRate();
			if(result != -1.0f){
				String resultTo2DecimalPlaces = String.format("%.2f", result);
				SetExchangeRateOutput(resultTo2DecimalPlaces);
				SetExchangeRateInfo(
						txt_Input.getText() + " " + cbx_Input.getValue()
						+ " equals " + txt_Output.getText()
						+ " " + cbx_Output.getValue() + ".");
			}
			else {
				SetExchangeRateOutput("");
				SetExchangeRateInfo("An error occured! Input should be a real number only.");
			}
		}
	}
	
	public void btn_CalculateInterest_OnClick() {
		if(!txt_InvestmentAmount.getText().isEmpty()
				&& !txt_APR.getText().isEmpty()
				&& !txt_Years.getText().isEmpty()) {
			float result = CalculateInterest();
			if(result != -1.0f) {
				String resultTo2DecimalPlaces = String.format("%.2f",  result);
				SetFutureValue(resultTo2DecimalPlaces);
				SetInterestInfo("");
			}
			else {
				SetFutureValue("");
				SetInterestInfo("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20");
			}
		}
		else {
			SetFutureValue("");
			SetInterestInfo("An error occurred! Input should be a real number only. \n Investment Amount should be less than 10,000,000,000 \n APR should be <= 50% \n Years should be an integer <= 20");
		}
	}
	
	private void SetFutureValue(String text) {
		txt_FutureValue.setText(text);
	}
	
	private void SetInterestInfo(String text) {
		lbl_InterestInfo.setText(text);
	}
	
	private void SetExchangeRateInfo(String text) {
		lbl_ExchangeRateInfo.setText(text);
	}
	
	private void SetExchangeRateOutput(String text) {
		txt_Output.setText(text);
	}
	
	private void SetExchangeRateInput(String text) {
		txt_Input.setText(text);
	}
	
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
	

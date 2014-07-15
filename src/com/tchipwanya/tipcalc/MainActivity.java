package com.tchipwanya.tipcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Tawanda Chipwanya
 * Class MainActivity to provide functionality to the Tip Calculator
 *
 */
public class MainActivity extends Activity {
	
	private EditText enterBill;
	private Button reset, calculate, tAddButton, sAddButton, tSubButton, sSubButton;
	private TextView showTip, split, showTotal, totalTip, showPerPerson, showTipPerson, showBillPerPerson;
	private BigDecimal bill, tipPercentage, people, tip, total, perPerson, tipPerson, billPerson;
	private String textBill, textTipPercentage, textPeople;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		setOnclickListeners();	
	}
	
	private void setOnclickListeners() {
		
		calculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setInputs();
				calculate(bill, tipPercentage, people);
				totalTip.setText("$"+tip.toString());
				showTotal.setText(
						"$"+total.toString());
				showPerPerson.setText("$"+perPerson.toString());
				showTipPerson.setText("$"+tipPerson.toString());
				showBillPerPerson.setText("$"+billPerson.toString());
			}
		});
		
		tAddButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tCurrentTip = showTip.getText().toString();
				BigDecimal currentTip = new BigDecimal(tCurrentTip);
				BigDecimal updatedTip = currentTip.add(new BigDecimal(1));
				showTip.setText(updatedTip.toString());
				
			}
		});
		
		sAddButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tCurrentPeople = split.getText().toString();
				BigDecimal currentPeople = new BigDecimal(tCurrentPeople);
				BigDecimal updatedPeople = currentPeople.add(new BigDecimal(1));
				split.setText(updatedPeople.toString());
				
			}
		});
		
		tSubButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tCurrentTip = showTip.getText().toString();
				BigDecimal currentTip = new BigDecimal(tCurrentTip);
				BigDecimal updatedTip = currentTip.subtract(new BigDecimal(1));
				if(updatedTip.compareTo(BigDecimal.ZERO) >= 0)
					showTip.setText(updatedTip.toString());
				else
					toast("Tip cannot be negative");
					
			}
		});
		
		sSubButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tCurrentPeople = split.getText().toString();
				BigDecimal currentPeople = new BigDecimal(tCurrentPeople);
				BigDecimal updatedPeople = currentPeople.subtract(new BigDecimal(1));
				if(updatedPeople.compareTo(BigDecimal.ZERO) > 0)
					split.setText(updatedPeople.toString());
				else
					toast("The number of people cannot be less than one");
			}
		});
		
		reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reset();	
			}
		});

	}
	
	private void init() {
		
		enterBill = (EditText)findViewById(R.id.enterBill);
		
		reset = (Button)findViewById(R.id.resetButton);
		calculate = (Button)findViewById(R.id.calculateButton);
		tAddButton = (Button)findViewById(R.id.addButton);
		tSubButton = (Button)findViewById(R.id.subButton);
		sAddButton = (Button)findViewById(R.id.addPeople);
		sSubButton = (Button)findViewById(R.id.subPeople);
		
		showTip = (TextView)findViewById(R.id.showTip);
		split = (TextView)findViewById(R.id.showPeople);
		showTotal = (TextView)findViewById(R.id.showTotal);
		totalTip = (TextView)findViewById(R.id.showTotalTip);
		showPerPerson = (TextView)findViewById(R.id.showPerPerson);
		showBillPerPerson = (TextView)findViewById(R.id.showBillPerson);
		showTipPerson = (TextView)findViewById(R.id.showTipPerson);
	}
	
	private void setInputs(){
		textBill = enterBill.getText().toString();
		if(textBill.matches("") || textBill.matches("\\.")){
			textBill="0.00";
			toast("Please enter a valid number");
		}
		bill = new BigDecimal(textBill);
		textTipPercentage = showTip.getText().toString();
		tipPercentage = new BigDecimal(textTipPercentage);
		textPeople = split.getText().toString();
		people = new BigDecimal(textPeople);
	}
	
	private void toast(String string) {
		Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 0, 150);
		toast.show();
		
	}

	private void calculate(BigDecimal bill, BigDecimal tipPercentage, BigDecimal people){
		BigDecimal rawTip = (bill.multiply(tipPercentage).divide(new BigDecimal(100))).setScale
				(6, RoundingMode.HALF_UP);
		tip = rawTip.setScale(2, RoundingMode.HALF_UP);
		BigDecimal rawTotal = (bill.add(rawTip)).setScale(6, RoundingMode.HALF_UP);
		total = rawTotal.setScale(2, RoundingMode.HALF_UP);
		perPerson = rawTotal.divide(people, 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
		billPerson = bill.divide(people, 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
		tipPerson = rawTip.divide(people, 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
		
	}
	
	private void reset() {
		enterBill.setText("");
		showTip.setText("15");
		split.setText("1");
		showTotal.setText("$0.00");
		totalTip.setText("$0.00");
		showPerPerson.setText("$0.00");
		showBillPerPerson.setText("$0.00");
		showTipPerson.setText("$0.00");

	}
	
}

package com.example.adityasapate.registration;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SignUPActivity extends Activity
{
	EditText editTextName,editTextRegId,editTextAddress,editTextDob,editTextCourse ,editTextYear,editTextPassword,editTextConfirmPassword,editTextMob;
	Button btnCreateAccount;
	CheckBox checkboxIT,checkboxCS,checkboxCivil,checkboxENTC;
	String course = "";
	String year="";
	DatePicker date;
	Spinner spinner1;
	ArrayList<String> list = new ArrayList<>();
	
	LoginDataBaseAdapter loginDataBaseAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		// get Instance  of Database Adapter
		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();

		// Get Refferences of Views
		editTextName=(EditText)findViewById(R.id.editTextUserName);
        editTextRegId = (EditText)findViewById(R.id.editTextRegId);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
      //  editTextDob = (EditText) findViewById(R.id.editTextDate);

      // editTextYear = (EditText) findViewById(R.id.editTextYear);




		editTextPassword=(EditText)findViewById(R.id.editTextPassword);
		editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
		date = (DatePicker) findViewById(R.id.datePicker3);

        spinner1 = (Spinner) findViewById(R.id.spinner1);

          list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignUPActivity.this, R.layout.support_simple_spinner_dropdown_item,list);
        spinner1.setAdapter(adapter);



	//	checkboxIT = (CheckBox) findViewById(R.id.checkBoxIT);
		btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			String Name=editTextName.getText().toString();
            String regid=editTextRegId.getText().toString();
            String Address=editTextAddress.getText().toString();
            String day=Integer.toString(date.getDayOfMonth());
			String month=Integer.toString(date.getMonth());
			String yer=Integer.toString(date.getYear());
			String dob= day + "/" + month + "/" + yer;

		//	String year = editTextYear.getText().toString();
			String year = spinner1.getSelectedItem().toString();

			String password=editTextPassword.getText().toString();
			String confirmPassword=editTextConfirmPassword.getText().toString();





				// Log.i("Checked",);


				// check if any of the fields are vaccant
				if (Name.equals("") || regid.equals("") || Address.equals("") || dob.equals("") || password.equals("") || confirmPassword.equals("")) {
					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
					return;
				}
				// check if both password matches
				if (!password.equals(confirmPassword)) {
					Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
					return;
				} else {
					// Save the Data in Database
					loginDataBaseAdapter.insertEntry(Name, regid, Address, dob ,year ,password);
					Toast.makeText(getApplicationContext(), "Registered Successfully ", Toast.LENGTH_LONG).show();
				}


		}
	});
}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		loginDataBaseAdapter.close();
	}




}

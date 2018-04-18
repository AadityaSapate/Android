package com.example.adityasapate.registration;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity 
{
	Button btnSignIn,btnSignUp,buttonDelete,buttonView,buttonback,buttonlogin;
	LoginDataBaseAdapter loginDataBaseAdapter;
	EditText editTextDelete,editTextSearch,editTextadminN,getEditTextadminP;
	TextView textView,AdminText;
			// ,editTextName,editTextNewPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	     // create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	     // Get The Refference Of Buttons
	     btnSignIn=(Button)findViewById(R.id.buttonSignIN);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUP);
		buttonDelete=(Button)findViewById(R.id.button1);
		editTextDelete= (EditText) findViewById(R.id.editTextDelete) ;
		editTextSearch= (EditText) findViewById(R.id.editTextSearch);
		textView = (TextView) findViewById(R.id.textViewDisplay);
		buttonView = (Button) findViewById(R.id.buttonView);
		buttonback = (Button) findViewById(R.id.buttonback);
		buttonlogin = (Button) findViewById(R.id.buttonadmin);
		editTextadminN = (EditText) findViewById(R.id.editTextadminN);
		getEditTextadminP = (EditText) findViewById(R.id.editTextadminP);
		AdminText = (TextView) findViewById(R.id.textViewA);
     //   buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
	//	editTextName = (EditText) findViewById(R.id.editTextName);
	//	editTextNewPass = (EditText) findViewById(R.id.editTextNewPass);
	    // Set OnClick Listener on SignUp button
		btnSignUp.setVisibility(View.INVISIBLE);
		btnSignIn.setVisibility(View.INVISIBLE);
		buttonDelete.setVisibility(View.INVISIBLE);
		buttonView.setVisibility(View.INVISIBLE);
		editTextSearch.setVisibility(View.INVISIBLE);
		editTextDelete.setVisibility(View.INVISIBLE);





		btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
			startActivity(intentSignUP);
			}
		});
	}
	// Methos to handleClick Event of Sign In Button
	public void signIn(View V)
	   {
			final Dialog dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.login);
		    dialog.setTitle("Enter");
	
		    // get the Refferences of views
		    final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
		    final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
		    
			Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);
				
			// Set On ClickListener
			btnSignIn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// get The User name and Password
					String userName=editTextUserName.getText().toString();
					String password=editTextPassword.getText().toString();
					
					// fetch the Password form database for respective user name
					String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
					
					// check if the Stored password matches with  Password entered by user
					if(password.equals(storedPassword))
					{
						Toast.makeText(HomeActivity.this, "Database Exists", Toast.LENGTH_LONG).show();

						dialog.dismiss();
					}
					else
					{
						Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			dialog.show();
	}
           public  void  delete(View v)
		   {
			  String Name = editTextDelete.getText().toString();
			  int NoROw = loginDataBaseAdapter.deleteEntry(Name);
              String rows = Integer.toString(NoROw);


			   Toast.makeText(HomeActivity.this,"total no of rows deleted Are " + rows, Toast.LENGTH_LONG).show();

		   }

		   public void view(View v)
		   {
			   btnSignUp.setVisibility(View.INVISIBLE);
			   btnSignIn.setVisibility(View.INVISIBLE);
			   buttonDelete.setVisibility(View.INVISIBLE);
			   buttonView.setVisibility(View.INVISIBLE);
			   editTextSearch.setVisibility(View.INVISIBLE);
			   editTextDelete.setVisibility(View.INVISIBLE);
			   buttonback.setVisibility(View.VISIBLE);

              textView.setVisibility(View.VISIBLE);
			  String name = editTextSearch.getText().toString();
			   String Data = loginDataBaseAdapter.get(name);
			 //  Toast.makeText(HomeActivity.this,Data, Toast.LENGTH_LONG).show();
                textView.setText(Data);

		   }
		   public void back(View v) {

			   btnSignUp.setVisibility(View.VISIBLE);
			   btnSignIn.setVisibility(View.VISIBLE);
			   buttonDelete.setVisibility(View.VISIBLE);
			   buttonView.setVisibility(View.VISIBLE);
			   editTextSearch.setVisibility(View.VISIBLE);
			   editTextDelete.setVisibility(View.VISIBLE);
              buttonback.setVisibility(View.INVISIBLE);
			   textView.setVisibility(View.INVISIBLE);
			   textView.setText("");



		   }
		   public void login(View v)
		   {
			   String name = editTextadminN.getText().toString();
			   String pass = getEditTextadminP.getText().toString();
			  // String admin = AdminText.getText().toString();
                if(name.equals("admin") && pass.equals("admin")) {
					AdminText.setVisibility(View.INVISIBLE);
					editTextadminN.setVisibility(View.INVISIBLE);
					getEditTextadminP.setVisibility(View.INVISIBLE);
					buttonlogin.setVisibility(View.INVISIBLE);
					btnSignUp.setVisibility(View.VISIBLE);
					btnSignIn.setVisibility(View.VISIBLE);
					buttonDelete.setVisibility(View.VISIBLE);
					buttonView.setVisibility(View.VISIBLE);
					editTextSearch.setVisibility(View.VISIBLE);
					editTextDelete.setVisibility(View.VISIBLE);
				}
				else
				{
					Toast.makeText(HomeActivity.this,"Check username and password", Toast.LENGTH_LONG).show();
				}


		   }




	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		loginDataBaseAdapter.close();
	}
}

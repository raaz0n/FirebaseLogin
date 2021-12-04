package com.ncit.firebaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName,regUsername,regPassword,regEmail,regPhone;
    Button reg_signUp_btn,reg_login_btn;


    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


        //hooks
        regName =findViewById(R.id.reg_name);
        regPassword =findViewById(R.id.reg_password);
        regEmail =findViewById(R.id.reg_email);
        regPhone =findViewById(R.id.reg_phone);
        regUsername =findViewById(R.id.reg_username);
        reg_signUp_btn =findViewById(R.id.reg_btn);
        reg_login_btn =findViewById(R.id.reg_login);


    }

    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();

        if(val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }


    }
    private Boolean validateUserName(){
        String val = regUsername.getEditText().getText().toString();
//        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }else if (val.length()>= 15){
            regUsername.setError("UserName too long");
            return false;

        }
//        else if(!val.matches(noWhiteSpace)){
//            regUsername.setError("White Space are not allowed");
//            return false;
//
//        }
        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }


    }
    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern ="(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)";

        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid Email Address");
            return false;
        }
        else{
            regEmail.setError(null);
            return true;
        }


    }
    private Boolean validatePhone(){
        String val = regPhone.getEditText().getText().toString();

        if(val.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        }
        else{
            regPhone.setError(null);
            return true;
        }


    }
    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();

        if(val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else{
            regPassword.setError(null);
            return true;
        }


    }




    public void registerUser(View view){

        if(!validateName() | !validateUserName() | !validateEmail() | !validatePhone() | !validatePassword()){
            return;
        }

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        //get  all  the values
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();


        UserHelperClass helperClass = new UserHelperClass(name,username,email,phoneNo,password);

        reference.child(username).setValue(helperClass);

    }


    public void alreadyRegisterUser(View view){
        Intent intent1 = new Intent(getApplicationContext(),Login.class);
        startActivity(intent1);
        finish();
    }
}
package com.ncit.firebaselogin;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    Button signup_screen, login_btn;
    ImageView logo_image;
    TextView logo_text, sloganText;

    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //hooks
        signup_screen = findViewById(R.id.signup_screen);
        logo_image = findViewById(R.id.logo_image);
        logo_text = findViewById(R.id.logo_text);
        sloganText = findViewById(R.id.sloganText);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);

        signup_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,SignUp.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logo_image,"logo_image");  // transition name "...."
                pairs[1] = new Pair<View,String>(logo_text,"logo_text");
                pairs[2] = new Pair<View,String>(sloganText,"logo_desc");
                pairs[3] = new Pair<View,String>(username,"username");
                pairs[4] = new Pair<View,String>(password,"password");
                pairs[5] = new Pair<View,String>(login_btn,"login_button");
                pairs[6] = new Pair<View,String>(signup_screen,"button_signIn_up");

                ActivityOptionsCompat options =ActivityOptionsCompat.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent,options.toBundle());

            }
        });
    }


    private Boolean validateUserName(){
        String val = username.getEditText().getText().toString();

        if(val.isEmpty()){
            username.setError("Field cannot be empty");
            return false;
        }
        else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }


    }
    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();

        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }


    }


    public void loginUser(View view){
        if(!validateUserName() | !validatePassword()){
            return;
        }else{
            isUser();
        }
    }

    private void isUser() {
        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if(passwordFromDB.equals(userEnteredPassword)){
                        password.setError(null);
                        password.setErrorEnabled(false);


                        Intent intent = new Intent(Login.this,Dashboard.class);
                        startActivity(intent);
                    }else{
                        password.setError("password incorrect");
                        password.requestFocus();
                    }

                }else{
                    username.setError("No such User exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Call SignUp Screen
    public void signup_screen(View view){


    }
}
package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by angelicagarcia16 on 4/9/2018.
 */

public class CreateAccountActivity extends ChinchillaChatActivity {
    FirebaseAuth firebaseAuth;

    EditText username;
    EditText password;
    EditText passwordConfirm;
    EditText email;
    EditText emailConfirm;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.password_confirm);
        email = findViewById(R.id.email);
        emailConfirm = findViewById(R.id.email_confirm);
        signInButton = findViewById(R.id.user_sign_in_button);

        firebaseAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                registerUser();
            }
        });
    }



    public void registerUser() {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String pass2 = passwordConfirm.getText().toString();
        String mail = email.getText().toString().toLowerCase();
        String mail2 = emailConfirm.getText().toString().toLowerCase();

        StringBuilder sb = new StringBuilder();
        if(isUsernameTaken(user)){
            sb.append("Username already taken.\n");
        }
//        if(firebaseAuth.)
        if(!isEmailValid(mail)){
            sb.append("Must use an @augustana.edu email.\n");
        }
        if(!mail.equals(mail2)){
            sb.append("Emails must match.\n");
        }
        if(!isPasswordValid(pass)){
            sb.append("Password not strong enough.\n");
        }
        if(!pass.equals(pass2)){
            sb.append("Passwords must match.\n");
        }

        if(sb.length() > 0){
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        } else {
            // Showing progress dialog at user registration time.
            Toast.makeText(this, "Registering. Please Wait.", Toast.LENGTH_LONG).show();
            firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(CreateAccountActivity.this, "Congratulations! Your account has been created.", Toast.LENGTH_LONG).show();
                    editor.putString("userid", firebaseAuth.getCurrentUser().getUid());
                    verifyUser();
                }
            });
        }
    }

    public void verifyUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Toast.makeText(CreateAccountActivity.this, "Please verify your account by following the link in the email we sent you.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), VerifyAccountActivity.class);
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("password", password.getText().toString());
        startActivity(intent);
    }

    /**
     * Require augustana.edu emails.
     *
     * @param email
     * @return true if this is an augustana.edu email, false otherwise
     */
    public static boolean isEmailValid(String email) {
        return email.endsWith("@augustana.edu");
    }

    /**
     * Require "secure enough" passwords. A password is deemed to be "secure enough"
     *
     *
     * @param password
     * @return true if the password is "secure enough", false otherwise
     */
    public static boolean isPasswordValid(String password) {
        return PasswordAnalysis.passwordComplexity(password) > PasswordAnalysis.NUM_SECONDS_PER_DAY; // password will take more than 1 day to crack
    }

    /**
     * @param username
     * @return true if the username is already in use, false otherwise
     */
    public static boolean isUsernameTaken(String username) {
        return false;
    }

}

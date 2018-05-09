package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chinchillas.chinchillachat.datamodel.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by angelicagarcia16 on 4/9/2018.
 */

public class CreateAccountActivity extends ChinchillaChatActivity {
    FirebaseAuth firebaseAuth;

    EditText usernameET;
    EditText passwordET;
    EditText passwordConfirmET;
    EditText emailET;
    EditText emailConfirmET;
    Button signInButton;

    private boolean failedToCreateAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
        passwordConfirmET = findViewById(R.id.password_confirm);
        emailET = findViewById(R.id.email);
        emailConfirmET = findViewById(R.id.email_confirm);

        String email = getIntent().getStringExtra("email");
        if(email != null) {
            emailET.setText(email);
        }
        String password = getIntent().getStringExtra("password");
        if(password != null) {
            passwordET.setText(password);
        }

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
        String user = usernameET.getText().toString();
        String pass = passwordET.getText().toString();
        String pass2 = passwordConfirmET.getText().toString();
        final String mail = emailET.getText().toString().toLowerCase();
        String mail2 = emailConfirmET.getText().toString().toLowerCase();

        StringBuilder sb = new StringBuilder();
        if(!isUsernameValid(user)){
            sb.append("Usernames may only contain letters and numbers.\n");
        }
        if(isUsernameTaken(user)){
            sb.append("Username already taken.\n");
        }
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
            final String username = usernameET.getText().toString();
//            final String pseudonym = usernameET.getText().toString(); // TODO: Change this.
//            databaseReference.child("usernameList").child(myUsername).setValue(true).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(CreateAccountActivity.this, "Username already in use.", Toast.LENGTH_LONG).show();
//                    failedToCreateAccount = true;
//                }
//            });
//            databaseReference.child("pseudonymList").child(pseudonym).setValue(true).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(CreateAccountActivity.this, "Pseudonym already in use.", Toast.LENGTH_LONG).show();
//                    failedToCreateAccount = true;
//                }
//            });
//            databaseReference.child("emailList").child(mail.substring(0,mail.indexOf("@"))).setValue(true).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(CreateAccountActivity.this, "E-mail already in use.", Toast.LENGTH_LONG).show();
//                    failedToCreateAccount = true;
//                }
//            });

            if(failedToCreateAccount){
                failedToCreateAccount = false;
                return;
            } else {
                firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(CreateAccountActivity.this, "Congratulations! Your account has been created.", Toast.LENGTH_LONG).show();
                        String userID = firebaseAuth.getCurrentUser().getUid();
//                        editor.putString("userid", userID);
                        User newUser = new User(username, mail);
                        databaseReference.child("users").child(userID).setValue(newUser.toMap());

                        // usernameList is a map of usernames in all caps to usernames as input by users.
                        databaseReference.child("usernameList").child(username.toUpperCase()).setValue(username);
//                        databaseReference.child("pseudonymList").child(pseudonym).setValue(userID);
                        databaseReference.child("emailList").child(mail.substring(0,mail.indexOf("@"))).setValue(userID);
                        editor.putString("myUsername", username);
//                        editor.putString("myPseudonym", pseudonym);
                        verifyUser();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateAccountActivity.this, "Failed to create account. Try again.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public void verifyUser() {
        Intent intent = new Intent(getApplicationContext(), VerifyAccountActivity.class);
        startActivity(intent);
        finish();
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
 //       return password.length() >= 6; // bare minimum length, for testing
    }

    /**
     * Usernames are considered to be valid if they contain at least one character
     * and do not contain any symbols.
     *
     * @param username
     * @return true if the password is valid, false otherwise
     */
    public static boolean isUsernameValid(String username) {
        if (username.length() == 0) {
            return false;
        }
        for(int i=0; i<username.length(); i++) {
            if(!Character.isLetterOrDigit(username.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * @param myUsername
     * @return true if the myUsername is already in use, false otherwise
     */
    public boolean isUsernameTaken(final String myUsername) {
        return userPrefs.contains(myUsername.toUpperCase());
    }

}

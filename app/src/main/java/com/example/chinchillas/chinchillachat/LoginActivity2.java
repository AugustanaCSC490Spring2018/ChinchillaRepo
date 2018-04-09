package com.example.chinchillas.chinchillachat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ivyvecna15 on 4/9/2018.
 *
 * INCOMPLETE, DO NOT TRY TO USE.
 */

public class LoginActivity2 extends AppCompatActivity {



    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginActivity.UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.user_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Require augustana.edu emails.
     *
     * @param email
     * @return true if this is an augustana.edu email, false otherwise
     */
    private boolean isEmailValid(String email) {
        return email.endsWith("@augustana.edu");
    }

    /**
     * Require "secure enough" passwords. A password is deemed to be "secure enough"
     *
     *
     * @param password
     * @return true if the password is "secure enough", false otherwise
     */
    private boolean isPasswordValid(String password) {
        return PasswordAnalysis.passwordComplexity(password) > 86400; // password will take more than 1 day to crack
    }
}

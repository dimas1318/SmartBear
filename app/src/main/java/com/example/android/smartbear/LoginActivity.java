package com.example.android.smartbear;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartbear.session.SessionManager;
import com.example.android.smartbear.session.SessionManagerImpl;
import com.example.android.smartbear.validator.UserDataValidator;
import com.example.android.smartbear.validator.exception.TooLongTextException;
import com.example.android.smartbear.validator.exception.TooShortTextException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parsh on 16.10.2017.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private SessionManager session;

    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.link_signup)
    TextView signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new SessionManagerImpl(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);

                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.equals("admin") && password.equals("admin")) {
           session.createUserSession(email, password, "Admin", true);

            // TODO: Implement your own authentication logic here.

            new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                    // On complete call either onLoginSuccess or onLoginFailed
                    onLoginSuccess();
                    // onLoginFailed();
                    progressDialog.dismiss();
                    }
                }, 3000);
        } else {
            new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                    onLoginFailed();
                    progressDialog.dismiss();
                    }
                }, 3000);
        }
    }


    /**
     * Implementation of successful signup logic
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        try {
            UserDataValidator.validateEmail(email);
            emailText.setError(null);
        } catch (TooShortTextException e) {
            emailText.setError("enter a valid email address");
            valid = false;
        }

        try {
            UserDataValidator.validatePassword(password);
            passwordText.setError(null);
        } catch (TooShortTextException | TooLongTextException e) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }
}
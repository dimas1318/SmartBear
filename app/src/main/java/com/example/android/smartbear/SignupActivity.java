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

import com.example.android.smartbear.validator.UserDataValidator;
import com.example.android.smartbear.validator.exception.NotValidDataException;
import com.example.android.smartbear.validator.exception.TooLondTextException;
import com.example.android.smartbear.validator.exception.TooShortTextException;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by parsh on 16.10.2017.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name) EditText nameText;
    @Bind(R.id.input_address) EditText addressText;
    @Bind(R.id.input_email) EditText emailText;
    @Bind(R.id.input_mobile) EditText mobileText;
    @Bind(R.id.input_password) EditText passwordText;
    @Bind(R.id.input_reEnterPassword) EditText reEnterPasswordText;
    @Bind(R.id.btn_signup) Button signupButton;
    @Bind(R.id.link_login) TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();
        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        try {
            UserDataValidator.validateName(name);
            nameText.setError(null);
        } catch (TooShortTextException e) {
            nameText.setError("at least 3 characters");
            valid = false;
        }

        try {
            UserDataValidator.validateAddress(address);
            addressText.setError(null);
        } catch (TooShortTextException e) {
            addressText.setError("Enter Valid Address");
            valid = false;
        }

        try {
            UserDataValidator.validateEmail(email);
            emailText.setError(null);
        } catch (TooShortTextException e) {
            emailText.setError("enter a valid email address");
            valid = false;
        }

        try {
            UserDataValidator.validateMobile(mobile);
            mobileText.setError(null);
        } catch (TooShortTextException | NotValidDataException e) {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        }

        try {
            UserDataValidator.validatePassword(password);
            passwordText.setError(null);
        } catch (TooShortTextException | TooLondTextException e) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        try {
            UserDataValidator.validateReEnterPassword(reEnterPassword, password);
            reEnterPasswordText.setError(null);
        } catch (NotValidDataException e) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } catch (TooShortTextException | TooLondTextException e) {
            reEnterPasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }
}

package com.example.android.smartbear;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartbear.database.DataBaseManager;
import com.example.android.smartbear.validator.UserDataValidator;
import com.example.android.smartbear.validator.exception.NotValidDataException;
import com.example.android.smartbear.validator.exception.TooLongTextException;
import com.example.android.smartbear.validator.exception.TooShortTextException;

import butterknife.ButterKnife;
import butterknife.Bind;

import static com.example.android.smartbear.constants.Constants.ADMIN_KEY;
import static com.example.android.smartbear.constants.Constants.EMAIL_KEY;
import static com.example.android.smartbear.constants.Constants.NAME_KEY;
import static com.example.android.smartbear.constants.Constants.PASSWORD_KEY;
import static com.example.android.smartbear.constants.Constants.PREFERENCE_FILE_KEY;

/**
 * Created by parsh on 16.10.2017.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    SharedPreferences sharedPreferences;

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

        nameText.setText("Dima");
        addressText.setText("Moscow, MIPT");
        emailText.setText("parshin@google.com");
        mobileText.setText("9001234567");
        passwordText.setText("12345");
        reEnterPasswordText.setText("12345");

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

        DataBaseManager.saveDataIntoBase(name, address, email, mobile, password, reEnterPassword);

        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, email);
        editor.putString(NAME_KEY, name);
        editor.putString(PASSWORD_KEY, password);
        editor.putBoolean(ADMIN_KEY, false);
        editor.commit();

        View navHeaderView = MainActivity.getNavigationView().inflateHeaderView(R.layout.nav_header_main);
        TextView nameInHeader = navHeaderView.findViewById(R.id.name_in_nav_header);
        nameInHeader.setText(sharedPreferences.getString(NAME_KEY, ""));
        TextView emailInHeader = navHeaderView.findViewById(R.id.email_in_nav_header);
        emailInHeader.setText(sharedPreferences.getString(EMAIL_KEY, ""));
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
        } catch (TooShortTextException | TooLongTextException e) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        try {
            UserDataValidator.validateReEnterPassword(reEnterPassword, password);
            reEnterPasswordText.setError(null);
        } catch (NotValidDataException e) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } catch (TooShortTextException | TooLongTextException e) {
            reEnterPasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }
}

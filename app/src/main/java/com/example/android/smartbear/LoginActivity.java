package com.example.android.smartbear;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartbear.validator.UserDataValidator;
import com.example.android.smartbear.validator.exception.TooLondTextException;
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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private SharedPreferences sharedPreferences;

    @Bind(R.id.input_email) EditText emailText;
    @Bind(R.id.input_password) EditText passwordText;
    @Bind(R.id.btn_login) Button loginButton;
    @Bind(R.id.link_signup) TextView signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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
                finish();
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
            sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
            sharedPreferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(EMAIL_KEY, email);
            editor.putString(NAME_KEY, "Admin");
            editor.putString(PASSWORD_KEY, password);
            editor.putBoolean(ADMIN_KEY, true);
            editor.commit();

            View navHeaderView = MainActivity.getNavigationView().inflateHeaderView(R.layout.nav_header_main);
            TextView nameInHeader = navHeaderView.findViewById(R.id.name_in_nav_header);
            nameInHeader.setText(sharedPreferences.getString(NAME_KEY, "") + " (ya admin)");
            TextView emailInHeader = navHeaderView.findViewById(R.id.email_in_nav_header);
            emailInHeader.setText(sharedPreferences.getString(EMAIL_KEY, ""));
        }
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
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
        } catch (TooShortTextException | TooLondTextException e) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }
}
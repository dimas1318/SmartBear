package com.example.android.smartbear;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartbear.database.DataBaseManager;
import com.example.android.smartbear.database.DatabaseManagerFirebase;
import com.example.android.smartbear.session.SessionManager;
import com.example.android.smartbear.session.SessionManagerImpl;
import com.example.android.smartbear.validator.UserDataValidator;
import com.example.android.smartbear.validator.exception.NotValidDataException;
import com.example.android.smartbear.validator.exception.TooLongTextException;
import com.example.android.smartbear.validator.exception.TooShortTextException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parsh on 16.10.2017.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private SessionManager session;

    private FirebaseAuth auth;


    @BindView(R.id.input_name)
    EditText nameText;
    @BindView(R.id.input_address)
    EditText addressText;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_mobile)
    EditText mobileText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.link_login)
    TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        session = new SessionManagerImpl(getApplicationContext());

        nameText.setText("Dima");
        addressText.setText("Moscow, MIPT");
        emailText.setText("parshin@phystech.edu");
        mobileText.setText("9001234567");
        passwordText.setText("12345x_x_*xYYY");
        reEnterPasswordText.setText("12345x_x_*xYYY");

        auth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String address = addressText.getText().toString();
                String email = emailText.getText().toString();
                String mobile = mobileText.getText().toString();
                String password = passwordText.getText().toString();
                String reEnterPassword = reEnterPasswordText.getText().toString();

                signUp(email, password, name);
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


    public void signUp(final String email, final String password, final String name) {
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

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseManagerFirebase.saveUser();

                    session.createUserSession(email, password, name, false);

                    onSignupSuccess();
                    progressDialog.dismiss();
                } else {
                    Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                    onSignupFailed();
                    progressDialog.dismiss();
                }
            }
        });
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
            passwordText.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        }

        try {
            UserDataValidator.validateReEnterPassword(reEnterPassword, password);
            reEnterPasswordText.setError(null);
        } catch (NotValidDataException e) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } catch (TooShortTextException | TooLongTextException e) {
            reEnterPasswordText.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        }

        return valid;
    }
}

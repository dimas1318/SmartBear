package com.example.android.smartbear;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.smartbear.base.activities.BaseActivity;
import com.example.android.smartbear.database.DatabaseManagerFirebase;
import com.example.android.smartbear.navigation.Navigator;
import com.example.android.smartbear.session.SessionManager;
import com.example.android.smartbear.session.SessionManagerImpl;
import com.example.android.smartbear.validator.UserDataValidator;
import com.example.android.smartbear.validator.exception.NotValidDataException;
import com.example.android.smartbear.validator.exception.TooLongTextException;
import com.example.android.smartbear.validator.exception.TooShortTextException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by parsh on 16.10.2017.
 */

public class SignupActivity extends BaseActivity {

    @BindView(R.id.input_name)
    EditText nameText;
    @BindView(R.id.input_surname)
    EditText surnameText;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_mobile)
    EditText mobileText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button signupBtn;
    @BindView(R.id.link_login)
    Button loginLinkBtn;
    @BindView(R.id.terms_conditions)
    CheckBox termsConditionsCheckBox;

    private static final String TAG = "SignupActivity";

    private SessionManager session;
    private FirebaseAuth auth;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        unbinder = ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        session = new SessionManagerImpl(getApplicationContext());
        navigator = new Navigator();

        nameText.setText("Dmitry");
        surnameText.setText("Parshin");
        emailText.setText("parshin@phystech.edu");
        mobileText.setText("9001234567");
        passwordText.setText("12345x_x_*xYYY");
        reEnterPasswordText.setText("12345x_x_*xYYY");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_signup)
    public void onSignupButtonClicked() {
        String name = nameText.getText().toString();
        String address = surnameText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        signUp(email, password, name);
    }

    @OnClick(R.id.link_login)
    public void onLoginButtonClicked() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @OnClick(R.id.terms_conditions)
    public void onTermsConditionsCheckBoxClicked() {
        signupBtn.setEnabled(termsConditionsCheckBox.isChecked());
    }

    public void signUp(final String email, final String password, final String name) {
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            return;
        }

//        signupBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseManagerFirebase.saveUser();

                    session.createUserSession(email, password, name, false);

                    progressDialog.dismiss();
                    navigator.navigateToLoginActivity(SignupActivity.this);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                } else {
                    progressDialog.dismiss();
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(getBaseContext(), "The given password is invalid. [ Password should be at least 6 characters ]", Toast.LENGTH_SHORT).show();
                        passwordText.requestFocus();
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(getBaseContext(), "The email address is badly formatted.", Toast.LENGTH_SHORT).show();
                        emailText.requestFocus();
                    } catch(FirebaseAuthUserCollisionException e) {
                        Toast.makeText(getBaseContext(), "The email address is already in use by another account.", Toast.LENGTH_SHORT).show();
                        emailText.requestFocus();
                    } catch (FirebaseNetworkException e) {
                        Toast.makeText(getBaseContext(), "A network error has occurred.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                    Log.e(TAG, "onComplete: Failed=" + task.getException());
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String address = surnameText.getText().toString();
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
            UserDataValidator.validateSurname(address);
            surnameText.setError(null);
        } catch (TooShortTextException e) {
            surnameText.setError("Enter Valid Surname");
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

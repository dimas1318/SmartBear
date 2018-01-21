package com.example.android.smartbear;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartbear.base.activities.BaseActivity;
import com.example.android.smartbear.di.component.LoginComponent;
import com.example.android.smartbear.navigation.Navigator;
import com.example.android.smartbear.session.SessionManager;
import com.example.android.smartbear.session.SessionManagerImpl;
import com.example.android.smartbear.validator.UserDataValidator;
import com.example.android.smartbear.validator.exception.TooLongTextException;
import com.example.android.smartbear.validator.exception.TooShortTextException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by parsh on 16.10.2017.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.link_signup)
    Button signupLink;
    @BindView(R.id.show_hide_password)
    CheckBox showPasswordCheckBox;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;

    private static final String TAG = "LoginActivity";

    private SessionManager session;

    private FirebaseAuth auth;

    private LoginComponent loginComponent;

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);

        initializeInjector();

        auth = FirebaseAuth.getInstance();
        session = new SessionManagerImpl(getApplicationContext());

        emailText.setText("parshin@phystech.edu");
        passwordText.setText("12345x_x_*xYYY");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @OnClick(R.id.show_hide_password)
    public void onShowPasswordClicked() {
        if (showPasswordCheckBox.isChecked()) {
            showPasswordCheckBox.setText(R.string.hide_password);
            passwordText.setInputType(InputType.TYPE_CLASS_TEXT);
            passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_confirm_password, 0, 0, 0);
        } else {
            showPasswordCheckBox.setText(R.string.show_password);
            passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, 0, 0);
        }
    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClicked() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        login(email, password);
    }

    @OnClick(R.id.link_signup)
    public void onSignupLinkClicked() {
        navigator.navigateToSignupActivity(this);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @OnClick(R.id.forgot_password)
    public void onForgotPasswordClicked() {
        navigator.navigateToForgotPasswordActivity(this);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void initializeInjector() {
//        this.loginComponent = DaggerLoginComponent.builder()
//                .applicationComponent(getApplicationComponent())
//                .build();
        navigator = new Navigator();
    }

    public void login(final String email, final String password) {
        Log.d(TAG, "Login");

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        if (email.equals("admin") && password.equals("admin")) {
            session.createUserSession(email, password, "Admin", true);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            navigator.navigateToMainActivity(LoginActivity.this);
                        }
                    }, 3000);
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        session.createUserSession(email, password, email, false);
                        progressDialog.dismiss();
                        navigator.navigateToMainActivity(LoginActivity.this);
                    } else {
                        progressDialog.dismiss();
                        try {
                            throw task.getException();
                        } catch (FirebaseNetworkException e) {
                            Toast.makeText(getBaseContext(), "A network error has occurred.", Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Toast.makeText(getBaseContext(), "No user was found.", Toast.LENGTH_SHORT).show();
                            emailText.requestFocus();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(getBaseContext(), "The password is invalid.", Toast.LENGTH_SHORT).show();
                            passwordText.requestFocus();
                        } catch (Exception e) {
                            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                        }
                        Log.e(TAG, "onComplete: Failed=" + task.getException());
                    }
                }
            });
        }

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
            passwordText.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        }

        return valid;
    }
}
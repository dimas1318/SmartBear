package com.example.android.smartbear;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by parsh on 16.10.2017.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private SessionManager session;

    private FirebaseAuth auth;

    private LoginComponent loginComponent;

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

        initializeInjector();

        ButterKnife.bind(this);

        emailText.setText("parshin@phystech.edu");
        passwordText.setText("12345x_x_*xYYY");

        session = new SessionManagerImpl(getApplicationContext());
    }

    private void initializeInjector() {
//        this.loginComponent = DaggerLoginComponent.builder()
//                .applicationComponent(getApplicationComponent())
//                .build();
        navigator = new Navigator();
    }

    @OnClick(R.id.btn_login)
    public void loginButtonClick() {
//        signIn(emailText.getText().toString(), passwordText.getText().toString());
        login();
    }

    @OnClick(R.id.link_signup)
    public void signupLinkClick() {
//        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//        startActivityForResult(intent, REQUEST_SIGNUP);
//        startActivity(intent);

//        finish();
        navigator.navigateToSignupActivity(this);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void signIn(final String email, final String password) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    session.createUserSession(email, password, email, false);
                    onLoginSuccess();
                } else {
                    onLoginFailed();
                }
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

        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();

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
            signIn(email, password);
            progressDialog.dismiss();
//            new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                    signIn(email, password);
//                    progressDialog.dismiss();
//                    }
//                }, 5000);
        }
    }


//    /**
//     * Implementation of successful signup logic
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                this.finish();
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);

        navigator.navigateToMainActivity(this);
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
//
//        finish();
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
            passwordText.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        }

        return valid;
    }
}
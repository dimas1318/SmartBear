package com.example.android.smartbear;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.smartbear.base.activities.BaseActivity;
import com.example.android.smartbear.navigation.Navigator;
import com.example.android.smartbear.validator.UserDataValidator;
import com.example.android.smartbear.validator.exception.TooShortTextException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by parsh on 21.01.2018.
 */

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.registered_email_id)
    EditText emailText;
    @BindView(R.id.backToLogin)
    Button backButton;
    @BindView(R.id.forgot_btn)
    Button submitButton;

    private static final String TAG = "ForgotPasswordActivity";

    private FirebaseAuth auth;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        unbinder = ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        initializeInjector();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        navigator.navigateToLoginActivity(ForgotPasswordActivity.this);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @OnTextChanged(R.id.registered_email_id)
    public void onEmailTextChanged() {
        submitButton.setEnabled(!emailText.getText().toString().isEmpty());
    }

    @OnClick(R.id.backToLogin)
    public void onBackButtonClicked() {
        onBackPressed();
    }

    @OnClick(R.id.forgot_btn)
    public void onSubmitButtonClicked() {
        String email = emailText.getText().toString();

        if (!validate(email)) {
            Toast.makeText(getBaseContext(), "Wrong email.", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Request is being processed...");
        progressDialog.show();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getBaseContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getBaseContext(), "The email address is badly formatted.", Toast.LENGTH_SHORT).show();
                                emailText.requestFocus();
                            }  catch (FirebaseNetworkException e) {
                                Toast.makeText(getBaseContext(), "A network error has occurred.", Toast.LENGTH_SHORT).show();
                            }catch (Exception e) {
                                Toast.makeText(getBaseContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }
                            Log.e(TAG, "onComplete: Failed=" + task.getException());
                        }
                    }
                });
    }

    private boolean validate(String email) {
        boolean valid = true;

        try {
            UserDataValidator.validateEmail(email);
            emailText.setError(null);
        } catch (TooShortTextException e) {
            emailText.setError("enter a valid email address");
            valid = false;
        }

        return valid;
    }

    private void initializeInjector() {
//        this.loginComponent = DaggerLoginComponent.builder()
//                .applicationComponent(getApplicationComponent())
//                .build();
        navigator = new Navigator();
    }
}

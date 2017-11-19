//package com.example.android.smartbear;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by Samsung on 12.11.2017.
// */
//
//public class EmailPasswordActivity extends AppCompatActivity {
//    private FirebaseAuth auth;
//    private FirebaseAuth.AuthStateListener authStateListener;
//
//    @BindView(R.id.input_email)
//    EditText emailText;
//    @BindView(R.id.input_password)
//    EditText passwordText;
//    @BindView(R.id.btn_login)
//    Button loginButton;
//    @BindView(R.id.link_signup)
//    TextView signupLink;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_email_password);
//        ButterKnife.bind(this);
//
//        auth = FirebaseAuth.getInstance();
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//
//                } else {
//                    // User is signed out
//
//                }
//                // ...
//            }
//        };
//    }
//
//    @OnClick(R.id.btn_login)
//    public void loginButtonClick() {
//        signIn(emailText.getText().toString(), passwordText.getText().toString());
//    }
//
//    @OnClick(R.id.link_signup)
//    public void signupLinkClick() {
//        signUp(emailText.getText().toString(), passwordText.getText().toString());
//    }
//
//
//    public void signIn(String email, String password) {
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(EmailPasswordActivity.this, "Authorization completed successfully", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(EmailPasswordActivity.this, "Authorization failed", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//
//    public void signUp(String email, String password) {
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(EmailPasswordActivity.this, "Registration completed successfully", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(EmailPasswordActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//}

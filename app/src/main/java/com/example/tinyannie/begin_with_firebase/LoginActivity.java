package com.example.tinyannie.begin_with_firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
//khai bao bien
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bien auth de set, get du lieu tu firebase
        auth = FirebaseAuth.getInstance();
        //neu da login trong firebase thi chuyen qua man hinh main
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }
        setContentView(R.layout.activity_login);
        //khoi tao gia tri cho bien
        inputEmail=(EditText) findViewById(R.id.email);
        inputPassword=(EditText) findViewById(R.id.password);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        btnSignup=(Button)findViewById(R.id.btn_signup);
        btnLogin=(Button) findViewById(R.id.btn_login);
        btnReset=(Button) findViewById(R.id.btn_reset_password);
        auth=FirebaseAuth.getInstance();
        //mo phong hanh dong cho cac doi tuong
        //btn login
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPassword.class));
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=inputEmail.getText().toString();
                final String password=inputPassword.getText().toString();
                //neu email rong
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter email address!",Toast.LENGTH_LONG).show();
                    return;
                }
                //neu password rong
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter password!",Toast.LENGTH_LONG).show();
                    return;
                }
                //hien thi progressbar
                progressBar.setVisibility(View.VISIBLE);
                //auth user
                auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                             if(!task.isSuccessful()){
                                 //co loi xay ra khi dang nhap
                                 if(password.length()<6){
                                     //neu pass<6 thi set error cho inputpassword
                                     inputPassword.setError(getString(R.string.minimum_password));
                                 }
                                 else{
                                     Toast.makeText(LoginActivity.this,getString(R.string.auth_failed),Toast.LENGTH_LONG).show();
                                 }
                                 progressBar.setVisibility(View.GONE);
                             }
                             //neu dang nhap thanh cong thi chuyen qua man hinh main
                             else{
                                 Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                 startActivity(intent);
                                 finish();
                             }

                            }
                        });
            }
        });
    }

}

package com.CNPM.letcook.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.CNPM.letcook.Controller.UserController;
import com.CNPM.letcook.Model.UserModel;
import com.CNPM.letcook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edEmail, edPassword, edRepassword;
    Button btnRegister;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.layout_register);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edRepassword = findViewById(R.id.edRepassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressbar);
        btnRegister.setOnClickListener(this);
    }

    private void Register() {
        final String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (email.isEmpty()) {
            edEmail.setError("Email trống");
            edEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Vui lòng nhập email");
            edEmail.requestFocus();
            return;
        }


        if (password.length() < 6) {
            edPassword.setError("Mật khẩu dài hơn 6 ký tự");
            edPassword.requestFocus();
            return;
        }


        if (password.isEmpty()) {

            edPassword.setError("Mật khẩu trống");
            edPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
//                    finish();
                    UserModel userModel = new UserModel();
                    userModel.setName(email);
                    userModel.setPic_profile("pic_profile");
                    String uid = task.getResult().getUser().getUid();
                    UserController userController = new UserController();
                    userController.InfoUserController(userModel, uid);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Tài khoản này đã được đăng ký", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                Register();
                break;
        }
    }
}

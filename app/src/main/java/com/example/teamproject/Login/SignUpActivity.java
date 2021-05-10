package com.example.teamproject.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.functions.FirebaseFunctions;

/**
 * Created by 김상윤 on 2020/11/10
 */

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG= "SignUpActivity";
    private FirebaseAuth mAuth;
    private FirebaseFunctions mFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();

        findViewById(R.id.signUpButton2).setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() { //로그인창에서 뒤로 가기를 눌렀을때 kill
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.signUpButton2): //회원가입이 성공적으로 이루어지면 토스트창 띄우고 로그인화면으로 이동
                    signUp();
                    break;
            }
        }
    };

    private void signUp() {
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();
        TextView emailwarning = (TextView)findViewById(R.id.emailWarning);
        TextView passwordwarning = (TextView)findViewById(R.id.passwordWarning);
        TextView passwordcheckwarning = (TextView)findViewById(R.id.passwordcheckWarning);

        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {
            if(password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startSignUpActivity2();
                                } else {
                                    if(task.getException() != null){
                                        startToast("중복된 이메일 입니다.");
                                    }
                                }
                            }
                        });
            }
            else{
                startToast("비밀번호가 일치하지 않습니다.");
                passwordcheckwarning.setVisibility(View.VISIBLE);//비밀번호 확인과 비밀번호가 다를때 토스트창 생성
            }

        }

        else {
            startToast("회원정보를 확인해주세요");
            if (email.length() == 0) {
                emailwarning.setVisibility(View.VISIBLE);
            }
            if (password.length() == 0) {
                passwordwarning.setVisibility(View.VISIBLE);
            }
            if (passwordCheck.length() == 0) {
                passwordcheckwarning.setVisibility(View.VISIBLE);
            }
        }

    }


    private void startToast(String message) { //토스트창 함수, 위 signUp함수에서 전달받은 message를 토스트창에 띄움
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startSignUpActivity2() { //추가정보입력란으로 이동
        Intent intent = new Intent(this, com.example.teamproject.Login.SignUpActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
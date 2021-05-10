package com.example.teamproject.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.example.teamproject.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 김상윤 on 2020/11/10
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG= "SignUpActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotosignupButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoresetButton).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onBackPressed() { //로그인창에서 뒤로 가기를 눌렀을때 kill
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.loginButton): //로그인 버튼을 눌렀을때
                    Login();
                    break;
                case (R.id.gotosignupButton): // 회원가입버튼을 눌렀을때
                    startAuthActivity();
                    break;
                case (R.id.gotoresetButton): //비밀번호찾기버튼을 눌렀을때
                    startResetActivity();
                    break;
            }
        }
    };

    private void Login() { //로그인 함수
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();

        TextView emailwarning = (TextView)findViewById(R.id.emailWarning);
        TextView passwordwarning = (TextView)findViewById(R.id.passwordWarning);

        if(email.length() > 0 && password.length() > 0 ) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("환영합니다.");
                                startMainActivity(); //로그인이 정상적으로 완료되어 메인액티비티를 호출하는 함수
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                if (task.getException() != null){
                                    startToast("이메일 또는 비밀번호 오류입니다.");
                                }
                            }
                        }
                    });
        }
        else {
            startToast("이메일 또는 비밀번호를 입력해주세요");
            if (email.length() == 0) {
                emailwarning.setVisibility(View.VISIBLE);
            }
            if (password.length() == 0) {
                passwordwarning.setVisibility(View.VISIBLE);
            }
        }

    }

    private void startToast(String message) { //토스트창 함수, 위 signUp함수에서 전달받은 message를 토스트창에 띄움
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() { //메인액티비티로 넘어가는 인텐트함수
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startAuthActivity() { //약관동의액티비티로 넘어가는 인텐트함수
        Intent intent = new Intent(this, com.example.teamproject.Login.AuthActivity.class);
        startActivity(intent);
    }

    private void startResetActivity() { //비밀번호변경액티비티로 넘어가는 인텐트함수
        Intent intent = new Intent(this,PasswordResetActivity.class);
        startActivity(intent);
    }
}
package com.example.teamproject.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 김상윤 on 2020/11/10
 */

public class PasswordResetActivity extends AppCompatActivity {
    private static final String TAG= "PasswordResetActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.sendButton).setOnClickListener(onClickListener);
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
                case (R.id.sendButton):
                    send(); //비밀번호 찾기에 필요한 정보들을 보내는 함수 실행
                    break;
            }
        }
    };

    private void send() {
        String email = ((EditText)findViewById(R.id.searchEmail)).getText().toString();

        if(email.length() > 0  ) {

            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startToast("이메일에서 인증번호를 확인해주세요"); //토스트창이 확인되면 비밀번호 재설정 링크가 포함된 이메일 발송 완료
                                startLoginActivity();
                               Log.d(TAG, "Email sent.");
                            }
                        }
                    });
        }
        else {
            startToast("이메일을 입력해주세요");
        }

    }

    private void startToast(String message) { //토스트창 함수, 위 signUp함수에서 전달받은 message를 토스트창에 띄움
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void startLoginActivity() { //비밀번호 찾기가 완료되면 로그인페이지로 이동
        Intent intent = new Intent(this, com.example.teamproject.Login.LoginActivity.class);
        startActivity(intent);
    }
}
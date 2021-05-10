package com.example.teamproject.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.functions.FirebaseFunctions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 김상윤 on 2020/11/10
 */
public class AuthActivity extends AppCompatActivity {
    private static final String TAG= "SignUpActivity";
    private FirebaseAuth mAuth;
    private FirebaseFunctions mFunctions;
    CheckBox pushcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();
        TextView text1 = (TextView) findViewById(R.id.text_1);
        text1.setText(readText1());
        TextView text2 = (TextView) findViewById(R.id.text_2);
        text2.setText(readText2());
        findViewById(R.id.NextButton).setOnClickListener(onClickListener);
        pushcheck = (CheckBox) findViewById(R.id.push_check);

        pushcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (pushcheck.isChecked() == true) {
                    startToast("푸쉬알림에 동의하셨습니다.");
                }
            }
        });
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.NextButton):
                    startSignUpActivity();//약관동의가 끝나면 회원가입 창으로 이동

            }
        }
    };

    private void startToast(String message) { //토스트창 함수, 위 signUp함수에서 전달받은 message를 토스트창에 띄움
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startSignUpActivity() { //회원가입 페이지로 이동
        Intent intent = new Intent(this,SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private String readText1() { //텍스트파일 1 입력
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text_1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while ( i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String (byteArrayOutputStream.toByteArray(),"MS949");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    private String readText2() { //텍스트파일 2 입력
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text_2);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while ( i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String (byteArrayOutputStream.toByteArray(),"MS949");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
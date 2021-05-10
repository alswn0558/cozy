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
import com.example.teamproject.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;

/**
 * Created by 김상윤 on 2020/11/10
 */

public class SignUpActivity2 extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;
    private FirebaseFunctions mFunctions;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();

        findViewById(R.id.signUpButton2).setOnClickListener(onClickListener);
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
                    startToast("회원가입이 성공적으로 완료되었습니다.");
                    profileUpdate();
                    break;
            }
        }
    };

    private void profileUpdate() {
        String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        String phonenumber = ((EditText) findViewById(R.id.phonenumberEditText)).getText().toString();
        String birthday = ((EditText) findViewById(R.id.birthdayEditText)).getText().toString();
        String gender = ((EditText) findViewById(R.id.genderEditText)).getText().toString();
        String address = ((EditText) findViewById(R.id.addressEditText)).getText().toString();
        String school = ((EditText) findViewById(R.id.schoolEditText)).getText().toString();
        String major = ((EditText) findViewById(R.id.majorEditText)).getText().toString();

        if (name.length() > 0 && phonenumber.length() > 0 && birthday.length() > 0 && gender.length() > 0 && address.length() > 0 && school.length() > 0 && major.length() > 0 ) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Memberinfo memberinfo = new Memberinfo(name, phonenumber, birthday, gender, address, school, major);
            db.collection("Users").document(user.getUid()).set(memberinfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            startMainActivity();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
            // 이름,핸드폰번호,생일,성별,주소,학교,전공등을 memberinfo에서 받아온다.
        }
        else {
            startToast("모든 정보사항들을 입력하시기 바랍니다.");
        }
    }
    private void startToast(String message) { //토스트창 함수, 위 signUp함수에서 전달받은 message를 토스트창에 띄움
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() { //메인액티비티로 넘어가는 인텐트함수
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

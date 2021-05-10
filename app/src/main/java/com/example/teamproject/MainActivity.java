package com.example.teamproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.teamproject.Board.BoardActivity;
import com.example.teamproject.Login.LoginActivity;
import com.example.teamproject.Login.ProfileimageActivity;
import com.example.teamproject.databinding.ActivityMainBinding;
import com.example.teamproject.viewmodel.ScheduleViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    ScheduleViewModel viewModel;

    @Override
    public void onBackPressed() { //로그인이 되어있는 상태에서 뒤로 가기를 눌렀을때 kill
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) { //앱을 실행했을때 현재 사용자가 로그인이 되어있는지를 판별하고
            startLoginActivity();                                //로그인이 안되어있으면 메인액티비티가 아닌 로그인액티비티를 호출
        }

        if(Singleton.getInstance().name == null)
        {
            startLoadingActivity();
        }

        /*** Created by 양민주 ***/
        // dataBinding 참조 및 뷰 설정.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        // toolbar 셋팅.
        setSupportActionBar(binding.toolbar);
        // navigation 컨트롤러 참조.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController navController = navHostFragment.getNavController();
        // bottom navigation 설정.
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);


        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) { //회원정보 가져오는 코드
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (document.exists()) {
                                Singleton.getInstance().name = document.getData().get("name").toString();
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.upDateSchedule();
    }

    //함수 만들어서 layour -> navigation -> nav_graph -> 원하는 fragment -> 원하는 button 의 onClick으로 등록해줘야함. 매개변수로 View view 써줘야함
    
    public void LogOut(View view) /*** Created by 김상윤 on 2020/11/18 ***/
    {
        FirebaseAuth.getInstance().signOut();
        startLoginActivity();
    }

    public void deleteAccount(View view) //회원탈퇴 함수 /*** Created by 김상윤 on 2020/11/18 ***/
    {

        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_DARK);
        alert_confirm.setMessage("정말로 회원을 탈퇴하시겠습니까?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        db.collection("Users").document(user.getUid())
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(MainActivity.this, "회원탈퇴가 정상적으로 완료 되었습니다.", Toast.LENGTH_LONG).show();
                                                        finish();
                                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                    }
                                                });
                                    }
                                });
                    }
                }
        );
        alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "회원탈퇴가 취소되었습니다.", Toast.LENGTH_LONG).show();
            }
        });
        alert_confirm.show();
    }



    private void startLoginActivity() { //로그인액티비티로 넘어가는 인텐트함수
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void startLoadingActivity() // 유저 정보 가져오기
    {
        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
        startActivity(intent);
    }


    public void startBoardActivity1(View view) {
        Intent myIntent = new Intent(getApplicationContext(), BoardActivity.class);

        myIntent.putExtra("select",1);

        startActivityForResult(myIntent,5);
    }
    public void startBoardActivity2(View view) {
        Intent myIntent = new Intent(getApplicationContext(), BoardActivity.class);

        myIntent.putExtra("select",2);

        startActivityForResult(myIntent,5);
    }
    public void startBoardActivity3(View view) {
        Intent myIntent = new Intent(getApplicationContext(), BoardActivity.class);

        myIntent.putExtra("select",3);

        startActivityForResult(myIntent,5);
    }
    public void startBoardActivity4(View view) {
        Intent myIntent = new Intent(getApplicationContext(), BoardActivity.class);

        myIntent.putExtra("select",4);

        startActivityForResult(myIntent,5);
    }

    public void startProfileimageActivity(View view) {
        Intent intent = new Intent(this, ProfileimageActivity.class);
        intent.putExtra("bitmapdata",5);
        startActivity(intent);
    }



}


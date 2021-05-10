package com.example.teamproject.Board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.example.teamproject.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 신무현
 */

//게시글을 보여주는 Activity

public class BoardActivity extends AppCompatActivity {

    ListView listview1;
    
    FloatingActionButton go_write_btn;
    String whatBoard;


    //뒤로가기 누를 시 메인으로 돌아가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);

        setResult(RESULT_OK,myIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // 메인에서 어떤 게시판을 선택했는지 받아오고
        Intent inIntent = getIntent();
        int select = inIntent.getIntExtra("select",1);
        whatBoard = inIntent.getStringExtra("whatBoard");

        //그에 맞는 데이터베이스 이름으로 매칭해주기
        if(whatBoard == null)
        switch(select)
        {
            case 1: whatBoard = "message"; break;
            case 2: whatBoard = "message2"; break;
            case 3: whatBoard = "message3"; break;
            case 4: whatBoard = "message4"; break;
        }

        //어느 게시판인지 표시
        TextView whatboardtext = (TextView) findViewById(R.id.WhatBoardText);
        if(whatBoard.equals("message"))
            whatboardtext.setText("자유게시판");
        else if(whatBoard.equals("message2"))
            whatboardtext.setText("세탁실");
        else if(whatBoard.equals("message3"))
            whatboardtext.setText("배달 1 / N");
        else if(whatBoard.equals("message4"))
            whatboardtext.setText("장터/나눔");


        // 게시판에 저장된 글들 읽어오기
        ReadDatabase();

        //글쓰기 버튼 클릭 시
        go_write_btn = (FloatingActionButton) findViewById(R.id.go_write_btn);
        go_write_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                go_write_button(v);
            }

        });

    }
    
    //데이터베이스 읽기
    public void ReadDatabase()
    {
        //리스트뷰 어댑터 연결
        listview1 = (ListView) findViewById(R.id.listView);
        final MyAdapter adapter = new MyAdapter(this);
        listview1.setAdapter(adapter);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(whatBoard).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                // 저장된 글 리스트뷰에 추가
                Board board = snapshot.getValue(Board.class);
                adapter.Add(board);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //글쓰기 버튼 누를 시 WriteActivity로 이동, 어느 게시판인지 정보 전달
    public void go_write_button(View v)
    {
        Intent myIntent = new Intent(getApplicationContext(), WriteActivity.class);
        myIntent.putExtra("whatBoard",whatBoard);
        startActivityForResult(myIntent,0);
    }

}

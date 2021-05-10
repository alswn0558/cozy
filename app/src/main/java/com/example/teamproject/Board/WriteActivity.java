package com.example.teamproject.Board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.example.teamproject.Singleton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 신무현
 */

// 글 쓰기 Activity

public class WriteActivity extends AppCompatActivity {

    Button button1;
    EditText edittext1;
    EditText edittext2;


    Board board;
    String whatBoard;

    boolean isUpdate;


    @Override
    public void onBackPressed() {
        if(isUpdate) {
            super.onBackPressed();
            Intent myIntent = new Intent(getApplicationContext(), PostActivity.class);
            myIntent.putExtra("board", board);
            startActivity(myIntent);
            finish();
        }
        else
        {
            super.onBackPressed();

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        button1 = (Button) findViewById(R.id.button21);
        edittext1 = (EditText) findViewById(R.id.edittext21);
        edittext2 = (EditText) findViewById(R.id.edittext22);


        //어느 게시판인지 받아옴
        Intent inIntent = getIntent();
        whatBoard = inIntent.getStringExtra("whatBoard");
        board = (Board) inIntent.getSerializableExtra("board");
        isUpdate = inIntent.getBooleanExtra("Update",false);
        if(whatBoard == null)
            whatBoard = board.getWhatBoard();

        if(isUpdate)
        {
            button1.setText("수정");
            edittext1.setText(board.getTitle());
            edittext2.setText(board.getContent());
        }

        edittext2.setHorizontallyScrolling(false);

        //글 등록 버튼을 누르면, click1button 함수 호출
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(isUpdate == false)
                {
                    if(edittext1.getText().toString().length() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(edittext2.getText().toString().length() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        click1button(v);
                    }
                }
                else
                {
                    update1button(v);
                }

            }
        });


    }

    //글 등록 버튼
    public void click1button(View v)
    {
        SimpleDateFormat dateform = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String key;
        //제목 / 날짜 / 내용 / 고유 키값 / 어떤 게시판인지 / 유저 이름
        Board board = new Board(edittext1.getText().toString(),
                dateform.format(cal.getTime()).toString(),
                edittext2.getText().toString(),
                key = database.child("message").push().getKey(),
                whatBoard, Singleton.getInstance().name);

        database.child(whatBoard).child(key).setValue(board); // 아까 받아온 게시판에 글 추가
        edittext1.setText("");
        edittext2.setText("");

        Toast.makeText(this, "글을 등록했습니다", Toast.LENGTH_SHORT).show();

        // 그리고 게시판으로 돌아감
        Intent myIntent = new Intent(getApplicationContext(), BoardActivity.class);
        setResult(RESULT_OK,myIntent);
        finish();

    }

    
    //글 수정 버튼
    public void update1button(View v)
    {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        Board boarding = new Board(edittext1.getText().toString(),board.getDate(),edittext2.getText().toString(),board.getId(),board.getWhatBoard(),board.getName() );

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("title",boarding.getTitle());
        updateMap.put("content",boarding.getContent());

        database.child(board.getWhatBoard()).child(boarding.getId()).updateChildren(updateMap); // 아까 받아온 게시판에 글 수정
        edittext1.setText("");
        edittext2.setText("");

        Toast.makeText(this, "글을 등록했습니다", Toast.LENGTH_SHORT).show();

        // 그리고 게시판으로 돌아감
        Intent myIntent = new Intent(getApplicationContext(), PostActivity.class);
        myIntent.putExtra("board",boarding);
        startActivity(myIntent);
        finish();

    }

}

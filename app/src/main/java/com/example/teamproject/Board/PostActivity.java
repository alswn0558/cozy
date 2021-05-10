package com.example.teamproject.Board;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.example.teamproject.Singleton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 신무현
 */

// 글 내용 / 댓글을 보여주는 Activity

public class PostActivity extends AppCompatActivity {

    LinearLayout baseLayout;
    TextView titleText;
    TextView contentText;
    TextView writerText;
    TextView dateText;

    ListView listview1;

    EditText CommentEdit;
    ImageButton CommentBtn;

    Board board;

    MyAdapter2 adapter;

    //뒤로 가기 대신 BoardActivity 호출
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(getApplicationContext(), BoardActivity.class);
        myIntent.putExtra("whatBoard",board.getWhatBoard());
        startActivity(myIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);




        //액션바 추가 (뒤로가기 버튼, 메뉴 생성)
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼
        actionBar.setHomeButtonEnabled(true);
        
        //댓글 리스트뷰 어댑터 연결
        listview1 = (ListView) findViewById(R.id.commentList);
        adapter = new MyAdapter2(this);
        listview1.setAdapter(adapter);

        // 제목, 내용, 그리고 id 값을 받아옴
        Intent inIntent = getIntent();
        board = (Board) inIntent.getSerializableExtra("board");

        //객체 배정
        baseLayout = (LinearLayout) findViewById(R.id.post);
        titleText = (TextView) findViewById(R.id.TitleText);
        contentText = (TextView) findViewById(R.id.ContentText);
        writerText = (TextView) findViewById(R.id.WriterText);
        dateText = (TextView) findViewById(R.id.DateText);
        //내용은 스크롤 되게
        contentText.setMovementMethod(ScrollingMovementMethod.getInstance());

        // 제목 게시글 넣어주기
        titleText.setText(board.getTitle());
        contentText.setText(board.getContent());
        writerText.setText(board.getName());
        dateText.setText(board.getDate());
        
        //댓글 달기 -> clickComment 함수 호출
        CommentEdit = (EditText) findViewById(R.id.commentEdit);
        CommentBtn = (ImageButton) findViewById(R.id.CommentBtn);
        CommentBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(CommentEdit.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    clickCommnet(v);
                }
            }

        });

        //댓글 리스트뷰 추가 , 받아온 id 값에 있는 값들을 읽어옴
        CreateCommentList();

    }

    //메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Singleton.getInstance().name != null && Singleton.getInstance().name.equals(board.getName()))    // 자기가 쓴 글일 때만 메뉴 노출 (수정 / 삭제 가능)
        {
            super.onCreateOptionsMenu(menu);
            MenuInflater mInflater = getMenuInflater();
            mInflater.inflate(R.menu.update_delete_menu, menu);
            return true;
        }
        return true;
    }

    //메뉴 역할 부여
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            
            case R.id.menuUpdate: // 수정
                UpdatePost();
                return true;
                
                
            case R.id.menuDelete: // 삭제
                DeletePost();
                return true;
                
            case android.R.id.home:      // 뒤로가기 버튼
                Intent myIntent = new Intent(getApplicationContext(), BoardActivity.class);
                myIntent.putExtra("whatBoard",board.getWhatBoard());
                startActivity(myIntent);
                finish();
                return true;

        }
        return false;
    }

    //댓글 리스트 추가
    public void CreateCommentList()
    {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("comment").child(board.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {

                Com com = snapshot.getValue(Com.class);
                adapter.Add(com);
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

    //글 수정
    public void UpdatePost()
    {
        Intent myIntent = new Intent(getApplicationContext(), WriteActivity.class);
        myIntent.putExtra("Update",true);
        myIntent.putExtra("board",board);
        startActivity(myIntent);
        finish();
    }

    //글 삭제
    public void DeletePost()
    {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = mDatabase.getReference().child(board.getWhatBoard()).child(board.getId());
        dataRef.removeValue();


        Intent myIntent = new Intent(getApplicationContext(), BoardActivity.class);
        myIntent.putExtra("whatBoard",board.getWhatBoard());
        startActivity(myIntent);
        finish();
    }

    //댓글 등록 함수
    public void clickCommnet(View v)
    {
        SimpleDateFormat dateform = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        
        Com com = new Com(CommentEdit.getText().toString(),board.getId(),dateform.format(cal.getTime()),Singleton.getInstance().name);
        database.child("comment").child(board.getId()).push().setValue(com); // comment의 id(게시글) 아래에 댓글 저장

        CommentEdit.setText("");

        Toast.makeText(this, "댓글을 등록했습니다", Toast.LENGTH_SHORT).show();

    }




}

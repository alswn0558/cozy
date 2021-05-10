package com.example.teamproject.Board;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teamproject.R;

import java.util.ArrayList;

/**
 * Created by 신무현
 */

//게시글 리스트뷰를 위한 어댑터

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<Board> data = new ArrayList<Board>();

    MyAdapter(Context context)
    {
        this.context = context;
    }


    @Override
    public int getCount() {
        return data.size();
    } 

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    } 

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int viewType = getItemViewType(position);

        if(convertView==null) // list_lay 레이아웃과 연결
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_lay, parent, false);
        }

        TextView textView1 = (TextView) convertView.findViewById(R.id.textView);
        TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
        TextView textView3 = (TextView) convertView.findViewById(R.id.textView3);

        final Board board = data.get(data.size()-1 - position); // 역순 배치 (가장 최신 글이 가장 위에 표시되게)
        
        //제목, 날짜, 내용 배치
        textView1.setText(board.getTitle());
        textView2.setText(board.getDate());
        textView3.setText(board.getContent());

        //게시글을 눌렀을 때, PostActivity로 이동
        LinearLayout itemLay = (LinearLayout)convertView.findViewById(R.id.ListArea);
        itemLay.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(v.getContext(), PostActivity.class);
                myIntent.putExtra("board",board);
                ((Activity)context).startActivity(myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                ((Activity)context).finish();
            }

        });


        return convertView;

    }

    public void Add(Board b)
    {
        data.add(b);
    } // 데이터 추가





}
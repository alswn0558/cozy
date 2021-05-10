package com.example.teamproject.Board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.Singleton;

import java.util.ArrayList;

/**
 * Created by 신무현
 */

//댓글 리스트뷰를 위한 어댑터

public class MyAdapter2 extends BaseAdapter {

    Context context;
    ArrayList<Com> data = new ArrayList<Com>();

    MyAdapter2(Context context)
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

        if(convertView==null) // post_lay 레이아웃과 연결결
       {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.post_lay, parent, false);
        }

        
        TextView textView1 = (TextView) convertView.findViewById(R.id.textView);
        TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
        TextView textView3 = (TextView) convertView.findViewById(R.id.textView3);

        final Com com = data.get(position); // 먼저 달린 댓글이 가장 위로

        // user id, 날짜, 댓글내용 배치
        textView1.setText(com.getName());
        textView2.setText(com.getDate());
        textView3.setText(com.getComment());


        return convertView;

    }

    public void Add(Com b)
    {
        data.add(b);
    }





}

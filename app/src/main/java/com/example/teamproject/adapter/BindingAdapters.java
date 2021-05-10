package com.example.teamproject.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.teamproject.R;
import com.example.teamproject.model.Schedule;
/**
 * Created by 양민주
 */
public class BindingAdapters {

    @BindingAdapter({"android:imgRes"})
    public static void img(ImageView imageView, String type) {
        if(type.equals(Schedule.TYPE_DORMITORY))
            imageView.setImageResource(R.drawable.round_shape_red);
        if(type.equals(Schedule.TYPE_INDIVIDUAL))
            imageView.setImageResource(R.drawable.round_shape_blue);
    }

    @BindingAdapter({"android:visible"})
    public static void btnVisible(ImageButton imageView, String type) {
        if(type.equals(Schedule.TYPE_DORMITORY))
            imageView.setVisibility(View.GONE);
        else
            imageView.setVisibility(View.VISIBLE);
    }


}

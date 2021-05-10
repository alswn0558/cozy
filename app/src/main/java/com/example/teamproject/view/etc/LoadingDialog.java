package com.example.teamproject.view.etc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.example.teamproject.R;
/**
 * Created by 양민주
 */
public class LoadingDialog extends ProgressDialog {

     public static final int DATA_LOADING_DIALOG_TYPE = 0;

     private final int theme;

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.theme = theme;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(theme == DATA_LOADING_DIALOG_TYPE)
            setContentView(R.layout.loading_dialog);
    }
}

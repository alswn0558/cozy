package com.example.teamproject.Login;

import android.graphics.Bitmap;

/**
 * Created by 김상윤 on 2020/11/18
 */


public class DataManager {
    private static DataManager _Instance;

    public static DataManager getInstance() {
        if(_Instance == null) {
            _Instance = new DataManager();
        }
        return _Instance;
    }

    private Bitmap data;

    public  void setData(Bitmap data) {
        this.data = data;
    }

    public  Bitmap getData() {
        return data;
    }

}

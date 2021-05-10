package com.example.teamproject.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teamproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView info1 = view.findViewById(R.id.maintextView1);
        final TextView info2 = view.findViewById(R.id.maintextView2);
        final TextView info3 = view.findViewById(R.id.maintextView3);
        final ImageView infoimage = view.findViewById(R.id.mainimageView);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) { //회원정보 가져오는 코드
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (document.exists()) {
                                info1.setText(document.getData().get("name").toString());
                                info2.setText("국민대 / " + document.getData().get("major").toString());
                                info3.setText(document.getData().get("gender").toString());
                                if (document.getData().get("data") != null) {
                                    String encodedstr = document.getData().get("data").toString();
                                    Bitmap encodedbit = StringToBitmap(encodedstr);
                                    infoimage.setImageBitmap(encodedbit);
                                }
                                else {
                                }
                            }
                        }
                    }
                }
            });
        }
        return view;
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
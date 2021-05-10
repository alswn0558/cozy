package com.example.teamproject.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teamproject.R;
import com.example.teamproject.Singleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by 김상윤 on 2020/11/18
 */


public class UserInfoFragment extends Fragment {
    private static final String TAG = "UserInfofragment";

    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        //final ImageView profileImageView = view.findViewById(R.id.profileImageView);

        final TextView nameuserinfo = view.findViewById(R.id.nameUserInfo);
        final TextView birthdayuserinfo = view.findViewById(R.id.birthdayUserInfo);
        final TextView genderuserinfo = view.findViewById(R.id.genderUserInfo);
        final TextView phonenumberuserinfo = view.findViewById(R.id.phonenumberUserInfo);
        final TextView addressuserinfo = view.findViewById(R.id.addressUserInfo);
        final TextView schooluserinfo = view.findViewById(R.id.schoolUserInfo);
        final TextView majoruserinfo = view.findViewById(R.id.majorUserInfo);
        final Button logout = view.findViewById(R.id.logoutButton);


        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { //회원정보 가져오는 코드
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            /**if(document.getData().get("photoUrl") != null){
                                Glide.with(getActivity()).load(document.getData().get("photoUrl")).centerCrop().override(500).into(profileImageView);
                            }**/
                            nameuserinfo.setText(document.getData().get("name").toString());
                            birthdayuserinfo.setText(document.getData().get("birthday").toString());
                            genderuserinfo.setText(document.getData().get("gender").toString());
                            phonenumberuserinfo.setText(document.getData().get("phonenumber").toString());
                            addressuserinfo.setText(document.getData().get("address").toString());
                            schooluserinfo.setText(document.getData().get("school").toString());
                            majoruserinfo.setText(document.getData().get("major").toString());

                            Singleton.getInstance().name = nameuserinfo.getText().toString();

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return view;
    }
}
package com.example.teamproject.repository;

import android.util.Log;

import com.example.teamproject.model.Schedule;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
/**
 * Created by 양민주
 */
public class ScheduleRepo {
    private static final String TAG = "ScheduleRepo";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // 임시 로그인 했다고 가정
    private static final String tmpWriter = "alswn0558@naver.com";

    // 일정을 서버에서 받아옵니다.
    public List<Schedule> loadSchedules(){
        List<Schedule> scheduleList = new ArrayList<>();
        Log.d(TAG, "loadSchedules: start ");

        // 공지일정을 받아온다.
        Task<QuerySnapshot> dormitoryTask =  db.collection("Schedule")
                .whereEqualTo("type", Schedule.TYPE_DORMITORY)
                .get();

        // 개인일정을 받아온다.
        Task<QuerySnapshot> individualTask = db.collection("Schedule")
                .whereEqualTo("type", Schedule.TYPE_INDIVIDUAL)
                .whereEqualTo("writer", tmpWriter)
                .get();
        // 서버에서 2개의 작업이 완료될때를 기다린다.
        try {
            QuerySnapshot dormitoryQuerySnapshot = Tasks.await(dormitoryTask);
            QuerySnapshot individualQuerySnapshot = Tasks.await(individualTask);
            for(DocumentSnapshot documentSnapshot : dormitoryQuerySnapshot.getDocuments()){
                Schedule schedule = documentSnapshot.toObject(Schedule.class);
                scheduleList.add(schedule);
            }
            for(DocumentSnapshot documentSnapshot : individualQuerySnapshot.getDocuments()){
                Schedule schedule = documentSnapshot.toObject(Schedule.class);
                scheduleList.add(schedule);
            }
            Log.d(TAG, "loadSchedules: complete");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return scheduleList;
    }


    // 작성한 일정을 서버로 업로드합니다.
    public boolean uploadSchedule(Schedule schedule){
        Task<Void> task =  db.collection("Schedule")
                .document(schedule.getId())
                .set(schedule);
        try {
            Tasks.await(task);
            Log.d(TAG, "서버 저장 완료. ");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return task.isSuccessful();
    }
    // 일정을 삭제 합니다.
    public boolean deleteSchedule(String id){
        Task<Void> task = db.collection("Schedule").document(id)
                .delete();
        try {
            Tasks.await(task);
            Log.d(TAG, "서버 삭제 완료.");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return task.isSuccessful();
    }
}

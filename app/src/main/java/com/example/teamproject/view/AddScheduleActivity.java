package com.example.teamproject.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.teamproject.R;
import com.example.teamproject.databinding.ActivityAddScheduleBinding;
import com.example.teamproject.model.Schedule;
import com.example.teamproject.utill.DateOperation;
import com.example.teamproject.view.etc.LoadingDialog;
import com.example.teamproject.viewmodel.ScheduleViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.example.teamproject.utill.DateOperation.getDate;
/**
 * Created by 양민주
 */
public class AddScheduleActivity extends AppCompatActivity {

    private ScheduleViewModel viewModel;
    private ActivityAddScheduleBinding binding;
    private SimpleDateFormat sdf;
    private LoadingDialog loadingDialog;
    private final String writer = "alswn0558@naver.com";
    private static final String TAG = "AddScheduleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // viewModel 및 binding 설정.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_schedule);
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        // toolbar 셋팅.
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        binding.dateLayout.setOnClickListener(view -> showDatePicker());
        // 데이트 포멧 설정.
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 글쓰기 창 닫기 버튼 리스너 설정.
        binding.backCalendarBtn.setOnClickListener(view -> finish());
        // 로딩 다이얼 로그 설정.
        loadingDialog = new LoadingDialog(AddScheduleActivity.this, LoadingDialog.DATA_LOADING_DIALOG_TYPE);

        // Intent 수신후 Add 인지 기존글의 Update인지 판단한다.
        Intent intent = getIntent();

        Schedule updateSchedule = (Schedule) intent.getSerializableExtra("upDateSchedule");
        if(updateSchedule != null) {
            Log.d(TAG, "onCreate: " + updateSchedule.toString());
            binding.tvToolbarTitle.setText("글 수정");
            binding.addTitle.setText(updateSchedule.getTitle());
            binding.addContents.setText(updateSchedule.getContents());
            viewModel.setNewSchedule(updateSchedule);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(viewModel.getNewSchedule().getDate() != null){
            Date date = viewModel.getNewSchedule().getDate();
            int[] intDate = DateOperation.getIntDate(date);
            String text = String.format(getResources().getString(R.string.picked_date),
                    intDate[0],  intDate[1]+1, intDate[2]);
            binding.dateTextView.setText(text);
        }
    }

    // ToolBar 에 메뉴생성.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_schedule_activity_option_menu, menu);
        return true;
    }

    // 각 메뉴에대한 설정
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // 저장(글쓰기)버튼을 눌렀을때 로직.
        if(id == R.id.saveMenu){
            // 데이터 추출후 스케쥴 생성.
            String title = binding.addTitle.getText().toString();
            String contents = binding.addContents.getText().toString();
            Schedule newSchedule = viewModel.getNewSchedule();
            newSchedule.setWriter(writer);
            newSchedule.setTitle(title);
            newSchedule.setContents(contents);
            newSchedule.setType(Schedule.TYPE_INDIVIDUAL);


            // 스케쥴 검사
            if(!viewModel.inspectionSchedule(newSchedule)){
                Toast.makeText(AddScheduleActivity.this,
                        getResources().getString(R.string.inspection_schedule_error),
                        Toast.LENGTH_SHORT).show();
            }else{
                SaveScheduleAsyncTask asyncTask = new SaveScheduleAsyncTask(viewModel);
                asyncTask.execute(viewModel.getNewSchedule());
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // datePicker 보여주는 함수
    private void showDatePicker(){
        //현재 날짜 받아오기.
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        //데이트 포멧팅
        String[] dateArr = sdf.format(date).split("-");
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1])-1;
        int day = Integer.parseInt(dateArr[2]);
        // DatePickerDialog
        DatePickerDialog dialog = new DatePickerDialog(this, listener, year, month, day );
        dialog.show();
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
    }

    // datePicker 날짜 선택 리스너.
    private final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // 선택된 날짜 -> String
            String text = String.format(getResources().getString(R.string.picked_date),
                    year, monthOfYear+1, dayOfMonth);
            // 데이트에 셋팅
            binding.dateTextView.setText(text);
            // 뷰모델 객체에 셋팅.
            viewModel.getNewSchedule().setDate(getDate(year,monthOfYear+1,dayOfMonth));
        }
    };

    // DB 접근 비동기 작업.
    public class SaveScheduleAsyncTask extends AsyncTask<Schedule, Void, Boolean> {

        private final ScheduleViewModel viewModel;
        private static final String TAG = "SaveScheduleAsyncTask";

        public SaveScheduleAsyncTask(ScheduleViewModel viewModel) {
            this.viewModel = viewModel;
        }
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
            loadingDialog.show();
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Schedule... schedules) {
            boolean result = false;
            Log.d(TAG, "doInBackground:");
            for(Schedule schedule : schedules)
                result = viewModel.saveSchedule(schedule);
            return result;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d(TAG, "onPostExecute:"+ aBoolean);
            loadingDialog.dismiss();

            if(aBoolean){
                finish();
            }else{
                Toast.makeText(AddScheduleActivity.this,
                        getResources().getString(R.string.save_schedule_error),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
package com.teamopendata.mindcareapp.ui.notifications;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.MainActivity;
import com.teamopendata.mindcareapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddRecordActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        final RecyclerView recyclerView = findViewById(R.id.recordAddRecyclerview);
        //일자 가져오기
        final TextView dateText = findViewById(R.id.dateTextView);
        LinearLayout dateLayout = findViewById(R.id.DateViewLayout);

        long now =System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy.MM.dd");
        String getTime = simpleDate.format(mDate);
        dateText.setText("Date : "+getTime);

        ArrayList<String> arrayList = new ArrayList<String>();
        //defalt 기관 정보 이후 DAO 생성하고 변경
        arrayList.add("a");

        final RecordAddListAdapter adapter = new RecordAddListAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        //리사이클러뷰 일정 아이템 추가 버튼 구현
        ImageButton itemAddBtn = findViewById(R.id.addImageButton);
        itemAddBtn.setClickable(true);
        itemAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem("a");
                adapter.notifyDataSetChanged();
            }
        });

        Button saveBtn = findViewById(R.id.recordSaveButton);
        saveBtn.setClickable(true);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateText.setText("Date: "+year+"."+month+"."+dayOfMonth);
            }
        };

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),callbackMethod,2021,11,01);
                datePickerDialog.show();
            }
        });

    }
}

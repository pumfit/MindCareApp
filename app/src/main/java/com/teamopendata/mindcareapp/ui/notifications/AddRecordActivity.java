package com.teamopendata.mindcareapp.ui.notifications;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class AddRecordActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        final RecyclerView recyclerView = findViewById(R.id.recordAddRecyclerview);
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
        //일자 가져오기
        final TextView dateText = findViewById(R.id.dateTextView);
        LinearLayout dateLayout = findViewById(R.id.DateViewLayout);

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

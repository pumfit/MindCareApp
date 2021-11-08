package com.teamopendata.mindcareapp.ui.notifications;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class AddRecordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        final RecyclerView recyclerView = findViewById(R.id.recordAddRecyclerview);
        ArrayList<String> arrayList = new ArrayList<String>();

        //defalt 기관 정보 이후 DAO 생성하고 변경
        arrayList.add("21.09.14");
        arrayList.add("21.09.14~21.10.04");

        RecordAddListAdapter adapter = new RecordAddListAdapter(arrayList);
        recyclerView.setAdapter(adapter);
    }
}

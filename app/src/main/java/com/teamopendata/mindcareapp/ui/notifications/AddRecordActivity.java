package com.teamopendata.mindcareapp.ui.notifications;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

public class AddRecordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        final RecyclerView recyclerView = findViewById(R.id.recordAddRecyclerview);

        RecordAddListAdapter adapter = new RecordAddListAdapter();
        recyclerView.setAdapter(adapter);
    }
}

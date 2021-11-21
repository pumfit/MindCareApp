package com.teamopendata.mindcareapp.ui.Graph;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.teamopendata.mindcareapp.R;

public class CalendarDialog extends Dialog {
    private final String TAG = CalendarDialog.class.getSimpleName();
    android.widget.Button cancel_button_graph;

    public CalendarDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calender_dialog_graph);
        cancel_button_graph = findViewById(R.id.cancel_button_graph);

        cancel_button_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

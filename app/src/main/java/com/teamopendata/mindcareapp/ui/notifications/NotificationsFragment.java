package com.teamopendata.mindcareapp.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private ImageButton addRecordBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recordRecyclerview);
        addRecordBtn = root.findViewById(R.id.addImageButton);

        ArrayList<String> arrayList = new ArrayList<String>();

        //defalt 기관 정보 이후 DAO 생성하고 변경
        arrayList.add("21.09.14");
        arrayList.add("21.09.14~21.10.04");
        arrayList.add("21.10.16");
        arrayList.add("21.10.06~21.10.17");

        RecordListAdapter adapter = new RecordListAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddRecordActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }




}

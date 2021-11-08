package com.teamopendata.mindcareapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.SelectActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    android.widget.Button back_select_button;
    TextView tvKeyword;

    String stress,fear, insomnia;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View HomeView = inflater.inflate(R.layout.fragment_home, container, false);
        back_select_button = HomeView.findViewById(R.id.back_select_button);
        tvKeyword = HomeView.findViewById(R.id.tvKeyword);

        stress = "";
        fear = "";
        insomnia = "";
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle.getString("stress") != null){
            stress = bundle.getString("stress");
        }
        if(bundle.getString("fear") != null){
            fear = bundle.getString("fear");
        }
        if(bundle.getString("insomnia") != null){
            insomnia = bundle.getString("insomnia");
        }



        String keywords = "";
        keywords = "keywords = "+ stress +","+ fear +","+ insomnia;

        tvKeyword.setText(keywords);

        //!-- keyword 다시 설정하는 화면으로 이동
        back_select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SelectActivity.class);
                startActivity(intent);
            }
        });

        return HomeView;
    }
}

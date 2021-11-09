package com.teamopendata.mindcareapp.ui.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.teamopendata.mindcareapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SettingFragment extends Fragment {
    private final String TAG = SettingFragment.class.getSimpleName();

    TextView tvToday;
    Calendar current_Time;
    BarChart barchart;
    ImageButton backPressButton,settingButton;

    private SettingViewModel settingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel.class);
        View GraphView = inflater.inflate(R.layout.fragment_graph, container, false);




        //!--차트 넣기
        barchart = GraphView.findViewById(R.id.graph);
        setChart(barchart);

        //!--날짜 넣기
        tvToday = GraphView.findViewById(R.id.tvToday);
        current_Time = Calendar.getInstance();

        String sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentDate = String.format(sdf,current_Time);

        tvToday.setText(currentDate);
        Log.d(TAG,"currentDate"+currentDate);

        return GraphView;
    }

    //!--라벨  메소드
    public void setDay(ArrayList<String> a, String day){

        a.add(day);

    }

    //!-- graph 메소드
    public void setChart(BarChart chart){


        //!--1단계
        ArrayList<BarEntry> arrayList = new ArrayList<>();
        arrayList.add(new BarEntry(0,10));
        arrayList.add(new BarEntry(1,50));
        arrayList.add(new BarEntry(2,60));
        arrayList.add(new BarEntry(3,30));
        arrayList.add(new BarEntry(4,90));
        arrayList.add(new BarEntry(5,40));
        arrayList.add(new BarEntry(6,100));

        //!--2단계
        BarDataSet barDataSet = new BarDataSet(arrayList,"실천 점수");
        barDataSet.setColor(Color.rgb(9,32,161));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(5F);
        barDataSet.setDrawValues(false);

        //!--3단계
        BarData barData = new BarData(barDataSet);

        chart.setFitBars(true);
        chart.setData(barData);
        chart.getDescription().setText(" ");
        chart.animateY(1000);

        //!--x축 라벨 설정하기
        ArrayList<String> label_day = new ArrayList<String>(); //x축라벨


        setDay(label_day,"월요일");
        setDay(label_day,"화요일");
        setDay(label_day,"수요일");
        setDay(label_day,"목요일");
        setDay(label_day,"금요일");
        setDay(label_day,"토요일");
        setDay(label_day,"일요일");

        //!--x축 관리
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(label_day));

        //!-- y축 관리
        chart.getAxisLeft().setEnabled(true); //y축 left 지우기
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setAxisMaximum(100);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setLabelCount(5);

        //!--차트 기타 관리
        chart.setTouchEnabled(false);
    }


}

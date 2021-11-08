package com.teamopendata.mindcareapp.ui.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;
import java.util.List;

public class GraphFragment extends Fragment {

    private SettingViewModel settingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_graph, container, false);


        //!--차트 넣기
        BarChart chart = root.findViewById(R.id.graph);

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

        //!--차트 기타 관리
        chart.setTouchEnabled(false);
        return root;
    }

    public void setDay(ArrayList<String> a, String day){

        a.add(day);

    }

}

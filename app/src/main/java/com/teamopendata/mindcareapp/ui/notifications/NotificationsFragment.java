package com.teamopendata.mindcareapp.ui.notifications;

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View fragmentxml = inflater.inflate(R.layout.fragment_notifications, container, false);


        //!--차트 넣기
        LineChart chart = fragmentxml.findViewById(R.id.graph);







        List<Entry> list = new ArrayList<Entry>();

        list.add(new Entry(0,55)); //월
        list.add(new Entry(1,30)); //화
        list.add(new Entry(2,20)); //수
        list.add(new Entry(3,10)); //목
        list.add(new Entry(4,45)); //금
        list.add(new Entry(5,55)); //토
        list.add(new Entry(6,75)); //일

        //!--1단계
        LineDataSet linedata = new LineDataSet(list,"판단");
        //!--2단계
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(linedata);
        //!--3단계
        LineData data = new LineData(dataSets);

        //!--x축 라벨 설정하기
        ArrayList<String> label_day = new ArrayList<String>(); //x축라벨


        setDay(label_day,"월요일");
        setDay(label_day,"화요일");
        setDay(label_day,"수요일");
        setDay(label_day,"목요일");
        setDay(label_day,"금요일");
        setDay(label_day,"토요일");
        setDay(label_day,"일요일");


        //!--X축 control
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(label_day));

        //!--Y축 control
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setAxisMaximum(100);
        chart.getAxisLeft().setAxisMinimum(0);

        //chart set
        chart.animateX(1000);
        chart.setData(data);
        chart.setTouchEnabled(false);
        return fragmentxml;
    }

    public void setDay(ArrayList<String> a, String day){

        a.add(day);

    }
}

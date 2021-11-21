package com.teamopendata.mindcareapp.ui.Graph;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.databinding.FragmentGraphBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class GraphFragment extends Fragment {
    private final String TAG = GraphFragment.class.getSimpleName();

    private FragmentGraphBinding binding;

    Date current_Time;


    String week_day,year,month,day;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGraphBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // !--변수

        //!--현재 날짜 넣기
        current_Time = Calendar.getInstance().getTime();

        SimpleDateFormat weekFormat = new SimpleDateFormat("EE",Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd",Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM",Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.getDefault());


        week_day = weekFormat.format(current_Time);
        year = yearFormat.format(current_Time);
        month = monthFormat.format(current_Time);
        day = dayFormat.format(current_Time);

        String Date = "현재날짜: "+ year +"."+ month +"."+ day + " "+ week_day;

        binding.tvToday.setText(Date);


        //DateRangePicker
        binding.calenderButtonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //요일 별 날짜 constraint
                CalendarConstraints.Builder constrainBuilder =  new CalendarConstraints.Builder();

                if(week_day.equals("월")){
                    constrainBuilder.setValidator(DateValidatorPointBackward.before(Calendar.getInstance().getTimeInMillis()+60*60*24*1000*6));
                }
                else if (week_day.equals("화")){
                    constrainBuilder.setValidator(DateValidatorPointBackward.before(Calendar.getInstance().getTimeInMillis()+60*60*24*1000*5));
                }
                else if (week_day.equals("수")){
                    constrainBuilder.setValidator(DateValidatorPointBackward.before(Calendar.getInstance().getTimeInMillis()+60*60*24*1000*4));
                }
                else if (week_day.equals("목")){
                    constrainBuilder.setValidator(DateValidatorPointBackward.before(Calendar.getInstance().getTimeInMillis()+60*60*24*1000*3));
                }
                else if (week_day.equals("금")){
                    constrainBuilder.setValidator(DateValidatorPointBackward.before(Calendar.getInstance().getTimeInMillis()+60*60*24*1000*2));
                }
                else if (week_day.equals("토")){
                    constrainBuilder.setValidator(DateValidatorPointBackward.before(Calendar.getInstance().getTimeInMillis()+60*60*24*1000));
                }
                else if (week_day.equals("일")){
                    constrainBuilder.setValidator(DateValidatorPointBackward.before(Calendar.getInstance().getTimeInMillis()));
                }


                //MaterialDatePicker
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker().setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
                builder.setTitleText("날짜를 선택해주세요");
                builder.setCalendarConstraints(constrainBuilder.build());

                final MaterialDatePicker materialDatePicker = builder.build();
                materialDatePicker.show(getChildFragmentManager(),"DATE_PICKER");


                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener(){
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Long selectionSecond =  (Long)selection+60*60*24*1000*6;
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Log.d(TAG,"selection: "+selection);
                        String selectedDays = "선택 날짜: "+ simpleFormat.format(selection)+" ~ " +simpleFormat.format(selectionSecond);

                        binding.tvToday.setText(selectedDays);
                    }

                });

            }
        });

        //!--차트 넣기
        setChart(binding.graph);

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

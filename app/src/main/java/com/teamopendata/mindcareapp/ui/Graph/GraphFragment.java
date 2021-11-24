package com.teamopendata.mindcareapp.ui.Graph;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
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

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.teamopendata.mindcareapp.databinding.FragmentGraphBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;


public class GraphFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private final String TAG = GraphFragment.class.getSimpleName();
    DecimalFormat form;
    private FragmentGraphBinding binding;
    Long millSec,secondDayMilSec;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;
    Calendar[] disabledDays;
    Date format1;
    String firstDate,secondDate;
    float sum;
    float mondayData,tuesDayData,wednesdayData,thursdayData,fridayData,saturdayData,sundayData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGraphBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // !-- datePickerDialog 세팅하기
        setDatePickerDialog();
        form = new DecimalFormat("#");

        //선택 안했을 때 가리기
        binding.graph.setVisibility(View.GONE);
        disappearArrow();

        // left버튼
        btnListener(binding.leftArrowBtnGraph);


        // right버튼
        btnListener(binding.rightArrowBtnGraph);


        //DateRangePicker
        binding.calenderButtonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearArrow();
                datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
                binding.tvDate.setText("");
            }
        });

        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearArrow();;
                datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
                binding.tvDate.setText("");
            }
        });



        // !-- end
    }


    public void btnListener(ImageButton imgBtn){
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imgBtn == binding.leftArrowBtnGraph){
                    millSec = millSec - 60*60*24*1000*7;
                }

                else {
                    if(System.currentTimeMillis() >= millSec + 60*60*24*1000*6){
                        millSec = millSec + 60*60*24*1000*7;
                        secondDayMilSec = millSec+60*60*24*1000*6;
                    }
                }
                secondDayMilSec = millSec+60*60*24*1000*6;

                // millSec - >  date
                firstDate = convertDate(millSec,"MM월 dd일");
                secondDate = convertDate(secondDayMilSec,"MM월 dd일");

                setChart(binding.graph,mondayData,tuesDayData,wednesdayData,thursdayData,fridayData,saturdayData,sundayData);
                sum = ((mondayData+tuesDayData+wednesdayData+thursdayData+fridayData+saturdayData+sundayData)/7);

                binding.tvDate.setText(firstDate+" ~ "+secondDate);
                binding.progressbar.setProgress((int) sum);
                binding.tvProgressbar.setText(form.format(sum));
                binding.tvProgressbar2.setText(form.format(sum));
            }
        });
    }


    // !-- 사라져 메소드
    public void disappearArrow(){
        binding.leftArrowBtnGraph.setVisibility(View.GONE);
        binding.rightArrowBtnGraph.setVisibility(View.GONE);
    }

    // !-- 보여줘 메소드
    public void appearArrow(){
        binding.leftArrowBtnGraph.setVisibility(View.VISIBLE);
        binding.rightArrowBtnGraph.setVisibility(View.VISIBLE);
    }


    //!--라벨  메소드
    public void setDay(ArrayList<String> a, String day){
        a.add(day);
    }

    //!-- graph 메소드 monDayRate 등 데이터 가져와서 null값인 경우엔 0으로
    public void setChart(BarChart chart,float monDayRate,float tuesDayRate,float wednesdayRate,float thursdayRate,float fridayRate,float saturdayRate,float sundayRate){

        // 임시 랜덤 데이터
        monDayRate = (float) Math.random()*50+50;
        tuesDayRate = (float) Math.random()*50+50;
        wednesdayRate = (float) Math.random()*50+50;
        thursdayRate = (float) Math.random()*50+50;
        fridayRate = (float) Math.random()*50+50;
        saturdayRate = (float) Math.random()*50+50;
        sundayRate = (float) Math.random()*50+50;

        mondayData = monDayRate;
        tuesDayData = tuesDayRate;
        wednesdayData = wednesdayRate;
        thursdayData = thursdayRate;
        fridayData = fridayRate;
        saturdayData = saturdayRate;
        sundayData = sundayRate;




        //!--1단계
        ArrayList<BarEntry> arrayList = new ArrayList<>();
        arrayList.add(new BarEntry(0,monDayRate));
        arrayList.add(new BarEntry(1,tuesDayRate));
        arrayList.add(new BarEntry(2,wednesdayRate));
        arrayList.add(new BarEntry(3,thursdayRate));
        arrayList.add(new BarEntry(4,fridayRate));
        arrayList.add(new BarEntry(5,saturdayRate));
        arrayList.add(new BarEntry(6,sundayRate));

        //!--2단계
        BarDataSet barDataSet = new BarDataSet(arrayList,"실천 점수");
        barDataSet.setColors(Color.rgb(9,32,161),Color.rgb(34,135,201),Color.rgb(136,206,235));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(5F);
        barDataSet.setDrawValues(false);

        //!--3단계
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1f);

        chart.setFitBars(true);
        chart.setData(barData);
        chart.getDescription().setText(" ");
        chart.animateY(800);

        //!--x축 라벨 설정하기
        ArrayList<String> label_day = new ArrayList<String>(); //x축라벨


        setDay(label_day,"월");
        setDay(label_day,"화");
        setDay(label_day,"수");
        setDay(label_day,"목");
        setDay(label_day,"금");
        setDay(label_day,"토");
        setDay(label_day,"일");

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
        chart.setTouchEnabled(false); //터치막음

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        String parseDate = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

//       millSec= LocalDate.of(year,monthOfYear,dayOfMonth);

        try {
            format1 = new SimpleDateFormat("yyyy-MM-dd").parse(parseDate);
        }
        catch (Exception e){

        }
        if(format1 != null){
            millSec = format1.getTime();
        }
        // 6일 뒤  = 일요일
        secondDayMilSec = millSec+60*60*24*1000*6;

        // millSec - >  date
        firstDate = convertDate(millSec,"MM월 dd일");
        secondDate = convertDate(secondDayMilSec,"MM월 dd일");


        String writeDateFirst =  firstDate +" ~ "+secondDate;

        binding.tvDate.setText(writeDateFirst);

        setChart(binding.graph,mondayData,tuesDayData,wednesdayData,thursdayData,fridayData,saturdayData,sundayData);

        binding.graph.setVisibility(View.VISIBLE);
        appearArrow();

        sum = ((mondayData+tuesDayData+wednesdayData+thursdayData+fridayData+saturdayData+sundayData)/7);

        binding.tvProgressbar.setText(form.format(sum));
        binding.tvProgressbar2.setText(form.format(sum));
    }
    //  milSec -> Date 메소드
    public String convertDate(Long dateInMilSec,String dateFormat){
        return DateFormat.format(dateFormat,dateInMilSec).toString();
    }

    //데이트피커 세팅하기 메소드
    public void setDatePickerDialog(){

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);

        datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(this, Year, Month, Day);

        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setTitle("날짜를 선택해 주세요");

        // min 날짜 제한
        Calendar min_date_c = Calendar.getInstance();
        min_date_c.set(Calendar.YEAR,Year-15); //과거 15년까지 가능 메모리 상의 후 얼마까지 가능하게 할 지 결정
        datePickerDialog.setMinDate(min_date_c);


        Calendar max_date_c = Calendar.getInstance();
        datePickerDialog.setMaxDate(max_date_c);

        // !-- 요일 제한하기 for 문이라 메모리 좀 많이씀...10 * 365 번 돌아감..
        for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.TUESDAY || dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.FRIDAY  ) {
                disabledDays =  new Calendar[1];
                disabledDays[0] = loopdate;
                datePickerDialog.setDisabledDays(disabledDays);
            }
        }
        if(max_date_c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){

        }
        else {
            Log.d(TAG,"월요일이 아닙니다.");
            disabledDays = new Calendar[1];
            disabledDays[0] = max_date_c;
            datePickerDialog.setDisabledDays(disabledDays);
        }
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                binding.tvDate.setText("날짜를 선택해주세요");
                binding.graph.setVisibility(View.GONE);
                binding.leftArrowBtnGraph.setVisibility(View.GONE);
                binding.rightArrowBtnGraph.setVisibility(View.GONE);
            }
        });
    }
    //!--end
}

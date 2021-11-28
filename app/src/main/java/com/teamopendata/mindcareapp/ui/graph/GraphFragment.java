package com.teamopendata.mindcareapp.ui.graph;

import static android.view.View.GONE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.teamopendata.mindcareapp.ColoredLabelXAxisRenderer;
import com.teamopendata.mindcareapp.MainActivity;
import com.teamopendata.mindcareapp.MindChargeDB;
import com.teamopendata.mindcareapp.R;
import com.teamopendata.mindcareapp.common.model.entity.Record;
import com.teamopendata.mindcareapp.common.model.entity.Task;
import com.teamopendata.mindcareapp.converters.Converters;
import com.teamopendata.mindcareapp.databinding.FragmentGraphBinding;
import com.teamopendata.mindcareapp.ui.records.RecordsFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.Array;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;


public class GraphFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private final String TAG = GraphFragment.class.getSimpleName();
    DecimalFormat form;
    int taskTrueNum,taskNum;
    private FragmentGraphBinding binding;
    long millSec,millSec2,millSec3,millSec4,millSec5,millSec6,millSec7;
    float mondayData,tuesDayData,wednesdayData,thursdayData,fridayData,saturdayData,sundayData;

    LocalDate monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;
    Calendar[] disabledDays;
    Date format1;
    Converters converters;
    int isDataNone;
    ArrayList<LocalDate> datesList;

    float sum;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGraphBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        converters = new Converters();

        //record 화면으로 이동
        setAlYakClickListener();
        // !-- datePickerDialog 세팅하기
        setDatePickerDialog();
        form = new DecimalFormat("#");


        //creatView 시 초기 세팅
        setDefault();



        // left버튼(일주일 전으로 )
        btnListener(binding.leftArrowBtnGraph);


        // right버튼(일주일 후로)
        btnListener(binding.rightArrowBtnGraph);


        //DatePicker
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
                disappearArrow();
                datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
                binding.tvDate.setText("");
            }
        });




        // !-- onCreateEnd
    }

    public  void setAlYakClickListener(){
        binding.btnAlyakGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_records);
            }
        });
    }

    public void setDefault() {

        Calendar cal = Calendar.getInstance();
        long currentTimeMill = System.currentTimeMillis();

        int weekDay = cal.get(Calendar.DAY_OF_WEEK);



        if(weekDay == 1){
            //일요일
            millSec =currentTimeMill - 60*60*24*1000*6;
        }
        else if(weekDay == 2){
            //월요일
            millSec =currentTimeMill;
        }
        else if(weekDay == 3){
            //화요일
            millSec =currentTimeMill - 60*60*24*1000;
        }
        else if(weekDay == 4){
            //수요일
            millSec =currentTimeMill - 60*60*24*1000*2;
        }
        else if(weekDay == 5){
            //목요일
            millSec =currentTimeMill - 60*60*24*1000*3;
        }else if(weekDay == 6){
            //금요일
            millSec =currentTimeMill - 60*60*24*1000*4;
        }else if(weekDay == 7){
            //토요일
            millSec =currentTimeMill - 60*60*24*1000*5;
        }

        millSec2 = millSec+ 60*60*24*1000; //화
        millSec3 = millSec+ 60*60*24*1000*2;//수
        millSec4 = millSec + 60*60*24*1000*3;//목
        millSec5 = millSec + 60*60*24*1000*4;//금
        millSec6 = millSec + 60*60*24*1000*5;//토
        millSec7 = millSec + 60*60*24*1000*6;//일


        //바로 구현
        monday = converters.LongFromLocalDate(millSec);
        tuesday = converters.LongFromLocalDate(millSec2);
        wednesday = converters.LongFromLocalDate(millSec3);
        thursday = converters.LongFromLocalDate(millSec4);
        friday = converters.LongFromLocalDate(millSec5);
        saturday = converters.LongFromLocalDate(millSec6);
        sunday = converters.LongFromLocalDate(millSec7);


        //!-- 날짜 쓰기
        Calendar calWeek = Calendar.getInstance();
        calWeek.set(Calendar.YEAR,monday.getYear());

        calWeek.set(Calendar.MONTH,monday.getMonthValue());
        calWeek.set(Calendar.DAY_OF_MONTH,monday.getDayOfMonth());
        int weekOfMonth = calWeek.get(Calendar.WEEK_OF_MONTH);

        Log.d(TAG,"weekOfMonth: "+weekOfMonth);
        binding.tvWeekOfMonth.setText(String.valueOf(weekOfMonth));
        String writeDateFirst =  monday +" - "+sunday;
        binding.tvDate.setText(writeDateFirst);

        //!-- DB -----------
        startBackgroundThread();

        setChart(binding.graph);

        //!--controlSum
        controlSum();
    }


    //! -- 메소드 시작


    public void btnListener(ImageButton imgBtn){
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imgBtn == binding.leftArrowBtnGraph){

                    millSec = millSec - 60*60*24*1000*7; //전 월
                    millSec2 = millSec+ 60*60*24*1000; //화
                    millSec3 = millSec+ 60*60*24*1000*2;//수
                    millSec4 = millSec + 60*60*24*1000*3;//목
                    millSec5 = millSec + 60*60*24*1000*4;//금
                    millSec6 = millSec + 60*60*24*1000*5;//토
                    millSec7 = millSec + 60*60*24*1000*6;//일
                }

                else {
                    Log.d(TAG,"system.currentMills: "+System.currentTimeMillis());
                    Log.d(TAG,"millSec7: "+millSec7);


                    if(System.currentTimeMillis() > millSec7+60*60*24*1000 ){

                        millSec = millSec + 60*60*24*1000*7; //후 월
                        millSec2 = millSec+ 60*60*24*1000; //화
                        millSec3 = millSec+ 60*60*24*1000*2;//수
                        millSec4 = millSec + 60*60*24*1000*3;//목
                        millSec5 = millSec + 60*60*24*1000*4;//금
                        millSec6 = millSec + 60*60*24*1000*5;//토
                        millSec7 = millSec + 60*60*24*1000*6;//일
                    }
                }

                //날짜형으로 형 변환
//                monday = convertDate(millSec,"yyyy-MM-dd");
                monday = converters.LongFromLocalDate(millSec);
                tuesday = converters.LongFromLocalDate(millSec2);
                wednesday = converters.LongFromLocalDate(millSec3);
                thursday = converters.LongFromLocalDate(millSec4);
                friday = converters.LongFromLocalDate(millSec5);
                saturday = converters.LongFromLocalDate(millSec6);
                sunday = converters.LongFromLocalDate(millSec7);


                //!-- 날짜 쓰기
                Calendar calWeek = Calendar.getInstance();
                calWeek.set(Calendar.YEAR,monday.getYear());

                calWeek.set(Calendar.MONTH,monday.getMonthValue());
                calWeek.set(Calendar.DAY_OF_MONTH,monday.getDayOfMonth());
                int weekOfMonth = calWeek.get(Calendar.WEEK_OF_MONTH);

                Log.d(TAG,"weekOfMonth: "+weekOfMonth);
                binding.tvWeekOfMonth.setText(String.valueOf(weekOfMonth));
                String writeDateFirst =  monday +" - "+sunday;
                binding.tvDate.setText(writeDateFirst);

                //!-- DB -----------
                startBackgroundThread();

                setChart(binding.graph);

                //!--controlSum
                controlSum();
            }
        });
    }


    // !-- 사라져 메소드
    public void disappearArrow(){
        binding.leftArrowBtnGraph.setVisibility(GONE);
        binding.rightArrowBtnGraph.setVisibility(GONE);
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

    //!-- graph 메소드
    public void setChart(BarChart chart){


        //!--1단계
        ArrayList<BarEntry> arrayList = new ArrayList<>();
//
        arrayList.add(new BarEntry(0,new float[]{mondayData,100-mondayData}));
        arrayList.add(new BarEntry(1,new float[]{tuesDayData,100-tuesDayData}));
        arrayList.add(new BarEntry(2,new float[]{wednesdayData,100-wednesdayData}));
        arrayList.add(new BarEntry(3,new float[]{thursdayData,100-thursdayData}));
        arrayList.add(new BarEntry(4,new float[]{fridayData,100-fridayData}));
        arrayList.add(new BarEntry(5,new float[]{saturdayData,100-saturdayData}));
        arrayList.add(new BarEntry(6,new float[]{sundayData,100-sundayData}));

        //!--2단계
        BarDataSet barDataSet = new BarDataSet(arrayList,"실천 점수");
        barDataSet.setColors(Color.rgb(99,171,247),Color.argb(100,213,222,226));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(5F);
        barDataSet.setDrawValues(true);


        //!--3단계
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);

        chart.setFitBars(true);
        chart.setData(barData);
        chart.getDescription().setText(" ");
        chart.animateX(500);
        chart.getLegend().setEnabled(false); //하단부 없애기

        //!--x축 라벨 설정하기
        ArrayList<String> label_day = new ArrayList<String>(); //x축라벨

        //x라벨 컬러 세팅하기
        List<Integer> colors = new ArrayList<>();
        for(int i = 0;i<7;i++){
            colors.add(Color.rgb(200,200,200));
        }
        chart.setXAxisRenderer(new ColoredLabelXAxisRenderer(chart.getViewPortHandler(),chart.getXAxis(),chart.getTransformer(YAxis.AxisDependency.LEFT),colors));


        setDay(label_day,"Mon");
        setDay(label_day,"Tue");
        setDay(label_day,"wed");
        setDay(label_day,"Thu");
        setDay(label_day,"Fri");
        setDay(label_day,"Sat");
        setDay(label_day,"Sun");

        isDataNone =0;

        //DB에 요일이 있는지 없는지 체크
        if(datesList.contains(converters.LongFromLocalDate(millSec))) {
            colors.set(0,Color.rgb(0,0,0));
            isDataNone++;
        }
        if(datesList.contains(converters.LongFromLocalDate(millSec2))) {
            colors.set(1,Color.rgb(0,0,0));
            isDataNone++;
        }
        if(datesList.contains(converters.LongFromLocalDate(millSec3))) {
            colors.set(2,Color.rgb(0,0,0));
            isDataNone++;
        }
        if(datesList.contains(converters.LongFromLocalDate(millSec4))) {
            colors.set(3,Color.rgb(0,0,0));
            isDataNone++;
        }
        if(datesList.contains(converters.LongFromLocalDate(millSec5))) {
            colors.set(4,Color.rgb(0,0,0));
            isDataNone++;
        }
        if(datesList.contains(converters.LongFromLocalDate(millSec6))) {
            colors.set(5,Color.rgb(0,0,0));
            isDataNone++;
        }
        if(datesList.contains(converters.LongFromLocalDate(millSec7))) {
            colors.set(6,Color.rgb(0,0,0));
            isDataNone++;
        }

        Log.d(TAG,"isDataNone: "+isDataNone);





        //!--x축 관리
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(label_day));

        //!-- y축 관리
        chart.getAxisLeft().setEnabled(false); //y축 left 지우기
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setAxisMaximum(100);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setLabelCount(5);

        //!--차트 기타 관리
        chart.setTouchEnabled(false); //터치막음
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        // 날짜 변환
        millSec = converters.LocalDateToLong(LocalDate.of(year,monthOfYear+1,dayOfMonth));

        // 날짜의 milSec
        millSec2 = millSec+ 60*60*24*1000; //화
        millSec3 = millSec+ 60*60*24*1000*2;//수
        millSec4 = millSec + 60*60*24*1000*3;//목
        millSec5 = millSec + 60*60*24*1000*4;//금
        millSec6 = millSec + 60*60*24*1000*5;//토
        millSec7 = millSec + 60*60*24*1000*6;//일

        //날짜형으로 형 변환
        monday = converters.LongFromLocalDate(millSec);
        tuesday = converters.LongFromLocalDate(millSec2);
        wednesday = converters.LongFromLocalDate(millSec3);
        thursday = converters.LongFromLocalDate(millSec4);
        friday = converters.LongFromLocalDate(millSec5);
        saturday = converters.LongFromLocalDate(millSec6);
        sunday = converters.LongFromLocalDate(millSec7);


        //!-- 날짜

        //몇 주 째인지 계산하기.
        Calendar calWeek = Calendar.getInstance();
        calWeek.set(Calendar.YEAR,monday.getYear());

        calWeek.set(Calendar.MONTH,monday.getMonthValue());
        calWeek.set(Calendar.DAY_OF_MONTH,monday.getDayOfMonth());
        int weekOfMonth = calWeek.get(Calendar.WEEK_OF_MONTH);

        Log.d(TAG,"weekOfMonth: "+weekOfMonth);
        binding.tvWeekOfMonth.setText(String.valueOf(weekOfMonth));

        //월~일 날짜 쓰기
        String writeDateFirst =  monday +" - "+sunday;
        binding.tvDate.setText(writeDateFirst);

        //!-- DB -----------
        startBackgroundThread();

        //차트 그리기
        setChart(binding.graph);
        appearArrow();

        //달성률 관리하기
        controlSum();


    }

    //!--control sum
    public void controlSum(){
        sum = ((mondayData+tuesDayData+wednesdayData+thursdayData+fridayData+saturdayData+sundayData)/7);

        //getTheme 안되는데 뭐 써야하는지
        if((int)sum >=0 && (int)sum <25){

            binding.btnAlyakGraph.setImageResource(R.drawable.icon_alyak0);
        }
        else if((int)sum >=25 && (int)sum <50){
            binding.btnAlyakGraph.setImageDrawable(getResources().getDrawable(R.drawable.icon_alyak25));
        }
        else if((int)sum >=50 && (int)sum <75){
            binding.btnAlyakGraph.setImageDrawable(getResources().getDrawable(R.drawable.icon_alyak50));
        }
        else if((int)sum >=75 && (int)sum <100){
            binding.btnAlyakGraph.setImageDrawable(getResources().getDrawable(R.drawable.icon_alyak75));
        }
        else if((int)sum == 100){
            binding.btnAlyakGraph.setImageDrawable(getResources().getDrawable(R.drawable.icon_alyak100));
        }

        //기록 없을 때
        if(isDataNone == 0){

            binding.tvWeek.setVisibility(GONE);
            binding.tvProgressbar2.setVisibility(GONE);
            binding.tvPercent.setText("기록을 ");
            binding.tvCharge.setText("입력해주세요!");

        }
        else{
            binding.tvWeek.setVisibility(View.VISIBLE);
            binding.tvProgressbar2.setVisibility(View.VISIBLE);
            binding.tvPercent.setVisibility(View.VISIBLE);
            binding.tvPercent.setText("% ");
            binding.tvCharge.setText("마음충전되었습니다.");
            binding.tvProgressbar2.setText(form.format(sum));
        }

    }


    //!-- DB
    public void startBackgroundThread(){

        // BackgroundThread 구현하기
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{

                    // db 초기화
                    MindChargeDB db = MindChargeDB.getInstance(getContext());

                    datesList = new ArrayList<>();


                    mondayData = setDayData(millSec);
                    tuesDayData = setDayData(millSec2);
                    wednesdayData = setDayData(millSec3);
                    thursdayData = setDayData(millSec4);
                    fridayData = setDayData(millSec5);
                    saturdayData = setDayData(millSec6);
                    sundayData = setDayData(millSec7);




////                                      // !--  가짜 데이터----
//                    ArrayList<Task> tasks = new ArrayList<>();
//
//                    Task task1 = new Task("소주 마시기",true);
//                    Task task2 = new Task("친구랑 놀기",true);
//                    Task task3 = new Task("영화보기",true);
//                    Task task4 = new Task("맛있는 거먹기",true);
//                    Task task5 = new Task("맛있는 거먹기",true);
//                    Task task6 = new Task("취미생활 하기",false);
//                    Task task8 = new Task("하루종일 독서하기",false);
//
//
//                    tasks.add(task1);
//                    tasks.add(task2);
//                    tasks.add(task3);
//                    tasks.add(task4);
//                    tasks.add(task5);
//                    tasks.add(task6);
//
//                    tasks.add(task8);
//
//
//
//
//
//
////
//                    Record record8 = new Record(LocalDate.of(2021,11,28),"kk병원 방문하기",tasks);
////                    Record record2 = new Record(LocalDate.of(2021,11,02),"ㅇㅇ병원 방문하기",tasks2);
//
//
//
//
//                    db.getRecordDao().insert(record8);


                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        Thread backgroundThread = new Thread(runnable);
        backgroundThread.start();
        try {
            backgroundThread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    public float  setDayData(long millSecDate){
        float dayData;
        MindChargeDB db = MindChargeDB.getInstance(getContext());
        LocalDate convertedDay = converters.LongFromLocalDate(millSecDate); //화요일
        Log.d(TAG,"convertedDay: "+convertedDay);

        Record selectedRecordDay = db.getRecordDao().
                getDateRecord(convertedDay);



        //선택한 날짜에 값이 들어있는지 여부 체크
        if(selectedRecordDay != null){
            //라벨을 위해
            datesList.add(selectedRecordDay.getDate());
            Log.d(TAG,"selectedRecordDate: "+String.valueOf(selectedRecordDay.getDate()));

            List<Task> tasksList = selectedRecordDay.getTasks();
            taskTrueNum = 0;
            // !--true인 task 개수 구하기
            for(int i =0 ;i<tasksList.size();i++){
                if( tasksList.get(i).isCompleted()){
                    taskTrueNum ++;
                }
            }
            // task 개수 구하기
            taskNum = tasksList.size();
            Log.d(TAG, "db taskTrueNum: "+ taskTrueNum);
            Log.d(TAG, "db taskNum: "+ taskNum);

            dayData = (taskTrueNum*100 /taskNum);
            Log.d(TAG,"DayDataSet: "+dayData);
            Log.d(TAG,convertedDay+"날의 실천률 값: "+ dayData);
            return  dayData;
        }
        else {
            Log.d(TAG,convertedDay+"날에 기록이 없습니다.");
            dayData = 0;
            return  dayData;
        }
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

        //!-- 현재날짜가 월요일이 아닌경우 뜨는 문제가 발생햇는데 그 오류를 해결하는 코드
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
                binding.tvDate.setText(monday+"-"+sunday);
                binding.leftArrowBtnGraph.setVisibility(View.VISIBLE);
                binding.rightArrowBtnGraph.setVisibility(View.VISIBLE);
            }
        });
    }
    //!--end
}
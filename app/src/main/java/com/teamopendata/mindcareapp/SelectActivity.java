package com.teamopendata.mindcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SelectActivity extends BaseActivity {
    private final String TAG = SelectActivity.class.getSimpleName();

    android.widget.Button stress_button,fear_button,insomnia_button,complete_button,selectCancel_button,depressed_button,anxiety_button,suicide_try_button,suicide_thinking_button,left_people_button;
    TextView picknum;

    int  stress_button_status =0,fear_button_status =0,insomnia_button_status=0 ,depressed_button_status=0,anxiety_button_status=0; //정신 버튼
    int suicide_try_button_status = 0, suicide_thinking_button_status = 0, left_people_button_status = 0;
    int sum =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_slide1);

        depressed_button = findViewById(R.id.depressed_button);
        anxiety_button = findViewById(R.id.anxiety_button);
        selectCancel_button = findViewById(R.id.selectCancel_button);
        stress_button = findViewById(R.id.stress_button);
        fear_button = findViewById(R.id.fear_button);
        insomnia_button = findViewById(R.id.insomnia_button);
        complete_button = findViewById(R.id.complete_button);
        suicide_try_button = findViewById(R.id.suicide_try_button);
        suicide_thinking_button = findViewById(R.id.suicide_thinking_button);
        left_people_button = findViewById(R.id.left_people_button);


        picknum = findViewById(R.id.picknum);


        //!--버튼 Reset 메소드
        selectCancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonReset(depressed_button,anxiety_button,stress_button,fear_button,insomnia_button,suicide_try_button,suicide_thinking_button,left_people_button);
            }
        });

        //!--키워드 클릭
        //!-- button 클릭인 겅우 status = 1

        stress_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(stress_button_status,stress_button);
                if(result != -1){
                    stress_button_status = result;
                if(stress_button_status == 0 && sum <4){
                    buttonClicked(stress_button);
                    stress_button_status = 1;
                    Log.d(TAG,"클릭시 sum: "+sum);
                }
                else if(stress_button_status == 1){
                    buttonDefault(stress_button);
                    stress_button_status = 0;
                    Log.d(TAG,"클릭 다시 sum: "+sum);
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        fear_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(fear_button_status,fear_button);
                if(result != -1){
                    fear_button_status = result;

                if(fear_button_status == 0 && sum < 4){
                    buttonClicked(fear_button);
                    fear_button_status = 1;
                }
                else if(fear_button_status == 1){
                    buttonDefault(fear_button);
                    fear_button_status = 0;
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        insomnia_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(insomnia_button_status,insomnia_button);
                if(result != -1){
                    insomnia_button_status = result;

                if(insomnia_button_status == 0 && sum < 4){
                    buttonClicked(insomnia_button);
                    insomnia_button_status = 1;
                }
                else if(insomnia_button_status == 1){
                    buttonDefault(insomnia_button);
                    insomnia_button_status = 0;
                }
            }
        });

        depressed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(depressed_button_status,depressed_button);
                if(result != -1){
                    depressed_button_status = result;
                }
            }
        });

        anxiety_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(anxiety_button_status,anxiety_button);
                if(result != -1){
                    anxiety_button_status = result;
                }
            }
        });

        suicide_try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(suicide_try_button_status,suicide_try_button);
                if(result != -1){
                    suicide_try_button_status = result;
                }
            }
        });

        suicide_thinking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(suicide_thinking_button_status,suicide_thinking_button);
                if(result != -1){
                    suicide_thinking_button_status = result;
                }
            }
        });

        left_people_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(left_people_button_status,left_people_button);
                if(result != -1){
                    left_people_button_status = result;
                }
            }
        });

        suicide_try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(suicide_try_button_status == 0 && sum < 4){
                    buttonClicked(suicide_try_button);
                    suicide_try_button_status = 1;
                }
                else if(suicide_try_button_status == 1){
                    buttonDefault(suicide_try_button);
                    suicide_try_button_status = 0;
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        suicide_thinking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(suicide_thinking_button_status == 0 && sum < 4){
                    buttonClicked(suicide_thinking_button);
                    suicide_thinking_button_status = 1;
                }
                else if(suicide_thinking_button_status == 1){
                    buttonDefault(suicide_thinking_button);
                    suicide_thinking_button_status = 0;
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        left_people_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(left_people_button_status == 0 && sum < 4){
                    buttonClicked(left_people_button);
                    left_people_button_status = 1;
                }
                else if(left_people_button_status == 1){
                    buttonDefault(left_people_button);
                    left_people_button_status = 0;
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });




        complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum == 0){
                    Toast.makeText(getApplicationContext(),"키워드를 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SelectActivity.this, MainActivity.class);

                if( stress_button_status == 1){
                    intent.putExtra("stress",stress_button.getText().toString());
                }
                if(fear_button_status == 1){
                    intent.putExtra("fear",fear_button.getText().toString());
                }
                if(insomnia_button_status == 1){
                    intent.putExtra("insomnia",insomnia_button.getText().toString());
                }
                if(depressed_button_status == 1){
                    intent.putExtra("depressed",depressed_button.getText().toString());
                }
                if(anxiety_button_status == 1){
                    intent.putExtra("anxiety",anxiety_button.getText().toString());
                }
                if(suicide_try_button_status == 1){
                    intent.putExtra("anxiety",anxiety_button.getText().toString());
                }
                if(suicide_thinking_button_status == 1){
                    intent.putExtra("anxiety",anxiety_button.getText().toString());
                }
                if(left_people_button_status == 1){
                    intent.putExtra("anxiety",anxiety_button.getText().toString());
                }
                if( intent.getExtras() != null){
                    startActivity(intent);
                }
            }
        });

    }

    private int buttonAllInOne(int status,android.widget.Button button){
        if(status == 0 && sum < 4){
            buttonClicked(button);
            Log.d(TAG,"클릭시 sum: "+sum);
            return  1;
        }
        else if (status == 1){
            buttonDefault(button);
            Log.d(TAG,"클릭 다시 sum: "+sum);
            return 0;
        }
        else if(sum == 4){
            Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
        }
        return -1;
    }
//    //!--버튼  총합 메소드
//    private void  buttonAllInOne(final android.widget.Button button , final int buttonStatus,final int sum){
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(buttonStatus == 0 && sum < 4){
//                    buttonClicked(button);
//                    buttonStatus = 1;
//                }
//                else if (buttonStatus == 1){
//                    buttonDefault(button);
//                    buttonStatus = 0;
//                }
//            }
//        });
////    }

    //!--버튼 누를 때 메소드
    private void buttonClicked(android.widget.Button button) {
        button.setBackgroundResource(R.drawable.buttonselected);
        button.setTextColor(getResources().getColor(R.color.white, getTheme()));
        sum++;
        picknum.setText(String.valueOf(sum));
    }

    //!--버튼 다시 누를 때 메소드
    private  void buttonDefault(android.widget.Button button){
        button.setBackgroundResource(R.drawable.button);
        button.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));
        sum--;
        picknum.setText(String.valueOf(sum));
    }


    //!--buttonSelection 초기화 메소드
    private  void buttonReset(android.widget.Button button1, android.widget.Button button2,android.widget.Button button3,android.widget.Button button4,android.widget.Button button5,android.widget.Button button6,android.widget.Button button7,android.widget.Button button8 ){

        button1.setBackgroundResource(R.drawable.button);
        button1.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button2.setBackgroundResource(R.drawable.button);
        button2.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button3.setBackgroundResource(R.drawable.button);
        button3.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button4.setBackgroundResource(R.drawable.button);
        button4.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button5.setBackgroundResource(R.drawable.button);
        button5.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button6.setBackgroundResource(R.drawable.button);
        button6.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button7.setBackgroundResource(R.drawable.button);
        button7.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button8.setBackgroundResource(R.drawable.button);
        button8.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        stress_button_status = 0;
        fear_button_status = 0;
        insomnia_button_status = 0;
        depressed_button_status = 0;
        anxiety_button_status = 0;
        suicide_try_button_status =0;
        suicide_thinking_button_status=0;
        left_people_button_status =0;

        sum = 0;

        picknum.setText("0"); //초기화

    }
}

package com.teamopendata.mindcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SelectActivity extends BaseActivity {
    private final String TAG = SelectActivity.class.getSimpleName();

    android.widget.Button stressbutton,fearbutton,insomniabutton,completebutton,selectcancelbutton,depressed_button,anxiety_button;
    TextView picknum;

    int  stressbutton_status =0,fearbutton_status =0,insomniabutton_status=0 ,depressed_button_status=0,anxiety_button_status=0; //정신 버튼
    int sum =0;
    String keyword1,keyword2,keyword3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_slide1);

        depressed_button = findViewById(R.id.depressed_button);
        anxiety_button = findViewById(R.id.anxiety_button);
        selectcancelbutton = findViewById(R.id.selectcancelbutton);
        stressbutton = findViewById(R.id.stressbutton);
        fearbutton = findViewById(R.id.fearbutton);
        insomniabutton = findViewById(R.id.insomniabutton);
        completebutton = findViewById(R.id.completebutton);

        picknum = findViewById(R.id.picknum);



        selectcancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonReset(depressed_button,anxiety_button,stressbutton,fearbutton,insomniabutton);
            }
        });

        //!--키워드 클릭
        //!-- button 클릭인 겅우 status = 1


        stressbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(stressbutton_status == 0 && sum <4){
                    buttonClicked(stressbutton);
                    stressbutton_status = 1;
                    Log.d(TAG,"클릭시 sum: "+sum);
                }
                else if(stressbutton_status == 1){
                    buttonDefault(stressbutton);
                    stressbutton_status = 0;
                    Log.d(TAG,"클릭 다시 sum: "+sum);
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        fearbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(fearbutton_status == 0 && sum < 4){
                    buttonClicked(fearbutton);
                    fearbutton_status = 1;
                }
                else if(fearbutton_status == 1){
                    buttonDefault(fearbutton);
                    fearbutton_status = 0;
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        insomniabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(insomniabutton_status == 0 && sum < 4){
                    buttonClicked(insomniabutton);
                    insomniabutton_status = 1;
                }
                else if(insomniabutton_status == 1){
                    buttonDefault(insomniabutton);
                    insomniabutton_status = 0;
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        depressed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(depressed_button_status == 0 && sum < 4){
                    buttonClicked(depressed_button);
                    depressed_button_status = 1;
                }
                else if(depressed_button_status == 1){
                    buttonDefault(depressed_button);
                    depressed_button_status = 0;
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        anxiety_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(anxiety_button_status == 0 && sum < 4){
                    buttonClicked(anxiety_button);
                    anxiety_button_status = 1;
                }
                else if(anxiety_button_status == 1){
                    buttonDefault(anxiety_button);
                    anxiety_button_status = 0;
                }
                else if(sum == 4){
                    Toast.makeText(getApplicationContext(),"4개 이상 선택할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });



        completebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum == 0){
                    Toast.makeText(getApplicationContext(),"키워드를 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SelectActivity.this, MainActivity.class);

                if( stressbutton_status == 1){
                    intent.putExtra("stress",stressbutton.getText().toString());
                }
                if(fearbutton_status == 1){
                    intent.putExtra("fear",fearbutton.getText().toString());
                }
                if(insomniabutton_status == 1){
                    intent.putExtra("insomnia",insomniabutton.getText().toString());
                }
                if(depressed_button_status == 1){
                    intent.putExtra("depressed",depressed_button.getText().toString());
                }
                if(anxiety_button_status == 1){
                    intent.putExtra("anxiety",anxiety_button.getText().toString());
                }
                if( intent.getExtras() != null){
                    startActivity(intent);
                }
            }
        });

    }

//    //!--버튼  총합 메소드
//    private void  buttonAllInOne(final android.widget.Button button , int buttonStatus, int sum){
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
//    }

    //!--버튼 누를 때 메소드
    private void buttonClicked(android.widget.Button button ) {
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
    private  void buttonReset(android.widget.Button button1, android.widget.Button button2,android.widget.Button button3,android.widget.Button button4,android.widget.Button button5 ){

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

        stressbutton_status = 0;
        fearbutton_status = 0;
        insomniabutton_status = 0;
        depressed_button_status = 0;
        anxiety_button_status = 0;
        sum = 0;

        picknum.setText("0"); //초기화

    }
}

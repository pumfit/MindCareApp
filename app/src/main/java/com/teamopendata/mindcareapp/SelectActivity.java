package com.teamopendata.mindcareapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SelectActivity extends AppCompatActivity {
    private final String TAG = SelectActivity.class.getSimpleName();

    android.widget.Button stressbutton,fearbutton,insomniabutton,completebutton,selectcancelbutton;
    TextView picknum;
    int  stressbutton_status,fearbutton_status,insomniabutton_status ,sum;
    String keyword1,keyword2,keyword3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_slide1);

        selectcancelbutton = findViewById(R.id.selectcancelbutton);
        stressbutton = findViewById(R.id.stressbutton);
        fearbutton = findViewById(R.id.fearbutton);
        insomniabutton = findViewById(R.id.insomniabutton);
        completebutton = findViewById(R.id.completebutton);

        picknum = findViewById(R.id.picknum);

        stressbutton_status = 0;
        fearbutton_status = 0;
        insomniabutton_status = 0;
        sum = 0;

        selectcancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonReset(stressbutton,fearbutton,insomniabutton);

            }
        });

        //!--키워드 클릭
        //!-- button 클릭인 겅우 status = 1
        stressbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(stressbutton_status == 0){
                    buttonClicked(stressbutton);
                    stressbutton_status = 1;
                    Log.d(TAG,"클릭시 sum: "+sum);
                }
                else if(stressbutton_status == 1){
                    buttonDefault(stressbutton);
                    stressbutton_status = 0;
                    Log.d(TAG,"클릭 다시 sum: "+sum);
                }

            }
        });

        fearbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(fearbutton_status == 0){
                    buttonClicked(fearbutton);
                    fearbutton_status = 1;

                }
                else if(fearbutton_status == 1){
                    buttonDefault(fearbutton);
                    fearbutton_status = 0;

                }

            }
        });

        insomniabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(insomniabutton_status == 0){
                    buttonClicked(insomniabutton);
                    insomniabutton_status = 1;
                }
                else if(insomniabutton_status == 1){
                    buttonDefault(insomniabutton);
                    insomniabutton_status = 0;
                }

            }
        });

        completebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if( intent.getExtras() != null){
                    startActivity(intent);
                }

            }
        });

    }
    private void buttonClicked(android.widget.Button button ) {

        button.setBackgroundResource(R.drawable.buttonselected);
        button.setTextColor(getResources().getColor(R.color.white, getTheme()));
        sum++;
        picknum.setText(String.valueOf(sum));
    }
    private  void buttonDefault(android.widget.Button button){

        button.setBackgroundResource(R.drawable.button);
        button.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));
        sum--;
        picknum.setText(String.valueOf(sum));
    }

    private  void buttonReset(android.widget.Button button1, android.widget.Button button2,android.widget.Button button3 ){

        button1.setBackgroundResource(R.drawable.button);
        button1.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button2.setBackgroundResource(R.drawable.button);
        button2.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button3.setBackgroundResource(R.drawable.button);
        button3.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));
        stressbutton_status = 0;
        fearbutton_status = 0;
        insomniabutton_status = 0;
        sum = 0;

        picknum.setText("0"); //초기화

    }
}

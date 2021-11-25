package com.teamopendata.mindcareapp;

import static com.teamopendata.mindcareapp.BtnPrefMgr.BTN_PREF_KEY;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {
    private final String TAG = SelectActivity.class.getSimpleName();


    android.widget.Button btn_depress_anxiety,btn_sleeplessness,btn_stress,btn_phobia,btn_mental_illness,btn_suicide_thinking,btn_left_people,btn_self_injury;
    android.widget.Button btn_adult,btn_kids,btn_elders,btn_parents_family,btn_healthcare;
    android.widget.Button btn_out_clinic,btn_rehabilitation,btn_residence,btn_guidance,btn_vocation_training,btn_mental_retardation,btn_disaster,btn_adjust;
    android.widget.Button btn_alcohol_addicted,btn_gambling_addicted,btn_drug_addicted,btn_smartphone_addicted;

    android.widget.Button selectCancel_button,complete_button;

    TextView picknum;


    int  btn_depress_anxiety_status =0,btn_sleeplessness_status = 0,btn_stress_status = 0,btn_phobia_status = 0,btn_mental_illness_status = 0,btn_suicide_thinking_status = 0,btn_left_people_status = 0,btn_self_injury_status = 0; //정신 버튼

    int  btn_adult_status =0,btn_kids_status = 0,btn_elders_status = 0,btn_parents_family_status = 0,btn_healthcare_status = 0;

    int  btn_out_clinic_status =0,btn_rehabilitation_status = 0,btn_residence_status = 0,btn_guidance_status = 0,btn_vocation_training_status = 0,btn_mental_retardation_status = 0,btn_disaster_status=0,btn_adjust_status =0 ;

    int  btn_alcohol_addicted_status =0,btn_gambling_addicted_status = 0,btn_drug_addicted_status = 0,btn_smartphone_addicted_status = 0;


    int sum =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // !-- 첫번 째 버튼
        btn_depress_anxiety = findViewById(R.id.btn_depress_anxiety);
        btn_sleeplessness = findViewById(R.id.btn_sleeplessness);
        btn_stress = findViewById(R.id.btn_stress);
        btn_phobia = findViewById(R.id.btn_phobia);
        btn_mental_illness = findViewById(R.id.btn_mental_illness);
        btn_suicide_thinking = findViewById(R.id.btn_suicide_thinking);
        btn_left_people = findViewById(R.id.btn_left_people);
        btn_self_injury = findViewById(R.id.btn_self_injury);


        // !-- 두번 째 버튼
        btn_adult = findViewById(R.id.btn_adult);
        btn_kids = findViewById(R.id.btn_kids);
        btn_elders = findViewById(R.id.btn_elders);
        btn_parents_family = findViewById(R.id.btn_parents_family);
        btn_healthcare = findViewById(R.id.btn_healthcare);


        // !-- 세번 째 버튼
        btn_out_clinic = findViewById(R.id.btn_out_clinic);
        btn_rehabilitation = findViewById(R.id.btn_rehabilitation);
        btn_residence = findViewById(R.id.btn_residence);
        btn_guidance = findViewById(R.id.btn_guidance);
        btn_vocation_training = findViewById(R.id.btn_vocation_training);
        btn_mental_retardation = findViewById(R.id.btn_mental_retardation);
        btn_disaster = findViewById(R.id.btn_disaster);
        btn_adjust = findViewById(R.id.btn_adjust);


        // !-- 네번 째 버튼
        btn_alcohol_addicted = findViewById(R.id.btn_alcohol_addicted);
        btn_gambling_addicted = findViewById(R.id.btn_gambling_addicted);
        btn_drug_addicted = findViewById(R.id.btn_drug_addicted);
        btn_smartphone_addicted = findViewById(R.id.btn_smartphone_addicted);






        complete_button = findViewById(R.id.complete_button);
        selectCancel_button = findViewById(R.id.selectCancel_button);
        picknum = findViewById(R.id.picknum);


        //!--버튼 Reset 메소드
        selectCancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonReset(btn_depress_anxiety,btn_sleeplessness,btn_stress,btn_phobia,btn_mental_illness,btn_suicide_thinking,btn_left_people,btn_self_injury
                        ,btn_adult,btn_kids,btn_elders,btn_parents_family,btn_healthcare
                        ,btn_out_clinic,btn_rehabilitation,btn_residence,btn_guidance,btn_vocation_training,btn_mental_retardation,btn_disaster,btn_adjust
                        ,btn_alcohol_addicted,btn_gambling_addicted,btn_drug_addicted,btn_smartphone_addicted);
            }
        });

        //!--키워드 클릭
        //!-- button 클릭인 겅우 status = 1
        //!-- 첫번째 라인 버튼
        btn_depress_anxiety.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_depress_anxiety_status,btn_depress_anxiety);
                if(result != -1){
                    btn_depress_anxiety_status = result;
                    Log.d(TAG,"btn_depress_anxiety_status: "+result);
                }
            }
        });



        btn_sleeplessness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_sleeplessness_status,btn_sleeplessness);
                if(result != -1){
                    btn_sleeplessness_status = result;
                }
            }
        });

        btn_stress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_stress_status,btn_stress);
                if(result != -1){
                    btn_stress_status = result;
                }
            }
        });



        btn_phobia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_phobia_status,btn_phobia);
                if(result != -1){
                    btn_phobia_status = result;
                }
            }
        });

        btn_mental_illness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_mental_illness_status,btn_mental_illness);
                if(result != -1){
                    btn_mental_illness_status = result;
                }
            }
        });
        btn_suicide_thinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_suicide_thinking_status,btn_suicide_thinking);
                if(result != -1){
                    btn_suicide_thinking_status = result;
                }
            }
        });

        btn_left_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_left_people_status,btn_left_people);
                if(result != -1){
                    btn_left_people_status = result;
                }
            }
        });

        btn_self_injury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_self_injury_status,btn_self_injury);
                if(result != -1){
                    btn_self_injury_status = result;
                }
            }
        });



        //!-- 두번째 라인 버튼
        btn_adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_adult_status,btn_adult);
                if(result != -1){
                    btn_adult_status = result;
                }
            }
        });

        btn_kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_kids_status,btn_kids);
                if(result != -1){
                    btn_kids_status = result;
                }
            }
        });

        btn_elders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_elders_status,btn_elders);
                if(result != -1){
                    btn_elders_status = result;
                }
            }
        });

        btn_parents_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_parents_family_status,btn_parents_family);
                if(result != -1){
                    btn_parents_family_status = result;
                }
            }
        });

        btn_healthcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_healthcare_status,btn_healthcare);
                if(result != -1){
                    btn_healthcare_status = result;
                }
            }
        });




        // !-- 세번 째 버튼
        btn_out_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_out_clinic_status,btn_out_clinic);
                if(result != -1){
                    btn_out_clinic_status = result;
                }
            }
        });

        btn_rehabilitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_rehabilitation_status,btn_rehabilitation);
                if(result != -1){
                    btn_rehabilitation_status = result;
                }
            }
        });

        btn_residence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_residence_status,btn_residence);
                if(result != -1){
                    btn_residence_status = result;
                }
            }
        });

        btn_guidance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_guidance_status,btn_guidance);
                if(result != -1){
                    btn_guidance_status = result;
                }
            }
        });

        btn_vocation_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_vocation_training_status,btn_vocation_training);
                if(result != -1){
                    btn_vocation_training_status = result;
                }
            }
        });

        btn_mental_retardation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_mental_retardation_status,btn_mental_retardation);
                if(result != -1){
                    btn_mental_retardation_status = result;
                }
            }
        });

        btn_disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_disaster_status,btn_disaster);
                if(result != -1){
                    btn_disaster_status = result;
                }
            }
        });

        btn_adjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_adjust_status,btn_adjust);
                if(result != -1){
                    btn_adjust_status = result;
                }
            }
        });


        //!--네번째 라인 버튼

        btn_alcohol_addicted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_alcohol_addicted_status,btn_alcohol_addicted);
                if(result != -1){
                    btn_alcohol_addicted_status = result;
                }
            }
        });

        btn_gambling_addicted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_gambling_addicted_status,btn_gambling_addicted);
                if(result != -1){
                    btn_gambling_addicted_status = result;
                }
            }
        });

        btn_drug_addicted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_drug_addicted_status,btn_drug_addicted);
                if(result != -1){
                    btn_drug_addicted_status = result;
                }
            }
        });

        btn_smartphone_addicted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = buttonAllInOne(btn_smartphone_addicted_status,btn_smartphone_addicted);
                if(result != -1){
                    btn_smartphone_addicted_status = result;
                }
            }
        });


        complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> btnList = new ArrayList<String>();
                if(sum == 0){
                    Toast.makeText(getApplicationContext(),"키워드를 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SelectActivity.this, MainActivity.class);

                // !-- 첫번째 버튼
                if( btn_depress_anxiety_status == 1){
                    btnList.add("btn_depress_anxiety");
                }
                if(btn_sleeplessness_status == 1){
                    btnList.add("btn_sleeplessness");
                }
                if(btn_stress_status == 1){
                    btnList.add("btn_stress");
                }
                if(btn_phobia_status == 1){
                    btnList.add("btn_phobia");
                }
                if(btn_mental_illness_status == 1){
                    btnList.add("btn_mental_illness");
                }
                if(btn_suicide_thinking_status == 1){
                    btnList.add("btn_suicide_thinking");
                }
                if(btn_left_people_status == 1){
                    btnList.add("btn_left_people");
                }
                if(btn_self_injury_status == 1){
                    btnList.add("btn_self_injury");
                }

                // !-- 두번째 버튼
                if(btn_adult_status == 1){
                    btnList.add("btn_adult");
                }
                if(btn_kids_status == 1){
                    btnList.add("btn_kids");
                }
                if(btn_elders_status == 1){
                    btnList.add("btn_elders");
                }
                if(btn_parents_family_status == 1){
                    btnList.add("btn_parents_family");
                }
                if(btn_healthcare_status == 1){
                    btnList.add("btn_healthcare");
                }

                if(btn_out_clinic_status == 1){
                    btnList.add("btn_out_clinic");
                }
                if(btn_rehabilitation_status == 1){
                    btnList.add("btn_rehabilitation");
                }
                if(btn_residence_status == 1){
                    btnList.add("btn_residence");
                }
                if(btn_guidance_status == 1){
                    btnList.add("btn_guidance");
                }
                if(btn_vocation_training_status == 1){
                    btnList.add("btn_vocation_training");
                }
                if(btn_mental_retardation_status == 1){
                    btnList.add("btn_mental_retardation");
                }
                if(btn_disaster_status == 1){
                    btnList.add("btn_disaster");
                }
                if(btn_adjust_status == 1){
                    btnList.add("btn_adjust");
                }
                if(btn_alcohol_addicted_status == 1){
                    btnList.add("btn_alcohol_addicted");
                }
                if(btn_gambling_addicted_status == 1){
                    btnList.add("btn_gambling_addicted");
                }
                if(btn_drug_addicted_status == 1){
                    btnList.add("btn_drug_addicted");
                }
                if(btn_smartphone_addicted_status == 1){
                    btnList.add("btn_smartphone_addicted");
                }


                for(int i = 0;i<btnList.size();i++){
                    Log.d(TAG,"SelectedBtnList : "+btnList.get(i));
                }

                BtnPrefMgr.setStringArrayPref(SelectActivity.this,BTN_PREF_KEY,btnList);
                startActivity(intent);
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
    private  void buttonReset(android.widget.Button button1, android.widget.Button button2,android.widget.Button button3,android.widget.Button button4,android.widget.Button button5,android.widget.Button button6,android.widget.Button button7,android.widget.Button button8
            ,android.widget.Button button9,android.widget.Button button10,android.widget.Button button11,android.widget.Button button12,android.widget.Button button13
            ,android.widget.Button button14,android.widget.Button button15,android.widget.Button button16,android.widget.Button button17,android.widget.Button button18,android.widget.Button button19,android.widget.Button button20,android.widget.Button button21
            ,android.widget.Button button22,android.widget.Button button23,android.widget.Button button24,android.widget.Button button25){


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

        button9.setBackgroundResource(R.drawable.button);
        button9.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button10.setBackgroundResource(R.drawable.button);
        button10.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button11.setBackgroundResource(R.drawable.button);
        button11.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button12.setBackgroundResource(R.drawable.button);
        button12.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button13.setBackgroundResource(R.drawable.button);
        button13.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button14.setBackgroundResource(R.drawable.button);
        button14.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button15.setBackgroundResource(R.drawable.button);
        button15.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button16.setBackgroundResource(R.drawable.button);
        button16.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button17.setBackgroundResource(R.drawable.button);
        button17.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button18.setBackgroundResource(R.drawable.button);
        button18.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button19.setBackgroundResource(R.drawable.button);
        button19.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button20.setBackgroundResource(R.drawable.button);
        button20.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button21.setBackgroundResource(R.drawable.button);
        button21.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button22.setBackgroundResource(R.drawable.button);
        button22.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button23.setBackgroundResource(R.drawable.button);
        button23.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button24.setBackgroundResource(R.drawable.button);
        button24.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));

        button25.setBackgroundResource(R.drawable.button);
        button25.setTextColor(getResources().getColor(R.color.buttonDefaultcolor,getTheme()));


        btn_depress_anxiety_status = 0;
        btn_sleeplessness_status = 0;
        btn_stress_status = 0;
        btn_phobia_status = 0;
        btn_mental_illness_status = 0;
        btn_suicide_thinking_status =0;
        btn_left_people_status=0;
        btn_self_injury_status =0;

        sum = 0;
        picknum.setText("0"); //초기화

    }

//!--end
}
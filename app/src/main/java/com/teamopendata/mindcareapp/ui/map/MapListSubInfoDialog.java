package com.teamopendata.mindcareapp.ui.map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teamopendata.mindcareapp.R;

public class MapListSubInfoDialog {

    private Context context;
    private MedicalInstitution mediInfo;

    public MapListSubInfoDialog(Context context,MedicalInstitution mediInfo) {
        this.context = context;
        this.mediInfo = mediInfo;
    }

    public void callFunction() {

        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_map_list_subinfo);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlg.show();

        final Button cancelButton = (Button) dlg.findViewById(R.id.btn_map_info_back);
        final Button callButton = (Button) dlg.findViewById(R.id.btn_map_info_call);
        final Button webButton = (Button) dlg.findViewById(R.id.btn_map_info_web);
        final TextView textView = (TextView) dlg.findViewById(R.id.tv_map_info_name);
        textView.setText(mediInfo.name+", "+mediInfo.tel+", "+mediInfo.address+", "+mediInfo.grade);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mediInfo.tel));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((Activity)context).startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(view.getContext(), "해당 기능이 불가능한 기기입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+mediInfo.url));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((Activity)context).startActivity(intent);
                }catch(Exception e)
                {
                    Toast.makeText(view.getContext(), "해당 기능이 불가능한 기기입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

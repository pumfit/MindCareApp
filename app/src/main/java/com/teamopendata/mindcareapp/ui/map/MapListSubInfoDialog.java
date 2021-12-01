package com.teamopendata.mindcareapp.ui.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamopendata.mindcareapp.common.MindChargeDB;
import com.teamopendata.mindcareapp.R;

import java.util.HashMap;
import java.util.Map;

public class MapListSubInfoDialog {
    public static Map<String, Integer> colormap = new HashMap() {
        {
            put("광역형정신건강증진센터", Integer.valueOf(R.drawable.ic_icon_location_fill_red));
            put("기초정신건강증진센터", Integer.valueOf(R.drawable.ic_icon_location_fill_orange));
            put("자살예방센터", Integer.valueOf(R.drawable.ic_icon_location_fill_yellow));
            put("중독관리통합지원센터", Integer.valueOf(R.drawable.ic_icon_location_fill_green));
            put("사회복귀시설", Integer.valueOf(R.drawable.ic_icon_location_fill_blue));
            put("정신의료기관", Integer.valueOf(R.drawable.ic_icon_location_fill_navy));
            put("정신요양시설", Integer.valueOf(R.drawable.ic_icon_location_fill_purple));
        }
    };

    public Context context;
    public boolean isMarked;
    public MedicalInstitution mediInfo;

    public MapListSubInfoDialog(Context context, MedicalInstitution mediInfo, boolean isMarked) {
        this.context = context;
        this.mediInfo = mediInfo;
        this.isMarked = isMarked;
    }

    public void callFunction() {
        Dialog dlg = new Dialog(this.context);
        dlg.requestWindowFeature(1);
        dlg.setContentView(R.layout.dialog_map_list_subinfo);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dlg.show();
        ImageButton callButton = (ImageButton) dlg.findViewById(R.id.btn_map_info_call);
        ImageButton webButton = (ImageButton) dlg.findViewById(R.id.btn_map_info_web);
        final ImageButton bookMarkButton = (ImageButton) dlg.findViewById(R.id.btn_map_info_bookmark);
        ImageView iconImageview = (ImageView) dlg.findViewById(R.id.iv_map_info_marker);
        TextView nameTextView = (TextView) dlg.findViewById(R.id.tv_map_info_name);
        TextView typeTextView = (TextView) dlg.findViewById(R.id.tv_map_info_type);
        TextView addressTextView = (TextView) dlg.findViewById(R.id.tv_map_info_address);
        TextView urlTextView = (TextView) dlg.findViewById(R.id.tv_map_info_url);
        TextView telTextView = (TextView) dlg.findViewById(R.id.tv_map_info_tel);
        if (this.isMarked) {
            buttonClicked(bookMarkButton);
        }
        iconImageview.setImageResource(colormap.get(this.mediInfo.type).intValue());
        nameTextView.setText(this.mediInfo.name);
        typeTextView.setText(this.mediInfo.type);
        addressTextView.setText(this.mediInfo.address);
        urlTextView.setText(this.mediInfo.url);
        telTextView.setText(this.mediInfo.tel);
        callButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + MapListSubInfoDialog.this.mediInfo.tel));
                    intent.addFlags(268435456);
                    ((Activity) MapListSubInfoDialog.this.context).startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "해당 기능이 불가능한 기기입니다.", 1).show();
                }
            }
        });
        webButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://" + MapListSubInfoDialog.this.mediInfo.url));
                    intent.addFlags(268435456);
                    ((Activity) MapListSubInfoDialog.this.context).startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "해당 기능이 불가능한 기기입니다.", 1).show();
                }
            }
        });
        bookMarkButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                if (!MapListSubInfoDialog.this.isMarked) {
                    MapListSubInfoDialog.this.buttonClicked(bookMarkButton);
                    MapListSubInfoDialog mapListSubInfoDialog = MapListSubInfoDialog.this;
                    new bookmarkInsertThread(mapListSubInfoDialog.mediInfo).start();
                    Toast.makeText(MapListSubInfoDialog.this.context, "즐겨찾기에 추가되었습니다.", 1).show();
                    MapListSubInfoDialog.this.isMarked = true;
                } else if (MapListSubInfoDialog.this.isMarked) {
                    MapListSubInfoDialog.this.buttonDefault(bookMarkButton);
                    MapListSubInfoDialog mapListSubInfoDialog2 = MapListSubInfoDialog.this;
                    new bookmarkDeleteThread(mapListSubInfoDialog2.mediInfo).start();
                    Toast.makeText(MapListSubInfoDialog.this.context, "즐겨찾기가 취소되었습니다.", 1).show();
                    MapListSubInfoDialog.this.isMarked = false;
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void buttonClicked(ImageButton button) {
        button.setImageDrawable(button.getResources().getDrawable(R.drawable.ic_icon_map_bookmark_starfilled));
    }

    /* access modifiers changed from: private */
    public void buttonDefault(ImageButton button) {
        button.setImageDrawable(button.getResources().getDrawable(R.drawable.ic_icon__map_bookmark_star));
    }

    /* renamed from: com.teamopendata.mindcareapp.ui.map.MapListSubInfoDialog$bookmarkInsertThread */
    class bookmarkInsertThread extends Thread {
        private MedicalInstitution medi;

        bookmarkInsertThread(MedicalInstitution medi2) {
            this.medi = medi2;
        }

        public void run() {
            MindChargeDB.getInstance(MapListSubInfoDialog.this.context).getBookMarkDao().insert(new BookMark(this.medi.id));
        }
    }

    /* renamed from: com.teamopendata.mindcareapp.ui.map.MapListSubInfoDialog$bookmarkDeleteThread */
    class bookmarkDeleteThread extends Thread {
        private MedicalInstitution medi;

        bookmarkDeleteThread(MedicalInstitution medi2) {
            this.medi = medi2;
        }

        public void run() {
            MindChargeDB.getInstance(MapListSubInfoDialog.this.context).getBookMarkDao().deleteById(this.medi.id);
        }
    }
}

package com.teamopendata.mindcareapp.ui.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.teamopendata.mindcareapp.R;

class CustomDialog {

    private Context context;

    public CustomDialog(Context context) {
        this.context = context;
    }

    public void callFunction() {

        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.map_question_dialog);
        dlg.show();

    }
}

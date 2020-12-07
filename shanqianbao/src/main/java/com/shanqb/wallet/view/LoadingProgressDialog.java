package com.shanqb.wallet.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.shanqb.wallet.R;

public class LoadingProgressDialog extends ProgressDialog {


    public LoadingProgressDialog(Context context) {
//        super(context);
        super(context, R.style.Progress_Dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progress_dialog);
    }

}

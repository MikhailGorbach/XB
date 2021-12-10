package com.example.firebasepicture.Utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatButton;

import com.example.firebasepicture.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Common.isConnectionToInternet(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.myFullscreenAlertDialogStyle);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
            builder.setView(layout_dialog);

            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.btnRetryNoInternetConnection);

            AlertDialog dialog = builder.create();

            ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;

            //dialog.getWindow().addContentView(layout_dialog, params);
            dialog.getWindow().setGravity( Gravity.FILL_VERTICAL  |Gravity.DISPLAY_CLIP_HORIZONTAL);

            dialog.show();
            dialog.setCancelable(false);


            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    onReceive(context, intent);

                }
            });
        }
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }


}

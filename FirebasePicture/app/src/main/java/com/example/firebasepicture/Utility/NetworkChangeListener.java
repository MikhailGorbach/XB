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
import android.widget.LinearLayout;

import com.example.firebasepicture.R;


public class NetworkChangeListener extends BroadcastReceiver {



    void two(Context context, Intent intent){



    }

    void one(Context context, Intent intent){
        if(!Common.isConnectionToInternet(context)){
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
            layout_dialog.setBackgroundResource(R.color.main_color);

            //layout_dialog.findViewById(R.id.imageView9).setLayoutParams(new LinearLayout.LayoutParams(M));


            AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.myFullscreenAlertDialogStyle);
            builder.setView(layout_dialog);

            AlertDialog dialog = builder.create();

            dialog.getWindow().setBackgroundDrawableResource(R.color.main_color);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

            dialog.show();
            dialog.setCancelable(false);

            layout_dialog.findViewById(R.id.btnRetryNoInternetConnection).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        one(context,intent);
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }


}

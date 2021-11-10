package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.firebasepicture.MainActivity;
import com.example.firebasepicture.Model;
import com.example.firebasepicture.R;

public class MyDialogFragment extends DialogFragment {
    private Model model;

    public MyDialogFragment(Model model) {
        super();
        this.model = model;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(this.getContext());

        dialog.setContentView(R.layout.fragment_dialog);
        ( (TextView) dialog.getWindow().findViewById(R.id.txtDialogSeller)).setText(model.getSeller() == null ? "Нет" : model.getSeller());
        ( (TextView) dialog.getWindow().findViewById(R.id.txtDialogOgrn)).setText(model.getOgrn());
        ( (Button) dialog.getWindow().findViewById(R.id.btnDialogOkey)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }
}

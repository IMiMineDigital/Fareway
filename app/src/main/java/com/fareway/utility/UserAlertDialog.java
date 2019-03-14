package com.fareway.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;



public class UserAlertDialog {
    Context context;
    public UserAlertDialog(Context context)
    {
        this.context=context;
    }

    public AlertDialog createPositiveAlert(String msg, String ok, String title)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(msg);
        // Setting OK Button


        alertDialog.setButton(Dialog.BUTTON_POSITIVE,ok,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return alertDialog;
    }

}


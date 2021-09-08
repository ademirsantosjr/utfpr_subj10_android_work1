package br.edu.utfpr.listadecomprasemmercados.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import br.edu.utfpr.listadecomprasemmercados.R;

public class UtilsGUI {

    public static void confirmAction(Context context,
                                     String message,
                                     DialogInterface.OnClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirm_removal);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(message);

        builder.setPositiveButton(R.string.yes, listener);
        builder.setNegativeButton(R.string.no, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }
}

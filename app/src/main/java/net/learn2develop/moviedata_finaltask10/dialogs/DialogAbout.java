package net.learn2develop.moviedata_finaltask10.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import net.learn2develop.moviedata_finaltask10.R;

public class DialogAbout extends AlertDialog.Builder {
    public DialogAbout(Context context) {
        super(context);

        setTitle(context.getString(R.string.About));

        setMessage(context.getString(R.string.created_by) + " Kisker");
        setPositiveButton(context.getString(R.string.Ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    public AlertDialog prepareDialog() {
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }
}


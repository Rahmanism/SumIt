package ir.rahmanism.general;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Rahmani on 08/01/2016.
 */
public class About {
    public Context context;
    public AlertDialog.Builder dialogBuilder;
    public AlertDialog dialog;
    public String message = "Mostafa Rahmani\nhttp://rahmanism.ir\n";
    public String okBtnText = "OK";

    public About(Context context, String msg) {
        this.message = msg;
        this.context = context;
        Init();
    }

    public About(Context context, String msg, String ok) {
        this.message = msg;
        this.context = context;
        this.okBtnText = ok;
        Init();
    }

    /// Makes the alert dialog builder
    private void Init() {
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(okBtnText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
        dialog = dialogBuilder.create();
//        dialog.show();
    }

    /// Shows the created dialog
    public void Show() {
        dialog.show();
    }
}

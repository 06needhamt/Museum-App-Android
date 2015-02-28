package riskybusiness.riskybusinessmuseumapp.root.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Alex on 28/02/2015.
 */
public class BackToMainMenuDialogFragment extends DialogFragment {
    private String question;
    private IConfirmDialogCompliant ICDC;
    public BackToMainMenuDialogFragment(){}

    public BackToMainMenuDialogFragment(String question){this.question = question;}

    public BackToMainMenuDialogFragment(String question, IConfirmDialogCompliant ICDC){this.question = question; this.ICDC = ICDC;}

    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(question);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ICDC.doYesConfirmClick();
                    this.finalize();
                } catch (Throwable e) {
                    e.printStackTrace();
                    System.out.println("For Some Reason We Could Not Close The Dialog");
                }

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ICDC.doNoConfirmClick();
                    this.finalize();
                } catch (Throwable E) {
                    E.printStackTrace();
                    System.out.println("For some reason Tom left this error in here.");
                }
            }
        });

        return builder.create();
    }
}


package riskybusiness.riskybusinessmuseumapp.root.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import riskybusiness.riskybusinessmuseumapp.R;

/**
 * Created by Alex on 28/02/2015.
 */
public class BackToMainMenuDialogFragment extends DialogFragment implements DialogInterface{
    private String question;
    private IConfirmDialogCompliant ICDC;
    private int resID = 0;
    public BackToMainMenuDialogFragment(){}

    @Deprecated
    public BackToMainMenuDialogFragment(String question){this.question = question;}

    @Deprecated
    public BackToMainMenuDialogFragment(String question, IConfirmDialogCompliant ICDC){this.question = question; this.ICDC = ICDC;}

    public BackToMainMenuDialogFragment(int resID) {this.resID = resID;}

    public BackToMainMenuDialogFragment(int resID, IConfirmDialogCompliant ICDC) {this.resID = resID; this.ICDC = ICDC;}

    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(resID == 0) { //resID is zero so use the question String
            builder.setMessage(question);
            builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        ICDC.doYesConfirmClick(IConfirmDialogCompliant.FROM_BACK_TO_MAIN_DIALOG);
                        this.finalize();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        System.out.println("For Some Reason We Could Not Close The Dialog");
                    }

                }
            });

            builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        ICDC.doNoConfirmClick(IConfirmDialogCompliant.FROM_BACK_TO_MAIN_DIALOG);
                        this.finalize();
                    } catch (Throwable E) {
                        E.printStackTrace();
                        System.out.println("For some reason Tom left this error in here.");
                    }
                }
            });
        }
        else { //use resID as the resource for the question (THIS SHOULD BE THE PREFERRED WAY OF DOING THIS)
            builder.setMessage(resID);
            builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        ICDC.doYesConfirmClick(IConfirmDialogCompliant.FROM_BACK_TO_MAIN_DIALOG);
                        this.finalize();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        System.out.println("For Some Reason We Could Not Close The Dialog");
                    }

                }
            });

            builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        ICDC.doNoConfirmClick(IConfirmDialogCompliant.FROM_BACK_TO_MAIN_DIALOG);
                        this.finalize();
                    } catch (Throwable E) {
                        E.printStackTrace();
                        System.out.println("For some reason Tom left this error in here.");
                    }
                }
            });
        }
        return builder.create();
    }

    @Override
    public void cancel() {
        ICDC.doNoConfirmClick(IConfirmDialogCompliant.FROM_BACK_TO_MAIN_DIALOG);
    }
}


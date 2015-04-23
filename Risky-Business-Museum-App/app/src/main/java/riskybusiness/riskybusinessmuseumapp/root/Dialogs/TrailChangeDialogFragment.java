package riskybusiness.riskybusinessmuseumapp.root.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;

/**
 * Created by Tom on 03/04/2015.
 */
public class TrailChangeDialogFragment extends DialogFragment implements AppConstants {
    private String Message;
    private IChoiceDialogCompliant Caller;
    private int resID = 0;
    private int selected = -1;
    ListView lv;

    @Deprecated
    private TrailChangeDialogFragment() {
    }

    @Deprecated
    private TrailChangeDialogFragment(String Message) {
        this.Message = Message;
    }

    public TrailChangeDialogFragment(IChoiceDialogCompliant caller) {
        this.Message = "Do you want to leave your current trail";
        this.Caller = caller;
    }

    public TrailChangeDialogFragment(String Message, IChoiceDialogCompliant caller) {
        this.Message = Message;
        this.Caller = caller;
    }
    @Deprecated
    public TrailChangeDialogFragment(int resID) {
        this.resID = resID;
    }


    public TrailChangeDialogFragment(int resID, IChoiceDialogCompliant caller) {
        this.resID = resID;
        this.Caller = caller;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        selected = STAY_ON_TRAIL;
        if (resID == 0) {
            builder.setTitle(Message);
        }
        else {
            builder.setTitle(getActivity().getResources().getString(resID));
        }
        builder.setSingleChoiceItems(R.array.TrailChangeChoices, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                Log.e("Position", String.valueOf(selected)); //selcted is based on Index of the selected option, make sure these relate to the values in AppConstants
            }
        });

        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Caller.doYesConfirmClick(IChoiceDialogCompliant.FROM_CHANGE_TRAIL_DIALOG,selected);
                    this.finalize();
                } catch (Throwable e) {
                    e.printStackTrace();
                    System.out.println("For Some Reason We Could Not Close The Dialog");
                }

            }
        });
        return builder.create();

    }
}

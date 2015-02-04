package riskybusiness.riskybusinessmuseumapp.root.Ddalogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import riskybusiness.riskybusinessmuseumapp.R;

public class ForSomeReasonDialogFragment extends DialogFragment {

	String Message;
	public ForSomeReasonDialogFragment() {
		this.Message = "For Some Reason we couldn't load the web page";
	}
	public ForSomeReasonDialogFragment(String Message)
	{
		this.Message = Message;
	}

	@Override
	public Dialog onCreateDialog(Bundle SavedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(Message);
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
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

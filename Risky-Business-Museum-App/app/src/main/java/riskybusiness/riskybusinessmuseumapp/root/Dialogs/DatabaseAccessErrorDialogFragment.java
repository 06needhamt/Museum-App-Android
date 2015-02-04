package riskybusiness.riskybusinessmuseumapp.root.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class DatabaseAccessErrorDialogFragment extends DialogFragment {
	
	String Message;

	public DatabaseAccessErrorDialogFragment() {
		this.Message = "There Was an error while accessing the database Check the location and password";
	}
	
	public DatabaseAccessErrorDialogFragment(String Message)
	{
		this.Message = Message;
	}
	
	public Dialog onCreateDialog(Bundle SavedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(this.Message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try
				{
					this.finalize();
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
				
			}
		});
		return builder.create();
	}
}

package riskybusiness.riskybusinessmuseumapp.root.Dialogs;

public interface IConfirmDialogCompliant {
    public final int FROM_SKIP_DIALOG = 0;
    public final int FROM_BACK_TO_MAIN_DIALOG = 1;
    public final int FROM_CHANGE_TRAIL_DIALOG = 2;

	public void doYesConfirmClick(final int from);
	public void doNoConfirmClick(final int from);
}

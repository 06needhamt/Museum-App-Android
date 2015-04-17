package riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import riskybusiness.riskybusinessmuseumapp.R;

/**
 * Created by Alex on 01/03/2015.
 */
public class WelcomeTrailFragment extends Fragment {
    View v;
    DisplayMetrics dm;
    TextView title, subTitle, instructions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome_trail, container, false);
        view.setPadding(0,0,0,0);

        v = view;
        getDisplayMetrics();
        setupViews();

        return view;
    }

    private void getDisplayMetrics() {
        dm = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    private void setupViews() {
        int itemWidth, itemHeight;

        itemWidth = dm.widthPixels;
        itemHeight = dm.heightPixels;

        title = (TextView) v.findViewById(R.id.title);
        title.setText(getActivity().getResources().getString(R.string.title_trail_welcome));
        title.setTextColor(getActivity().getResources().getColor(R.color.White));
        title.setLayoutParams(createTitleLayoutParams(itemWidth, itemHeight));

        subTitle = (TextView) v.findViewById(R.id.subTitle);
        subTitle.setText(getActivity().getResources().getString(R.string.sub_title_trail_welcome));
        subTitle.setTextColor(getActivity().getResources().getColor(R.color.White));
        subTitle.setLayoutParams(createSubTitleLayoutParams(itemWidth, itemHeight));

        instructions = (TextView) v.findViewById(R.id.instructions);
        instructions.setText(getActivity().getResources().getString(R.string.instructions));
        instructions.setTextColor(getActivity().getResources().getColor(R.color.White));
        instructions.setLayoutParams(createInstructionsLayoutParams(itemWidth, itemHeight));

    }

    private FrameLayout.LayoutParams createTitleLayoutParams(int itemWidth, int itemHeight) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(itemWidth, itemHeight);

        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;

        params.topMargin = (int) (itemHeight * 0.05);

        return params;

    }

    private FrameLayout.LayoutParams createSubTitleLayoutParams(int itemWidth, int itemHeight) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(itemWidth, itemHeight);

        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;

        params.topMargin = (int) (itemHeight * 0.20);

        return params;

    }

    private FrameLayout.LayoutParams createInstructionsLayoutParams(int itemWidth, int itemHeight) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(itemWidth, itemHeight);

        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;

        params.topMargin = (int) (itemHeight * 0.50);

        return params;

    }

}

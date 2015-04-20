package riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import riskybusiness.riskybusinessmuseumapp.R;

/**
 * Created by Tom on 17/04/2015.
 */
public class WelcomeExplorerTrailFragment extends Fragment {
    View v;
    DisplayMetrics dm;
    TextView title, trailsDescription;
    Resources res;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        res = getResources();

        View view = inflater.inflate(R.layout.fragment_welcome_explorer_trail, container, false);
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
        String description;
        CharSequence styledDescription;

        description = String.format(res.getString(R.string.explorer_trails_intro_long));
        styledDescription = Html.fromHtml(description);

        itemWidth = dm.widthPixels;
        itemHeight = dm.heightPixels;

        title = (TextView) v.findViewById(R.id.title);
        title.setText(res.getString(R.string.title_explorer_trail_welcome));
        title.setTextColor(res.getColor(R.color.White));
        title.setLayoutParams(createTitleLayoutParams(itemWidth, itemHeight));

        trailsDescription = (TextView) v.findViewById(R.id.trailDescription);
        trailsDescription.setText(styledDescription);
        trailsDescription.setTextColor(res.getColor(R.color.White));
        trailsDescription.setLayoutParams(createContentLayoutParams(itemWidth, itemHeight));



    }

    private FrameLayout.LayoutParams createTitleLayoutParams(int itemWidth, int itemHeight) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(itemWidth, itemHeight);

        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;

        params.topMargin = (int) (itemHeight * 0.05);

        return params;

    }

    private FrameLayout.LayoutParams createContentLayoutParams(int itemWidth, int itemHeight) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(itemWidth, itemHeight);

        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;

        return params;

    }

}

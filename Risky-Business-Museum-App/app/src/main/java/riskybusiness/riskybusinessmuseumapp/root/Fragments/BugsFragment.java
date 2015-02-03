package riskybusiness.riskybusinessmuseumapp.root.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import riskybusiness.riskybusinessmuseumapp.R;

/**
 * Created by Tom on 03/02/2015.
 */

public class BugsFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bugs, container, false);
        Button btnEnter = (Button) view.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Bugs Button Clicked");
            }
        });
        return view;
    }

    public void EnterButtonClicked(View v)
    {
        System.out.println("Bugs Button Clicked");
    }


}
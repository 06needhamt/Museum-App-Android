package riskybusiness.riskybusinessmuseumapp.root.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import riskybusiness.riskybusinessmuseumapp.R;

/**
 * Created by Tom on 03/02/2015.
 */
public class DinosaursFragment extends Fragment{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dinosaurs, container, false);
    }

    public void EnterButtonClicked(View v)
    {
        System.out.println("Dinosaurs Button Clicked");
    }
}


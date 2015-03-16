package riskybusiness.riskybusinessmuseumapp.root.classes;

//import android.annotation.TargetApi;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;
import riskybusiness.riskybusinessmuseumapp.root.Activities.QRScannerActivity;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.InformationFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.QRFragment;


/**
 * Created by Tom on 02/02/2015.
 */
public class ButtonCreator {


    public static final int NUM_BUTTONS = 5; // Number of BUTTONS (Columns in table)
    public static final int NUM_TOP_BUTTONS = 6; // Number of Buttons on top
    HomePageActivity act;
    TableLayout Toptable;
    TableLayout Bottomtable;
    int toptableid;
    int bottomtableid;
    Field[] fields;
    Fragment[] Trailfragments;
    Fragment[] Mapfragments;
    Fragment[] BottomFragments;
    Fragment[] InfoFragments;
    TableRow tableRowTop;

    Button[] Topbuttons = new Button[NUM_TOP_BUTTONS]; // Array for buttons
    Button[] Bottombuttons = new Button[NUM_BUTTONS]; // Array for buttons

    boolean[] TopButtonState = {false, false, false, false, false, false}; // Which top button is pressed?
    boolean[] BottomButtonState = {false, false, false, false, false}; // Which bottom button is pressed?
    boolean[] MapButtonState = {false, false, false, false, false, false}; // Which map button is pressed?
    boolean[] InfoButtonState = {false, false, false, false, false, false}; // Which map button is pressed?

    //ArrayList<IconInfo> drawableList = new ArrayList<>(); // Array list of type IconInfo for drawables

    IconList drawableList = new IconList(act); // Array list of type IconInfo for drawables
    final String[] iconUnderNamesTop = {"blue__icon_trail_ancientworld","blue__icon_trail_aquarium", "blue__icon_trail_bugs", "blue__icon_trail_worldcultures", "blue__icon_trail_dinosaurs", "blue__icon_trail_space"};
    final String[] iconOverNamesTop = {"green__icon_trail_ancientworld","green__icon_trail_aquarium", "green__icon_trail_bugs", "green__icon_trail_worldcultures", "green__icon_trail_dinosaurs", "green__icon_trail_space"};
    final String[] iconUnderNamesBottom = {"blue__icon_question", "blue__icon_trail", "blue__icon_qr", "blue__icon_map", "blue__icon_information"};
    final String[] iconOverNamesBottom = {"green__icon_question", "green__icon_trail", "green__icon_qr", "green__icon_map", "green__icon_information"};
    final String[] iconUnderNamesMap = {"blue__icon_floorg","blue__icon_floor1", "blue__icon_floor2", "blue__icon_floor3", "blue__icon_floor4", "blue__icon_floor5"};
    final String[] iconOverNamesMap = {"green__icon_floorg", "green__icon_floor1","green__icon_floor2", "green__icon_floor3", "green__icon_floor4", "green__icon_floor5"};
    ////////////////////// icon names needs changing  ///////////////////////////////
    final String[] iconUnderNamesInfo = {"blue__icon_floorg","blue__icon_floor1", "blue__icon_floor2", "blue__icon_floor3", "blue__icon_floor4", "blue__icon_floorg"};
    final String[] iconOverNamesInfo = {"green__icon_floorg", "green__icon_floor1","green__icon_floor2", "green__icon_floor3", "green__icon_floor4", "green__icon_floorg"};

    public ButtonCreator(HomePageActivity A, int toptable, int bottomtable, Field[] fields, Fragment[] Trailfragments, Fragment[] Mapfragments, Fragment[] BottomFragments, Fragment[] InfoFragments)
    {
        this.act = A;
        this.toptableid = toptable;
        this.bottomtableid = bottomtable;
        this.fields = fields;
        this.Trailfragments = Trailfragments;
        this.Mapfragments = Mapfragments;
        this.BottomFragments = BottomFragments;
        this.InfoFragments = InfoFragments;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    public void populateBottomButtons() {

        Bottomtable = (TableLayout) act.findViewById(this.bottomtableid); // Table to be populated

        getDrawables();

        TableRow tableRow = new TableRow(act); // Create new table row

        Bottomtable.addView(tableRow);  // Add the row to the table
        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, // Width
                180/*TableLayout.LayoutParams.MATCH_PARENT*/, // Height
                1.0f)); // Scale


        for(int col = 0; col < NUM_BUTTONS; col++) {
            final int finalCol = col;
            Button button = new Button(act); // Create button

            //button.setText("B" + col); // Add example text

            button.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, // Width
                    TableRow.LayoutParams.MATCH_PARENT, // Height
                    1.0f)); // Scale

            button.setPadding(0, 0, 0, 0);

//            drawablelist.indexOf(new Tuple<String, Drawable>(iconUnderNames[col]), )


            System.out.println(col);
            button.setBackground(act.getResources().getDrawable(drawableList.getId(iconUnderNamesBottom[col])));

            System.out.println("<<<<<<<<<<<<<<< OK Here >>>>>>>>>>>>>>>>>");

            // How do we get the right drawable from the list based on iconUnderNames[col] ?
            // button.setBackground(drawablelist.);

            ////// end add image

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomgridButtonClicked(finalCol);
                }
            });
            tableRow.addView(button); // Add button to the table
            Bottombuttons[col] = button; // Add the button to the buttons array

        }
    }


    public void populateMapButtons()
    {
       for(int i = 0; i < Topbuttons.length; i++)
       {
           final int btn = i;
           //Topbuttons[i] = new Button(act);
           //Topbuttons[i].setBackgroundColor(act.getResources().getColor(R.color.White));
           Topbuttons[i].setBackground(act.getResources().getDrawable(drawableList.getId(iconUnderNamesMap[i])));
           Topbuttons[i].setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   MapgridButtonClicked(btn);
               }
           });
       }
    }
    public void populateInfoButtons()
    {
        for(int i = 0; i < Topbuttons.length; i++)
        {
            final int btn = i;
            //Topbuttons[i] = new Button(act);
            //Topbuttons[i].setBackgroundColor(act.getResources().getColor(R.color.White));
            Topbuttons[i].setBackground(act.getResources().getDrawable(drawableList.getId(iconUnderNamesInfo[i])));
            Topbuttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapgridButtonClicked(btn);
                }
            });
        }
    }
    public void populateTopButtons() {

        getDrawables();
        Toptable = (TableLayout) act.findViewById(toptableid); // Table to be populated

        tableRowTop = new TableRow(act); // Create new table row

        Toptable.addView(tableRowTop);  // Add the row to the table
        tableRowTop.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, // Width
                180/*TableLayout.LayoutParams.MATCH_PARENT*/, // Height
                1.0f)); // Scale


        for(int col = 0; col < NUM_TOP_BUTTONS; col++) {
            final int finalCol = col;
            Button button = new Button(act); // Create button

            //button.setText("B" + col); // Add example text

            button.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, // Width
                    TableRow.LayoutParams.MATCH_PARENT, // Height
                    1.0f)); // Scale

            button.setPadding(0, 0, 0, 0);

//            drawablelist.indexOf(new Tuple<String, Drawable>(iconUnderNames[col]), )



            button.setBackground(act.getResources().getDrawable(drawableList.getId(iconUnderNamesTop[col])));

            System.out.println("<<<<<<<<<<<<<<< OK Here >>>>>>>>>>>>>>>>>");

            // How do we get the right drawable from the list based on iconUnderNames[col] ?
            // button.setBackground(drawablelist.);

            ////// end add image

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TopgridButtonClicked(finalCol);
                }
            });
            tableRowTop.addView(button); // Add button to the table
            Topbuttons[col] = button; // Add the button to the buttons array

        }
    }

    /**
     * Tom's getDrawables
     * Gets the drawable items from the resources based on name starting with x_
     * and popluates drawableList
     *
     */
    public void getDrawables() {

        for (Field F : fields) {
            try
            {
                String name = F.getName();
                int id = act.getResources().getIdentifier(name, "drawable", act.getPackageName()); // Get the resource ID
                //Drawable d = getResources().getDrawable(id); // Don't need to get this, just retrieve id instead

                drawableList.add(new IconInfo(name, id)); // Add IconInfo with icon details


            }
            catch(Exception ex)
            {
                System.out.println("Could not load icon ");
                ex.printStackTrace();

            }
        }

        // Debuging output, itterate throught the icon list and print info
        System.out.println("Icons found:");
//        for(IconInfo icn : drawableList.icons) {
//            System.out.println(icn.name + " : " + icn.iconID);
//        }
    }



    public void TopgridButtonClicked(int btn) { // Show Toast message

        //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
        resetButtonBackgroundTop(btn);
        resetButtonBackgroundBottom(-1);
        act.getFragmentManager().beginTransaction().replace(R.id.frame, Trailfragments[btn]).commit();

    }

    public void MapgridButtonClicked(int btn)
    {
        //Toast.makeText(act, "Map Button Clicked " + btn, Toast.LENGTH_SHORT).show();
        resetButtonBackgroundMap(btn);
        act.getFragmentManager().beginTransaction().replace(R.id.frame, Mapfragments[btn]).commit();

    }

    public void InfogridButtonClicked(int btn)
    {
        //Toast.makeText(act, "Map Button Clicked " + btn, Toast.LENGTH_SHORT).show();
        resetButtonBackgroundInfo(btn);
        act.getFragmentManager().beginTransaction().replace(R.id.frame, InfoFragments[btn]).commit();

    }


    public void BottomgridButtonClicked(int btn) { // Show Toast message

        switch(btn)
        {
            case 0:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateTopButtons();
                resetButtonBackgroundBottom(btn);
                resetButtonBackgroundTop(-1);
                break;
            }
            case 1:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateTopButtons();
                resetButtonBackgroundBottom(btn);
                resetButtonBackgroundTop(-1);
                break;
            }
            case 2:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateTopButtons();
                resetButtonBackgroundBottom(btn);
                act.getFragmentManager().beginTransaction().replace(R.id.frame, BottomFragments[btn]).commit();
                //StartQRActivity();
                break;
            }
            case 3:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateMapButtons();
                act.getFragmentManager().beginTransaction().replace(R.id.frame, BottomFragments[btn]).commit();
                resetButtonBackgroundBottom(btn);
                resetButtonBackgroundMap(0);
                //StartQRActivity();
                break;
            }
            case 4:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateInfoButtons();
                resetButtonBackgroundBottom(btn);
                resetButtonBackgroundTop(-1);
                resetButtonBackgroundInfo(0);
                act.getFragmentManager().beginTransaction().replace(R.id.frame, BottomFragments[btn]).commit();

                break;
            }
        }
    }

    private void StartQRActivity() {

        Intent I = new Intent(act.getBaseContext(), QRScannerActivity.class);
        act.CallQRScannerActivity();
        resetButtonBackgroundTop(-1);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void resetButtonBackgroundBottom(int btn)
    {
        for(int i = 0; i < NUM_BUTTONS; i++)
        {
            final int col = i;
            if(i != btn)
            {
                int id = drawableList.getId(iconUnderNamesBottom[i]);
                Bottombuttons[i].setBackground(act.getResources().getDrawable(id));
                BottomButtonState[i] = false; ;
            }
            else
            {
                int id = drawableList.getId(iconOverNamesBottom[i]);
                Bottombuttons[i].setBackground(act.getResources().getDrawable(id));
                BottomButtonState[i] = true;
            }
        }

    }

    public void resetButtonBackgroundMap(int btn)
    {
        for(int i = 0; i < NUM_TOP_BUTTONS; i++)
        {
            final int col = i;
            if(i != btn)
            {
                int id = drawableList.getId(iconUnderNamesMap[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
                Topbuttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MapgridButtonClicked(col);

                    }
                });
                MapButtonState[i] = false;
            }
            else
            {
                int id = drawableList.getId(iconOverNamesMap[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
                Topbuttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MapgridButtonClicked(col);
                    }
                });
                MapButtonState[i] = true;
            }
        }

    }

    public void resetButtonBackgroundTop(int btn)
    {
        for(int i = 0; i < NUM_TOP_BUTTONS; i++)
        {
            final int col = i;
            if(i != btn)
            {
                int id = drawableList.getId(iconUnderNamesTop[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
                Topbuttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TopgridButtonClicked(col);
                    }
                });
                TopButtonState[i] = false;
            }
            else
            {
                int id = drawableList.getId(iconOverNamesTop[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
                Topbuttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TopgridButtonClicked(col);
                    }
                });
                TopButtonState[i] = true;
            }
        }
    }

    public void resetButtonBackgroundInfo(int btn)
    {
        for(int i = 0; i < NUM_TOP_BUTTONS; i++)
        {
            final int col = i;
            if(i != btn)
            {
                int id = drawableList.getId(iconUnderNamesInfo[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
                Topbuttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InfogridButtonClicked(col);
                    }
                });
                InfoButtonState[i] = false;
            }
            else
            {
                int id = drawableList.getId(iconOverNamesInfo[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
                Topbuttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InfogridButtonClicked(col);
                    }
                });
                InfoButtonState[i] = true;
            }
        }
    }

    public int getPressedTop() {
        for(int t = 0; t < TopButtonState.length; t++)
            if(TopButtonState[t])
                return t; // return the index of the pressed button

        return -1; // Error - no buttons pressed

    }

    public int getPressedBottom () {
        for(int t = 0; t < BottomButtonState.length; t++)
            if(BottomButtonState[t])
                return t; // return the index of the pressed button

        return -1; // Error - no buttons pressed

    }

    public int getPressedMap() {
        for(int t = 0; t < MapButtonState.length; t++)
            if(MapButtonState[t])
                return t; // return the index of the pressed button

        return -1; // Error - no buttons pressed
    }

    public int getPressedInfo() {
        for(int t = 0; t < InfoButtonState.length; t++)
            if(InfoButtonState[t])
                return t; // return the index of the pressed button

        return -1; // Error - no buttons pressed
    }


}

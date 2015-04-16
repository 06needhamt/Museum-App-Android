package riskybusiness.riskybusinessmuseumapp.root.classes;

//import android.annotation.TargetApi;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.lang.reflect.Field;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;
import riskybusiness.riskybusinessmuseumapp.root.Activities.QRScannerActivity;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.informationFragments.InformationWebView;


/**
 * Created by Tom on 02/02/2015.
 */
public class ButtonCreator implements AppConstants{


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
    InformationWebView infoWebView;

    Button[] Topbuttons = new Button[NUM_TOP_BUTTONS]; // Array for buttons
    Button[] Bottombuttons = new Button[NUM_BUTTONS]; // Array for buttons

    boolean[] TopButtonState = {false, false, false, false, false, false}; // Which top button is pressed?
    boolean[] BottomButtonState = {false, false, false, false, false}; // Which bottom button is pressed?
    boolean[] MapButtonState = {false, false, false, false, false, false}; // Which map button is pressed?
    boolean[] InfoButtonState = {false, false, false, false, false, false}; // Which map button is pressed?

    //ArrayList<IconInfo> drawableList = new ArrayList<>(); // Array list of type IconInfo for drawables

    IconList drawableList = new IconList(act); // Array list of type IconInfo for drawables
    final String[] iconUnderNamesTop = {"blue___icon_ancientworld","blue___icon_aquarium", "blue___icon_bugs", "blue___icon_worldcultures", "blue___icon_dinosaurs", "blue___icon_space"};
    final String[] iconOverNamesTop = {"green___icon_ancientworld","green___icon_aquarium", "green___icon_bugs", "green___icon_worldcultures", "green___icon_dinosaurs", "green___icon_space"};
    final String[] iconUnderNamesBottom = {"blue___icon_question", "blue___icon_explorer", "blue___icon_qr", "blue___icon_map", "blue___icon_information"};
    final String[] iconOverNamesBottom = {"green___icon_question", "green___icon_explorer", "green___icon_qr", "green___icon_map", "green___icon_information"};
    final String[] iconUnderNamesMap = {"blue___icon_floorg","blue___icon_floor1", "blue___icon_floor2", "blue___icon_floor3", "blue___icon_floor4", "blue___icon_floor5"};
    final String[] iconOverNamesMap = {"green___icon_floorg", "green___icon_floor1","green___icon_floor2", "green___icon_floor3", "green___icon_floor4", "green___icon_floor5"};
    ////////////////////// icon names needs changing  ///////////////////////////////
    final String[] iconUnderNamesInfo = {"blue___icon_appinfo","blue___icon_museuminfo", "blue___icon_planetarium", "blue___icon_cafe", "blue___icon_toilets", "blue___icon_information"};
    final String[] iconOverNamesInfo = {"green___icon_appinfo", "green___icon_museuminfo","green___icon_planetarium", "green___icon_cafe", "green___icon_toilets", "green___icon_information"};

    public ButtonCreator(HomePageActivity A, int toptable, int bottomtable, Field[] fields, Fragment[] Trailfragments, Fragment[] Mapfragments, Fragment[] BottomFragments, InformationWebView infoWebView)
    {

        this.act = A;
        this.toptableid = toptable;
        this.bottomtableid = bottomtable;
        this.fields = fields;
        this.Trailfragments = Trailfragments;
        this.Mapfragments = Mapfragments;
        this.BottomFragments = BottomFragments;
        //this.InfoFragments = InfoFragments;
        this.infoWebView = infoWebView;
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

    public void lightUpButtons(final int which){
        resetButtonBackgroundBottom(-1);
        if(which == TRAIL) {
            Bottombuttons[0].setBackgroundResource(R.drawable.purple___icon_question);
        }

        else if(which == EXPLORER) {
            Bottombuttons[1].setBackgroundResource(R.drawable.purple___icon_explorer);
        }
        else if(which == TRAIL_AND_EXPLORER)
        {
            Bottombuttons[0].setBackgroundResource(R.drawable.purple___icon_question);
            Bottombuttons[1].setBackgroundResource(R.drawable.purple___icon_explorer);
        }
        else
        {
            throw new Error("Invalid Button Highlight Pattern");
        }
        Bottombuttons[2].setBackgroundResource(R.drawable.green___icon_qr);
    }
    public void makeTopButtonsInvisible(){
        Toptable.setVisibility(View.GONE);
    }

    public void makeTopButtonsVisible(){
        Toptable.setVisibility(View.VISIBLE);
    }

@Deprecated
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
    @Deprecated
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
    }



    public void TopgridButtonClicked(int btn) { // Show Toast message

        //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
        resetButtonBackgroundTop(btn);
        //resetButtonBackgroundBottom(-1);
        act.getFragmentManager().beginTransaction().replace(R.id.frame, Trailfragments[btn]).commit();

    }

    public void MapgridButtonClicked(int btn)
    {
        //Toast.makeText(act, "Map Button Clicked " + btn, Toast.LENGTH_SHORT).show();
        resetButtonBackgroundMap(btn);
        act.getFragmentManager().beginTransaction().replace(R.id.frame, Mapfragments[btn]).commit();

    }


    public void InfogridButtonClicked(int btn) {
        //Toast.makeText(act, "Map Button Clicked " + btn, Toast.LENGTH_SHORT).show();
        resetButtonBackgroundInfo(btn);
        //act.getFragmentManager().beginTransaction().replace(R.id.frame, InfoFragments[btn]).commit();
        infoWebView.loadPage(DEFAULT_PAGE);

        switch(btn){
            case 0:
                infoWebView.loadPage(APP_INFO_PAGE);
                break;
            case 1:
                //@TODO add museum information page
                break;
            case 2:
                infoWebView.loadPage(CAFE_AND_SHOP_PAGE);
                break;
            case 3:
                infoWebView.loadPage(FACILITIES_PAGE);
                break;
            case 4:
                infoWebView.loadPage(INFO_DESK_PAGE);
                break;
            case 5:
                infoWebView.loadPage(PLANETARIUM_PAGE);
                break;

        }
    }


    public void BottomgridButtonClicked(int btn) { // Show Toast message

        switch(btn)
        {
            case 0:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateTopButtons();
                makeTopButtonsVisible();
                resetButtonBackgroundBottom(btn);
                resetButtonBackgroundTop(-1);
                break;
            }
            case 1:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateTopButtons();
                makeTopButtonsVisible();
                resetButtonBackgroundBottom(btn);
                resetButtonBackgroundTop(-1);
                break;
            }
            case 2:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateTopButtons();
                makeTopButtonsInvisible();
                resetButtonBackgroundBottom(btn);
                act.getFragmentManager().beginTransaction().replace(R.id.frame, BottomFragments[btn]).commit();
                //StartQRActivity();
                break;
            }
            case 3:
            {
                //Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
                //populateMapButtons();
                makeTopButtonsVisible();
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
                makeTopButtonsVisible();
                resetButtonBackgroundBottom(btn);
                //resetButtonBackgroundTop(-1);
                resetButtonBackgroundInfo(-1);
                act.getFragmentManager().beginTransaction().replace(R.id.frame, infoWebView).commit();

                break;
            }
        }
    }
@Deprecated
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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void resetButtonBackgroundInfo(int btn)
    {
//        for(String s : iconUnderNamesInfo)
//        {
//            Log.e("Array",s);
//        }
        //Log.e("Contains", String.valueOf(drawableList.getIcon("blue___icon_appinfo").iconID));
//        final int[] idsblueinfo = {2130837557,2130837571,2130837572,2130837560,2130837576,2130837569};
//        final int[] idsgreeninfo = {2130837598,2130837612,2130837613,2130837601,2130837617,2130837610};

        for(int i = 0; i < NUM_TOP_BUTTONS; i++)
        {
            final int col = i;
            //Log.e("btn", String.valueOf(iconUnderNamesInfo[i]));

            if(i != btn)
            {
                int id = drawableList.getId(iconUnderNamesInfo[i]);
                //Log.e("Icon id", String.valueOf(id));
                //Log.e("under names",iconOverNamesInfo[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
//                Topbuttons[i].setBackground(act.getResources().getDrawable((idsblueinfo[i])));
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
//                Topbuttons[i].setBackground(act.getResources().getDrawable(idsgreeninfo[i]));
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

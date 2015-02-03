package riskybusiness.riskybusinessmuseumapp.root.classes;

//import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.lang.reflect.Field;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.AncientWorldFragment;


/**
 * Created by Tom on 02/02/2015.
 */
public class ButtonCreator {


    public static final int NUM_BUTTONS = 5; // Number of BUTTONS (Columns in table)
    Activity act;
    TableLayout Toptable;
    TableLayout Bottomtable;
    int toptableid;
    int bottomtableid;
    Field[] fields;
    Fragment[] fragments;

    Button[] Topbuttons = new Button[NUM_BUTTONS]; // Array for buttons
    Button[] Bottombuttons = new Button[NUM_BUTTONS]; // Array for buttons


    //ArrayList<IconInfo> drawableList = new ArrayList<>(); // Array list of type IconInfo for drawables

    IconList drawableList = new IconList(act); // Array list of type IconInfo for drawables
    final String[] iconUnderNamesTop = {"blue__icon_trail_ancientworld", "blue__icon_trail_bugs", "blue__icon_trail_worldcultures", "blue__icon_trail_dinosaurs", "blue__icon_trail_space"};
    final String[] iconOverNamesTop = {"green__icon_trail_ancientworld", "green__icon_trail_bugs", "green__icon_trail_worldcultures", "green__icon_trail_dinosaurs", "green__icon_trail_space"};
    final String[] iconUnderNamesBottom = {"blue__icon_question", "blue__icon_trail", "blue__icon_qr", "blue__icon_map", "blue__icon_information"};
    final String[] iconOverNamesBottom = {"green__icon_question", "green__icon_trail", "green__icon_qr", "green__icon_map", "green__icon_information"};


    public ButtonCreator(Activity A, int toptable, int bottomtable, Field[] fields, Fragment[] fragments)
    {
        this.act = A;
        this.toptableid = toptable;
        this.bottomtableid = bottomtable;
        this.fields = fields;
        this.fragments = fragments;
    }

    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)

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

    public void populateTopButtons() {

        Bottomtable = (TableLayout) act.findViewById(toptableid); // Table to be populated

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
            tableRow.addView(button); // Add button to the table
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

            }
        }

        // Debuging output, itterate throught the icon list and print info
        System.out.println("Icons found:");
        for(IconInfo icn : drawableList.icons) {
            System.out.println(icn.name + " : " + icn.iconID);
        }
    }



    public void TopgridButtonClicked(int btn) { // Show Toast message

        Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
        resetButtonBackgroundTop(btn);
        act.getFragmentManager().beginTransaction().replace(R.id.frame, fragments[btn]).commit();

    }

    public void BottomgridButtonClicked(int btn) { // Show Toast message

        Toast.makeText(act, "Button Clicked " + btn, Toast.LENGTH_SHORT).show();
        resetButtonBackgroundBottom(btn);
    }


    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void resetButtonBackgroundBottom(int btn)
    {
        // int index=0;

        for(int i = 0; i < 5; i++)
        {
            if(i != btn)
            {
                int id = drawableList.getId(iconUnderNamesBottom[i]);
                Bottombuttons[i].setBackground(act.getResources().getDrawable(id));
                // index++;
            }
            else
            {
                int id = drawableList.getId(iconOverNamesBottom[i]);
                Bottombuttons[i].setBackground(act.getResources().getDrawable(id));
                //index++;
            }
        }

    }
    public void resetButtonBackgroundTop(int btn)
    {
        // int index=0;

        for(int i = 0; i < 5; i++)
        {
            if(i != btn)
            {
                int id = drawableList.getId(iconUnderNamesTop[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
                // index++;
            }
            else
            {
                int id = drawableList.getId(iconOverNamesTop[i]);
                Topbuttons[i].setBackground(act.getResources().getDrawable(id));
                //index++;
            }
        }
    }
}

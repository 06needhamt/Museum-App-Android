package riskybusiness.riskybusinessmuseumapp.root.Activities;

//import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


        import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.BugsFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.AncientWorldFragment;
import riskybusiness.riskybusinessmuseumapp.root.classes.ButtonCreator;

public class HomePageActivity extends FragmentActivity {

    int toptable;
    int bottomtable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toptable = R.id.topTableForButtons;
        bottomtable = R.id.bottomTableForButtons;
        ButtonCreator btncreate = new ButtonCreator(this,toptable,bottomtable,R.drawable.class.getFields(),new AncientWorldFragment());
        btncreate.populateTopButtons();
        btncreate.populateBottomButtons();
        //setContentView(R.layout.fragment_bugs);
        AddInfoFragment();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AddInfoFragment() {
        BugsFragment fragment = new BugsFragment();
    getFragmentManager().beginTransaction().add(R.id.frame, fragment).commit();
    }

    public void ReplaceFragment()
    {
        AncientWorldFragment fragment = new AncientWorldFragment();
        getFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

}

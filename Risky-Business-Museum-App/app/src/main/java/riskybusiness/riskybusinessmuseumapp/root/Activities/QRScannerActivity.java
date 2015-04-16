package riskybusiness.riskybusinessmuseumapp.root.Activities;

/**
 * Created by Tom on 03/02/2015.
 */

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.inputmethodservice.Keyboard;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import riskybusiness.riskybusinessmuseumapp.R;
import android.os.Build;

public class QRScannerActivity extends ActionBarActivity {

    TextView formatTxt;
    TextView contentTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        formatTxt = (TextView) findViewById(R.id.formatTxt);
        contentTxt = (TextView) findViewById(R.id.contentTxt);

        System.out.println("Android Build = " + Build.BRAND);

        // The following code tests if it being run on an emulator (generic), if so does not call the QR scanner
        if(Build.BRAND.contains("generic")) { // Generic emulator
            Bundle B = new Bundle();


            String artefactNumber = "81"; //<<<<<<<<----- change the number to simulate scanning different artefacts

            B.putString("Content", "CONTENT: http://riskybuisiness.co.uk/" + artefactNumber);
            B.putString("Format", "FORMAT: Test");
            B.putString("FROM", "QRScannerActivity");
            Intent i = getIntent();
            i.putExtras(B);
            setIntent(i);
            setResult(RESULT_OK, i);
            finish();
        }
        else { // Not running on emulator so use QR scanner
            initiateScan();
        }

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event)
    {
        if(KeyCode == KeyEvent.KEYCODE_BACK) {
            Bundle B = Bundle.EMPTY;
            Intent i = getIntent();
            i.putExtras(B);
            setIntent(i);
            setResult(RESULT_OK, i);
            finish();
            return true;
        }
        else
        {
            super.onKeyDown(KeyCode,event);
            return false;
        }
    }

    /**
     * Start a scan only accepting QR codes and not saving them to the history.
     */
    public void initiateScan()
    {
        //instantiate ZXing integration class
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        //start scanning
        List<String> formats = new ArrayList<String>();
        formats.add("QR_CODE");
        scanIntegrator.initiateScan(formats,-1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //check we have a valid result
        if (scanningResult != null) {
            //get content from Intent Result
            String scanContent = scanningResult.getContents();
            //get format name of data scanned
            String scanFormat = scanningResult.getFormatName();
            //output to UI
            formatTxt.setText((CharSequence) ("FORMAT: " + scanFormat));
            contentTxt.setText((CharSequence) ("CONTENT: " + scanContent));

            ///////////////////////////////////////////////////////////////////////////////////
            Bundle B = new Bundle();
            B.putString("Content",(String) contentTxt.getText());
            B.putString("Format", (String) formatTxt.getText());
            B.putString("FROM", "QRScannerActivity");
            Intent i = getIntent();
            i.putExtras(B);
            setIntent(i);
            setResult(RESULT_OK,i);
            finish();

        }
        else{
            //invalid scan data or scan cancelled
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
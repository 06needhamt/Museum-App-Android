package riskybusiness.riskybusinessmuseumapp.root.trailmanager;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * A File testing reading images from the assets folder
 */
public class ImageTest extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_test);
//
//        Bundle bundle;
//        String fileName;
//
//        ImageView view = (ImageView) findViewById(R.id.MyImageView);
//        view.setOnClickListener(this);
//
//        bundle = getIntent().getExtras();
//
//        fileName = bundle.getString("image");
//
//        TextView textView = (TextView) findViewById(R.id.myTextView);
//
//        textView.setText(fileName);
//
//        AssetManager asset = getAssets();
//
//        String[] imageFiles;
//
//        try {
//
//            imageFiles = getAssets().list("database_images");
//
//
//
////            for (String fileNames : getAssets().list("")) {
////                if (fileNames.endsWith(".jpg") || fileNames.endsWith(".png")) {
////                    imageFiles.add(fileNames);
////                }
////            }
//        } catch(IOException e) {
//            e.printStackTrace();
//            imageFiles = null;
//        }
//
//        Random r = new Random();
//
//        // Read bitmap from assets
//        try {
//            int t;
//
//            if(imageFiles != null) {
//
//                t = r.nextInt(imageFiles.length);
//
//                InputStream open = asset.open("database_images"+File.separator+imageFiles[t]);
//                Bitmap bitmap = BitmapFactory.decodeStream(open);
//
//                open.close();
//
//                view.setImageBitmap(bitmap);
//                textView.setText(imageFiles[t]);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//

    }

    @Override
    public void onClick(View arg0) { // If the image is clicked, close activity returning a string.
        Intent intent = new Intent();
        intent.putExtra("test", "Hello from ImageTest");
        setResult(RESULT_OK, intent);

        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_image_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}

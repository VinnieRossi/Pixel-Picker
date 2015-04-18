package com.example.vinnie.pixelpicker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LayoutGeneratorActivity extends ActionBarActivity {

    private RelativeLayout layout;
    private ImageView box1, box2, box3, box4;
    private String hex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_generator);

        hex = getIntent().getExtras().getString("Hex");
        layout = (RelativeLayout) findViewById(R.id.relativelayout);
        layout.setBackgroundColor(Color.parseColor(hex));

        box1 = (ImageView) findViewById(R.id.box1);
        box2 = (ImageView) findViewById(R.id.box2);
        box3 = (ImageView) findViewById(R.id.box3);
        box4 = (ImageView) findViewById(R.id.box4);

        getScheme(hex);
    }

    public void getScheme(String hexValue) {

        int r = Integer.parseInt(hexValue.substring(1, 3), 16);
        int g = Integer.parseInt(hexValue.substring(3, 5), 16);
        int b = Integer.parseInt(hexValue.substring(5, 7), 16);
        float[] hsv = new float[3];
        int color;

        Color.RGBToHSV(r, g, b, hsv);
        hsv[2] *= 0.8f;
        color = Color.HSVToColor(hsv);
        box1.setBackgroundColor(color);

        hsv[2] *= 0.8f;
        color = Color.HSVToColor(hsv);
        box2.setBackgroundColor(color);

        hsv[2] *= 0.8f;
        color = Color.HSVToColor(hsv);
        box3.setBackgroundColor(color);

        hsv[2] *= 0.8f;
        color = Color.HSVToColor(hsv);
        box4.setBackgroundColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout_generator, menu);
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
}

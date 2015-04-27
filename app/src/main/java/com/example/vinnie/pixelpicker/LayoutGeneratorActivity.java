package com.example.vinnie.pixelpicker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutGeneratorActivity extends ActionBarActivity {

    private RelativeLayout layout;
    private ImageView topLeft, topRight, botLeft, botRight;
    private TextView topLeftVal, topRightVal, botLeftVal, botRightVal;
    private String hex, colorToSave;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_generator);

        db = new DataBaseHandler(getApplicationContext());

        hex = getIntent().getExtras().getString("Hex");
        layout = (RelativeLayout) findViewById(R.id.relativelayout);
        layout.setBackgroundColor(Color.parseColor(hex));

        topLeft = (ImageView) findViewById(R.id.top_left_box);
        topRight = (ImageView) findViewById(R.id.top_right_box);
        botLeft = (ImageView) findViewById(R.id.bottom_left_box);
        botRight = (ImageView) findViewById(R.id.bottom_right_box);

        topLeftVal = (TextView) findViewById(R.id.top_left_value);
        topRightVal = (TextView) findViewById(R.id.top_right_value);
        botLeftVal = (TextView) findViewById(R.id.bottom_left_value);
        botRightVal = (TextView) findViewById(R.id.bottom_right_value);

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
        topLeft.setBackgroundColor(color);
        topLeftVal.setText(getRGB(color));

        hsv[2] *= 0.8f;
        color = Color.HSVToColor(hsv);
        topRight.setBackgroundColor(color);
        topRightVal.setText(getRGB(color));

        hsv[2] *= 0.8f;
        color = Color.HSVToColor(hsv);
        botLeft.setBackgroundColor(color);
        botLeftVal.setText(getRGB(color));

        hsv[2] *= 0.8f;
        color = Color.HSVToColor(hsv);
        botRight.setBackgroundColor(color);
        botRightVal.setText(getRGB(color));
    }

    public String getRGB(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // Dark background = White text and Light Background = Black text
        int c = Math.round((red * 299) + (blue * 587) + (green * 114)) / 1000;
        if (c > 125) {
            topLeftVal.setTextColor(Color.BLACK);
            topRightVal.setTextColor(Color.BLACK);
            botLeftVal.setTextColor(Color.BLACK);
            botRightVal.setTextColor(Color.BLACK);
        } else {
            topLeftVal.setTextColor(Color.WHITE);
            topRightVal.setTextColor(Color.WHITE);
            botLeftVal.setTextColor(Color.WHITE);
            botRightVal.setTextColor(Color.WHITE);
        }

        String hexValue = "#";
        if (red < 16) {
            hexValue += "0" + Integer.toHexString(red);
        } else hexValue += Integer.toHexString(red);

        if (green < 16) {
            hexValue += "0" + Integer.toHexString(green);
        } else hexValue += Integer.toHexString(green);
        if (blue < 16) {
            hexValue += "0" + Integer.toHexString(blue);
        } else hexValue += Integer.toHexString(blue);

        return hexValue;
    }

    public void hide(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void show(View view) {
        if (view == topLeft) topLeftVal.setVisibility(View.VISIBLE);
        else if (view == topRight) topRightVal.setVisibility(View.VISIBLE);
        else if (view == botLeft) botLeftVal.setVisibility(View.VISIBLE);
        else if (view == botRight) botRightVal.setVisibility(View.VISIBLE);
    }

    // ******************************** implement save HERE ********************************
    public void saveColor(View view) {
        if (view == findViewById(R.id.top_left_button)) colorToSave = topLeftVal.getText().toString();
        else if (view == findViewById(R.id.top_right_button)) colorToSave = topRightVal.getText().toString();
        else if (view == findViewById(R.id.bottom_left_button)) colorToSave = botLeftVal.getText().toString();
        else if (view == findViewById(R.id.bottom_right_button)) colorToSave = botRightVal.getText().toString();

        displayDialog();
    }

    public void displayDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("What do you want to name this color?");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                PickedColor newColor = new PickedColor(colorToSave, value);
                db.createColor(newColor);
                Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
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

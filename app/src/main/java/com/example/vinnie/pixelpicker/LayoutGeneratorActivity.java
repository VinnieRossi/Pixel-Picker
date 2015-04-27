package com.example.vinnie.pixelpicker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LayoutGeneratorActivity extends ActionBarActivity implements View.OnLongClickListener {

    private RelativeLayout layout;
    private Button topLeft, topRight, botLeft, botRight;
    private int tl,tr,bl,br;
    private int colorTL, colorTR, colorBL, colorBR;
    private String hex, colorToSave;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_generator);

        db = new DataBaseHandler(getApplicationContext());
        tl = tr = bl = br = 0;

        hex = getIntent().getExtras().getString("Hex");
        layout = (RelativeLayout) findViewById(R.id.relativelayout);
        layout.setBackgroundColor(Color.parseColor(hex));

        topLeft = (Button) findViewById(R.id.top_left_box);
        topRight = (Button) findViewById(R.id.top_right_box);
        botLeft = (Button) findViewById(R.id.bottom_left_box);
        botRight = (Button) findViewById(R.id.bottom_right_box);

        topLeft.setOnLongClickListener(this);
        topRight.setOnLongClickListener(this);
        botLeft.setOnLongClickListener(this);
        botRight.setOnLongClickListener(this);

        getScheme(hex);
    }

    @Override
    public boolean onLongClick(View view) {
        saveColor(view);
        return true;
    }

    public void getScheme(String hexValue) {

        int r = Integer.parseInt(hexValue.substring(1, 3), 16);
        int g = Integer.parseInt(hexValue.substring(3, 5), 16);
        int b = Integer.parseInt(hexValue.substring(5, 7), 16);
        float[] hsv = new float[3];

        Color.RGBToHSV(r, g, b, hsv);
        hsv[2] *= 0.8f;
        colorTL = Color.HSVToColor(hsv);
        topLeft.setBackgroundColor(colorTL);
        topLeft.setText(getRGB(colorTL));

        hsv[2] *= 0.8f;
        colorTR = Color.HSVToColor(hsv);
        topRight.setBackgroundColor(colorTR);
        topRight.setText(getRGB(colorTR));

        hsv[2] *= 0.8f;
        colorBL = Color.HSVToColor(hsv);
        botLeft.setBackgroundColor(colorBL);
        botLeft.setText(getRGB(colorBL));

        hsv[2] *= 0.8f;
        colorBR = Color.HSVToColor(hsv);
        botRight.setBackgroundColor(colorBR);
        botRight.setText(getRGB(colorBR));
    }

    public String getRGB(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // Dark background = White text and Light Background = Black text
        int c = Math.round((red * 299) + (blue * 587) + (green * 114)) / 1000;
        if (c > 125) {
            topLeft.setTextColor(Color.BLACK);
            topRight.setTextColor(Color.BLACK);
            botLeft.setTextColor(Color.BLACK);
            botRight.setTextColor(Color.BLACK);

        } else {
            topLeft.setTextColor(Color.WHITE);
            topRight.setTextColor(Color.WHITE);
            botLeft.setTextColor(Color.WHITE);
            botRight.setTextColor(Color.WHITE);
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

    public void display(View view) {
        if (view == topLeft) {
            if (tl % 2 == 0) {
                topLeft.setTextColor(colorTL);

            } else {
                topLeft.setTextColor(Color.WHITE);
            }
            tl++;
        }

        if (view == topRight) {
            if (tr % 2 == 0) {
                topRight.setTextColor(colorTR);

            } else {
                topRight.setTextColor(Color.WHITE);
            }
            tr++;
        }

        if (view == botLeft) {
            if (bl % 2 == 0) {
                botLeft.setTextColor(colorBL);

            } else {
                botLeft.setTextColor(Color.WHITE);
            }
            bl++;
        }

        if (view == botRight) {
            if (br % 2 == 0) {
                botRight.setTextColor(colorBR);

            } else {
                botRight.setTextColor(Color.WHITE);
            }
            br++;
        }
    }

    public void saveColor(View view) {
        if (view == topLeft) colorToSave = topLeft.getText().toString();
        else if (view == topRight) colorToSave = topRight.getText().toString();
        else if (view == botLeft) colorToSave = botLeft.getText().toString();
        else if (view == botRight) colorToSave = botRight.getText().toString();

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

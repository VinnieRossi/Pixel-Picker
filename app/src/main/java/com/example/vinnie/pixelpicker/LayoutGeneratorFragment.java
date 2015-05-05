package com.example.vinnie.pixelpicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LayoutGeneratorFragment extends Fragment implements View.OnLongClickListener,
                                                                 View.OnClickListener {
    // Handles fragment communication
    private Activity myActivity;
    private Communicator comm;

    // Handles background color of main layout
    private RelativeLayout layout;

    // Handles buttons
    private Button topLeft, topRight, botLeft, botRight;
    private int tl,tr,bl,br;
    private int colorTL, colorTR, colorBL, colorBR;

    // Handles colors
    private String hex, colorToSave;
    private DataBaseHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myActivity = getActivity();
        View v = inflater.inflate(R.layout.fragment_layout_generator, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get reference to PageViewActivity
        comm = (Communicator) myActivity;

        // Get reference to color bank
        db = new DataBaseHandler(myActivity);

        // Set counters to 0 (deals with when to display hex values on each button)
        tl = tr = bl = br = 0;

        // Get default color from activity (black)
        hex = myActivity.getIntent().getStringExtra("Hex");

        // Set the layout's background to the hex value
        layout = (RelativeLayout) myActivity.findViewById(R.id.relativelayout);
        layout.setBackgroundColor(Color.parseColor(hex));

        // Initialize button variables
        topLeft = (Button) myActivity.findViewById(R.id.top_left_box);
        topRight = (Button) myActivity.findViewById(R.id.top_right_box);
        botLeft = (Button) myActivity.findViewById(R.id.bottom_left_box);
        botRight = (Button) myActivity.findViewById(R.id.bottom_right_box);

        // Handles with the display of hex values
        topLeft.setOnClickListener(this);
        topRight.setOnClickListener(this);
        botLeft.setOnClickListener(this);
        botRight.setOnClickListener(this);

        // Handles with saving a color
        topLeft.setOnLongClickListener(this);
        topRight.setOnLongClickListener(this);
        botLeft.setOnLongClickListener(this);
        botRight.setOnLongClickListener(this);

        // Get the color scheme
        getScheme(hex);
    }

    public void changeColors(String hex) {
        layout.setBackgroundColor(Color.parseColor(hex));
        getScheme(hex);
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
        topLeft.setText(getHex(colorTL));

        hsv[2] *= 0.8f;
        colorTR = Color.HSVToColor(hsv);
        topRight.setBackgroundColor(colorTR);
        topRight.setText(getHex(colorTR));

        hsv[2] *= 0.8f;
        colorBL = Color.HSVToColor(hsv);
        botLeft.setBackgroundColor(colorBL);
        botLeft.setText(getHex(colorBL));

        hsv[2] *= 0.8f;
        colorBR = Color.HSVToColor(hsv);
        botRight.setBackgroundColor(colorBR);
        botRight.setText(getHex(colorBR));
    }

    public String getHex(int color) {
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

        // Get the hex value of the RGB color
        String hexValue = "#";
        if (red < 16) hexValue += "0" + Integer.toHexString(red);
        else hexValue += Integer.toHexString(red);

        if (green < 16) hexValue += "0" + Integer.toHexString(green);
        else hexValue += Integer.toHexString(green);

        if (blue < 16) hexValue += "0" + Integer.toHexString(blue);
        else hexValue += Integer.toHexString(blue);

        return hexValue;
    }

    @Override
    public void onClick(View view) {
        if (view == topLeft) {
            if (tl % 2 == 0) topLeft.setTextColor(colorTL);
            else topLeft.setTextColor(Color.WHITE);
            tl++;
        }
        if (view == topRight) {
            if (tr % 2 == 0) topRight.setTextColor(colorTR);
            else topRight.setTextColor(Color.WHITE);
            tr++;
        }
        if (view == botLeft) {
            if (bl % 2 == 0) botLeft.setTextColor(colorBL);
            else botLeft.setTextColor(Color.WHITE);
            bl++;
        }
        if (view == botRight) {
            if (br % 2 == 0) botRight.setTextColor(colorBR);
            else botRight.setTextColor(Color.WHITE);
            br++;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        saveColor(view);
        return true;
    }

    public void saveColor(View view) {
        if (view == topLeft) colorToSave = topLeft.getText().toString();
        else if (view == topRight) colorToSave = topRight.getText().toString();
        else if (view == botLeft) colorToSave = botLeft.getText().toString();
        else if (view == botRight) colorToSave = botRight.getText().toString();

        displayDialog();
    }

    public void displayDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(myActivity);

        alert.setMessage("What do you want to name this color?");

        // Set an EditText view to get user input
        final EditText input = new EditText(myActivity);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                if(value.equals("")) {
                    Toast.makeText(myActivity, "Enter a name before hitting enter.", Toast.LENGTH_SHORT).show();

                } else {
                    PickedColor newColor = new PickedColor(colorToSave, value);
                    db.createColor(newColor);
                    comm.updateBankColorList(db);
                    Toast.makeText(myActivity, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
}
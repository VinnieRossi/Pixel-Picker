package com.example.vinnie.pixelpicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class ColorDropperFragment extends Fragment implements View.OnTouchListener,
                                                              View.OnClickListener {
    // Handles fragment interaction
    private Activity myActivity;
    private Communicator comm;

    // Handles layout
    private ImageView imageView;
    private ImageView preview;
    private TextView displayHex;

    // Handles image
    private Bitmap bitmap;
    private Bitmap scaled;

    // Handles colors
    private String hexValue;
    private DataBaseHandler db;
    private PickedColor newColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myActivity = getActivity();
        View v = inflater.inflate(R.layout.fragment_color_dropper, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get reference to PageViewActivity
        comm = (Communicator) myActivity;

        // Initialize variables
        imageView = (ImageView) myActivity.findViewById(R.id.imageView);
        preview = (ImageView) myActivity.findViewById(R.id.previewBox);
        displayHex = (TextView) myActivity.findViewById(R.id.textView);
        db = new DataBaseHandler(myActivity);

        // Get the image
        Intent intent = myActivity.getIntent();
        Uri selectedImage = Uri.parse(intent.getExtras().getString("selectedImage"));

        try {
            bitmap = MediaStore.Images.Media.getBitmap(myActivity.getContentResolver(), selectedImage);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the default hex value to black
        hexValue = "#000000";

        // Determine scaled image size
        if (bitmap.getWidth() > 960 || bitmap.getHeight() > 1280) {
            int nh = (int) (bitmap.getHeight() * (960.0 / bitmap.getWidth()));
            scaled = Bitmap.createScaledBitmap(bitmap, 960, nh, true);
            imageView.setImageBitmap(scaled);

        } else {
            imageView.setImageBitmap(bitmap);
            scaled = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }

        imageView.setAdjustViewBounds(true);
        imageView.setOnTouchListener(this);

        // Initialize select/save buttons
        Button selectColor = (Button) myActivity.findViewById(R.id.selectColor);
        Button saveColor = (Button) myActivity.findViewById(R.id.saveToColorBank);
        selectColor.setOnClickListener(this);
        saveColor.setOnClickListener(this);

        // Pass default color to fragments
        myActivity.getIntent().putExtra("Hex", hexValue);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Make sure swipe event does not interfere while drawing
        v.getParent().requestDisallowInterceptTouchEvent(true);

        int eventX = (int) event.getX();
        int eventY = (int) event.getY();

        // Keep in bounds of the image
        if (eventY > 0 && eventY < scaled.getHeight() && eventX > 0 && eventX < scaled.getWidth()) {
            int pixel = scaled.getPixel(eventX, eventY);
            int red = Color.red(pixel);
            int green = Color.green(pixel);
            int blue = Color.blue(pixel);

            // Get the hex value of the pixel color
            hexValue = "#";
            if (red < 16) hexValue += "0" + Integer.toHexString(red);
            else hexValue += Integer.toHexString(red);

            if (green < 16) hexValue += "0" + Integer.toHexString(green);
            else hexValue += Integer.toHexString(green);

            if (blue < 16) hexValue += "0" + Integer.toHexString(blue);
            else hexValue += Integer.toHexString(blue);

            // Set the preview box to the calculated color
            preview.setBackgroundColor(Color.parseColor(hexValue));
            // Set the text to the hex value
            displayHex.setText(hexValue.toString());
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        // Do the following for selecting a color
        if (v == myActivity.findViewById(R.id.selectColor)) {
            comm.sendColor(hexValue);
            Toast.makeText(myActivity, "Selected", Toast.LENGTH_SHORT).show();
        }
        // Else do the following for saving colors
        else if (v == myActivity.findViewById(R.id.saveToColorBank)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(myActivity);

            alert.setMessage("What do you want to name this color?");

            // Set an EditText view to get user input
            final EditText input = new EditText(myActivity);
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    if (value.equals("")) {
                        Toast.makeText(myActivity, "Enter a name before hitting enter", Toast.LENGTH_SHORT).show();

                    } else {
                        newColor = new PickedColor(hexValue, value);
                        db.createColor(newColor);
                        comm.updateBankColorList(db);
                        Toast.makeText(myActivity, "Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled
                }
            });

            alert.show();
        }
    }
}
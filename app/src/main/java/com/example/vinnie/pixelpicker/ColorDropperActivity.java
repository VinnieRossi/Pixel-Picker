package com.example.vinnie.pixelpicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;


public class ColorDropperActivity extends ActionBarActivity implements View.OnTouchListener {
    private ImageView imageView;
    private EditText name;
    private Bitmap bitmap;
    private Bitmap scaled;
    private String hexValue;
    private ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_dropper);
        imageView = (ImageView) findViewById(R.id.imageView);
        preview = (ImageView) findViewById(R.id.previewBox);
        name = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        Uri selectedImage = Uri.parse(intent.getExtras().getString("selectedImage"));

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hexValue = "#000000";

        if (bitmap.getWidth()>960 || bitmap.getHeight() > 1280) {
            int nh = (int) (bitmap.getHeight() * (960.0 / bitmap.getWidth()));
            scaled = Bitmap.createScaledBitmap(bitmap, 960, nh, true);
            imageView.setImageBitmap(scaled);
        } else {
            imageView.setImageBitmap(bitmap);
            scaled = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        }

        imageView.setAdjustViewBounds(true);
        imageView.setOnTouchListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_color_dropper, menu);
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

    public void handleStartDoodleClick(View v) {
        Intent intent = new Intent(this, DoodleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("color", hexValue);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void handleStartLayoutClick(View v) {
        Intent intent = new Intent(this, LayoutGeneratorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Hex", hexValue);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void handleSaveColorClick(View v){

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();

        //keep in bounds of the image
       if (eventY > 0 && eventY < scaled.getHeight() && eventX > 0 && eventX < scaled.getWidth()) {
            int pixel = scaled.getPixel(eventX, eventY);
            int red = Color.red(pixel);
            int green = Color.green(pixel);
            int blue = Color.blue(pixel);
           hexValue = "#";
            if (red < 16) {
                hexValue += "0" + Integer.toHexString(red);
            } else hexValue += Integer.toHexString(red);

            if (green < 16) {
                hexValue += "0" + Integer.toHexString(green);
            } else hexValue += Integer.toHexString(green);
            if (blue < 16) {
                hexValue += "0" + Integer.toHexString(blue);
            } else hexValue += Integer.toHexString(blue);

            preview.setBackgroundColor(Color.parseColor(hexValue));
        }
        return true;
    }
}
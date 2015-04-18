package com.example.vinnie.pixelpicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ColorDropperActivity extends ActionBarActivity {
    private ImageView image;
    private TextView textBox;
    private Bitmap bitmap;
    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_dropper);
        image = (ImageView)findViewById(R.id.image);
        textBox = (TextView)findViewById(R.id.textBox);
        //hexValue = "";
        bitmap = BitmapFactory.decodeFile("/sdcard/CSC495Project1/tomClassEmail.png");
        temp = "#000000";

        image.setImageBitmap(bitmap);
        //image.setBackgroundColor(Color.BLUE);

        image.setOnTouchListener(new ImageView.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float eventX = event.getX();
                float eventY = event.getY();
                //y must be < bitmap.height()
                if (eventY < bitmap.getHeight() && eventX < bitmap.getWidth()) {
                    int pixel = bitmap.getPixel((int) eventX, (int) eventY);
                    int red = Color.red(pixel);
                    int green = Color.green(pixel);
                    int blue = Color.blue(pixel);
                    temp = "#";
                    if (red < 16) {
                        temp += "0" + Integer.toHexString(red);
                    } else temp += Integer.toHexString(red);

                    if (green < 16) {
                        temp += "0" + Integer.toHexString(green);
                    } else temp += Integer.toHexString(green);
                    if (blue < 16) {
                        temp += "0" + Integer.toHexString(blue);
                    } else temp += Integer.toHexString(blue);

                    textBox.setText(temp.toUpperCase());

                }
                return true;
            }
        });
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
    public void handleStartDoodleClick(View v){
        Intent intent = new Intent(this, DoodleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("color", temp);
        startActivity(intent);
    }

    public void handleStartLayoutClick(View v){
        Intent intent = new Intent(this, LayoutGeneratorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Hex", temp);
        startActivity(intent);
    }
}
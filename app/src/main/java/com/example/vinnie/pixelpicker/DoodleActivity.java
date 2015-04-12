package com.example.vinnie.pixelpicker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class DoodleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);

        ImageView iV=(ImageView) findViewById(R.id.imageView2);
        iV.setImageResource(R.drawable.ic_launcher);
    }

}

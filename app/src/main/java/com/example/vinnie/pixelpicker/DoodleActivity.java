package com.example.vinnie.pixelpicker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;


public class DoodleActivity extends ActionBarActivity {
    public DrawView stuff;
    public ArrayList<String> ColorNames;
    DataBaseHandler db;
    ArrayList<PickedColor> colors;
    String pickedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);
        stuff = (DrawView) findViewById(R.id.drawView);
        db = new DataBaseHandler(getApplicationContext());
        pickedColor = getIntent().getExtras().getString("color");

        PickedColor newColor = new PickedColor(pickedColor,"");
//
//        PickedColor newCol = new PickedColor("#20b2aa","");
//        PickedColor newCol2 = new PickedColor("#008080","");
//        PickedColor newCol3 = new PickedColor("#9c9ea0","");
//        PickedColor newCol4 = new PickedColor("#f2f2e7","");
//        PickedColor newCol5 = new PickedColor("#20b2aa","");
//
//        db.createColor(newCol);
//        db.createColor(newCol2);
//        db.createColor(newCol3);
//        db.createColor(newCol4);
//        db.createColor(newCol5);
        db.createColor(newColor);
         colors = db.getColors();

        ColorNames = new ArrayList<String>();

        ListView listView = (ListView) findViewById(R.id.listView);
        for(PickedColor s : colors){
            ColorNames.add(s.getHex());
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, ColorNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv1 = (TextView)view.findViewById(android.R.id.text1);

                int id = colors.get(position).getID();

                String hex = db.getColorByID(id).getHex();

                int color;
                int r = Integer.parseInt(hex.substring(1, 3), 16);
                int g = Integer.parseInt(hex.substring(3, 5), 16);
                int b = Integer.parseInt(hex.substring(5, 7), 16);
                float[] hsv = new float[3];

                Color.RGBToHSV(r, g, b, hsv);
                hsv[0] *= 299;
                hsv[1] *= 597;
                hsv[2] *= 114;
                //doesn't do fucking anything but make the text red
                color = Color.HSVToColor(hsv);

                tv1.setTextColor(color);


                view.setBackgroundColor(Color.parseColor(hex));
                return view;
            }

        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDetailClick(position, (String) parent.getItemAtPosition(position));
            }
        });
    }

    public void onDetailClick(int position, String name) {
        //ColorNames = db.getStudents();
       // int ID = ColorNames.get(position);
        int id = colors.get(position).getID();
        String hex= db.getColorByID(id).getHex();


        stuff.changeColor(Color.parseColor(hex));
      //  else color = 0

    }
    @Override
    protected void onPause() {
        super.onPause();
        DrawView drawView = (DrawView) findViewById(R.id.drawView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

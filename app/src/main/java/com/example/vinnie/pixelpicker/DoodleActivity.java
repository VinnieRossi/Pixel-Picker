package com.example.vinnie.pixelpicker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.graphics.Color;

import java.util.ArrayList;


public class DoodleActivity extends ActionBarActivity {
    public DrawView stuff;
    public ArrayList<String> ColorNames;
    DataBaseHandler db;
    ArrayList<PickedColor> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);
        stuff = (DrawView) findViewById(R.id.drawView);
       db = new DataBaseHandler(getApplicationContext());

        PickedColor newCol = new PickedColor("#20b2aa","");
        PickedColor newCol2 = new PickedColor("#008080","");
        PickedColor newCol3 = new PickedColor("#9c9ea0","");
        PickedColor newCol4 = new PickedColor("#f2f2e7","");
        PickedColor newCol5 = new PickedColor("#20b2aa","");

        db.createColor(newCol);
        db.createColor(newCol2);
        db.createColor(newCol3);
        db.createColor(newCol4);
        db.createColor(newCol5);

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

                int color = 0x00FFFFFF; // Transparent
                int id = colors.get(position).getID();

                view.setBackgroundColor(Color.parseColor(db.getColorByID(id).getHex()));
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
        int color;
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

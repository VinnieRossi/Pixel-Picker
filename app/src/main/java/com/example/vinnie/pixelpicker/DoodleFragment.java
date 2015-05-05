package com.example.vinnie.pixelpicker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class DoodleFragment extends Fragment {
    // Handles fragment communication
    private Activity myActivity;

    // Gives user an area to draw on
    private DrawView drawView;

    // Handles color list display
    private ListView listView;
    private DataBaseHandler db;
    private ArrayList<PickedColor> colors;
    private ArrayList<String> colorNames;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myActivity = getActivity();
        View v = inflater.inflate(R.layout.fragment_doodle, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize variables
        drawView = (DrawView) myActivity.findViewById(R.id.drawView);
        listView = (ListView) myActivity.findViewById(R.id.listView);

        // Get access to the database of colors
        db = new DataBaseHandler(myActivity);
        // Get a list of sorted colors
        colors = db.getSortedColors();
        // Create a new array list of strings
        colorNames = new ArrayList();
        for (PickedColor color : colors) {colorNames.add(color.getCustomName());}

        // Create the adapter
        adapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_list_item_1,android.R.id.text1, colorNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
                int id = colors.get(position).getID();
                String hex = db.getColorByID(id).getHex();

                // Dark background = White text and Light Background = Black text
                int r = Integer.parseInt(hex.substring(1, 3), 16);
                int g = Integer.parseInt(hex.substring(3, 5), 16);
                int b = Integer.parseInt(hex.substring(5, 7), 16);

                int c = Math.round((r * 299) + (b * 587) + (g * 114)) / 1000;
                if (c > 125) {
                    tv1.setTextColor(Color.BLACK);
                } else {
                    tv1.setTextColor(Color.WHITE);
                }

                view.setBackgroundColor(Color.parseColor(hex));
                return view;
            }
        };

        // Display the list of colors
        listView.setAdapter(adapter);

        // Change the drawing color to the selected color from the list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDetailClick(position, (String) parent.getItemAtPosition(position));
            }
        });
    }

    public void changeDoodleColor(String hex) {
        drawView.changeColor(Color.parseColor(hex));
    }

    public void updateList(DataBaseHandler dbh) {
        db = dbh;
        colors = db.getSortedColors();
        colorNames = new ArrayList();
        for (PickedColor color : colors) {colorNames.add(color.getCustomName());}
        adapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_list_item_1,android.R.id.text1, colorNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
                int id = colors.get(position).getID();
                String hex = db.getColorByID(id).getHex();

                // Dark background = White text and Light Background = Black text
                int r = Integer.parseInt(hex.substring(1, 3), 16);
                int g = Integer.parseInt(hex.substring(3, 5), 16);
                int b = Integer.parseInt(hex.substring(5, 7), 16);

                int c = Math.round((r * 299) + (b * 587) + (g * 114)) / 1000;
                if (c > 125) {
                    tv1.setTextColor(Color.BLACK);
                } else {
                    tv1.setTextColor(Color.WHITE);
                }

                view.setBackgroundColor(Color.parseColor(hex));
                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    public void onDetailClick(int position, String name) {
        int id = colors.get(position).getID();
        String hex = db.getColorByID(id).getHex();
        drawView.changeColor(Color.parseColor(hex));
    }
}
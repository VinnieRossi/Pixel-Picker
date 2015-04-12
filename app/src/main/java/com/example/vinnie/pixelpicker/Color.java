package com.example.vinnie.pixelpicker;

/**
 * Created by Luis on 2/17/2015.
 */
public class Color {
    int id;
    int red, green, blue;

    public Color() {
        int id = 0;
        red=0;
        green=0;
        blue=0;


    }

    public Color(int red, int green, int blue) {

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(int id) {
        this.id = id;
    }
////////////////////////////////////////////////////////////////////////////////////////
    public void setID(int id) {
        this.id = id;
    }

    public void setRed(int blue) {
        this.blue =blue;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue =blue;
    }

//    public void setStudentImage(String Uri) {
//        this.imageUri = Uri;
//    }

////////////////////////////////////////////////////////////////////////////////////////////
    public int getID() {
        return this.id;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

}

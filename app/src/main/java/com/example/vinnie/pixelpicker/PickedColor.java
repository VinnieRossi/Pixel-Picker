package com.example.vinnie.pixelpicker;

/**
 * Created by Luis on 2/17/2015.
 */
public class PickedColor {
    private int id;
    private String hex_code, customName;

    public PickedColor() {
        id = 0;
        this.hex_code = "";
        this.customName = "";
    }

    public PickedColor(int id, String hex_code, String customName) {
        this.id = id;
        this.hex_code = hex_code;
        this.customName = customName;
    }

    public PickedColor(String hex_code, String customName) {
        this.hex_code = hex_code;
        this.customName = customName;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    public int getID() {
        return this.id;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    public void setID(int id) {
        this.id = id;
    }

    public String getHex() {
        return this.hex_code;
    }

    public void setHex(String hex) {
        this.hex_code = hex;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
}

package com.example.vinnie.pixelpicker;

public interface Communicator {
    public void sendColor(String hex);
    public void updateBankColorList(DataBaseHandler dbh);
}
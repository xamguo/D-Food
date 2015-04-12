package com.example.sam.d_food.integration.service;

import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by Sam on 4/11/2015.
 */
public class Data implements Serializable {
    static Cursor c;

    public Data(Cursor c){
        this.c = c;
    }

    public void setC(Cursor c) {
        this.c = c;
    }

    static public Cursor getC() {
        return c;
    }
}

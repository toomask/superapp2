package com.mooncascade.superapp2.fun;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by toomas on 18.09.2014.
 */
public class ColorFun {

    public static int randomColor() {
        return Color.argb(
                255,                // alpha
                randInt(0, 255),    // red
                randInt(0, 255),    // green
                randInt(0, 255)     // blue
        );
    }

    static Random rand = new Random();

    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

}

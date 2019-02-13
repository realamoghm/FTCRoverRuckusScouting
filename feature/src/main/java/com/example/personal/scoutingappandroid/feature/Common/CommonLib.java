/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature.Common;

public class CommonLib {
    public static int parseToInt(String stringToParse, int defaultValue) {
        int ret;
        try {
            ret = Integer.parseInt(stringToParse);
        }
        catch(NumberFormatException ex) {
            ret = defaultValue; //use defaultValue if parsing failed
        }
        return ret;
    }
}

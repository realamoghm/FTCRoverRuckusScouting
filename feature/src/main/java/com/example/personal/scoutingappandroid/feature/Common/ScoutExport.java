/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature.Common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.personal.scoutingappandroid.feature.ScoutView.ScoutViewMasterElement;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoutExport {

    public final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;


    public String createCSVFile(ArrayList<ScoutViewMasterElement> scoutViewMasterElementArrayList, String selectorColor, Activity thisActivity) {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

        } else {
            // Permission has already been granted


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String dateString = format.format(new Date());


            String fileName = selectorColor + ScoutConstants.EXPORT_FILE_NAME + dateString + ".csv";

            File dcimDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

            File scoutdir = new File(dcimDir, ScoutConstants.EXPORT_FILE_NAME);
            scoutdir.mkdirs(); //make if not exist
            File newFile = new File(scoutdir, fileName);
            // File newFile = new File(scoutdir, "image.png");
            OutputStream os;

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer);


            try {
                os = new FileOutputStream(newFile);

                List<String[]> dataInList = new ArrayList<String[]>();
                dataInList.add(ScoutViewMasterElement.toCSVHeaderString());
                for (ScoutViewMasterElement scoutViewMasterElement : scoutViewMasterElementArrayList) {
                    dataInList.add(scoutViewMasterElement.toCSVString());
                }

                csvWriter.writeAll(dataInList);
                csvWriter.close();
                //System.out.println("weiter = " + writer);

                byte[] bytes = writer.toString().getBytes("UTF-8");
                os.write(bytes);

                os.flush();
                os.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String filePath = dcimDir.getAbsolutePath() + File.separator + ScoutConstants.EXPORT_FILE_NAME + File.separator + fileName;
            return filePath;
        }
        return ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING;
    }




}


/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.personal.scoutingappandroid.feature.ScoutView.ScoutViewMasterElement;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myScoutDB.db";
    public static final String TABLE_NAME = "scoutelement";

    private static final String SQL_CREATE_ENTRIES =
            "create Table " + TABLE_NAME + "(id integer primary key AUTOINCREMENT, " +
                "teamName VARCHAR (32)," +
                "teamNumber VARCHAR (32)," +
                "gameNumber integer," +
                "landed VARCHAR (8)," +
                "sampled VARCHAR (8)," +
                "claimedDepot VARCHAR (8)," +
                "parked VARCHAR (8)," +
                "depotScored integer," +
                "silverLanderScored integer," +
                "goldLanderScored integer," +
                "latched VARCHAR (8)," +
                "craterPartial VARCHAR (8)," +
                "craterFull VARCHAR (8)," +
                "gameDateString VARCHAR (32)," +
                "gameScore integer," +
                "comment VARCHAR (512))";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //This data base is only a cache for online data, the upgrade policy is just discard data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertScoutElement (ScoutViewMasterElement scoutViewMasterElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("teamName", scoutViewMasterElement.getTeamName());
        contentValues.put("teamNumber", scoutViewMasterElement.getTeamNumber());
        contentValues.put("gameNumber", scoutViewMasterElement.getGameNumber());
        contentValues.put("landed", scoutViewMasterElement.getLanded());
        contentValues.put("sampled", scoutViewMasterElement.getSampled());
        contentValues.put("claimedDepot", scoutViewMasterElement.getClaimedDepot());
        contentValues.put("parked", scoutViewMasterElement.getParked());
        contentValues.put("depotScored", scoutViewMasterElement.getDepotScored());
        contentValues.put("silverLanderScored", scoutViewMasterElement.getSilverLanderScored());
        contentValues.put("goldLanderScored", scoutViewMasterElement.getGoldLanderScored());
        contentValues.put("latched", scoutViewMasterElement.getLatched());
        contentValues.put("craterPartial", scoutViewMasterElement.getCraterPartial());
        contentValues.put("craterFull", scoutViewMasterElement.getCraterFull());
        contentValues.put("gameDateString", scoutViewMasterElement.getGameDateString());
        contentValues.put("gameScore", scoutViewMasterElement.getGameScore());
        contentValues.put("comment", scoutViewMasterElement.getComment());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where id=" +id+ "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateScoutElement (ScoutViewMasterElement scoutViewMasterElement, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("teamName", scoutViewMasterElement.getTeamName());
        contentValues.put("teamNumber", scoutViewMasterElement.getTeamNumber());
        contentValues.put("gameNumber", scoutViewMasterElement.getGameNumber());
        contentValues.put("landed", scoutViewMasterElement.getLanded());
        contentValues.put("sampled", scoutViewMasterElement.getSampled());
        contentValues.put("claimedDepot", scoutViewMasterElement.getClaimedDepot());
        contentValues.put("parked", scoutViewMasterElement.getParked());
        contentValues.put("depotScored", scoutViewMasterElement.getDepotScored());
        contentValues.put("silverLanderScored", scoutViewMasterElement.getSilverLanderScored());
        contentValues.put("goldLanderScored", scoutViewMasterElement.getGoldLanderScored());
        contentValues.put("latched", scoutViewMasterElement.getLatched());
        contentValues.put("craterPartial", scoutViewMasterElement.getCraterPartial());
        contentValues.put("craterFull", scoutViewMasterElement.getCraterFull());
        contentValues.put("gameDateString", scoutViewMasterElement.getGameDateString());
        contentValues.put("gameScore", scoutViewMasterElement.getGameScore());
        contentValues.put("comment", scoutViewMasterElement.getComment());
        db.update(TABLE_NAME, contentValues, "id = ?", new String[] {Integer.toString(id) } );
        return true;
    }

    public int deleteScoutElement (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<ScoutViewMasterElement> getAllScoutElements() {

        ArrayList<ScoutViewMasterElement> scoutViewMasterElementList = new ArrayList<ScoutViewMasterElement>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME + " Order By id DESC", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            ScoutViewMasterElement tempScoutViewMasterElement = new ScoutViewMasterElement();
            tempScoutViewMasterElement.setId(res.getInt(res.getColumnIndex("id")));
            tempScoutViewMasterElement.setTeamName(res.getString(res.getColumnIndex("teamName")));
            tempScoutViewMasterElement.setTeamNumber(res.getString(res.getColumnIndex("teamNumber")));
            tempScoutViewMasterElement.setGameNumber(res.getInt(res.getColumnIndex("gameNumber")));
            tempScoutViewMasterElement.setLanded(res.getString(res.getColumnIndex("landed")));
            tempScoutViewMasterElement.setSampled(res.getString(res.getColumnIndex("sampled")));
            tempScoutViewMasterElement.setClaimedDepot(res.getString(res.getColumnIndex("claimedDepot")));
            tempScoutViewMasterElement.setParked(res.getString(res.getColumnIndex("parked")));
            tempScoutViewMasterElement.setDepotScored(res.getInt(res.getColumnIndex("depotScored")));
            tempScoutViewMasterElement.setSilverLanderScored(res.getInt(res.getColumnIndex("silverLanderScored")));
            tempScoutViewMasterElement.setGoldLanderScored(res.getInt(res.getColumnIndex("goldLanderScored")));
            tempScoutViewMasterElement.setLatched(res.getString(res.getColumnIndex("latched")));
            tempScoutViewMasterElement.setCraterPartial(res.getString(res.getColumnIndex("craterPartial")));
            tempScoutViewMasterElement.setCraterFull(res.getString(res.getColumnIndex("craterFull")));
            tempScoutViewMasterElement.setGameDateString(res.getString(res.getColumnIndex("gameDateString")));
            tempScoutViewMasterElement.setGameScore(res.getInt(res.getColumnIndex("gameScore")));
            tempScoutViewMasterElement.setComment(res.getString(res.getColumnIndex("comment")));

            scoutViewMasterElementList.add(tempScoutViewMasterElement);

            res.moveToNext();
        }
        res.close();
        return scoutViewMasterElementList;

    }





}

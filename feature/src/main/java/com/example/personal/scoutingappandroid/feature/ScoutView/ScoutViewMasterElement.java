/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature.ScoutView;

import com.example.personal.scoutingappandroid.feature.Common.ScoutConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoutViewMasterElement {

    private int id;
    private String teamName;
    private String teamNumber;
    private int gameNumber;
    private String landed;  //Boolean
    private String sampled; //Boolean
    private String claimedDepot; //Boolean
    private String parked; //Boolean
    private int depotScored;
    private int silverLanderScored;
    private int goldLanderScored;
    private String latched; //Boolean
    private String craterPartial; //Boolean
    private String craterFull;//Boolean
    private String gameDateString;
    private int gameScore;
    private String comment;

    public ScoutViewMasterElement() {
        id = 0;
        teamName = ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING;
        teamNumber = ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING;
        gameNumber = 0;
        landed = ScoutConstants.SCOUT_DATA_UNCHECKED;  //Boolean
        sampled = ScoutConstants.SCOUT_DATA_UNCHECKED; //Boolean
        claimedDepot = ScoutConstants.SCOUT_DATA_UNCHECKED; //Boolean
        parked = ScoutConstants.SCOUT_DATA_UNCHECKED; //Boolean
        depotScored = 0;
        silverLanderScored = 0;
        goldLanderScored = 0;
        latched = ScoutConstants.SCOUT_DATA_UNCHECKED; //Boolean
        craterPartial = ScoutConstants.SCOUT_DATA_UNCHECKED; //Boolean
        craterFull = ScoutConstants.SCOUT_DATA_UNCHECKED;//Boolean
        gameDateString = ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING;
        gameScore = 0;
        comment = ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING;
    }

    public ScoutViewMasterElement(int id, String teamName, String teamNumber, int gameNumber, String landed, String sampled, String claimedDepot, String parked, int depotScored, int silverLanderScored, int goldLanderScored, String latched, String craterPartial, String craterFull, String gameDateString, int gameScore, String comment) {
        this.id = id;
        this.teamName = teamName;
        this.teamNumber = teamNumber;
        this.gameNumber = gameNumber;
        this.landed = landed;
        this.sampled = sampled;
        this.claimedDepot = claimedDepot;
        this.parked = parked;
        this.depotScored = depotScored;
        this.silverLanderScored = silverLanderScored;
        this.goldLanderScored = goldLanderScored;
        this.latched = latched;
        this.craterPartial = craterPartial;
        this.craterFull = craterFull;
        this.gameDateString = gameDateString;
        this.gameScore = gameScore;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public String getLanded() {
        return landed;
    }

    public void setLanded(String landed) {
        this.landed = landed;
    }

    public String getSampled() {
        return sampled;
    }

    public void setSampled(String sampled) {
        this.sampled = sampled;
    }

    public String getClaimedDepot() {
        return claimedDepot;
    }

    public void setClaimedDepot(String claimedDepot) {
        this.claimedDepot = claimedDepot;
    }

    public String getParked() {
        return parked;
    }

    public void setParked(String parked) {
        this.parked = parked;
    }

    public int getDepotScored() {
        return depotScored;
    }

    public void setDepotScored(int depotScored) {
        this.depotScored = depotScored;
    }

    public int getSilverLanderScored() {
        return silverLanderScored;
    }

    public void setSilverLanderScored(int silverLanderScored) {
        this.silverLanderScored = silverLanderScored;
    }

    public int getGoldLanderScored() {
        return goldLanderScored;
    }

    public void setGoldLanderScored(int goldLanderScored) {
        this.goldLanderScored = goldLanderScored;
    }

    public String getLatched() {
        return latched;
    }

    public void setLatched(String latched) {
        this.latched = latched;
    }

    public String getCraterPartial() {
        return craterPartial;
    }

    public void setCraterPartial(String craterPartial) {
        this.craterPartial = craterPartial;
    }

    public String getCraterFull() {
        return craterFull;
    }

    public void setCraterFull(String craterFull) {
        this.craterFull = craterFull;
    }

    public String getGameDateString() {
        return gameDateString;
    }

    public void setGameDateString(String gameDateString) {
        this.gameDateString = gameDateString;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //own setter
    public void setGameDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ScoutConstants.SCOUT_DATA_DATEFORMAT );
        String dateString = dateFormat.format(new Date());
        this.gameDateString = dateString;
    }

    public void calculateScore() {
        this.gameScore = 2*this.depotScored + 5*this.silverLanderScored + 5*this.goldLanderScored;
        if (this.landed.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            this.gameScore += 30;
        }

        if (this.sampled.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            this.gameScore += 25;
        }

        if (this.claimedDepot.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            this.gameScore += 15;
        }

        if (this.parked.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            this.gameScore += 10;
        }

        if (this.latched.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            this.gameScore += 50;
        }

        if (this.craterPartial.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            this.gameScore += 15;
        }

        if (this.craterFull.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            this.gameScore += 25;
        }
    }


    public static int calculateLiveScore(String landedArg, String sampledArg, String claimedDepotArg, String parkedArg, String latchedArg, String craterPartialArg, String craterFullArg, int depotScoredArg, int silverLanderScoredArg, int goldLanderScoredArg) {

        int calculatedGameScore = 2*depotScoredArg + 5*silverLanderScoredArg + 5*goldLanderScoredArg;
        if (landedArg.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            calculatedGameScore += 30;
        }

        if (sampledArg.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            calculatedGameScore += 25;
        }

        if (claimedDepotArg.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            calculatedGameScore += 15;
        }

        if (parkedArg.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            calculatedGameScore += 10;
        }

        if (latchedArg.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            calculatedGameScore += 50;
        }

        if (craterPartialArg.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            calculatedGameScore += 15;
        }

        if (craterFullArg.equals(ScoutConstants.SCOUT_DATA_CHECKED)) {
            calculatedGameScore += 25;
        }
        return calculatedGameScore;
    }

    public boolean isFilterContentPresent( String filterText)
    {
        //If filterText is present
        if ((this.getTeamName().toLowerCase().contains(filterText)) ||
                (this.getTeamNumber().toLowerCase().contains(filterText)) ||
                ((this.getGameNumber()+"").toLowerCase().contains(filterText)) ||
                (this.getGameDateString().toLowerCase().contains(filterText)) ||
                ((this.getGameScore()+"").toLowerCase().contains(filterText)))
        {
            return  true;
        }

        return false;
    }

    public JSONObject getjsonMap() throws JSONException {
        JSONObject map = new JSONObject();
        map.put(ScoutConstants.SCOUT_DATA_ID, id + "");
        map.put(ScoutConstants.SCOUT_DATA_TEAMNAME,  teamName);
        map.put(ScoutConstants.SCOUT_DATA_TEAMNUMBER, teamNumber);
        map.put(ScoutConstants.SCOUT_DATA_GAMENUMBER, gameNumber + "");
        map.put(ScoutConstants.SCOUT_DATA_LANDED, landed);
        map.put(ScoutConstants.SCOUT_DATA_SAMPLED, sampled);
        map.put(ScoutConstants.SCOUT_DATA_CLAIMEDDEPOT, claimedDepot);
        map.put(ScoutConstants.SCOUT_DATA_PARKED, parked);
        map.put(ScoutConstants.SCOUT_DATA_DEPOTSCORED, depotScored + "");
        map.put(ScoutConstants.SCOUT_DATA_SILVERLANDERSCORED, silverLanderScored + "");
        map.put(ScoutConstants.SCOUT_DATA_GOLDLANDERSCORED, goldLanderScored + "");
        map.put(ScoutConstants.SCOUT_DATA_LATCHED, latched);
        map.put(ScoutConstants.SCOUT_DATA_CRATERPARTIAL, craterPartial);
        map.put(ScoutConstants.SCOUT_DATA_CRATERFULL, craterFull);
        map.put(ScoutConstants.SCOUT_DATA_GAMEDATESTRING, gameDateString);
        map.put(ScoutConstants.SCOUT_DATA_GAMESCORE , gameScore);
        map.put(ScoutConstants.SCOUT_DATA_COMMENT , comment);

        System.out.println("getjsonMap map = " + map);
        return map;
    }


    public  void setWithJsonString (JSONObject jsonObject) throws JSONException {
        System.out.println("setWithJsonString jsonObject = " + jsonObject);

        this.setId(Integer.parseInt((String)jsonObject.get(ScoutConstants.SCOUT_DATA_ID)));
        this.setTeamName((String) jsonObject.get(ScoutConstants.SCOUT_DATA_TEAMNAME));
        this.setTeamNumber((String) jsonObject.get(ScoutConstants.SCOUT_DATA_TEAMNUMBER));
        this.setGameNumber(Integer.parseInt((String)jsonObject.get(ScoutConstants.SCOUT_DATA_GAMENUMBER)));
        this.setLanded((String)jsonObject.get(ScoutConstants.SCOUT_DATA_LANDED));
        this.setSampled((String)jsonObject.get(ScoutConstants.SCOUT_DATA_SAMPLED));
        this.setClaimedDepot((String) jsonObject.get(ScoutConstants.SCOUT_DATA_CLAIMEDDEPOT));
        this.setParked((String) jsonObject.get(ScoutConstants.SCOUT_DATA_PARKED));
        this.setDepotScored(Integer.parseInt((String) jsonObject.get(ScoutConstants.SCOUT_DATA_DEPOTSCORED)));
        this.setSilverLanderScored(Integer.parseInt((String) jsonObject.get(ScoutConstants.SCOUT_DATA_SILVERLANDERSCORED)));
        this.setGoldLanderScored(Integer.parseInt((String) jsonObject.get(ScoutConstants.SCOUT_DATA_GOLDLANDERSCORED)));
        this.setLatched((String) jsonObject.get(ScoutConstants.SCOUT_DATA_LATCHED));
        this.setCraterPartial((String) jsonObject.get(ScoutConstants.SCOUT_DATA_CRATERPARTIAL));
        this.setCraterFull((String) jsonObject.get(ScoutConstants.SCOUT_DATA_CRATERFULL));
        this.setGameDateString((String) jsonObject.get(ScoutConstants.SCOUT_DATA_GAMEDATESTRING));
        this.setGameScore((int) jsonObject.get(ScoutConstants.SCOUT_DATA_GAMESCORE ));
        this.setComment((String) jsonObject.get(ScoutConstants.SCOUT_DATA_COMMENT ));

        this.toString();

    }

    @Override
    public String toString() {
        return "ScoutViewMasterElement{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", teamNumber='" + teamNumber + '\'' +
                ", gameNumber=" + gameNumber +
                ", landed='" + landed + '\'' +
                ", sampled='" + sampled + '\'' +
                ", claimedDepot='" + claimedDepot + '\'' +
                ", parked='" + parked + '\'' +
                ", depotScored=" + depotScored +
                ", silverLanderScored=" + silverLanderScored +
                ", goldLanderScored=" + goldLanderScored +
                ", latched='" + latched + '\'' +
                ", craterPartial='" + craterPartial + '\'' +
                ", craterFull='" + craterFull + '\'' +
                ", gameDateString='" + gameDateString + '\'' +
                ", gameScore=" + gameScore +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static String[] toCSVHeaderString() {
        String CSVHeaderOutput[] =  {"Team Name", "Team Number", "Game Number", "Landed", "Sampled", "Claimed Depot", "Parked", "Depot Scored", "Silver Lander Scored", "Gold Lander Scored", "Latched", "Crater Partial", "Crater Full", "Game Date", "Game Score", "Comment"};
        return CSVHeaderOutput;
    }

    public String[] toCSVString () {
        String CSVOutput[] ={ teamName , teamNumber ,gameNumber + "" ,landed ,sampled ,claimedDepot ,parked , depotScored + "", silverLanderScored + "",goldLanderScored + "",latched , craterPartial ,craterFull , gameDateString , gameScore + "",comment};
        return CSVOutput;
    }
}

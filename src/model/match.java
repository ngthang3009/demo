/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import control.clubsManager;

/**
 *
 * @author ADMIN
 */
public class match {
     private int id;
     private String season;
     private String homeClubId;
     private String awayClubId;

    public match(int id, String season, String homeClubId, String awayClubId) {
        this.id = id;
        this.season = season;
        this.homeClubId = homeClubId;
        this.awayClubId = awayClubId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getHomeClubId() {
        return homeClubId;
    }

    public void setHomeClubId(String homeClubId) {
        this.homeClubId = homeClubId;
    }

    public String getAwayClubId() {
        return awayClubId;
    }

    public void setAwayClubId(String awayClubId) {
        this.awayClubId = awayClubId;
    }
     
     public String getDisplayInfo(clubsManager clubManager){
         String homeName = clubManager.getClubNameById(homeClubId);
         String awayName = clubManager.getClubNameById(awayClubId);
         if (homeName == null) homeName = "Unknown";
         if (awayName == null) awayName = "Unknown";
         return String.format("Match %d: %s vs %s (%s)", id, homeName, awayName, season);
     }

    @Override
    public String toString() {
        return String.format("Match %d: %s vs %s (%s)", id, homeClubId, awayClubId, season);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        match other = (match) obj;
        return id == other.id;
    }
    
    
    
    
     
     
     
     
     
}

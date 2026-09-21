/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Club implements Comparable<Club>{
    private String clubID;
    private String clubName;
    private String sponsor;
    private double budget;
    public Club() {
        
    }

    public Club(String clubID, String clubName, String sponsor, double budget) {
        this.clubID = clubID;
        this.clubName = clubName;
        this.sponsor = sponsor;
        this.budget = budget;
    }

    public String getClubID() {
        return clubID;
    }

    public String getClubName() {
        return clubName;
    }

    public String getSponsor() {
        return sponsor;
    }

    public double getBudget() {
        return budget;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

     @Override
    public String toString() {
        return String.format("%-8s| %-24s| %-12s| %10.2f",
                clubID, clubName, sponsor, budget);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Club a = (Club) obj;
        return clubID.equals(a.clubID);
    }

    @Override
    public int compareTo(Club o) {
        return this.clubID.compareToIgnoreCase(o.clubID);
    }
    
   

    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import model.Club;
import model.Player;
import utils.Inputer;
import utils.ValidationUtils;
import control.MatchManager;
import control.broadcastManager;
import model.match;
import control.playersManager;
/**
 *
 * @author ADMIN
 */
public class clubsManager {
    private List<Club> clubs = new ArrayList<>();
    private boolean dirty = false;
    private MatchManager matchmanager;
    private broadcastManager broacastmanager;
    private playersManager playermanager;
    
   
     
    public clubsManager(){
        
    }

public clubsManager(MatchManager matchmanager, broadcastManager broadcastManager, playersManager playerManager) {
    this.matchmanager = matchmanager;
    this.broacastmanager = broadcastManager;
    this.playermanager = playerManager;
}
public void setMatchManager(MatchManager matchmanager) {
    this.matchmanager = matchmanager;
}
public void setBroadcastManager(broadcastManager broadcastManager) {
    this.broacastmanager = broadcastManager;
}
public void setPlayerManager(playersManager playerManager) {
    this.playermanager = playerManager;
}
    
    public void listAllClubs(){
        if (clubs.isEmpty()){
            System.out.println("Danh sach rong");
            return;
        }
       System.out.printf("%-8s| %-24s| %-12s| %10s%n", "Club ID", "Club Name", "Sponsor", "Budget");
        for (Club c: clubs){
            System.out.println(c);
        }
    }
    
    public void addClub() {
    String id = Inputer.inputString("Club ID (CL-xxxx): ");
    if (!ValidationUtils.isValidClubId(id)){
        System.out.println("Club Id khong hop le");
        return;
    }
    if(isClubIdExists(id)){
        System.out.println("This clubId is exists!");
        return;
    }
    
    String name = Inputer.inputString("Enter club name: ");
    if (!ValidationUtils.isValidClubName(name)){
        System.out.println("Club name không rong va do dai duoi 50 char");
        return;
    }

     String sponsor = Inputer.inputString("Enter Sponsor: ");
        if (!ValidationUtils.isValidSponsor(sponsor)) {
            System.out.println("Sponsor cannot be empty.");
            return;
        }

   double budget = Inputer.inputDouble("Enter Budget (million EUR): ", 0.01, Double.MAX_VALUE);
        if (!ValidationUtils.isValidBudget(budget)) {
            System.out.println("Budget must be positive.");
            return;
        }
        
       Club c = new Club(id, name, sponsor, budget);
       clubs.add(c);
       dirty = true;
        System.out.println("Club added successfully.");
    }
    
    
    
    public Club findClubById (String clubId){
        if (clubId == null){
            return null;
        }
        else{
            for (int i = 0 ; i < getClubCount(); i++){
                if (clubs.get(i).getClubID().equals(clubId))
                    return clubs.get(i);
            }
        } 
        return null;
    }
    
    public void searchClubById(){
        String id = Inputer.inputString("Enter Club Id: ");
        Club c = findClubById(id);
        if (c == null){
            System.out.println("This club not found");
        }
        else{
            System.out.println(c);
        }
    }
    
    
    public void updateClub(){
       String id = Inputer.inputString("Enter clubId: ");
       Club c = findClubById(id);
       if(c == null){
           System.out.println("this Club is not found");
           return;
       }
        int choice;
        do{
            System.out.println("\n--- Update Club Menu ---");
            System.out.println("1. Update Id: ");
            System.out.println("2. Update Name");
            System.out.println("3. Update Sponsor");
            System.out.println("4. Update Budget");
            System.out.println("5. Exit");
            choice = Inputer.inputInt("Vui long lua chon 1 - 5: ", 1, 5);
            switch(choice){
                case 1:
                      String newId = Inputer.inputString("Enter new ID (CL-xxxx): ");
                      if (!ValidationUtils.isValidClubId(newId)){
                      System.out.println("Invalid format! Must be CL-xxxx. Keeping old ID.");
                      break;  // không thoát method
                       }
                     if (isClubIdExists(newId)){
                      System.out.println("New ID already exists! Keeping old ID.");
                      break;
                    }
                    c.setClubID(newId);
                   dirty = true;
                   System.out.println("Update ID successfully.");
                   break;
                case 2:
                    String newName = Inputer.inputString("Enter newClubName: ");
                    if (!ValidationUtils.isValidClubName(newName)){
                     System.out.println("Invalid Name, keeping old");
                     }
                    else{
                      c.setClubName(newName);
                      dirty = true;
                      System.out.println("Update Name succesfully");
                    }
                    break;
                case 3:        
                    String newSponsor = Inputer.inputString("New sponsor");
                    if (!ValidationUtils.isValidSponsor(newSponsor)) {
                     System.out.println("Invalid sponsor, keeping old.");
                    } else {
                    c.setSponsor(newSponsor);
                    dirty = true;
                    System.out.println("Update Sponsor succesfully");
                    }
                    break;
                case 4:
                    Double budget = Inputer.inputDouble("Enter new Budget: ", 1.00 , Double.MAX_VALUE);
                    c.setBudget(budget);
                    dirty = true;
                    System.out.println("Update Budget succesfully");
                    break;
                case 5:
                    System.out.println("Da thoat update menu");
                    break;
            }
        } while(choice != 5);
    }
    
    
    
    
 public boolean removeClub(String clubId) {
    Club c = findClubById(clubId);
    if (c == null) {
        System.out.println("Club not found!");
        return false;
    }

    if (matchmanager == null) {
        System.out.println("MatchManager not initialized!");
        return false;
    }
    if (broacastmanager == null) {
        System.out.println("BroadcastManager not initialized!");
        return false;
    }
    if (playermanager == null) {
        System.out.println("PlayerManager not initialized!");
        return false;
    }
   
    List<match> matchesToRemove = matchmanager.getMatchesByClub(clubId);
    for (match m : matchesToRemove) {
        broacastmanager.removeBroadcastsByMatchId(m.getId());
    }
    matchmanager.removeMatchesByClub(clubId);
   playermanager.releasePlayersByClub(clubId);
    clubs.remove(c);
    dirty = true;
    System.out.println("Club removed successfully. Players released, matches and broadcasts deleted.");
    return true;
}
    
    
    public void findClubByBudget(){
        List<Club> list = new ArrayList<>();
        double budget = Inputer.inputDouble("Enter Max budget", 1.00, Double.MAX_VALUE);
        for (int i = 0 ; i < getClubCount(); i++){
            if (clubs.get(i).getBudget() <= budget){
                list.add(clubs.get(i));
            }
        }
        if (list.isEmpty()){
            System.out.println("Khong co club hop le");
        }
        else{
        for (Club c : list){
            System.out.println(c);
        }
        }
    }
    
    
    public int getClubCount(){
       return clubs.size();
    }
    
     public boolean isClubIdExists(String id) {
        return findClubById(id) != null;
    }
    
    public String getClubNameById(String clubId) {
        Club c = findClubById(clubId);
        return c == null ? null : c.getClubName();
    }
    
    public List<Club> getAllClubs() { 
        return clubs;
    }
    
    public boolean isDirty(){ 
        return dirty; 
    }
    public void setDirty(boolean dirty){ 
        this.dirty = dirty; 
    }
    
    // Dùng cho load file (thay thế toàn bộ list)
    public void setClubs(List<Club> newClubs) {
        this.clubs = new ArrayList<>(newClubs);
        dirty = true;
    }
    
//    public boolean removeClub(String clubId) {
//    Club c = findClubById(clubId);
//    if (c == null) return false;
//    clubs.remove(c);
//    dirty = true;
//    return true;
//}
    public boolean addClub(Club club) {
    if (club == null) return false;
    if (clubs.contains(club)) return false;
    clubs.add(club);
    dirty = true;
    return true;
}
    
}

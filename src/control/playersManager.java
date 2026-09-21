/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.ToIntFunction;
import model.Player;
import utils.Inputer;
import utils.ValidationUtils;
/**
 *
 * @author ADMIN
 */
public class playersManager{
    private clubsManager clubManager;
    private List<Player> players;
    private boolean dirty = false;
    
    public playersManager(clubsManager clubManager) {
    this.clubManager = clubManager;
    this.players = new ArrayList<>();
}
    
  public void setDirty(boolean dirty) {
      this.dirty = dirty; 
  }
     public boolean isDirty() { 
         return dirty; 
     }
    
    public List<Player> getAllPlayers(){
        Collections.sort(players);
        return players;
    }
    
    
    public void listPlayersSortedByClubThenShirt(clubsManager clubManager){
    if(players.isEmpty()){
        System.out.println("No players.");
        return;
    }

    List<Player> copy = new ArrayList<>(players);

Collections.sort(copy, new Comparator<Player>() {
    @Override
    public int compare(Player p1, Player p2) {
        String club1 = clubManager.getClubNameById(p1.getClubId());
        String club2 = clubManager.getClubNameById(p2.getClubId());

        // Nếu club name null, coi như rỗng
        if (club1 == null) club1 = "";
        if (club2 == null) club2 = "";

        int result = club1.compareToIgnoreCase(club2);
        if (result != 0) return result;
        return Integer.compare(p1.getShirtNumber(), p2.getShirtNumber());
    }
});

    for(Player p : copy){
        System.out.println(p);
    }
}
    
    
   public void addNewPlayer() {
   String Id = Inputer.inputString("Enter ID: ");
   if(!ValidationUtils.isValidPlayerId(Id)){
       System.out.println("Id wrong format");
       return;
   }
   if (isPlayerIdExists(Id)){
       System.out.println("this player Id already exists!");
       return;
   }
   
    clubManager.listAllClubs();
    String clubId = Inputer.inputString("Club ID: ");
        if (!clubManager.isClubIdExists(clubId)) {
            System.out.println("This club does not exist!");
            return;
        }
    String name = Inputer.inputString("Player name: ");
    if (name.isEmpty()) return;
    String pos = Inputer.inputString("Position (Goalkeeper/Defender/Midfielder/Forward/Winger): ");
      if (!ValidationUtils.isValidPosition(pos)) {
            System.out.println("Invalid position! Allowed: Goalkeeper, Defender, Midfielder, Forward, Winger");
            return;
        }

      int shirt = Inputer.inputInt("Shirt number (1-99): ", 1, 99);
        if (!isShirtNumberUniqueInClub(clubId, shirt)) {
            System.out.println("This shirt number already exists in this club!");
            return;
        }
       players.add(new Player(Id, clubId, name, pos, shirt));
        dirty = true;
        System.out.println("Player added successfully.");
}
    
   
   
    public Player findPlayerById(String playerId){
            for ( int i = 0; i < players.size(); i++){
                if (players.get(i).getId().equals(playerId)){
                    return players.get(i);
                }
            }
        return null;
    }
    
    
   public void updatePlayer(){
       String newClub = null;
       String Id = Inputer.inputString("Enter Id can thay doi");
       if (Id.isEmpty()){
           System.out.println("Id rong");
           return;
       }
       Player p = findPlayerById(Id);
       if (p == null){
           System.out.println("Not found");
           return;
       }
        int choice;
       do{   
            System.out.println("1. Thay doi ID: ");           
            System.out.println("2. Thay doi club: ");
            System.out.println("3. Thay doi Name: ");
            System.out.println("4. Thay doi position: ");
            System.out.println("5. Thay doi so ao: ");
            System.out.println("6. Thoat");
            choice = Inputer.inputInt("Vui long lua chon: ", 1, 6);
            switch(choice){
                case 1:
                    String newId = Inputer.inputString("Enter newId: ");
                    if (isPlayerIdExists(newId)){
                        System.out.println("Id da ton tai");
                        break;
                    }
                    if (!ValidationUtils.isValidPlayerId(newId)){
                        System.out.println("Sai format");
                       break;
                    }
                    p.setId(newId);
                    dirty = true;
                    System.out.println("Update Id succesfully!");
                    break; 
                case 2:
                    newClub = Inputer.inputString("Enter new Club: ");
                    if (!ValidationUtils.isValidClubId(newClub)){
                        System.out.println("Sai format");
                        break;
                    }
                    if (!clubManager.isClubIdExists(newClub)){
                        System.out.println("This is not exist");
                        break;
                    }
                   p.setClubId(newClub);
                   dirty = true;
                    System.out.println("Update ClubId succesfully!");
                    break;
                case 3:
                    String newName = Inputer.inputString("Enter new Name: ");
                    if (!ValidationUtils.isValidPlayerName(newName)){
                        System.out.println("Sai format");
                        break;
                    }
                   p.setName(newName);
                   dirty = true;
                    System.out.println("Update Name succesfully!");
                    break;
                      
                case 4:
                    String newPos = Inputer.inputString("Enter new Position: ");
                    if (!ValidationUtils.isValidPosition(newPos)){
                        System.out.println("Sai format");
                       break;
                    }
                   p.setPosition(newPos);
                   dirty = true;
                    System.out.println("Update Position succesfully!");
                    break;
                case 5:
                    int newShirt = Inputer.inputInt("Enter new Shirt: ", 1, 99);
                    if (!ValidationUtils.isValidShirtNumber(newShirt)){
                        System.out.println("Sai format");
                        break;
                    }
                    if (!isShirtNumberUniqueInClub(p.getClubId(), newShirt)){
                        System.out.println("So ao trung");
                        break;
                    }
                   p.setShirtNumber(newShirt);
                   dirty = true;
                    System.out.println("Update Shirt number succesfully!");
                    break;
                case 6:
                    System.out.println("Da thoat update");
                    break;          
            }
        }while(choice != 6);
    }
    
     public void removePlayer() {
        String Id = Inputer.inputString("Enter Id can xoa: ");
        if (Id.isEmpty()){
            System.out.println("Id rong");
            return;
        }
        Player p = findPlayerById(Id);
        if (p == null){
            System.out.println("Not found");
            return;
        }
        players.remove(p);
        dirty = true;
        System.out.println("player removed");
    }
     
    
    public void searchPlayersByName(){
        String keyword = Inputer.inputString("Enter keyword: ");
        List<Player> result = new ArrayList<>();
        if (players.isEmpty()){
            System.out.println("No Player.");
            return;
        }
        for (Player p: players){
            if (p.getName().contains(keyword)){
                result.add(p);
            }
        }
        if (result.isEmpty()){
            System.out.println("No Player found");
        }
        Collections.sort(result);
       for(Player p: result){
           System.out.println(p);
       }
    }
    
    

    public void listByPosition() {
        if (players.isEmpty()){
            System.out.println("Danh sach rong");
            return;
        }
        List<Player> listPosition = new ArrayList<>();
        String pos = Inputer.inputString("Enter position: ");
        for (Player p: players){
            if (p.getPosition().equalsIgnoreCase(pos)){
                listPosition.add(p);
            }
        }
        if (listPosition.isEmpty()){
            System.out.println("Not found");
            return;
        }
        for (Player p : listPosition){
            System.out.println(p);
        }
    }

      
     
    public boolean isPlayerIdExists(String playerId) {
        return findPlayerById(playerId) != null;
    }
    
    
    public boolean isShirtNumberUniqueInClub(String clubId, int shirtNumber) {
        if (clubId == null){
           return false;
        }
        for (Player p : players) {
            if (p.getClubId().equals(clubId) && p.getShirtNumber() == shirtNumber) {
                return false;
            }
        }
        return true;
    }
    
      public void removePlayersByClub(String clubId) {
       for (int i = getPlayerCount() - 1; i >= 0; i--) {
    if (players.get(i).getClubId().equals(clubId)) {
        players.remove(i);
    }
      }
       dirty = true;
    }
        
     public int getPlayerCount() {
        return players.size();
    }
    
 public boolean removePlayer(String playerId) {
    Player p = findPlayerById(playerId);
    if (p == null) return false;
    players.remove(p);
    dirty = true;
    return true;
}

public boolean addPlayer(Player p) {
    if (p == null) return false;
    if (players.contains(p)) return false;
    players.add(p);
    dirty = true;
    return true;
}  


public void releasePlayersByClub(String clubId) {
    for (Player p : players) {
        if (p.getClubId().equals(clubId)) {
            p.setClubId("FREE");
            dirty = true;
        }
    }
}
    
}

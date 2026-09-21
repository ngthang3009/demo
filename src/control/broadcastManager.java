/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import model.Broadcast;
import model.match;
import utils.Inputer;
import utils.ValidationUtils;

public class broadcastManager {
    private List<Broadcast> broadcasts = new ArrayList<>();
    private  MatchManager matchmanager;
    private clubsManager clubmanager;
    private boolean dirty = false;

    public broadcastManager(MatchManager matchmanager, clubsManager clubmanager) {
        this.matchmanager = matchmanager;
        this.clubmanager = clubmanager;
    }
    
    public void addBroadcast(){
        String id = Inputer.inputString("Enter id (ABC-xxxx): ");
        if (!ValidationUtils.isValidBroadcastId(id)){
            System.out.println("Invalid format");
            return;
        }
        if (findBroadCastById(id) != null){
            System.out.println("ID already exists!");
            return;
        }
        int matchId = Inputer.inputInt("Match ID", 1, Integer.MAX_VALUE);
        if (!matchmanager.isMatchExists(matchId)){
            System.out.println("Match dose not exist !");
            return;
        }
        
        if (isMatchBroadcasted(matchId)){
            System.out.println("match is exist in broadcast");
            return;
        }
        String channel = Inputer.inputString("Enter channel: ");
        if (!ValidationUtils.isValidChannel(channel)){
            System.out.println("Channel is not empty");
            return;
        }
        double fee = Inputer.inputDouble("Enter fee", 0.01, Double.MAX_VALUE);
        if (!ValidationUtils.isValidFee(fee)){
            System.out.println("Fee nen lon hon 0");
            return;
        }
        broadcasts.add(new Broadcast(id, matchId, channel, fee));
        dirty = true;
        System.out.println("Broadcast added.");  
    }
    
    
    public void listAllBroadcast(){
        if (broadcasts.isEmpty()){
            System.out.println("Danh sach rong");
            return;
        }
         System.out.printf("%-8s| %-6s| %-12s| %10s%n", "ID", "Match", "Channel", "Fee");
        for (Broadcast b: broadcasts){
            System.out.println(b);
        }
    }
    
    
    
    public Broadcast findBroadCastById(String id){
        for (Broadcast b : broadcasts){
            if(b.getId().equals(id)){
                return b;
            }
        }
        return null;
    }
    
    public boolean isMatchBroadcasted(int matchId){
       for (Broadcast b: broadcasts){
           if (b.getMatchId() == matchId) return true;
       }
       return false;
    }
    
    public void revenueByClub(){
        if (broadcasts.isEmpty()){
            System.out.println("Danh sach rong");
            return;
        }
        Map<String, Double> revenue = new HashMap<>();
        for (Broadcast b : broadcasts){
            match m  = matchmanager.findById(b.getMatchId());
            if (m == null){
                continue;
            }
            String clubName = clubmanager.getClubNameById(m.getHomeClubId());
            if (clubName == null){
                clubName = "Unknown";
            }
            
           if (revenue.containsKey(clubName)){
               double current = revenue.get(clubName);
               revenue.put(clubName, current+ b.getFee());
           }
           else{
               revenue.put(clubName, b.getFee());
           }
        }
        System.out.println("\n===== REVENUE BY CLUB =====");
        for(Map.Entry<String, Double> e : revenue.entrySet()){
             System.out.printf("%-24s: %10.2f%n", e.getKey(), e.getValue());
        }
    }
    
     public void revenueBySeason(){
         if (broadcasts.isEmpty()){
             System.out.println("Danh sach rong");
             return;
         }
         Map<String, Double> revenue = new HashMap<>();
         for (Broadcast b : broadcasts){
             match m = matchmanager.findById(b.getMatchId());
             if (m == null) continue;
             String season = m.getSeason();
             revenue.put(season, revenue.getOrDefault(season, 0.0)+b.getFee());
            
         }
         System.out.println("\n===== REVENUE BY SEASON =====");
        for (Map.Entry<String, Double> e : revenue.entrySet())
            System.out.printf("%-12s: %10.2f%n", e.getKey(), e.getValue());
     }
     
     public void updateBroadcast(){
         int choice;
         String id = Inputer.inputString("Enter Broadcast ID:");
         Broadcast b = findBroadCastById(id);
         if (b == null){
             System.out.println("Khong tim thay");
             return;
         }
         do{
             System.out.println("1. Update broadcast ID: ");
             System.out.println("2. Update channel: ");
             System.out.println("3. Update Fee: ");
             System.out.println("4. Exit: ");
             choice = Inputer.inputInt("Enter choice: ", 1, 4);
             switch(choice){
                 case 1: 
                     String newId = Inputer.inputString("Enter newId: ");
                     if (!ValidationUtils.isValidBroadcastId(newId)){
                         System.out.println("Wrong ID");
                        break;
                     }
                     if (findBroadCastById(newId) != null){
                         System.out.println("Id trung");
                          break;
                     }
                     b.setId(newId);
                     dirty = true;
                     System.out.println("Update thanh cong ID");
                    break;
                 case 2:
                    String newChannel= Inputer.inputString("Enter newChannel: ");
                    if (!ValidationUtils.isValidChannel(newChannel)){
                        System.out.println("Wrong new channel");
                        break;
                    }
                    b.setChannel(newChannel);
                    dirty = true;
                     System.out.println("Update thanh cong  channel");
                      break;
                 case 3:
                     double newFee = Inputer.inputDouble("Enter newFee", 0.01, Double.MAX_VALUE);
                     if (!ValidationUtils.isValidFee(newFee)){
                         System.out.println("Wrong new Fee");
                       break;
                     }
                     b.setFee(newFee);
                     dirty = true;
                     System.out.println("Update thanh cong Fee");
                     break;
                 case 4:
                     System.out.println("Da thoat Update");
                     break;
             }
         }while (choice != 4);
     }
     
     public void removeBroadcast() {
        String id = Inputer.inputString("Broadcast ID to remove: ");
        Broadcast b = findBroadCastById(id);
        if (b == null) { System.out.println("Not found!"); return; }
        System.out.print("Are you sure to delete? (Y/N): ");
        String confirm = Inputer.inputString("").trim();
        if (confirm.equalsIgnoreCase("Y")) {
            broadcasts.remove(b);
            dirty = true;
            System.out.println("Removed.");
        } else System.out.println("Cancelled.");
    }
     
   public void removeBroadcastsByMatchId(int matchId) {
    for (int i = broadcasts.size() - 1; i >= 0; i--) {
        if (broadcasts.get(i).getMatchId() == matchId) {
            broadcasts.remove(i);
            dirty = true;
        }
    }
}
     
 
     
     

//    public boolean isMatchBroadcasted(int matchId) {
//        for (Broadcast b : broadcasts) {
//            if (b.getMatchId() == matchId){
//                return true;
//            }
//        }
//        return false;
//    }
     public boolean isBroadcastExists(String id) {
        return findBroadCastById(id) != null;
    }
     
     
    public List<Broadcast> getAllBroadcasts() {
        return new ArrayList<>(broadcasts);
    }
    public boolean isDirty() { return dirty; }
    public void setDirty(boolean d) { dirty = d; }
 
}
   
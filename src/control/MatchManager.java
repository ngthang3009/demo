/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;
import java.util.ArrayList;
import java.util.List;
import model.match;
import utils.Inputer;
import utils.ValidationUtils;
import control.clubsManager;
/**
 *
 * @author ADMIN
 */
public class MatchManager {
    private List<match> matches = new ArrayList<>(); 
    private boolean dirty = false;
    private clubsManager clubmanager;
            

    public MatchManager() {
    }

    public MatchManager(clubsManager clubmanager) {
        this.clubmanager = clubmanager;
    }
    
    
//  public void DisplayInfo(String homeId, String awayId){
//      int id = Inputer.inputInt("enter Id can hien thi", 1, Integer.MAX_VALUE);
//      if (!isMatchExists(id)){
//          System.out.println("Tran dau khong ton tai");
//          return;
//      }
//      String homeName = clubmanager.getClubNameById(homeId);
//      String awayName = clubmanager.getClubNameById(awayId);
//       if (homeName == null) homeName = "Unknown";
//      if (awayName == null) awayName = "Unknown";
//       match m = findById(id);
//      System.out.printf("Match %d: %s vs %s (%s)", id, homeName, awayName, m.getSeason());
//     }
//    
    
    public void addMatch(){
        int id = Inputer.inputInt("Match Id", 1, Integer.MAX_VALUE);
        if (isMatchExists(id)){
            System.out.println("Match ID already exists!");
            return;
        }
        String home = Inputer.inputString("Home Club Id: ");
        if (!ValidationUtils.isValidClubId(home)){
            System.out.println("Home club Id sai dinh dang");
            return;
        }
         String away = Inputer.inputString("Away Club Id: ");
          if (!ValidationUtils.isValidClubId(away)){
            System.out.println("away club Id sai dinh dang");
            return;
        }
          if (!clubmanager.isClubIdExists(home) || !clubmanager.isClubIdExists(away)){
            System.out.println("Club khong ton tai");
            return;
        }
        String season = Inputer.inputString("Season: ");
        matches.add(new match(id, season , home, away));
        dirty = true;
        System.out.println("match added");
    }
    
    public void listAllMatches(){
        if (matches.isEmpty()){
            System.out.println("Danh sach tran dau rong");
            return;
        }
           if (clubmanager == null) {
        System.out.println("ClubManager chưa được khởi tạo, không thể lấy tên CLB!");
        return;
        }
        for (match m: matches){
            System.out.println(m.getDisplayInfo(clubmanager));
        }
    }
    
    public void removeMatchById(){
        int id = Inputer.inputInt("Enter id of match", 1, Integer.MAX_VALUE);
        if (!isMatchExists(id)){
            System.out.println("Khong ton tai match");
            return;
        }
        if (findById(id) != null){
            match m = findById(id);
            matches.remove(m);
            System.out.println("Da xoa thanh cong");
            dirty = true;
        }
    }
// MatchManager.java
public List<match> getMatchesByClub(String clubId) {
    List<match> result = new ArrayList<>();
    for (match m : matches) {
        if (m.getHomeClubId().equals(clubId) || m.getAwayClubId().equals(clubId)) {
            result.add(m);
        }
    }
    return result;
}

public void removeMatchesByClub(String clubId) {
    for (int i = matches.size() - 1; i >= 0; i--) {
        match m = matches.get(i);
        if (m.getHomeClubId().equals(clubId) || m.getAwayClubId().equals(clubId)) {
            matches.remove(i);
            dirty = true;
        }
    }
}
    
    
    public match findById(int i){
        for (match m: matches){
            if (m.getId() == i){
                return m;
            }
        }
        return null;
    }
    
    public boolean isMatchExists(int i){
        return findById(i) != null;
    }
    public  List<match> getAllMatches(){
        return matches;
    } 
    

    public boolean isdirty(){
        return dirty;
    }
    public void setDirty(boolean  d){
        dirty = d;
    }  
}

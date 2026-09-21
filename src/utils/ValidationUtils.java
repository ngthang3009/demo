/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;


import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ADMIN
 */
public interface ValidationUtils {
    String ClUB_ID = "^CL-\\d{4}$";
    String PlAYER_ID = "^P\\d{4}$";
    String BROADCAST_ID_REGEX = "^ABC-\\d{4}$";
    
    static boolean isValidClubId(String clubId){
       return clubId!= null && clubId.matches(ClUB_ID);
   }
    
   public static boolean isValidClubName(String clubName) {
        return clubName != null &&  !clubName.isEmpty() && clubName.length() <= 50;
    }
   
    public static boolean isValidSponsor(String sponsor) {
        return sponsor != null && !sponsor.trim().isEmpty();
    }

    public static boolean isValidBudget(double budget) {
        return budget >= 1;
    }
     
          
      static boolean isValidPlayerId(String playerId) {
             return playerId != null && playerId.matches(PlAYER_ID);
    }

    public static boolean isValidPlayerName(String playerName) {
         return playerName != null &&  !playerName.isEmpty() && playerName.length() <= 50;
    }
    
     public static boolean isValidPosition(String position) {
        if (position != null && !position.isEmpty()){
            if (position.equalsIgnoreCase("goalkeeper") || position.equalsIgnoreCase("defender")
                                                        || position.equalsIgnoreCase("midfielder")
                                                        || position.equalsIgnoreCase("forward")
                                                        || position.equalsIgnoreCase("winger"))
                return true;
        }
        return false;
    }
          
        public static boolean isValidShirtNumber(int shirtNumber) {
          return shirtNumber >= 1 && shirtNumber <= 99;
    }
        
        public static boolean isValidBroadcastId(String id){
            return id != null && id.matches(BROADCAST_ID_REGEX);
        }
        public static  boolean isValidChannel (String channel){
            return channel != null && !channel.trim().isEmpty();
        }
        
        public static boolean isValidFee(double  fee){
            return fee > 0;
        }
        
}


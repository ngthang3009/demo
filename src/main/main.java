package main;

import control.clubsManager;
import control.playersManager;
import menu.menuu;
import utils.FileRepository;
import control.broadcastManager;
import control.MatchManager;

public class main {
    public static void main(String[] args) {
        clubsManager clubManager = new clubsManager();
        playersManager playerManager = new playersManager(clubManager);
        FileRepository fileRepository = new FileRepository(clubManager, playerManager);
        MatchManager matchmanager = new MatchManager(clubManager);
        broadcastManager broadcastManager = new broadcastManager(matchmanager, clubManager);
           clubManager.setMatchManager(matchmanager);
         clubManager.setBroadcastManager(broadcastManager);
         clubManager.setPlayerManager(playerManager);
        menuu menu = new menuu(clubManager, playerManager, fileRepository, broadcastManager, matchmanager);
        
        menu.run();
    }
}
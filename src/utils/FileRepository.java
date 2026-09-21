package utils;

import control.clubsManager;
import control.playersManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Club;
import model.Player;

public class FileRepository {
    private static final String CLUB_FILE = "clubs.txt";
    private static final String PLAYER_FILE = "players.txt";

    private clubsManager clubManager;
    private playersManager playerManager;

    public FileRepository(clubsManager clubManager, playersManager playerManager) {
        this.clubManager = clubManager;
        this.playerManager = playerManager;
    }

   public void loadClubs() {
    List<Club> clubs = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(CLUB_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            Club c = parseClub(line);
            if (c != null) clubs.add(c);
        }
    } catch (IOException e) {
        System.out.println("Cannot read clubs.txt: " + e.getMessage());
    }
    
  
//    List<Club> oldClubs = new ArrayList<>(clubManager.getAllClubs());
//    for (Club c : oldClubs) {
//        clubManager.removeClub(c.getClubID());
//    }

    clubManager.getAllClubs().clear();
    
    for (Club c : clubs) {
        clubManager.addClub(c);
    }
    System.out.println("Loaded " + clubs.size() + " clubs.");
}

public void loadPlayers() {
    List<Player> players = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(PLAYER_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            Player p = parsePlayer(line);
            if (p != null) players.add(p);
        }
    } catch (IOException e) {
        System.out.println("Cannot read players.txt: " + e.getMessage());
    }
    

    List<Player> oldPlayers = new ArrayList<>(playerManager.getAllPlayers());
    for (Player p : oldPlayers) {
        playerManager.removePlayer(p.getId());
    }
    
    for (Player p : players) {
        playerManager.addPlayer(p);
    }
    System.out.println("Loaded " + players.size() + " players.");
}

    private Club parseClub(String line) {
        String[] parts = line.split(",");
        if (parts.length < 4) return null;
        try {
            String id = parts[0].trim();
            String name = parts[1].trim();
            String sponsor = parts[2].trim();
            double budget = Double.parseDouble(parts[3].trim());
            if (!ValidationUtils.isValidClubId(id)) return null;
            if (name.isEmpty() || sponsor.isEmpty()) return null;
            if (budget <= 0) return null;
            return new Club(id, name, sponsor, budget);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Player parsePlayer(String line) {
        String[] parts = line.split(",");
        if (parts.length < 5) return null;
        try {
            String pid = parts[0].trim();
            String cid = parts[1].trim();
            String name = parts[2].trim();
            String pos = parts[3].trim();
            int shirt = Integer.parseInt(parts[4].trim());
            if (!ValidationUtils.isValidPlayerId(pid)) return null;
            if (name.isEmpty()) return null;
            if (!ValidationUtils.isValidPosition(pos)) return null;
            if (shirt < 1 || shirt > 99) return null;
            // Dùng constructor (id, clubId, name, position, shirt)
            return new Player(pid, cid, name, pos, shirt);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // ==================== SAVE ====================
    public void saveClubs() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLUB_FILE))) {
            for (Club c : clubManager.getAllClubs()) {
                bw.write(c.getClubID() + "," + c.getClubName() + "," + c.getSponsor() + "," + c.getBudget());
                bw.newLine();
            }
            System.out.println("Clubs saved.");
        } catch (IOException e) {
            System.out.println("Error saving clubs: " + e.getMessage());
        }
    }

    public void savePlayers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PLAYER_FILE))) {
            for (Player p : playerManager.getAllPlayers()) {
                // Dùng getPlayerId() và getPlayerName() đã thêm trong Player
                bw.write(p.getId() + "," + p.getClubId() + "," + p.getName() + "," + p.getPosition() + "," + p.getShirtNumber());
                bw.newLine();
            }
            System.out.println("Players saved.");
        } catch (IOException e) {
            System.out.println("Error saving players: " + e.getMessage());
        }
    }

    public void saveAll() {
        saveClubs();
        savePlayers();
        clubManager.setDirty(false);
        playerManager.setDirty(false);
    }
}
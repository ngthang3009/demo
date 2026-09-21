package model;

public class Player extends Person implements Comparable<Player>{
    private String clubId;
    private String position;
    private int shirtNumber;

    // Đã XÓA constructor mặc định gây lỗi

    public Player(String id, String clubId, String name, String position, int shirtNumber) {
        super(id, name);
        this.clubId = clubId;
        this.position = position;
        this.shirtNumber = shirtNumber;
    }

    public String getClubId() { return clubId; }
    public String getPosition() { return position; }
    public int getShirtNumber() { return shirtNumber; }
    public void setClubId(String clubId) { this.clubId = clubId; }
    public void setPosition(String position) { this.position = position; }
    public void setShirtNumber(int shirtNumber) { this.shirtNumber = shirtNumber; }

    @Override  
    public String getDisplayInfo() {
        return String.format("%s %s %d", id, name, shirtNumber);
    }

    @Override
    public String toString() {
        return String.format("%-7s| %-8s| %-20s| %-12s| %d",
                id, clubId, name, position, shirtNumber);
    }

    @Override
    public int compareTo(Player o) {
        return this.id.compareToIgnoreCase(o.id);
    }

    public String getPlayerId() { return getId(); }
    public String getPlayerName() { return getName(); }
}
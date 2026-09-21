package menu;

import control.clubsManager;
import control.playersManager;
import utils.FileRepository;
import utils.Inputer;
import model.Broadcast;
import control.broadcastManager;
import control.MatchManager;
import utils.ValidationUtils;
public class menuu {
    private clubsManager clubManager;
    private playersManager playerManager;
    private FileRepository fileRepository;
    private  broadcastManager broadcastManager;
    private MatchManager matchmanager;

    public menuu(clubsManager clubManager, playersManager playerManager, FileRepository fileRepository, broadcastManager broadcastManager, MatchManager matchmanager) {
        this.clubManager = clubManager;
        this.playerManager = playerManager;
        this.fileRepository = fileRepository;
        this.broadcastManager = broadcastManager;
        this.matchmanager = matchmanager;
    }

    

    public void run() {
        fileRepository.loadClubs();
        fileRepository.loadPlayers();

        int choice;
        do {
            printMainMenu();
            choice = Inputer.inputInt("Your choice (0 - 16): ", 0, 16);
            
            switch (choice) {
                case 1:
                    clubManager.listAllClubs();
                    break;
                case 2:
                    clubManager.addClub();
                    break;
                case 3:
                    clubManager.searchClubById();
                    break;
                case 4:
                    clubManager.updateClub();
                    break;
                case 5:
                    clubManager.findClubByBudget();
                    break;
                case 6:
                    playerManager.listPlayersSortedByClubThenShirt(clubManager);
                    break;
                case 7:
                    playerManager.searchPlayersByName();
                    break;
                case 8:
                    playerManager.addNewPlayer();
                    break;
                case 9:
                    playerManager.removePlayer();
                    break;
                case 10:
                    playerManager.updatePlayer();
                    break;
                case 11:
                    playerManager.listByPosition();
                    break;
                case 12:
                    fileRepository.saveAll();
                    break;
                case 13:
                    fileRepository.loadClubs();
                    fileRepository.loadPlayers();
                    System.out.println("Data reloaded from files.");
                    break;
                case 14:
                    broadcastMenu();
                    break;
                case 15:
                    matchMenu();
                    break;
                case 16:
                    String clubId  = Inputer.inputString("Enter ClubId can xoa: ");
                    if (!ValidationUtils.isValidClubId(clubId)){
                        System.out.println("Sai dinh sang club");
                        break;
                    }
                   if (clubManager.removeClub(clubId)){
                        System.out.println("Club removed successfully. Players released, matches and broadcasts deleted.");
                   }
                    break;
                case 0:
                    if (clubManager.isDirty() || playerManager.isDirty()) {
                        System.out.print("Data changed. Save before exit? (Y/N): ");
                        String save = Inputer.inputString("").trim();
                        if (save.equalsIgnoreCase("Y")) {
                            fileRepository.saveAll();
                        }
                    }
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private void printMainMenu() {
        System.out.println("\n===== FOOTBALL CLUB MANAGER =====");
        System.out.println("1. List all clubs");
        System.out.println("2. Add a new club");
        System.out.println("3. Search club by ID");
        System.out.println("4. Update club by ID");
        System.out.println("5. List clubs with budget ≤ input");
        System.out.println("6. List players sorted by club name, then shirt number");
        System.out.println("7. Search players by partial name");
        System.out.println("8. Add a new player");
        System.out.println("9. Remove a player by ID");
        System.out.println("10. Update a player by ID");
        System.out.println("11. List players by position");
        System.out.println("12. Save data to files");
        System.out.println("13. Load data from files");
        System.out.println("14. Broadcast menu");
        System.out.println("15. Match menu");
        System.out.println("16. Remove club");
        System.out.println("0. Exit and save (if changed)");
    }
    
    public void broadcastMenu(){
        int choice;
        do{
        System.out.println("\n--- BROADCAST MANAGER ---");
        System.out.println("1. Add broadcast");
        System.out.println("2. List all broadcasts");
        System.out.println("3. Revenue by club");
        System.out.println("4. Revenue by season");
        System.out.println("5. Update broadcast");
        System.out.println("6. Remove broadcast");
        System.out.println("0. Back");
        choice = Inputer.inputInt("Enter your choice", 0, 6);
        switch (choice){
            case 1:
               broadcastManager.addBroadcast();
               break;
            case 2:
                broadcastManager.listAllBroadcast();
                break;
            case 3:
                broadcastManager.revenueByClub();
                break;
            case 4:
                broadcastManager.revenueBySeason();
                break;
            case 5:
                broadcastManager.updateBroadcast();
                break;
            case 6:
                broadcastManager.removeBroadcast();
                break;
            case 0:
                System.out.println("Da thoat thanh cong broadcast menu");
                break;
        }
        } while(choice != 0);
    }
    
    
    
     public void matchMenu(){
        int choice;
        do{
        System.out.println("\n--- MATCH MANAGER ---");
        System.out.println("1. Add match");
        System.out.println("2. Remove match");
        System.out.println("3. List all match");
//            System.out.println("4. Display match");
        System.out.println("0. Back");
        choice = Inputer.inputInt("Enter your choice", 0, 3);
        switch (choice){
            case 1:
               matchmanager.addMatch();
               break;
            case 2:
                matchmanager.removeMatchById();
                break;
            case 3:
                matchmanager.listAllMatches();
                break;
//            case 4:
//                matchmanager.DisplayInfo(homeId, awayId);
//                break;
            case 0:
                System.out.println("Da thoat thanh cong match menu");
                break;
        }
        } while(choice != 0);
    }
}
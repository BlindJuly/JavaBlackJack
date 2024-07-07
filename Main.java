import java.util.ArrayList;
import java.util.Scanner;
//import java.util.stream.IntStream;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;


public class Main {
    private static Scanner input = new Scanner(System.in);
    static Map<String, Account> accounts = new HashMap<>();
    

    public static void main(String[] args){
        accounts.put("masterAccount", new Account("Dealer"));
        printTitle();
        Game game = new Game(addPlayers());
        //SoundPlayer.playSound("sounds/678227_cardshuffle.wav");
        String decision;
        
        while (true){
            SoundPlayer.playSound("sounds/678227_cardshuffle.wav");
            if (!game.checkForPlayers()){
                addPlayers();
            }
            gameStart(game);
            WallBuilder.printRandomWalls();
            P.pln("=========================================================================================================================");
            P.p("Do you want to play another hand?  (<Enter> - Yes, N - No): ");
            decision = input.nextLine();
            P.pln("=========================================================================================================================");
            if (decision.equals("N") || decision.equals("n")){
                SoundPlayer.playSound("sounds/leaving/leavingSound.wav");
                P.pln("GOODBYE! HOPE YOU LIVE TO COME SEE US AGAIN...");
                Pauser.pause(3000);
                break;
            }
        }

    }
    private static ArrayList<Player> addPlayers(){
        int playerAmount = 0;
        ArrayList<Player> players = new ArrayList<>();
        P.pln("");
        System.out.println("Insert how many players: ");
        playerAmount = input.nextInt();
        SoundPlayer.playSound("sounds/options/optionsSound.wav");
        input.nextLine();
        for (int i = 0; i < playerAmount; i++){

            System.out.println("Enter player name: ");
            String playerName = input.nextLine();
            SoundPlayer.playSound("sounds/options/optionsSound.wav");
            Pauser.pause(300);
            if (accounts.containsKey(playerName)) {
                players.add(new Player(accounts.get(playerName)));
            }
            else{
                Account newAccount = new Account(playerName);
                accounts.put(playerName, newAccount);
                players.add(new Player(newAccount));
            }
        }
        //players.add(new Dealer(accounts.get("masterAccount")));
        return players;
    }
    
    private static void gameStart(Game game) {
        System.out.println();
        P.p("                                     ");
        System.out.println("Welcome to Zach's");
        P.p("                                    ");
        P.pln("  JAVA BLACKJACK");
        System.out.println();
        double currentDeckLen = (Game.getDeck().getSize()/52)*100;
        if (currentDeckLen <= 25){
            Game.resetDeck();
        }
        game.getBets();
        WallBuilder.printRandomWalls();
        String deal = "Dealing...";
        WallBuilder.printSolidWalls(deal);
        game.dealOpener();
        game.getPlayerAction(false);
        game.compareHands(game.dealerTurn());
    }
    
    private static void printTitle(){
        int counter = 0;
        try (Scanner scanner = new Scanner(new File("titleScreen.txt"))){
            while (scanner.hasNextLine()){
                SoundPlayer.playSound("sounds/title/epicTitleTwo.wav");
                P.pln(scanner.nextLine());
                //Pauser.pause(150);
                counter++;
                if (counter == 9){
                    Pauser.pause(350);
                    counter = 0;
                }
            }
            SoundPlayer.playSound("sounds/title/440661_classicLaser.wav");
        }
        catch (FileNotFoundException e){
            System.err.println("Title screen file not found.");
        }
        

    }
}

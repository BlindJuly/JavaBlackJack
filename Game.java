import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;


public class Game {

    private static Deck deck;
    private Dealer dealer;
    private ArrayList<Player> players;
    private Map<Player, List<List<Card>>> hands;
    private Map<Player, ArrayList<Integer>> bets;
    //private Map<Player, ArrayList<Card>[]> splitHands;
    Scanner userInput = new Scanner(System.in);

    public Game(ArrayList<Player> players) {
        this.dealer = new Dealer(Main.accounts.get("masterAccount")); // needs master account
        this.players = players;
        deck = new Deck();
        this.hands = new HashMap<>();
        this.bets = new HashMap<>();
    }

    // public void addPlayer(Player player) {
    //     hands.put(player, null);
    // }
    // public void removePlayer(Player player){
    //     hands.
    // }
    public static Deck getDeck(){
        return deck;
    }

    public void addPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public static void resetDeck(){
        deck = new Deck();
    }
    public String handsString(Player player) {
        Card card;
        List<Card> playerHand;

        StringBuilder hand = new StringBuilder();
        List<List<Card>> playerHands = getHands(player);
        
        for (int i = 0; i < playerHands.size(); i++){
            playerHand = playerHands.get(i);
            hand.append("| ");
            for (int j = 0; j < playerHand.size(); j++) {
                card = playerHand.get(j);
                if (card.isFaceDown()) {
                    hand.append(" ■■");
                } else {
                    hand.append(playerHand.get(j)).append(" ");
                }
            }
            hand.append(" |");
        }
        return hand.toString().trim();
    }

    public String handString(Player player, int handIndex) {
        Card card;
        List<Card> playerHand;

        StringBuilder hand = new StringBuilder();
        
        playerHand = getHand(player, handIndex);
        hand.append("| ");
        for (int j = 0; j < playerHand.size(); j++) {
            card = playerHand.get(j);
            if (card.isFaceDown()) {
                hand.append(" ■■");
            } else {
                hand.append(playerHand.get(j)).append(" ");
            }
        }
        hand.append(" |");
        
        return hand.toString().trim();
    }

    public String handString(List<Card> hand){
        Card card;
        StringBuilder handString = new StringBuilder();
        
        for (int i = 0; i < hand.size(); i++) {
            card = hand.get(i);
            if (card.isFaceDown()) {
                handString.append(" ■■");
            } else {
                handString.append(hand.get(i)).append(" ");
            }
        }
        return handString.toString().trim();
    }

    public List<Card> getHand(Player player, int handIndex){ //if you have 1 hand
        return hands.get(player).get(handIndex);
    }

    public List<List<Card>> getHands(Player player){
        return hands.get(player);
    }

    public boolean checkForPlayers(){
        if (players.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean surrenderHalf(Player player){
        ArrayList<Integer> currentBet = bets.get(player);
        boolean result = true;
        ArrayList<Integer> saveCurrentBet = currentBet;
        if (currentBet.get(5) == 1){
            result = false;
        }
        else{
            keepHalf(player, saveCurrentBet);
        }
        return result;
    }

    public void keepHalf(Player player, ArrayList<Integer> currentBet){
        int currentChipAmount;
        int playerHalf = currentBet.get(5)/2 + 1;

        for (int i = 0; i < 5; i++){
            currentChipAmount = currentBet.get(i);
            if (currentChipAmount == 1){
                player.addChips(i, currentChipAmount);
                currentBet.add(i, 0);
                playerHalf -= currentChipAmount*Chip.getValByIndex(i);
            }
            else if (currentBet.get(i)%2 == 0){
                player.addChips(i, currentChipAmount/2);
                playerHalf -= (currentChipAmount/2) * Chip.getValByIndex(i);
            }
            else{
                player.addChips(i, currentChipAmount/2 + 1);
                playerHalf -= (currentChipAmount/2+1) * Chip.getValByIndex(i);
            }
            if (playerHalf == 0){
                break;
            }
        }
        if (playerHalf != 0){
            player.addChips(0, playerHalf);
        }
        currentBet.clear();

    }

    public boolean doubleBet(Player player) {
        ArrayList<Integer> currentBet = bets.get(player);
        boolean result = true;
        //ArrayList<Integer> saveCurrentBet = currentBet;
        if (2 * currentBet.get(5) > player.getBagTotal()) {
            result = false;
        } 
        else {
            int doubleTotal = currentBet.get(5) * 2;
            int total = 0;
            // the whole bet is going to be recalculated
            for (int i = 4; i >= 0; i--) {
                int newChipAmount = currentBet.get(i) * 2;
                currentBet.set(i, newChipAmount);
                total += newChipAmount * Chip.getValByIndex(i);
            }
            if (doubleTotal != total) {
                P.p("DoubleTotal: " + doubleTotal + " Total: " + total);
            }
            currentBet.set(5, doubleTotal);
            //greedyBetDouble(player, saveCurrentBet);    
        }
        return result;
    }

    // public void greedyBetDouble(Player player, ArrayList<Integer> currentBet){ //with just unadulterated chip values
        // // int currBagChips;
        // // int maxQ;
        // // int newChipBet = 0;
        // int doubleTotal = currentBet.get(5)*2;
        // int total = 0;
        // //the whole bet is going to be recalculated
        // for (int i = 4; i >= 0; i--){
        //     int newChipAmount = currentBet.get(i)*2;
        //     currentBet.set(i, newChipAmount);
        //     total += newChipAmount*Chip.getValByIndex(i);
        //     // newChipBet = 0;
        //     // currBagChips = player.getChipBag()[i];
        //     // maxQ = doubleTotal/Chip.getValByIndex(i);
        //     // while (newChipBet < currBagChips && newChipBet < maxQ){
        //     //     newChipBet++;
        //     // }
        //     // player.setChipAmount(i, currBagChips - newChipBet); //update player bag
        //     // currentBet.add(i, newChipBet);     // update player bet
        //     // doubleTotal -= newChipBet*Chip.getValByIndex(i);  
        //     // P.pln("" + newChipBet);
        //     // if (doubleTotal == 0){
        //     //     break;
        //     // }
        // }
        // if (doubleTotal != total){
        //     P.p("DoubleTotal: " + doubleTotal + " Total: ");
        // }
    // }

    public void printChipInfo(){
        try (Scanner scanner = new Scanner(new File("chipInfo.txt"))){
            while (scanner.hasNextLine()) {
                P.pln(scanner.nextLine());
                Pauser.pause(100);
            }
        }
        catch (FileNotFoundException e){
            System.err.println("Bet screen file not found.");
        }
    }

    public void printBetPrompt(){
        String enterBet = "Enter your bet:";
        WallBuilder.printSolidWalls(enterBet);
        WallBuilder.printLeftWall();
        P.p("> ");
    }

    public void getBets() {
        ArrayList<Integer> bet;
        String betInput;
        
        for (Player player : players) {
            
            player.setOOround(false);
            printChipInfo();
            P.pln("");
            WallBuilder.printLeftWall();
            P.pln("Your chips: ");
            player.printChipBag();

            
            while (true) {
                WallBuilder.printRandomWalls();
                printBetPrompt();
                userInput = new Scanner(System.in);
                betInput = userInput.nextLine();
                bet = betParser(betInput, player.getChipBag());
                int betAmount = bet.get(5);

                if (betAmount > 10 && betAmount < player.getBagTotal()/2){
                    SoundPlayer.playSound("sounds/betting/midBet.wav");
                }
                else if (betAmount < 10){
                    SoundPlayer.playSound("sounds/betting/chipDrop.wav");
                }
                else{
                    SoundPlayer.playSound("sounds/betting/allin.wav");
                }
                
                String totalBet = "Total bet: " + betAmount;
                WallBuilder.printSolidWalls(totalBet);
                WallBuilder.printRandomWalls();

                if (bet.get(5) == player.getBagTotal()){
                    String allIn = player + " is all in!";
                    WallBuilder.printSolidWalls(allIn);
                }

                else if (bet.get(5) > player.getBagTotal() || bet.get(5) == 0) {
                    P.pln("" + bet.get(5) + ", " + player.getBagTotal());
                    P.pln("You do not have enough chips! Try again.");

                    if (player.getBagTotal() == 0) {
                        P.pln("Come back when you have money bum!");
                        player.setOOround(true);
                        break;
                    }
                    continue; // betParser creates new arraylist for new bet
                }
                bets.put(player, bet);
                break;
            }
            if (!player.getOORS()) {
                String betAccept = "Bet accepted! Total: " + bet.get(5);
                WallBuilder.printSolidWalls(betAccept);
            }  
        }
        removePlayers(false);
    }

    private ArrayList<Integer> betParser(String bet, int[] playerChips) {
        userInput = new Scanner(bet);
        ArrayList<Integer> betArr = new ArrayList<>(); // last element is total
        int currNum;
        int total = 0;
        int count = 0;
        while (userInput.hasNextInt()) { //
            currNum = userInput.nextInt();
            if (currNum > playerChips[count]) {
                //P.pln("TOO MANY TEST");
                betArr = new ArrayList<>(List.of(0, 0, 0, 0, 0));
                total = 0;
                break;
            }
            betArr.add(currNum);
            total += currNum*Chip.getValByIndex(count);
            count++;
        }
        if (userInput.hasNextLine()){
            userInput.nextLine();
        }
        betArr.add(5, total);
        return betArr;
    }

    public void dealOpener() {
        deal();
        deal();
        bjCheck();
    }

    //START HERE IN THE MORNING!
    public void bjCheck(){

        boolean dealerBJ = false;
        int winTotal;
        List<Card> dealerHand = getHand(dealer, 0);

        if (isBlackJack(dealerHand)) {
            dealerHand.get(1).setFaceUp();
            P.pln(this.toString());
            String dHasBJ = "Dealer has a Black Jack!";
            WallBuilder.printSolidWalls(dHasBJ);
            dealerBJ = true;
        }

        for (Player player : players) {
            if (isBlackJack(getHand(player, 0))) {
                SoundPlayer.playSound("sounds/winning/blackjackSound.wav");
                String pHasBJ = "Player " + player + " has a Black Jack!";
                WallBuilder.printSolidWalls(pHasBJ);
                String playerHand = handString(player, 0);
                WallBuilder.printSolidWalls(playerHand);
                

                if (dealerBJ) {
                    String push = "It's a push.";
                    WallBuilder.printSolidWalls(push);
                    SoundPlayer.playSound("sounds/losing/push.wav");
                    continue;
                }

                SoundPlayer.playSound("sounds/winning/bigWin.wav");
                winTotal = addChips(player);
                String playerBJ = player + " wins " + winTotal + " dollars!";
                WallBuilder.printRandomWalls();
                WallBuilder.printSolidWalls(playerBJ);
                
                hands.remove(player);
            } 
            else if (dealerBJ) {
                int loss = removeChips(player);
                String dealerWins = "Dealer wins against " + player;
                String playerLoses = player + " loses $" + loss;
                SoundPlayer.playSound("sounds/losing/dealerBJ.wav");
                WallBuilder.printRandomWalls();
                WallBuilder.printSolidWalls(dealerWins);
                WallBuilder.printSolidWalls(playerLoses);
                
                hands.remove(player);
            }
        }
        if (hands.size() != 1 ){ //just dealer has a hand, everyone is out
            WallBuilder.printRandomWalls();
            P.pln(this.toString());
            //printRightWall(this.toString().length(), 42);
            for (int i = 0; i < 3; i++){
                SoundPlayer.playSound("sounds/dealing/240777_dealingCard.wav");
            }
        }
        
    }

    public void deal() {
        Card card;
        
        for (Player player : players) {
            card = deck.drawCard();

            if (hands.get(player) == null){
                hands.put(player, new ArrayList<>());
                hands.get(player).add(new ArrayList<>());
            }
            hands.get(player).get(0).add(card);
        }
        
        card = deck.drawCard();
        if (hands.get(dealer) == null) {    
            hands.put(dealer, new ArrayList<>());  //create hands container
            hands.get(dealer).add(new ArrayList<>()); //creates first hand in hand container
        }
        else if (getHand(dealer, 0).size() == 1){
            card.setFaceDown(true);
        }
        hands.get(dealer).get(0).add(card);  //adds card to initial hand
    }

    public int handTotal(List<Card> hand) {
        int total = 0;
        int aceCounter = 0;

        for (Card card : hand) {
            total += card.getValue();
            if (card.getRank().equals("A")){
                aceCounter++;
            }
        }
        
        while (aceCounter > 0 && total > 21) {
            total -= 10;
            aceCounter--;
        }
        
        return total;
    }

    public boolean isBust(List<Card> hand){
        if (handTotal(hand) > 21){
            return true;
        }
        return false;
    }

    public boolean isBust(int total, Card card) {
        if (total + card.getValue() > 21) {
            return true;
        }
        return false;
    }

    public boolean isWin(int total, Card card) {
        if (total + card.getValue() == 21) {
            return true;
        }
        return false;
    }

    public boolean isBlackJack(List<Card> hand) {
        if (handTotal(hand) == 21) {
            return true;
        }
        return false;
    }

    public int removeChips(Player player) {
        int[] chipBag = player.getChipBag();
        int lostChips;
        int totalLost = bets.get(player).get(5);
        for (int i = 0; i < chipBag.length - 1; i++) {
            lostChips = bets.get(player).get(i);
            chipBag[i] = chipBag[i] - lostChips;
            chipBag[5] -= (lostChips * Chip.getValByIndex(i)); 
        }
        player.updateBagTotal();
        //bets.get(player).clear();
        return totalLost;
    }

    public int addChips(Player player){ 
        int[] chipBag = player.getChipBag();
        int currentChipAmount = 0;
        
        int wonTotal = (int) Math.round(bets.get(player).get(5)*1.5);
        int totalLeft = wonTotal;

        for (int i = 4; i >= 0; i--) {
            int wonChips = 0;
            currentChipAmount = bets.get(player).get(i);
            // P.p(currentChipAmount + " ");
            if (currentChipAmount != 0){
                while (totalLeft >= Chip.getValByIndex(i)){
                    totalLeft -= Chip.getValByIndex(i);
                    wonChips++;
                }
                //P.pln("" + wonChips);
                chipBag[i] += wonChips;
                chipBag[5] += (wonChips * Chip.getValByIndex(i));
            }
        }
        player.updateBagTotal();
        return wonTotal;
    }

    public boolean containsAce(ArrayList<Card> hand){
        boolean hasAce = false;
        for (Card card : hand){
            if (card.getRank().equals("A")){
                hasAce = true;
            }
        }
        return hasAce;
    }

    public void getPlayerAction(boolean initialAction){

        for (Player player : hands.keySet()){
            if (player.getName().equals("Dealer")){
                continue;
            }
            P.pln("");
            P.pln("==========================================================================================================================");
            P.pln(PlayerAction.printActions());
            P.pln("==========================================================================================================================");
            P.pln("");
            playHand(player, getHand(player, 0));
        }
    }

    public void removePlayers(boolean hand){
        for (int i = players.size()-1; i >= 0; i--){
            if (players.get(i).getOORS()){
                if (hand){
                    hands.remove(players.get(i));
                }
                else{ //do both
                    String leftTable = players.get(i) + " has left the table.";
                    WallBuilder.printSolidWalls(leftTable);
                    
                    hands.remove(players.get(i));
                    players.remove(i);
                }
            }
        }
    }

    public int dealerTurn(){
        List<Card> dealerHand = getHand(dealer, 0);
        //int dealerTotal;
        String dHand = handString(dealerHand);
        SoundPlayer.playSound("sounds/dealing/240777_dealingCard.wav");
        WallBuilder.printRandomWalls();
        WallBuilder.printSolidWalls(dHand);

        dealerHand.get(1).setFaceUp();
        Pauser.pause(1000);
        dHand = handString(dealerHand);
        WallBuilder.printSolidWalls(dHand);
    
        while (handTotal(dealerHand) < 17){
            dealerHand.add(deck.drawCard());
            Pauser.pause(2000);
            SoundPlayer.playSound("sounds/dealing/240777_dealingCard.wav");
            dHand = handString(dealerHand);
            WallBuilder.printSolidWalls(dHand);
        }

        Pauser.pause(1000);
        return handTotal(dealerHand);
    }

    public void compareHands(int dealerScore){  //chips aren't taken out until this is called
        String playerHand;
        String playerWin;
        String playerLoss;
        String totalWin;
        String handLoss;
        String totalLoss;
        int netWinnings;

        String dealerTotal = "Dealer hand total: " + dealerScore;
        String dealerHand = "Dealer's Hand: " + handsString(dealer);
        
        WallBuilder.printSolidWalls(dealerHand);
        WallBuilder.printSolidWalls(dealerTotal);

        if (dealerScore > 21){
            String dealerBust = "Dealer busts!";
            WallBuilder.printSolidWalls(dealerBust);
            Pauser.pause(300);
        }

        for (Player player : hands.keySet()){
            netWinnings = 0;
            if (player.getName().equals("Dealer")) {
                getHand(dealer, 0).clear();
                continue;
            }
            if (dealerScore > 21){
                for (int i = 0; i < getHands(player).size(); i++){
                    int handScore = handTotal(getHand(player, i));

                    if (handScore <= 21){
                        netWinnings += addChips(player);
                        WallBuilder.printRandomWalls();
                        playerWin = player + " wins with hand " + handString(getHand(player, i));
                        WallBuilder.printSolidWalls(playerWin);
                    }
                    else{
                        netWinnings -= removeChips(player);
                        playerWin = player + " loses with hand " + handString(getHand(player, i));
                        SoundPlayer.playSound("sounds/losing/regLose.wav");
                    }
                }
            } else {
                for (List<Card> hand : getHands(player)) {
                    int playerTotal = handTotal(hand);
                    playerHand = handString(hand);
                    
                    if (playerTotal > dealerScore && playerTotal <= 21) {
                        int gain = addChips(player);
                        netWinnings += gain;

                        WallBuilder.printRandomWalls();
                        playerWin = player + " wins with hand " + playerHand;
                        WallBuilder.printSolidWalls(playerWin);

                    } else if (playerTotal == dealerScore) {
                        String push = "It's a push!";
                        WallBuilder.printRandomWalls();
                        WallBuilder.printSolidWalls(push);
                        
                    } else {
                        int loss = removeChips(player);
                        netWinnings -= loss;
                        
                        playerLoss = player + " loses with hand " + playerHand;
                        handLoss = "Amount Lost: " + loss;
                        WallBuilder.printRandomWalls();
                        WallBuilder.printSolidWalls(playerLoss);
                        WallBuilder.printSolidWalls(handLoss);
                    }
                }
            }
            if (netWinnings < 0){
                WallBuilder.printRandomWalls();
                totalLoss = player + " loses a total of $" + Math.abs(netWinnings);
                WallBuilder.printSolidWalls(totalLoss);
                SoundPlayer.playSound("sounds/losing/regLose.wav");
            }
            if (netWinnings > 0){
                if (netWinnings < 10){
                    SoundPlayer.playSound("sounds/winning/smallWin.wav");
                }
                else if (netWinnings > 10 && netWinnings < 100){
                    SoundPlayer.playSound("sounds/winning/midWin.wav");
                }
                else{
                    SoundPlayer.playSound("sounds/winning/bigWin.wav");
                }
                
                totalWin = player + " wins a total of $" + netWinnings;
                WallBuilder.printSolidWalls(totalWin);
            }

            for (List<Card> hand : getHands(player)){
                hand.clear();
            }
            bets.get(player).clear();
        }
    }

    public boolean playHand(Player player, List<Card> hand){
        
        Card newCard;
        int actionOrdinal = -1;
        PlayerAction[] actions = PlayerAction.values();
        boolean turnOver = false;
        boolean subAction = false;
        boolean initialAction = true;
        userInput = new Scanner(System.in);


        //P.pln("Split test: Hand size equals " + hand.size());
        if (hand.size() == 1){ //this means it was a split
            String splitDraw = "Dealer deals new card for the Split...";
            WallBuilder.printSolidWalls(splitDraw);
            hand.add(deck.drawCard());
            Pauser.pause(300);
            WallBuilder.printSolidWalls(handString(hand));
        }

        int currentHandTotal = handTotal(hand);

        while (!turnOver) {
            if (hand == null){
                break;
            }
            if (!subAction){
    
                PlayerAction.printActions();
                String currHandTotalStr = "Your current hand total: " + currentHandTotal;
                String playerTurn = "Player: " + player.getName();
                String enterAction = "Enter your action: ";
                WallBuilder.printSolidWalls(currHandTotalStr);
                WallBuilder.printRandomWalls();
                WallBuilder.printSolidWalls(playerTurn);
                WallBuilder.printSolidWalls(enterAction);
                WallBuilder.printLeftWall();
                P.p(">");
                actionOrdinal = userInput.nextInt();
                userInput.nextLine();
                SoundPlayer.playSound("sounds/options/optionSoundsTwo.wav");
                printAction(player, actions[actionOrdinal]);
            }

            switch (actionOrdinal) {
                // HIT
                case 0:
                    String currHand = handString(hand);
                    WallBuilder.printRandomWalls();
                    WallBuilder.printSolidWalls(currHand);
                    Pauser.pause(400);
                    newCard = deck.drawCard();
                    hand.add(newCard);
                    currHand = handString(hand);
                    SoundPlayer.playSound("sounds/dealing/240777_dealingCard.wav");
                    WallBuilder.printSolidWalls(currHand);

                    currentHandTotal += newCard.getValue();

                    if (isBust(hand)) {    
                        String newHandTotal = "New Hand Total: " + currentHandTotal + " You bust!";
                        WallBuilder.printSolidWalls(newHandTotal);
                        return true;
                    }
                    if (currentHandTotal > 21){ //if over 21 and got past bust, means there is an Ace
                        currentHandTotal -= 10;  //count it as 1.
                    }
                    subAction = false;
                    break;
                // STAND
                case 1:
                    turnOver = true;
                    String standValue = player + " stands with a hand value of " + currentHandTotal;
                    WallBuilder.printSolidWalls(standValue);
                    break;
                // DOUBLE_DOWN
                case 2:
                    if (!initialAction) {
                        P.pln("You can only do that as an initial action for each hand.");
                    } 
                    else if (doubleBet(player)) { // doubleBet doubles the bet and/or returns t/f
                        actionOrdinal = 0;
                        subAction = true;
                    }
                    break;
                // SPLIT
                case 3:
                    if (!initialAction) {
                        P.pln("You can only do that as an initial action for each hand.");
                    }
                    else if (hand.get(0).getValue() == hand.get(1).getValue() && player.getBagTotal() >= 2*bets.get(player).get(5)){ 
                        //don't need doubleBet because we parse and evaluate 
                        List<Card> leftHand = new ArrayList<>();
                        List<Card> rightHand = new ArrayList<>();
                        leftHand.add(hand.get(0));
                        rightHand.add(hand.get(1));
                        List<List<Card>> newHands = new ArrayList<>();
                        newHands.add(leftHand);
                        newHands.add(rightHand);
                        hand = null;
                        hands.put(player, newHands);
                        
                        String splitHands = "Split hands: " + handsString(player);
                        WallBuilder.printSolidWalls(splitHands);
                        playHand(player, leftHand);
                        playHand(player, rightHand);
                    }

                    else{
                        P.pln("You do not have the correct cards or amount of chips for this action.");
                    }
                    break;
                //SURRENDER
                case 4:
                    if (!initialAction) {
                        P.pln("You can only do that as an initial action for each hand.");
                    }
                    keepHalf(player, bets.get(player));
                    return true;
                //INSURANCE
                case 5:
                    // if (hands.get(dealer).get(0).getRank() == "A"){
                        
                    // }
                    break;
                case 6:

                    break;
                default:
            }
            initialAction = false;
        }
        return false;
    }

    public void printAction(Player player, PlayerAction action){
        StringBuilder event = new StringBuilder();
        WallBuilder.printRandomWalls();
        event.append("Player ").append(player).append(" chooses to ").append(action).append(".");
        WallBuilder.printRandomWalls();
        String playerAction = event.toString();
        WallBuilder.printSolidWalls(playerAction);
    }

    // public String StringCrafter(String){} //I want to create a function that auto
    // uses .append in sb

    @Override
    public String toString() {
        StringBuilder table = new StringBuilder();

        table.append("***************              ");
        for (Player player : players) {
            table.append(player.getName()).append("\t\t");
        }
        table.append("Dealer").append("                              ***************").append("\n");
        
        table.append("***************              ");
        for (Player player : hands.keySet()) {
            if (player.getName().equals("Dealer")){
                continue;
            }
            table.append(handsString(player)).append("\t\t");
        }
        table.append(handsString(dealer)).append("                          ***************").append("\n");

        table.append("***************              ");
        for (Player player : bets.keySet()) {
            table.append(bets.get(player).get(5)).append("\t\t");
        }
        table.append("                                            ***************");
        return table.toString();
    }

}




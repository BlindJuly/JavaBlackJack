public class Player {

    String name;
    int[] chipBag;
    int bagTotal;
    boolean outOfRound = false;

    public Player(Account account) {
        this.chipBag = account.getChips();
        this.name = account.getName();
        updateBagTotal();
    }

    public void setOOround(boolean OOR){
        this.outOfRound = OOR;
    }

    public boolean getOORS(){
        return outOfRound;
    }

    public String getName() {
        return name;
    }

    public int[] getChipBag() {
        return chipBag;
    }

    public void setChipsBag(int[] chipBag) {
        this.chipBag = chipBag;
    }

    public void setChipAmount(int i, int amount){
        chipBag[i] = amount;
    }

    public void addChips(int i, int amount){
        chipBag[i] += amount;
    }

    public void updateBagTotal(){
        bagTotal = chipBag[5];
    }

    public int getBagTotal(){
        return bagTotal;
    }

    public void printChipBag(){
        int maxLength = 42;
        for (Chip chip : Chip.values()) {
            Pauser.pause(100);
            String text = chipBag[chip.ordinal()] + " - " + chip;
            WallBuilder.printSolidWalls(text);
        }
        String totalText = "Chips total: " + chipBag[5]; 
        WallBuilder.printSolidWalls(totalText);
    }
    
    @Override
    public String toString(){
        return getName();
    }


    

    




}

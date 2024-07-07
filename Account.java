public class Account {

    int[] chipBank;
    String name;
    int chipTotal;

    public Account(String name){ //give new accounts 250 chips
        this.name = name;
        this.chipBank = new int[]{10, 8, 5, 2, 1, 250};  
        this.chipTotal = 250;
    }

    public int[] getChips() {
        return chipBank;
    }

    public String getName(){
        return name;
    }

    public void changeName(String newName){
        this.name = newName;
    }

    public int getChipTotal(){
        return chipTotal;
    }

    public void setChipTotal(int total){
        this.chipTotal = total;
    }



    
}

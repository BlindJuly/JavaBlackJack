public enum Chip {
    WHITE(1),
    RED(5),
    BLUE(10),
    GREEN(25),
    BLACK(100);

    private final int value;

    Chip(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static int getValByIndex(int i){
        Chip[] chips = Chip.values();
        return chips[i].getValue();
    }

    public String toString(){
        return name() + " ($" + value + ")";
    }

    public static String printChips(){
        StringBuilder chipString = new StringBuilder();
        for (Chip chip : Chip.values()){
            chipString.append(chip).append("\n");
        }
        return chipString.toString();
    }
}

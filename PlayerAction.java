public enum PlayerAction {
    HIT,
    STAND,
    DOUBLE_DOWN,
    SPLIT,
    SURRENDER,
    INSURANCE,
    EVEN_MONEY;


    public static String printActions(){
        StringBuilder actions = new StringBuilder();
        for (PlayerAction action : PlayerAction.values()){
            actions.append(String.format("%d - %-14s", action.ordinal(), action));
            // actions.append(action.ordinal()).append(" - ").append(action).append("\t");
        }
        return actions.toString();
    }
}

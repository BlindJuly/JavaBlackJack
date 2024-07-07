import java.util.Random;

public class WallBuilder{

    public static void printSolidWalls(String text){
        printLeftWall();
        P.p(text);
        printRightWall(text.length(), 42);
    }

    public static void printSolidWalls(){
        printLeftWall();
        printRightWall(0, 42);
    }

    public static void printRandomWalls(String text){
        printRandomLeft();
        P.p(text);
        printRandomRight(text.length(), 42);
    }

    public static void printRandomWalls(){
        printRandomLeft();
        printRandomRight();
    }

    public static void printLeftWall(){
        P.p("**************************** ");
    }

    public static void printRightWall(int textLen, int maxLen){
        int spaces = maxLen - textLen;
        for (int i = 0; i < spaces; i++) {
            P.p(" ");
        }
        P.pln("****************************");
    }

    public static void printRandomLeft(){
        StringBuilder leftWallStr = new StringBuilder();
        leftWallStr.append("*");
        String leftWall = "****************************";
        int leftWallLen = leftWall.length();
        Random rand = new Random();
        
        for (int i = 1; i < leftWallLen-1; i++){
            int oneOrZero = rand.nextInt(2);
            if (oneOrZero == 0){
                leftWallStr.append(" ");
            }
            else{
                leftWallStr.append("*");
            }
        }
        leftWallStr.append("*");
        P.p(leftWallStr.toString());
    }

    public static void printRandomRight(int txtLen, int maxLen) {
        int spaces = maxLen - txtLen + 1;
        for (int i = 0; i < spaces; i++) {
            P.p(" ");
        }

        StringBuilder rightWallStr = new StringBuilder();
        rightWallStr.append("*");
        String rightWall = "****************************";
        int leftWallLen = rightWall.length();
        Random rand = new Random();

        for (int i = 1; i < leftWallLen - 1; i++) {
            int oneOrZero = rand.nextInt(2);
            if (oneOrZero == 0) {
                rightWallStr.append(" ");
            } else {
                rightWallStr.append("*");
            }
        }
        rightWallStr.append("*");
        P.pln(rightWallStr.toString());

    }

    public static void printRandomRight() {
        int spaces = 43;
        for (int i = 0; i < spaces; i++) {
            P.p(" ");
        }

        StringBuilder rightWallStr = new StringBuilder();
        rightWallStr.append("*");
        String rightWall = "****************************";
        int leftWallLen = rightWall.length();
        Random rand = new Random();

        for (int i = 1; i < leftWallLen - 1; i++) {
            int oneOrZero = rand.nextInt(2);
            if (oneOrZero == 0) {
                rightWallStr.append(" ");
            } else {
                rightWallStr.append("*");
            }
        }
        rightWallStr.append("*");
        P.pln(rightWallStr.toString());

    }
}
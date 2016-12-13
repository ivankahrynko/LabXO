public class TypeCell {
    public static int NULL = 0;
    public static int X = 1;
    public static int O = -1;
    
    public static String strFormatOfState(int state) {
        if(state==1)return "X";
        if(state==-1)return "O";
        return "";
    }
    public static String vinnerFormatOfState(int state) {
        if(state==TypeCell.X)return "Win computer";
        if(state==TypeCell.O)return "Win player";
        if(state==2)return "Tandoff";
        if(state==3)return "Unknown";
        return null;
    }
}

import java.util.Random;
import java.util.Vector;

public class TicTacToeLogic {
    private int ButtonsInGame, vin;	
    private int m[]= new int [9];
    private Random rand = new Random();
    private Main main;
        
    public TicTacToeLogic(Main main) {
        this.main = main;
        initialize();
    }
    
    private void initialize() {
        newGame();
    }
    
    public void setCell(int cellIndex, int value) {
        m[cellIndex] = value;
    }
    public int tactica(){
        if(ButtonsInGame==9)return 4;	
        if(ButtonsInGame==8)			
            while(true){
                switch(getRand(3)){
                case 0:
                    if(m[0]==0)return 0;
                case 1:
                    if(m[2]==0)return 2;
                case 2:
                    if(m[6]==0)return 6;
                case 3:
                    if(m[8]==0)return 8;
                    continue;
                }
            }
        if(ButtonsInGame<8){	
            int res = emptyPole(TypeCell.X);
            if(res!=-1)return res;
                    res = emptyPole(TypeCell.O);
            if(res!=-1)return res;		
        }				
        return randPole();
    }

    public void newGame() {
        for(int i=0;i<9;i++){
            setState(i, TypeCell.NULL);
        }
        main.setContentAreaForPole();
        ButtonsInGame = 9;
        main.setStateGameText(TypeCell.vinnerFormatOfState(3));				
        int in = tactica();
        if(in!=-1)setState(in, TypeCell.X);
    }

    public void setVinner() {
        vin = lockWinner();
    }
    public int getVinner() {
        return vin;
    }
    public int lockWinner()
    {			
        if(m[0]+m[1]+m[2]==3||m[0]+m[1]+m[2]==-3){
            main.line(0,1,2);
            return m[0];
        }
        if(m[0]+m[3]+m[6]==3||m[0]+m[3]+m[6]==-3){
            main.line(0,3,6);
            return m[0];
        }

        if(m[6]+m[7]+m[8]==3||m[6]+m[7]+m[8]==-3){
            main.line(6,7,8);
            return m[8];
        }
        if(m[2]+m[5]+m[8]==3||m[2]+m[5]+m[8]==-3){
            main.line(2,5,8);
            return m[8];
        }

        if(m[0]+m[4]+m[8]==3||m[0]+m[4]+m[8]==-3){
            main.line(0,4,8);
            return m[4];
        }
        if(m[2]+m[4]+m[6]==3||m[2]+m[4]+m[6]==-3){
            main.line(2,4,6);
            return m[4];
        }

        if(m[1]+m[4]+m[7]==3||m[1]+m[4]+m[7]==-3){
            main.line(1,4,7);
            return m[4];
        }
        if(m[3]+m[4]+m[5]==3||m[3]+m[4]+m[5]==-3){
            main.line(3,4,5);
            return m[4];
        }		
        if(ButtonsInGame==0)return 2;
        return TypeCell.NULL;
    }
    private int emptyPole(int player)
    {
        int n = (player==TypeCell.O)?-2:2; 
        if(m[0]+m[1]+m[2]==n)
        {
            if(m[0]==0)
                return 0;
            if(m[1]==0)
                return 1;
            if(m[2]==0)
                return 2;			
        }
        else if(m[3]+m[4]+m[5]==n)
        {
        if(m[3]==0)
            return 3;
        if(m[4]==0)
            return 4;
        if(m[5]==0)
            return 5;			
        }
        else if(m[6]+m[7]+m[8]==n)
        {
            if(m[6]==0)
                return 6;
            if(m[7]==0)
                return 7;
            if(m[8]==0)
                return 8;			
        }
        else if(m[0]+m[3]+m[6]==n)
        {
            if(m[0]==0)
                return 0;
            if(m[3]==0)
                return 3;
            if(m[6]==0)
                return 6;			
        }
        else if(m[1]+m[4]+m[7]==n)
        {
            if(m[1]==0)
                return 1;
            if(m[4]==0)
                return 4;
            if(m[7]==0)
                return 7;			
        }
        else if(m[2]+m[5]+m[8]==n)
        {
            if(m[2]==0)
                return 2;
            if(m[5]==0)
                return 5;
            if(m[8]==0)
                return 8;			
        }
        else if(m[0]+m[4]+m[8]==n)
        {
            if(m[0]==0)
                return 0;
            if(m[4]==0)
                return 4;
            if(m[8]==0)
                return 8;			
        }
        else if(m[6]+m[4]+m[2]==n)
        {
            if(m[6]==0)
                return 6;
            if(m[4]==0)
                return 4;
            if(m[2]==0)
                return 2;			
        }
        return -1;		
    }
    public void setState(int index, int state){
        main.setState(index, state);
        ButtonsInGame--;
    }

    private int getRand(int lim){
        if(lim<1)return -1;
        return Math.abs(rand.nextInt(lim));
    }

    private int randPole(){
        Vector <Integer> v = new Vector<Integer>();
        for(int i=0;i<9;i++)
                if(m[i]==0)v.add(i);
        return v.get(getRand(v.size()));
    }
}

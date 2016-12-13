import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;
 
public class Main extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    private final int FRAME_WIDTH = 240;
    private final int FRAME_HEIGHT = 268;
    
    private JPanel jContentPane = null;
    private JPanel jPanel = null;
    private JPanel jPanel1 = null;
    private JButton New = null;
    private JButton Ex = null;	
    private JButton[] pole = null;	
    private JLabel StateGame = null;
    
    private TicTacToeLogic logic;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main main = new Main();
                main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                main.setVisible(true);
            }
        });
    }

    public Main() {
        super();
        initialize();
    }

    private void initialize() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setContentPane(getJContentPane());
        this.setTitle("TicTacToe");
        this.logic = new TicTacToeLogic(this);
    }

    public void setContentAreaForPole() {
        for(int i=0;i<9;i++){
            pole[i].setContentAreaFilled(false);
        }
    }
    
    public void setStateGameText(String text) {
        StateGame.setText(text);
    }
    
    public void setState(int index, int state){
        pole[index].setText(TypeCell.strFormatOfState(state));
        pole[index].setEnabled(!(state==1||state==-1));
    }
    
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            StateGame = new JLabel();
            StateGame.setText(TypeCell.vinnerFormatOfState(3));
            StateGame.setHorizontalAlignment(SwingConstants.CENTER);
            StateGame.setBackground(Color.black);
            StateGame.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.setBackground(new Color(0, 204, 0));
            jContentPane.add(getJPanel(), BorderLayout.NORTH);
            jContentPane.add(getJPanel1(), BorderLayout.CENTER);
            jContentPane.add(StateGame, BorderLayout.SOUTH);
        }
        return jContentPane;
    }	

    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new FlowLayout());
            jPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            jPanel.setBackground(new Color(32, 32, 32));
            jPanel.add(getNew(), null);
            jPanel.add(getEx(), null);
        }
        return jPanel;
    }

    private JButton getNew() {
        if (New == null) {
            New = new JButton("Start new game");
            New.setBackground(new Color(0, 204, 0));
            New.addActionListener(this);
        }
        return New;
    }

    private JButton getEx() {
        if (Ex == null) {
            Ex = new JButton("Exit");
            Ex.setBackground(new Color(255, 102, 102));
            Ex.addActionListener(this);
        }
        return Ex;
    }

    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel(new GridLayout(3,3,1,1));
            jPanel1.setBackground(new Color(0, 0, 0));		
            pole = new JButton[9];
            for(int i=0;i<pole.length;i++){
                pole[i] = new JButton();
                setState(i, TypeCell.NULL);
                pole[i].addActionListener(this);
                pole[i].setForeground(new Color(0,204,0));
                jPanel1.add(pole[i]);				
                pole[i].setContentAreaFilled(false);
                pole[i].setBackground(new Color(0,102,0));
            }
        }
        return jPanel1;
    }	
    
    public void line(int a, int b, int c){
        pole[a].setContentAreaFilled(true);
        pole[b].setContentAreaFilled(true);
        pole[c].setContentAreaFilled(true);
    }
		
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(Ex)) {
            System.exit(0);
        }
        if(e.getSource().equals(New)) {
            logic.newGame();
        }		
        for(int i=0 ; i<9 ; i++){
            if(e.getSource().equals(pole[i])){					
                logic.setState(i, TypeCell.O);
                setMass();				
                logic.setVinner();				
                if(logic.getVinner()!=0) {
                    StateGame.setText(TypeCell.vinnerFormatOfState(logic.getVinner()));
                    inFile(TypeCell.vinnerFormatOfState(logic.getVinner()));
                }
                else{
                    logic.setState(logic.tactica(), TypeCell.X);
                    setMass();
                    int ret = logic.lockWinner();	
                    if(ret>0) {
                        StateGame.setText(TypeCell.vinnerFormatOfState(ret));
                        inFile(TypeCell.vinnerFormatOfState(ret));
                    }
                }				
            }
        }
    }
		
    private String getStrState(int index) {
        return String.format("%1s", TypeCell.strFormatOfState(logic.getCell(index)));
    }
    
    private void inFile(String text) {
        try(FileWriter writer = new FileWriter("filename.txt", false))
        {
            StringBuilder str = new StringBuilder();
            int i = 0;
            str.append("TicTacToe game:\r\n\r\n\t");
            str.append(getStrState(i++)).append("|").append(getStrState(i++));
            str.append("|").append(getStrState(i++));
            str.append("\r\n\t-|-|-\r\n\t");
            str.append(getStrState(i++)).append("|").append(getStrState(i++));
            str.append("|").append(getStrState(i++));
            str.append("\r\n\t-|-|-\r\n\t");
            str.append(getStrState(i++)).append("|").append(getStrState(i++));
            str.append("|").append(getStrState(i++));
            str.append("\r\n\r\n").append(text);
            writer.write(str.toString());
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
    }
    public void setMass()
    {
        for(int i=0; i<9; i++)
        {
            if(pole[i].getText().equals("X"))
                logic.setCell(i, TypeCell.X);
            else if(pole[i].getText().equals("O"))
                logic.setCell(i, TypeCell.O);
            else
                logic.setCell(i, TypeCell.NULL);
        }   
    }	
}

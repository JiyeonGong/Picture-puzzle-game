import javax.swing.*;
import java.awt.*;

public class View_endGame extends View_basicLayout{
    View_endGame(){
        super();
        endPrint();
    }

    public void endPrint()
    {
        frame.setPreferredSize(new Dimension(900, 900));
        frame.pack();
        JLabel background=new JLabel(new ImageIcon("C:\\sliding_puzzle\\src\\배경\\성공!.jpg"));
        background.setBounds(0,0,1300,1000);
        background.setLayout(null);
        frame.add(background);
        frame.setLocation(500, 50);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}

import javax.swing.*;
import java.awt.*;

public class View_gameRule extends View_basicLayout{
    View_gameRule(){
        super();
        rulePrint();
    }
    public void rulePrint()
    {
        frame.setPreferredSize(new Dimension(1300, 1000));
        frame.pack();
        JLabel background=new JLabel(new ImageIcon("C:\\sliding_puzzle\\src\\배경\\rule.jpg"));
        background.setBounds(0,0,1300,1000);
        background.setLayout(null);
        frame.add(background);
        frame.setLocation(200, 0);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

}

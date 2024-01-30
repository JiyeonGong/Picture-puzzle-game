import javax.swing.*;
import java.awt.*;

public class View_puzzlePicture extends Model_checkButton{


    public void puzzlePicture(int size)
    {
        frame.setPreferredSize(new Dimension(700, 700));
        frame.pack();
        JLabel background=new JLabel(new ImageIcon("C:\\sliding_puzzle\\src\\"+size+"퍼즐\\"+size+"puzzle.jpg"));
        background.setBounds(0,0,1300,1000);
        background.setLayout(null);
        frame.add(background);
        frame.setLocation(1000, 0);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // 창을 닫아도 이 창만 닫혀
    }
}

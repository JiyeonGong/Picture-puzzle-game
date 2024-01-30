import javax.swing.*;
import java.awt.*;

public class View_basicLayout {
    public JFrame frame;
    public JPanel menu;

    View_basicLayout()
    {

        basicScreen();
    }

    public void basicScreen() {

        frame = new JFrame("Sliding Puzzle Game");
        menu = new JPanel();
        frame.setLocation(300, 0);
        frame.setPreferredSize(new Dimension(1300, 1037));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setImage(JButton btn, String text)
    {
        btn.setText(text);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
    }

}

import javax.swing.*;

public class View_basicFrame extends Model_buttonEvent {
    View_basicFrame() {
        super();
        firstButton();
    }

    public void firstButton() {
        btn_start = new JButton(new ImageIcon("C:\\sliding_puzzle\\src\\버튼\\start.png"));
        btn_rule = new JButton(new ImageIcon("C:\\sliding_puzzle\\src\\버튼\\rule.png"));
        btn_end = new JButton(new ImageIcon("C:\\sliding_puzzle\\src\\버튼\\end.png"));
        JLabel background=new JLabel(new ImageIcon("C:\\sliding_puzzle\\src\\배경\\basic.jpg"));
        background.setBounds(0,0,1300,1000);    // 버튼위치 세팅
        background.setLayout(null);                                 // label의 레이아웃을 null로 셋팅하여 자유롭게 픽셀 조정
        setImage(btn_start,"Start");
        setImage(btn_rule,"Rule");
        setImage(btn_end,"End");

        btn_start.setBounds(200, 330, 225, 80);
        btn_rule.setBounds(530, 330, 225, 80);
        btn_end.setBounds(860, 330, 225, 80);

        btn_start.addActionListener(new firstButton()); // 버튼의 이벤트를 추가한다.
        background.add(btn_start);

        btn_rule.addActionListener(new firstButton());
        background.add(btn_rule);

        btn_end.addActionListener(new firstButton());
        background.add(btn_end);


        frame.add(background);
        frame.setVisible(true);

    }

}

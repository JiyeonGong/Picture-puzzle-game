import javax.swing.*;
import java.util.Random;

public class View_gameStart extends Model_setBoard{
    View_gameStart()
    {
        super();
    }

    public void buttonSetting(int size)
    {
        int i,j,k=0,h=0,count=0;    // h는 y축을 조정하는 변수, k는 x축을 조정하는 변수
        this.size = size;   // parameter로 받은 size는 유저가 보드판을 눌렀을때의 값이거나 초기 값인 3이 들어간다.
        Random r = new  Random();
        btn_com = new JButton[size][size];

        // 사이즈에 따른 버튼사이즈 셋팅
        switch (size)
        {
            case 3: {
                button_size = 220;
                break;
            }
            case 4: {
                button_size = 180;
                break;
            }
            case 5: {
                button_size = 170;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + size);
        }

        JLabel background=new JLabel(new ImageIcon("C:\\sliding_puzzle\\src\\배경\\basic2.jpeg"));
        background.setBounds(0,0,1300,1000);

        x_index = new int[size][size];
        y_index = new int[size][size];
        btn_set = new JButton[size][size];

        menu.setBounds(0,0,1300,1000);
        menu.setLayout(null);

        // 버튼의 이미지를 무작위로 넣기 위해 보드판의 크기만큼 생성
        int[] mix_puzzle = new int[size*size];
        for(i=0; i<size; i++) {
            for(j=0; j<size; j++) {
                mix_puzzle[count] = count;
                count++;
            }
        }
        count = 0;

        for(i=0; i<size; i++) {
            for(j=0; j<size; j++)
            {
                int mix_index = mix_puzzle[r.nextInt(size*size)]; // 각 버튼에 무작위 이미지를 넣기 위한 변수

                // 버튼에 중복된 이미지가 들어가면 안되기 때문에 인덱스 값이 중복되지 않게 삽입
                while (mix_index == -1)
                    mix_index = mix_puzzle[r.nextInt(size* size)];  // 무작위로 뽑힌 정수값을 삽입
                mix_puzzle[mix_index] = -1;

                // 미리 만들어놓은 리사이즈함수로 버튼 사이즈를 조절하여 넣는다.
                btn_set[i][j] = new JButton(setImageResize("C:\\sliding_puzzle\\src\\"+size+"퍼즐\\"+mix_index+".gif",button_size+11));
                setImage(btn_set[i][j],String.valueOf(count));

                // 보드판을 셋팅할때 버튼들의 절대좌표로 쓰일 x,y 좌표
                x_index[i][j] = 30+k*(button_size);
                y_index[i][j] = 30+h;

                // 버튼들의 위치를 셋팅하고 버튼이벤트와 키이벤트를 넣는다.
                btn_set[i][j].setBounds(x_index[i][j], y_index[i][j], button_size, button_size);
                btn_set[i][j].addKeyListener(new MyKeyListener());
                btn_set[i][j].addActionListener(new gameButton());
                menu.add(btn_set[i][j]);
                k++;
                count++;
            }
            h+=button_size;
            k=0;
        }

        // 보드판의 좌표를 벗어났을 때 기본 좌표로 셋팅
        // 그렇지 않을 경우 0번 버튼에 포커스를 주어 키이벤트를 발생시킨다.
        if(zero_row>=btn_set.length || zero_col>=btn_set.length)
        {
            zero_row=0;
            zero_col=0;
        }
        else {
            btn_set[zero_row][zero_col].setFocusable(true); // 0번 버튼의 포커스 사용을 가능하게 함
            btn_set[zero_row][zero_col].requestFocus(); // 0번 버튼을 포커스로 맞춤
            menu.add(btn_set[zero_row][zero_col]);
        }
        setBoard(setNumber(size*size), btn_set);    // 보드판 셋팅

        label_ctext.setBounds(900, 780,200,100);    // 시도횟수 label 셋팅
        mok_count.setBounds(995,780,200,100);   // 몫 label 셋팅
        mod_count.setBounds(1020,780,200,100);  // 나머지 label셋팅
        labelCount();   // 몫과 나머지 label이 사용자가 움직일때마다 초기화하기 위한 함수

        menu.add(label_ctext);

        menu.add(mok_count);
        menu.add(mod_count);
        menu.add(background);
        frame.add(menu);
        funcButton();
        menu.add(background);

    }

    // 3x3-5x5버튼과 reset버튼을 만들어 셋팅하는 함수
    public void funcButton()
    {
        int i;
        func_btn = new JButton[4];

        for(i=0; i<4; i++)
        {
            if(i<3) {
                func_btn[i] = new JButton(new ImageIcon("C:\\sliding_puzzle\\src\\버튼\\"+(i+3)+".png"));
                func_btn[i].setText(String.valueOf(i+3)+"x"+String.valueOf(i+3));
            }
            else{
                func_btn[i] = new JButton(new ImageIcon("C:\\sliding_puzzle\\src\\버튼\\reset.png"));
                func_btn[i].setText("Reset");
            }

            func_btn[i].setBorderPainted(false);
            func_btn[i].setFocusPainted(false);
            func_btn[i].setContentAreaFilled(false);

            func_btn[i].setBounds(950, 30 + (i * 150), 240, 80);
            func_btn[i].addActionListener(new funcButton());
            menu.add(func_btn[i]);
        }
        frame.add(menu);
    }

    // 몫과 나머지 라벨을 유저가 움직인 횟수를 몫과 나머지로 나누어 각각 다시 이미지를 셋팅한다.
    public void labelCount()
    {
        mok = user_count / 10;
        mod = user_count % 10;
        mok_count.setIcon(new ImageIcon("C:\\sliding_puzzle\\src\\카운트\\" + mok + ".png"));
        mod_count.setIcon(new ImageIcon("C:\\sliding_puzzle\\src\\카운트\\" + mod + ".png"));
    }


}

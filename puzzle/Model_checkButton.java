import javax.swing.*;
import java.util.Stack;

public class Model_checkButton extends Model_buttonEvent {
    public int zero_col;    // 0번 버튼의 열 인덱스
    public int zero_row;    // 0번 버튼의 행 인덱스
    public int user_col;    // 유저가 누른 버튼의 열 인덱스
    public int user_row;    // 유저가 누른 버튼의 행 인덱스
    public int[][] x_index; // 버튼들의 x좌표
    public int[][] y_index; // 버튼들의 y좌표
    public int user_count;  // 유저가 움직인 횟수
    public int state;   // 현재 상태 1은 게임 중 0은 초기 메뉴
    public int size;    // 퍼즐판의 사이즈
    public int mok, mod;    // 움직인 횟수의 몫과 나머지
    public int button_size;    // 버튼 크기

    public boolean move_flag = true;    // 움직임을 체크하는 flag 변수
    public boolean check_reset = false; // reset으로 움직이는지 체크하는 flag 변수

    public Stack<Integer> find_zero;    // 0번 버튼의 행*10+열 값이 저장되는 스택 변수
    public Stack<Integer> find_user;    // 유저가 누른 버튼의 행*10+열 값이 저장되는 스택 변수
    public Stack<Integer> user_input;   // 유저가 누른 버튼의 text값이 저장되는 스택 변수

    public JButton[] func_btn;  // 기능 버튼들 ex)3x3,4x4..

    public JLabel label_ctext; // 시도횟수 라벨
    public JLabel mok_count;    // 몫 라벨
    public JLabel mod_count;    // 나머지 라벨

    public moveButtonThread move;

    // 생성자로 기초 변수들 초기화
    Model_checkButton(){
        find_zero = new Stack<>();
        find_user = new Stack<>();
        user_input = new Stack<>();

        label_ctext= new JLabel(new ImageIcon("C:\\sliding_puzzle\\src\\카운트\\try.png"));
        mok_count = new JLabel();
        mod_count = new JLabel();
        mok_count = new JLabel(new ImageIcon("C:\\sliding_puzzle\\src\\카운트\\" + mok + ".png"));
        mod_count = new JLabel(new ImageIcon("C:\\sliding_puzzle\\src\\카운트\\" + mod + ".png"));
    }

    // 0번 버튼의 인덱스를 찾는 함수
    public void findZeroIndex() {
        for (int i = 0; i < btn_set.length; i++) {
            for (int j = 0; j < btn_set.length; j++) {
                if (btn_set[i][j].getText().equals("0")) {
                    if (!check_reset)
                        find_zero.push((i * 10 + j));
                    else if (find_zero.empty())
                        return;
                    else {
                        zero_row = find_zero.peek() / 10;
                        zero_col = find_zero.peek() % 10;
                        find_zero.pop();
                        return;
                    }
                    break;
                }
            }
        }
        zero_row = find_zero.peek() / 10;
        zero_col = find_zero.peek() % 10;
    }

    // 0번 버튼의 인덱스를 찾는 것과 동일한 함수 다만 유저가 누른 버튼의 인덱스를 찾는다.
    public void findUserIndex(String user) {
        for (int i = 0; i < btn_set.length; i++) {
            for (int j = 0; j < btn_set.length; j++) {
                if (btn_set[i][j].getText().equals(user)) {
                    if (!check_reset)
                        find_user.push((i * 10 + j));
                    else if (find_user.empty())
                        return;
                    else {
                        user_row = find_user.peek() / 10;
                        user_col = find_user.peek() % 10;
                        find_user.pop();
                        return;
                    }
                    break;
                }
            }
        }
        user_row = find_user.peek() / 10;
        user_col = find_user.peek() % 10;
    }

    // 대각선, 2칸씩 이동을 체크하여 잘못된 이동이면 false값을 return하고 제대로된 값이라면 true를 return
    public boolean checkIndex() {
        if ((zero_col - user_col == 1 || zero_col - user_col == -1) && (zero_row - user_row == 1 || zero_row - user_row == -1))
            return false;
        else if ((zero_col - user_col < -1 || zero_col - user_col > 1))
            return false;
        else if ((zero_row - user_row < -1 || zero_row - user_row > 1))
            return false;
        else
            return true;
    }

    // 버튼들이 천천히 움직이는 쓰레드 객체의 시작 함수
    public void startThread() {
        move = new moveButtonThread();
        move.start();
    }

    // 버튼들의 위치를 변경한다. (텍스트 값은 변경 안됨) ex) 1번 버튼과 2번 버튼이 교환하면 자리는 바뀌지만 텍스트 값은 그대로
    public void swapButton()
    {
        JButton tmp;
        tmp = btn_set[user_row][user_col];
        btn_set[user_row][user_col] = btn_set[zero_row][zero_col];
        btn_set[zero_row][zero_col] = tmp;
    }

    // 버튼들의 텍스트 값을 변경한다.
    public void swapValue() {
        int swap_index;
        swap_index = zero_row;
        zero_row = user_row;
        user_row = swap_index;

        swap_index = zero_col;
        zero_col = user_col;
        user_col = swap_index;
    }

    // 천천히 움직이는 쓰레드를 구현하는 함수
    public class moveButtonThread extends Thread{
        public void run() {
            int x = x_index[zero_row][zero_col],y=y_index[zero_row][zero_col]; // 0번 버튼의 x축,y축 절대좌표
            int x2 = x_index[user_row][user_col], y2 = y_index[user_row][user_col]; // 유저가 입력한 버튼의 x축,y축 절대좌표
            if (move_flag) {
                move_flag = false;
                while (!((btn_set[zero_row][zero_col].getX() == x_index[user_row][user_col]
                        && btn_set[zero_row][zero_col].getY() == y_index[user_row][user_col]))) {

                    x += (btn_set[0][0].getWidth()/10) * (-zero_col + user_col);
                    y += (btn_set[0][0].getHeight()/10) * (-zero_row + user_row);

                    x2 += (btn_set[0][0].getWidth()/10) * -(-zero_col + user_col);
                    y2 += (btn_set[0][0].getHeight()/10) * -(-zero_row + user_row);
                    btn_set[zero_row][zero_col].setLocation(x, y);
                    btn_set[user_row][user_col].setLocation(x2, y2);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e1) {
                        System.out.println("error");
                        e1.printStackTrace();
                    }
                }
                move_flag = true;
                swapButton();
            }
            else {
                System.out.println("움직이는 중");
            }
        }
    }

    // 보드판을 강제로 완성시키는 함수
    public void completeBoard()
    {
        int count=0;
        game_start.btn_set[game_start.zero_row][game_start.zero_col].setEnabled(true);
        for (int i = 0; i < btn_set.length; i++) {
            for (int j = 0; j < btn_set.length; j++) {
                btn_set[i][j].setIcon(game_start.setImageResize("C:\\sliding_puzzle\\src\\"+size+"퍼즐\\"+count+".gif",button_size+11));
                setImage(btn_set[i][j],String.valueOf(count));
                count++;
            }
        }
    }

    // 보드판이 완성되었는지 확인하는 함수
    public boolean checkGame() {
        int count = 0;
        for (int i = 0; i < btn_set.length; i++) {
            for (int j = 0; j < btn_set.length; j++) {
                if (btn_set[i][j].getText().equals(String.valueOf(count++))) {
                    continue;
                }
                else
                    return false;
            }
        }
        return true;
    }
}



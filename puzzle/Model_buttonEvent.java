import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


// 버튼 이벤트를 처리하는 클래스
public class Model_buttonEvent extends View_basicLayout {

    public JButton btn_start; // 게임시작 버튼
    public JButton btn_rule; // 게임규칙 버튼
    public JButton btn_end; // 게임종료 버튼
    public JButton[][] btn_set; // 퍼즐판 버튼
    public JButton[][] btn_com;
    public static View_gameStart game_start; // 게임시작 객체
    public static View_puzzlePicture puzzle_picture;
    Model_buttonEvent()
    {
        super();
    } // 상위 클래스를 상속할 생성자


    // 첫 번째 버튼 이벤트처리
    /*
    1.
    게임 시작 버튼을 누르면 현재의 창이 닫히고 새로운 게임 시작 클래스 객체를 생성한다.
    게임 시작 객체의 상위 클래스인 캐릭터 창을 선택하는 함수를 불러온다.
    진행상황을 알려주는 state변수의 값을 1 증진한다.
     */
    /*
    4.
    게임 종료를 누르면 현재 프레임이 종료된다.
     */
    class firstButton implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {

            if(e.getActionCommand().equals("Start"))
            {
                frame.dispose();
                game_start = new View_gameStart();
                game_start.state++;
                game_start.buttonSetting(3);
                audioPlayer("C:\\sliding_puzzle\\src\\소리\\first.wav");
            }
            else if(e.getActionCommand().equals("Rule"))
            {
                View_gameRule rule = new View_gameRule();
            }
            else
            {
                frame.dispose();
            }
        }
    }

    // 사용자가 버튼을 누르면 발생하는 이벤트로 해당 버튼을 누르면 0번 버튼과 교환된다.
    class gameButton implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            // 퍼즐판이 순서대로 다 맞춰졌다면 종료.
            if(game_start.checkGame()) {
                View_endGame end = new View_endGame();
                return;
            }
            String user = e.getActionCommand();
            audioPlayer("C:\\sliding_puzzle\\src\\소리\\move.wav");
            moveEvent(user);

        }
    }

    // 버튼을 스왑하는 함수
    public void moveEvent(String user)
    {
        // 이동 중에 사용자가 또 버튼을 누를경우 해당 함수를 실행하지 못하게하는 flag변수
        if(!game_start.move_flag) {
            return;
        }

        game_start.check_reset = false; // 리셋버튼을 누른 이동인지 일반적인 이동인지를 판별하기 위한 flag변수
        game_start.user_input.push(Integer.valueOf(user)); // 사용자가 누른 버튼의 텍스트 값을 스택에 쌓는다.
        game_start.findUserIndex(String.valueOf(game_start.user_input.peek())); // 사용자의 누른 버튼의 인덱스 값을 얻는다.
        game_start.findZeroIndex(); // 0번 버튼의 인덱스 값을 얻는다.

        // 사용자가 누른 버튼의 위치와 0번 버튼의 위치가 스왑이 가능하다면 조건문에 들어가게된다.
        if(game_start.checkIndex())
        {
            game_start.startThread(); // 사용자 입력 버튼과 0번 버튼의 스왑 움직임을 제어하는 쓰레드
            game_start.user_count++; // 시도횟수 +1
            game_start.swapValue(); // 인덱스 값 스왑
//            game_start.label_count.setText("시도횟수 : "+String.valueOf(game_start.user_count)); // 시도횟수 : x 라벨
            game_start.labelCount();
        }
        // 사용자가 누른 버튼과 0번 버튼의 위치가 스왑이 불가능하다면 각각 들어간 값들을 전부 제거
        else {
//            System.out.println("불가");
            game_start.user_input.pop();
            game_start.find_user.pop();
            game_start.find_zero.pop();
        }
    }





    // 게임 시작 후 기능 버튼들의 이벤트
    // 각 보드판셋팅은 JPanel을 전부 지우고 parameter들을 초기화한다.
    // 그 후 보드판을 다시 셋팅
    class funcButton implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equals("3x3"))
            {
                game_start.menu.removeAll();
                resetParameter();
                audioPlayer("C:\\sliding_puzzle\\src\\소리\\board.wav");
                game_start.buttonSetting(3);
            }
            else if(e.getActionCommand().equals("4x4"))
            {
                game_start.menu.removeAll();
                resetParameter();
                audioPlayer("C:\\sliding_puzzle\\src\\소리\\board.wav");
                game_start.buttonSetting(4);
            }
            else if(e.getActionCommand().equals("5x5"))
            {
                game_start.menu.removeAll();
                resetParameter();
                audioPlayer("C:\\sliding_puzzle\\src\\소리\\board.wav");
                game_start.buttonSetting(5);
            }
            else if(e.getActionCommand().equals("Reset"))
            {

                if(!game_start.move_flag ) {
                    return;
                }

                // 스택에 zero index가 쌓여 reset할 때마다 스택이pop되는데 이 때 더이상 값이 안들어가있을 때까지 확인
                // 조건에 들어가게되면 reset flag인 값을 참으로 변경
                // 쓰레드를 실행시키고 값을 스왑하고 zero index와 user index를 찾는다.
                // count를 1 반감시키고 실행횟수 label을 1감소시켜 보인다.
                if (!game_start.find_zero.empty()) {
                    game_start.check_reset = true;
                    game_start.startThread();
                    game_start.swapValue();
                    game_start.findZeroIndex();
                    game_start.findUserIndex(String.valueOf(game_start.user_input.peek()));
                    game_start.user_count--;
                    game_start.labelCount();
                }
                else
                    return;
                game_start.btn_set[game_start.zero_row][game_start.zero_col].requestFocus();
            }
        }
    }

    // 사용자 키 입력을 받는다.
    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode(); // 사용자가 입력한 키 값을 받는다.
            // 보드판이 전부 맞추어졌는지 확인, 맞추어졌다면 게임이 끝났다는 창을 생성시키고 종료
            if(game_start.checkGame()) {
                View_endGame end = new View_endGame();
                return;
            }
            switch (keycode) {
                case (KeyEvent.VK_LEFT): {
                    // 현재 0번 버튼의 인덱스가 왼쪽으로 갈 수 있는지 확인 ex) 3X3보드판에서 열이 0인 상태에서는 보드판을 벗어나기 때문에 리턴
                    if (game_start.zero_col - 1  == -1)
                        return;
                    audioPlayer("C:\\sliding_puzzle\\src\\소리\\move.wav");
                    moveEvent(btn_set[game_start.zero_row][game_start.zero_col - 1].getText()); // 이동
                    break;
                }
                case (KeyEvent.VK_RIGHT): {
                    // 현재 0번 버튼의 인덱스가 오른쪽으로 갈 수 있는지 확인 ex) 3X3보드판에서 열이 2인 상태에서는 보드판을 벗어나기 때문에 리턴
                    if (game_start.zero_col +1 == btn_set.length)
                        return;
                    audioPlayer("C:\\sliding_puzzle\\src\\소리\\move.wav");
                    moveEvent(btn_set[game_start.zero_row][game_start.zero_col + 1].getText()); // 이동

                    break;
                }
                case (KeyEvent.VK_UP): {
                    // 현재 0번 버튼의 인덱스가 위쪽으로 갈 수 있는지 확인 ex) 3X3보드판에서 행이0인 상태에서는 보드판을 벗어나기 때문에 리턴
                    if (game_start.zero_row - 1 == -1)
                        return;
                    audioPlayer("C:\\sliding_puzzle\\src\\소리\\move.wav");
                    moveEvent(btn_set[game_start.zero_row - 1][game_start.zero_col].getText()); // 이동
                    break;
                }
                case (KeyEvent.VK_DOWN): {
                    // 현재 0번 버튼의 인덱스가 아래쪽으로 갈 수 있는지 확인 ex) 3X3보드판에서 행이2인 상태에서는 보드판을 벗어나기 때문에 리턴
                    if (game_start.zero_row +1 == btn_set.length)
                        return;
                    audioPlayer("C:\\sliding_puzzle\\src\\소리\\move.wav");
                    moveEvent(btn_set[game_start.zero_row + 1][game_start.zero_col].getText()); // 이동
                    break;
                }
                // 1칸 되돌리기
                case (KeyEvent.VK_BACK_SPACE): {
                    audioPlayer("C:\\sliding_puzzle\\src\\소리\\reset.wav");
                    game_start.func_btn[3].doClick(); // RESET버튼을 클릭하게한다.
                    break;
                }
                case (KeyEvent.VK_R): {
                    game_start.func_btn[btn_set.length-3].doClick();    // 현재 퍼즐판의 사이즈의 보드를 RESET한다
                    game_start.btn_set[game_start.zero_row][game_start.zero_col].setFocusable(true); // 포커스를 잃지 않게 재조정
                    break;
                }
                case (KeyEvent.VK_C): {
                    game_start.completeBoard(); // 보드를 강제로 완성되게 하는 함수
                    // 보드가 완성되었는지 확인하고 완성되었다면 게임이 끝났다는 객체 생성
                    if (game_start.checkGame()) {
                        View_endGame end = new View_endGame();
                    }
                    else
                        System.out.println("error");

                    break;
                }
                // 이전화면으로 되돌아가기
                case (KeyEvent.VK_Z): {
                    // 현재의 상태가 초기화면이라면 return
                    if(game_start.state<=0)
                        return;
                    game_start.state--; // 현재의 상태값을 1 반감시킨다.

                    switch (game_start.state)
                    {
                        case (0) :
                        {
                            game_start.frame.dispose(); // 게임창을 닫는다
                            View_basicFrame start = new View_basicFrame(); // 새로운 화면을 생성한다.
                            break;
                        }
                    }
                    break;
                }
                // 플레이하고 있는 해당 퍼즐판의 원본을 보여주는 이벤트
                case (KeyEvent.VK_P): {
                    puzzle_picture = new View_puzzlePicture(); // 퍼즐의 원본이 담겨있는 프레임 객체 생성
                    puzzle_picture.puzzlePicture(game_start.size); // 함수 호출
                    break;
                }
            }
        }
    }


    // 사운드를 재생하는 함수
    public void audioPlayer(String sound){
        File file = new File(sound);    // sound file을 읽어온다.
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);    // 스트림으로부터 지정한 바이트를 읽는다. 사운드 파일을 스트림으로 읽어들인다.
            Clip clip = AudioSystem.getClip();  // 재생에 사용할 수 있는 clip획득
            clip.open(stream);  // 스트림을 개방시킨다.
            clip.start();   // 소리 재생
        } catch(Exception e) {
            e.printStackTrace();    // 오류가 날 경우 오류 메시지 출력
        }
    }

    // 보드판을 리셋할 때마다 변수들을 초기화시켜준다.
    public void resetParameter(){
        game_start.menu.removeAll();    // JPanel을 지우고 다시 쓰기위해서 컴포넌트 삭제
        game_start.find_zero.clear();   // 각종 스택 변수들 초기화 및 유저가 움직인 횟수 초기화
        game_start.find_user.clear();
        game_start.user_input.clear();
        game_start.user_count=0;
    }
}

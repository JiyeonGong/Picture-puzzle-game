import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Model_setBoard extends View_puzzlePicture{


    // 사이즈를 받아서 사이즈만큼 1차원 숫자를 return
    public int[] setNumber(int size) {
        int[] number = new int[size];
        for (int i = 0; i < size; i++)
            number[i] = i;
        return number;
    }

    // 보드판을 셋팅하는 함수 이 함수에서는 보드판을 무작위로 셋팅한다.
    public void setBoard(int[] number, JButton[][] btn_set) {
        Random r = new Random();
        int use_index;  // 난수가 저장될 변수 난수 값이 버튼에 들어갈 텍스트 값
        int check_index = 0; //반복문을 제어할 제어변수

        for (int i = 0; i < btn_set.length; i++) {
            for (int j = 0; j < btn_set.length; j++) {
                use_index = r.nextInt(number.length); // 난수 값 저장

                // 반복문은 check_index가 보드판의 크기만큼 돌면서 값을 넣을 number값이 -1이 아닐때까지 돈다.
                while (check_index < btn_set.length*btn_set.length && number[use_index] == -1) {
                    use_index = r.nextInt(number.length);
                }
                btn_set[i][j].setText(String.valueOf(number[use_index]));
                if ( 0 == number[use_index] ) {
                    btn_set[i][j].setEnabled(false);    // 0번버튼은 사용하지 못하게 막는다.
                    zero_row=i;
                    zero_col=j;
                }
                number[use_index] = -1; // 중복셋팅 방지
                check_index = check_index + 1;
            }
        }
    }

    // 이미지의 사이즈를 재조정하는 함수
    public ImageIcon setImageResize(String path, int size)
    {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();    // 이미지아이콘을 이미지로 변환
        Image changeImg = img.getScaledInstance(size,size,Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);
        return changeIcon;

    }
}

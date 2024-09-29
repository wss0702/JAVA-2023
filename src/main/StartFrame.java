package main;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class StartFrame extends JFrame {
    ImageIcon icon = new ImageIcon("src/image/qorud.png");
    // ImageIcon을 이용하여 "src/image/background.jpg"을 읽어들이고 Image를 얻는다.
    Image image = icon.getImage();
    // 입장 버튼을 생성한다.
    JButton but = new JButton("");
    
    public StartFrame() {
        // 닫기 버튼을 누를 때 프로그램을 종료한다.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 프레임의 크기 설정
        setBounds(100, 80, 1280, 720);
        // 화면 타이틀을 설정
        this.setTitle("시작화면");
        Font font = new Font("굴림", Font.BOLD, 20);
        getContentPane().setLayout(null);
        but.setIcon(new ImageIcon(StartFrame.class.getResource("/image/589566.png")));
        but.setBounds(388, 357, 485, 57);
        but.setFont(font);
        // 버튼을 투명하게 만듦
        but.setOpaque(false);
        but.setContentAreaFilled(false);
        but.setBorderPainted(false);
        but.setBorder(null);
        but.setForeground(new Color(0, 0, 0, 0));

        // 프레임에 버튼 추가
        getContentPane().add(but);
        // 입장 버튼을 누를 때 clientFrame을 띄우도록 한다.
        but.addActionListener(new ActionListener() {

            // 입장 버튼을 누를 때 호출된다.
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                // clientFrame을 띄우도록 한다.
                clientFrame.main(null);
                setVisible(false);
            }

        });
    }
    /**프레임이 다시 그려질 때 호출된다.*/
    public void paint(Graphics g) {
        // image를 그린다.
        g.drawImage(image, 0,  0,  getWidth(), getHeight(),  null);
        // 입장 버튼을 그린다.
       
    }
    /**프로그램을 시작할 때 호출된다.*/
    public static void main(String[] args) {
        // StartFrame을 생성하고 화면에 보여준다.
        StartFrame sf = new StartFrame();
        sf.setVisible(true);
    }
}

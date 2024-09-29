package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class clientFrame {
    // 자유롭게 사용하려면 여기에 필드로 선언해야 한다
    // 채팅창 프레임을 구성하는 컴포넌트
    // textarea 한줄 이상의 문자 입력 보여주기
    private JFrame frame;
    private JTextArea textarea;
    private JTextField sendMsgTf;
    private JScrollPane scrollPane;

    public int USERSCORE = 0;
    public int WITHSCORE = 0;
    public int Round = 1;
    // 서버와의 통신을 위한 소켓
    private Socket socket;
    private BufferedWriter bw;

    private JLabel userScore;
    private JLabel withScore;

    public JPanel panel_2;

    public JPanel panel;

    public JLabel lblNewLabel_4;
    public JLabel lblNewLabel_4_1;
    public JLabel lblNewLabel_4_1_2;
    public JLabel lblNewLabel_4_2;
    public JLabel lblNewLabel_4_2_1;
    public JLabel lblNewLabel_4_2_1_1;
    public JLabel lblNewLabel_4_2_2;
    public JLabel lblNewLabel_4_2_2_1;
    public JLabel lblNewLabel_4_2_2_1_1;
    public ImageIcon card = new ImageIcon("image/img1.png");

    public clientFrame() {
        frame = new JFrame();

        frame.setBounds(100, 80, 1280, 720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));

        // 프레임 보이기 시작
        frame.setVisible(true);

        ImageIcon imageIcon = new ImageIcon("image/background.jpg");

        textarea = new JTextArea();
        textarea.setEditable(false);
        textarea.setBounds(870, 303, 330, 307);
        textarea.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        frame.getContentPane().add(textarea);
        textarea.setEditable(false);// 쓰기를 금지함 edit 할 수 없는 상태
        textarea.setWrapStyleWord(true);
        textarea.setLineWrap(true);
        textarea.setBackground(new Color(255, 255, 255));
        textarea.setOpaque(true);

        scrollPane = new JScrollPane(textarea);
        scrollPane.setBounds(870, 303, 330, 307);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // As needed 즉 필요에의해서 내용이 많아지면 스크롤 바가 생긴다
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // 가로 스크롤은 안만든다
        scrollPane.setAutoscrolls(true);
        frame.getContentPane().add(scrollPane);
        sendMsgTf = new JTextField();
        sendMsgTf.setBounds(870, 621, 330, 28);
        sendMsgTf.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        sendMsgTf.setBackground(new Color(255, 255, 255));
        sendMsgTf.setForeground(new Color(0, 0, 0));
        // frame.setTitle("합!결!");

        JPanel panel_2 = new JPanel();
        panel_2.setOpaque(false);
        panel_2.setBounds(0, 0, 1280, 720);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout(null);
        panel_2.add(sendMsgTf);
        sendMsgTf.setColumns(10);
        panel_2.setLayout(null);

        JButton btnNewButton_1 = new JButton();
        btnNewButton_1.setBounds(1221, 10, 47, 41);
        btnNewButton_1.setOpaque(false);
        btnNewButton_1.setContentAreaFilled(false);
        btnNewButton_1.setBorderPainted(false);
        btnNewButton_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/나가기3.png")));

        panel_2.add(btnNewButton_1);

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JLabel lblNewLabel_3_1_2 = new JLabel("[상대방]");
        lblNewLabel_3_1_2.setBounds(37, 286, 84, 35);
        lblNewLabel_3_1_2.setFont(new Font("맑은 고딕", Font.BOLD, 21));
        panel_2.add(lblNewLabel_3_1_2);
        lblNewLabel_3_1_2.setBackground(Color.WHITE);

        JLabel lblNewLabel_3_1_1 = new JLabel("VS");
        lblNewLabel_3_1_1.setBounds(58, 260, 26, 35);
        lblNewLabel_3_1_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        panel_2.add(lblNewLabel_3_1_1);
        lblNewLabel_3_1_1.setBackground(Color.WHITE);

        JLabel lblNewLabel_3_1 = new JLabel("[나]");
        lblNewLabel_3_1.setBounds(52, 233, 39, 35);
        lblNewLabel_3_1.setFont(new Font("맑은 고딕", Font.BOLD, 21));
        panel_2.add(lblNewLabel_3_1);
        lblNewLabel_3_1.setBackground(Color.WHITE);

        JLabel lblNewLabel_3 = new JLabel("ROUND");
        lblNewLabel_3.setBounds(304, 45, 422, 41);
        lblNewLabel_3.setForeground(new Color(0, 0, 0));
        lblNewLabel_3.setBackground(new Color(255, 255, 255));
        lblNewLabel_3.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        panel_2.add(lblNewLabel_3);
        lblNewLabel_3.setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBounds(309, 107, 531, 531);
        panel.setBackground(new Color(255, 255, 255));
        panel_2.add(panel);
        panel.setLayout(null);

        lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(new ImageIcon(clientFrame.class.getResource("/image/img1.jpg")));
        lblNewLabel_4.setBounds(23, 38, 140, 130);
        panel.add(lblNewLabel_4);

        lblNewLabel_4_1 = new JLabel("");
        lblNewLabel_4_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/2.png")));
        lblNewLabel_4_1.setBounds(202, 38, 132, 130);
        panel.add(lblNewLabel_4_1);

        lblNewLabel_4_1_2 = new JLabel("");
        lblNewLabel_4_1_2.setIcon(new ImageIcon(clientFrame.class.getResource("/image/7.png")));
        lblNewLabel_4_1_2.setBounds(376, 38, 132, 130);
        panel.add(lblNewLabel_4_1_2);

        lblNewLabel_4_2 = new JLabel("");
        lblNewLabel_4_2.setIcon(new ImageIcon(clientFrame.class.getResource("/image/6.png")));
        lblNewLabel_4_2.setBounds(23, 208, 140, 130);
        panel.add(lblNewLabel_4_2);

        lblNewLabel_4_2_1 = new JLabel("");
        lblNewLabel_4_2_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/333.png")));
        lblNewLabel_4_2_1.setBounds(202, 208, 140, 130);
        panel.add(lblNewLabel_4_2_1);

        lblNewLabel_4_2_1_1 = new JLabel("");
        lblNewLabel_4_2_1_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/8.png")));
        lblNewLabel_4_2_1_1.setBounds(379, 208, 140, 130);
        panel.add(lblNewLabel_4_2_1_1);

        lblNewLabel_4_2_2 = new JLabel("");
        lblNewLabel_4_2_2.setIcon(new ImageIcon(clientFrame.class.getResource("/image/10.png")));
        lblNewLabel_4_2_2.setBounds(23, 377, 140, 130);
        panel.add(lblNewLabel_4_2_2);

        lblNewLabel_4_2_2_1 = new JLabel("");
        lblNewLabel_4_2_2_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/9.png")));
        lblNewLabel_4_2_2_1.setBounds(202, 377, 140, 130);
        panel.add(lblNewLabel_4_2_2_1);

        lblNewLabel_4_2_2_1_1 = new JLabel("");
        lblNewLabel_4_2_2_1_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/4.png")));
        lblNewLabel_4_2_2_1_1.setBounds(379, 377, 140, 130);
        panel.add(lblNewLabel_4_2_2_1_1);
        
        JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(clientFrame.class.getResource("/image/img3.png")));
		lblNewLabel_5.setBounds(23, 38, 140, 130);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("");
		lblNewLabel_5_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/img2.png")));
		lblNewLabel_5_1.setBounds(202, 38, 132, 130);
		panel.add(lblNewLabel_5_1);

		JLabel lblNewLabel_5_1_2 = new JLabel("");
		lblNewLabel_5_1_2.setIcon(new ImageIcon(clientFrame.class.getResource("/image/img9.png")));
		lblNewLabel_5_1_2.setBounds(376, 38, 132, 130);
		panel.add(lblNewLabel_5_1_2);

		JLabel lblNewLabel_5_2 = new JLabel("");
		lblNewLabel_5_2.setIcon(new ImageIcon(clientFrame.class.getResource("/image/5.png")));
		lblNewLabel_5_2.setBounds(23, 208, 140, 130);
		panel.add(lblNewLabel_5_2);

		JLabel lblNewLabel_5_2_1 = new JLabel("");
		lblNewLabel_5_2_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/333.png")));
		lblNewLabel_5_2_1.setBounds(202, 208, 140, 130);
		panel.add(lblNewLabel_5_2_1);

		JLabel lblNewLabel_5_2_1_1 = new JLabel("");
		lblNewLabel_5_2_1_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/11.png")));
		lblNewLabel_5_2_1_1.setBounds(379, 208, 140, 130);
		panel.add(lblNewLabel_5_2_1_1);

		JLabel lblNewLabel_5_2_2 = new JLabel("");
		lblNewLabel_5_2_2.setIcon(new ImageIcon(clientFrame.class.getResource("/image/4.png")));
		lblNewLabel_5_2_2.setBounds(23, 377, 140, 130);
		panel.add(lblNewLabel_5_2_2);

		JLabel lblNewLabel_5_2_2_1 = new JLabel("");
		lblNewLabel_5_2_2_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/10.png")));
		lblNewLabel_5_2_2_1.setBounds(202, 377, 140, 130);
		panel.add(lblNewLabel_5_2_2_1);

		JLabel lblNewLabel_5_2_2_1_1 = new JLabel("");
		lblNewLabel_5_2_2_1_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/6.png")));
		lblNewLabel_5_2_2_1_1.setBounds(379, 377, 140, 130);
		panel.add(lblNewLabel_5_2_2_1_1);
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(0, 112, 132, 100);
        panel_2.add(lblNewLabel);

        lblNewLabel.setIcon(new ImageIcon(clientFrame.class.getResource("/image/New Piskel (1)9.png")));

        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setBounds(19, 342, 113, 113);
        panel_2.add(lblNewLabel_1);

        lblNewLabel_1.setIcon(new ImageIcon(clientFrame.class.getResource("/image/0111.png")));

        //스타트 버튼
        
        /*JButton btnNewButton_10 = new JButton();
        btnNewButton_10.setBounds(1097, 219, 121, 57);
        btnNewButton_10.setIcon(new ImageIcon(clientFrame.class.getResource("/image/45.png")));
        btnNewButton_10.setOpaque(false);
        btnNewButton_10.setContentAreaFilled(false);
        btnNewButton_10.setBorderPainted(false);
        btnNewButton_10.setFont(new Font("둘기마요_고딕", Font.BOLD, 20));
        panel_2.add(btnNewButton_10);
        btnNewButton_10.setBackground(Color.WHITE);*/
        
       

            // 입장 버튼을 누를 때 호출된다.
            
        JLabel lblNewLabel_4_1_1 = new JLabel("SCORE");
        lblNewLabel_4_1_1.setBounds(139, 129, 98, 28);
        lblNewLabel_4_1_1.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        panel_2.add(lblNewLabel_4_1_1);

        userScore = new JLabel("");
        userScore.setForeground(new Color(255, 255, 51));
        userScore.setFont(new Font("맑은 고딕", Font.BOLD, 32));
        userScore.setBounds(144, 146, 85, 85);
        userScore.setText(Integer.toString(USERSCORE));
        panel_2.add(userScore);

        JLabel lblNewLabel_4_1_1_1 = new JLabel("SCORE");
        lblNewLabel_4_1_1_1.setBounds(139, 355, 98, 28);
        lblNewLabel_4_1_1_1.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        panel_2.add(lblNewLabel_4_1_1_1);

        withScore = new JLabel("");
        withScore.setForeground(new Color(255, 255, 51));
        withScore.setFont(new Font("맑은 고딕", Font.BOLD, 32));
        withScore.setBounds(144, 370, 85, 85);
        withScore.setText(Integer.toString(WITHSCORE));
        panel_2.add(withScore);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(290, 89, 568, 568);
        panel_1.setBackground(new Color(120, 163, 248));
        panel_2.add(panel_1);

        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBounds(852, 286, 366, 371);
        panel_1_1.setBackground(new Color(120, 163, 248));
        panel_2.add(panel_1_1);

        JLabel label = new JLabel();
        label.setBounds(0, 0, 1280, 720);
        panel_2.add(label);
        label.setOpaque(false);
        label.setIcon(new ImageIcon(clientFrame.class.getResource("/image/background.jpg")));

        sendMsgTf.addKeyListener(new MsgSendListener());

        frame.setVisible(true);
        // 텍스트 필드에 키 리스너를 등록
        // 텍스트 필드를 지켜보고 있다가 특정 상황이 오면
        // 이벤트 리스너에 정의된 내용 실행

    }

    // 소켓 설정을 위한 세터
    
    public void SetSocket(Socket socket) {
        this.socket = socket;
        try {
            OutputStream out = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 내부 클래스로 이벤트 리스너 만들기
   
    class MsgSendListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }


        @Override
        public void keyReleased(KeyEvent e) {// 키가 눌렸다가 떼어졌을때
            // 엔터키가 눌렸다가 떼어지면 텍스트 필드에 있는 내용이 텍스트 에어리어에 나타나게
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {// 각각의 키들이 가지고 있는 코드 값이 나타난다
                // VK_ENTER = 상수 , 엔터 키에 대한 키값을 의미한다
                String msg = sendMsgTf.getText();
                System.out.println(msg);

                textarea.append("[ 나 ]: " + msg + "\n");
                sendMsgTf.setText("");
                try {
                    bw.write(msg + "\n");// 서버에게 보내는 메세지
                    bw.flush();

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } 

            }

        }

    }

    // 수신 스레드 작성
    class Server_message_Receive_ClientThread implements Runnable {
        private Socket socket;

        public Server_message_Receive_ClientThread(Socket socket) {
            this.socket = socket;

        }

        public void run() {
            // 서버로부터 오는 메세지를 읽어서 textarea 추가

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                while (true) {
                    String msg = br.readLine();// 메세지 한줄 읽어오기
                    if (msg.equals("[나]합 정답입니다.")) {
                        textarea.append("[Server]" + msg + "\n");
                        USERSCORE++;
                        userScore.setText(Integer.toString(USERSCORE));
                    } else if (msg.equals("[상대방]합 정답입니다.")) {
                        textarea.append("[Server]" + msg + "\n");
                        WITHSCORE++;
                        withScore.setText(Integer.toString(WITHSCORE));
                    } else if (msg.equals("[나]결 정답입니다.")) {
                        textarea.append("[Server]" + msg + "\n");
                        USERSCORE += 3;
                        userScore.setText(Integer.toString(USERSCORE));
                        Round++;
                        if (Round == 2) {
                            lblNewLabel_4.setIcon(card);
                            lblNewLabel_4_1.setIcon(card);
                            lblNewLabel_4_1_2.setIcon(card);
                            lblNewLabel_4_2.setIcon(card);
                            lblNewLabel_4_2_1.setIcon(card);
                            lblNewLabel_4_2_1_1.setIcon(card);
                            lblNewLabel_4_2_2.setIcon(card);
                            lblNewLabel_4_2_2_1.setIcon(card);
                            lblNewLabel_4_2_2_1_1.setIcon(card);
                            textarea.append("[Server]Round2 Start!\n");
                            
                        }
                    } else if (msg.equals("[상대방]결 정답입니다.")) {
                        textarea.append("[Server]" + msg + "\n");
                        WITHSCORE += 3;
                        withScore.setText(Integer.toString(WITHSCORE));
                        Round++;
                        if(Round == 2) {
                        	lblNewLabel_4.setIcon(card);
                            lblNewLabel_4_1.setIcon(card);
                            lblNewLabel_4_1_2.setIcon(card);
                            lblNewLabel_4_2.setIcon(card);
                            lblNewLabel_4_2_1.setIcon(card);
                            lblNewLabel_4_2_1_1.setIcon(card);
                            lblNewLabel_4_2_2.setIcon(card);
                            lblNewLabel_4_2_2_1.setIcon(card);
                            lblNewLabel_4_2_2_1_1.setIcon(card);

                            textarea.append("[Server]Round2 Start!\n");

                        }

                    } else {
                        textarea.append("[상대방]" + msg + "\n");
                    }

                }

            } catch (Exception e) {

                textarea.append("연결이 종료되었습니다.");
                System.out.println("연결이 종료되었습니다.");

            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();// 다 쓴 소켓 닫기
                    }
                } catch (Exception e2) {

                }
            }

        }

    }

    public static void main(String[] args) {

        try {
            // 서버 아이피 , 포트번호 -> 소켓 생성 -> 연결 요청
            Socket socket = new Socket("127.0.0.1", 9101);
            // 소켓 객체 생성
            clientFrame cf = new clientFrame();
            cf.SetSocket(socket);// 메인에서 프레임 생성
            Server_message_Receive_ClientThread th1 = cf.new Server_message_Receive_ClientThread(socket);
            // TcpClientReceiveThread가 내부 클래스로 선언되어 있기 때문에
            // cf로 접근해서 socket을 전달한다
            new Thread(th1).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
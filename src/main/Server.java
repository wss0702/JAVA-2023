package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ServerManager {
	private List<Socket> socketList;
	private String[][] Answer = { { "합127", "합169", "합248", "합259", "합368" }, { "합156", "합679" },
			{ "합178", "합238", "합459" } };
	private int[] answer_size = {5,2,3};
	private int round = 0;
	private String f = "결";
	public ServerManager() {
		socketList = new ArrayList<Socket>();
	}

	public void AddSocket(Socket socket) {
		this.socketList.add(socket);
		new Thread(new ReceiverThread(socket)).start();
	}

	class ReceiverThread implements Runnable {
		private Socket socket;

		public ReceiverThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (true) {
					String msg = in.readLine();// 내가 쓴 메세지
					System.out.println(msg);

					Socket tmpSocket = null;
					int ch = 0;
				
					try {
						for (int i = 0; i < socketList.size(); i++) {
							tmpSocket = socketList.get(i);
							int flag = 0;// 정답이면 1로 변환
							int final_flag = 0;
							int answer_zero = 0;
							for (int answer_serach = 0; answer_serach < answer_size[round]; answer_serach++) {
								if (msg.equals(Answer[round][answer_serach])) {
									flag = 1;
								}
								if(Answer[round][answer_serach].equals("0"))answer_zero++;
								if(msg.equals("결") && answer_zero == answer_size[round]) {
									final_flag = 1;
									if(round != 1 && ch == 1) round++;
									ch++;
									
								} 
							}
									
							
							if (socket.equals(tmpSocket) && flag == 0 && final_flag == 0)
								continue;
							else if ((socket.equals(tmpSocket) && final_flag == 1)) {
								BufferedWriter out = new BufferedWriter(
										new OutputStreamWriter(tmpSocket.getOutputStream()));
								out.write("[나]결 정답입니다.\n"); // 정답을 맞췄을 때 나한테 보내는 메세지
								out.flush();
							}
							else if ((socket.equals(tmpSocket) && flag == 1)) {
								BufferedWriter out = new BufferedWriter(
										new OutputStreamWriter(tmpSocket.getOutputStream()));
								out.write("[나]합 정답입니다.\n"); // 정답을 맞췄을 때 나한테 보내는 메세지
								out.flush();
							} else if (flag == 1) {
								BufferedWriter out = new BufferedWriter(
										new OutputStreamWriter(tmpSocket.getOutputStream()));
								out.write(msg + "\n");
								out.write("[상대방]합 정답입니다.\n"); // 정답을 맞췄을 때 상대방한테 보내는 메세지
								out.flush();
							}else if (final_flag == 1) {
								BufferedWriter out = new BufferedWriter(
										new OutputStreamWriter(tmpSocket.getOutputStream()));
								out.write("결\n[상대방]결 정답입니다.\n"); // 정답을 맞췄을 때 상대방한테 보내는 메세지
								out.flush();
							}else {
								BufferedWriter out = new BufferedWriter(
										new OutputStreamWriter(tmpSocket.getOutputStream()));
								out.write(msg + "\n"); // 상대방한테 보내는 메세지
								out.flush();
							}
						}
						for (int answer_serach = 0; answer_serach < answer_size[round]; answer_serach++) {
							if (msg.equals(Answer[round][answer_serach])) {
								Answer[round][answer_serach] = "0";
							}
						}
					} catch (Exception e) {
						System.out.println(tmpSocket.getRemoteSocketAddress() + "conntection end");
						socketList.remove(tmpSocket);

						System.out.println("=======now Client=================");
						for (Socket s : socketList) {
							System.out.println(s.getRemoteSocketAddress());
						}
						System.out.println("==================================");
					}

				}
			} catch (Exception e) {

			} finally {
				if (socket != null) {
					System.out.println(socket.getRemoteSocketAddress() + "connetion end");
					socketList.remove(socket);
					// 연결을 끊은 클라이언트를 위한 소켓 제거

					System.out.println("=======now Client=================");
					for (Socket s : socketList) {
						System.out.println(s.getRemoteSocketAddress());
					}
					System.out.println("==================================");
				}
			}
		}
	}
}

public class Server {
	public static void main(String[] args) {
		ServerManager server_manager = new ServerManager();
		try {
			ServerSocket serversocket = new ServerSocket(9101);
			while (true) {
				Socket socket = serversocket.accept();
				System.out.println("connetion");
				server_manager.AddSocket(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
/* TCPEchoServer.java */
import java.io.*;
import java.net.*;
import java.util.Date;
import java.lang.System;
import java.lang.Thread;
import java.lang.Runnable;

public class Echo01_server {
	public final static int serverPort = 4347;
	public static int count = 0;

	@SuppressWarnings({"resource"})
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket connection = null;
		try {
			/* 创建一个ServerSocket监听端口 */
			server = new ServerSocket(serverPort);
			System.out.println("Server start running...");
			while (true) {
				/* accept()阻塞等待客户返回Socket */
				connection = server.accept();
				System.out.println("Socket " + count + " established...");
				/* 创建一个线程与该用户交互 */
				Thread workThread = new Thread(new Handler(connection, count++));
				workThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Handler implements Runnable {
		private Socket socket = null;
		private int index = -1;
		
		public Handler(Socket _socket, int _index) {
			this.socket = _socket;
			this.index = _index;
		}

		public void run() {
			BufferedReader cin = null;
			Writer cout = null;
			try {
				/* 从Socket读取输入 */
				cin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				/* 写入数据到Socket中 */
				cout = new OutputStreamWriter(socket.getOutputStream());
				String line;
				/* 当Socket连接正常时 */
				while (socket.isConnected() && !socket.isClosed()) {
					/* 当阻塞成功获取用户输入时 */
					line = cin.readLine();
					/* 获取当前系统时间 */
					Date currentTime = new Date();
					String echo = currentTime.toString().split(" ")[3] + " " + line;
					/* 打印系统日志记录用户输入 */
					System.out.println(echo);
					/* 发送数据给用户 */
					cout.write(echo + '\n');
					/* 刷新输出流使用户马上收到消息 */
					cout.flush();
				}
			} catch (SocketException e) {
				// do nothing
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					/* 安全关闭输入输出和Socket */
					if (socket != null) {
						cout.close();
						cin.close();
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Socket " + index + " exit...");
			}
		}
	}
}

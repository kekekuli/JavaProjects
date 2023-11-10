/* TCPEchoClient.java */
import java.io.*;
import java.net.*;

public class Echo01_client {
	public final static String serverIP = "localhost";
	public final static int serverPort = 4347;
	public static String userName = null;

	public static void main(String[] args) {
		Socket client = null;
		BufferedReader sin = null;
		Writer cout = null;
		BufferedReader cin = null;
		try {
			/* 建立Socket连接 */
			client = new Socket(serverIP, serverPort);
			/* 从用户键盘读取输入 */
			sin = new BufferedReader(new InputStreamReader(System.in));
			/* 写入数据到Socket中 */
			cout = new OutputStreamWriter(client.getOutputStream());
			/* 从Socket读取输入 */
			cin = new BufferedReader(new InputStreamReader(client.getInputStream()));
			/* 登陆 */
			System.out.print("Please login: ");
			userName = sin.readLine();
			/* 获取用户输入的一行，exit则退出 */
			String line;
			System.out.print("> ");
			line = sin.readLine();
			while (!line.equals("exit")) {
				/* 将用户输入发送给Server */
				cout.write("[" + userName + "]: " + line + "\n");
				/* 刷新输出流使Server马上收到用户输入 */
				cout.flush();
				/* 从Server读入输入并显示 */
				System.out.println(cin.readLine());
				/* 读取用户下一行输入 */
				System.out.print("> ");
				line = sin.readLine();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			/* 安全关闭输入输出和Socket */
			try {
				cout.close();
				cin.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
